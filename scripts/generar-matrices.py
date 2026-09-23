#!/usr/bin/env python3
"""Genera matrices desde la migración que consume Flyway; --check detecta deriva."""
import argparse
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
SQL = (ROOT / 'backend/src/main/resources/db/migration/V2__catalogos.sql').read_text()

def bloque(tabla):
    m = re.search(rf'INSERT INTO {tabla} .*? VALUES\n(.*?);', SQL, re.S)
    if not m:
        raise ValueError(f'No se encontró el catálogo {tabla}')
    return m.group(1)

roles = [(int(i), c) for i,c in re.findall(r"\((\d+), '([^']+)', '[^']+'\)", bloque('rol'))]
permisos = [(int(i), c) for i,c in re.findall(r"\((\d+), '([^']+)', '[^']+'\)", bloque('permiso'))]
asignaciones = {(int(a), int(b)) for a,b in re.findall(r'\((\d+),(\d+)\)', bloque('rol_permiso'))}
reglas = re.findall(r"\('([^']+)', '([^']+)', '([^']+)', '(\[[^']*\])', '(\[[^']*\])', '(\{[^']*\})', (TRUE|FALSE)\)", bloque('politica'))

CODIGO = re.compile(r'public String codigo\(\)\s*\{\s*return "([^"]+)";')
reglas_java = set()
for archivo in (ROOT / 'backend/src/main/java/com/techcorp/securedocs/autorizacion/abac/reglas').glob('Regla*.java'):
    codigo = CODIGO.search(archivo.read_text())
    if not codigo:
        raise ValueError(f'La regla no declara código: {archivo.name}')
    reglas_java.add(codigo.group(1))
politicas_sql = {fila[0] for fila in reglas}
if reglas_java != politicas_sql:
    raise SystemExit(f'Deriva ABAC entre Flyway y motor: SQL={politicas_sql}, Java={reglas_java}')

acciones_java = set(re.findall(r'\b[A-Z_]+\("([A-Z_]+)"\)',
    (ROOT / 'backend/src/main/java/com/techcorp/securedocs/autorizacion/Accion.java').read_text()))
permisos_sql = {codigo for _, codigo in permisos}
if acciones_java != permisos_sql:
    raise SystemExit(f'Deriva RBAC entre Flyway y acciones: SQL={permisos_sql}, Java={acciones_java}')

rbac = ['# Matriz RBAC', '', 'Generada por `python3 scripts/generar-matrices.py` desde `V2__catalogos.sql`. La API usa este catálogo mediante `ServicioRbac`.', '', '| Operación | ' + ' | '.join(c for _,c in roles) + ' |', '| --- | ' + ' | '.join('---' for _ in roles) + ' |']
for pid, codigo in permisos:
    rbac.append('| ' + codigo + ' | ' + ' | '.join('✓' if (rid,pid) in asignaciones else '—' for rid,_ in roles) + ' |')
rbac += ['', 'Para consultar o modificar un documento, RBAC es condición necesaria. ABAC puede denegar aunque exista permiso.', '']

abac = ['# Matriz ABAC', '', 'Generada por `python3 scripts/generar-matrices.py` desde `V2__catalogos.sql`. La lógica está en `backend/src/main/java/com/techcorp/securedocs/autorizacion/abac/reglas`.', '', '| Código | Política | Acciones | Roles exentos | Parámetros | Activa |', '| --- | --- | --- | --- | --- | --- |']
for codigo, nombre, _, acciones, exentos, parametros, activa in reglas:
    abac.append(f'| {codigo} | {nombre} | `{acciones}` | `{exentos}` | `{parametros}` | {"Sí" if activa == "TRUE" else "No"} |')
abac += ['', 'Las políticas aplicables se evalúan juntas. Si alguna falla, la decisión es denegar y la auditoría guarda todas las causas. P7 no puede desactivarse mediante la API.', '']

p = argparse.ArgumentParser()
p.add_argument('--check', action='store_true', help='fallar si la documentación difiere de la migración')
args = p.parse_args()
for nombre, contenido in [('matriz-rbac.md', '\n'.join(rbac)), ('matriz-abac.md', '\n'.join(abac))]:
    destino = ROOT / 'docs' / nombre
    if args.check:
        if not destino.exists() or destino.read_text() != contenido:
            raise SystemExit(f'Matriz desactualizada: {destino}')
    else:
        destino.write_text(contenido)
print('Matrices verificadas' if args.check else 'Matrices generadas')

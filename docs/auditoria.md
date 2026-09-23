# Auditoría de accesos

SecureDocs crea un registro por decisión de autorización y por evento de autenticación. Los datos se guardan en una transacción nueva para que un rechazo o un error posterior de negocio no borre la evidencia. La tabla no tiene claves foráneas: conserva el usuario, rol, departamento y contexto como valores del momento del intento.

## Campos

Cada fila guarda fecha y hora de Lima, usuario, rol, departamento, recurso, acción, resultado (`PERMITIDO` o `DENEGADO`), capa (`AUTH`, `RBAC` o `ABAC`), motivo, políticas evaluadas en JSON, IP, ubicación, dispositivo y departamento del recurso. Las solicitudes sin token se registran como `anonimo` y `AUTH_DENIED`.

## Consulta

`GET /auditoria` exige `VER_AUDITORIA`. Administrador y auditor ven todos los registros; el gerente ve solo los del departamento de sus recursos. La consulta admite `usuario`, `recurso`, `accion`, `resultado`, `desde`, `hasta`, `pagina` y `tamano`. `formato=csv` descarga los mismos registros que puede ver el rol.

Ejemplos, tras obtener un token válido:

```text
GET /auditoria?accion=READ&resultado=DENEGADO
GET /auditoria?usuario=diego.salas&desde=2026-09-23T08:00:00
GET /auditoria?formato=csv
```

La exportación CSV cita todos los valores y neutraliza celdas que podrían interpretarse como fórmulas en hojas de cálculo.

## Consultas SQL útiles

```sql
SELECT fecha_hora, usuario, recurso, accion, resultado, capa, motivo
FROM auditoria ORDER BY id DESC LIMIT 20;

SELECT accion, resultado, COUNT(*) AS intentos
FROM auditoria GROUP BY accion, resultado ORDER BY accion, resultado;

SELECT usuario, motivo FROM auditoria
WHERE resultado = 'DENEGADO' AND fecha_hora >= '2026-09-23 00:00:00';
```

Los triggers de `V3__auditoria_append_only.sql` impiden `UPDATE` y `DELETE`. Las pruebas de integración comprobaron ambos rechazos. Para reiniciar una demostración se recreará el volumen de base de datos mediante el script específico del perfil `demo`; no se modifica una fila de auditoría existente.

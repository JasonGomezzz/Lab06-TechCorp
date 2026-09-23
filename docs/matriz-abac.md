# Matriz ABAC

Generada por `python3 scripts/generar-matrices.py` desde `V2__catalogos.sql`. La lógica está en `backend/src/main/java/com/techcorp/securedocs/autorizacion/abac/reglas`.

| Código | Política | Acciones | Roles exentos | Parámetros | Activa |
| --- | --- | --- | --- | --- | --- |
| P3_PROPIEDAD | Propiedad | `["UPDATE"]` | `["GERENTE","ADMINISTRADOR"]` | `{}` | Sí |

Las políticas aplicables se evalúan juntas. Si alguna falla, la decisión es denegar y la auditoría guarda todas las causas. P7 no puede desactivarse mediante la API.

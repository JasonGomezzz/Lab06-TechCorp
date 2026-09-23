# Casos de prueba de SecureDocs

La guía exige T1–T12 y cinco casos adicionales. Se añadieron T13–T17; B1–B5 son comprobaciones extra del diseño. Las columnas **Observado** y **Captura** se dejan vacías para anotar resultados y evidencias visuales de una ejecución de entrega real. La colección Postman contiene las solicitudes, los logins previos y los `pm.test` correspondientes.

| Caso | Precondición y solicitud | Esperado | Observado | Captura |
|---|---|---|---|---|
| T1 | Diego, `GET /documentos/502`, entorno normal | 200, permitido |  |  |
| T2 | Diego, `GET /documentos/505`, entorno normal | 403, P1 únicamente |  |  |
| T3 | Carlos, `POST /documentos/511/aprobar`, pendiente | 200, publicado |  |  |
| T4 | Diego, `POST /documentos/511/aprobar` | 403, RBAC sin evaluar ABAC |  |  |
| T5 | Diego, `GET /documentos/503`, entorno normal | 403, P2 únicamente |  |  |
| T6 | Laura, `DELETE /documentos/509`; ejecutar al final | 204, borrado lógico |  |  |
| T7 | Sofía, `PUT /documentos/502` | 403, RBAC |  |  |
| T8 | Pedro y Rosa, `POST /auth/login` con contraseña válida | 403, usuario no activo, ambos auditados |  |  |
| T9 | Laura, `GET /documentos/503`, hora 19:00 | 403, P4 únicamente |  |  |
| T10 | Laura, `GET /documentos/504`, dispositivo personal | 403, P6 únicamente |  |  |
| T11 | Invitado externo, `GET /documentos/502` | 200, permitido |  |  |
| T12 | Invitado externo, `GET /documentos/503` | 403, P2 y P8 |  |  |
| T13 | Diego, `PUT /documentos/502` | 403, P3 únicamente |  |  |
| T14 | Laura, `PUT /documentos/502` | 200, excepción de P3 |  |  |
| T15 | Juan inicia sesión; admin lo desactiva; Juan usa token anterior en `GET /documentos` | 401, usuario no activo |  |  |
| T16 | Diego, `GET /documentos/502`, ubicación México | 403, P5B únicamente |  |  |
| T17 | Diego, `GET /documentos` antes de T6 | 200, IDs 502, 507, 508 y 509 únicamente |  |  |
| B1 | Invitado externo, `GET /documentos/507` borrador | 403, P8 únicamente |  |  |
| B2 | Invitado vencido, `GET /documentos/502` | 403, P9 únicamente |  |  |
| B3 | Laura, `GET /documentos/503`, horas 07:59, 08:00, 17:59 y 18:00 | 403, 200, 200, 403 |  |  |
| B4 | Admin desactiva P4, Laura repite T9; admin la reactiva y Laura repite | 200 y luego 403 por P4 |  |  |
| B5 | Marta, `PUT /documentos/503` | 403, P1, P2 y P3 juntas |  |  |

**Entorno normal:** hora 10:30, fecha 2026-09-23, IP 192.168.10.20, ubicación PERU y dispositivo CORPORATIVO. Todos los casos que cambian datos se restauran dentro de la colección salvo T6. Para repetir todo, reiniciar la base demo.

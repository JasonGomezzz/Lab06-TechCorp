# Matriz ABAC

Generada por `python3 scripts/generar-matrices.py` desde `V2__catalogos.sql`. La lógica está en `backend/src/main/java/com/techcorp/securedocs/autorizacion/abac/reglas`.

| Código | Política | Acciones | Roles exentos | Parámetros | Activa |
| --- | --- | --- | --- | --- | --- |
| P1_DEPARTAMENTO | Departamento | `["CREATE","READ","UPDATE","DELETE","APPROVE"]` | `[]` | `{}` | Sí |
| P2_NIVEL_SEGURIDAD | Nivel de seguridad | `["CREATE","READ","UPDATE","DELETE","APPROVE"]` | `[]` | `{}` | Sí |
| P3_PROPIEDAD | Propiedad | `["UPDATE"]` | `["GERENTE","ADMINISTRADOR"]` | `{}` | Sí |
| P4_HORARIO | Horario | `["READ","UPDATE","DELETE","APPROVE"]` | `[]` | `{"nivelMinimo":4,"inicio":"08:00","fin":"18:00"}` | Sí |
| P5A_PAIS_USUARIO | País del usuario | `["CREATE","READ","UPDATE","DELETE","APPROVE"]` | `[]` | `{}` | Sí |
| P5B_UBICACION | Ubicación | `["READ","UPDATE","DELETE","APPROVE"]` | `[]` | `{"pais":"PERU"}` | Sí |
| P6_DISPOSITIVO | Dispositivo | `["READ","UPDATE","DELETE","APPROVE"]` | `[]` | `{"nivelMinimo":4}` | Sí |
| P7_ESTADO_USUARIO | Estado del usuario | `["*"]` | `[]` | `{}` | Sí |
| P8_INVITADO | Invitados | `["READ"]` | `[]` | `{"nivelMaximo":1}` | Sí |
| P9_VIGENCIA_INVITADO | Vigencia de invitados | `["READ"]` | `[]` | `{}` | Sí |

Las políticas aplicables se evalúan juntas. Si alguna falla, la decisión es denegar y la auditoría guarda todas las causas. P7 no puede desactivarse mediante la API.

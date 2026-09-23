# Matriz RBAC

Generada por `python3 scripts/generar-matrices.py` desde `V2__catalogos.sql`. La API usa este catálogo mediante `ServicioRbac`.

| Operación | ADMINISTRADOR | GERENTE | SUPERVISOR | EMPLEADO | AUDITOR | INVITADO |
| --- | --- | --- | --- | --- | --- | --- |
| CREAR_DOCUMENTO | ✓ | ✓ | ✓ | ✓ | — | — |
| CONSULTAR_DOCUMENTO | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| MODIFICAR_DOCUMENTO | ✓ | ✓ | ✓ | ✓ | — | — |
| ELIMINAR_DOCUMENTO | ✓ | ✓ | — | — | — | — |
| APROBAR_DOCUMENTO | ✓ | ✓ | ✓ | — | — | — |
| VER_AUDITORIA | ✓ | ✓ | — | — | ✓ | — |
| GESTIONAR_USUARIOS | ✓ | — | — | — | — | — |
| ASIGNAR_ROLES | ✓ | — | — | — | — | — |
| GESTIONAR_CONFIGURACION | ✓ | — | — | — | — | — |

Para consultar o modificar un documento, RBAC es condición necesaria. ABAC puede denegar aunque exista permiso.

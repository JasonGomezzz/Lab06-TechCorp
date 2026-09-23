# Plan y trazabilidad de commits

El historial se construye por unidades que compilan o documentan una pieza verificable. Los asuntos están en español sencillo y no llevan prefijos `feat:`, `fix:` ni equivalentes. No se reescriben los commits publicados ni se crean commits vacíos para alcanzar una cifra.

## Unidades publicadas o preparadas

| Fase | Resultado | Commit |
| --- | --- | --- |
| 0 | Backend y perfiles | `c9b324c` |
| 0 | Docker y guía inicial | `a4c291e` |
| 1 | Esquema, permisos y políticas | `989681d` |
| 1 | Usuarios y documentos demo | `1434906` |
| 1 | Entidades y modelo de datos | `6ed6299` |
| 2 | Login, logout y JWT revocable | `c20c6d9` |
| 3 | Motor ABAC y pruebas | `47e4e08` |
| 3–4 | Autorizador y registro de decisiones | `d46d27c` |
| 4 | Consulta y exportación de auditoría | `85d6fc2` |
| 5 | Documentos y filtrado por recurso | `969c251` |
| 5 | Usuarios y políticas | `93672a1` |
| 5 | Prueba de arquitectura | `f97701d` |
| 5 | Respuesta paginada estable | `596150b` |
| 6 | Postman y ejecución Newman | `9ced5b2` |
| 7 | Interfaz y panel T1–T17 | `e45d532` |
| 7 | Docker frontend y prueba en navegador | `bff4b27` |
| 8 | Matrices generadas desde Flyway | `d9546b3` |
| 8 | Diagramas de arquitectura | `c7d4668` |
| 5 | OpenAPI y cabeceras demo | `d90a794` |
| 2 | Limpieza de tokens revocados expirados | `99f2c61` |
| 5 | OpenAPI solo en demo | `be2876b` |
| 8 | Diagramas exportados a SVG | `1b5b4f9` |
| 8 | README y seguimiento final | `79a53b4` |
| 8 | Guion de demostración | `62a5a8e` |
| 8 | Registro del plan e historial | `e8af1e9` |
| 8 | Corrección y validación cruzada de la matriz ABAC | `952f045` |
| 9 | Verificación final reproducible | `f978729` |

## Cierre propuesto

Estas unidades deben convertirse en commits solo si el trabajo y su verificación se completan. El historial puede superar o quedar por debajo de 25 commits según el alcance real:

1. Incorporar capturas de T1–T17 y Postman hechas por el grupo.
2. Incorporar el video o su enlace cuando el grupo lo grabe.
3. Completar integrantes y observaciones personales de la entrega.

## Reparto sugerido

| Área | Integrante |
| --- | --- |
| Backend, RBAC y ABAC | [completar] |
| Frontend y demostración | [completar] |
| Pruebas, Postman y evidencias | [completar] |
| Documentación y presentación | [completar] |

Los nombres se completan con contribuciones reales; esta tabla no atribuye trabajo de forma ficticia. Antes de cada commit: revisar `git diff --cached`, comprobar ausencia de secretos y ejecutar la prueba que corresponde a la unidad.

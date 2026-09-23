# Seguimiento del laboratorio

## Requisitos de la guía

- Seis roles y matriz de ocho operaciones RBAC.
- Atributos de usuario, documento y entorno; ocho políticas ABAC mínimas.
- Autenticación, usuarios, documentos, autorización centralizada y auditoría.
- API mínima, 12 casos obligatorios y cinco casos adicionales diseñados por el grupo.
- Código, repositorio, README, diagramas, matrices, evidencias, auditoría y video o demostración.

## Decisiones de implementación

El grupo usará Spring Boot, MySQL y React. El diseño separará autenticación, RBAC y ABAC. La implementación de políticas configurables, JWT con revocación, auditoría inmutable y pruebas de arquitectura son decisiones para hacer verificable el requisito especial de la guía.

La política de vigencia de invitados, el simulador de entorno y el panel automático de casos son ampliaciones. Se abordarán después de los requisitos mínimos. No se presentarán capturas ni resultados hasta que provengan de una ejecución real.

## Avance comprobado

Flyway aplicó las tres primeras migraciones en MySQL 8.4. El perfil `demo` cargó 12 usuarios y 10 documentos con IDs fijos. Un segundo arranque mantuvo los mismos conteos. El [modelo de datos](modelo-datos.md) describe las entidades y enlaza el DDL ejecutable.

La autenticación con BCrypt y JWT pasó las pruebas de integración: login, credenciales erróneas, usuario suspendido, `/auth/me`, logout y token emitido antes de desactivar al usuario. El filtro vuelve a consultar el usuario en cada petición; el token no determina su rol ni su estado.

Las políticas ABAC pasaron los casos aislados y los bordes de horario. El orquestador registró decisiones de RBAC y ABAC en MySQL; los triggers rechazaron la actualización y el borrado de registros. La [guía de auditoría](auditoria.md) explica filtros, alcance y exportación.

La API de documentos ya verifica lectura, listado filtrado, modificación, eliminación lógica y aprobación. Las pruebas de integración cubren los casos T1–T7 y T9–T17 relacionados con documentos, incluidos el listado exacto de Diego y los rechazos por una sola política. Los casos con cambios de estado restauran sus datos al terminar.

La gestión de usuarios permite registrar, listar y actualizar solo con `GESTIONAR_USUARIOS`; los cambios de rol requieren también `ASIGNAR_ROLES`. El administrador no puede desactivarse ni quitarse su propio rol. El módulo de políticas permite cambiar configuraciones y activación con `GESTIONAR_CONFIGURACION`; P7 queda protegida. La prueba B4 confirmó que la caché se invalida tras desactivar y reactivar P4.

## Estado de evidencias

Pendientes de implementación y ejecución. Cada caso tendrá solicitud, resultado esperado, resultado observado y evidencia real antes de declararse completo.

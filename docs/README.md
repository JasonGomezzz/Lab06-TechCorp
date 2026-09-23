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

## Estado de evidencias

Pendientes de implementación y ejecución. Cada caso tendrá solicitud, resultado esperado, resultado observado y evidencia real antes de declararse completo.

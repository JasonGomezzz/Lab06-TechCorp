# Seguimiento del laboratorio

## Requisitos de la guía

La guía pide seis roles, una matriz de ocho operaciones RBAC, atributos de usuario/recurso/entorno y ocho políticas ABAC mínimas. También exige autenticación, usuarios, documentos, autorización centralizada, auditoría, API, doce casos obligatorios más cinco diseñados por el grupo, diagramas, evidencias y video o demostración.

## Implementación y ampliaciones

El backend separa autenticación de autorización. El punto central `Autorizador` consulta `ServicioRbac` y `MotorAbac`. MySQL contiene usuarios, documentos, permisos, políticas y auditoría; Flyway aplica las migraciones. Se añadieron P9 (vigencia de invitado), JWT con revocación, protección de auditoría con triggers, simulador de entorno y panel T1–T17.

- [Arquitectura y secuencia de aprobación](arquitectura.md)
- [Modelo de datos](modelo-datos.md)
- [Matriz RBAC](matriz-rbac.md) y [matriz ABAC](matriz-abac.md), generadas desde la migración
- [Auditoría](auditoria.md)
- [Casos T1–T17 y B1–B5](casos-de-prueba.md)
- [Guion del video](GUION-VIDEO.md)
- [Plan de commits](PLAN-DE-COMMITS.md)

## Verificación real

Newman ejecutó [55 solicitudes y 91 comprobaciones sin fallos](evidencias/newman-resumen.md). El panel de navegador ejecutó [T1–T17 con 17 correctos y 0 fallos](evidencias/panel-casos.md). Las pruebas de backend cubren autenticación, RBAC, ABAC, documentos, usuarios, políticas, auditoría y arquitectura. El build de React y TypeScript pasó.

## Evidencias personales pendientes

Las columnas **Observado** y **Captura** del archivo de casos quedan vacías intencionalmente hasta que Jason o el grupo generen y seleccionen las capturas de entrega. El video tampoco está grabado. Los resultados automatizados registrados no sustituyen estas evidencias visuales solicitadas por el curso.

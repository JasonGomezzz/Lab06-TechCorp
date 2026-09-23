# SecureDocs

Laboratorio 6 de Seguridad en la nube: sistema de gestión de documentos y expedientes de TechCorp S.A. con autorización combinada RBAC y ABAC.

## Alcance y estado

El proyecto sigue la guía `GLAB-S06-JFARFAN-2026-02.docx`. La tecnología elegida es Java 21, Spring Boot 3.5.16, MySQL 8 y, en una fase posterior, React con TypeScript. La guía permite elegir la tecnología.

**Estado actual:** el backend ya implementa autenticación, autorización RBAC/ABAC, auditoría y los módulos de documentos, usuarios y políticas. La colección Postman cubre T1–T17 y B1–B5 y pasó una ejecución real con 55 solicitudes y 91 comprobaciones. Faltan el frontend, las matrices finales, las capturas y el video de entrega. Este aviso se actualizará conforme avancen los commits.

## Estructura

- `backend/`: aplicación Spring Boot y Maven Wrapper.
- `docs/`: modelo, decisiones, matrices, casos y evidencias verificadas.
- `postman/`: colección de pruebas de API y entorno local sin contraseña.
- `scripts/`: utilidades de demostración y entrega.
- `docker-compose.yml`: MySQL y backend con perfil `demo`.

## Preparación

1. Instalar Java 21 y Docker para la ejecución completa.
2. Copiar `.env.example` a `.env` y sustituir los valores `CAMBIAR` por secretos locales. Nunca subir `.env`.
3. Compilar el backend con `cd backend && ./mvnw -q -DskipTests compile`.

Las pruebas de autenticación se ejecutan con `cd backend && ./mvnw test`. Usan Testcontainers y se saltan automáticamente si Docker no está disponible. En el perfil `demo`, los usuarios de prueba usan la contraseña de `SEED_PASSWORD`. `JWT_SECRET` debe contener al menos 32 bytes UTF-8; el token dura una hora, y `/auth/logout` revoca su identificador.

La [guía de Postman](postman/README.md) explica cómo ejecutar los 22 casos sin guardar la contraseña en Git. El [resumen de Newman](docs/evidencias/newman-resumen.md) registra el resultado automatizado real; la [plantilla de casos](docs/casos-de-prueba.md) reserva las capturas para la entrega.

MySQL usa el puerto `3307` del equipo para evitar conflictos con instalaciones locales en `3306`. El backend usará el perfil `demo` dentro de Docker. La contraseña de los usuarios de demostración se configurará con `SEED_PASSWORD` cuando exista el sembrador.

## Criterio de autorización

Una operación se permitirá únicamente si RBAC permite la acción y ABAC permite el acceso al recurso en ese entorno. La matriz de permisos, las ocho políticas obligatorias y los casos de prueba se implementarán según la guía. Los componentes de autorización estarán centralizados y los intentos quedarán auditados.

Consulta [el seguimiento del laboratorio](docs/README.md) para distinguir lo exigido por la guía de las ampliaciones planeadas.

# SecureDocs · TechCorp

Laboratorio 06 de Seguridad en la nube. Sistema de documentos con autenticación JWT, permisos por rol (RBAC), políticas por atributos y entorno (ABAC), y auditoría de decisiones.

La guía `GLAB-S06-JFARFAN-2026-02.docx` pide seis roles, ocho operaciones RBAC, ocho políticas ABAC mínimas, API protegida, doce casos base y evidencias. Este repositorio incluye además revocación de tokens, vigencia de invitados (P9), simulación de entorno y un panel de casos T1–T17. Las evidencias de Postman provienen de una ejecución real; las capturas y el video de entrega los prepara el estudiante.

## Arquitectura

React + TypeScript + Vite y Tailwind en `frontend/`; Spring Boot 3.5.16 y Java 21 en `backend/`; MySQL 8.4 con Flyway. La autenticación la realiza Spring Security. `Autorizador` decide cada acceso con `ServicioRbac` y `MotorAbac`. [Diagramas y flujo](docs/arquitectura.md) · [modelo de datos](docs/modelo-datos.md) · [matriz RBAC](docs/matriz-rbac.md) · [matriz ABAC](docs/matriz-abac.md) · [auditoría](docs/auditoria.md).

## Ejecución con Docker

Requisitos: Docker y Docker Compose.

1. Copia `.env.example` a `.env`.
2. Sustituye los valores `CAMBIAR` por contraseñas locales y un `JWT_SECRET` aleatorio de al menos 32 bytes UTF-8. Define `SEED_PASSWORD` para las cuentas demo. No subas `.env`.
3. Ejecuta `docker compose up --build -d`.
4. Abre `http://localhost:3000` (interfaz). La API queda en `http://localhost:8080`; MySQL se expone en `localhost:3307`.

El backend se inicia con perfil `demo`; `VITE_DEMO=true` compila el simulador en el frontend. Para ver el estado usa `docker compose ps`; para detener los servicios, `docker compose down`. `./scripts/reset-demo.sh --confirmar` borra **solo el volumen de datos de este proyecto** y vuelve a crear el conjunto demo; revisa el script antes de usarlo si hay datos propios.

## Desarrollo local

Requisitos: Java 21, Node.js 22, MySQL 8, Docker para las pruebas de integración.

1. Arranca MySQL del Compose: `docker compose up -d mysql` con `.env` configurado.
2. Inicia el backend: `cd backend && DB_URL=jdbc:mysql://localhost:3307/securedocs DB_USER=securedocs DB_PASSWORD=<valor-local> JWT_SECRET=<valor-local> SEED_PASSWORD=<valor-local> SPRING_PROFILES_ACTIVE=demo ./mvnw spring-boot:run`. Sustituye los valores por los de `.env` sin publicarlos.
3. En otra terminal: `cd frontend && cp .env.example .env.local && npm ci && npm run dev`.
4. Abre la URL que indique Vite. Su proxy envía `/api` al backend en `localhost:8080`.

## Cuentas demo

Todas usan `SEED_PASSWORD` del entorno. Ninguna contraseña real está en Git. Los correos `@techcorp.example` y algunas descripciones son datos sintéticos de demostración; la guía fija los nombres, roles, niveles e IDs utilizados por los casos.

| Usuario | Rol | Uso destacado |
| --- | --- | --- |
| `admin` | ADMINISTRADOR | Usuarios, roles, políticas, auditoría |
| `laura.mendez` | GERENTE | Documentos de FINANZAS y auditoría de su área |
| `ana.torres`, `carlos.ruiz` | SUPERVISOR | Aprobación y propiedad |
| `diego.salas`, `marta.quispe`, `juan.temporal` | EMPLEADO | Documentos y casos de denegación |
| `sofia.paredes` | AUDITOR | Lectura y auditoría |
| `invitado.externo`, `invitado.vencido` | INVITADO | Restricciones P8 y P9 |
| `pedro.suspendido`, `rosa.inactiva` | EMPLEADO | Login denegado |

## API y autorización

`POST /auth/login` entrega un JWT de una hora; `POST /auth/logout` revoca su identificador. `GET /auth/me` devuelve rol, atributos y permisos actuales. Recursos: `/documentos`, `/usuarios`, `/politicas` y `/auditoria`. El backend vuelve a leer estado y rol en cada petición. El filtro no usa `@PreAuthorize` ni autoridades de Spring para decidir sobre documentos.

En el perfil demo, la especificación está en `http://localhost:8080/v3/api-docs` y Swagger UI en `http://localhost:8080/swagger-ui.html`. Documentan el esquema Bearer y las cinco cabeceras de simulación. Ambos se desactivan fuera del perfil demo.

El acceso a documentos requiere simultáneamente permiso RBAC y cumplimiento de las políticas ABAC aplicables. Si RBAC deniega, ABAC no se evalúa. Si ABAC deniega, se registran todas las políticas fallidas. El listado se filtra antes de responder. La auditoría registra acción, recurso, contexto, resultado, capa y motivos, y tiene triggers contra UPDATE y DELETE. [Casos T1–T17 y B1–B5](docs/casos-de-prueba.md).

En perfil `demo`, el simulador puede enviar `X-Sim-Hora`, `X-Sim-Ubicacion`, `X-Sim-Dispositivo` y `X-Sim-Ip`. El entorno normal de los casos es 10:30, PERU, CORPORATIVO, 192.168.10.20. Fuera de demo, el backend ignora estas cabeceras. No expongas el perfil demo públicamente: las cabeceras son deliberadamente manipulables para la práctica. El JWT está en `sessionStorage` del navegador; para un despliegue real harían falta HTTPS, política de CSP y una estrategia de sesión endurecida.

## Pruebas y evidencias

- Backend: `cd backend && ./mvnw test` (Testcontainers MySQL; las pruebas que requieren Docker se omiten si no está disponible).
- Frontend: `cd frontend && npm ci && npm run build && npm run lint`.
- Matrices: `python3 scripts/generar-matrices.py --check` detecta deriva entre las tablas de documentación y la migración Flyway.
- Postman: importa `postman/SecureDocs.postman_collection.json` y su entorno. [Instrucciones](postman/README.md) y [resultado real de Newman: 55 solicitudes, 91 comprobaciones, 0 fallos](docs/evidencias/newman-resumen.md).
- Panel de casos: desde la interfaz demo, introduce la contraseña demo y ejecuta T1–T17. T3, T14 y T6 alteran datos; el panel ejecuta T17 antes de T6. Puede exportar un informe Markdown.

Las columnas **Observado** y **Captura** de [casos de prueba](docs/casos-de-prueba.md) quedan listas para la evidencia personal. No se han fabricado capturas ni video. El [guion de video](docs/GUION-VIDEO.md) indica el recorrido sugerido.

El [registro de verificación final](docs/evidencias/verificacion-final.md) contiene los resultados observados de compilación, pruebas y arranque de los tres servicios.

## Estructura del repositorio

- `backend/`: API, motor RBAC/ABAC, migraciones y pruebas.
- `frontend/`: interfaz React y Nginx para Docker.
- `postman/`: colección y entorno sin secretos.
- `docs/`: arquitectura, matrices, modelo, auditoría y evidencias.
- `scripts/`: reinicio demo, generación de matrices y exportación de diagramas.

## Decisiones y límites

El sistema usa rechazo por defecto, políticas centralizadas en la tabla `politica`, caché invalidada tras cambios y reloj en `America/Lima`. La decisión de autorización se registra incluso cuando se deniega. El borrado de documentos es lógico. Las contraseñas se codifican con BCrypt. La revocación de JWT exige persistencia, por lo que el token deja de ser completamente independiente del servidor.

Este es un entorno académico; faltan las capturas, la grabación y los nombres de integrantes para la entrega. **Integrantes:** Jason Gómez; [completar integrantes del grupo]. Repositorio: [Lab06-TechCorp](https://github.com/JasonGomezzz/Lab06-TechCorp).

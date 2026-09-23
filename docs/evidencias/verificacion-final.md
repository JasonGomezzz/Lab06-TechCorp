# Verificación final reproducible

Ejecución local: 23 de septiembre de 2026, 10:43 (America/Lima). Los valores de entorno usados en las pruebas eran solo de laboratorio y no se guardaron en Git.

| Comprobación | Resultado observado |
| --- | --- |
| `cd backend && ./mvnw -q test` | 21 pruebas, 0 fallos, 0 errores, 0 omitidas; MySQL mediante Testcontainers. |
| `cd frontend && npm run build` | Build de TypeScript y Vite correcto. |
| `cd frontend && npm run lint` | Sin errores; oxlint informó cinco avisos `react(set-state-in-effect)` sobre cargas asíncronas de páginas. |
| `python3 scripts/generar-matrices.py --check` | Matrices RBAC y ABAC sincronizadas con Flyway, `Accion` y las clases de reglas. |
| `./scripts/export-diagramas.sh` | Tres diagramas Mermaid convertidos a SVG. |
| `docker compose up -d --build` | Imágenes frontend/backend construidas; MySQL saludable y tres servicios iniciados. |
| Interfaz `http://localhost:3000/` | HTTP 200 y título SecureDocs. |
| `POST /api/auth/login` desde el proxy | HTTP 200 con Diego. |
| `GET /api/auth/me` y `GET /api/documentos` | HTTP 200; Diego ve 502, 507, 508, 509. |
| `GET /api/v3/api-docs` | HTTP 200; `SecureDocs API`, 11 rutas y esquema Bearer. |
| Newman | 55 solicitudes, 91 comprobaciones y 0 fallos; [detalle](newman-resumen.md). |
| Panel de casos en navegador | T1–T17: 17 correctos, 0 fallos; [detalle](panel-casos.md). |

La inspección de archivos rastreados no encontró `.env`, `node_modules`, `target`, `dist`, claves PEM ni archivos `.key`. La búsqueda de `@PreAuthorize`, `hasRole` y `hasAuthority` en producción no dio resultados. `LocalTime.now(reloj)` usa el reloj inyectado; las lecturas de `getRol()` permanecen en adaptación de sujeto, autenticación o gestión de usuarios.

**Pendientes de la entrega personal:** capturas de los casos, grabación de video y nombres de los demás integrantes. Se dejan explícitos para que el grupo los complete con evidencias propias.

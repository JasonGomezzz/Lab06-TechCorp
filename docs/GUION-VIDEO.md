# Guion de demostración (6–8 minutos)

Antes de grabar: `docker compose up --build -d`, datos demo restablecidos, `.env` cerrado y credenciales fuera de pantalla. Mantener visible la interfaz y Postman sin mostrar JWT ni contraseñas. Este documento es un guion; el video aún debe grabarse.

| Minuto | Acción | Resultado que se debe mostrar |
| --- | --- | --- |
| 0:00–0:50 | Presentar SecureDocs y `docs/arquitectura.md`. | React → API → autenticación → RBAC → ABAC → auditoría → MySQL. |
| 0:50–1:40 | Ingresar como `diego.salas` y luego mostrar el menú. | Documentos disponibles; usuarios, políticas y auditoría no figuran para EMPLEADO. |
| 1:40–3:40 | Abrir el panel de casos. Introducir la contraseña demo fuera de cámara y ejecutar T1–T12. | T1 permitido; T2 P1; T3 aprobado; T4 RBAC; T5 P2; T6 al final de la batería; T7 RBAC; T8 inactivos; T9 P4; T10 P6; T11 permitido; T12 P2+P8. |
| 3:40–4:35 | En el simulador cambiar hora a 19:00, dispositivo a PERSONAL y ubicación a MEXICO; consultar recursos y restablecer. | El backend responde por políticas P4, P6 o P5B; las cabeceras solo operan en demo. |
| 4:35–5:25 | Entrar como `admin`, abrir auditoría, filtrar una denegación y exportar CSV. | Usuario, recurso, acción, resultado, capa y motivos. |
| 5:25–6:20 | Mostrar `Autorizador`, `ServicioRbac`, `MotorAbac` y la tabla `politica`/matriz ABAC. | Separación de autenticación y autorización; reglas configuradas en datos. |
| 6:20–7:10 | Enseñar `docker compose ps`, los tres servicios y la colección Postman. | Servicios operativos, 55 solicitudes y 91 comprobaciones de Newman. |
| 7:10–7:40 | Cerrar con aprendizajes y límites del perfil demo. | JWT revocable, auditoría inmutable, entorno simulado solo para laboratorio. |

Al mostrar `docker compose up`, usar una terminal con `.env` oculto. El video debe ser grabado por el grupo; este repositorio no incluye una grabación inventada.

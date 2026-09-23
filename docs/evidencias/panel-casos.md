# Ejecución real del panel de casos

- Fecha local: 23 de septiembre de 2026, 10:31 (America/Lima).
- Ruta: `http://localhost:3000/casos` mediante Docker Compose (Nginx → Spring Boot → MySQL 8.4).
- Perfil: `demo`, entorno normal 10:30 / PERU / CORPORATIVO / 192.168.10.20; las variaciones de T9, T10 y T16 se aplicaron por caso.
- Resultado visible del panel: **17 correctos, 0 fallos** para T1–T17.
- T17 devolvió los IDs **502, 507, 508, 509** antes de la eliminación T6.
- T15 desactivó temporalmente a Juan, observó `401 USUARIO_NO_ACTIVO` con el token anterior y lo restauró.

La batería modifica datos demo (T3, T14 y T6). Esta nota registra una ejecución real del navegador; no sustituye las capturas personales ni el video de entrega. La colección Postman y su ejecución independiente constan en [newman-resumen.md](newman-resumen.md).

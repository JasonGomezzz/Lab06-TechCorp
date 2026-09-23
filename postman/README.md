# Colección Postman de SecureDocs

Importa `SecureDocs.postman_collection.json` y `SecureDocs.postman_environment.json`. Completa `seedPassword` **solo en tu entorno local** con el valor configurado en `SEED_PASSWORD`; el archivo versionado mantiene ese campo vacío. Cambia `baseUrl` si usas otro puerto.

La colección contiene 22 carpetas: T1–T17 y B1–B5. Cada caso obtiene el token del usuario correspondiente antes de ejecutar la operación. T8 incluye los dos usuarios no activos; B3 comprueba cuatro fronteras de horario; T15 y B4 restauran el estado al terminar. T6 se ejecuta al final porque borra lógicamente el documento 509 que T17 necesita ver.

Para repetir la colección completa, reinicia la base demo con `scripts/reset-demo.sh --confirmar`. Este comando elimina únicamente el volumen de la demostración y también reinicia su auditoría. Nunca lo uses sobre una base con datos que quieras conservar.

Ejecución con Newman después de iniciar la aplicación:

```bash
newman run postman/SecureDocs.postman_collection.json \
  -e postman/SecureDocs.postman_environment.json \
  --env-var "seedPassword=VALOR_LOCAL" \
  --env-var "baseUrl=http://localhost:8080"
```

Las cabeceras `X-Sim-*` solo tienen efecto con el perfil `demo`; permiten reproducir hora, fecha, IP, ubicación y dispositivo. En perfiles normales se ignoran. En producción la ubicación y el dispositivo deben provenir de fuentes confiables, no de cabeceras controladas por el cliente.

# Arquitectura de SecureDocs

La autenticación identifica al usuario. Cada operación protegida llega al `Autorizador`, que consulta permisos RBAC y, si el permiso existe, evalúa las reglas ABAC configuradas en MySQL. El resultado se registra en auditoría.

```mermaid
flowchart LR
    UI[Frontend React] --> API[API REST Spring Boot]
    API --> AUTH[Autenticación JWT]
    API --> DOC[Document Service]
    API --> USER[User Service]
    API --> POLICY[Policy Service]
    API --> AUDIT[Audit Service]
    DOC --> AZ[Autorizador]
    USER --> AZ
    POLICY --> AZ
    AUDIT --> AZ
    AZ --> RBAC[RBAC Service]
    AZ --> ABAC[ABAC Policy Engine]
    AUTH --> DB[(MySQL 8)]
    RBAC --> DB
    ABAC --> DB
    DOC --> DB
    USER --> DB
    AUDIT --> DB
```

## Aprobación de un documento

```mermaid
sequenceDiagram
    actor Cliente
    participant API as POST /documentos/{id}/aprobar
    participant JWT as Filtro JWT
    participant DOC as DocumentoService
    participant AZ as Autorizador
    participant RBAC as ServicioRbac
    participant ABAC as MotorAbac
    participant AUD as AuditoriaService
    participant DB as MySQL
    Cliente->>API: Bearer token y entorno
    API->>JWT: Validar firma, revocación y estado actual
    JWT->>DB: Cargar usuario activo
    API->>DOC: Solicitar aprobación
    DOC->>DB: Cargar documento
    DOC->>AZ: exigir(APPROVE, recurso, entorno)
    AZ->>RBAC: ¿Tiene APROBAR_DOCUMENTO?
    alt RBAC deniega
        AZ->>AUD: Registrar denegación RBAC
        AZ-->>Cliente: 403
    else RBAC permite
        AZ->>ABAC: Evaluar todas las políticas aplicables
        ABAC-->>AZ: Resultados y motivos
        AZ->>AUD: Registrar decisión y políticas
        alt ABAC deniega
            AZ-->>Cliente: 403
        else ABAC permite
            DOC->>DB: Cambiar PENDIENTE a PUBLICADO
            DOC-->>Cliente: 200 y documento
        end
    end
```

## Flujo de decisión

```mermaid
flowchart TD
    A[Solicitud] --> B{JWT válido y usuario activo}
    B -- No --> X[401 y auditoría AUTH]
    B -- Sí --> C{RBAC: permiso para la acción}
    C -- No --> Y[403 y auditoría RBAC]
    C -- Sí --> D[ABAC: evaluar políticas aplicables]
    D --> E{¿Alguna política falla?}
    E -- Sí --> Z[403 y auditoría ABAC con todos los motivos]
    E -- No --> F[Permitir operación y auditar]
```

Las cabeceras `X-Sim-*` solo se leen en el perfil `demo`. En perfiles normales, el entorno procede de la petición, del reloj configurado y de la IP observada. Consulta [auditoría](auditoria.md) para el formato de los eventos y [las matrices](matriz-rbac.md) para las autorizaciones iniciales.

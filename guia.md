Quiero diseñar y desarrollar un proyecto final para un curso de **Java Library Development**. El proyecto consiste en crear un ecosistema de librerías Java orientado a estandarizar y automatizar la integración de APIs REST basadas en contratos **OpenAPI**.

## 1. Objetivo general

Crear un conjunto modular de tres librerías:

1. `andes-api-common`
2. `andes-api-server`
3. `andes-api-client`

El objetivo es reutilizar funcionalidades comunes y separar claramente las responsabilidades de:

* APIs propias/expuestas (**Server**).
* APIs externas consumidas (**Client**).
* Funcionalidades compartidas entre ambas (**Common**).

La solución debe estar diseñada para aplicaciones Java modernas, especialmente **Spring Boot**, y debe cumplir una rúbrica académica de desarrollo, publicación, documentación y consumo de librerías.

---

# 2. Arquitectura general

La arquitectura propuesta es:

```text
                        ┌─────────────────────────┐
                        │   andes-api-common      │
                        │                         │
                        │ OpenAPI models          │
                        │ Error models            │
                        │ Response models         │
                        │ Exceptions              │
                        │ Headers                 │
                        │ Constants               │
                        │ Utilities               │
                        └────────────┬────────────┘
                                     │
                    ┌────────────────┴────────────────┐
                    │                                 │
                    ▼                                 ▼
        ┌──────────────────────┐          ┌──────────────────────┐
        │  andes-api-server    │          │  andes-api-client    │
        │                      │          │                      │
        │ APIs EXPUESTAS       │          │ APIs CONSUMIDAS      │
        │                      │          │                      │
        │ Request handling     │          │ HTTP clients         │
        │ Response handling    │          │ Serialization        │
        │ Error handling       │          │ Error mapping        │
        │ Validation           │          │ Configuration        │
        │ OpenAPI integration  │          │ OpenAPI integration  │
        └──────────┬───────────┘          └──────────┬───────────┘
                   │                                 │
                   ▼                                 ▼
          Microservicio propio              Microservicios externos
```

Debe existir además un **BOM** para centralizar las versiones de todas las librerías.

---

# 3. Módulo Common

Crear:

```text
andes-api-common
```

Este módulo debe ser independiente de Spring siempre que sea posible.

Su responsabilidad es contener elementos compartidos por Server y Client.

Debe incluir, como mínimo:

### Modelos

* `ApiResponse<T>`
* `ApiError`
* `ApiErrorDetail`
* `ApiMetadata`
* `ApiRequestMetadata`
* `Pagination`
* `PageResponse<T>`

### Errores

Crear una jerarquía común de excepciones:

```text
AndesApiException
├── AndesValidationException
├── AndesAuthenticationException
├── AndesAuthorizationException
├── AndesNotFoundException
├── AndesConflictException
├── AndesBadRequestException
└── AndesRemoteServiceException
```

### HTTP

Centralizar:

* HTTP status codes.
* Headers.
* Content Types.
* Correlation ID.
* Request ID.
* API version.
* constantes comunes.

### Utilidades

Crear librerías Java puras utilizando clases `final`, constructor privado y métodos estáticos cuando corresponda.

Ejemplo:

```java
public final class HeaderUtils {

    private HeaderUtils() {
    }

    public static boolean isValidCorrelationId(String value) {
        // ...
    }
}
```

También considerar:

```text
ApiResponseUtils
HeaderUtils
OpenApiUtils
ErrorUtils
ValidationUtils
JsonUtils
```

No agregar dependencias de Spring al módulo Common salvo que sea estrictamente necesario.

---

# 4. Módulo Server

Crear:

```text
andes-api-server
```

Su objetivo es facilitar la implementación de APIs REST que el proyecto expone.

Debe integrarse con Spring Boot.

Responsabilidades:

### Request

Automatizar o estandarizar:

* headers
* correlation ID
* request ID
* validación
* deserialización
* manejo de DTOs
* validaciones Bean Validation

### Response

Permitir respuestas estandarizadas:

```json
{
  "success": true,
  "data": {},
  "error": null,
  "traceId": "..."
}
```

El formato debe ser configurable y no debe imponer una estructura incompatible con el contrato OpenAPI existente.

### Error handling

Crear un mecanismo centralizado mediante `@RestControllerAdvice`.

Mapear excepciones a respuestas HTTP:

```text
400 → BadRequest
401 → Authentication
403 → Authorization
404 → NotFound
409 → Conflict
422 → Validation
500 → InternalServerError
```

El sistema debe permitir registrar excepciones personalizadas.

### OpenAPI

Integrar la documentación OpenAPI del servicio.

Permitir configurar:

* title
* description
* version
* servers
* contact
* license
* tags
* security schemes
* common responses
* headers

Siempre respetar el contrato OpenAPI real del servicio.

---

# 5. Módulo Client

Crear:

```text
andes-api-client
```

Su objetivo es simplificar y estandarizar el consumo de APIs REST externas.

Debe permitir configurar clientes basados en contratos OpenAPI.

Debe soportar inicialmente un mecanismo moderno de HTTP client de Spring Boot, preferiblemente:

* Spring `RestClient`, o
* `WebClient`

Elegir uno como implementación principal y justificar la decisión.

---

## Configuración

Permitir algo como:

```yaml
andes:
  api:
    clients:

      customer:
        base-url: ${CUSTOMER_API_URL}
        connect-timeout: 2s
        read-timeout: 5s

      payment:
        base-url: ${PAYMENT_API_URL}
        connect-timeout: 2s
        read-timeout: 5s
```

La configuración debe crear automáticamente los componentes necesarios.

---

## Requests

Automatizar:

* serialización JSON
* headers
* authentication
* correlation ID
* request ID
* content type
* timeouts
* construcción de URLs

---

## Responses

Automatizar:

* deserialización JSON
* validación de respuestas
* manejo de HTTP status
* conversión a DTOs

---

## Errors

Mapear respuestas HTTP a las excepciones comunes de:

```text
andes-api-common
```

Por ejemplo:

```text
404 → AndesNotFoundException
401 → AndesAuthenticationException
403 → AndesAuthorizationException
409 → AndesConflictException
5xx → AndesRemoteServiceException
```

Debe conservar información útil del error remoto:

```text
status
code
message
details
traceId
endpoint
```

---

# 6. AutoConfiguration

Crear Spring Boot Starters para facilitar la integración.

Idealmente:

```text
andes-api-server-spring-boot-starter
andes-api-client-spring-boot-starter
```

Los starters deben utilizar:

```text
META-INF/spring/
└── org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

Ejemplo:

```text
pe.andes.api.server.autoconfigure.AndesApiServerAutoConfiguration
```

y:

```text
pe.andes.api.client.autoconfigure.AndesApiClientAutoConfiguration
```

Utilizar correctamente:

```java
@AutoConfiguration
@ConditionalOnMissingBean
@ConditionalOnProperty
@ConfigurationProperties
@Bean
```

Evitar registrar beans innecesariamente si el usuario ya tiene una implementación propia.

---

# 7. BOM

Crear:

```text
andes-api-bom
```

El BOM debe controlar las versiones de:

```text
andes-api-common
andes-api-server
andes-api-client
andes-api-server-spring-boot-starter
andes-api-client-spring-boot-starter
```

Debe permitir al consumidor importar:

```xml
<dependencyManagement>
    <dependencies>

        <dependency>
            <groupId>pe.andes</groupId>
            <artifactId>andes-api-bom</artifactId>
            <version>${andes.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

    </dependencies>
</dependencyManagement>
```

Y posteriormente utilizar las librerías sin especificar versiones individualmente.

---

# 8. OpenAPI como contrato

El proyecto debe utilizar OpenAPI como contrato principal.

El caso real de demostración tendrá tres contratos:

```text
openapi-server.yaml
openapi-client-a.yaml
openapi-client-b.yaml
```

El primero representa la API propia que se expone.

Los otros dos representan APIs externas consumidas.

No asumir que los tres contratos tienen necesariamente la misma estructura.

La librería debe demostrar cómo manejar contratos diferentes manteniendo una infraestructura común.

---

# 9. PoCs

Crear al menos tres PoCs.

## PoC 1 — Server

Una aplicación Spring Boot que utilice:

```text
andes-api-common
andes-api-server
andes-api-server-spring-boot-starter
```

Debe exponer endpoints definidos por `openapi-server.yaml`.

Demostrar:

* request
* validation
* response
* errores
* correlation ID
* OpenAPI
* configuración automática

---

## PoC 2 — Client

Una aplicación Spring Boot que utilice:

```text
andes-api-common
andes-api-client
andes-api-client-spring-boot-starter
```

Debe consumir una API definida por:

```text
openapi-client-a.yaml
```

Demostrar:

* request
* headers
* serialization
* response
* deserialization
* error mapping
* timeout
* configuration

---

## PoC 3 — Integración

Crear una aplicación que utilice simultáneamente:

```text
andes-api-server
andes-api-client
andes-api-common
```

Esta aplicación debe:

```text
                 ┌──────────────────────┐
                 │   Integration PoC    │
                 └──────────┬───────────┘
                            │
                    ┌───────┴────────┐
                    ▼                ▼
                 Server            Client
                    │                │
                    │                ├── API A
                    │                └── API B
                    │
                    ▼
              Own REST API
```

La PoC debe demostrar el flujo completo:

```text
Request
  ↓
Own API
  ↓
Business logic
  ↓
Client Library
  ↓
Remote API
  ↓
Response
  ↓
Standardized response
```

---

# 10. Estructura del repositorio

Proponer una estructura Maven multi-module similar a:

```text
andes-api-toolkit/
│
├── pom.xml
│
├── andes-api-bom/
│
├── andes-api-common/
│
├── andes-api-server/
│
├── andes-api-client/
│
├── andes-api-server-spring-boot-starter/
│
├── andes-api-client-spring-boot-starter/
│
├── examples/
│   ├── poc-server/
│   ├── poc-client/
│   └── poc-integration/
│
├── contracts/
│   ├── openapi-server.yaml
│   ├── openapi-client-a.yaml
│   └── openapi-client-b.yaml
│
├── docs/
│
├── scripts/
│   ├── publish-local.sh
│   ├── publish-local.ps1
│   ├── publish-nexus.sh
│   ├── publish-nexus.ps1
│   ├── publish-jitpack.sh
│   └── publish-jitpack.ps1
│
└── README.md
```

---

# 11. Maven

Utilizar Maven como build tool principal.

Configurar:

* parent POM
* dependency management
* BOM
* Maven Compiler Plugin
* Surefire
* JaCoCo
* Maven Enforcer
* Javadocs
* Spring Boot
* SemVer

Utilizar versiones compatibles y actuales de Java y Spring Boot.

Separar correctamente:

```text
compile
test
optional
provided
```

Evitar dependencias innecesarias.

---

# 12. Testing

Implementar tests unitarios para:

* utilidades
* errores
* request mapping
* response mapping
* HTTP status mapping
* configuración
* properties

Implementar tests de integración para:

* AutoConfiguration
* Server
* Client

Utilizar mocks o WireMock/Testcontainers cuando sea conveniente.

---

# 13. Documentación

Crear:

```text
README.md
ARCHITECTURE.md
GETTING_STARTED.md
SERVER.md
CLIENT.md
ERROR_HANDLING.md
CONFIGURATION.md
OPENAPI.md
VERSIONING.md
PUBLISHING.md
```

Documentar cada módulo.

Agregar JavaDoc a las APIs públicas.

Incluir diagramas de arquitectura.

---

# 14. Versionamiento

Aplicar Semantic Versioning:

```text
MAJOR.MINOR.PATCH
```

Comenzar con:

```text
1.0.0
```

Documentar claramente qué cambios generan:

```text
MAJOR
MINOR
PATCH
```

---

# 15. Publicación

Preparar publicación en:

```text
Maven Local
Nexus
JitPack
```

Crear scripts:

```text
Linux/Mac:
publish-local.sh
publish-nexus.sh
publish-jitpack.sh

Windows:
publish-local.ps1
publish-nexus.ps1
publish-jitpack.ps1
```

Los scripts deben validar errores y detener la ejecución si falla algún paso.

---

# 16. Objetivo académico

El diseño debe demostrar explícitamente los siguientes conceptos de la rúbrica:

1. Inventario de librerías.
2. Arquitectura modular.
3. Maven.
4. BOM.
5. Semantic Versioning.
6. Librerías Java puras.
7. Librerías wrapper.
8. Spring Boot AutoConfiguration.
9. `AutoConfiguration.imports`.
10. PoCs.
11. JavaDoc.
12. Spring REST Docs cuando corresponda.
13. Publicación local.
14. Nexus.
15. JitPack.
16. Scripts de publicación.
17. Git/GitHub.
18. Documentación técnica.

---

# 17. Restricción importante de alcance

No convertir el proyecto en un API Gateway, Service Mesh o framework completo.

El objetivo principal es:

> **automatizar y estandarizar la integración de APIs REST basadas en contratos OpenAPI mediante librerías Java reutilizables.**

La primera versión debe concentrarse en:

```text
REQUEST
RESPONSE
ERROR
CONFIGURATION
OPENAPI
```

para los dos escenarios:

```text
SERVER → API EXPUESTA
CLIENT → API CONSUMIDA
```

La funcionalidad adicional como retry, circuit breaker, cache, generación completa de código o discovery debe considerarse únicamente como una posible extensión futura.

---

# 18. Resultado esperado

Al finalizar debe ser posible que un desarrollador agregue las dependencias de Andes API Toolkit y pueda:

### Para Server

```text
Agregar starter
     ↓
Configurar propiedades
     ↓
Implementar Controller
     ↓
Obtener automáticamente:
- Request handling
- Response handling
- Error handling
- Headers
- Correlation ID
- OpenAPI configuration
```

### Para Client

```text
Agregar starter
     ↓
Configurar API remota
     ↓
Crear/usar Client
     ↓
Obtener automáticamente:
- HTTP request
- Headers
- Serialization
- Deserialization
- Error mapping
- Timeouts
- Configuration
```

El diseño debe priorizar **separación de responsabilidades, bajo acoplamiento, alta reutilización, extensibilidad, testabilidad y compatibilidad con Spring Boot**.

Antes de implementar código, presentar primero:

1. Arquitectura propuesta.
2. Diagrama de módulos.
3. Dependencias entre módulos.
4. Responsabilidad de cada módulo.
5. Modelo de clases.
6. Flujo Server.
7. Flujo Client.
8. Estrategia de AutoConfiguration.
9. Estrategia OpenAPI.
10. Plan de implementación por fases.

No generar todo el código de una sola vez. Diseñar primero la arquitectura y luego implementar módulo por módulo.

# Ejecución, compilación y publicación

Esta guía describe el orden recomendado para trabajar con `andes-api-toolkit`, desde la preparación del entorno hasta la publicación de las librerías.

Todos los comandos se ejecutan desde la raíz del proyecto:

```bash
cd /mnt/extra/proyectos/ProyectoFinalLibreriaGT/andes-api-toolkit
```

## 1. Requisitos

El proyecto requiere:

- JDK 21 configurado en `JAVA_HOME`.
- Maven 3.9 o superior.
- Acceso a Maven Central para descargar dependencias.
- Acceso al repositorio remoto cuando se publique en Nexus o Artifactory.

Comprobar las versiones:

```bash
java -version
mvn -version
```

Maven debe mostrar Java 21. Si muestra otra versión, corregir `JAVA_HOME` antes de continuar.

## 2. Orden de los módulos

No es necesario compilar cada módulo manualmente. Maven usa el reactor multi-módulo y calcula el orden correcto:

1. `andes-api-bom`
2. `andes-api-common`
3. `andes-api-server` y `andes-api-client`
4. `andes-api-server-spring-boot-starter` y `andes-api-client-spring-boot-starter`
5. `examples/poc-server`, `examples/poc-client` y `examples/poc-integration`

Los contratos OpenAPI ubicados en `contracts/` se utilizan durante la fase `generate-sources` de los módulos PoC. El código generado queda en `target/generated-sources/openapi` y no debe editarse manualmente.

## 3. Limpiar y generar código OpenAPI

Para generar únicamente el código de los contratos:

```bash
mvn -q clean generate-sources -pl examples/poc-server,examples/poc-client,examples/poc-integration
```

Para revisar los resultados:

```bash
ls examples/poc-server/target/generated-sources/openapi
ls examples/poc-client/target/generated-sources/openapi
ls examples/poc-integration/target/generated-sources/openapi
```

Si cambia un archivo YAML, se debe volver a ejecutar esta fase antes de probar o empaquetar.

## 4. Compilar y probar

### Compilación rápida

Genera las fuentes y compila todos los módulos sin ejecutar las pruebas:

```bash
mvn -q -DskipTests clean package
```

Esta opción sirve para comprobar rápidamente que el código y el codegen compilan. No debe ser el único chequeo antes de publicar.

### Verificación completa

Ejecuta las pruebas, genera los reportes de JaCoCo y crea los JAR:

```bash
mvn clean verify
```

La publicación debe detenerse si este comando falla. Para ejecutar las pruebas de un módulo concreto:

```bash
mvn -pl andes-api-common test
mvn -pl andes-api-client test
```

Para incluir las dependencias internas necesarias al probar un módulo:

```bash
mvn -pl examples/poc-integration -am test
```

El parámetro `-am` significa `also-make`: también construye los módulos requeridos.

## 5. Ejecutar las PoC

Cada aplicación se ejecuta en una terminal separada. El comando se lanza desde la raíz del toolkit.

### PoC Server

Puerto `8080`, contrato `contracts/openapi-server.yaml`:

```bash
mvn -pl examples/poc-server spring-boot:run
```

### PoC Client

Puerto `8082`, contratos `openapi-client-a.yaml` y `openapi-client-b.yaml`:

```bash
mvn -pl examples/poc-client spring-boot:run
```

### PoC Integration

Puerto `8083`, contrato propio de integración y consumo de Orders:

```bash
mvn -pl examples/poc-integration spring-boot:run
```

Ejemplos de comprobación:

```bash
curl http://localhost:8080/api/v1/customers/1
curl http://localhost:8082/api/v1/orders-demo/ORD-1
```

## 6. Instalar las librerías en Maven Local

Esta es la publicación local. Instala el BOM, las librerías, los starters y las PoC en `~/.m2/repository`:

```bash
mvn clean install
```

Para una instalación local con fuentes y Javadocs, usando el perfil `release`:

```bash
mvn clean install -Prelease
```

Usar `install` cuando se quiera probar el consumo desde otro proyecto local. El proyecto consumidor podrá declarar, por ejemplo:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>pe.andes.api</groupId>
      <artifactId>andes-api-bom</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

## 7. Preparar una versión de release

Antes de publicar una versión estable:

1. Confirmar que `mvn clean verify` termina correctamente.
2. Revisar los contratos OpenAPI y la compatibilidad de la API pública.
3. Actualizar la versión del POM raíz, por ejemplo de `1.0.0-SNAPSHOT` a `1.0.0`.
4. Actualizar la misma versión en `andes-api-bom/pom.xml` y en la propiedad `andes.version`.
5. Confirmar que todas las referencias internas usan la nueva versión.
6. Ejecutar nuevamente `mvn clean verify -Prelease`.
7. Crear un commit y un tag Git con el mismo número de versión, preferiblemente `v1.0.0`.

El BOM y todos los artefactos deben publicar la misma versión. Aplicar SemVer:

- `MAJOR`: cambio incompatible.
- `MINOR`: funcionalidad nueva compatible.
- `PATCH`: corrección compatible.

## 8. Publicar en Nexus o Artifactory

La publicación remota se realiza con `deploy`. Las credenciales no deben escribirse en el POM ni en este documento.

### 8.1 Configurar credenciales

Agregar un servidor con el mismo `id` que usa el repositorio remoto en `~/.m2/settings.xml`:

```xml
<settings>
  <servers>
    <server>
      <id>andes-releases</id>
      <username>USUARIO</username>
      <password>TOKEN_O_PASSWORD</password>
    </server>
  </servers>
</settings>
```

El repositorio remoto debe estar configurado mediante `distributionManagement` en el POM raíz, o mediante una configuración equivalente de Maven:

```xml
<distributionManagement>
  <repository>
    <id>andes-releases</id>
    <url>https://servidor.example.com/repository/maven-releases/</url>
  </repository>
  <snapshotRepository>
    <id>andes-snapshots</id>
    <url>https://servidor.example.com/repository/maven-snapshots/</url>
  </snapshotRepository>
</distributionManagement>
```

En el estado actual del proyecto este bloque todavía no está definido en el POM. Por eso se debe agregar con las URLs reales del equipo antes de ejecutar `deploy`, o proporcionar una configuración de publicación equivalente.

### 8.2 Publicar un snapshot

Con una versión terminada en `-SNAPSHOT`:

```bash
mvn clean deploy
```

### 8.3 Publicar una versión estable

Con una versión sin `-SNAPSHOT`:

```bash
mvn clean deploy -Prelease
```

Verificar en Nexus que estén disponibles el BOM, los JAR, los POM, las fuentes y los Javadocs.

## 9. Publicar mediante JitPack

JitPack construye el proyecto desde un repositorio Git. El orden recomendado es:

1. Publicar el código en GitHub o GitLab.
2. Confirmar que el `pom.xml` raíz compila desde un checkout limpio.
3. Crear un tag de versión, por ejemplo:

   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```

4. Abrir `https://jitpack.io` y solicitar la compilación del tag.
5. Esperar el estado `Get it` antes de usar la dependencia.

JitPack no sustituye a Nexus como repositorio corporativo. Es una alternativa útil para demostraciones y consumo desde el repositorio Git.

## 10. Orden recomendado para una entrega

Ejecutar exactamente en este orden:

```bash
cd /mnt/extra/proyectos/ProyectoFinalLibreriaGT/andes-api-toolkit
java -version
mvn -version
mvn clean generate-sources -pl examples/poc-server,examples/poc-client,examples/poc-integration
mvn clean verify
mvn clean install -Prelease
```

Después:

1. Ejecutar las tres PoC y verificar sus endpoints.
2. Revisar los JAR y metadatos dentro de `target/`.
3. Para una publicación remota, confirmar `settings.xml`, `distributionManagement` y las credenciales.
4. Ejecutar `mvn clean deploy -Prelease`.
5. Crear y publicar el tag Git para JitPack.
6. Probar la dependencia desde un proyecto consumidor limpio.

## 11. Problemas frecuentes

### Maven usa otro Java

Revisar `mvn -version` y corregir `JAVA_HOME`. El POM exige Java 21 mediante Maven Enforcer.

### No encuentra una librería interna

Ejecutar el build desde la raíz del reactor. Para un módulo aislado usar `-am`, o instalar primero con `mvn clean install`.

### El código generado no compila

Eliminar `target/` y volver a generar:

```bash
mvn clean generate-sources -pl examples/poc-server,examples/poc-client,examples/poc-integration
mvn -DskipTests compile
```

Revisar el código generado en `target/generated-sources/openapi` y el contrato YAML que lo originó.

### `deploy` no encuentra un repositorio

Revisar que exista `distributionManagement`, que el `id` coincida con `settings.xml` y que la URL corresponda al tipo de versión: releases o snapshots.

### Una versión release ya existe

Los repositorios Maven normalmente no permiten sobrescribir una versión estable. Incrementar la versión y publicar una nueva versión SemVer.
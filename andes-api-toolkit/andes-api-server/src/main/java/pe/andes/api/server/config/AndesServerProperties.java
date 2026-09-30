package pe.andes.api.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Propiedades raíz de configuración para el módulo {@code andes-api-server}.
 *
 * <p>Spring Boot enlaza esta clase con las propiedades declaradas bajo el prefijo
 * {@code andes.api.server} mediante {@link ConfigurationProperties}. Cada clase anidada agrupa
 * un subconjunto funcional de la autoconfiguración: envoltura uniforme de respuestas,
 * manejo global de errores, correlación de peticiones y metadatos de OpenAPI.
 */
@ConfigurationProperties(prefix = "andes.api.server")
public class AndesServerProperties {

    /**
     * Configuración de la envoltura automática de respuestas HTTP exitosas.
     */
    private final Response response = new Response();

    /**
     * Configuración del manejo centralizado de excepciones expuesto como
     * {@code @RestControllerAdvice}.
     */
    private final ErrorHandling errorHandling = new ErrorHandling();

    /**
     * Configuración del filtro servlet que resuelve y propaga identificadores de correlación.
     */
    private final Correlation correlation = new Correlation();

    /**
     * Metadatos adicionales usados para enriquecer el documento OpenAPI publicado por springdoc.
     */
    private final OpenApi openapi = new OpenApi();

    /**
     * Obtiene la configuración de envoltura de respuestas.
     *
     * @return propiedades consumidas por {@code AndesResponseBodyAdvice}
     */
    public Response getResponse() {
        return response;
    }

    /**
     * Obtiene la configuración del manejo global de errores.
     *
     * @return propiedades usadas por {@code GlobalExceptionHandler}
     */
    public ErrorHandling getErrorHandling() {
        return errorHandling;
    }

    /**
     * Obtiene la configuración del filtro de correlación.
     *
     * @return propiedades usadas al registrar {@code CorrelationIdFilter}
     */
    public Correlation getCorrelation() {
        return correlation;
    }

    /**
     * Obtiene la configuración de OpenAPI.
     *
     * @return propiedades usadas para construir el bean {@code OpenAPI}
     */
    public OpenApi getOpenapi() {
        return openapi;
    }

    /**
     * Propiedades que gobiernan la adaptación de respuestas de controladores REST.
     *
     * <p>Estas opciones son consumidas por la autoconfiguración para decidir si se registra
     * {@code ResponseBodyAdvice} y, por tanto, si los valores devueltos por los controladores se
     * encapsulan en el contrato estándar {@code ApiResponse}.
     */
    public static class Response {
        /**
         * Indica si los valores devueltos por los controladores deben envolverse en
         * {@code ApiResponse}.
         */
        private boolean wrapEnabled = true;

        /**
         * Indica si la envoltura automática de respuestas está habilitada.
         *
         * @return {@code true} si debe registrarse el envoltorio estándar
         */
        public boolean isWrapEnabled() {
            return wrapEnabled;
        }

        /**
         * Activa o desactiva la envoltura automática de respuestas.
         *
         * @param wrapEnabled {@code true} para encapsular respuestas exitosas en {@code ApiResponse}
         */
        public void setWrapEnabled(boolean wrapEnabled) {
            this.wrapEnabled = wrapEnabled;
        }
    }

    /**
     * Propiedades para el manejo transversal de excepciones HTTP.
     *
     * <p>La autoconfiguración consulta este bloque para decidir si registra el
     * {@code GlobalExceptionHandler} y si las respuestas de error genéricas deben incluir
     * información sensible como el {@code stack trace}.
     */
    public static class ErrorHandling {
        /**
         * Indica si debe registrarse el {@code @RestControllerAdvice} incorporado.
         */
        private boolean enabled = true;
        /**
         * Indica si las respuestas de error inesperado pueden incluir el detalle técnico de la
         * excepción. Normalmente debe permanecer deshabilitado en producción.
         */
        private boolean includeStackTrace = false;

        /**
         * Indica si el manejo global de errores está habilitado.
         *
         * @return {@code true} si la autoconfiguración debe crear el manejador global
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * Activa o desactiva el registro del manejador global de excepciones.
         *
         * @param enabled {@code true} para habilitar el consejo global de errores
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * Indica si se incluirá el detalle técnico de la excepción en errores 500.
         *
         * @return {@code true} si las respuestas internas deben incluir {@code ex.toString()}
         */
        public boolean isIncludeStackTrace() {
            return includeStackTrace;
        }

        /**
         * Configura si las respuestas de error inesperado incluirán información de depuración.
         *
         * @param includeStackTrace {@code true} para exponer el detalle técnico de la excepción
         */
        public void setIncludeStackTrace(boolean includeStackTrace) {
            this.includeStackTrace = includeStackTrace;
        }
    }

    /**
     * Propiedades para el filtro que administra {@code correlation id} y {@code request id}.
     *
     * <p>Estas opciones determinan si el filtro servlet se registra y si está autorizado a generar
     * identificadores cuando el cliente no los envía o los envía con un formato inválido.
     */
    public static class Correlation {
        /**
         * Indica si debe registrarse el filtro de correlación y trazabilidad.
         */
        private boolean enabled = true;
        /**
         * Indica si el servidor puede generar identificadores cuando el consumidor no los envía.
         */
        private boolean generateIfMissing = true;

        /**
         * Indica si el filtro de correlación está habilitado.
         *
         * @return {@code true} si debe registrarse {@code CorrelationIdFilter}
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * Activa o desactiva el filtro de correlación.
         *
         * @param enabled {@code true} para registrar el filtro servlet
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * Indica si el servidor puede generar identificadores faltantes o inválidos.
         *
         * @return {@code true} si se permite generar nuevos identificadores
         */
        public boolean isGenerateIfMissing() {
            return generateIfMissing;
        }

        /**
         * Define si el filtro debe generar identificadores cuando el cliente no los provee.
         *
         * @param generateIfMissing {@code true} para generar ids faltantes o inválidos
         */
        public void setGenerateIfMissing(boolean generateIfMissing) {
            this.generateIfMissing = generateIfMissing;
        }
    }

    /**
     * Propiedades que alimentan la construcción del modelo {@code OpenAPI}.
     *
     * <p>Los valores de este bloque no describen endpoints ni esquemas del negocio; únicamente
     * complementan el documento generado por springdoc con información general como título,
     * contacto, servidores, etiquetas y esquemas de seguridad.
     */
    public static class OpenApi {
        /**
         * Indica si debe publicarse el bean {@code OpenAPI} adicional.
         */
        private boolean enabled = true;
        /**
         * Título principal del documento OpenAPI.
         */
        private String title = "Andes API";
        /**
         * Descripción funcional de la API.
         */
        private String description = "";
        /**
         * Versión publicada de la API.
         */
        private String version = "1.0.0";
        /**
         * Información de contacto mostrada en la especificación.
         */
        private Contact contact = new Contact();
        /**
         * Información de licencia mostrada en la especificación.
         */
        private License license = new License();
        /**
         * Lista de servidores sugeridos para consumidores de la API.
         */
        private List<ServerInfo> servers = new ArrayList<>();
        /**
         * Etiquetas adicionales para agrupar operaciones en la documentación.
         */
        private List<TagInfo> tags = new ArrayList<>();
        /**
         * Esquemas de seguridad que se agregarán a {@code components.securitySchemes}.
         */
        private Map<String, SecuritySchemeInfo> securitySchemes = new LinkedHashMap<>();

        /**
         * Indica si la publicación del bean {@code OpenAPI} está habilitada.
         *
         * @return {@code true} si la autoconfiguración debe construir el documento enriquecido
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * Activa o desactiva la exposición de metadatos adicionales de OpenAPI.
         *
         * @param enabled {@code true} para construir el bean {@code OpenAPI}
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * Obtiene el título del documento OpenAPI.
         *
         * @return título visible para consumidores y herramientas
         */
        public String getTitle() {
            return title;
        }

        /**
         * Define el título del documento OpenAPI.
         *
         * @param title título de la API
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Obtiene la descripción general de la API.
         *
         * @return texto descriptivo del documento
         */
        public String getDescription() {
            return description;
        }

        /**
         * Define la descripción general de la API.
         *
         * @param description descripción funcional o técnica del servicio
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Obtiene la versión publicada de la API.
         *
         * @return versión declarada en el documento OpenAPI
         */
        public String getVersion() {
            return version;
        }

        /**
         * Define la versión publicada de la API.
         *
         * @param version versión de la especificación o del servicio
         */
        public void setVersion(String version) {
            this.version = version;
        }

        /**
         * Obtiene la información de contacto expuesta en la documentación.
         *
         * @return datos de contacto de la API
         */
        public Contact getContact() {
            return contact;
        }

        /**
         * Reemplaza la información de contacto expuesta en la documentación.
         *
         * @param contact datos de contacto a publicar
         */
        public void setContact(Contact contact) {
            this.contact = contact;
        }

        /**
         * Obtiene la información de licencia expuesta en la documentación.
         *
         * @return licencia declarada para la API
         */
        public License getLicense() {
            return license;
        }

        /**
         * Reemplaza la información de licencia expuesta en la documentación.
         *
         * @param license licencia a publicar
         */
        public void setLicense(License license) {
            this.license = license;
        }

        /**
         * Obtiene los servidores sugeridos para la documentación OpenAPI.
         *
         * @return lista de servidores que se serializará en el documento
         */
        public List<ServerInfo> getServers() {
            return servers;
        }

        /**
         * Define la lista de servidores sugeridos para la documentación.
         *
         * @param servers servidores que se agregarán a la especificación
         */
        public void setServers(List<ServerInfo> servers) {
            this.servers = servers;
        }

        /**
         * Obtiene las etiquetas declaradas para la documentación.
         *
         * @return lista de etiquetas OpenAPI
         */
        public List<TagInfo> getTags() {
            return tags;
        }

        /**
         * Define las etiquetas que se agregarán a la documentación.
         *
         * @param tags etiquetas descriptivas del documento OpenAPI
         */
        public void setTags(List<TagInfo> tags) {
            this.tags = tags;
        }

        /**
         * Obtiene los esquemas de seguridad configurados.
         *
         * @return mapa cuyo nombre de clave corresponde al identificador del esquema
         */
        public Map<String, SecuritySchemeInfo> getSecuritySchemes() {
            return securitySchemes;
        }

        /**
         * Reemplaza los esquemas de seguridad configurados.
         *
         * @param securitySchemes mapa de esquemas de seguridad a publicar
         */
        public void setSecuritySchemes(Map<String, SecuritySchemeInfo> securitySchemes) {
            this.securitySchemes = securitySchemes;
        }
    }

    /**
     * Información de contacto usada por el bloque {@code info.contact} del documento OpenAPI.
     */
    public static class Contact {
        /**
         * Nombre del equipo, área o persona responsable.
         */
        private String name;
        /**
         * Correo electrónico de contacto.
         */
        private String email;
        /**
         * URL pública con información complementaria de contacto.
         */
        private String url;

        /**
         * Obtiene el nombre del contacto.
         *
         * @return nombre publicado en la especificación
         */
        public String getName() {
            return name;
        }

        /**
         * Define el nombre del contacto.
         *
         * @param name nombre del responsable o equipo
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Obtiene el correo del contacto.
         *
         * @return correo publicado en la especificación
         */
        public String getEmail() {
            return email;
        }

        /**
         * Define el correo del contacto.
         *
         * @param email dirección de correo del responsable
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * Obtiene la URL del contacto.
         *
         * @return enlace publicado en la especificación
         */
        public String getUrl() {
            return url;
        }

        /**
         * Define la URL del contacto.
         *
         * @param url enlace con información adicional o mesa de ayuda
         */
        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * Información de licencia usada por el bloque {@code info.license} del documento OpenAPI.
     */
    public static class License {
        /**
         * Nombre visible de la licencia.
         */
        private String name;
        /**
         * URL con el texto o referencia de la licencia.
         */
        private String url;

        /**
         * Obtiene el nombre de la licencia.
         *
         * @return nombre publicado en la especificación
         */
        public String getName() {
            return name;
        }

        /**
         * Define el nombre de la licencia.
         *
         * @param name nombre visible de la licencia
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Obtiene la URL de la licencia.
         *
         * @return enlace publicado en la especificación
         */
        public String getUrl() {
            return url;
        }

        /**
         * Define la URL de la licencia.
         *
         * @param url referencia web de la licencia
         */
        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * Descriptor de servidor para la sección {@code servers} de OpenAPI.
     */
    public static class ServerInfo {
        /**
         * URL base del servidor.
         */
        private String url;
        /**
         * Descripción visible del entorno o propósito del servidor.
         */
        private String description;

        /**
         * Obtiene la URL base del servidor.
         *
         * @return URL del entorno documentado
         */
        public String getUrl() {
            return url;
        }

        /**
         * Define la URL base del servidor.
         *
         * @param url URL del servidor a publicar
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * Obtiene la descripción del servidor.
         *
         * @return texto explicativo del entorno
         */
        public String getDescription() {
            return description;
        }

        /**
         * Define la descripción del servidor.
         *
         * @param description descripción del entorno, región o propósito
         */
        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * Descriptor de etiqueta para organizar operaciones en la documentación OpenAPI.
     */
    public static class TagInfo {
        /**
         * Nombre de la etiqueta.
         */
        private String name;
        /**
         * Descripción de la agrupación asociada.
         */
        private String description;

        /**
         * Obtiene el nombre de la etiqueta.
         *
         * @return nombre mostrado en la documentación
         */
        public String getName() {
            return name;
        }

        /**
         * Define el nombre de la etiqueta.
         *
         * @param name nombre de la agrupación funcional
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Obtiene la descripción de la etiqueta.
         *
         * @return texto explicativo asociado a la agrupación
         */
        public String getDescription() {
            return description;
        }

        /**
         * Define la descripción de la etiqueta.
         *
         * @param description texto explicativo de la agrupación
         */
        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * Descriptor de esquema de seguridad para {@code components.securitySchemes}.
     *
     * <p>El nombre del esquema se toma de la clave del mapa {@code securitySchemes}, mientras que
     * esta clase contiene sus atributos configurables.
     */
    public static class SecuritySchemeInfo {
        /**
         * Tipo del esquema, por ejemplo {@code http}, {@code apiKey}, {@code oauth2} u
         * {@code openIdConnect}.
         */
        private String type;
        /**
         * Esquema HTTP, por ejemplo {@code bearer} o {@code basic}, cuando el tipo es
         * {@code http}.
         */
        private String scheme;
        /**
         * Formato del token, por ejemplo {@code JWT}, cuando el esquema es {@code bearer}.
         */
        private String bearerFormat;
        /**
         * Ubicación del {@code apiKey}, por ejemplo {@code header}, {@code query} o
         * {@code cookie}.
         */
        private String in;
        /**
         * Nombre del encabezado, parámetro de consulta o cookie usada por el esquema.
         */
        private String name;

        /**
         * Obtiene el tipo del esquema de seguridad.
         *
         * @return valor compatible con {@code SecurityScheme.Type}
         */
        public String getType() {
            return type;
        }

        /**
         * Define el tipo del esquema de seguridad.
         *
         * @param type tipo del esquema, por ejemplo {@code http} o {@code apiKey}
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * Obtiene el esquema HTTP configurado.
         *
         * @return nombre del esquema HTTP, si aplica
         */
        public String getScheme() {
            return scheme;
        }

        /**
         * Define el esquema HTTP configurado.
         *
         * @param scheme esquema HTTP, por ejemplo {@code bearer}
         */
        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        /**
         * Obtiene el formato del token portador.
         *
         * @return formato del token, si aplica
         */
        public String getBearerFormat() {
            return bearerFormat;
        }

        /**
         * Define el formato del token portador.
         *
         * @param bearerFormat formato adicional del token, por ejemplo {@code JWT}
         */
        public void setBearerFormat(String bearerFormat) {
            this.bearerFormat = bearerFormat;
        }

        /**
         * Obtiene la ubicación del {@code apiKey}.
         *
         * @return ubicación del valor de autenticación, si aplica
         */
        public String getIn() {
            return in;
        }

        /**
         * Define la ubicación del {@code apiKey}.
         *
         * @param in ubicación del valor de autenticación, por ejemplo {@code header}
         */
        public void setIn(String in) {
            this.in = in;
        }

        /**
         * Obtiene el nombre del parámetro o encabezado del esquema.
         *
         * @return nombre expuesto en la especificación
         */
        public String getName() {
            return name;
        }

        /**
         * Define el nombre del parámetro o encabezado del esquema.
         *
         * @param name nombre del encabezado, query param o cookie
         */
        public void setName(String name) {
            this.name = name;
        }
    }
}

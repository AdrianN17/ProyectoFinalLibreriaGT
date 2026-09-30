package pe.andes.api.common.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Contiene metadatos transversales adjuntos a cada {@link ApiResponse},
 * especialmente para trazabilidad, correlación y diagnóstico operativo.
 */
public final class ApiMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de traza asociado a la operación que originó la respuesta.
     */
    private final String traceId;
    /**
     * Identificador de correlación compartido a lo largo de la cadena de servicios.
     */
    private final String correlationId;
    /**
     * Identificador único asignado a la solicitud concreta dentro del flujo distribuido.
     */
    private final String requestId;
    /**
     * Versión del contrato de API con la que se emitió la respuesta.
     */
    private final String apiVersion;
    /**
     * Marca temporal en la que se generaron estos metadatos.
     */
    private final Instant timestamp;

    /**
     * Construye los metadatos estándar de una respuesta API.
     *
     * @param traceId identificador de traza de la operación
     * @param correlationId identificador de correlación extremo a extremo
     * @param requestId identificador único de la solicitud
     * @param apiVersion versión de API asociada a la respuesta
     * @param timestamp instante de generación; si es {@code null}, se usa el actual
     */
    public ApiMetadata(String traceId, String correlationId, String requestId,
                        String apiVersion, Instant timestamp) {
        this.traceId = traceId;
        this.correlationId = correlationId;
        this.requestId = requestId;
        this.apiVersion = apiVersion;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
    }

    /**
     * Crea un constructor fluido para instancias de {@link ApiMetadata}.
     *
     * @return nuevo builder vacío
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Devuelve el identificador de traza de la operación.
     *
     * @return identificador de traza o {@code null}
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Devuelve el identificador de correlación compartido entre servicios.
     *
     * @return identificador de correlación o {@code null}
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Devuelve el identificador único de la solicitud.
     *
     * @return identificador de solicitud o {@code null}
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Devuelve la versión de API asociada a la respuesta.
     *
     * @return versión de API o {@code null}
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Devuelve el instante en que se generaron los metadatos.
     *
     * @return marca temporal de la respuesta
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Compara estos metadatos con otro objeto según el contrato observable expuesto a clientes.
     *
     * @param o objeto contra el que se realizará la comparación
     * @return {@code true} si ambos metadatos representan la misma información de trazabilidad
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiMetadata that)) {
            return false;
        }
        return Objects.equals(traceId, that.traceId)
                && Objects.equals(correlationId, that.correlationId)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(apiVersion, that.apiVersion);
    }

    /**
     * Calcula el código hash coherente con la semántica de igualdad de estos metadatos.
     *
     * @return código hash de la instancia
     */
    @Override
    public int hashCode() {
        return Objects.hash(traceId, correlationId, requestId, apiVersion);
    }

    /**
     * Genera una representación textual útil para logs y herramientas de diagnóstico.
     *
     * @return representación textual de los metadatos
     */
    @Override
    public String toString() {
        return "ApiMetadata{" +
                "traceId='" + traceId + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    /**
     * Builder fluido para ensamblar instancias de {@link ApiMetadata}.
     */
    public static final class Builder {
        private String traceId;
        private String correlationId;
        private String requestId;
        private String apiVersion;
        private Instant timestamp;

        private Builder() {
        }

        /**
         * Define el identificador de traza de la operación.
         *
         * @param traceId identificador de traza
         * @return builder actual para encadenamiento
         */
        public Builder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        /**
         * Define el identificador de correlación compartido entre servicios.
         *
         * @param correlationId identificador de correlación
         * @return builder actual para encadenamiento
         */
        public Builder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        /**
         * Define el identificador único de la solicitud.
         *
         * @param requestId identificador de solicitud
         * @return builder actual para encadenamiento
         */
        public Builder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        /**
         * Define la versión de API asociada a la respuesta.
         *
         * @param apiVersion versión de API
         * @return builder actual para encadenamiento
         */
        public Builder apiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        /**
         * Define el instante de generación de los metadatos.
         *
         * @param timestamp marca temporal de la respuesta
         * @return builder actual para encadenamiento
         */
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Construye la instancia final de {@link ApiMetadata}.
         *
         * @return metadatos construidos con los valores acumulados
         */
        public ApiMetadata build() {
            return new ApiMetadata(traceId, correlationId, requestId, apiVersion, timestamp);
        }
    }
}

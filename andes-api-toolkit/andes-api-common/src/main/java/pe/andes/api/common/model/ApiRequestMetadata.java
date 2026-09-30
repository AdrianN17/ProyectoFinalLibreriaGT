package pe.andes.api.common.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Contiene los metadatos extraídos de una solicitud entrante o saliente para
 * propagar de forma consistente información de trazabilidad y versionado.
 */
public final class ApiRequestMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador de correlación que acompaña la solicitud a través de múltiples servicios.
     */
    private final String correlationId;
    /**
     * Identificador único de la petición individual procesada por el toolkit.
     */
    private final String requestId;
    /**
     * Versión de API declarada o inferida para la solicitud.
     */
    private final String apiVersion;
    /**
     * Instante en que la solicitud fue recibida o en que se construyó el metadato.
     */
    private final Instant receivedAt;

    /**
     * Construye los metadatos asociados a una solicitud procesada por el toolkit.
     *
     * @param correlationId identificador de correlación extremo a extremo
     * @param requestId identificador único de la solicitud
     * @param apiVersion versión de API indicada en la solicitud
     * @param receivedAt instante de recepción; si es {@code null}, se usa el actual
     */
    public ApiRequestMetadata(String correlationId, String requestId, String apiVersion, Instant receivedAt) {
        this.correlationId = correlationId;
        this.requestId = requestId;
        this.apiVersion = apiVersion;
        this.receivedAt = receivedAt != null ? receivedAt : Instant.now();
    }

    /**
     * Crea un constructor fluido para instancias de {@link ApiRequestMetadata}.
     *
     * @return nuevo builder vacío
     */
    public static Builder builder() {
        return new Builder();
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
     * Devuelve la versión de API indicada en la solicitud.
     *
     * @return versión de API o {@code null}
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Devuelve el instante de recepción o construcción del metadato.
     *
     * @return marca temporal de recepción
     */
    public Instant getReceivedAt() {
        return receivedAt;
    }

    /**
     * Compara estos metadatos de solicitud con otro objeto equivalente del contrato.
     *
     * @param o objeto contra el que se realizará la comparación
     * @return {@code true} si ambos objetos describen la misma solicitud
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiRequestMetadata that)) {
            return false;
        }
        return Objects.equals(correlationId, that.correlationId)
                && Objects.equals(requestId, that.requestId)
                && Objects.equals(apiVersion, that.apiVersion);
    }

    /**
     * Calcula el código hash consistente con la igualdad de estos metadatos.
     *
     * @return código hash de la instancia
     */
    @Override
    public int hashCode() {
        return Objects.hash(correlationId, requestId, apiVersion);
    }

    /**
     * Genera una representación textual útil para seguimiento y depuración de solicitudes.
     *
     * @return representación textual de los metadatos de solicitud
     */
    @Override
    public String toString() {
        return "ApiRequestMetadata{" +
                "correlationId='" + correlationId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", receivedAt=" + receivedAt +
                '}';
    }

    /**
     * Builder fluido para ensamblar instancias de {@link ApiRequestMetadata}.
     */
    public static final class Builder {
        private String correlationId;
        private String requestId;
        private String apiVersion;
        private Instant receivedAt;

        private Builder() {
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
         * Define la versión de API indicada en la solicitud.
         *
         * @param apiVersion versión de API
         * @return builder actual para encadenamiento
         */
        public Builder apiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        /**
         * Define el instante de recepción de la solicitud.
         *
         * @param receivedAt marca temporal de recepción
         * @return builder actual para encadenamiento
         */
        public Builder receivedAt(Instant receivedAt) {
            this.receivedAt = receivedAt;
            return this;
        }

        /**
         * Construye la instancia final de {@link ApiRequestMetadata}.
         *
         * @return metadatos de solicitud construidos
         */
        public ApiRequestMetadata build() {
            return new ApiRequestMetadata(correlationId, requestId, apiVersion, receivedAt);
        }
    }
}

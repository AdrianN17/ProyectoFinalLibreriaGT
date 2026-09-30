package pe.andes.api.common.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa la carga de error estándar incluida en {@link ApiResponse#getError()}
 * para comunicar fallos funcionales o técnicos de forma homogénea.
 */
public final class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código estable que permite a clientes y servidores clasificar programáticamente el error.
     */
    private final String code;
    /**
     * Mensaje principal que resume la causa del fallo para el consumidor de la API.
     */
    private final String message;
    /**
     * Estado HTTP asociado a la respuesta que encapsula este error.
     */
    private final int httpStatus;
    /**
     * Identificador de trazabilidad distribuida para correlacionar logs y eventos.
     */
    private final String traceId;
    /**
     * Instante en que se materializó el error en el contrato estándar del toolkit.
     */
    private final Instant timestamp;
    /**
     * Lista detallada de incidencias puntuales, especialmente útil en validaciones.
     */
    private final List<ApiErrorDetail> details;

    /**
     * Construye una representación estructurada de error para respuestas de API.
     *
     * @param code código estable que identifica la categoría del error
     * @param message mensaje principal destinado al consumidor
     * @param httpStatus estado HTTP asociado al error
     * @param traceId identificador de traza para diagnóstico distribuido
     * @param timestamp instante del error; si es {@code null}, se usa el actual
     * @param details lista opcional de incidencias detalladas
     */
    public ApiError(String code, String message, int httpStatus, String traceId,
                     Instant timestamp, List<ApiErrorDetail> details) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.traceId = traceId;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
        this.details = details != null ? List.copyOf(details) : Collections.emptyList();
    }

    /**
     * Crea un constructor fluido para instancias de {@link ApiError}.
     *
     * @return nuevo builder vacío
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Devuelve el código estable del error.
     *
     * @return código de error
     */
    public String getCode() {
        return code;
    }

    /**
     * Devuelve el mensaje principal del error.
     *
     * @return mensaje descriptivo
     */
    public String getMessage() {
        return message;
    }

    /**
     * Devuelve el estado HTTP asociado al error.
     *
     * @return estado HTTP del error
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * Devuelve el identificador de traza asociado al error.
     *
     * @return identificador de traza o {@code null}
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Devuelve el instante en que se construyó el error.
     *
     * @return marca de tiempo del error
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Devuelve el detalle estructurado de incidencias asociadas al error.
     *
     * @return lista inmutable de detalles
     */
    public List<ApiErrorDetail> getDetails() {
        return details;
    }

    /**
     * Compara este error con otro a partir de los componentes relevantes del contrato expuesto.
     *
     * @param o objeto contra el que se evaluará la igualdad
     * @return {@code true} si ambos errores describen la misma condición observable
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiError apiError)) {
            return false;
        }
        return httpStatus == apiError.httpStatus
                && Objects.equals(code, apiError.code)
                && Objects.equals(message, apiError.message)
                && Objects.equals(traceId, apiError.traceId)
                && Objects.equals(details, apiError.details);
    }

    /**
     * Calcula el código hash consistente con la igualdad del contrato de error.
     *
     * @return código hash del error
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, message, httpStatus, traceId, details);
    }

    /**
     * Genera una representación textual útil para logging y depuración.
     *
     * @return representación textual del error
     */
    @Override
    public String toString() {
        return "ApiError{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", httpStatus=" + httpStatus +
                ", traceId='" + traceId + '\'' +
                ", timestamp=" + timestamp +
                ", details=" + details +
                '}';
    }

    /**
     * Builder fluido para ensamblar instancias de {@link ApiError} de manera legible.
     */
    public static final class Builder {
        private String code;
        private String message;
        private int httpStatus;
        private String traceId;
        private Instant timestamp;
        private List<ApiErrorDetail> details;

        private Builder() {
        }

        /**
         * Define el código estable del error.
         *
         * @param code código funcional o técnico del error
         * @return builder actual para encadenamiento
         */
        public Builder code(String code) {
            this.code = code;
            return this;
        }

        /**
         * Define el mensaje principal del error.
         *
         * @param message mensaje descriptivo destinado al consumidor
         * @return builder actual para encadenamiento
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Define el estado HTTP asociado al error.
         *
         * @param httpStatus estado HTTP del error
         * @return builder actual para encadenamiento
         */
        public Builder httpStatus(int httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        /**
         * Define el identificador de traza asociado al error.
         *
         * @param traceId identificador de trazabilidad distribuida
         * @return builder actual para encadenamiento
         */
        public Builder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        /**
         * Define la marca temporal asociada al error.
         *
         * @param timestamp instante del error
         * @return builder actual para encadenamiento
         */
        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        /**
         * Define los detalles estructurados asociados al error.
         *
         * @param details incidencias detalladas asociadas al error
         * @return builder actual para encadenamiento
         */
        public Builder details(List<ApiErrorDetail> details) {
            this.details = details;
            return this;
        }

        /**
         * Construye la instancia final de {@link ApiError}.
         *
         * @return error API construido con los valores acumulados
         */
        public ApiError build() {
            return new ApiError(code, message, httpStatus, traceId, timestamp, details);
        }
    }
}

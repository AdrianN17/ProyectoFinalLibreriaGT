package pe.andes.api.common.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Describe una incidencia puntual asociada a un {@link ApiError}, por ejemplo
 * un campo inválido, una regla incumplida o un valor rechazado.
 */
public final class ApiErrorDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Nombre lógico del campo, propiedad o atributo asociado a la incidencia.
     */
    private final String field;
    /**
     * Código específico del detalle cuando existe una clasificación más fina del error.
     */
    private final String code;
    /**
     * Mensaje legible que describe la validación fallida o la regla incumplida.
     */
    private final String message;
    /**
     * Valor rechazado que originó el error, útil para diagnóstico del consumidor.
     */
    private final Object rejectedValue;

    /**
     * Construye el detalle estructurado de un error individual.
     *
     * @param field nombre lógico del campo o atributo implicado
     * @param code código opcional específico de la incidencia
     * @param message mensaje descriptivo del problema detectado
     * @param rejectedValue valor recibido que originó el rechazo, si aplica
     */
    public ApiErrorDetail(String field, String code, String message, Object rejectedValue) {
        this.field = field;
        this.code = code;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    /**
     * Crea un detalle simple para un campo con mensaje descriptivo.
     *
     * @param field nombre lógico del campo o atributo implicado
     * @param message mensaje descriptivo del problema detectado
     * @return detalle de error construido con código y valor rechazado vacíos
     */
    public static ApiErrorDetail of(String field, String message) {
        return new ApiErrorDetail(field, null, message, null);
    }

    /**
     * Crea un detalle simple para un campo con código y mensaje descriptivo.
     *
     * @param field nombre lógico del campo o atributo implicado
     * @param code código específico de la incidencia
     * @param message mensaje descriptivo del problema detectado
     * @return detalle de error construido sin valor rechazado
     */
    public static ApiErrorDetail of(String field, String code, String message) {
        return new ApiErrorDetail(field, code, message, null);
    }

    /**
     * Devuelve el nombre lógico del campo afectado.
     *
     * @return nombre del campo afectado o {@code null}
     */
    public String getField() {
        return field;
    }

    /**
     * Devuelve el código específico asociado al detalle.
     *
     * @return código del detalle o {@code null}
     */
    public String getCode() {
        return code;
    }

    /**
     * Devuelve el mensaje descriptivo del detalle.
     *
     * @return mensaje del detalle
     */
    public String getMessage() {
        return message;
    }

    /**
     * Devuelve el valor rechazado que originó la incidencia, si corresponde.
     *
     * @return valor rechazado o {@code null}
     */
    public Object getRejectedValue() {
        return rejectedValue;
    }

    /**
     * Compara este detalle con otro usando sus componentes relevantes del contrato de error.
     *
     * @param o objeto contra el que se realizará la comparación
     * @return {@code true} si ambos detalles representan la misma incidencia
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiErrorDetail that)) {
            return false;
        }
        return Objects.equals(field, that.field)
                && Objects.equals(code, that.code)
                && Objects.equals(message, that.message)
                && Objects.equals(rejectedValue, that.rejectedValue);
    }

    /**
     * Calcula el código hash consistente con los atributos expuestos del detalle.
     *
     * @return código hash del detalle
     */
    @Override
    public int hashCode() {
        return Objects.hash(field, code, message, rejectedValue);
    }

    /**
     * Genera una representación textual útil para trazas y depuración.
     *
     * @return representación textual del detalle de error
     */
    @Override
    public String toString() {
        return "ApiErrorDetail{" +
                "field='" + field + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", rejectedValue=" + rejectedValue +
                '}';
    }
}

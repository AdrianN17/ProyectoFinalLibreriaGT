package pe.andes.api.server.error;

import pe.andes.api.common.model.ApiError;

/**
 * Punto de extensión para traducir excepciones ajenas a la jerarquía
 * {@code AndesApiException} al contrato estándar de errores de Andes.
 *
 * <p>Las implementaciones suelen declararse como beans de Spring para que
 * {@link GlobalExceptionHandler} las descubra e incorpore al flujo de manejo global de
 * excepciones. Esto resulta útil para adaptar errores de librerías externas o de capas
 * técnicas sin acoplarlas al modelo de excepciones propio del toolkit.
 *
 * @param <E> tipo concreto de excepción manejado por el adaptador
 */
public interface AndesExceptionMapper<E extends Throwable> {

    /**
     * Informa el tipo exacto de excepción compatible con este adaptador.
     *
     * @return clase de la excepción que este mapper sabe convertir
     */
    Class<E> getExceptionType();

    /**
     * Define el código de estado HTTP que debe usarse en la respuesta resultante.
     *
     * @return código HTTP asociado a la excepción adaptada
     */
    int getHttpStatus();

    /**
     * Construye la carga útil {@link ApiError} para la excepción recibida.
     *
     * @param exception instancia concreta de la excepción a traducir
     * @param traceId identificador de traza/correlación vigente; puede ser {@code null} si la
     *                petición no pasó por el filtro de correlación
     * @return representación estandarizada del error para serializar en la respuesta
     */
    ApiError map(E exception, String traceId);
}

package pe.andes.api.common.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa una colección paginada de elementos preparada para ser expuesta
 * dentro de {@link ApiResponse#getData()} junto con su metadata de paginación.
 *
 * @param <T> tipo de cada elemento contenido en la página
 */
public final class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Elementos pertenecientes a la página solicitada.
     */
    private final List<T> content;
    /**
     * Metadatos que describen posición, tamaño y totales de la colección paginada.
     */
    private final Pagination pagination;

    /**
     * Construye una respuesta paginada con contenido y metadata de paginación.
     *
     * @param content elementos de la página actual
     * @param pagination metadata de paginación asociada
     */
    public PageResponse(List<T> content, Pagination pagination) {
        this.content = content != null ? List.copyOf(content) : Collections.emptyList();
        this.pagination = pagination;
    }

    /**
     * Crea una respuesta paginada calculando la metadata a partir de parámetros básicos.
     *
     * @param content elementos de la página actual
     * @param page índice base cero de la página actual
     * @param size tamaño solicitado de página
     * @param totalElements total de elementos disponibles
     * @param <T> tipo de cada elemento contenido en la página
     * @return respuesta paginada con metadata calculada
     */
    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        return new PageResponse<>(content, Pagination.of(page, size, totalElements));
    }

    /**
     * Devuelve los elementos de la página actual.
     *
     * @return lista inmutable de elementos
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Devuelve la metadata de paginación asociada al contenido.
     *
     * @return información de paginación
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Compara esta respuesta paginada con otra según contenido y metadatos de paginación.
     *
     * @param o objeto contra el que se realizará la comparación
     * @return {@code true} si ambas respuestas paginadas son equivalentes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageResponse<?> that)) {
            return false;
        }
        return Objects.equals(content, that.content) && Objects.equals(pagination, that.pagination);
    }

    /**
     * Calcula el código hash consistente con el contenido y la metadata de paginación.
     *
     * @return código hash de la respuesta paginada
     */
    @Override
    public int hashCode() {
        return Objects.hash(content, pagination);
    }

    /**
     * Genera una representación textual de la página, útil para trazas y depuración.
     *
     * @return representación textual de la respuesta paginada
     */
    @Override
    public String toString() {
        return "PageResponse{" +
                "content=" + content +
                ", pagination=" + pagination +
                '}';
    }
}

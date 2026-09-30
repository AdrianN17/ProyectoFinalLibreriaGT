package pe.andes.api.common.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Contiene la metadata de paginación asociada a una colección parcial de resultados.
 */
public final class Pagination implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Índice base cero de la página actual.
     */
    private final int page;
    /**
     * Cantidad de elementos solicitados por página.
     */
    private final int size;
    /**
     * Total de elementos disponibles en la consulta completa.
     */
    private final long totalElements;
    /**
     * Total de páginas calculadas a partir del tamaño y del total de elementos.
     */
    private final int totalPages;
    /**
     * Indica si la página actual es la primera del conjunto.
     */
    private final boolean first;
    /**
     * Indica si la página actual es la última del conjunto.
     */
    private final boolean last;

    /**
     * Construye la metadata de paginación completa para una respuesta paginada.
     *
     * @param page índice base cero de la página actual
     * @param size tamaño solicitado de página
     * @param totalElements total de elementos disponibles
     * @param totalPages total de páginas calculadas
     * @param first indica si la página actual es la primera
     * @param last indica si la página actual es la última
     */
    public Pagination(int page, int size, long totalElements, int totalPages, boolean first, boolean last) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
    }

    /**
     * Crea metadata de paginación calculando el total de páginas y banderas de posición.
     *
     * @param page índice base cero de la página actual
     * @param size tamaño solicitado de página
     * @param totalElements total de elementos disponibles
     * @return metadata de paginación calculada
     */
    public static Pagination of(int page, int size, long totalElements) {
        int totalPages = size == 0 ? 0 : (int) Math.ceil((double) totalElements / size);
        boolean isFirst = page == 0;
        boolean isLast = page >= totalPages - 1;
        return new Pagination(page, size, totalElements, totalPages, isFirst, isLast);
    }

    /**
     * Devuelve el índice base cero de la página actual.
     *
     * @return índice de la página actual
     */
    public int getPage() {
        return page;
    }

    /**
     * Devuelve el tamaño solicitado de página.
     *
     * @return tamaño de página
     */
    public int getSize() {
        return size;
    }

    /**
     * Devuelve el total de elementos disponibles en la consulta original.
     *
     * @return total de elementos
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Devuelve el total de páginas calculadas.
     *
     * @return total de páginas
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Indica si la página actual es la primera de la secuencia.
     *
     * @return {@code true} si es la primera página
     */
    public boolean isFirst() {
        return first;
    }

    /**
     * Indica si la página actual es la última de la secuencia.
     *
     * @return {@code true} si es la última página
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Compara esta metadata de paginación con otra según sus componentes expuestos.
     *
     * @param o objeto contra el que se evaluará la igualdad
     * @return {@code true} si ambos objetos representan la misma paginación
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagination that)) {
            return false;
        }
        return page == that.page
                && size == that.size
                && totalElements == that.totalElements
                && totalPages == that.totalPages
                && first == that.first
                && last == that.last;
    }

    /**
     * Calcula el código hash consistente con la igualdad de la metadata de paginación.
     *
     * @return código hash de la paginación
     */
    @Override
    public int hashCode() {
        return Objects.hash(page, size, totalElements, totalPages, first, last);
    }

    /**
     * Genera una representación textual útil para logs y depuración.
     *
     * @return representación textual de la metadata de paginación
     */
    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", first=" + first +
                ", last=" + last +
                '}';
    }
}

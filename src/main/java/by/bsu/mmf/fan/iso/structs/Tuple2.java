package by.bsu.mmf.fan.iso.structs;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

/**
 * A tuple of two elements.
 *
 * @param <T1> type of the first element.
 * @param <T2> type of the second element.
 */
public class Tuple2<T1, T2> {
    private final T1 first;
    private final T2 second;

    /**
     * Constructs a 2-tuple.
     *
     * @param first  the first element.
     * @param second the second element.
     */
    public Tuple2(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first element of this tuple.
     *
     * @return the first element of this tuple.
     */
    public T1 getFirst() {
        return this.first;
    }

    /**
     * Returns the second element of this tuple.
     *
     * @return the second element of this tuple.
     */
    public T2 getSecond() {
        return this.second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Tuple2<?, ?> tuple = (Tuple2<?, ?>) o;
        return Objects.equals(this.first, tuple.first) &&
                Objects.equals(this.second, tuple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    /**
     * Returns a comparator that sorts the tuples, first using the first element, and then using the second element.
     *
     * @return a comparator that sorts the tuples.
     */
    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> Comparator<Tuple2<T1, T2>> comparator() {
        return Comparator.<Tuple2<T1, T2>, T1>comparing(x -> x.first)
                .thenComparing(x -> x.second);
    }
}

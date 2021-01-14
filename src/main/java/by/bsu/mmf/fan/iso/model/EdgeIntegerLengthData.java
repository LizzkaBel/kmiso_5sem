package by.bsu.mmf.fan.iso.model;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Data class containing the length of the edge.
 */
public class EdgeIntegerLengthData implements Comparable<EdgeIntegerLengthData> {
    private final Integer length;

    /**
     * Constructs the data class containing the length of the edge.
     *
     * @param length the length of the edge.
     */
    public EdgeIntegerLengthData(Integer length) {
        this.length = length;
    }

    /**
     * Returns the length of the edge.
     *
     * @return the length of the edge.
     */
    public Integer getLength() {
        return this.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        EdgeIntegerLengthData that = (EdgeIntegerLengthData) o;
        return Objects.equals(this.length, that.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.length);
    }

    @Override
    public String toString() {
        return MessageFormat.format("EdgeIntegerLengthData'{'length={0}'}'", this.length);
    }

    @Override
    public int compareTo(@NotNull EdgeIntegerLengthData edgeIntegerLengthData) {
        return Integer.compare(this.length, edgeIntegerLengthData.length);
    }
}

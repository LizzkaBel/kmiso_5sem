package by.bsu.mmf.fan.iso.model;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Data class containing the weight of the edge.
 */
public class EdgeIntegerWeightData implements Comparable<EdgeIntegerWeightData> {
    private final Integer weight;

    /**
     * Constructs the data class containing the weight of the edge.
     *
     * @param weight the weight of the edge.
     */
    public EdgeIntegerWeightData(Integer weight) {
        this.weight = weight;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return the weight of the edge.
     */
    public Integer getWeight() {
        return this.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        EdgeIntegerWeightData that = (EdgeIntegerWeightData) o;
        return Objects.equals(this.weight, that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.weight);
    }

    @Override
    public String toString() {
        return MessageFormat.format("EdgeIntegerWeightData'{'weight={0}'}'", this.weight);
    }

    @Override
    public int compareTo(@NotNull EdgeIntegerWeightData edgeIntegerWeightData) {
        return Integer.compare(this.weight, edgeIntegerWeightData.weight);
    }
}


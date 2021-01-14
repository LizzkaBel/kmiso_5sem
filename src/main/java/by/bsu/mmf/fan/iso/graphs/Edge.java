package by.bsu.mmf.fan.iso.graphs;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * An edge, either directed or undirected, depending on the parent graph.
 *
 * @param <VertexIdType> the type of the vertices in the graph.
 * @param <DataT>        the type of the data associated with the edge.
 */
public class Edge<VertexIdType extends Comparable<VertexIdType>, DataT> {
    private final DataT data;
    private final VertexIdType sourceVertex;
    private final VertexIdType targetVertex;

    /**
     * Constructs an edge from the source vertex to the target vertex with the specified additional data.
     *
     * @param sourceVertex the source vertex.
     * @param targetVertex the target vertex.
     * @param data         the additional data to be associated with this edge.
     */
    public Edge(VertexIdType sourceVertex, VertexIdType targetVertex, DataT data) {
        this.data = data;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
    }

    /**
     * Returns the source vertex.
     *
     * @return the source vertex.
     */
    public VertexIdType getSourceVertex() {
        return this.sourceVertex;
    }

    /**
     * Returns the target vertex.
     *
     * @return the target vertex.
     */
    public VertexIdType getTargetVertex() {
        return this.targetVertex;
    }

    /**
     * Returns the additional data associated with this edge.
     *
     * @return the additional data associated with this edge.
     */
    public DataT getData() {
        return this.data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Edge<?, ?> edge = (Edge<?, ?>) o;
        return Objects.equals(this.sourceVertex, edge.sourceVertex) &&
                Objects.equals(this.targetVertex, edge.targetVertex) &&
                Objects.equals(this.data, edge.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.sourceVertex, this.targetVertex);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Edge'{'{0}<->{1}, data={2}'}'",
                this.sourceVertex.toString(),
                this.targetVertex.toString(),
                this.data.toString()
        );
    }
}


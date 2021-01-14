package by.bsu.mmf.fan.iso.graphs;

import by.bsu.mmf.fan.iso.structs.Tuple2;

import java.text.MessageFormat;
import java.util.*;

public class GenericBasicGraph<VertexT extends Comparable<VertexT>, DataT> extends BasicGraph<VertexT, DataT> {
    private final Map<Tuple2<VertexT, VertexT>, Edge<VertexT, DataT>> edgeIndex = new HashMap<>();
    private final Map<VertexT, List<Edge<VertexT, DataT>>> edgeFromIndex = new HashMap<>();
    private final Map<VertexT, List<Edge<VertexT, DataT>>> edgeToIndex = new HashMap<>();
    private final Map<VertexT, Integer> vertexIndexes = new HashMap<>();

    /**
     * Constructs a graph with specified vertices and edges.
     *
     * @param vertices   list of vertices in this graph.
     * @param edges      list of edges in this graph.
     * @param isDirected set to true if a directed graph should be constructed, set to false if an undirected graph
     */
    public GenericBasicGraph(List<VertexT> vertices, List<Edge<VertexT, DataT>> edges, boolean isDirected) {
        super(vertices, edges, isDirected);
        this.generateVertexIndex();
        this.generateEdgeIndex();
    }

    protected void generateVertexIndex() {
        for (int i = 0; i < this.vertices.size(); i++) {
            this.vertexIndexes.put(this.vertices.get(i), i);
        }
    }

    protected void generateEdgeIndex() {
        for (Edge<VertexT, DataT> edge : this.edges) {
            Tuple2<VertexT, VertexT> key = new Tuple2<>(edge.getSourceVertex(), edge.getTargetVertex());
            Tuple2<VertexT, VertexT> inverseKey = new Tuple2<>(edge.getTargetVertex(), edge.getSourceVertex());
            if (this.edgeIndex.containsKey(key) || (!this.isDirected && this.edgeIndex.containsKey(inverseKey)))
                throw new IllegalArgumentException(
                        MessageFormat.format(
                                "Duplicate edge from {0} to {1}",
                                edge.getSourceVertex(),
                                edge.getTargetVertex()
                        )
                );
            this.edgeIndex.put(key, edge);
            if (!this.isDirected)
                this.edgeIndex.put(inverseKey, edge);

            this.edgeFromIndex.computeIfAbsent(edge.getSourceVertex(), k -> new ArrayList<>())
                    .add(new Edge<>(edge.getSourceVertex(), edge.getTargetVertex(), edge.getData()));
            this.edgeToIndex.computeIfAbsent(edge.getTargetVertex(), k -> new ArrayList<>())
                    .add(new Edge<>(edge.getSourceVertex(), edge.getTargetVertex(), edge.getData()));
            if (!this.isDirected) {
                this.edgeFromIndex.computeIfAbsent(edge.getTargetVertex(), k -> new ArrayList<>())
                        .add(new Edge<>(edge.getTargetVertex(), edge.getSourceVertex(), edge.getData()));
                this.edgeToIndex.computeIfAbsent(edge.getSourceVertex(), k -> new ArrayList<>())
                        .add(new Edge<>(edge.getTargetVertex(), edge.getSourceVertex(), edge.getData()));
            }
        }
    }

    @Override
    protected Integer getVertexIndex(VertexT vertex) {
        return this.vertexIndexes.get(vertex);
    }

    @Override
    public List<Edge<VertexT, DataT>> getEdgesFromVertex(VertexT vertex) {
        return Collections.unmodifiableList(this.edgeFromIndex.getOrDefault(vertex, List.of()));
    }

    @Override
    public List<Edge<VertexT, DataT>> getEdgesToVertex(VertexT vertex) {
        return Collections.unmodifiableList(this.edgeToIndex.getOrDefault(vertex, List.of()));
    }

    @Override
    public Edge<VertexT, DataT> lookupEdge(VertexT fromVertex, VertexT toVertex) {
        return this.edgeIndex.get(new Tuple2<VertexT, VertexT>(fromVertex, toVertex));
    }

}

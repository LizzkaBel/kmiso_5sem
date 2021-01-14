package by.bsu.mmf.fan.iso.graphs;

import by.bsu.mmf.fan.iso.structs.Tuple2;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContiguousIntegerBasicGraph<DataT> extends BasicGraph<Integer, DataT> {
    private final Map<Tuple2<Integer, Integer>, Edge<Integer, DataT>> edgeIndex = new HashMap<>();
    private final List<List<Edge<Integer, DataT>>> edgeFromIndex = new ArrayList<>();
    private final List<List<Edge<Integer, DataT>>> edgeToIndex = new ArrayList<>();

    /**
     * Constructs a graph with specified vertices and edges.
     *
     * @param numberOfVertices number of vertices in the desired graph.
     * @param edges            list of edges in this graph.
     * @param isDirected       set to true if a directed graph should be constructed, set to false if an undirected
     *                         graph
     */
    public ContiguousIntegerBasicGraph(int numberOfVertices, List<Edge<Integer, DataT>> edges, boolean isDirected) {
        super(IntStream.range(0, numberOfVertices).boxed().collect(Collectors.toList()), edges, isDirected);
        this.generateVertexIndex();
        this.generateEdgeIndex();
    }

    protected void generateVertexIndex() {
    }

    protected void generateEdgeIndex() {
        for (int i = 0; i < this.getNumberOfVertices(); i++) {
            this.edgeFromIndex.add(new ArrayList<>());
            this.edgeToIndex.add(new ArrayList<>());
        }
        for (var edge : this.edges) {
            this.edgeFromIndex.get(edge.getSourceVertex()).add(edge);
            this.edgeToIndex.get(edge.getTargetVertex()).add(edge);
            if (!this.isDirected) {
                this.edgeFromIndex.get(edge.getTargetVertex()).add(edge);
                this.edgeToIndex.get(edge.getSourceVertex()).add(edge);
            }
            Tuple2<Integer, Integer> key = new Tuple2<>(edge.getSourceVertex(), edge.getTargetVertex());
            Tuple2<Integer, Integer> inverseKey = new Tuple2<>(edge.getTargetVertex(), edge.getSourceVertex());
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
        }
    }

    @Override
    protected Integer getVertexIndex(Integer vertex) {
        if (vertex < 0 || vertex >= this.getNumberOfVertices())
            return null;
        return vertex;
    }

    @Override
    public List<Edge<Integer, DataT>> getEdgesFromVertex(Integer vertex) {
        return this.edgeFromIndex.get(vertex);
    }

    @Override
    public List<Edge<Integer, DataT>> getEdgesToVertex(Integer vertex) {
        return this.edgeToIndex.get(vertex);
    }

    @Override
    public Edge<Integer, DataT> lookupEdge(Integer fromVertex, Integer toVertex) {
        return this.edgeIndex.get(new Tuple2<>(fromVertex, toVertex));
    }
}

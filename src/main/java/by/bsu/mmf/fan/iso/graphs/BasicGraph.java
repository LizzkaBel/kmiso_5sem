package by.bsu.mmf.fan.iso.graphs;

import java.util.*;

/**
 * A graph consisting of vertices of type VertexT with edges which contain data of type DataT. Can be either a directed
 * or an undirected graph.
 *
 * @param <VertexT> type of the vertices.
 * @param <DataT>   type of the data associated with each edge.
 */
public abstract class BasicGraph<VertexT extends Comparable<VertexT>, DataT>
        implements Graph<VertexT, Edge<VertexT, DataT>> {

    protected final List<Edge<VertexT, DataT>> edges;
    protected final boolean isDirected;
    protected final List<VertexT> vertices;

    /**
     * Constructs a graph with specified vertices and edges.
     *
     * @param vertices   list of vertices in this graph.
     * @param edges      list of edges in this graph.
     * @param isDirected set to true if a directed graph should be constructed, set to false if an undirected graph
     *                   should be constructed.
     */
    protected BasicGraph(List<VertexT> vertices, List<Edge<VertexT, DataT>> edges, boolean isDirected) {
        this.vertices = vertices;
        this.edges = edges;
        this.isDirected = isDirected;
    }


    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph.
     */
    @Override
    public int getNumberOfVertices() {
        return this.vertices.size();
    }


    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph.
     */
    @Override
    public int getNumberOfEdges() {
        return this.edges.size();
    }

    /**
     * Returns the list of vertices in this graph.
     *
     * @return the list of vertices in this graph.
     */
    @Override
    public List<VertexT> getVertices() {
        return this.vertices;
    }

    /**
     * Returns the index of the vertex in the vertex list.
     *
     * @return the index of the vertex in the vertex list.
     */
    protected abstract Integer getVertexIndex(VertexT vertex);

    /**
     * Returns the list of edges in this graph.
     *
     * @return the list of edges in this graph.
     */
    @Override
    public List<Edge<VertexT, DataT>> getEdges() {
        return Collections.unmodifiableList(this.edges);
    }

    /**
     * Returns the list of edges that start in the specified vertex.
     *
     * @param vertex the source vertex.
     * @return the list of edges in this graph.
     */
    @Override
    public abstract List<Edge<VertexT, DataT>> getEdgesFromVertex(VertexT vertex);

    /**
     * Returns the list of edges that start in the specified vertex.
     *
     * @param vertex the target vertex.
     * @return the list of edges in this graph.
     */
    @Override
    public abstract List<Edge<VertexT, DataT>> getEdgesToVertex(VertexT vertex);

    /**
     * Returns the edge connecting the specified vertices. For undirected graphs, the order of vertices is not taken
     * into account.
     *
     * @param fromVertex the source vertex.
     * @param toVertex   the target vertex.
     * @return the edge connecting the source vertex to the target vertex, or null if such an edge does not exist.
     */
    @Override
    public abstract Edge<VertexT, DataT> lookupEdge(VertexT fromVertex, VertexT toVertex);

    /**
     * Returns true if this graph is directed, or false if it is undirected.
     *
     * @return true if this graph is directed, or false if it is undirected.
     */
    @Override
    public boolean isDirected() {
        return this.isDirected;
    }

    /**
     * Constructs a graph with vertices numbered from <pre>0</pre> to <pre>numberOfVertices - 1</pre> (inclusive) and
     * the edges passed to this function.
     *
     * @param numberOfVertices the number of vertices to create.
     * @param edges            the edges to add to this graph.
     * @param isDirected       set to true if a directed graph should be constructed, set to false if an undirected graph
     *                         should be constructed.
     * @param <DataT>          type of the data associated with each edge.
     * @return the constructed graph.
     */
    public static <DataT> ContiguousIntegerBasicGraph<DataT> createIntegerVertexGraph(
            int numberOfVertices,
            List<Edge<Integer, DataT>> edges,
            boolean isDirected
    ) {
        return new ContiguousIntegerBasicGraph<>(
                numberOfVertices,
                edges,
                isDirected
        );
    }

    /**
     * Constructs a graph with the specified vertices and edges.
     *
     * @param vertices   list of vertices in this graph.
     * @param edges      list of edges in this graph.
     * @param isDirected set to true if a directed graph should be constructed, set to false if an undirected graph
     *                   should be constructed.
     * @param <DataT>    type of the data associated with each edge.
     * @return the constructed graph.
     */
    public static <VertexT extends Comparable<VertexT>, DataT> BasicGraph<VertexT, DataT> createVertexGraph(
            List<VertexT> vertices,
            List<Edge<VertexT, DataT>> edges,
            boolean isDirected
    ) {
        return new GenericBasicGraph<>(
                vertices,
                edges,
                isDirected
        );
    }

    /**
     * Converts this generic graph into an adjacency list, where each vertex is represented by its index in the current
     * graph's vertex array.
     *
     * @return the adjacency list.
     */
    @Override
    public List<List<Integer>> toAdjacencyList() {
        var adjacencyList = new ArrayList<List<Integer>>();
        for (int i = 0; i < this.vertices.size(); i++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (var edge : this.edges) {
            Integer from = this.getVertexIndex(edge.getSourceVertex());
            Integer to = this.getVertexIndex(edge.getTargetVertex());
            adjacencyList.get(from).add(to);
            if (!this.isDirected)
                adjacencyList.get(to).add(from);
        }
        return adjacencyList;
    }
}

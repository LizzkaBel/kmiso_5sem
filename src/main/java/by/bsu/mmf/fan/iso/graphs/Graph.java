package by.bsu.mmf.fan.iso.graphs;

import java.util.List;

/**
 * A graph consisting of vertices of type VertexT with edges of type EdgeT. Can be either a directed or
 * an undirected graph.
 *
 * @param <VertexT> type of the vertices.
 * @param <EdgeT>   type of the edges.
 */
public interface Graph<VertexT extends Comparable<VertexT>, EdgeT> {
    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph.
     */
    int getNumberOfVertices();

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph.
     */
    int getNumberOfEdges();

    /**
     * Returns the list of vertices in this graph.
     *
     * @return the list of vertices in this graph.
     */
    List<VertexT> getVertices();

    /**
     * Returns the list of edges in this graph.
     *
     * @return the list of edges in this graph.
     */
    List<EdgeT> getEdges();

    /**
     * Returns the list of edges that start in the specified vertex.
     *
     * @param vertex the source vertex.
     * @return the list of edges in this graph.
     */
    List<EdgeT> getEdgesFromVertex(VertexT vertex);

    /**
     * Returns the list of edges that start in the specified vertex.
     *
     * @param vertex the target vertex.
     * @return the list of edges in this graph.
     */
    List<EdgeT> getEdgesToVertex(VertexT vertex);

    /**
     * Returns the edge connecting the specified vertices. For undirected graphs, the order of vertices is not taken
     * into account.
     *
     * @param fromVertex the source vertex.
     * @param toVertex   the target vertex.
     * @return the edge connecting the source vertex to the target vertex, or null if such an edge does not exist.
     */
    EdgeT lookupEdge(VertexT fromVertex, VertexT toVertex);

    /**
     * Returns true if this graph is directed, or false if it is undirected.
     *
     * @return true if this graph is directed, or false if it is undirected.
     */
    boolean isDirected();

    /**
     * Converts this generic graph into an adjacency list, where each vertex is represented by its index in the current
     * graph's vertex array.
     *
     * @return the adjacency list.
     */
    List<List<Integer>> toAdjacencyList();
}

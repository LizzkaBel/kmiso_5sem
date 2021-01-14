package by.bsu.mmf.fan.iso.tasks;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.model.EdgeIntegerLengthData;

import java.util.List;

/**
 * Task number 3.
 * <p>
 * Implement Floyd's algorithm for finding the shortest paths between all pairs of vertices.
 * <p>
 * The graph may contain up to 10^2 nodes. The testing server may request up to 10^4 paths.
 * <p>
 * You may assume that all edges have non-negative lengths and that the graph is strongly connected.
 */
public interface FindAllShortestPathsSolution {
    /**
     * Builds up indexes necessary to perform shortest path queries.
     *
     * @param graph the graph, consisting of vertices numbered from <pre>0</pre> to
     *              <pre>graph.getVertices().size() - 1</pre> (inclusive).
     */
    void performPreprocessing(
            ContiguousIntegerBasicGraph<EdgeIntegerLengthData> graph
    );

    /**
     * Finds the sequence of vertices corresponding to the shortest path from the fromVertex to the toVertex.
     *
     * @param fromVertex the source vertex. Must be the first element in the returned sequence of vertices.
     * @param toVertex   the target vertex. Must be the last element in the returned sequence of vertices.
     * @return a sequence of vertices corresponding to the shortest path from the fromVertex to the toVertex.
     */
    List<Integer> getShortestPath(int fromVertex, int toVertex);
}

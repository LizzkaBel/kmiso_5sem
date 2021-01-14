package by.bsu.mmf.fan.iso.tasks;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.graphs.Edge;
import by.bsu.mmf.fan.iso.model.EdgeIntegerLengthData;

import java.util.List;

/**
 * Task number 2.
 * <p>
 * Implement Dijkstra's algorithm for finding the shortest path between two vertices.
 * <p>
 * The graph may contain up to 10^6 edges. For each example, your solution must complete its execution in less than 1
 * second.
 * <p>
 * You may assume that all edges have non-negative lengths and that the graph is strongly-connected.
 */
public interface FindShortestPathSolution {
    /**
     * Finds the sequence of vertices corresponding to the shortest path from the fromVertex to the toVertex.
     *
     * @param graph      the graph, consisting of vertices numbered from <pre>0</pre> to
     *                   <pre>graph.getVertices().size() - 1</pre> (inclusive).
     * @param fromVertex the source vertex. Must be the first element in the returned sequence of vertices.
     * @param toVertex   the target vertex. Must be the last element in the returned sequence of vertices.
     * @return a sequence of vertices corresponding to the shortest path from the fromVertex to the toVertex.
     */
    List<Integer> getShortestPath(
            ContiguousIntegerBasicGraph<EdgeIntegerLengthData> graph,
            int fromVertex,
            int toVertex
    );
}

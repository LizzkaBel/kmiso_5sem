package by.bsu.mmf.fan.iso.tasks;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.graphs.Edge;
import by.bsu.mmf.fan.iso.model.EdgeIntegerWeightData;

import java.util.List;

/**
 * Task number 1.
 * <p>
 * Implement Kruskal's algorithm for finding the minimum spanning tree of an undirected graph.
 * <p>
 * The graph may contain up to 1000 vertices and 10^6 edges. For each example, your solution must complete its execution
 * in 1 second.
 */
public interface FindMinimumSpanningTreeTaskSolution {
    /**
     * Finds the list of edges belonging to the minimum spanning tree of the undirected graph.
     *
     * @param graph the graph to be processed by Kruskal's algorithm.
     * @return the list of edges in the minimum spanning tree.
     */
    List<? extends Edge<Integer, EdgeIntegerWeightData>> findMinimumSpanningTree(
            ContiguousIntegerBasicGraph<EdgeIntegerWeightData> graph
    );
}

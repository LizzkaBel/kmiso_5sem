package by.bsu.mmf.fan.iso.utils;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A recursive algorithm for finding connected components within a graph.
 */
public class StronglyConnectedComponentFinder {
    private final BitSet used;
    private final List<? extends List<Integer>> edges;
    private final ArrayList<Integer> order = new ArrayList<>();
    private ArrayList<Integer> component = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> inverseEdges;
    private final int numberOfVertices;

    private StronglyConnectedComponentFinder(List<? extends List<Integer>> adjacencyList) {
        this.numberOfVertices = adjacencyList.size();
        this.used = new BitSet(this.numberOfVertices);
        this.edges = adjacencyList;
        this.inverseEdges = new ArrayList<>();
        for (int i = 0; i < this.numberOfVertices; i++) {
            this.inverseEdges.add(new ArrayList<>());
        }
        for (int i = 0; i < this.numberOfVertices; i++) {
            for (int j : adjacencyList.get(i)) {
                this.inverseEdges.get(j).add(i);
            }
        }
    }

    /**
     * Finds strongly connected components in the graph represented by the adjacency list.
     *
     * @param adjacencyList the adjacency list representing the graph.
     * @return a list of strongly connected components, each one represented by a list of vertices.
     */
    public static List<List<Integer>> getComponents(List<? extends List<Integer>> adjacencyList) {
        return new StronglyConnectedComponentFinder(adjacencyList).getComponents();
    }

    /**
     * Finds strongly connected components in the graph.
     *
     * @param graph     the graph to analyse.
     * @param <VertexT> the type of the vertices in the graph.
     * @param <DataT>   the type of the data associated with each edge in the graph.
     * @return a list of strongly connected components, each one represented by a list of vertices.
     */
    public static <VertexT extends Comparable<VertexT>, DataT> List<List<VertexT>> getComponents(
            BasicGraph<VertexT, DataT> graph
    ) {
        var components = getComponents(graph.toAdjacencyList());
        return components.stream()
                .map(component -> component.stream()
                        .map(vertexId -> graph.getVertices().get(vertexId))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<List<Integer>> getComponents() {
        this.used.set(0, this.numberOfVertices, false);
        for (int i = 0; i < this.numberOfVertices; i++) {
            if (!this.used.get(i))
                this.forwardDfs(i);
        }
        this.used.set(0, this.numberOfVertices, false);
        ArrayList<List<Integer>> components = new ArrayList<>();
        for (int i = 0; i < this.numberOfVertices; i++) {
            int v = this.order.get(this.numberOfVertices - 1 - i);
            if (!this.used.get(v)) {
                this.backwardDfs(v);
                components.add(this.component);
                this.component = new ArrayList<>();
            }
        }
        return components;
    }

    private void forwardDfs(int v) {
        this.used.set(v);
        for (var v2 : this.edges.get(v))
            if (!this.used.get(v2))
                this.forwardDfs(v2);
        this.order.add(v);
    }

    private void backwardDfs(int v) {
        this.used.set(v);
        this.component.add(v);
        for (var v2 : this.inverseEdges.get(v))
            if (!this.used.get(v2))
                this.backwardDfs(v2);
    }
}

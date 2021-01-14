package by.bsu.mmf.fan.iso.solution;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.graphs.Edge;
import by.bsu.mmf.fan.iso.model.EdgeIntegerWeightData;
import by.bsu.mmf.fan.iso.tasks.FindMinimumSpanningTreeTaskSolution;
import by.bsu.mmf.fan.iso.utils.DisjointSetUnion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Task number 1.
 * <p>
 * Implement Kruskal's algorithm for finding the minimum spanning tree of an undirected graph.
 * <p>
 * The graph may contain up to 1000 vertices and 10^6 edges. For each example, your solution must complete its execution
 * in 1 second.
 * <p>
 * You may assume that the graph is connected.
 * <p>
 * Use the {@link DisjointSetUnion DisjointSetUnion} data structure to check whether adding an edge will create a loop
 * in the graph.
 */
public class FindMinimumSpanningTreeTaskSolutionImpl implements FindMinimumSpanningTreeTaskSolution {
    /**
     * Finds the list of edges belonging to the minimum spanning tree of the undirected graph.
     *
     * @param graph the graph to be processed by Kruskal's algorithm.
     * @return the list of edges in the minimum spanning tree.
     */
    /*
    by.bsu.mmf.fan.iso.utils.DisjointSetUnion
    0001:     int mstKruskal(Edge[] edges) {
    0002:         DSF dsf = new DSF(vNum); // СНМ
    0003:         sort(edges); // Сортируем ребра
    0004:         int ret = 0; // результат
    0005:         for (Edge e : edges) // перебираем ребра в порядке возрастания
    0006:             if (dsf.union(e.u, e.v)) // если ребра принадлежат разным компонентам
    0007:                 ret += e.w; // добавляем вес ребра к стоимости MST
    0008:         return ret;
    0009:     }
     */
    @Override
    public List<? extends Edge<Integer, EdgeIntegerWeightData>> findMinimumSpanningTree(
            ContiguousIntegerBasicGraph<EdgeIntegerWeightData> graph
    ) {
        DisjointSetUnion disjointSetUnion = new DisjointSetUnion(graph.getNumberOfVertices());
        var res = new ArrayList<>(graph.getEdges()) ;
        res.sort(Comparator.comparingInt(edge -> edge.getData().getWeight()));
        List<Edge<Integer, EdgeIntegerWeightData>> toReturn = new ArrayList<>();
        for(var edge : res){
            if(!disjointSetUnion.areInSameSet(edge.getSourceVertex(),edge.getTargetVertex())){
                disjointSetUnion.mergeSets(edge.getSourceVertex(),edge.getTargetVertex());
                toReturn.add(edge);
            }
        }
        return toReturn;
    }

}

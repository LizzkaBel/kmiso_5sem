package by.bsu.mmf.fan.iso.solution;

import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.model.EdgeIntegerLengthData;
import by.bsu.mmf.fan.iso.tasks.FindAllShortestPathsSolution;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.lang.Integer.min;

/**
 * Task number 3.
 * <p>
 * Implement Floyd's algorithm for finding the shortest paths between all pairs of vertices.
 * <p>
 * The graph may contain up to 10^2 nodes. The testing server may request up to 10^4 paths.
 * <p>
 * You may assume that all edges have non-negative lengths and that the graph is strongly connected.
 */
public class FindAllShortestPathsSolutionImpl implements FindAllShortestPathsSolution {
    /**
     * Builds up indexes necessary to perform shortest path queries.
     *
     * @param graph the graph, consisting of vertices numbered from <pre>0</pre> to
     *              <pre>graph.getVertices().size() - 1</pre> (inclusive).
     */

    private int[][] adjMatrix;
    private  int[][] a;

    @Override
    public void performPreprocessing(ContiguousIntegerBasicGraph<EdgeIntegerLengthData> graph) {
        int numberOfVertices = graph.getNumberOfVertices();
        this.adjMatrix = new int[numberOfVertices][numberOfVertices];
        this.a = new int[numberOfVertices][numberOfVertices];
        for (int i = 0 ; i < numberOfVertices; i++){
            for (int j = 0 ; j < numberOfVertices; j++){
                this.adjMatrix[i][j] = Integer.MAX_VALUE / 2; // костыль ( типо бесконечность )
                this.a[i][j] = j;
            }
            for(var edge : graph.getEdgesFromVertex(i)){
                this.adjMatrix[i][edge.getTargetVertex()] = edge.getData().getLength();
            }
        }

        for (int k = 0; k < numberOfVertices; k++) {
            for (int i = 0; i < numberOfVertices; i++) {
                for (int j = 0; j < numberOfVertices; j++) {
                    if((this.adjMatrix[i][k] + this.adjMatrix[k][j]) < this.adjMatrix[i][j]){
                        this.adjMatrix[i][j] = this.adjMatrix[i][k] + this.adjMatrix[k][j];
                        this.a[i][j] = this.a[i][k];
                    }
                }
            }
        }
    }

    /**
     * Finds the sequence of vertices corresponding to the shortest path from the fromVertex to the toVertex.
     *
     * @param fromVertex the source vertex. Must be the first element in the returned sequence of vertices.
     * @param toVertex   the target vertex. Must be the last element in the returned sequence of vertices.
     * @return a sequence of vertices corresponding to the shortest path from the fromVertex to the toVertex.
     */
    /*
    x , 8 , 12      1 , 2 , 2
    x , x , 4       1 , 2 , 3
    x , x , x       1 , 2 , 3
    */
    // 1 - > 2 -> 3
    @Override
    public List<Integer> getShortestPath(int fromVertex, int toVertex) {
        List<Integer> shortestPath = new LinkedList<>();
        shortestPath.add(fromVertex);
        while(this.a[fromVertex][toVertex] != toVertex){
            fromVertex = this.a[fromVertex][toVertex];
            shortestPath.add(fromVertex);
        }
        shortestPath.add(toVertex);
        return  shortestPath;
    }
}

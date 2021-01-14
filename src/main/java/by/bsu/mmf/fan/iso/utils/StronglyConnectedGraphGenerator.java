package by.bsu.mmf.fan.iso.utils;

import by.bsu.mmf.fan.iso.utils.dfs.EnqueueConsumer;
import by.bsu.mmf.fan.iso.utils.dfs.NonRecursiveDFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum VisitType {
    VISIT,
    SKIP
}

/**
 * Generates strongly connected graphs using the modified Tarjan algorithm described in
 * https://csce.ucmss.com/cr/books/2017/LFS/CSREA2017/MSV3359.pdf
 */
public class StronglyConnectedGraphGenerator extends NonRecursiveDFS<VisitType, VisitType> {

    private final int[] startTime;
    private final int[] lowTime;
    private final int[] startTimeToNode;
    private final int numberOfVertices;
    private final Random random;
    private int globalStartTime;
    private final ArrayList<List<Integer>> adjacencyList;

    private StronglyConnectedGraphGenerator(int numberOfVertices, Random random) {
        this.startTime = new int[numberOfVertices];
        this.lowTime = new int[numberOfVertices];
        this.startTimeToNode = new int[2 * numberOfVertices];
        this.numberOfVertices = numberOfVertices;
        this.random = random;
        this.adjacencyList = new ArrayList<>();
        this.globalStartTime = 0;
        for (int i = 0; i < numberOfVertices; i++) {
            this.startTime[i] = -1;
            this.lowTime[i] = -1;
            this.adjacencyList.add(new ArrayList<>());
        }
        for (int i = 0; i < 2 * numberOfVertices; i++) {
            this.startTimeToNode[i] = -1;
        }
    }

    /**
     * Generates a strongly connected graph using the modified Tarjan algorithm described in
     * https://csce.ucmss.com/cr/books/2017/LFS/CSREA2017/MSV3359.pdf
     *
     * @param numberOfVertices the number of vertices in the graph.
     * @param random           the random number generator to use.
     * @return an adjacency list of the graph.
     */
    public static List<List<Integer>> generateEdges(int numberOfVertices, Random random) {
        return new StronglyConnectedGraphGenerator(numberOfVertices, random).generateEdges();
    }

    private List<List<Integer>> generateEdges() {
        this.generateTree();
        for (int i = 0; i < this.numberOfVertices; i++) {
            if (this.startTime[i] == -1)
                this.performDfs(i, VisitType.VISIT);
        }
        return Graphs.shuffleAdjacencyList(this.adjacencyList);
    }

    private void generateTree() {
        var vertices = new ArrayList<Integer>();
        vertices.add(0);
        for (int i = 1; i < this.numberOfVertices; i++) {
            var j = vertices.get(this.random.nextInt(vertices.size()));
            this.adjacencyList.get(j).add(i);
            vertices.add(i);
        }
    }

    @Override
    public VisitType visit(int node, VisitType param, EnqueueConsumer<VisitType> enqueue) {
        if (param == VisitType.SKIP)
            return VisitType.SKIP;
        this.startTime[node] = this.globalStartTime++;
        this.lowTime[node] = this.startTime[node];
        this.startTimeToNode[this.startTime[node]] = node;
        for (var adjacentNode : this.adjacencyList.get(node)) {
            enqueue.enqueue(adjacentNode, this.startTime[adjacentNode] == -1 ? VisitType.VISIT : VisitType.SKIP);
        }
        return VisitType.VISIT;
    }

    @Override
    public void exitNode(int childNode, int parentNode, VisitType result) {
        if (result == VisitType.VISIT) {
            if (this.lowTime[childNode] != -1 && this.lowTime[parentNode] > this.lowTime[childNode])
                this.lowTime[parentNode] = this.lowTime[childNode];
        }
        if (this.startTime[childNode] != -1 && this.lowTime[parentNode] > this.startTime[childNode])
            this.lowTime[parentNode] = this.startTime[childNode];
    }

    @Override
    public void exit(int node) {
        if (this.lowTime[node] != this.startTime[node] || this.startTime[node] == 0) {
            return;
        }
        int x = this.random.nextInt(this.globalStartTime - this.startTime[node]) + this.startTime[node];
        int y = this.random.nextInt(this.startTime[node]);
        int v = this.startTimeToNode[x];
        int w = this.startTimeToNode[y];
        if (v < 0 || w < 0)
            throw new IllegalStateException();
        this.adjacencyList.get(v).add(w);
        this.lowTime[node] = y;
    }
}

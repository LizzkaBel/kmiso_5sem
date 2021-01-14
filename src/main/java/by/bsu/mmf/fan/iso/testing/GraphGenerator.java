package by.bsu.mmf.fan.iso.testing;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.Edge;
import by.bsu.mmf.fan.iso.model.EdgeIntegerLengthData;
import by.bsu.mmf.fan.iso.model.EdgeIntegerWeightData;
import by.bsu.mmf.fan.iso.utils.StronglyConnectedGraphGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GraphGenerator {
    private final Random random;

    public GraphGenerator(Random random) {
        this.random = random;
    }


    public <TData> BasicGraph<Integer, TData> generateRandomDenseGraph(
            int numberOfVertices,
            double edgeProbability,
            Supplier<TData> dataGenerator,
            boolean isDirected,
            boolean isStronglyConnected
    ) {
        List<Edge<Integer, TData>> edges = new ArrayList<>();
        var requiredAdjacencyList = isStronglyConnected
                ? StronglyConnectedGraphGenerator.generateEdges(numberOfVertices, this.random)
                : null;
        for (int i = 0; i < numberOfVertices; i++) {
            var existingEdges = new HashSet<>(isStronglyConnected ? requiredAdjacencyList.get(i) : List.of());
            for (int j = isDirected ? 0 : i + 1; j < numberOfVertices; j++) {
                if (i == j) continue;
                if (
                        existingEdges.contains(j)
                                || (this.random.nextDouble() < edgeProbability)
                                || (!isDirected && this.random.nextDouble() < edgeProbability)
                ) {
                    edges.add(new Edge<>(i, j, dataGenerator.get()));
                }
            }
        }
        return BasicGraph.createIntegerVertexGraph(
                numberOfVertices,
                edges,
                isDirected
        );
    }

    public <TData> BasicGraph<Integer, TData> generateRandomSparseGraph(
            int numberOfVertices,
            int numberOfEdges,
            Supplier<TData> dataGenerator,
            boolean isDirected,
            boolean isStronglyConnected
    ) {
        List<Edge<Integer, TData>> edges = new ArrayList<>();
        var currentAdjacencyList = new ArrayList<HashSet<Integer>>();
        if (isStronglyConnected) {
            var adjacencyList = StronglyConnectedGraphGenerator.generateEdges(numberOfVertices, this.random);
            for (var i = 0; i < adjacencyList.size(); i++)
                for (var j : adjacencyList.get(i)) {
                    edges.add(new Edge<>(i, j, dataGenerator.get()));
                }
        }
        for (int i = 0; i < numberOfVertices; i++) {
            currentAdjacencyList.add(new HashSet<>());
        }
        while (edges.size() < numberOfEdges) {
            var i = this.random.nextInt(numberOfVertices);
            var j = this.random.nextInt(numberOfVertices);
            if (i == j)
                continue;
            if (currentAdjacencyList.get(i).contains(j) || (!isDirected && currentAdjacencyList.get(j).contains(i)))
                continue;
            currentAdjacencyList.get(i).add(j);
        }
        return BasicGraph.createIntegerVertexGraph(
                numberOfVertices,
                edges,
                isDirected
        );
    }

    public BasicGraph<Integer, EdgeIntegerLengthData> generateRandomDenseGraphWithLength(
            int numberOfVertices,
            double edgeProbability,
            boolean isDirected,
            int lowerEdgeLengthBound,
            int upperEdgeLengthBound,
            boolean isStronglyConnected
    ) {
        return this.generateRandomDenseGraph(
                numberOfVertices,
                edgeProbability,
                () -> new EdgeIntegerLengthData(
                        lowerEdgeLengthBound +
                                this.random.nextInt(upperEdgeLengthBound - lowerEdgeLengthBound)
                ),
                isDirected,
                isStronglyConnected
        );
    }

    public BasicGraph<Integer, EdgeIntegerWeightData> generateRandomDenseGraphWithWeight(
            int numberOfVertices,
            double edgeProbability,
            boolean isDirected,
            int lowerEdgeLengthBound,
            int upperEdgeLengthBound,
            boolean isStronglyConnected
    ) {
        return this.generateRandomDenseGraph(
                numberOfVertices,
                edgeProbability,
                () -> new EdgeIntegerWeightData(
                        lowerEdgeLengthBound +
                                this.random.nextInt(upperEdgeLengthBound - lowerEdgeLengthBound)
                ),
                isDirected,
                isStronglyConnected
        );
    }

    public BasicGraph<Integer, EdgeIntegerLengthData> generateRandomSparseGraphWithLength(
            int numberOfVertices,
            int numberOfEdges,
            boolean isDirected,
            int lowerEdgeLengthBound,
            int upperEdgeLengthBound,
            boolean isStronglyConnected
    ) {
        return this.generateRandomSparseGraph(
                numberOfVertices,
                numberOfEdges,
                () -> new EdgeIntegerLengthData(
                        lowerEdgeLengthBound +
                                this.random.nextInt(upperEdgeLengthBound - lowerEdgeLengthBound)
                ),
                isDirected,
                isStronglyConnected
        );
    }

    public BasicGraph<Integer, EdgeIntegerWeightData> generateRandomSparseGraphWithWeight(
            int numberOfVertices,
            int numberOfEdges,
            boolean isDirected,
            int lowerEdgeLengthBound,
            int upperEdgeLengthBound,
            boolean isStronglyConnected
    ) {
        return this.generateRandomSparseGraph(
                numberOfVertices,
                numberOfEdges,
                () -> new EdgeIntegerWeightData(
                        lowerEdgeLengthBound +
                                this.random.nextInt(upperEdgeLengthBound - lowerEdgeLengthBound)
                ),
                isDirected,
                isStronglyConnected
        );
    }
}


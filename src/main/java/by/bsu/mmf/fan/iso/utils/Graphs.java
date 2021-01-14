package by.bsu.mmf.fan.iso.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Miscellaneous graph utilities.
 */
public class Graphs {
    /**
     * Shuffles nodes in the graph represented by the adjacency list. The topology of the graph is preserved.
     *
     * @param adjacencyList the adjacency list that represents the graph.
     * @return a new graph, where the indexes of vertices are shuffled.
     */
    @NotNull
    public static List<List<Integer>> shuffleAdjacencyList(List<List<Integer>> adjacencyList) {
        int numberOfVertices = adjacencyList.size();
        var substitution = IntStream.range(0, numberOfVertices).boxed().collect(Collectors.toList());
        Collections.shuffle(substitution);

        var shuffledAdjacency = new ArrayList<List<Integer>>();
        for (int i = 0; i < numberOfVertices; i++) {
            shuffledAdjacency.add(new ArrayList<>());
        }
        for (int i = 0; i < numberOfVertices; i++) {
            shuffledAdjacency.get(substitution.get(i)).addAll(
                    adjacencyList.get(i).stream().map(substitution::get).collect(Collectors.toList())
            );
        }
        return shuffledAdjacency;
    }
}

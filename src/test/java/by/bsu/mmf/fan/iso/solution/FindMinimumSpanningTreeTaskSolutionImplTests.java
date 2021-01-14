package by.bsu.mmf.fan.iso.solution;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.graphs.Edge;
import by.bsu.mmf.fan.iso.model.EdgeIntegerWeightData;
import by.bsu.mmf.fan.iso.testing.GraphGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class FindMinimumSpanningTreeTaskSolutionImplTests {

    private final FindMinimumSpanningTreeTaskSolutionImpl solution = new FindMinimumSpanningTreeTaskSolutionImpl();

    @ParameterizedTest
    @MethodSource("smallSpanningTreeParameters")
    public void smallSpanningTree(ContiguousIntegerBasicGraph<EdgeIntegerWeightData> graph, int minimumWeight) {
        var actual = this.solution.findMinimumSpanningTree(graph);

        var visitedSet = actual.stream()
                .flatMap(edge -> Stream.of(edge.getSourceVertex(), edge.getTargetVertex()))
                .collect(Collectors.toSet());
        Assertions.assertThat(visitedSet)
                .containsExactlyInAnyOrder(graph.getVertices().toArray(Integer[]::new));

        var cost = actual
                .stream()
                .mapToInt(x -> graph.lookupEdge(x.getSourceVertex(), x.getTargetVertex()).getData().getWeight())
                .sum();
        Assertions.assertThat(cost).isEqualTo(minimumWeight);
    }

    public static Stream<Object[]> smallSpanningTreeParameters() {
        return Stream.of(
                new Object[]{
                        BasicGraph.createIntegerVertexGraph(
                                3,
                                List.of(
                                        new Edge<>(0, 1, new EdgeIntegerWeightData(8)),
                                        new Edge<>(1, 2, new EdgeIntegerWeightData(4)),
                                        new Edge<>(2, 0, new EdgeIntegerWeightData(5))
                                ),
                                false
                        ),
                        9
                },
                new Object[]{
                        BasicGraph.createIntegerVertexGraph(
                                6,
                                List.of(
                                        new Edge<>(0, 1, new EdgeIntegerWeightData(1)),
                                        new Edge<>(0, 3, new EdgeIntegerWeightData(4)),
                                        new Edge<>(0, 4, new EdgeIntegerWeightData(3)),
                                        new Edge<>(1, 3, new EdgeIntegerWeightData(4)),
                                        new Edge<>(1, 4, new EdgeIntegerWeightData(2)),
                                        new Edge<>(3, 4, new EdgeIntegerWeightData(4)),
                                        new Edge<>(2, 4, new EdgeIntegerWeightData(4)),
                                        new Edge<>(2, 5, new EdgeIntegerWeightData(5)),
                                        new Edge<>(4, 5, new EdgeIntegerWeightData(7))
                                ),
                                false
                        ),
                        16
                }
        );
    }

    @ParameterizedTest
    @MethodSource("bigSpanningTreeParameters")
    public void bigSpanningTree(ContiguousIntegerBasicGraph<EdgeIntegerWeightData> graph) {

        var actual = org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(
                Duration.ofMillis(1000),
                () -> this.solution.findMinimumSpanningTree(graph)
        );
        var visitedSet = actual.stream()
                .flatMap(edge -> Stream.of(edge.getSourceVertex(), edge.getTargetVertex()))
                .collect(Collectors.toSet());

        Assertions.assertThat(visitedSet)
                .containsExactlyInAnyOrder(graph.getVertices().toArray(Integer[]::new));
    }

    public static Stream<Object[]> bigSpanningTreeParameters() {
        GraphGenerator generator = new GraphGenerator(new Random());
        return IntStream.of(10, 100, 500, 800, 800, 900, 1000, 1000).mapToObj(
                length -> new Object[]{
                        generator.generateRandomDenseGraphWithWeight(
                                length,
                                0.9,
                                true,
                                0,
                                10000,
                                true
                        )
                }
        );
    }

}

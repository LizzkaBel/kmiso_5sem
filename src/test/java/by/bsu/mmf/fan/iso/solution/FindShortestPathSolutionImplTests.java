package by.bsu.mmf.fan.iso.solution;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.graphs.Edge;
import by.bsu.mmf.fan.iso.model.EdgeIntegerLengthData;
import by.bsu.mmf.fan.iso.testing.GraphGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class FindShortestPathSolutionImplTests {

    private final FindShortestPathSolutionImpl solution = new FindShortestPathSolutionImpl();


    @Test
    public void basicShortestPath() {
        var graph = BasicGraph.createIntegerVertexGraph(
                3,
                List.of(
                        new Edge<>(0, 1, new EdgeIntegerLengthData(8)),
                        new Edge<>(1, 2, new EdgeIntegerLengthData(4)),
                        new Edge<>(2, 0, new EdgeIntegerLengthData(5))
                ),
                true
        );
        var actual = this.solution.getShortestPath(graph, 0, 1);
        Assertions.assertThat(actual).containsExactly(0, 1);
    }

    @Test
    public void noDirectPathShortestPath() {
        var graph = BasicGraph.createIntegerVertexGraph(
                3,
                List.of(
                        new Edge<>(0, 1, new EdgeIntegerLengthData(8)),
                        new Edge<>(1, 2, new EdgeIntegerLengthData(4))
                ),
                true
        );
        var actual = this.solution.getShortestPath(graph, 0, 2);
        Assertions.assertThat(actual).containsExactly(0, 1, 2);
    }

    @Test
    public void smallGraphShortestPath() {
        var graph = BasicGraph.createIntegerVertexGraph(
                6,
                List.of(
                        new Edge<>(0, 1, new EdgeIntegerLengthData(4)),
                        new Edge<>(0, 2, new EdgeIntegerLengthData(7)),
                        new Edge<>(0, 3, new EdgeIntegerLengthData(3)),
                        new Edge<>(1, 2, new EdgeIntegerLengthData(3)),
                        new Edge<>(1, 4, new EdgeIntegerLengthData(3)),
                        new Edge<>(3, 4, new EdgeIntegerLengthData(3)),
                        new Edge<>(2, 5, new EdgeIntegerLengthData(2)),
                        new Edge<>(4, 5, new EdgeIntegerLengthData(2))
                ),
                true
        );

        var actual = this.solution.getShortestPath(graph, 0, 5);

        Assertions.assertThat(actual).containsExactly(0, 3, 4, 5);
    }

    @ParameterizedTest
    @MethodSource("bigGraphShortestPathParameters")
    public void bigGraphShortestPath(
            ContiguousIntegerBasicGraph<EdgeIntegerLengthData> graph,
            Integer fromVertex,
            Integer toVertex
    ) {

        var actual = org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(
                Duration.ofMillis(1000),
                () -> this.solution.getShortestPath(graph, fromVertex, toVertex)
        );

    }

    public static Stream<Object[]> bigGraphShortestPathParameters() {
        GraphGenerator generator = new GraphGenerator(new Random());
        return Stream.of(
                new Object[]{50, 20, 5, 10},
                new Object[]{1000, 16, 7, 8},
                new Object[]{100000, 100000, 100, 646}
        ).map(
                params -> new Object[]{
                        generator.generateRandomSparseGraphWithLength(
                                (Integer) params[0],
                                (Integer) params[1],
                                true,
                                0,
                                10000,
                                true
                        ),
                        params[2],
                        params[3]
                }
        );
    }
}
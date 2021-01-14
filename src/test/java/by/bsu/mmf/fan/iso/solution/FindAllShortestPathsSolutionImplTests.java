package by.bsu.mmf.fan.iso.solution;

import by.bsu.mmf.fan.iso.graphs.BasicGraph;
import by.bsu.mmf.fan.iso.graphs.ContiguousIntegerBasicGraph;
import by.bsu.mmf.fan.iso.graphs.Edge;
import by.bsu.mmf.fan.iso.model.EdgeIntegerLengthData;
import by.bsu.mmf.fan.iso.structs.Tuple2;
import by.bsu.mmf.fan.iso.tasks.FindAllShortestPathsSolution;
import by.bsu.mmf.fan.iso.testing.GraphGenerator;
import by.bsu.mmf.fan.iso.utils.ZipUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindAllShortestPathsSolutionImplTests {

    private final FindAllShortestPathsSolution solution = new FindAllShortestPathsSolutionImpl();


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
        this.solution.performPreprocessing(graph);
        var actual = this.solution.getShortestPath(0, 1);
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
        this.solution.performPreprocessing(graph);
        var actual = this.solution.getShortestPath(0, 2);
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

        this.solution.performPreprocessing(graph);
        var actual = this.solution.getShortestPath(0, 5);
        Assertions.assertThat(actual).containsExactly(0, 3, 4, 5);
    }

    @ParameterizedTest
    @MethodSource("bigGraphShortestPathParameters")
    public void bigGraphShortestPath(
            ContiguousIntegerBasicGraph<EdgeIntegerLengthData> graph,
            List<Tuple2<Integer, Integer>> shortestPathQueries
    ) {
        org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(
                Duration.ofMillis(1000),
                () -> {
                    this.solution.performPreprocessing(graph);
                    for (var query : shortestPathQueries) {
                        this.solution.getShortestPath(query.getFirst(), query.getSecond());
                    }
                }
        );

    }

    public static Stream<Object[]> bigGraphShortestPathParameters() {
        Random random = new Random();
        GraphGenerator generator = new GraphGenerator(random);
        return Stream.of(
                new Object[]{50, 0.8, 10000},
                new Object[]{100, 0.8, 10000},
                new Object[]{100, 0.9, 10000},
                new Object[]{100, 1.0, 10000}
        ).map(
                params -> new Object[]{
                        generator.generateRandomDenseGraphWithLength(
                                (Integer) params[0],
                                (Double) params[1],
                                true,
                                0,
                                10000,
                                true
                        ),
                        ZipUtils.zip(
                                random.ints(0, (Integer) params[0]).boxed(),
                                random.ints(0, (Integer) params[0]).boxed(),
                                Tuple2::new
                        )
                                .limit((Integer) params[2])
                                .collect(Collectors.toList())
                }
        );
    }
}
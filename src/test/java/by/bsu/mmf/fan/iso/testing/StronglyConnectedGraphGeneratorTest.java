package by.bsu.mmf.fan.iso.testing;

import by.bsu.mmf.fan.iso.utils.StronglyConnectedComponentFinder;
import by.bsu.mmf.fan.iso.utils.StronglyConnectedGraphGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

@Disabled
class StronglyConnectedGraphGeneratorTest {
    private final Random random = new Random();

    @ParameterizedTest
    @MethodSource("checkGeneratedGraphIsStronglyConnectedParameters")
    public void checkGeneratedGraphIsStronglyConnected(int n) {
        var edges = StronglyConnectedGraphGenerator.generateEdges(n, this.random);
        Assertions.assertEquals(1, StronglyConnectedComponentFinder.getComponents(edges).size());
    }

    public static Stream<Object[]> checkGeneratedGraphIsStronglyConnectedParameters() {
        return Stream.of(
                new Object[]{3},
                new Object[]{5},
                new Object[]{10},
                new Object[]{50},
                new Object[]{1000}
        ).flatMap(x -> Stream.generate(() -> x).limit(100));
    }
}
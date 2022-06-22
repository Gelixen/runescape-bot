package lt.kslipaitis.osrs;

import lt.kslipaitis.osrs.util.OptionsUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptionsUtilsTest {

    private OptionsUtils optionsUtils;

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(Arguments.of(0, 2),
                         Arguments.of(1, 3),
                         Arguments.of(2, 5),
                         Arguments.of(3, 6),
                         Arguments.of(4, 8),
                         Arguments.of(5, 9),
                         Arguments.of(6, 11),
                         Arguments.of(7, 12),
                         Arguments.of(8, 14),
                         Arguments.of(9, 15));
    }

    private static Stream<Arguments> provideStringsForIsBlank2() {
        return Stream.of(Arguments.of(0, 42),
                         Arguments.of(1, 64),
                         Arguments.of(2, 87),
                         Arguments.of(3, 109),
                         Arguments.of(4, 132),
                         Arguments.of(5, 154));
    }

    @BeforeEach
    void setUp() {
        optionsUtils = new OptionsUtils(null, null);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void name(int options, int expectedGap) {
        int actualGap = optionsUtils.calculateTotalVariableGapBetweenOptions(options);
        assertEquals(expectedGap, actualGap);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank2")
    void name2(int options, int expectedOffset) {
        int actualOffset = optionsUtils.getNthOptionOffset(options);
        assertEquals(expectedOffset, actualOffset);
    }
}
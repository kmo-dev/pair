package kmo.utils.pair;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PairTest {

    @Test
    void whenOfGivenNullKeyThenThrowException() {
        assertThrows(NullPointerException.class, () -> Pair.of(null, "value"));
    }

    @Test
    void whenOfGivenNullValueThenCreateIncompletePair() {
        final var result = Pair.of("aKey", null);

        assertThat(result, is(not(nullValue())));

        assertThat(result.getKey().isPresent(), is(true));
        assertThat(result.getKey().get(), is("aKey"));

        assertThat(result.getValue().isPresent(), is(false));
        assertThrows(NoSuchElementException.class, () -> result.getValue().get());

        assertThat(result.isComplete(), is(false));
        assertThat(result.isIncomplete(), is(true));

        assertThat(result.toMapEntry(), is(instanceOf(Map.Entry.class)));
        assertThat(result.toString(), is("Pair{key=aKey, value=null}"));
    }

    @Test
    void whenOfGivenKeyAndValueThenCreateCompletePair() {
        final var result = Pair.of("aKey", "aValue");

        assertThat(result, is(not(nullValue())));

        assertThat(result.getKey().isPresent(), is(true));
        assertThat(result.getKey().get(), is("aKey"));

        assertThat(result.getValue().isPresent(), is(true));
        assertThat(result.getValue().get(), is("aValue"));

        assertThat(result.isComplete(), is(true));
        assertThat(result.isIncomplete(), is(false));

        assertThat(result.toMapEntry(), is(instanceOf(Map.Entry.class)));
        assertThat(result.toString(), is("Pair{key=aKey, value=aValue}"));
    }

    @Test
    void whenSortGivenUnsortedPairsThenReturnSortedPairsByKeyThenValue() {
        final var first = Pair.of("A", "2");
        final var second = Pair.of("B", "2");
        final var third = Pair.of("B", "3");
        final var fourth = Pair.of("C", "1");

        final var result = Stream.of(second, third, fourth, first)
                .sorted()
                .collect(Collectors.toList());

        assertThat(result, contains(first, second, third, fourth));
    }
}
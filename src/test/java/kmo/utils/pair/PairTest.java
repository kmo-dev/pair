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
    void whenOfGivenNullValueThenThrowException() {
        assertThrows(NullPointerException.class, () -> Pair.of("key", null));
    }

    @Test
    void whenOfNullableGivenNullKeyThenCreateIncompletePair() {
        final var result = Pair.ofNullable(null, "aValue");

        assertThat(result, is(not(nullValue())));

        assertThat(result.getKey().isPresent(), is(false));
        assertThrows(NoSuchElementException.class, () -> result.getKey().get());

        assertThat(result.getValue().isPresent(), is(true));
        assertThat(result.getValue().get(), is("aValue"));

        assertThat(result.isComplete(), is(false));
        assertThat(result.isIncomplete(), is(true));

        assertThat(result.toMapEntry(), is(instanceOf(Map.Entry.class)));
        assertThat(result.toString(), is("Pair{key=null, value=aValue}"));
    }

    @Test
    void whenOfNullableGivenNullValueThenCreateIncompletePair() {
        final var result = Pair.ofNullable("aKey", null);

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
        final var fifth = Pair.ofNullable("C", null);
        final var sixth = Pair.ofNullable("D", null);
        final var seventh = Pair.ofNullable(null, "1");
        final var eighth = Pair.ofNullable(null, "2");
        final var ninth = Pair.ofNullable(null, null);

        final var result = Stream.of(second, third, fourth, first, sixth, seventh, fifth, ninth, eighth)
                .sorted()
                .collect(Collectors.toList());

        assertThat(result, contains(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth));
    }

    @Test
    void whenEqualsGivenSameInputThenEqualsIsTrue() {
        final var pair01 = Pair.ofNullable(null, null);
        final var pair02 = Pair.ofNullable("aKey", "aValue");
        final var pair03 = Pair.ofNullable("aKey", null);
        final var pair04 = Pair.ofNullable(null, "aValue");
        final var pair05 = Pair.ofNullable("aKey", "aValue");
        final var pair06 = Pair.ofNullable(1, 2);
        final var pair07 = Pair.ofNullable("aKey", "1");
        final var pair08 = Pair.ofNullable("aKey", 1);
        final var pair09 = Pair.ofNullable("aKey", 2);
        final var pair10 = Pair.ofNullable("1", "aValue");

        assertThat(pair01, is(Pair.ofNullable(null, null)));
        assertThat(pair02, is(Pair.ofNullable("aKey", "aValue")));
        assertThat(pair03, is(Pair.ofNullable("aKey", null)));
        assertThat(pair04, is(Pair.ofNullable(null, "aValue")));
        assertThat(pair05, is(Pair.ofNullable("aKey", "aValue")));
        assertThat(pair06, is(Pair.ofNullable(1, 2)));
        assertThat(pair07, is(not(Pair.ofNullable("aKey", 1))));
        assertThat(pair08, is(not(Pair.ofNullable("aKey", "1"))));
        assertThat(pair09, is(not(Pair.ofNullable(2, "aKey"))));
        assertThat(pair10, is(not(Pair.ofNullable("aKey", "1"))));
    }

    @Test
    void whenEqualsGivenDifferentInputThenEqualsIsFalse() {
        final var pair07 = Pair.ofNullable("aKey", "1");
        final var pair08 = Pair.ofNullable("aKey", 1);
        final var pair09 = Pair.ofNullable("aKey", 2);
        final var pair10 = Pair.ofNullable("1", "aValue");

        assertThat(pair07, is(not(Pair.ofNullable("aKey", 1))));
        assertThat(pair08, is(not(Pair.ofNullable("aKey", "1"))));
        assertThat(pair09, is(not(Pair.ofNullable(2, "aKey"))));
        assertThat(pair10, is(not(Pair.ofNullable("aKey", "1"))));
    }

}
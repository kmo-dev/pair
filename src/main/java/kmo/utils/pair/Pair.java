package kmo.utils.pair;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Pair<K extends Comparable<K>, V extends Comparable<V>> implements Serializable, Comparable<Pair<K, V>> {

    private static final long serialVersionUID = 1L;

    private final K key;
    private final V value;

    private Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    public static <K extends Comparable<K>, V extends Comparable<V>> Pair<K, V> of(@Nonnull final K key, @Nonnull final V value) {
        return new Pair<>(Objects.requireNonNull(key), Objects.requireNonNull(value));
    }

    public static <K extends Comparable<K>, V extends Comparable<V>> Pair<K, V> ofNullable(final K key,
                                                                                           final V value) {
        return new Pair<>(key, value);
    }

    public Optional<K> getKey() {
        return Optional.ofNullable(key);
    }

    public Optional<V> getValue() {
        return Optional.ofNullable(value);
    }

    public boolean isComplete() {
        return key != null && value != null;
    }

    public boolean isIncomplete() {
        return key == null || value == null;
    }

    public boolean isEmpty() {
        return key == null && value == null;
    }

    public Map.Entry<K, V> toMapEntry() {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @Override
    public int compareTo(@Nonnull final Pair<K, V> pair) {
        if (isEmpty() && pair.isEmpty()) {
            return 1;
        }

        if (isEmpty()) {
            return 1;
        }

        if (getKey().isEmpty() && pair.getKey().isPresent()) {
            return 1;
        }

        if (getKey().isEmpty() && pair.getKey().isEmpty()) {
            if (getValue().isPresent() && pair.getValue().isPresent()) {
                return value.compareTo(pair.value);
            }
            if (getValue().isPresent() && pair.getValue().isEmpty()) {
                return -1;
            }
        }
        if (getKey().isPresent() && pair.getKey().isEmpty()) {
            return -1;
        }
        if (getKey().isPresent() && pair.getKey().isPresent()) {
            if (getValue().isPresent() && pair.getValue().isPresent()) {
                final var comparedKeys = key.compareTo(pair.key);
                return comparedKeys == 0 ? value.compareTo(pair.value) : comparedKeys;
            }
            if (getValue().isPresent() && pair.getValue().isEmpty()) {
                return -1;
            }
            if (getValue().isEmpty() && pair.getValue().isEmpty()) {
                return key.compareTo(pair.key);
            }
        }

        return 1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) &&
                Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

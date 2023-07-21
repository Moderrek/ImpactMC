package pl.impact.lib.api.container;

import java.util.HashMap;
import java.util.Random;

/**
 * @param <T> Values type
 */
public class WeightedList<T> extends HashMap<T, Integer> {

    private int totalWeight;

    /**
     * Puts new weighted value
     *
     * @param a The value
     * @param b The weight
     * @return The weight
     */
    @Override
    public Integer put(T a, Integer b) {
        Integer newValueWeight = super.put(a, b);
        totalWeight = values().stream().mapToInt(Integer::intValue).sum();
        return newValueWeight;
    }

    /**
     * Gets random value by given seed
     *
     * @param seed The randomizer seed
     * @return The seed based random value
     */
    public T get(long seed) {
        return get(new Random(seed));
    }

    /**
     * Gets random value by given randomizer
     *
     * @param random The randomizer
     * @return The random value
     */
    public T get(Random random) {
        if (totalWeight <= 0) return null;
        int i = random.nextInt(totalWeight);
        for (Entry<T, Integer> entry : entrySet()) {
            i -= entry.getValue();
            if (i < 0) return entry.getKey();
        }
        return null;
    }

    /**
     * Gets random value by random seed
     *
     * @return The random value
     */
    public T get() {
        return get(new Random());
    }

    /**
     * Returns total objects weight.
     *
     * @return The total objects weight
     */
    public int getTotalWeight() {
        return totalWeight;
    }
}
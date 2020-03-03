package src.utils;

import java.util.List;

/**
 * Utility for storing pairs of elements
 * @param <F> First Pair Element
 * @param <S> Second Pair Element
 */

public class Pair<F, S> {

    private final F first;
    private final S second;

    /**
     *
     * @param p1 the first element of the pair
     * @param p2 the second element of the pair
     */
    public Pair(F p1, S p2) {
        assert p1 != null;
        assert p2 != null;

        this.first = p1;
        this.second = p2;
    }

    public int getFirstPair() {
        return Integer.parseInt(String.valueOf(first));
    }

    public int getSecondPair() {
        return Integer.parseInt(String.valueOf(second));
    }

    /**
     * findPair searches based on first element in an Integer pair only
     * @param toSearch A list of pairs
     * @param firstElement the first element of the pair. Must be an Integer
     * @return the pair containing the firstElement
     * @throws IllegalArgumentException if the firstElement is not an integer
     */
    public Pair<F, S> findPair(List<Pair<F, S>> toSearch, F firstElement) throws IllegalArgumentException{
        int searchFor;
        if(firstElement instanceof Integer) {
            searchFor = Integer.parseInt(String.valueOf(firstElement));
        }else {
            throw new IllegalArgumentException();
        }
        for(Pair<F, S> f : toSearch) {
            if(f.getFirstPair() == searchFor) {
                return f;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return("(" + first + ", " + second + ")");
    }
}

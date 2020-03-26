package utils;

import java.util.ArrayList;
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

    /**
     *
     * @return the first element of the pair
     */
    public F getFirst() {
        return first;
    }

    /**
     *
     * @return the second element of the pair
     */
    public S getSecond() {
        return second;
    }

    /**
     * @param toSearch A list of pairs
     * @param searchFor the pair being searched for
     * @return true if the list contains the pair being searched, false otherwise
     */
    public boolean pairExists(List<Pair<F, S>> toSearch, Pair<F, S> searchFor) {
        for(Pair<F, S> current : toSearch) {
            if(current.getFirst() == searchFor.getFirst()
                && current.getSecond() == searchFor.getSecond()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param toSearch A list of pairs
     * @param searchFor the first element of the pair being searched for
     * @return the pair, if it exists, null otherwise
     */
    public List<Pair<F, S>> findPair(List<Pair<F, S>> toSearch, F searchFor) {
        List<Pair<F, S>> allMatches = new ArrayList<>();
        for(Pair<F, S> f : toSearch) {
            if(f.getFirst() == searchFor) {
                allMatches.add(f);
            }
        }
        return allMatches;
    }

    /**
     *
     * @return a string representation of the pair
     */
    @Override
    public String toString() {
        return("(" + first + ", " + second + ")");
    }
}

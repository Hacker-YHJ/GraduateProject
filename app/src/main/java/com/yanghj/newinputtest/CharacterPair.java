package com.yanghj.newinputtest;

import android.util.Pair;

/**
 * Created by yanghj on 4/19/15.
 */
public class CharacterPair extends Pair<String, Float> implements Comparable<CharacterPair> {
    /**
     * Constructor for a CharacterPair.
     *
     * @param first  the Character
     * @param second the frequency
     */
    public CharacterPair(String first, Float second) {
        super(first, second);
    }

    @Override
    public int compareTo(CharacterPair another) {
        return this.second.compareTo(another.second);
    }
}

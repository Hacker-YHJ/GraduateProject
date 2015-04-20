package com.yanghj.newinputtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yanghj on 4/19/15.
 */
public class CharacterList extends HashMap<String, List<CharacterPair>> {
    public CharacterList() {
        super();
    }

    public boolean storeLine(String line) {
        String[] parsedResult;
        parsedResult = line.split(" ");
        // ['the character', 'the frequency', 'not used', 'the Pinyin']

        List<CharacterPair> targetList;
        if (!this.containsKey(parsedResult[3])) {
            targetList = new ArrayList<>();
            this.put(parsedResult[3], targetList);
        }
        else {
            targetList = this.get(parsedResult[3]);
        }
        return targetList.add(new CharacterPair(parsedResult[0], Float.parseFloat(parsedResult[1])));
    }

    public void sortAll() {
        for (List<CharacterPair> list : this.values()) {
            Collections.sort(list, Collections.reverseOrder());
        }
    }
}

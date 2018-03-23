package storagewords;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    private String word; //Foreign word
    private List<String> meanings; //Native meanings
    private int count;

    public Pair(String word, List<String> meanings) {
        this.word = word;
        this.meanings = meanings;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "word='" + word + '\'' +
                ", meanings=" + meanings +
                ", count=" + count +
                '}';
    }

    public String getNextMeaning() {
        if(count < meanings.size()) {
            String nextMeaning = meanings.get(count);
            ++count;
            return nextMeaning;
        } else {
            count = 0; //If we is going though this list second time
            return null;
        }
    }

    public void setMeanings(ArrayList<String> meanings) {
        this.meanings = meanings;
    }
}

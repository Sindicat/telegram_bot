package com.github.sindicat.storagewords;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    private String word; //Foreign word
    private List<String> meanings; //Native meanings
    private int numMeanings;
    private int count;

    public int getNumMeanings() {
        return numMeanings;
    }

    public Pair(String word, List<String> meanings) {
        this.word = word;
        this.meanings = meanings;
        this.numMeanings = meanings.size();

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
                ", numMeanings=" + numMeanings +
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

    public String getMeaning(int pos) {
        return meanings.get(pos);
    }

    public void setMeanings(ArrayList<String> meanings) {
        this.meanings = meanings;
    }
}

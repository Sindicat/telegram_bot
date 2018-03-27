package com.github.sindicat.storagewords;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    private String unknownWord; //Foreign word
    private List<Word> meanings; //Native meanings
    private int numMeanings;

    public int getNumMeanings() {
        return numMeanings;
    }

    public Pair(String unknownWord, List<String> meanings) {
        this.unknownWord = unknownWord;
        this.numMeanings = meanings.size();
        this.meanings = new ArrayList<>();
        for (String str : meanings) {
            this.meanings.add(new Word(str));
        }
    }

    public String getUnknownWord() {
        return unknownWord;
    }

    public void setUnknownWord(String unknownWord) {
        this.unknownWord = unknownWord;
    }

    public List<Word> getMeanings() {
        return meanings;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "unknownWord='" + unknownWord + '\'' +
                ", meanings=" + meanings +
                ", numMeanings=" + numMeanings +
                '}';
    }

    public Word getWord(int pos) {
        return meanings.get(pos);
    }

    public void setMeanings(ArrayList<Word> meanings) {
        this.meanings = meanings;
    }
}

class Word {
    @Override
    public String toString() {
        return "Word{" +
                "meaning='" + meaning + '\'' +
                '}';
    }

    private String meaning;
    private boolean isGuessed;
    Word(String meaning) {
        this.meaning=meaning;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public boolean isGuessed() {
        return isGuessed;
    }

    public void setGuessed(boolean guessed) {
        isGuessed = guessed;
    }
}
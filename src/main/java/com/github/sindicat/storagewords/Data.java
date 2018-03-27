package com.github.sindicat.storagewords;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {

    private static final String inputfileName = "src\\main\\resources\\data\\text\\wordsEN.txt";
    private static String outputFileName = "src\\main\\resources\\data\\json\\";
    private static ArrayList<Pair> readListPairs;

    private static ArrayList<String> getListWords() { //Return list unhandled strings
        BufferedReader in;
        ArrayList<String> listWords = null;
        try {
            in = new BufferedReader(new FileReader(inputfileName));
            String str = in.readLine();
            listWords = new ArrayList<>();
            while (str != null) {
                listWords.add(str);
                str = in.readLine();
            }
        } catch (IOException e) {
            System.err.println("No such file or direcory");
        }
        return listWords;
    }

    private static ArrayList<Pair> getListPairs() {
        ArrayList<Pair> listPairs = new ArrayList<>();
        ArrayList<String> listWords = getListWords();
        for (int i = 0; i < listWords.size(); i++) {
            String[] arrStr = listWords.get(i).split("-");
            List<String> list = Arrays.asList(arrStr);
            String engWord = list.get(0).substring(0, list.get(0).length() - 1); //Delete space after first word
            System.out.println(listWords.get(i));
            String transWords = list.get(1).substring(1);
            Pair pair = new Pair(engWord, Arrays.asList(transWords.split(", ")));
            listPairs.add(pair);
        }
        return listPairs;
    }

    public static void savePairsToJsonFile(String jsonFileName) {
        //readListPairs = getListPairs();
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(
                    new FileWriter(outputFileName + jsonFileName)));
            Gson gson = new Gson();
            gson.toJson(readListPairs, out); //save to JSON file
        } catch (IOException e) {
            System.err.println("No such output file or directory");
        } finally {
            out.close();
        }
    }

    public static ArrayList<Pair> readPairsFromJsonFile(String fileName) throws FileNotFoundException {
        ArrayList<Pair> listPair;
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(outputFileName + fileName));
            Gson gson = new Gson();
            listPair = gson.fromJson(in, new TypeToken<ArrayList<Pair>>() {
            }.getType());
        } catch (FileNotFoundException x) {
            throw new FileNotFoundException();
        }
        return listPair;
    }

    public static void main(String[] args) {
        List<Pair> pairsList = getListPairs();
        Integer numJsonFiles = pairsList.size()/20+1;
        int counter=0;
        Integer name=1;
        readListPairs = new ArrayList<>();
        while(numJsonFiles>0) {
                readListPairs.clear();
            for (int i = 0; i < 10 ; i++,counter++) {
                if (counter < pairsList.size()) {
                    readListPairs.add(pairsList.get(i));
                } else {
                    break;
                }
            if (!readListPairs.isEmpty()) {
                    savePairsToJsonFile(name.toString()+".json");
                    ++name;
            }
            }
            --numJsonFiles;
        }

    }
}

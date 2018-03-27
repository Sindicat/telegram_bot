package com.github.sindicat.storagewords;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Data {

    private static final String inputfileName = "src\\main\\resources\\data\\text\\1.txt";
    private static String outputFileName = "src\\main\\resources\\data\\json\\";
    private static ArrayList<Pair> readListPairs;

    private static ArrayList <String> getListWords() { //Return list unhandled strings
        BufferedReader in;
        ArrayList<String> listWords=null;
        try {
            in = new BufferedReader( new FileReader(inputfileName));
            String str = in.readLine();
            listWords = new ArrayList<>();
            while(str!=null) {
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
        for (int i=0; i < listWords.size(); i++)
        {
            String [] arrStr = listWords.get(i).split("-");
            List<String> list = Arrays.asList(arrStr);
            String engWord =  list.get(0).substring(0,list.get(0).length()-1); //Delete space after first word
            String transWords = list.get(1).substring(1);
            Pair pair = new Pair(engWord, Arrays.asList(transWords.split(", ")));
            listPairs.add(pair);
        }
        return listPairs;
    }

    public static void savePairsToJsonFile(String fileName) {
        readListPairs = getListPairs();
        PrintWriter out=null;
        try {
            out = new PrintWriter( new BufferedWriter(
                    new FileWriter(outputFileName+fileName)));
            Gson gson = new Gson();
            gson.toJson(readListPairs,out); //save to JSON file
        } catch (IOException e) {
            System.err.println("No such output file or directory");
        }
        finally {
            out.close();
        }
    }

    public static ArrayList<Pair> readPairsFromJsonFile(String fileName) {
        ArrayList<Pair> listPair = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader(outputFileName+fileName));
            Gson gson = new Gson();
            listPair = gson.fromJson(in, new TypeToken<ArrayList<Pair>>(){}.getType());
        } catch (FileNotFoundException e) {
            System.err.println("File or directory not found ");
        }
        return  listPair;
    }

    public static void main(String[] args) {
        savePairsToJsonFile("1.json");
    }
}

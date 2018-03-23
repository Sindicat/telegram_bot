package storagewords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoCreatorJSON {

    private static final String fileName = "D:\\Storage\\data.txt";

    public static ArrayList <String> getListWords() { //Return list unhandled strings
        BufferedReader in;
        ArrayList<String> listWords=null;
        try {
            in = new BufferedReader( new FileReader(fileName));
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

    public static ArrayList<Pair> getListPairs() {
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

    public static void main(String[] args) {
        for (Pair pair : getListPairs()) {
            System.out.println(pair);
        }
    }
}

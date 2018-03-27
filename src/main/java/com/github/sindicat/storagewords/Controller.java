package com.github.sindicat.storagewords;

import org.telegram.telegrambots.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    List<Pair> listPairs;
    int currPosInList;
    int currPosInPair;
    Pair currPair;
    boolean isStarted;
    private final String defaultAnswer = "Illegal command:\n look at enable commands:\n/changefile: file_number\n/idk\n/begin";

    public Controller() {
        this.listPairs=Data.readPairsFromJsonFile("1.json");//Default meaning
        this.currPair = listPairs.get(currPosInList);
    }

    public String handle(Update update) { //Обрабатывает новое пришедешее сообщение
        String messsage = update.getMessage().getText();
        if(messsage.matches("^/changefile:\\s+\\d+\\s+$") && isStarted) { //Check for command: /chanfefile:digits
            this.listPairs = Data.readPairsFromJsonFile(Arrays.asList(messsage.split(":")).get(1).replaceAll(" ","")+".json");
        }
        if(messsage.matches("^/idk$")) {
            StringBuilder sb = new StringBuilder(currPair.getUnknownWord()+" means:\n");
            for (Word word : currPair.getMeanings()) {
               sb.append("- "+word.getMeaning()+"\n");
            }
            return sb.toString();

        }
        if(messsage.matches("^/begin$")) {
            isStarted=true;
            return "Translate: " +currPair.getUnknownWord();
        }
        if (isStarted) {
            String resultChecking = checkMeaning(messsage);
            if(resultChecking.equals("true")) { //Если значение разгадано true
                ++currPosInPair;
                if(currPosInPair < currPair.getNumMeanings()) { //Отправляем следующее значение слова в паре
                    return "Good, next meaning for: "+currPair.getUnknownWord();//Отгадываем следующее значение
                } else if(currPosInList+1 < listPairs.size()) { //Если еще не закончился список пар, переходим к слудющей паре
                    ++currPosInList;
                    currPosInPair=0;
                    currPair = listPairs.get(currPosInList);
                    return "Next word is: " + currPair.getUnknownWord();
                } else {
                    restroreInitialState(); //Заходим на второй круг
                }
            } else if (resultChecking.equals("false")) {
                return "Incorrect meaning \""+currPair.getUnknownWord()+"\". Try again!";
            } else if (checkMeaning(messsage).equals("guessed")) {
               return "Already guessed! Write another meaning.";
            }
        }
        return defaultAnswer;
    }

    String checkMeaning(String message) { //Баг в том, что можно несколько раз присылать одно и то же значение
        for (int i = 0; i < currPair.getNumMeanings(); i++) {
            if(message.equalsIgnoreCase(currPair.getWord(i).getMeaning())) {
                if(currPair.getWord(i).isGuessed()) {
                    return "guessed";
                } else {
                    currPair.getWord(i).setGuessed(true);
                    return "true";
                }
            }
        }
        return "false";
    }

    void restroreInitialState() {
        currPosInList=0;
        currPosInPair=0;
        currPair = listPairs.get(0);
        for (Pair pair : listPairs) {
            for (int i = 0; i < pair.getNumMeanings() ; i++) {
                pair.getWord(i).setGuessed(false);
            }
        }
    }
}

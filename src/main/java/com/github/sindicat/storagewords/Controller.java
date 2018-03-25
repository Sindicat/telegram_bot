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

    public Controller() {
        this.listPairs=Data.readPairsFromJsonFile("1.json");//Default meaning
        this.currPair = listPairs.get(currPosInList);
    }

    public String handle(Update update) {
        String messsage = update.getMessage().getText();
        if(messsage.matches("^/changefile:\\s+\\d+\\s+$") && isStarted) { //Check for command: /chanfefile:digits
            this.listPairs = Data.readPairsFromJsonFile(Arrays.asList(messsage.split(":")).get(1).replaceAll(" ","")+".json");
        }
        if(messsage.matches("^/begin$")) {
            isStarted=true;
            return "Translate: " +currPair.getWord();
        }
        if (isStarted) {
            if(checkMeaning(messsage)) { //Если значение разгадано true
                ++currPosInPair;
                if(currPosInPair < currPair.getNumMeanings()) { //Отправляем следующее значение слова в паре
                    return "Good, next meaning for:"+currPair.getWord();//Отгадываем следующее значение
                } else if(currPosInList < listPairs.size()) { //Если еще не закончился список пар, переходим к слудющей паре
                    ++currPosInList;
                    currPosInPair=0;
                    currPair = listPairs.get(currPosInList);
                    return "Next word: " + currPair.getWord();
                } else {
                    currPosInList=0;
                    currPosInPair=0;
                    currPair = listPairs.get(0);
                }
            } else {
                return "Incorrect ("+currPair.getWord()+")";
            }
        }
        return null;
    }

    boolean checkMeaning(String message) { //Баг в том, что можно несколько раз присылать одно и то же значение
        for (int i = 0; i < currPair.getNumMeanings(); i++) {
            if(message.equalsIgnoreCase(currPair.getMeaning(i))) {
                return true;
            }
        }
        return false;
    }
}

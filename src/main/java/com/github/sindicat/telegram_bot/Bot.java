package com.github.sindicat.telegram_bot;

import com.github.sindicat.storagewords.Controller;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot{

    private final static String PATH_TO_PROPERIES = "src/main/resources/config.properties";
    private static String bot_token;
    private static String bot_username;
    private static int creatorId;
    Controller controller = new Controller();


    static {
        Properties properties = readConfigProperties();
        bot_token =  properties.getProperty("bot_token");
        bot_username = properties.getProperty("bot_username");
        creatorId = Integer.parseInt(properties.getProperty("creator_id"));
    }

    private static Properties readConfigProperties() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();

        try {
            fileInputStream = new FileInputStream(PATH_TO_PROPERIES);
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Incorrect properties file", e);
        }
 }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("New message");
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String response = controller.handle(update);
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(response);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getBotUsername() {
        return bot_username;
    }

    @Override
    public String getBotToken() {
        return bot_token;
    }
}



package com.github.sindicat.telegram_bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

public class Bot extends AbilityBot {

    private final static String PATH_TO_PROPERIES = "src/main/resources/config.properties";
    private static String bot_token;
    private static String bot_username;
    private static int creatorId;


    static {
        Properties properties = readConfigProperties();
        bot_token =  properties.getProperty("bot_token");
        bot_username = properties.getProperty("bot_username");
        creatorId = Integer.parseInt(properties.getProperty("creator_id"));
    }
    public Bot() {
        super(bot_token, bot_username);
    }

    public static Properties readConfigProperties() {
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

    public int creatorId() {
        return creatorId;
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .build();
    }

}

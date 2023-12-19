package com.project.currencybot.config;

import com.project.currencybot.bank.mono.CurrencyModelMono;
import com.project.currencybot.bank.mono.CurrencyServiceMono;
import com.project.currencybot.bank.privat.CurrencyModelPrivat;
import com.project.currencybot.bank.privat.CurrencyServicePrivat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        CurrencyModelPrivat currencyModelPrivat = new CurrencyModelPrivat();
        CurrencyModelMono currencyModelMono = new CurrencyModelMono();

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/privat":
                    try {
                        String currencyInfo = CurrencyServicePrivat.getCurrencyRatePrivat(new CurrencyModelPrivat());
                        sendMessage(chatId, currencyInfo);
                    } catch (IOException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/mono":
                    try {
                        String currencyInfo = CurrencyServiceMono.getCurrencyRateMono(new CurrencyModelMono());
                        sendMessage(chatId, currencyInfo);
                    } catch (IOException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }


    private void startCommandReceived(Long chatId, String name) {
        String answer = "Вітаю, " + name + "\n" +
                "Натисни /privat щоб отримати курс валют Приватбанку" + "\n" +
                "Натисни /mono щоб отримати курс валют Приватбанку";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }
}
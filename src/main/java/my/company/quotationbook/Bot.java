package my.company.quotationbook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingCommandBot {
    @Value("${app.userName}")
    private String userName;
    private final ParseService parseService;

    public Bot(ParseService parseService, @Value("${app.token}") String botToken) {
        super(botToken);
        this.parseService = parseService;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        try {
            if (update.getMessage().getText().startsWith("go")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId());
                sendMessage.setText(parseService.getQuota());

                execute(sendMessage);
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(update.getMessage().getChatId());
                sendMessage.setText("Не понимать тебя, брат");

                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }
}

package bux.trading.bot;

import bux.trading.bot.config.websocket.WebSocketSessionCreator;
import bux.trading.bot.service.BotConfigurator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotApplication implements ApplicationRunner {

    private BotConfigurator botConfigurator;

    private WebSocketSessionCreator webSocketSessionCreator;

    public BotApplication(BotConfigurator botConfigurator, WebSocketSessionCreator webSocketSessionCreator) {
        this.botConfigurator = botConfigurator;
        this.webSocketSessionCreator = webSocketSessionCreator;
    }

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        botConfigurator.configure(args);
        webSocketSessionCreator.create();
    }
}

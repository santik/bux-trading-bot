package bux.trading.bot;

import bux.trading.bot.provider.WebSocketSessionProvider;
import bux.trading.bot.service.BotConfigurator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotApplication implements ApplicationRunner {

    private BotConfigurator botConfigurator;

    private WebSocketSessionProvider webSocketSessionProvider;

    public BotApplication(BotConfigurator botConfigurator, WebSocketSessionProvider webSocketSessionProvider) {
        this.botConfigurator = botConfigurator;
        this.webSocketSessionProvider = webSocketSessionProvider;
    }

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        botConfigurator.configure(args);
        webSocketSessionProvider.provide();
    }
}

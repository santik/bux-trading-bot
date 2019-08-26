package bux.trading.bot;

import bux.trading.bot.provider.WebSocketSessionProvider;
import bux.trading.bot.service.BotConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotApplication implements ApplicationRunner {

    @Autowired
    private BotConfigurator botConfigurator;

    @Autowired
    private WebSocketSessionProvider webSocketSessionProvider;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        botConfigurator.configure(args);
        webSocketSessionProvider.provide();
    }
}

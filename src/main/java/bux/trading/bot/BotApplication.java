package bux.trading.bot;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.provider.WebSocketSessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BotApplication implements CommandLineRunner {

    @Autowired
    private BotConfiguration botConfiguration;

    @Autowired
    private WebSocketSessionProvider webSocketSessionProvider;

    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        productContext.setProductId("some");
        webSocketSessionProvider.provide();
    }
}

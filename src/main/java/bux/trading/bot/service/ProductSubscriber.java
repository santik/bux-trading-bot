package bux.trading.bot.service;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.message.SubscribeProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

@Service
public class ProductSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSubscriber.class);

    private BotConfiguration botConfiguration;

    public ProductSubscriber(BotConfiguration botConfiguration) {
        this.botConfiguration = botConfiguration;
    }

    public void subscribe(Session webSocketSession) {
        try {
            SubscribeProductRequest subscribeProductRequest = new SubscribeProductRequest();
            subscribeProductRequest.addSubscribeTo(botConfiguration.getProductId());
            webSocketSession.getBasicRemote().sendObject(subscribeProductRequest);
            LOGGER.info("Subscribed to {}", botConfiguration.getProductId());

        } catch (IOException | EncodeException e) {
            LOGGER.error("Connection to websocket failed", e);
            throw new RuntimeException(e);
        }
    }

}

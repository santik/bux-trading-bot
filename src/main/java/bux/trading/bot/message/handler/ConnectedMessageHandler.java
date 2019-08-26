package bux.trading.bot.message.handler;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.message.SubscribeProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

@Service
public class ConnectedMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectedMessageHandler.class);

    private BotConfiguration botConfiguration;

    public ConnectedMessageHandler(BotConfiguration botConfiguration) {
        this.botConfiguration = botConfiguration;
    }

    public void handle(Session webSocketSession) {
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

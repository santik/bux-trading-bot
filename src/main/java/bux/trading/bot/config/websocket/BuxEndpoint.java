package bux.trading.bot.config.websocket;

import bux.trading.bot.service.WebsocketMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class BuxEndpoint extends Endpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuxEndpoint.class);

    private WebsocketMessageHandler websocketMessageHandler;

    public BuxEndpoint(WebsocketMessageHandler websocketMessageHandler) {
        this.websocketMessageHandler = websocketMessageHandler;
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {

        MessageHandler.Whole<String> messageHandler = new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                try {
                    websocketMessageHandler.handle(message, session);
                } catch (Exception e) {
                    LOGGER.error("Error " + e.toString() + " with message " + message);
                }
            }
        };
        session.addMessageHandler(messageHandler);
    }
}

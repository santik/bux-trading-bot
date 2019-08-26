package bux.trading.bot.service;

import bux.trading.bot.generated.WebsocketResponseMessage;
import bux.trading.bot.message.handler.ConnectedMessageHandler;
import bux.trading.bot.message.handler.TradingQuoteMessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;

@Service
public class MessageRouter {

    private static final String CONNECT_CONNECTED = "connect.connected";
    private static final String TRADING_QUOTE = "trading.quote";
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRouter.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private ConnectedMessageHandler connectedMessageHandler;
    private TradingQuoteMessageHandler tradingQuoteMessageHandler;

    public MessageRouter(ConnectedMessageHandler connectedMessageHandler, TradingQuoteMessageHandler tradingQuoteMessageHandler) {
        this.connectedMessageHandler = connectedMessageHandler;
        this.tradingQuoteMessageHandler = tradingQuoteMessageHandler;
    }

    public void route(String messageJson, Session session) throws IOException {
        WebsocketResponseMessage message = OBJECT_MAPPER.readValue(messageJson, WebsocketResponseMessage.class);

        if (isConnectedMessage(message)) {
            connectedMessageHandler.handle(session);
        } else if (isTradingQuoteMessage(message)) {
            tradingQuoteMessageHandler.handle(message);
        } else {
            LOGGER.info("No suitable handler for message {}", message);
        }
    }

    private boolean isTradingQuoteMessage(WebsocketResponseMessage message) {
        return message.getT().equals(TRADING_QUOTE);
    }

    private boolean isConnectedMessage(WebsocketResponseMessage message) {
        return message.getT().equals(CONNECT_CONNECTED);
    }
}

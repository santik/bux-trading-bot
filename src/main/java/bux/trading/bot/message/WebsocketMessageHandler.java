package bux.trading.bot.message;

import bux.trading.bot.generated.WebsocketResponseMessage;
import bux.trading.bot.service.ProductSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;

@Service
public class WebsocketMessageHandler {

    private static final String CONNECT_CONNECTED = "connect.connected";
    private static final String TRADING_QUOTE = "trading.quote";
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketMessageHandler.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private ProductSubscriber productSubscriber;
    private TradingQuoteMessageHandler tradingQuoteMessageHandler;

    public WebsocketMessageHandler(ProductSubscriber productSubscriber, TradingQuoteMessageHandler tradingQuoteMessageHandler) {
        this.productSubscriber = productSubscriber;
        this.tradingQuoteMessageHandler = tradingQuoteMessageHandler;
    }

    public void handle(String messageJson, Session session) throws IOException {
        WebsocketResponseMessage message = OBJECT_MAPPER.readValue(messageJson, WebsocketResponseMessage.class);

        if (isConnectedMessage(message)) {
            productSubscriber.subscribe(session);
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

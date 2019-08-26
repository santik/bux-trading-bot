package bux.trading.bot.config.websocket;

import bux.trading.bot.message.SubscribeProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Encoder;

public class BuxEncoder implements Encoder.Text<SubscribeProductRequest> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(BuxEndpoint.class);

    @Override
    public String encode(SubscribeProductRequest object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        return null;
    }

    @Override
    public void init(javax.websocket.EndpointConfig endpointConfig) {
        LOGGER.info("Initialized");
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroyed");
    }
}

package bux.trading.bot.provider;

import bux.trading.bot.config.websocket.BuxEndpoint;
import bux.trading.bot.config.websocket.BuxConfigurator;
import bux.trading.bot.config.websocket.BuxEncoder;
import bux.trading.bot.service.MessageRouter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Collections;

@Service
public class WebSocketSessionProvider {

    private MessageRouter messageRouter;

    @Value("${bux.authorization.header}")
    private String authorization;

    @Value("${bux.language.header}")
    private String language;

    @Value("${bux.websocket.uri}")
    private String webSocketUri;

    public WebSocketSessionProvider(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    public Session provide() {
        try {
            BuxConfigurator buxConfigurator = new BuxConfigurator(authorization, language);
            BuxEndpoint endpointConfig = new BuxEndpoint(messageRouter);

            ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create()
                    .configurator(buxConfigurator)
                    .encoders(Collections.singletonList(BuxEncoder.class))
                    .build();

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            return container.connectToServer(endpointConfig, clientEndpointConfig, new URI(webSocketUri));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

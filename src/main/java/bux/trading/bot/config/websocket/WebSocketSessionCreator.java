package bux.trading.bot.config.websocket;

import bux.trading.bot.message.WebsocketMessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Collections;

@Service
public class WebSocketSessionCreator {

    private WebsocketMessageHandler websocketMessageHandler;

    @Value("${bux.authorization.header}")
    private String authorization;

    @Value("${bux.language.header}")
    private String language;

    @Value("${bux.websocket.uri}")
    private String webSocketUri;

    public WebSocketSessionCreator(WebsocketMessageHandler websocketMessageHandler) {
        this.websocketMessageHandler = websocketMessageHandler;
    }

    public Session create() {
        try {
            BuxConfigurator buxConfigurator = new BuxConfigurator(authorization, language);
            BuxEndpoint endpoint = new BuxEndpoint(websocketMessageHandler);
            ClientEndpointConfig clientEndpointConfig = ClientEndpointConfig.Builder.create()
                    .configurator(buxConfigurator)
                    .encoders(Collections.singletonList(BuxEncoder.class))
                    .build();
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            return container.connectToServer(endpoint, clientEndpointConfig, new URI(webSocketUri));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

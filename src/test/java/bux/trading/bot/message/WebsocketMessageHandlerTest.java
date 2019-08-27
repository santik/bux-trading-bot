package bux.trading.bot.message;

import bux.trading.bot.generated.WebsocketResponseMessage;
import bux.trading.bot.service.ProductSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.websocket.Session;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebsocketMessageHandlerTest {

    @Mock
    private ProductSubscriber productSubscriber;

    @Mock
    private TradingQuoteMessageHandler tradingQuoteMessageHandler;

    @Mock
    private ObjectMapper mapper;

    @Test
    public void route_withConnectedMessage_shouldCallConnectedMessageHandler() throws Exception {
        //arrange
        String type = "connect.connected";
        String jsonString = "jsonString";
        WebsocketResponseMessage websocketResponseMessage = mock(WebsocketResponseMessage.class);
        when(websocketResponseMessage.getT()).thenReturn(type);
        when(mapper.readValue(jsonString, WebsocketResponseMessage.class)).thenReturn(websocketResponseMessage);
        WebsocketMessageHandler websocketMessageHandler = getMessageRouter();
        Session session = mock(Session.class);

        //act
        websocketMessageHandler.handle(jsonString, session);

        //assert
        verify(productSubscriber).subscribe(session);
        verify(tradingQuoteMessageHandler, never()).handle(websocketResponseMessage);
    }

    @Test
    public void route_withTradingQuoteMessage_shouldCallTradingQuoteMessageHandler() throws Exception {
        //arrange
        String type = "trading.quote";
        String jsonString = "jsonString";
        WebsocketResponseMessage websocketResponseMessage = mock(WebsocketResponseMessage.class);
        when(websocketResponseMessage.getT()).thenReturn(type);
        when(mapper.readValue(jsonString, WebsocketResponseMessage.class)).thenReturn(websocketResponseMessage);
        WebsocketMessageHandler websocketMessageHandler = getMessageRouter();
        Session session = mock(Session.class);

        //act
        websocketMessageHandler.handle(jsonString, session);

        //assert
        verify(productSubscriber, never()).subscribe(session);
        verify(tradingQuoteMessageHandler).handle(websocketResponseMessage);
    }

    @Test
    public void route_withAnotherMessage_shouldNotCallHandlers() throws Exception {
        //arrange
        String type = "another";
        String jsonString = "jsonString";
        WebsocketResponseMessage websocketResponseMessage = mock(WebsocketResponseMessage.class);
        when(websocketResponseMessage.getT()).thenReturn(type);
        when(mapper.readValue(jsonString, WebsocketResponseMessage.class)).thenReturn(websocketResponseMessage);
        WebsocketMessageHandler websocketMessageHandler = getMessageRouter();
        Session session = mock(Session.class);

        //act
        websocketMessageHandler.handle(jsonString, session);

        //assert
        verify(productSubscriber, never()).subscribe(session);
        verify(tradingQuoteMessageHandler, never()).handle(websocketResponseMessage);
    }

    private WebsocketMessageHandler getMessageRouter() throws Exception {
        Field field = WebsocketMessageHandler.class.getDeclaredField("OBJECT_MAPPER");
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, mapper);
        return new WebsocketMessageHandler(productSubscriber, tradingQuoteMessageHandler);
    }

}

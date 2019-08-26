package bux.trading.bot.message.handler;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.message.SubscribeProductRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectedMessageHandlerTest {

    private ConnectedMessageHandler connectedMessageHandler;

    @Mock
    private BotConfiguration botConfiguration;

    @Mock
    private Session session;

    @Before
    public void setUp() {
        connectedMessageHandler = new ConnectedMessageHandler(botConfiguration);
    }

    @Test(expected = RuntimeException.class)
    public void handle_whenSessionThrowsException_shouldThrowRuntimeException() throws IOException, EncodeException {
        //arrange
        when(botConfiguration.getProductId()).thenReturn("productId");
        RemoteEndpoint.Basic basic = mock(RemoteEndpoint.Basic.class);
        doThrow(EncodeException.class).when(basic).sendObject(any());
        when(session.getBasicRemote()).thenReturn(basic);

        //act
        connectedMessageHandler.handle(session);

        //verify
        verify(botConfiguration, times(1)).getProductId();
    }

    @Test(expected = RuntimeException.class)
    public void handle_whenContextThrowsException_shouldThrowRuntimeException() {
        //arrange
        when(botConfiguration.getProductId()).thenThrow(new IOException(""));

        //act
        connectedMessageHandler.handle(session);

        //verify
        verify(botConfiguration, times(1)).getProductId();
    }

    @Test
    public void handle_withCorrectData_shouldSendObject() throws IOException, EncodeException {
        //arrange
        when(botConfiguration.getProductId()).thenReturn("productId");
        RemoteEndpoint.Basic basic = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(basic);

        //act
        connectedMessageHandler.handle(session);

        //verify
        verify(botConfiguration, times(2)).getProductId();
        verify(basic).sendObject(any(SubscribeProductRequest.class));
    }

}

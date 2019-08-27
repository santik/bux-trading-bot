package bux.trading.bot;

import bux.trading.bot.config.websocket.WebSocketSessionCreator;
import bux.trading.bot.service.BotConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.ApplicationArguments;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BotApplicationTest {

    @Mock
    private BotConfigurator botConfigurator;

    @Mock
    private WebSocketSessionCreator webSocketSessionCreator;

    @Test
    public void run_shouldConfigureAndProvideASession() {
        //arrange
        BotApplication application = new BotApplication(botConfigurator, webSocketSessionCreator);
        ApplicationArguments args = mock(ApplicationArguments.class);

        //act
        application.run(args);

        //assert
        verify(botConfigurator).configure(args);
        verify(webSocketSessionCreator).create();
    }
}

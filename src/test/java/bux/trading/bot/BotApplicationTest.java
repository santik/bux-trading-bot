package bux.trading.bot;

import bux.trading.bot.config.provider.WebSocketSessionProvider;
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
    private WebSocketSessionProvider webSocketSessionProvider;

    @Test
    public void run_shouldConfigureAndProvideASession() {
        //arrange
        BotApplication application = new BotApplication(botConfigurator, webSocketSessionProvider);
        ApplicationArguments args = mock(ApplicationArguments.class);

        //act
        application.run(args);

        //assert
        verify(botConfigurator).configure(args);
        verify(webSocketSessionProvider).provide();
    }
}

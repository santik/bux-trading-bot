package bux.trading.bot.message;

import bux.trading.bot.condition.BuyCondition;
import bux.trading.bot.condition.LowerLimitSellCondition;
import bux.trading.bot.condition.UpperLimitSellCondition;
import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.generated.Body;
import bux.trading.bot.generated.WebsocketResponseMessage;
import bux.trading.bot.service.PositionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradingQuoteMessageHandlerTest {

    private TradingQuoteMessageHandler handler;

    @Mock
    private BotConfiguration botConfiguration;

    @Mock
    private BuyCondition buyCondition;

    @Mock
    private LowerLimitSellCondition lowerLimitSellCondition;

    @Mock
    private UpperLimitSellCondition upperLimitSellCondition;

    @Mock
    private PositionService positionService;

    @Before
    public void setUp() {
        handler = new TradingQuoteMessageHandler(botConfiguration, buyCondition, lowerLimitSellCondition, upperLimitSellCondition, positionService);
    }

    @Test
    public void handle_withWrongProduct_shouldReturn() {
        //arrange
        WebsocketResponseMessage message = mock(WebsocketResponseMessage.class);
        String securityId = "securityId";
        Body body = new Body(securityId, 1D);
        when(message.getBody()).thenReturn(body);
        String anotherProductId = "anotherId";
        when(botConfiguration.getProductId()).thenReturn(anotherProductId);

        //act
        handler.handle(message);

        //assert
        verify(buyCondition, never()).isMet(any(), any());
    }

    @Test
    public void handle_withBuyConditionMet_shouldOpenPosition() {
        //arrange
        WebsocketResponseMessage message = mock(WebsocketResponseMessage.class);
        String securityId = "securityId";
        Body body = new Body(securityId, 1D);
        when(message.getBody()).thenReturn(body);
        when(botConfiguration.getProductId()).thenReturn(securityId);
        when(buyCondition.isMet(body, botConfiguration)).thenReturn(true);

        //act
        handler.handle(message);

        //assert
        verify(positionService).openPosition(body);
    }

    @Test
    public void handle_withLowerLimitConditionMet_shouldOpenPosition() {
        //arrange
        WebsocketResponseMessage message = mock(WebsocketResponseMessage.class);
        String securityId = "securityId";
        Body body = new Body(securityId, 1D);
        when(message.getBody()).thenReturn(body);
        when(botConfiguration.getProductId()).thenReturn(securityId);
        when(buyCondition.isMet(body, botConfiguration)).thenReturn(false);
        when(lowerLimitSellCondition.isMet(body, botConfiguration)).thenReturn(true);

        //act
        handler.handle(message);

        //assert
        verify(positionService).closePosition(body);
    }

    @Test
    public void handle_withUpperLimitConditionMet_shouldOpenPosition() {
        //arrange
        WebsocketResponseMessage message = mock(WebsocketResponseMessage.class);
        String securityId = "securityId";
        Body body = new Body(securityId, 1D);
        when(message.getBody()).thenReturn(body);
        when(botConfiguration.getProductId()).thenReturn(securityId);
        when(buyCondition.isMet(body, botConfiguration)).thenReturn(false);
        when(lowerLimitSellCondition.isMet(body, botConfiguration)).thenReturn(false);
        when(upperLimitSellCondition.isMet(body, botConfiguration)).thenReturn(true);

        //act
        handler.handle(message);

        //assert
        verify(positionService).closePosition(body);
    }
}

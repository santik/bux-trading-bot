package bux.trading.bot.condition;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.generated.Body;
import bux.trading.bot.product.Product;
import bux.trading.bot.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuyConditionTest {

    private static final String PRODUCT_ID = "product_id";

    @Mock
    private ProductService productService;

    @Mock
    private BotConfiguration botConfiguration;

    private BuyCondition condition;

    @Before
    public void setUp() {
        condition = new BuyCondition(productService);
    }

    @Test
    public void isMet_withNoProductAndHigherPrice_shouldReturnTrue() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 2D);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);
        when(botConfiguration.getBuyingPrice()).thenReturn(1D);

        //act && assert
        assertTrue(condition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withNoProductAndLowerPrice_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1D);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);
        when(botConfiguration.getBuyingPrice()).thenReturn(2D);

        //act && assert
        assertFalse(condition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withProductAndHigherPrice_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 2D);
        Product product = mock(Product.class);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(product);

        //act && assert
        assertFalse(condition.isMet(tradingQuote, botConfiguration));
    }
}

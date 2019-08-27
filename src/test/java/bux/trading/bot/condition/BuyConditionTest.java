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
import org.springframework.test.util.ReflectionTestUtils;

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
        ReflectionTestUtils.setField(condition, "deviation", 0.01);
    }

    @Test
    public void isMet_withNoProductAndHigherPriceInsideDeviation_shouldReturnTrue() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1 + 0.005);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);
        when(botConfiguration.getBuyingPrice()).thenReturn(1D);

        //act && assert
        assertTrue(condition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withNoProductAndHigherPriceOutsideDeviation_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1 + 0.015);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);
        when(botConfiguration.getBuyingPrice()).thenReturn(1D);

        //act && assert
        assertFalse(condition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withNoProductAndLowerPriceOutsideDeviation_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1 - 0.015);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);
        when(botConfiguration.getBuyingPrice()).thenReturn(1D);

        //act && assert
        assertFalse(condition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withNoProductAndLowerPriceInsideDeviation_shouldReturnTrue() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1 - 0.005);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);
        when(botConfiguration.getBuyingPrice()).thenReturn(1D);

        //act && assert
        assertTrue(condition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withProduct_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1D);
        Product product = mock(Product.class);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(product);

        //act && assert
        assertFalse(condition.isMet(tradingQuote, botConfiguration));
    }
}

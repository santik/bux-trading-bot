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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpperLimitSellConditionTest {

    private static final String PRODUCT_ID = "product_id";
    private static final String POSITION_ID = "positionId";
    private UpperLimitSellCondition upperLimitSellRule;

    @Mock
    private BotConfiguration botConfiguration;

    @Mock
    private ProductService productService;

    @Before
    public void setUp() {
        upperLimitSellRule = new UpperLimitSellCondition(productService);
    }

    @Test
    public void isMet_withProductAndHigherPrice_shouldReturnTrue() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 3D);

        Product savedProduct = new Product(PRODUCT_ID, POSITION_ID, 1D);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(savedProduct);
        when(botConfiguration.getUpperLimitPrice()).thenReturn(2D);

        //act && assert
        assertTrue(upperLimitSellRule.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withProductAndLowerPrice_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1D);

        Product savedProduct = new Product(PRODUCT_ID, POSITION_ID, 3D);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(savedProduct);
        when(botConfiguration.getUpperLimitPrice()).thenReturn(3D);

        //act && assert
        assertFalse(upperLimitSellRule.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withoutProduct_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1D);

        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);

        //act && assert
        assertFalse(upperLimitSellRule.isMet(tradingQuote, botConfiguration));
    }
}

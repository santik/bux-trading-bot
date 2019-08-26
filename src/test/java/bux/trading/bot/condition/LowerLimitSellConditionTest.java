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
public class LowerLimitSellConditionTest {

    private static final String PRODUCT_ID = "product_id";
    private static final String POSITION_ID = "positionId";
    private LowerLimitSellCondition lowerLimitSellCondition;

    @Mock
    private BotConfiguration botConfiguration;

    @Mock
    private ProductService productService;

    @Before
    public void setUp() {
        lowerLimitSellCondition = new LowerLimitSellCondition(productService);
    }

    @Test
    public void isMet_withProductWithHigherPrice_shouldReturnTrue() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 1D);
        Product savedProduct = new Product(PRODUCT_ID, POSITION_ID, 3D);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(savedProduct);
        when(botConfiguration.getLowerLimitPrice()).thenReturn(2D);

        //act && assert
        assertTrue(lowerLimitSellCondition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withProductWithLowerPrice_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 2D);
        Product savedProduct = new Product(PRODUCT_ID, POSITION_ID, 3D);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(savedProduct);
        when(botConfiguration.getLowerLimitPrice()).thenReturn(1D);

        //act && assert
        assertFalse(lowerLimitSellCondition.isMet(tradingQuote, botConfiguration));
    }

    @Test
    public void isMet_withoutProduct_shouldReturnFalse() {
        //arrange
        Body tradingQuote = new Body(PRODUCT_ID, 2D);
        when(productService.getProductByQuote(tradingQuote)).thenReturn(null);

        //act && assert
        assertFalse(lowerLimitSellCondition.isMet(tradingQuote, botConfiguration));
    }
}

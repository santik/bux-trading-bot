package bux.trading.bot.service;

import bux.trading.bot.generated.Body;
import bux.trading.bot.generated.Price;
import bux.trading.bot.generated.ProfitAndLoss;
import bux.trading.bot.generated.RestApiResponse;
import bux.trading.bot.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceTest {

    private PositionService positionService;

    @Mock
    private RestService restService;

    @Mock
    private ProductService productService;

    @Before
    public void setUp() {
        positionService = new PositionService(restService, productService);
    }

    @Test
    public void openPosition_withQuote_shouldBuyProductAndSaveIt() {

        //arrange
        String productId = "productId";
        double currentPrice = 1D;
        String positionId = "positionId";
        Body quote = new Body(productId, currentPrice);
        RestApiResponse apiResponse = mock(RestApiResponse.class);
        when(apiResponse.getPositionId()).thenReturn(positionId);
        when(apiResponse.getPrice()).thenReturn(new Price());
        when(restService.buyProduct(productId)).thenReturn(apiResponse);
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        //act
        positionService.openPosition(quote);

        //assert
        verify(productService).save(argument.capture());
        assertEquals(argument.getValue().getPrice(), currentPrice, 0);
        assertEquals(argument.getValue().getProductId(), productId);
        assertEquals(argument.getValue().getPositionId(), positionId);
    }

    @Test
    public void closePosition_withQuote_shouldSellProductAndDeleteIt() {

        //arrange
        String productId = "productId";
        double currentPrice = 1D;
        String positionId = "positionId";
        Body quote = new Body(productId, currentPrice);
        Product product = new Product(productId, positionId, currentPrice);
        RestApiResponse apiResponse = mock(RestApiResponse.class);
        when(restService.sellProduct(product)).thenReturn(apiResponse);
        when(productService.getProductByQuote(quote)).thenReturn(product);
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        when(apiResponse.getProfitAndLoss()).thenReturn(new ProfitAndLoss());

        //act
        positionService.closePosition(quote);

        //assert
        verify(productService).delete(argument.capture());
        assertEquals(product, argument.getValue());
    }
}

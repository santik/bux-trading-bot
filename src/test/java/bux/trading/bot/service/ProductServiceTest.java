package bux.trading.bot.service;

import bux.trading.bot.generated.Body;
import bux.trading.bot.product.Product;
import bux.trading.bot.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        service = new ProductService(productRepository);
    }

    @Test
    public void getProductByQuote_shouldCallRepository() {
        //arrange
        Body quote = mock(Body.class);
        String productId = "productId";
        when(quote.getSecurityId()).thenReturn(productId);
        Product product = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(product);

        //act
        Product productByQuote = service.getProductByQuote(quote);

        //assert
        assertEquals(product, productByQuote);
    }

    @Test
    public void save_shouldCallRepository() {
        //arrange
        Product product = mock(Product.class);

        //act
        service.save(product);

        //assert
        verify(productRepository).save(product);
    }

    @Test
    public void delete_shouldCallRepository() {
        //arrange
        Product product = mock(Product.class);

        //act
        service.delete(product);

        //assert
        verify(productRepository).delete(product);
    }
}

package bux.trading.bot.product;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductRepositoryTest {

    private ProductRepository repository = new ProductRepository();

    @Test
    public void save_withProduct_shouldSetProduct() {
        //arrange
        Product expectedProduct = mock(Product.class);

        //act
        repository.save(expectedProduct);

        //assert
        Object product = ReflectionTestUtils.getField(repository, "product");
        assertEquals(expectedProduct, product);
    }

    @Test
    public void findById_withCorrectProduct_shouldReturnProduct() {
        //arrange
        Product product = mock(Product.class);
        String productId = "productId";
        when(product.getProductId()).thenReturn(productId);
        ReflectionTestUtils.setField(repository, "product", product);

        //act && assert
        assertEquals(product, repository.findById(productId));
    }

    @Test
    public void findById_withIncorrectProduct_shouldNull() {
        //arrange
        Product product = mock(Product.class);
        String productId = "productId";
        when(product.getProductId()).thenReturn(productId);
        ReflectionTestUtils.setField(repository, "product", product);

        //act && assert
        assertNull(repository.findById("anotherproductId"));
    }

    @Test
    public void findById_withNullProduct_shouldNull() {
        //arrange
        ReflectionTestUtils.setField(repository, "product", null);

        //act && assert
        assertNull(repository.findById("anotherproductId"));
    }

    @Test
    public void delete_withCorrectProduct_shouldSetProductToNull() {
        //arrange
        Product product = mock(Product.class);
        String productId = "productId";
        when(product.getProductId()).thenReturn(productId);
        ReflectionTestUtils.setField(repository, "product", product);

        //act
        repository.delete(product);

        //assert
        assertNull(ReflectionTestUtils.getField(repository, "product"));
    }

    @Test
    public void delete_withIncorrectProduct_shouldNotDoAnything() {
        //arrange
        Product product = mock(Product.class);
        String productId = "productId";
        Product anotherProduct = mock(Product.class);
        when(anotherProduct.getProductId()).thenReturn("anotherproductId");
        when(product.getProductId()).thenReturn(productId);
        ReflectionTestUtils.setField(repository, "product", product);

        //act
        repository.delete(anotherProduct);

        //assert
        assertEquals(product, ReflectionTestUtils.getField(repository, "product"));
    }
}

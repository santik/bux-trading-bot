package bux.trading.bot.product;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private Product product;

    public void save(Product entity) {
        product = entity;
    }

    public Product findById(String s) {
        if (product == null || !product.getProductId().equals(s)) {
            return null;
        }
        return product;
    }

    public void delete(Product productToDelete) {
        if (findById(productToDelete.getProductId()) != null) {
            this.product = null;
        }
    }
}

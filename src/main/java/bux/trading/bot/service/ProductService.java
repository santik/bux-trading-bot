package bux.trading.bot.service;

import bux.trading.bot.generated.Body;
import bux.trading.bot.product.Product;
import bux.trading.bot.product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getProductByQuote(Body quote) {
        return repository.findById(quote.getSecurityId());
    }

    public void save(Product product) {
        repository.save(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }
}

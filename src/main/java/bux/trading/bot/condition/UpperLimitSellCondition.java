package bux.trading.bot.condition;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.generated.Body;
import bux.trading.bot.product.Product;
import bux.trading.bot.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class UpperLimitSellCondition {

    private final ProductService productService;

    public UpperLimitSellCondition(ProductService productService) {
        this.productService = productService;
    }

    public boolean isMet(Body quote, BotConfiguration botConfiguration) {
        Product product = productService.getProductByQuote(quote);
        return isProductExists(product) && isPriceHigherThenLimit(quote, botConfiguration)
                && isCurrentPriceHigherThanBought(quote, product);
    }

    private boolean isCurrentPriceHigherThanBought(Body quote, Product product) {
        return quote.getCurrentPrice() > product.getPrice();
    }

    private boolean isPriceHigherThenLimit(Body quote, BotConfiguration botConfiguration) {
        return quote.getCurrentPrice() >= botConfiguration.getUpperLimitPrice();
    }

    private boolean isProductExists(Product product) {
        return product != null;
    }
}

package bux.trading.bot.condition;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.generated.Body;
import bux.trading.bot.product.Product;
import bux.trading.bot.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class LowerLimitSellCondition {

    private final ProductService productService;

    public LowerLimitSellCondition(ProductService productService) {
        this.productService = productService;
    }

    public boolean isMet(Body quote, BotConfiguration botConfiguration) {
        Product product = productService.getProductByQuote(quote);
        return isProductExists(product)
                && isPriceLowerThenLimit(quote, botConfiguration)
                && isCurrentPriceLowerThanBought(quote, product);
    }

    private boolean isCurrentPriceLowerThanBought(Body quote, Product product) {
        return quote.getCurrentPrice() < product.getPrice();
    }

    private boolean isPriceLowerThenLimit(Body quote, BotConfiguration botConfiguration) {
        return quote.getCurrentPrice() <= botConfiguration.getLowerLimitPrice();
    }

    private boolean isProductExists(Product product) {
        return product != null;
    }
}

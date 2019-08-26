package bux.trading.bot.condition;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.generated.Body;
import bux.trading.bot.service.ProductService;
import org.springframework.stereotype.Component;

@Component
public class BuyCondition {

    private final ProductService productService;

    public BuyCondition(ProductService productService) {
        this.productService = productService;
    }

    public boolean isMet(Body quote, BotConfiguration botConfiguration) {
        return !isProductBought(quote) && isBuyingPriceAcceptable(quote, botConfiguration);
    }

    private boolean isBuyingPriceAcceptable(Body quote, BotConfiguration botConfiguration) {
        return quote.getCurrentPrice() >= botConfiguration.getBuyingPrice();
    }

    private boolean isProductBought(Body quote) {
        return productService.getProductByQuote(quote) != null;
    }
}

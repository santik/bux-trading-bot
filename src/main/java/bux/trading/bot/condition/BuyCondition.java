package bux.trading.bot.condition;

import bux.trading.bot.config.BotConfiguration;
import bux.trading.bot.generated.Body;
import bux.trading.bot.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BuyCondition {

    @Value("${bux.product.buying.price.deviation}")
    private Double deviation;

    private final ProductService productService;

    public BuyCondition(ProductService productService) {
        this.productService = productService;
    }

    public boolean isMet(Body quote, BotConfiguration botConfiguration) {
        return !isProductBought(quote) && isBuyingPriceAcceptable(quote, botConfiguration);
    }

    private boolean isBuyingPriceAcceptable(Body quote, BotConfiguration botConfiguration) {
        return quote.getCurrentPrice() >= getMinBuyingPrice(botConfiguration.getBuyingPrice())
                && quote.getCurrentPrice() <= getMaxBuyingPrice(botConfiguration.getBuyingPrice());
    }

    private Double getMaxBuyingPrice(Double price) {
        return price + price * deviation;
    }

    private Double getMinBuyingPrice(Double price) {
        return price - price * deviation;
    }

    private boolean isProductBought(Body quote) {
        return productService.getProductByQuote(quote) != null;
    }
}

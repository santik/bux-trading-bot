package bux.trading.bot.service;

import bux.trading.bot.generated.Body;
import bux.trading.bot.generated.RestApiResponse;
import bux.trading.bot.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionService.class);
    private RestService restService;
    private ProductService productService;

    public PositionService(RestService restService, ProductService productService) {
        this.restService = restService;
        this.productService = productService;
    }

    public void openPosition(Body quote) {
        String productId = quote.getSecurityId();
        RestApiResponse responseOrder = restService.buyProduct(productId);
        String positionId = responseOrder.getPositionId();
        productService.save(new Product(productId, positionId, quote.getCurrentPrice()));
        LOGGER.info("Bought product with price {}", responseOrder.getPrice().getAmount());
    }

    public void closePosition(Body quote) {
        Product product = productService.getProductByQuote(quote);
        LOGGER.info(String.format("Closing position at %.2f", quote.getCurrentPrice()));
        RestApiResponse responseOrder = restService.sellProduct(product);
        productService.delete(product);
        LOGGER.info(String.format("Difference: %.2f", responseOrder.getProfitAndLoss().getAmount()));
    }
}

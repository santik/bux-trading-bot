package bux.trading.bot.service;

import bux.trading.bot.config.provider.RestTemplateProvider;
import bux.trading.bot.generated.BuyOrder;
import bux.trading.bot.generated.InvestingAmount;
import bux.trading.bot.generated.RestApiResponse;
import bux.trading.bot.generated.Source;
import bux.trading.bot.product.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static bux.trading.bot.generated.Source.SourceType.OTHER;

@Service
public class RestService {

    private final RestTemplateProvider restTemplateProvider;

    @Value("${bux.api.uri}")
    private String apiUrl;

    @Value("${bux.api.uri.trade}")
    private String tradeUrl;

    @Value("${bux.api.uri.positions}")
    private String positionsUrl;

    public RestService(RestTemplateProvider restTemplateProvider) {
        this.restTemplateProvider = restTemplateProvider;
    }

    public RestApiResponse buyProduct(String productId) {
        BuyOrder buyOrder = getBuyOrder(productId);
        ResponseEntity<RestApiResponse> buyResponse = restTemplateProvider.provide().postForEntity(getApiTradeUrl(), buyOrder, RestApiResponse.class);
        return buyResponse.getBody();
    }

    public RestApiResponse sellProduct(Product product) {
        String url = getApiClosePositionUrl(product);
        ResponseEntity<RestApiResponse> exchange = restTemplateProvider.provide().exchange(url, HttpMethod.DELETE, null, RestApiResponse.class);
        return exchange.getBody();
    }

    private String getApiTradeUrl() {
        return apiUrl + tradeUrl;
    }

    private String getApiClosePositionUrl(Product product) {
        return apiUrl + positionsUrl + product.getPositionId();
    }

    private BuyOrder getBuyOrder(String productId) {
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setDirection(BuyOrder.Direction.BUY);
        buyOrder.setInvestingAmount(new InvestingAmount(InvestingAmount.Currency.BUX, 2, 10.0));
        buyOrder.setLeverage(2);
        buyOrder.setProductId(productId);
        buyOrder.setSource(new Source(OTHER));
        return buyOrder;
    }
}

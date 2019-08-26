package bux.trading.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BotConfiguration {

    @Value("${bux.product.id}")
    private String productId;

    @Value("${bux.product.buying.price}")
    private double buyingPrice;

    @Value("${bux.product.upper.limit.price}")
    private double upperLimitPrice;

    @Value("${bux.product.lower.limit.price}")
    private double lowerLimitPrice;

    @PostConstruct
    public void init() {
        if (lowerLimitPrice >= buyingPrice || buyingPrice >= upperLimitPrice) {
            throw new RuntimeException("Invalid prices");
        }
    }

    public String getProductId() {
        return productId;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getUpperLimitPrice() {
        return upperLimitPrice;
    }

    public double getLowerLimitPrice() {
        return lowerLimitPrice;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setUpperLimitPrice(double upperLimitPrice) {
        this.upperLimitPrice = upperLimitPrice;
    }

    public void setLowerLimitPrice(double lowerLimitPrice) {
        this.lowerLimitPrice = lowerLimitPrice;
    }
}

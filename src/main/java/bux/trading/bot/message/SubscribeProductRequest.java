package bux.trading.bot.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SubscribeProductRequest {

    private static final String PREFIX = "trading.product.";

    @JsonProperty("subscribeTo")
    private List<String> subscribeTo = new ArrayList<>();
    @JsonProperty("unsubscribeFrom")
    private List<String> unsubscribeFrom = new ArrayList<>();

    public void addSubscribeTo(String productId) {
        this.subscribeTo.add(PREFIX + productId);
    }

    public void addUnsubscribeFrom(String productId) {
        throw new UnsupportedOperationException();
    }
}

package bux.trading.bot.product;

public class Product {

    private String productId;
    private String positionId;
    private double price;

    public Product(String productId, String positionId, double price) {
        this.productId = productId;
        this.positionId = positionId;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public String getPositionId() {
        return positionId;
    }

    public double getPrice() {
        return price;
    }
}

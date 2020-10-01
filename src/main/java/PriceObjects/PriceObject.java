package PriceObjects;

import java.math.BigDecimal;

public class PriceObject {

    String source;
    BigDecimal price;

    public PriceObject(BigDecimal price, String source) {
        this.price = price;
        this.source = source;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "PriceObject{" +
                "source='" + source + '\'' +
                ", price=" + price +
                '}';
    }
}

package PriceObjects;

import org.json.JSONObject;

import java.math.BigDecimal;

public class PriceObject {

    String source;
    BigDecimal price;
    JSONObject original;

    public PriceObject(BigDecimal price, String source, JSONObject original) {
        this.price = price;
        this.source = source;
        this.original = original;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSource() {
        return source;
    }

    public JSONObject getOriginal() {
        return original;
    }

    @Override
    public String toString() {
        return "PriceObject{" +
                "source='" + source + '\'' +
                ", price=" + price +
                '}';
    }
}

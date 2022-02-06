package legacyfigher.dietary.newproducts;

import java.math.BigDecimal;
import java.util.UUID;

public class OldProduct {

    UUID serialNumber = UUID.randomUUID();
    BigDecimal price;
    Integer counter;
    String longDesc;
    private String desc;

    public OldProduct(BigDecimal price, String desc, String longDesc, Integer counter) {
        this.price = price;
        this.desc = desc;
        this.longDesc = longDesc;
        this.counter = counter;
    }

    void decrementCounter() {
        validatePriceAndCounter();
        if (counter - 1 < 0) {
            throw new IllegalStateException("Negative counter");
        }
        counter--;
    }

    void incrementCounter() {
        validatePriceAndCounter();
        counter++;
    }

    void changePriceTo(BigDecimal newPrice) {
        validatePriceAndCounter();
        if (counter > 0) {
            validatePrice(newPrice);
            this.price = newPrice;
        }
    }

    void replaceCharFromDesc(String charToReplace, String replaceWith) {
        if (isInvalidDescOrLongDesc()) {
            throw new IllegalStateException("null or empty desc");
        }
        longDesc = longDesc.replace(charToReplace, replaceWith);
        desc = desc.replace(charToReplace, replaceWith);
    }

    String formatDesc() {
        if (isInvalidDescOrLongDesc()) {
            return "";
        }
        return desc + " *** " + longDesc;
    }

    @Override
    public String toString() {
        return "OldProduct{" +
                "serialNumber=" + serialNumber +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", counter=" + counter +
                '}';
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalStateException("new price null");
        }
        if (price.signum()<0){
            throw new IllegalStateException("new price negative");
        }
    }

    private boolean isInvalidDescOrLongDesc() {
        return longDesc == null ||
                longDesc.isEmpty() ||
                desc == null ||
                desc.isEmpty();
    }

    private void validatePriceAndCounter() {
        if (counter == null) {
            throw new IllegalStateException("null counter");
        }
        if (counter < 0) {
            throw new IllegalStateException("Negative counter");
        }
        if (price != null && price.signum() <= 0) {
            throw new IllegalStateException("Invalid price");
        }
    }
}

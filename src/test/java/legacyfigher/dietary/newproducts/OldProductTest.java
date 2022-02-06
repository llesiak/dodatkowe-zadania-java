package legacyfigher.dietary.newproducts;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

class OldProductTest {

    static Stream<Arguments> provideInvalidProductsWithMessages() {
        return Stream.of(
                Arguments.of(new OldProduct(ZERO, "desc", "long_desc", 0), "Invalid price"),
                Arguments.of(new OldProduct(ONE, "desc", "long_desc", -2), "Negative counter"),
                Arguments.of(new OldProduct(ONE, "desc", "long_desc", null), "null counter")
        );
    }

    static Stream<Arguments> provideProductsWithInvalidDesc() {
        return Stream.of(
                Arguments.of(new OldProduct(ONE, null, "long_desc", 1)),
                Arguments.of(new OldProduct(ONE, "", "long_desc", 1)),
                Arguments.of(new OldProduct(ONE, "desc", null, 1)),
                Arguments.of(new OldProduct(ONE, "desc", "", 1))
        );
    }

    @Test
    void shouldDecrementCounter() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", 2);

        //when
        product.decrementCounter();

        //then
        Assertions.assertEquals(1, product.counter);
    }

    @Test
    void shouldNotDecrementToNegativeCounter() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", 0);

        //when
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                product::decrementCounter);

        //then
        Assertions.assertEquals("Negative counter", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidProductsWithMessages")
    void shouldNotDecrementCounterForInvalidProduct(OldProduct product, String message) {
        //when
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                product::decrementCounter);
        //then
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldIncrementCounter() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", 2);

        //when
        product.incrementCounter();

        //then
        Assertions.assertEquals(3, product.counter);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidProductsWithMessages")
    void shouldNotIncrementCounterForInvalidProduct(OldProduct product, String message) {
        //when
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                product::incrementCounter);
        //then
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldChangePriceTo() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", 1);
        BigDecimal newPrice = TEN;

        //when
        product.changePriceTo(newPrice);

        //then
        Assertions.assertEquals(newPrice, product.price);
    }

    @Test
    void shouldNotChangePriceToInvalidPrice() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", 1);
        BigDecimal newPrice = null;

        //expect
        Assertions.assertThrows(IllegalStateException.class, () -> product.changePriceTo(newPrice));
    }

    //    @ParameterizedTest
    @MethodSource("provideInvalidProductsWithMessages")
    void shouldNotChangePriceOfInvalidProduct(OldProduct product, String message) {
        //when
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                () -> product.changePriceTo(TEN));
        //then
        Assertions.assertEquals(message, exception.getMessage());
    }

    /**
     * looks like BUG in changePriceTo, use shouldNotChangePriceOfInvalidProduct(OldProduct, String) test when fixed
     */
    @Test
    void shouldNotChangePriceOfInvalidProduct() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", null);

        //expect
        Assertions.assertThrows(IllegalStateException.class, () -> product.changePriceTo(TEN));
    }

    @Test
    void replaceCharFromDesc() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", 1);

        //when
        product.replaceCharFromDesc("desc", "DESC");

        //then
        Assertions.assertEquals("long_DESC", product.longDesc);
        Assertions.assertEquals("DESC *** long_DESC", product.formatDesc());
    }

    @ParameterizedTest
    @MethodSource("provideProductsWithInvalidDesc")
    void shouldNotReplaceCharFromDescForProductsWithInvalidDesc(OldProduct product) {
        //when
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
                () -> product.replaceCharFromDesc("desc", "DESC"));
        //then
        Assertions.assertEquals("null or empty desc", exception.getMessage());
    }

    @Test
    void formatDesc() {
        //given
        OldProduct product = new OldProduct(ONE, "desc", "long_desc", 1);

        //when
        String formatDesc = product.formatDesc();

        //then
        Assertions.assertEquals("desc *** long_desc", formatDesc);
    }

    @ParameterizedTest
    @MethodSource("provideProductsWithInvalidDesc")
    void shouldNotFormatDescForProductsWithInvalidDesc(OldProduct product) {
        //when
        String formatDesc = product.formatDesc();
        //then
        Assertions.assertEquals("", formatDesc);
    }
}
import com.impact.lib.api.util.Currency;

import java.math.BigDecimal;

public class CurrencyTest {

    public static void main(String[] args) {
        System.out.println(Currency.format(BigDecimal.valueOf(0.1).add(BigDecimal.valueOf(0.1).add(BigDecimal.valueOf(0.1))).doubleValue()));
        System.out.println(Currency.format(BigDecimal.valueOf(0.1 + 0.1 + 0.1).doubleValue()));
        System.out.println(BigDecimal.valueOf(0.1).add(BigDecimal.valueOf(0.1).add(BigDecimal.valueOf(0.1))));
        System.out.println(BigDecimal.valueOf(0.1 + 0.1 + 0.1));
    }

}

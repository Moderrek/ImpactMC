import com.impact.lib.api.util.*;

import java.math.BigDecimal;

public class RangeTest {
    public static void main(String[] args) {
        Range<Integer> range = new ImmutableNumberRange<>(1, 2);
        System.out.println(range);
        for (int i : new int[]{1, 3}) {
            System.out.println(range.isIn(i));
            System.out.println(Randoms.inRange(range));
        }
        Range<Double> doubleRange = new ImmutableNumberRange<>(1.3D, 13.0D);
        System.out.println(doubleRange);
        for (double v : new double[]{1.0D, 1.3D, 1.4D, 13.0D, 13.1D}) {
            System.out.println(doubleRange.isIn(v));
        }
        var bigDecimalRange = new NumberRange<>(BigDecimal.valueOf(0.1D), BigDecimal.valueOf(3.2D));
//        bigDecimalRange.max(BigDecimal.valueOf(-0.1D));
        System.out.println(bigDecimalRange);
        for (double v : new double[]{0.1D, -0.1D, 3.1D, 3.200000000000000000000001D, 3.3D}) {
            System.out.println(bigDecimalRange.isIn(BigDecimal.valueOf(v)));
        }

        FlipFlop<Double> flipFlop = new FlipFlop<>(5.0D,2.0D);
        System.out.println(flipFlop.value());
        System.out.println(flipFlop.flip());
    }
}

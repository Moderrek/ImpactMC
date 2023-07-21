import com.impact.lib.api.noise.WhiteNoise2D;

import java.util.Arrays;

public class NoiseTest {
    public static void main(String[] args) {
        float[][] tab = new WhiteNoise2D().generate(5, 5);
        Arrays.stream(tab).toList().forEach(l -> System.out.println(Arrays.toString(l)));
//        System.out.println();
    }
}

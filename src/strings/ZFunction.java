package strings;

import java.util.Arrays;

import static java.lang.Math.min;

public class ZFunction {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(z_function("abacaba")));
    }

    public static int[] z_function(String s) {
        int n = s.length();
        int[] z = new int[n];

        for (int i = 1, l = 0, r = 0; i < n; ++i) {
            if (i <= r) {
                z[i] = min(z[i - l], r - i + 1);
            }

            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                ++z[i];
            }

            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }

        return z;
    }

}

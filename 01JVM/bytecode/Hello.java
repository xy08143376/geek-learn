
public class Hello {

    public static void main(String[] args) {

        int a = 3;
        float b = 0.99f;
        long c = 10L;
        double d = 3.14D;

        if (c < 100L) {
            mul(c, 2L);
        }
        sub(b, 0.09f);


        for (int i = 0; i < 3; i++) {
            if (a < 10) {
                add(a, 3);
            }
        }
        div(a, d);

    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static float sub(float a, float b) {
        return a - b;
    }

    public static long mul(long a, long b) {
        return a * b;
    }

    public static double div(double a, double b) {
        return a / b;
    }

}
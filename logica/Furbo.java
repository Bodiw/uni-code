public class Furbo {
    public static void main(String[] args) {
        boolean T = false;
        boolean C = false;
        boolean V = false;
        boolean M = false;
        boolean H = false;
        boolean K = false;
        boolean R = false;
        boolean B = false;
        boolean A = false;
        boolean VI = false;
        boolean CA = false;
        boolean[] array = { T, C, V, M, H, K, R, B, A, VI, CA };

        System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "T", "C", "V", "M", "H",
                "K",
                "R", "B", "A", "VI", "CA", "EXPR");
        for (int i = 0; i < 2048; i++) {
            for (int j = 0; j < 11; j++) {
                array[j] = (i & (1 << j)) != 0;
            }
            // Camavinga lesionado
            // array[1] = false;

            // Tchouameni lesionado
            // array[0] = false;

            boolean res = false;
            if (res = expression(array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7],
                    array[8],
                    array[9], array[10])) {
                System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", array[0], array[1],
                        array[2], array[3], array[4], array[5], array[6], array[7], array[8], array[9], array[10], res);
            }
        }
    }

    public static boolean then(boolean a, boolean b) {
        return !a || b;
    }

    public static boolean thenBidirrectional(boolean a, boolean b) {
        return then(a, b) && then(b, a);
    }

    public static boolean expression(boolean T, boolean C, boolean V, boolean M, boolean H, boolean K, boolean R,
            boolean B, boolean A, boolean VI, boolean CA) {
        return (then(!(C && V), T)) && ((M && H && !K) || (H && !M && K)) && then(VI, (R && B)) && then(T, CA)
                && thenBidirrectional(!M, B) && then(!VI, (A && B));
    }
}

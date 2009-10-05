
import java.util.Arrays;

/**
 *
 * @author djk1076
 */
public class BFPhenotype implements Phenotype {

    private String bfCode;
    private String output;
    private BFPhenotype.Pair fitness;

    public BFPhenotype(
            byte[] bytes) {
        bfCode = BFInterpreter.bfToString(bytes);
        output = BFInterpreter.interpret(bfCode);
        fitness = new BFPhenotype.Pair(distance(output), bfCode.length());
    }

    public Comparable getFitness() {
        return fitness;
    }

    public static double distance(String s1) {
        if (s1.length() == 0) {
            return Double.MAX_VALUE;
        }
        while (s1.length() < BFInterpreter.desired.length()) {
            s1 += ".";
        }
        byte[] b1 = s1.getBytes();



        byte[] b2 = BFInterpreter.desired.getBytes();

        //System.out.println(Arrays.toString(b1));
        //System.out.println(Arrays.toString(b2));
        double distance = 0;
        for (int i = 0; i < b2.length; i++) {
            distance += (b1[i] - b2[i]) * (b1[i] - b2[i]);
        }

        for (int i = BFInterpreter.desired.length(); i < s1.length(); i++) {
            distance += b1[i] * b1[i];
        }
        return distance;

    }

    public String toString() {
        return fitness + ":" + /*bfCode +*/ ":" + output.length() + ":" + output;
    }

    private class Pair
            implements Comparable<Pair> {

        double x, y;

        public Pair(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Pair o) {
            if (x < o.x) {
                return 1;
            }
            if (x > o.x) {
                return -1;
            }
            if (x == 0 ) {
                return (int)(o.y-y);
            }

            return (int) (Math.random() * 10 - 5);



        }

        public String toString() {
            return "(" + x + ":" + y + ")";
        }
    }
}

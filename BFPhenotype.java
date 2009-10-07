
import java.util.Arrays;

/**
 *
 * @author djk1076
 */
public class BFPhenotype implements Phenotype {

    private String bfCode;
    private String output;
    private BFPhenotype.Fitness fitness;
public static void main(String args[]){
Comparable fit1=new BFPhenotype.Fitness("hello world",0);
Comparable fit2=new BFPhenotype.Fitness("",0);
Comparable fit3=new BFPhenotype.Fitness("asdfsadfasdfasdfsa",0);
System.out.println(fit1.compareTo(fit2));
System.out.println(fit2.compareTo(fit3));
}
    public BFPhenotype(byte[] bytes) {
        bfCode = BFInterpreter.bfToString(bytes);
        output = BFInterpreter.interpret(bfCode);
        fitness = new BFPhenotype.Fitness(output, bfCode.length());
    }

    public Comparable getFitness() {
        return fitness;
    }

    public static int distance(String s1) {
        if (s1.length() == 0) {
            return Integer.MAX_VALUE;
        }
        while (s1.length() < BFInterpreter.desired.length()) {
            s1 += (char)0;
        }
        byte[] b1 = s1.getBytes();



        byte[] b2 = BFInterpreter.desired.getBytes();

        int distance = 0;
        for (int i = 0; i < b2.length; i++) {
            distance += (b1[i] - b2[i]) * (b1[i] - b2[i]) * (b2.length - i) * (b2.length - i) * (b2.length - i) * (b2.length - i);
        }

        for (int i = BFInterpreter.desired.length(); i < s1.length(); i++) {
            distance += b1[i] * b1[i];
        }
        return distance;

    }

    public String toString() {
        return fitness + ":" + /*bfCode + ":" + output.length() +*/ ":" + output;
    }

    private static class Fitness implements Comparable<Fitness> {

        int[] vals;
        int len;

        public Fitness(String s, int len) {
            while (s.length() < BFInterpreter.desired.length()) {
                s += " ";
            }

            vals = new int[s.length()];
//System.out.println(s);
            for (int i = 0; i < BFInterpreter.desired.length(); i++) {
                vals[i] = (BFInterpreter.desired.charAt(i) - s.charAt(i));
if (vals[i]<256 && vals[i]>-256)
                vals[i] = vals[i] * vals[i];
else
vals[i]=Integer.MAX_VALUE;
if (vals[i]<0)System.out.println("problem"+(int)BFInterpreter.desired.charAt(i)+":"+(int)s.charAt(i));
            }
            for (int i = BFInterpreter.desired.length(); i < s.length(); i++) {
                vals[i] = Integer.MAX_VALUE;
            }

            this.len = len;
        }

        public int compareTo(Fitness o) {
if (len>2000)
return -1;
            int[] otherVal = o.vals;
            for (int i = 0; i < otherVal.length && i < vals.length; i++) {
		//System.out.println("abc");
                if (vals[i] > otherVal[i]) {
                    return -1;
                } else if (vals[i] < otherVal[i]) {
                    return 1;
                }
            }

            if (vals.length > BFInterpreter.desired.length()) {
		//System.out.println("here");
                return -1;
            } else if (vals.length < BFInterpreter.desired.length()) {
		//System.out.println("there");
                return 1;
            }

       //     if (Math.random() < .8) {
                return (o.len - len);
         //   } else {
           //     return (len - o.len);
            //}



        }

        public String toString() {
            return "(" + Arrays.toString(vals) + ":" + len + ")";
        }
    }
}

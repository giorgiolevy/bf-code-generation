import java.util.Arrays;

/**
 * BFPhenotype.java: Phenotype class for Brainf*ck project.
 *
 * @author djk1076
 */
public class BFPhenotype implements Phenotype {

    private String bfCode;
    private String output;
    private BFPhenotype.Fitness fitness;

     /**
     * ***********************************************
     * Constructors
     */

    public BFPhenotype(byte[] bytes) {
        bfCode = BFInterpreter.bfToString(bytes);
        output = BFInterpreter.interpret(bfCode);
        fitness = new BFPhenotype.Fitness(output, bfCode.length());
    }

    /**
     * ***********************************************
     * Methods
     */

    /**
     * Method: getFitness()
     *
     *  Accessor method for the object's fitness object.
     *
     */
    public Comparable getFitness() {
        return fitness;
    }

    /**
	* Method: getGraphVal()
	*
	* Method to get a graphable fitness value
	*/
    public int getGraphVal(){
	    return fitness.getGraphVal();
    }
    /**
     * Method: distance()
     *
     *  Method to compute difference between output and s1.
     *
     */
    public String toString() {
        return fitness + ":" + ":" + output;
    }

    /**
     * Private Class: Fitness 
     *
     *  Inner class to represent a phenotype's fitness. 
     *
     */
    private static class Fitness implements Comparable<Fitness> {

        int[] vals;
        int len;

        /**
         * ***********************************************
         * Constructors
         */

        public Fitness(String s, int len) {
            while (s.length() < BFGenerator.desired.length()) {
                s += (char)0;
            }

            vals = new int[s.length()];
            //System.out.println(s);
            for (int i = 0; i < BFGenerator.desired.length(); i++) {
                vals[i] = (BFGenerator.desired.charAt(i) - s.charAt(i));
                if (vals[i]<256 && vals[i]>-256)
                  vals[i] = vals[i] * vals[i];
                else
                  vals[i]=Integer.MAX_VALUE;                
            }
            for (int i = BFGenerator.desired.length(); i < s.length(); i++) {
                vals[i] = Integer.MAX_VALUE;
            }

            this.len = len;
        }

        /**
         * ***********************************************
         * Methods
         */

        /**
         * Method: compareTo()
         *
         *  Overriden method to make fitness objects comparable. 
         *
         */
        public int compareTo(Fitness o) {
          if (len>2000)
            return -1;
          int[] otherVal = o.vals;
          for (int i = 0; i < otherVal.length && i < vals.length; i++) {
            if (vals[i] > otherVal[i]) {
                return -1;
            } else if (vals[i] < otherVal[i]) {
                return 1;
            }
          }

          if (vals.length > BFGenerator.desired.length()) {
            return -1;
          } else if (vals.length < BFGenerator.desired.length()) {
            return 1;
          }

            return (o.len - len);
        }

	
	/**
	* Method: getGraphVal()
	*
	* Method to get a graphable fitness value
	*/
	public int getGraphVal(){
	  int j=0;
	  for (int i=0;i<vals.length;i++){
	  	if (vals[i]!=0){
			j=i;
			return j;
		}
	  }
	  return 0;	
	}
	
	
        /**
         * Method: toString()
         *
         *  Overriden toString() method. 
         *
         */
        public String toString() {
	   
          return ""+getGraphVal()+" (" + Arrays.toString(vals) + ":" + len + ")";
        }
    }
}

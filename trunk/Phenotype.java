
/**
 * Phenotype stores the actual expression of the genes, instead of the raw data
 * @author djk1076
 */
public interface Phenotype {

    /**
     *
     * @return the fitness of this phenotype
     */
    public Comparable getFitness();
    
    /**
    * Method: getGraphVal()
    *
    * Method to get a graphable fitness value
    */
    public int getGraphVal();
}

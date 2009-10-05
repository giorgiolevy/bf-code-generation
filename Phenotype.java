
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
}

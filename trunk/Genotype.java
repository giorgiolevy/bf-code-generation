
/**
 * Genotype represents the actual data storage for a genetic algorithm individual.
 * This is the data that is manipulated in the mutation and crossover functions
 * @author djk1076
 * @param <x> this should be the same as the class implementing the interface
 */
public interface Genotype<x extends Genotype> {

    /**
     *
     * @return the phenotype for this genotype
     */
    public Phenotype getPhenotype();

    /**
     *
     * @return a new Genotype by modifying this genotype
     */
    public Genotype mutate();

    /**
     * Create a new Genotype by crossing this genotype with another genotype of the same type
     * @param gene the gene to cross with
     * @return a new genotype
     */
    public Genotype crossover(x gene);

    /**
     *
     * @return the id of this genotype
     */
    public int getID();
}


import java.util.*;

/**
 *
 * @author djk1076
 */
public class HW1 {

    /**
     *
     * @param args
     */
    private static final int populationSize = 50;
    private static final int numGenerations = 10000;

    public static void main2(String args[]) {
        System.out.println(BFPhenotype.distance("eijiij"));
    }

    public static void main(String args[]) {
        List<Genotype> population = new LinkedList<Genotype>();//Note: do not use arraylist here, it is painfully slow

        //generate the random first generation
        for (int i = 0; i < populationSize; i++) {
            population.add(new BFGenotype("++++++++++++++++++++++++++++++++++++++...."));
        }

        Comparator<Genotype> geneComparor = new Comparator<Genotype>() {

            public int compare(Genotype g1, Genotype g2) {
                return g2.getPhenotype().getFitness().compareTo(g1.getPhenotype().getFitness());
            }
        };
long addtime=0;
long sorttime=0;
long subtime=0;
long gctime=0;
long addstart;
long sortstart;
long substart;
long gcstart;

        for (int generation = 1; generation <= numGenerations; generation++) {
System.out.println(addtime);
System.out.println(sorttime);
System.out.println(gctime);
addstart=System.currentTimeMillis();
            //printouts
            System.out.println("generation:" + generation);
            System.out.println("\tmin:" + population.get(9).getPhenotype().getFitness() + ", max:" + population.get(0).getPhenotype().getFitness());
            /* for (int i = 0; i < populationSize / 10; i++) {
            System.out.println("\t\t" + population.get(i).getPhenotype());
            }*/
            System.out.println("\t\t" + population.get(0).getPhenotype());
            System.out.println("\t\t" + population.get(1).getPhenotype());

            //do the crossover children
            for (int i = 0; i < populationSize; i += 2) {
                population.add(population.get(i).crossover(population.get(i + 1)));
            }

            //do the mutation children
            for (int i = 0; i < populationSize; i += 2) {
                population.add(population.get(i).mutate());
            }
addtime+=System.currentTimeMillis()-addstart;

sortstart=System.currentTimeMillis();

            //sort by fitness
            Collections.sort(population, geneComparor);
sorttime+=System.currentTimeMillis()-sortstart;


gcstart=System.currentTimeMillis();
System.gc();
gctime+=System.currentTimeMillis()-gcstart;

            //take only the top 10 individuals
            population = population.subList(0, populationSize);
	}
    }
}

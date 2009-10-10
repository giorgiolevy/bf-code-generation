
import java.util.*;
import java.io.*;
/**
 *
 * @author djk1076
 */
public class HW1 {

    /**
     *
     * @param args
     */
    private static final int populationSize = 200;
    private static final int numGenerations = 100;

    public static void main(String args[]) throws Exception{
        List<Genotype> population = new LinkedList<Genotype>();

        //generate the random first generation
        for (int i = 0; i < populationSize; i++) {
            population.add(new BFGenotype("++++++++++++++++++++++++++++.++."));
        }

        Comparator<Genotype> geneComparor = new Comparator<Genotype>() {

            public int compare(Genotype g1, Genotype g2) {
                return g2.getPhenotype().getFitness().compareTo(g1.getPhenotype().getFitness());
            }
        };
	BufferedWriter best=new BufferedWriter(new FileWriter(new File("best.txt")));
	BufferedWriter worst=new BufferedWriter(new FileWriter(new File("worst.txt")));
	
        for (int generation = 1; generation <= numGenerations; generation++) {
            //printouts
            if (generation % 20 == 0) {
                System.out.println("generation:" + generation);
                System.out.println("\t\t" +
                        population.get(0).getPhenotype() + " : " + ((BFGenotype) population.get(0)).getChangeType());
            //	    System.out.println("\t\t" + population.get(1).getPhenotype());
            }

            //do the crossover children
            for (int i = 0; i < populationSize >> 1; i += 1) {
                population.add(population.get(i).crossover(population.get(i + 1)));
            }
            for (int i = 0; i < populationSize >> 1; i++) {
                population.add(population.get(i).crossover(population.get(population.size() - i - 1)));
            }

            //do the mutation children
            for (int i = 0; i < populationSize >> 1; i += 1) {
                population.add(population.get(i).mutate());
            }


            //sort by fitness
            Collections.sort(population, geneComparor);



            //take only the top 10 individuals
            population = population.subList(0, populationSize);
            if (generation % 100 == 0) {
                List<Genotype> temp = new ArrayList(populationSize);
                temp.addAll(population);
                population = null;
                population = temp;
            }
	    best.write(""+generation+":"+population.get(0).getPhenotype()+"\n");
	    worst.write(""+generation+":"+population.get(population.size()-1).getPhenotype()+"\n");
        }
	best.flush();
	best.close();
	worst.flush();
	worst.close();
        System.out.println(population.get(0));
    }
}


import java.util.*;
import java.io.*;
/**
 *
 * @author djk1076
 */
public class BFGenerator {

    /**
     *
     * @param args: args[0]: [optional] desired output
     */
    private static final int populationSize = 200;
    private static final int numGenerations = 100;
    public static String desired="hello world";

    public static void main(String args[]) throws Exception{
	if (args.length>0)
	    desired=args[0];
	    
        List<Genotype> population = new ArrayList<Genotype>();

        //generate the random first generation
        for (int i = 0; i < populationSize; i++) {
	   String rand="";
	   int len=(int)(Math.random()*100);
	   for (int j=0;j<len;j++)
		   rand+="+";
	   for (int j=0;j<5;j++){
		   int index=(int)(Math.random()*rand.length());
		   rand=rand.substring(0,index)+"."+rand.substring(index,rand.length());
	   }
            population.add(new BFGenotype(rand));//seed with something that will produce some ascii values
        }

        Comparator<Genotype> geneComparor = new Comparator<Genotype>() {

            public int compare(Genotype g1, Genotype g2) {
                return g2.getPhenotype().getFitness().compareTo(g1.getPhenotype().getFitness());
            }
        };
	BufferedWriter best=new BufferedWriter(new FileWriter(new File("best.txt")));
	BufferedWriter median=new BufferedWriter(new FileWriter(new File("median.txt")));
	BufferedWriter worst=new BufferedWriter(new FileWriter(new File("worst.txt")));
	
        for (int generation = 1; generation <= numGenerations; generation++) {
            //printouts
            if (generation % 10 == 0) {
                System.out.println("generation:" + generation);
                System.out.println("\t\t" +
                        population.get(0).getPhenotype() + " : " + ((BFGenotype) population.get(0)).getChangeType());
            }

            //do the crossover children
            for (int i = 0; i < populationSize >> 1; i += 1) {
                population.add(population.get(i).crossover(population.get(i + 1)));
            }
	    //try 'bad' crossovers
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
            if (generation % 100 == 0) {//prevents major memory leaks by redoing array
                List<Genotype> temp = new ArrayList(populationSize);
                temp.addAll(population);
                population = null;
                population = temp;
            }
	    best.write(""+generation+":"+population.get(0).getPhenotype()+"\n");
	    median.write(""+generation+":"+population.get(population.size()/2).getPhenotype()+"\n");
	    worst.write(""+generation+":"+population.get(population.size()-1).getPhenotype()+"\n");
        }
	best.flush();
	best.close();
	median.flush();
	median.close();
	worst.flush();
	worst.close();
        System.out.println(population.get(0));
    }
}

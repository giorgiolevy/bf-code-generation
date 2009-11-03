
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
    private static int populationSize = 200;
    private static int numGenerations = 100;
    public static String desired="hello world";

    public static double mut1 = 0.33;
    public static double mut2 = 0.33;
    public static double cross1 = 0.5;

    public static void main(String args[]) throws Exception{
	if (args.length>0) {
    if(args.length == 6){
	    desired=args[0];
      mut1=Double.parseDouble(args[1]);  
      mut2=Double.parseDouble(args[2]);  
      cross1=Double.parseDouble(args[3]);  
      populationSize=Integer.parseInt(args[4]);
      numGenerations=Integer.parseInt(args[5]);
    }
    else{
      System.out.println("Usage: java BFGenerator <desired string> <mutation 1 rate> " +
                         "<mutation rate 2> <crossover rate 1> <max population> <max generations>");
      System.exit(1);
    }
	} 
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
        String beststr = "best-" + args[1] + "-" + args[2] + "-" + args[3] + "-" + populationSize + "-" + numGenerations + ".txt"; 
        String medianstr = "median-" + args[1] + "-" + args[2] + "-" + args[3] + "-" + populationSize + "-" + numGenerations + ".txt"; 
        String worststr = "worst-" + args[1] + "-" + args[2] + "-" + args[3] + "-" + populationSize + "-" + numGenerations + ".txt"; 
	BufferedWriter best = new BufferedWriter(new FileWriter(new File(beststr)));
	BufferedWriter median = new BufferedWriter(new FileWriter(new File(medianstr)));
	BufferedWriter worst = new BufferedWriter(new FileWriter(new File(worststr)));
	
        for (int generation = 1; generation <= numGenerations; generation++) {
            //printouts
            if (generation % 10 == 0) {
                System.out.println("generation:" + generation);
                System.out.println("\t\t" +
                        population.get(0).getPhenotype() + " : " + ((BFGenotype) population.get(0)).getChangeType());
            }

            //do the crossover children
            for (int i = 0; i < populationSize >> 1; i++) {
                population.add(population.get(i).crossover(population.get(i + 1)));
            }
	    //try 'bad' crossovers
            for (int i = 0; i < populationSize >> 1; i++) {
                population.add(population.get(i).crossover(population.get(populationSize - i - 1)));
            }

            //do the mutation for 'good' children
            for (int i = 0; i < populationSize >> 1; i++) {
                population.add(population.get(i).mutate());
            }
	    
	    //do the mutation for 'bad' children
            for (int i = 1; i < populationSize >> 1; i++) {
                population.add(population.get(populationSize-i).mutate());
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
	    best.write(""+generation+","+population.get(0).getPhenotype().getGraphVal()+"\n");
	    median.write(""+generation+","+population.get(population.size()/2).getPhenotype().getGraphVal()+"\n");
	    worst.write(""+generation+","+population.get(population.size()-1).getPhenotype().getGraphVal()+"\n");
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

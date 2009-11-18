
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

    public static int mut1 = 1;
    public static int mut2 = 1;
    public static int mut3 = 1;
    public static int mutTotal;
    public static double cross1 = 0.5;

    public static void main(String args[]) throws Exception{
	if (args.length>0) {
    if(args.length == 7){
	    desired=args[0];
      mut1=Integer.parseInt(args[1]);  
      mut2=Integer.parseInt(args[2]);
      mut3=Integer.parseInt(args[3]);
      cross1=Double.parseDouble(args[4]);  
      populationSize=Integer.parseInt(args[5]);
      numGenerations=Integer.parseInt(args[6]);
    }
    else{
      System.out.println("Usage: java BFGenerator <desired string> <mutation 1 rate> " +
                         "<mutation rate 2> <mutation rate 3> <crossover rate 1> " + 
                         "<max population> <max generations>");
      System.exit(1);
    }
	} else{
		args=new String[6];
		args[4]=".5";
	}
	mutTotal=mut1+mut2+mut3;
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
        String filestr =  mut1 + "-" + mut2 +"-"+mut3+"-"+args[4] + "-" + populationSize + "-" + 
numGenerations + ".csv"; 
        BufferedWriter graph=new BufferedWriter(new FileWriter(new File("graph-"+filestr)));
	
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
	    graph.write(""+generation+","+population.get(0).getPhenotype().getGraphVal()+","+population.get(population.size()/2).getPhenotype().getGraphVal()+","+population.get(population.size()-1).getPhenotype().getGraphVal()+"\n");
        }
	graph.flush();
	graph.close();
        System.out.println(population.get(0));
    }
}

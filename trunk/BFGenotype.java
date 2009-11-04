import java.util.Arrays;

/**
 * BFGenotype.java: Genotype class for the Brainf*ck project.
 *
 * @author djk1076
 */
public class BFGenotype implements Genotype<BFGenotype> {

    private byte[] genes;
    private Phenotype phenotype;
    private int id;
    private static int nextID = 0;

    private enum ChangeType {

        none, insert, delete, change, singleCross,doubleCross
    };

    private ChangeType changeType = ChangeType.none;

    /**
     * ***********************************************
     * Constructors
     */

    public BFGenotype(byte[] bytes) {
        this.genes = bytes.clone();
        phenotype = new BFPhenotype(genes);
        id = nextID++;
    }

    public BFGenotype(byte[] bytes, ChangeType changeType) {
        this.changeType = changeType;
        this.genes = bytes.clone();
        phenotype = new BFPhenotype(genes);
        id = nextID++;
    }

    public BFGenotype(String s) {
        genes = BFInterpreter.stringToBF(s);
        phenotype = new BFPhenotype(genes);
        id = nextID++;
    }

    /**
     * ***********************************************
     * Methods
     */

    /**
     * Method: getPhenotype()
     *  
     *  Accessor method for the object's phenotype object.
     *
     */
    public Phenotype getPhenotype() {
        return phenotype;
    }

    public Genotype mutate() {
	    int rand=(int)(Math.random()*BFGenerator.mutTotal);
	    
	    if (rand<BFGenerator.mut1)
		    return insert();
	    if (rand<(BFGenerator.mut1+BFGenerator.mut2))
		    return delete();
	    return change();
    }

    /**
     * Method: insert()
     *  
     *  Insertion mutation method.  Randomly inserts a new gene into genotype. 
     *
     */
    private Genotype insert() {
        byte[] newBytes = new byte[genes.length + 1];
        int rand = (int) (Math.random() * newBytes.length);
        System.arraycopy(genes, 0, newBytes, 0, rand);
        System.arraycopy(genes, rand, newBytes, rand + 1, genes.length - rand);
        newBytes[rand] = (byte) (Math.random() * 5);//TODO: should be 7, if using while loops
        return new BFGenotype(newBytes, ChangeType.insert);
    }

    /**
     * Method: delete()
     *  
     *  Deletion mutation method.  Randomly deletes a gene from genotype. 
     *
     */
    private Genotype delete() {
        if (genes.length == 0) {
            return this;
        }
        byte[] newBytes = new byte[genes.length - 1];
        int rand = (int) (Math.random() * newBytes.length);
        System.arraycopy(genes, 0, newBytes, 0, rand);
        System.arraycopy(genes, rand + 1, newBytes, rand, genes.length - rand - 1);
        return new BFGenotype(newBytes, ChangeType.delete);
    }

    /**
     * Method: change()
     *  
     *  Change mutation method.  Randomly changes an existing gene in genotype. 
     *
     */
    private Genotype change() {
        if (genes.length == 0) {
            return this;
        }
        byte[] newBytes = new byte[genes.length - 1];
        if (newBytes.length == 0) {
            return this;
        }
        int rand = (int) (Math.random() * (newBytes.length - 1));
        System.arraycopy(genes, 0, newBytes, 0, newBytes.length);
        newBytes[rand] = (byte) (Math.random() * 5);//TODO: should be 7, if using while loops
        return new BFGenotype(newBytes, ChangeType.change);
    }

    /**
     * Method: crossover()
     *  
     *  Crossover method.  Cross this genotype with another. 
     *
     */
    public Genotype crossover(BFGenotype other) {
	    if (Math.random()<BFGenerator.cross1){
		    return singleCross(other);
	    }else{
		    return doubleCross(other);
	    }
    }

    
    /**
    * Method: doubleCross()
    *
    * Double crossover method. Use double crossover on two genotypes.
    * Replaces section (rand11-rand12) with other(rand21-rand22)
    * 
    */
    private Genotype doubleCross(BFGenotype other){
	    int rand11 = (int) (Math.random() * genes.length);
	    int rand12 = Math.min((int)(rand11+Math.random()*genes.length),genes.length-1);
	    int rand21 = (int) (Math.random() * other.genes.length);
	    int rand22 = Math.min((int)(rand21+Math.random()*other.genes.length),other.genes.length-1);
	    byte[] newBytes=new byte[rand11+ (rand22-rand21)+(genes.length-rand12)];
	    
	    //copy start and end from current
	    System.arraycopy(genes,0,newBytes,0,rand11);//0-rand11
	    System.arraycopy(genes,rand12,newBytes,newBytes.length-(genes.length-rand12),genes.length-rand12); //(rand12-end)
	    
	    //copy middle section from other
	    System.arraycopy(other.genes,rand21,newBytes,rand11,rand22-rand21);//(rand21-rand22)
	    
	    return new BFGenotype(newBytes,ChangeType.doubleCross);
    }
    
    
    /**
     * Method: singleCross()
     *  
     *  Single crossover method.  Use single point crossover on two genotypes. 
     *
     */
    private Genotype singleCross(BFGenotype other) {
        int rand1 = (int) (Math.random() * genes.length);
        int rand2 = (int) (Math.random() * other.genes.length);

        byte[] newBytes = new byte[rand1 + other.genes.length - rand2];
        System.arraycopy(genes, 0, newBytes, 0, rand1);
        System.arraycopy(other.genes, rand2, newBytes, rand1, newBytes.length - rand1);
        return new BFGenotype(newBytes, ChangeType.singleCross);
    }

    /**
     * Method: getID()
     *  
     *  Accessor method for the object's ID.
     *
     */
    public int getID() {
        return id;
    }

    /**
     * Method: getChangeType()
     *  
     *  Accessor method for the object's change type enum.
     *
     */
    public ChangeType getChangeType() {
        return changeType;
    }

    /**
     * Method: toSting()
     *  
     *  Overriden toString() method.
     *
     */
    public String toString() {
	return "" + id + ":" + BFInterpreter.bfToString(genes);
    }
}

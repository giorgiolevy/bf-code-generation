
import java.util.Arrays;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author djk1076
 */
public class BFGenotype implements Genotype<BFGenotype> {

    private byte[] genes;
    private Phenotype phenotype;
    private int id;
    private static int nextID = 0;

    public BFGenotype(byte[] bytes) {
        this.genes = bytes.clone();
        phenotype = new BFPhenotype(genes);
        id = nextID++;
    }

    public BFGenotype(String s) {
        genes = BFInterpreter.stringToBF(s);
        phenotype = new BFPhenotype(genes);
        id = nextID++;
    }

    public Phenotype getPhenotype() {
        return phenotype;
    }

    public Genotype mutate() {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return insert();
            case 1:
                return delete();
            default:
                return change();
        }
    }

    private Genotype insert() {
        byte[] newBytes = new byte[genes.length + 1];
        int rand = (int) (Math.random() * newBytes.length);
        System.arraycopy(genes, 0, newBytes, 0, rand);
        System.arraycopy(genes, rand, newBytes, rand + 1, genes.length - rand);
        newBytes[rand] = (byte) (Math.random() * 7);
        return new BFGenotype(newBytes);
    }

    private Genotype delete() {
        if (genes.length == 0) {
            return this;
        }
        byte[] newBytes = new byte[genes.length - 1];
        int rand = (int) (Math.random() * newBytes.length);
        System.arraycopy(genes, 0, newBytes, 0, rand);//todo: double check the indices here
        System.arraycopy(genes, rand + 1, newBytes, rand, genes.length - rand - 1);
        return new BFGenotype(newBytes);
    }

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
        newBytes[rand] = (byte) (Math.random() * 7);
        return new BFGenotype(newBytes);
    }

    public Genotype crossover(BFGenotype other) {

        //  if (Math.random() > .5) {
        return singleCross(other);
    //} else {
    //  return doubleCross(other);
    //}

    }

    private Genotype singleCross(BFGenotype other) {
        int rand1 = (int) (Math.random() * genes.length);
        int rand2 = (int) (Math.random() * other.genes.length);

        byte[] newBytes = new byte[rand1 + other.genes.length - rand2];
        System.arraycopy(genes, 0, newBytes, 0, rand1);
        System.arraycopy(other.genes, rand2, newBytes, rand1, newBytes.length - rand1);
        return new BFGenotype(newBytes);
    }

    public int getID() {
        return id;
    }

    public String toString() {
        return "" + id + Arrays.toString(genes);
    }
}

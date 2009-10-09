import java.util.*;

/**
 * BFInterpreter.java: Brainf*ck code interpreter for the Brainf*ck project.
 *
 * @author djk1076
 */
public class BFInterpreter {

    static String desired = "hello world";
    static byte[] code;
    /*
    >:move right
    <:move left
    +:increment
    -:decrement
    .:print char
    ,:read char(ignored)
    [: while !=0
    ]: end while
     */

    /**
     * ***********************************************
     * Methods
     */

    /**
     * Method: main()
     *
     *  Main method, runs test on the interpreter.
     *
     */

    public static void main(String args[]) {
        System.out.println(System.currentTimeMillis());
        String test;// = "+++++++++ +d[g>q+e+v+b+f+h+t+u>k+h+d+6+3+78+9+3+2+er+>c+z+b+m>g+s<w<er<y<ui-yt]e>+3+4.2>6+d.b+d+f+ert++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
        //test = "+++++++++++++++[>+++++++>++++++++>+++++++>++<<<<-]>-.>>.>++.<<----.>-.---.<--.>.>.<<++.<.+.>-.>>.<++++.<.>>.<<<--------.>>>.<<-.<.>----.<+++.>+.>++++.>.<<--.--------.>++++++..<----.<+++.--.>>>.<-.<.>----.<<-.>>+.--.>.+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.+.<-.-.>++++++++.-.-.<++++.<<.>>++++.<+.<.--.>>>++.+.<<-.+++.>.>-.<<.>+++.---.<.>+++.<.<.>>>.+.<<.>.<+++++++++++++++.<+++.>>+.<++.------.<.>>.<++.<.>>.------.---------------------------------------------------------.+.------.-.++++++.+.------.+.++++.+.>-----.+++.<<<-.++.----.>>>+.<<++.<.>>>.<<+++.<++++..--.";
        test = "++++++++++[>+++>++++++++++>+++++++++++>++++++++++++[<]>-]>>++++.>+.>-.<<<++.>>>.<<---.>---..<<.>-.>+++.<+.>++++.<<.>>>---.<<+++.+.>.<<.>>>+++.<----.+++.-------.<<.>>>---.<++++.<<.>-----.>.<<.>>---.+++..+.+++.";
        System.out.println(System.currentTimeMillis());


        System.out.println(System.currentTimeMillis());
        System.out.println(interpret(test));
        System.out.println(System.currentTimeMillis());
    }

    /**
     * Method: subInterpret()
     *
     *  <David fill in here>.
     *
     */
    private static String subInterpret(String bf) {
        ArrayList<Integer> tape = new ArrayList() {

            public Integer get(int index) {//prevent index errors on the left side of the tape
                if (index < 0) {
                    return 0;
                }
                if (index >= this.size()) {
                    return 0;
                }
                return (Integer) super.get(index);
            }

            public Integer set(int index, Object value) {
                while (index > size()) {
                    this.add(0);
                }
                if (index < 0) {
                    index = 0;
                }
                return (Integer) super.set(index, value);
            }
        };
        tape.add(0);
        Stack<Integer> whiles = new Stack<Integer>();
        int command = 1;
        StringBuilder out = new StringBuilder();
        int bfHead = 0;
        int tapeHead = 0;
        int numWhiles = 0;
        while (bfHead < bf.length()) {
            //System.out.print(command + " (" + bfHead + "): " + tape + " ");
            command++;
            switch (bf.charAt(bfHead)) {
                case '>'://>
                    tapeHead++;
                    while (tapeHead >= tape.size()) {
                        tape.add(0);
                    }
                    break;
                case '<'://<
                    tapeHead--;
                    break;
                case '+'://+
                    while (tapeHead >= tape.size()) {
                        tape.add(0);
                    }
                    tape.set(tapeHead, tape.get(tapeHead) + 1);
                    break;
                case '-'://-
                    while (tapeHead >= tape.size()) {
                        tape.add(0);
                    }
                    tape.set(tapeHead, tape.get(tapeHead) - 1);
                    break;
                case '.'://.
                    while (tapeHead >= tape.size()) {
                        tape.add(0);
                    }
                    out.append((char) tape.get(tapeHead).byteValue());
                    break;
                case '['://[
                    if (tape.get(tapeHead) != 0) {
                        whiles.push(bfHead);
                    } else {
                        while (bfHead < bf.length() && bf.charAt(bfHead) != ']') {//]
                            bfHead++;
                        }
                    }
                    numWhiles++;
                    if (numWhiles > 500) {
                        return out.toString();
                    }
                    break;
                case ']'://]
                    if (tape.get(tapeHead) == 0) {//this isn't quite the specs on wikipedia, but it is used in some code generated by other places, so is kept for consistency
                        if (!whiles.empty()) {
                            whiles.pop();
                        }
                    } else {
                        if (!whiles.empty()) {
                            bfHead = whiles.pop() - 1;
                        } else {
                            // System.out.println(command + ":" + bfHead);
                            // System.out.println("to many end whiles");
                        }
                    }
                    break;
                default:
                    break;
            }
            bfHead++;
        }
        return out.toString();
    }

    /**
     * Method: interpret()
     *
     *  Method to interpret Brainf*ck code..
     *
     */
    public static String interpret(final String bf) {
        return subInterpret(bf);
    }

    /**
     * Method: stringToBF()
     *
     *  Converts a string of BrainF*ck code to an array of bytes. 
     *
     */
    public static byte[] stringToBF(String s) {

        byte[] ret = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {

            switch (s.charAt(i)) {
                case '>':
                    ret[i] = 0;
                    break;
                case '<':
                    ret[i] = 1;
                    break;
                case '+':
                    ret[i] = 2;
                    break;
                case '-':
                    ret[i] = 3;
                    break;
                case '.':
                    ret[i] = 4;
                    break;
                case '[':
                    ret[i] = 5;
                    break;
                case ']':
                    ret[i] = 6;
                    break;
                default:
                    ret[i] = 7;
                    break;
            }

        }
        return ret;
    }

    /**
     * Method: bfToString()
     *
     *  Converts an array of bytes to BrainF*ck code. 
     *
     */
    public static String bfToString(byte[] b) {
        StringBuilder ret = new StringBuilder(b.length);
        for (byte byt : b) {
            switch (byt) {
                case 0:
                    ret.append(">");
                    break;
                case 1:
                    ret.append("<");
                    break;
                case 2:
                    ret.append("+");
                    break;
                case 3:
                    ret.append("-");
                    break;
                case 4:
                    ret.append(".");
                    break;
                case 5:
                    ret.append("[");
                    break;
                case 6:
                    ret.append("]");
                    break;
                default:
                    break;
            }

        }
        return ret.toString();
    }
}

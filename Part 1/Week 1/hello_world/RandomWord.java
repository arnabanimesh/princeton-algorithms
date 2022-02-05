import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int count = 0;
        String out = "";
        while (!StdIn.isEmpty()) {
            count++;
            String in = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / (double) count)) {
                out = in;
            }
        }
        StdOut.println(out);
    }
}

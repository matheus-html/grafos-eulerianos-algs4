import src.Graph;
import src.In;
import src.StdOut;

public class Main {
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
    }
    
}

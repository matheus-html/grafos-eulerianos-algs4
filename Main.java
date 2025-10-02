import src.Graph;
import src.Hierholzer;
import src.In;
import src.Stack;
import src.StdOut;

public class Main {
    public static void main(String[] args) {

        In in = new In("data/desconexo.txt");
        Graph G = new Graph(in);
        
        Hierholzer euler = new Hierholzer(G);

        if (euler.temCircuito()) {
            Stack<Integer> caminhoResultado = (Stack<Integer>) euler.circuito();
             while (!caminhoResultado.isEmpty()) {
                StdOut.print(caminhoResultado.pop() + (caminhoResultado.isEmpty() ? "" : " "));
            }
            StdOut.println();
        } else {
            StdOut.println(euler.obterRazao());
        }  
    } 
}

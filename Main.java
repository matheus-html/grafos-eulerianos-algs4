import src.Graph;
import src.Hierholzer;
import src.Fleury;
import src.In;
import src.Stack;
import src.StdOut;

public class Main {
    public static void main(String[] args) {

        String[] arquivosParaTestar = {
            "data/c4.txt",
            "data/c4_isolado9.txt",
            "data/desconexo.txt",
            "data/caminho_triangulo.txt",
            "data/konigsberg.txt"
        };

        for (String arquivo : arquivosParaTestar) {
            StdOut.println("Analisando grafo: " + arquivo);
            
            In in = new In(arquivo);
            Graph G = new Graph(in);
            
            Hierholzer hierholzer = new Hierholzer(G);
            
            if (hierholzer.temCircuito()) {
                StdOut.println("  Circuito Euleriano Encontrado:");
                Fleury fleury = new Fleury(G);

                StdOut.print("    Hierholzer: ");
                imprimirPilhaInvertida(hierholzer.circuito());
                
                StdOut.print("    Fleury:     ");
                imprimirPilhaOrdenada(fleury.circuito());
                
            } else {
                StdOut.println("  " + hierholzer.obterRazao());
            }
            StdOut.println();
        }
    }

    private static void imprimirPilhaInvertida(Iterable<Integer> circuito) {
        Stack<Integer> original = (Stack<Integer>) circuito;
        Stack<Integer> invertida = new Stack<>();
        for (int vertice : original) {
            invertida.push(vertice);
        }
        while(!invertida.isEmpty()) {
            StdOut.print(invertida.pop() + (invertida.isEmpty() ? "" : " "));
        }
        StdOut.println();
    }
    
    private static void imprimirPilhaOrdenada(Iterable<Integer> circuito) {
        Stack<Integer> original = (Stack<Integer>) circuito;
        Stack<Integer> ordemCorreta = new Stack<>();
        while(!original.isEmpty()){
            ordemCorreta.push(original.pop());
        }
        
        while (!ordemCorreta.isEmpty()) {
            StdOut.print(ordemCorreta.pop() + (ordemCorreta.isEmpty() ? "" : " "));
        }
        StdOut.println();
    }
}
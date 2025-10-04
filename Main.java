import src.Graph;
import src.Hierholzer;
import src.Fleury;
import src.In;
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
            StdOut.println("\nAnalisando grafo: " + arquivo);
            
            In in = new In(arquivo);
            Graph G = new Graph(in);
            
            Hierholzer hierholzer = new Hierholzer(G);
            
            if (hierholzer.temCircuito()) {
                StdOut.println("Circuito Euleriano Encontrado:");
                Fleury fleury = new Fleury(G);

                StdOut.print("Hierholzer: ");
                StdOut.println(hierholzer.circuito());
                
                StdOut.print("Fleury: ");
                StdOut.print(fleury.circuito());
                
            } else {
                StdOut.println(hierholzer.obterErro());
            }
            StdOut.println();
        }
    }
}
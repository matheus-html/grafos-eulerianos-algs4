package src;

import java.util.HashSet;

public class Hierholzer extends CircuitoEuleriano {

    public Hierholzer(Graph G) {
        super(G);
        
        if (!super.temCircuito()) {
            return;
        }
        
        HashSet<Integer>[] adjSets = (HashSet<Integer>[]) new HashSet[G.V()];

        for (int v = 0; v < G.V(); v++) {
            adjSets[v] = new HashSet<Integer>();
        }

        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                adjSets[v].add(w);
            }
        }

        int verticeInicial = -1;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) {
                verticeInicial = v;
                break;
            }
        }
        
        Stack<Integer> caminho = new Stack<>();
        caminho.push(verticeInicial);
        
        Stack<Integer> circuitoFinal = new Stack<>();

        while (!caminho.isEmpty()) {
            int u = caminho.peek();
            
            if (!adjSets[u].isEmpty()) {
                int v = adjSets[u].iterator().next();
                caminho.push(v);
                
                adjSets[u].remove(v);
                adjSets[v].remove(u);
            } else {
                circuitoFinal.push(caminho.pop());
            }
        }
        
        super.definirCircuito(circuitoFinal);
    }
}
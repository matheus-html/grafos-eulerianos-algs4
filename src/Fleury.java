package src;

import java.util.Iterator;

public class Fleury extends CircuitoEuleriano {

    
    public Fleury(Graph G) {
        super(G);

        if (!super.temCircuito()) {
            return;
        }

        Graph tempG = new Graph(G);
        
        int verticeInicial = -1;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) {
                verticeInicial = v;
                break;
            }
        }

        if (verticeInicial == -1) {
            super.definirCircuito(new Stack<Integer>()); 
            return;
        }

        Stack<Integer> circuitoFinal = new Stack<>();
        int u = verticeInicial;
        circuitoFinal.push(u);

        while (tempG.E() > 0) {
            int vSelecionado = -1;
            Iterator<Integer> it = tempG.adj(u).iterator();

            while (it.hasNext()) {
                int v = it.next();
                if (tempG.degree(u) == 1) {
                    vSelecionado = v;
                    break; 
                }
                if (!ehPonte(tempG, u, v)) {
                    vSelecionado = v;
                    break;
                }
            }
            
            if (vSelecionado != -1) {
                tempG.removeEdge(u, vSelecionado);
                
                circuitoFinal.push(vSelecionado);
                u = vSelecionado;
            } else {
                break; 
            }
        }
        
        super.definirCircuito(circuitoFinal);
    }
    private boolean ehPonte(Graph g, int u, int v) {
        Graph gCopy = new Graph(g);
        
        gCopy.removeEdge(u, v);
        int contagemOriginal = contarAlcancaveis(g, u);
        
        int contagemNova = contarAlcancaveis(gCopy, u);
        return contagemNova < contagemOriginal && gCopy.degree(v) > 0;
    }

    private int contarAlcancaveis(Graph g, int inicio) {
        boolean[] marcado = new boolean[g.V()];
        Queue<Integer> q = new Queue<>(); // BFS usa uma fila
        
        if (g.degree(inicio) == 0) return 0;
        
        q.enqueue(inicio);
        marcado[inicio] = true;
        int contador = 1;

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : g.adj(v)) {
                if (!marcado[w]) {
                    marcado[w] = true;
                    q.enqueue(w);
                    contador++;
                }
            }
        }
        return contador;
    }
}
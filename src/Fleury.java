package src;

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

        Stack<Integer> circuitoFinal = new Stack<>();
        if (verticeInicial == -1) {
            super.definirCircuito(circuitoFinal);
            return;
        }

        int u = verticeInicial;
        circuitoFinal.push(u);

        while (tempG.E() > 0) {
            int proximoVertice = -1;

            for (int v : tempG.adj(u)) {
                if (tempG.degree(u) == 1 || !ehPonte(tempG, u, v)) {
                    proximoVertice = v;
                    break;
                }
            }
            
            if (proximoVertice == -1 && tempG.degree(u) > 0) {
                proximoVertice = tempG.adj(u).iterator().next();
            }

            if (proximoVertice != -1) {
                circuitoFinal.push(proximoVertice);
                tempG.removeEdge(u, proximoVertice);
                u = proximoVertice;
            } else {
                break;
            }
        }
        
        super.definirCircuito(circuitoFinal);
    }

    private boolean ehPonte(Graph g, int u, int v) {
        int contagemOriginal = contarAlcancaveis(g, u);
        
        g.removeEdge(u, v);
        int contagemNova = contarAlcancaveis(g, u);
        g.addEdge(u, v);

        return contagemNova < contagemOriginal;
    }

    private int contarAlcancaveis(Graph g, int inicio) {
        boolean[] marcado = new boolean[g.V()];
        Queue<Integer> q = new Queue<>();
        
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
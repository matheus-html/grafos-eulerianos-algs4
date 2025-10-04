package src;

public abstract class CircuitoEuleriano {

    private Stack<Integer> circuito;
    private String erro;

    protected CircuitoEuleriano(Graph G) {
        if (G.E() == 0) {
            this.circuito = new Stack<>();
            this.erro = null;
            return;
        }

        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                this.erro = "Grafo não euleriano: vértice " + v + " tem grau ímpar.";
                this.circuito = null;
                return;
            }
        }
        
        int noInicial = -1;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) {
                noInicial = v;
                break;
            }
        }

        if (noInicial != -1) {
            BreadthFirstPaths bfs = new BreadthFirstPaths(G, noInicial);
            for (int v = 0; v < G.V(); v++) {
                if (G.degree(v) > 0 && !bfs.hasPathTo(v)) {
                    this.erro = "Grafo não euleriano: o subgrafo com arestas é desconexo.";
                    this.circuito = null;
                    return;
                }
            }
        }

        this.erro = null;
    }

    public boolean temCircuito() {
        return erro == null;
    }

    public Iterable<Integer> circuito() {
        return this.circuito;
    }

    public String obterErro() {
        return erro;
    }
    
    protected void definirCircuito(Stack<Integer> circuito) {
        this.circuito = circuito;
    }
}
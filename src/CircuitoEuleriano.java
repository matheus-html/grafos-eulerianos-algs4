package src;

public abstract class CircuitoEuleriano {
    
    private Stack<Integer> circuito;
    private final String razao;

    protected CircuitoEuleriano(Graph G) {
        if (G.E() == 0) {
            this.circuito = new Stack<>();
            this.razao = null;
            return;
        }

        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                this.razao = "Grafo não euleriano: vértice " + v + " tem grau ímpar.";
                this.circuito = null;
                return;
            }
        }

        if (!ehConexo(G)) {
            this.razao = "Grafo não euleriano: desconexo.";
            this.circuito = null;
            return;
        }

        this.razao = null;
    }

    private boolean ehConexo(Graph G) {
        boolean[] marcado = new boolean[G.V()];
        int noInicial = -1;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) {
                noInicial = v;
                break;
            }
        }
        
        if (noInicial == -1) return true; 

        Queue<Integer> q = new Queue<>();
        q.enqueue(noInicial);
        marcado[noInicial] = true;
        int contador = 1;

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marcado[w]) {
                    marcado[w] = true;
                    q.enqueue(w);
                    contador++;
                }
            }
        }

        int verticesNaoIsolados = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) > 0) {
                verticesNaoIsolados++;
            }
        }
        
        return contador == verticesNaoIsolados;
    }

    public boolean temCircuito() {
        return razao == null;
    }

    public Iterable<Integer> circuito() {
        return this.circuito;
    }

    public String obterRazao() {
        return razao;
    }
    
    protected void definirCircuito(Stack<Integer> circuito) {
        this.circuito = circuito;
    }
}

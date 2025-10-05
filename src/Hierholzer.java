package src;

import src.Stack;

public class Hierholzer extends CircuitoEuleriano {

    public Hierholzer(Graph G) {
        super(G);

        if (!temCircuito()) return;

        Graph grafoCopia = new Graph(G);
        Stack<Integer> path = new Stack<>();
        Stack<Integer> circuitoReverso = new Stack<>();

        int vInicial = -1;
        for (int v = 0; v < grafoCopia.V(); v++) {
            if (grafoCopia.degree(v) > 0) {
                vInicial = v;
                break;
            }
        }

        if (vInicial == -1) {
            definirCircuito(new Stack<>());
            return;
        }

        int v = vInicial;
        path.push(v);

        while (!path.isEmpty()) {
            if (grafoCopia.degree(v) > 0) {
                path.push(v);
                int w = grafoCopia.adj(v).iterator().next();
                grafoCopia.removeEdge(v, w);
                v = w;
            } else {
                circuitoReverso.push(v);
                v = path.pop();
            }
        }

        Stack<Integer> circuito = new Stack<>();
        while (!circuitoReverso.isEmpty()) {
            circuito.push(circuitoReverso.pop());
        }

        definirCircuito(circuito);
    }
}

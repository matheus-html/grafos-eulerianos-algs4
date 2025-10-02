package src;

public class Fleury extends CircuitoEuleriano {

    public Fleury(Graph G) {
        super(G);

        if (!super.temCircuito()) {
            return;
        }

        // A lógica do algoritmo de Fleury (com pontes) entraria aqui.
        // Por enquanto, apenas cria uma pilha vazia.
        super.definirCircuito(new Stack<Integer>()); 
    }
}

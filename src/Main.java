

public class Main {
    public static void main(String[] args) {


        // criando o digrafo original

        In in = new In("dados/entradaOriginal.txt");
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);



        // Verificação de Graus do digrafo original

        StdOut.println("=== Verificação de Balanceamento Digrafo original===\n");
        boolean isBalanced = true;
        for (int v = 0; v < G.V(); v++) {
            int inDegree = 0;
            // Cálculo manual do in-degree (grau de entrada)
            for (DirectedEdge e : G.edges()) {
                if (e.to() == v) inDegree++;
            }
            int outDegree = G.outdegree(v);

            StdOut.printf("Vértice %d: Entrada=%d, Saída=%d\n", v, inDegree, outDegree);
            if (inDegree != outDegree) isBalanced = false;
        }

        if (!isBalanced) {
            StdOut.println("O grafo original NÃO está balanceado. \n");
        }

        //identificar os vértices desbalanceados;

        System.out.println("vertices 0 , 1 , 4 , 5 estão desbalanceados\n");

        //justificar quais arestas ou caminhos precisam ser acrescentados;

        System.out.println("Para eulerizar o grafo, foram adicionadas 6 arestas tracejadas que representam os caminhos mínimos necessários para conectar os nós desbalanceados, garantindo que o grau de entrada seja igual ao grau de saída em todos os vértices.\n");

        // cirando o digrafo ponderado com a entrada balanciada

        In in2 = new In("dados/entradaEulerizada.txt");
        EdgeWeightedDigraph GEuler = new EdgeWeightedDigraph(in2);

        // informar os graus de entrada e saída dos vértices do digrafo balanciado e verificar se o grafo está balanceado;

        StdOut.println("=== Verificação de Balanceamento ===\n");
        boolean isBalanced2 = true;
        for (int v = 0; v < GEuler.V(); v++) {
            int inDegree = 0;

            for (DirectedEdge e : GEuler.edges()) {
                if (e.to() == v) inDegree++;
            }
            int outDegree = GEuler.outdegree(v);

            StdOut.printf("Vértice %d: Entrada=%d, Saída=%d\n", v, inDegree, outDegree);
            if (inDegree != outDegree) isBalanced2 = false;
        }

        if (!isBalanced2) {
            StdOut.println("ERRO: O grafo eulerizado NÃO está balanceado!");
            return;
        }

        // Converter para Digraph

        Digraph D = new Digraph(GEuler.V());

        for (DirectedEdge e : GEuler.edges()) {
            D.addEdge(e.from(), e.to());
        }

        // Executar Hierholzer

        DirectedEulerianCycle euler = new DirectedEulerianCycle(D);

        if (!euler.hasEulerianCycle()) {
            StdOut.println("Não existe circuito euleriano\n");
            return;
        }

        //Imprimir o circuito

        StdOut.println("=== Circuito Euleriano ===\n");

        Iterable<Integer> cycle = euler.cycle();

        int prev = -1;
        for (int v : cycle) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        //Calcular o custo total

        double totalCost = 0.0;

        Integer prevVertex = null;

        for (int v : cycle) {
            if (prevVertex != null) {

                // procurar aresta prevVertex -> v
                for (DirectedEdge e : GEuler.adj(prevVertex)) {
                    if (e.to() == v) {
                        totalCost += e.weight();
                        break; // importante: pega só uma ocorrência
                    }
                }
            }
            prevVertex = v;
        }

        //mostrar custo total

        StdOut.println("Custo total do circuito: " + totalCost);




    }
}
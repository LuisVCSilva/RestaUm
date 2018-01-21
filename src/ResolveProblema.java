import java.util.Stack;

public class ResolveProblema {
    private boolean ehSoluvel;
    private int totalMovimentos;
    private NoDeBusca ultimoNo;

    // Classe do no de busca
    private class NoDeBusca implements Comparable<NoDeBusca> {
        private Tabuleiro grade;
        private int movimentos;          // numero de movimentos que se levou a grade atual
        private NoDeBusca noAnterior;    // o no de busca anterior(pai)
        private int prioridade;       // a prioridade do no de busca atual,quanto menor mais importante


        public NoDeBusca(Tabuleiro b, int m, NoDeBusca p) {
            grade = b;
            movimentos = m;
            noAnterior = p;
            prioridade = b.distancia() + movimentos;
        }
        @Override
        public int compareTo(NoDeBusca nodo) {
            if (this.prioridade > nodo.prioridade) return 1;
            if (this.prioridade < nodo.prioridade) return -1;
            return 0;
        }
    }

    // metodo que adiciona nos de busca na fila de prioridade
    private void enfileiraNos(NoDeBusca nodo, MinPQ<NoDeBusca> pq) {
        for (Tabuleiro proximoTabuleiro: nodo.grade.vizinhos()) {
            if ((nodo.noAnterior == null) || (!proximoTabuleiro.equals(nodo.noAnterior.grade))) {
                pq.insert(new NoDeBusca(proximoTabuleiro, nodo.movimentos + 1, nodo));
            }
        }
    }

    public ResolveProblema(Tabuleiro noInicial) {
        MinPQ<NoDeBusca> filaDeNosDeBusca = new MinPQ<NoDeBusca>();
        MinPQ<NoDeBusca> filaDeNosDeBuscaGemeos = new MinPQ<NoDeBusca>();
        NoDeBusca nodoInicio = new NoDeBusca(noInicial, 0, null);
        NoDeBusca nodoInicioGemeo = new NoDeBusca(noInicial.novoEstadoTabuleiro(), 0, null);
        filaDeNosDeBusca.insert(nodoInicio);
        filaDeNosDeBuscaGemeos.insert(nodoInicioGemeo);
        while (true) {
            NoDeBusca nodo = filaDeNosDeBusca.delMin();
            NoDeBusca nodoGemeo = filaDeNosDeBuscaGemeos.delMin();
            if (nodo.grade.ehCorreto()) {
                ultimoNo = nodo;
                ehSoluvel = true;
                totalMovimentos = nodo.movimentos;
                break;
            }
            if (nodoGemeo.grade.ehCorreto()) {
                ehSoluvel = false;
                totalMovimentos = -1;
                break;
            }
            enfileiraNos(nodo, filaDeNosDeBusca);
            enfileiraNos(nodoGemeo, filaDeNosDeBuscaGemeos);
        }
    }

    public boolean ehSoluvel() {
        return ehSoluvel;
    }

    public int movimentos() {
        return totalMovimentos;
    }

    public Iterable<Tabuleiro> listaSolucao() {
        Stack<Tabuleiro> listaSolucao = new Stack<Tabuleiro>();
        if (!ehSoluvel) {
            listaSolucao = null;
        } else {
            // backtrack no menor caminho na arvore de busca
            NoDeBusca s = ultimoNo;
            while (s != null) {
                listaSolucao.push(s.grade);
                s = s.noAnterior;
            }
        }
        return listaSolucao;
    }

    
    public static void main(String[] args) {

        int N = 16;
        int[][] pedras = new int[][]{
            {11,4,8,13},
            {0, 2,6,3},
            {12,9,5,1},
            {15,14,10,7}
        };
        
            Tabuleiro tabuleiroInicial = new Tabuleiro(pedras);

        ResolveProblema solver = new ResolveProblema(tabuleiroInicial);

        if (!solver.ehSoluvel())
            StdOut.println("Não existe solução possível");
        else {
            StdOut.println("Número mínimo de movimentos = " + solver.movimentos());
            for (Tabuleiro grade : solver.listaSolucao())
                StdOut.println(grade);
        }
    }
}
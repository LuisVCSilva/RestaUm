import java.util.Arrays;
import java.util.Stack;

public class Tabuleiro {
    private final int N;  
    private final int[][] pedras;

    /**
     constroi um tabuleiro dada as pedras e o tamanho
     */
    public Tabuleiro(int[][] blocos) {
        N = blocos.length;
        pedras = copiaArray(blocos, N);
    }


    private int[][] copiaArray(int[][] y, int M) {
        int[][] s = new int[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                s[i][j] = y[i][j];
            }
        }
        return s;
    }

    public int tamanho() {
        return N;
    }

    public int qtdeBlocosDiferentes() {//i.e : distancia de hamming
        int qtdeDiffBlocos = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (pedras[i][j] != j + i * N  + 1) {
                    qtdeDiffBlocos++;
                }
            }
        }
        return --qtdeDiffBlocos; // exclui o buraco
    }

    // computa distancia de manhattan para um bloco ij
    private int distanciaBloco(int i, int j) {
        int iDestino = pedras[i][j] / N;
        int jDestino = pedras[i][j] % N;
        if (jDestino == 0) {
            iDestino -= 1;
            jDestino = N - 1;
        } else {
            jDestino -= 1;
        }
        return Math.abs(i - iDestino) + Math.abs(j - jDestino);
    }


    public int distancia() {//soma da distancia entre pedras e objetivo
        int manDist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (pedras[i][j] != j + i * N  + 1 && pedras[i][j] != 0) {
                    manDist += distanciaBloco(i, j);
                }
            }
        }
        return manDist;
    }

    //já chegamos?
    public boolean ehCorreto() {
        return distancia() == 0;
    }


    private void trocaPosBlocos(int[][] x, int i, int j, int m, int n) {
        int swap;
        swap = x[i][j];
        x[i][j] = x[m][n];
        x[m][n] = swap;
    }

    /**
     * gera novo estado trocando duas peças adjacentes na mesma linha
     */
    public Tabuleiro novoEstadoTabuleiro() {
        int[][] tabuleiroVariacao = copiaArray(pedras, N);
        if (tabuleiroVariacao[0][0] * tabuleiroVariacao[0][1] == 0) {
            trocaPosBlocos(tabuleiroVariacao, 1, 0, 1, 1);
        } else {
            trocaPosBlocos(tabuleiroVariacao, 0, 0, 0, 1);
        }
        return new Tabuleiro(tabuleiroVariacao);
    }

    public boolean equals(Object y) {//Compara pedra com outra pedra
        
        if (y == this) return true;

        if (!(y instanceof Tabuleiro)) return false;

        Tabuleiro tabuleiroQualquer = (Tabuleiro) y;
        return (this.N == tabuleiroQualquer.N) && (Arrays.deepEquals(this.pedras, tabuleiroQualquer.pedras));

    }

    private void empilhaTabuleiro(Stack<Tabuleiro> s, int x, int y, int m, int n) {
        int[][] pedrasVizinhas = copiaArray(pedras, N);
        trocaPosBlocos(pedrasVizinhas, x, y, m, n);
        s.push(new Tabuleiro(pedrasVizinhas));
    }

    public Iterable<Tabuleiro> vizinhos() {
        Stack<Tabuleiro> tabuleiros = new Stack<Tabuleiro>();
        boolean isBuraco = false;
        for (int i = 0; i < N && !isBuraco; i++) {
            for (int j = 0; j < N; j++) {
                if (pedras[i][j] == 0) {
                    if (i > 0) empilhaTabuleiro(tabuleiros, i - 1, j, i, j);
                    if (j > 0) empilhaTabuleiro(tabuleiros, i, j - 1, i, j);
                    if (i < N - 1) empilhaTabuleiro(tabuleiros, i + 1, j, i, j);
                    if (j < N - 1) empilhaTabuleiro(tabuleiros, i, j + 1, i, j);
                    isBuraco = true;
                    break;
                }
            }
        }
        return tabuleiros;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", pedras[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }




    public static void main(String[] args) {
//        int[][] pedras = {{1, 3, 7}, {0, 2, 8}, {4, 6, 5}};
//        int[][] pedras = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        int[][] blocos = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
//        int[][] pedras = {{1, 0}, {2, 3}};
        Tabuleiro a = new Tabuleiro(blocos);
        StdOut.println(a.tamanho());
        StdOut.println(a.qtdeBlocosDiferentes());
        StdOut.println(a.distancia());
        StdOut.println(a);
        
        StdOut.println(a.novoEstadoTabuleiro());
        StdOut.println(a);
        for (Tabuleiro tabuleiro: a.vizinhos()) {
            StdOut.println(tabuleiro);
        }
    }
}
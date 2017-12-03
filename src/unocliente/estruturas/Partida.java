package unocliente.estruturas;

public class Partida {
    
    private String nome;
    private int nJogadores;
    private int jogadoresConectados;
    private int id;

    public Partida(String nome, int nJogadores) {
        this.nome = nome;
        this.nJogadores = nJogadores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getnJogadores() {
        return nJogadores;
    }

    public void setnJogadores(int nJogadores) {
        this.nJogadores = nJogadores;
    }

    @Override
    public String toString() {
        return "Partida{" + "nome=" + nome + ", nJogadores=" + nJogadores + ", jogadoresConectados=" + jogadoresConectados + ", id=" + id + '}';
    }

    public int getJogadoresConectados() {
        return jogadoresConectados;
    }

    public void setJogadoresConectados(int jogadoresConectados) {
        this.jogadoresConectados = jogadoresConectados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Partida(String nome, int nJogadores, int jogadoresConectados, int id) {
        this.nome = nome;
        this.nJogadores = nJogadores;
        this.jogadoresConectados = jogadoresConectados;
        this.id = id;
    }
}

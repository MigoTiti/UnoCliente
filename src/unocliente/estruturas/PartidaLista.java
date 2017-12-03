package unocliente.estruturas;

import javafx.beans.property.SimpleStringProperty;

public class PartidaLista {
    
    private final SimpleStringProperty nome;
    private final SimpleStringProperty id;
    private final SimpleStringProperty numeroJogadores;
    private final SimpleStringProperty jogadoresConectados;
    
    public PartidaLista(Partida partida) {
        nome = new SimpleStringProperty(partida.getNome());
        id = new SimpleStringProperty(Integer.toString(partida.getId()));
        numeroJogadores = new SimpleStringProperty(Integer.toString(partida.getnJogadores()));
        jogadoresConectados = new SimpleStringProperty(Integer.toString(partida.getJogadoresConectados()));
    }

    public String getNome() {
        return nome.get();
    }

    public String getId() {
        return id.get();
    }

    public String getNumeroJogadores() {
        return numeroJogadores.get();
    }

    public String getJogadoresConectados() {
        return jogadoresConectados.get();
    }
}

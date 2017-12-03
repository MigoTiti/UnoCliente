package unocliente.telas;

import java.util.List;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import unocliente.UnoCliente;
import unocliente.rede.Comunicador;
import unocliente.estruturas.Partida;
import unocliente.estruturas.PartidaLista;

public class EscolherPartidaTela {
    
    private Comunicador comunicador;
    private TableView<PartidaLista> table;
    
    public void iniciarTela(Comunicador comunicador, List<Partida> partidas) {
        this.comunicador = comunicador;
        
        ObservableList<PartidaLista> partidasLista = FXCollections.observableArrayList();
        
        partidas.stream().forEach((partida) -> {
            partidasLista.add(new PartidaLista(partida));
        });
        
        BorderPane root;
        
        final Label label = new Label("Partidas disponíveis");
        label.setFont(new Font("Arial", 20));
        label.setAlignment(Pos.CENTER);
        
        table = new TableView<>();
        table.setEditable(false);
        
        TableColumn nomeColuna = new TableColumn("id");
        nomeColuna.setMinWidth(50);
        nomeColuna.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        nomeColuna.setResizable(false);
 
        TableColumn idColuna = new TableColumn("Nome");
        idColuna.setMinWidth(100);
        idColuna.setCellValueFactory(
                new PropertyValueFactory<>("nome"));
        idColuna.setResizable(false);
 
        TableColumn nJogadoresColuna = new TableColumn("Capacidade");
        nJogadoresColuna.setMinWidth(100);
        nJogadoresColuna.setCellValueFactory(
                new PropertyValueFactory<>("numeroJogadores"));
        nJogadoresColuna.setResizable(false);
        
        TableColumn jogadoresConectadosColuna = new TableColumn("Jogadores conectados");
        jogadoresConectadosColuna.setMinWidth(150);
        jogadoresConectadosColuna.setCellValueFactory(
                new PropertyValueFactory<>("jogadoresConectados"));
        jogadoresConectadosColuna.setResizable(false);
 
        table.setItems(partidasLista);
        table.getColumns().addAll(idColuna, nomeColuna, nJogadoresColuna, jogadoresConectadosColuna);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        vbox.setAlignment(Pos.CENTER);
        
        final HBox hbox = new HBox(vbox);
        hbox.setAlignment(Pos.CENTER);
        
        root = new BorderPane(hbox);
        
        Button voltarBotao = new Button("Voltar");
        voltarBotao.setOnAction((ActionEvent event) -> {
            new EscolhaTela().iniciarTela(comunicador);
        });
        
        Button entrarBotao = new Button("Entrar");
        entrarBotao.setOnAction((ActionEvent event) -> {
            PartidaLista partidaSelecionada = table.getSelectionModel().getSelectedItem();
            
            if (partidaSelecionada != null) {
                comunicador.enviarMensagem(Comunicador.ENTRAR_EM_PARTIDA + "&" + partidaSelecionada.getId());
                UnoCliente.enviarMensagemInfo("Tentando conexão");
                
                String resposta = comunicador.receberMensagem();
                StringTokenizer st = new StringTokenizer(resposta, "&");
                
                int comando = Integer.parseInt(st.nextToken());
                
                if (comando == Comunicador.CONFIRMAR_ENTRADA_EM_PARTIDA) {
                    new AguardarJogadoresTela().iniciarTela(comunicador);
                } else if (comando == Comunicador.PARTIDA_CHEIA) {
                    UnoCliente.enviarMensagemErro("Partida cheia. Escolha outra");
                }
            } else {
                UnoCliente.enviarMensagemErro("Escolha uma partida");
            }
        });
        
        final HBox hbox2 = new HBox(voltarBotao, entrarBotao);
        hbox2.setSpacing(10);
        hbox2.setPadding(new Insets(10));
        
        root.setBottom(hbox2);
        
        UnoCliente.fxContainer.setScene(new Scene(root));
    }
}

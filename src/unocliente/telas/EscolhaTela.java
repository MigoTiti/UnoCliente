package unocliente.telas;

import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import unocliente.UnoCliente;
import unocliente.estruturas.Partida;
import unocliente.rede.Comunicador;
import unocliente.util.Utilitarios;

public class EscolhaTela {

    private Comunicador comunicador;

    public void iniciarTela(Comunicador comunicador) {
        this.comunicador = comunicador;

        Button btnEntrar = new Button("Entrar em partida");
        btnEntrar.setOnAction((ActionEvent event) -> {
            new Thread(() -> entrarEmPartida()).start();
        });

        Button btnCriar = new Button("Criar partida");
        btnCriar.setOnAction((ActionEvent event) -> {
            criarPartida();
        });

        VBox vBox = new VBox(btnEntrar, btnCriar);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);

        StackPane root = new StackPane(vBox);

        UnoCliente.fxContainer.setScene(new Scene(root));
    }

    private void entrarEmPartida() {
        comunicador.enviarMensagem(Integer.toString(Comunicador.LISTAR_PARTIDAS));
        String resposta = comunicador.receberMensagem();
        StringTokenizer st = new StringTokenizer(resposta, "&");
        
        int comando = Integer.parseInt(st.nextToken());
        
        if (comando == Comunicador.LISTAR_PARTIDAS) {
            List<Partida> partidas = Utilitarios.decodificarPartidas(st);
            
            if (partidas.size() < 1)
                UnoCliente.enviarMensagemErro("Sem partidas disponíveis");
            else
                new EscolherPartidaTela().iniciarTela(comunicador, partidas);
        }
    }
    
    private void criarPartida() {
        Dialog<Partida> dialog = new Dialog<>();
        dialog.setTitle("Criar partida");
        dialog.setResizable(true);

        Label label1 = new Label("Nome: ");
        Label label2 = new Label("Número de jogadores: ");
        TextField text1 = new TextField("Teste");
        TextField text2 = new TextField("2");

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Criar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        final Button okButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeOk);
        okButton.setDisable(true);

        text1.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().length() < 1);
        });
        
        text2.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean ok;
            
            try {
                Integer.parseInt(newValue);
                ok = false;
            } catch (NumberFormatException ex) {
                ok = true;
            }
            
            okButton.setDisable(ok);
        });

        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                return new Partida(text1.getText(), Integer.parseInt(text2.getText()));
            }

            return null;
        });

        Optional<Partida> result = dialog.showAndWait();

        if (result.isPresent()) {
            Partida temp = result.get();
            comunicador.enviarMensagem(Comunicador.CRIAR_PARTIDA + "&" + temp.getNome() + "&" + temp.getnJogadores());
            String resposta = comunicador.receberMensagem();
            StringTokenizer st = new StringTokenizer(resposta, "&");
            
            int comando = Integer.parseInt(st.nextToken());
            
            if (comando == Comunicador.CONFIRMAR_CRIACAO_PARTIDA)
                new AguardarJogadoresTela().iniciarTela(comunicador);
        }
    }
}

package unocliente.telas;

import java.util.StringTokenizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import unocliente.UnoCliente;
import unocliente.rede.Comunicador;

public class AguardarJogadoresTela {
    
    public void iniciarTela(Comunicador comunicador) {
        Text texto = new Text("Aguardando jogadores");
        texto.setFont(Font.font("Arial", FontWeight.NORMAL, 20));

        ProgressIndicator pi = new ProgressIndicator();

        VBox vBoxCentro = new VBox(texto, pi);
        vBoxCentro.setSpacing(20);
        vBoxCentro.setAlignment(Pos.CENTER);
        BorderPane painel = new BorderPane(vBoxCentro);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(event -> {
            new EscolhaTela().iniciarTela(comunicador);
        });

        HBox hBoxBaixo = new HBox(voltar);
        hBoxBaixo.setPadding(new Insets(15));

        painel.setBottom(hBoxBaixo);

        UnoCliente.fxContainer.setScene(new Scene(painel));
        
        new Thread(() -> aguardarJogadores(comunicador)).start();
    }
    
    private void aguardarJogadores(Comunicador comunicador) {
        String mensagem = comunicador.receberMensagem();
        StringTokenizer st = new StringTokenizer(mensagem, "&");
        
        int comando = Integer.parseInt(st.nextToken());
        
        if (comando == Comunicador.TODOS_JOGADORES_CONECTADOS) {
            new PartidaTela().iniciarTela(comunicador);
        }
    }
}

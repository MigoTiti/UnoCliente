package unocliente.telas;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import unocliente.UnoCliente;

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
import unocliente.rede.Comunicador;

public class ConectarAoServidorTela {
    
    public void iniciarTela(String ip) {
        Text texto = new Text("Tentando conexão ao servidor");
        texto.setFont(Font.font("Arial", FontWeight.NORMAL, 20));

        ProgressIndicator pi = new ProgressIndicator();

        VBox vBoxCentro = new VBox(texto, pi);
        vBoxCentro.setSpacing(20);
        vBoxCentro.setAlignment(Pos.CENTER);
        BorderPane painel = new BorderPane(vBoxCentro);

        Button voltar = new Button("Voltar");
        voltar.setOnAction(event -> {
            UnoCliente.createScene();
        });

        HBox hBoxBaixo = new HBox(voltar);
        hBoxBaixo.setPadding(new Insets(15));

        painel.setBottom(hBoxBaixo);

        UnoCliente.fxContainer.setScene(new Scene(painel));
        
        new Thread(() -> conectarAoServidor(ip)).start();
    }
    
    private void conectarAoServidor(String ip) {
        try {
            Comunicador comunicador = new Comunicador(new DatagramSocket(), InetAddress.getByName(ip));
            comunicador.enviarMensagem(Integer.toString(Comunicador.SOLICITAR_CONEXAO));
            String confirmacao = comunicador.receberMensagem();
            
            StringTokenizer st = new StringTokenizer(confirmacao, "&");
            
            int comando = Integer.parseInt(st.nextToken());
            
            if (comando == Comunicador.CONFIRMAR_CONEXAO) {
                new EscolhaTela().iniciarTela(comunicador);
            }
        } catch (SocketException | UnknownHostException ex) {
            UnoCliente.exibirException(ex);
        }
    }
}

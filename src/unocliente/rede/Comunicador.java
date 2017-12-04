package unocliente.rede;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javafx.application.Platform;

import unocliente.UnoCliente;

public class Comunicador {

    public static final int SOLICITAR_CONEXAO = 1;
    public static final int CONFIRMAR_CONEXAO = 2;
    public static final int CRIAR_PARTIDA = 3;
    public static final int CONFIRMAR_CRIACAO_PARTIDA = 4;
    public static final int ENTRAR_EM_PARTIDA = 5;
    public static final int LISTAR_PARTIDAS = 6;
    public static final int TODOS_JOGADORES_CONECTADOS = 7;
    public static final int CONFIRMAR_ENTRADA_EM_PARTIDA = 8;
    public static final int PARTIDA_CHEIA = 9;
    public static final int DISTRIBUIR_CARTAS = 10;
    public static final int JOGAR_CARTA = 11;
    public static final int COMPRAR_CARTA = 12;
    public static final int PULAR_JOGADA = 13;
    public static final int RESPOSTA_COMPRA = 14;
    public static final int REPORTAR_JOGADA = 15;

    private InetAddress ipAEnviar;
    private int portaAEnviar;

    private DatagramSocket socket;

    public Comunicador(DatagramSocket socket, InetAddress ipAEnviar) {
        this.socket = socket;
        this.ipAEnviar = ipAEnviar;
        this.portaAEnviar = UnoCliente.PORTA_SERVIDOR;
    }

    public void enviarMensagem(String mensagemString) {
        try {
            mensagemString = mensagemString + "&";
            byte[] mensagem = mensagemString.getBytes();
            DatagramPacket pacoteAEnviar = new DatagramPacket(mensagem, mensagem.length, ipAEnviar, portaAEnviar);
            socket.send(pacoteAEnviar);
        } catch (Exception ex) {
            Platform.runLater(() -> {
                UnoCliente.exibirException(ex);
            });
        }
    }

    public String receberMensagem() {
        try {
            byte[] buffer = new byte[500];
            DatagramPacket pacoteResposta = new DatagramPacket(buffer, buffer.length);
            socket.receive(pacoteResposta);

            ipAEnviar = pacoteResposta.getAddress();
            portaAEnviar = pacoteResposta.getPort();

            String respostaString = new String(pacoteResposta.getData());

            return respostaString;
        } catch (Exception ex) {
            Platform.runLater(() -> {
                UnoCliente.exibirException(ex);
            });
        }

        return null;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }
}

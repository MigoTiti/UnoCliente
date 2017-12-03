package unocliente.telas;

import java.util.List;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import unocliente.UnoCliente;
import unocliente.estruturas.Carta;
import unocliente.estruturas.CartaVisual;
import unocliente.rede.Comunicador;
import unocliente.util.Utilitarios;

public class PartidaTela {

    private int nJogadores;
    private int nJogador;
    private int vezDoJogador = 1;
    private boolean correnteCompra = false;
    private int correnteCompraQuantidade = 0;
    
    private Carta cartaNaMesa;

    private List<Carta> maoDoJogador;

    private Comunicador comunicador;

    private StackPane cartaNaMesaPane;

    private final Button jogar = new Button("Jogar");
    private final Label vezJogador = new Label();
    private final ListView<Carta> listaCartas = new ListView<>();
    private final StackPane cartaPreview = new StackPane();

    public void iniciarTela(Comunicador comunicador) {
        this.comunicador = comunicador;

        BorderPane root;
        BorderPane campo;

        cartaNaMesaPane = new StackPane();

        campo = new BorderPane(cartaNaMesaPane);

        root = new BorderPane(campo);

        Button voltar = new Button("Sair da partida");
        voltar.setOnAction(value -> {

        });
        HBox hbox = new HBox(voltar);
        hbox.setPadding(new Insets(10));

        HBox opcoes = new HBox();
        opcoes.setSpacing(10);

        jogar.setDisable(true);
        jogar.setOnAction(value -> {
            if (nJogador == vezDoJogador) {
                Carta cartaJogada = listaCartas.getSelectionModel().getSelectedItem();
                
                if (validarJogada(cartaJogada)) {
                    
                } else {
                    UnoCliente.enviarMensagemErro("Jogada inválida");
                }
            } else {
                UnoCliente.enviarMensagemErro("Espere a sua vez");
            }
        });

        Button comprarCarta = new Button("Comprar");
        comprarCarta.setOnAction(value -> {
            if (nJogador == vezDoJogador) {
                
            } else {
                UnoCliente.enviarMensagemErro("Espere a sua vez");
            }
        });

        opcoes.getChildren().addAll(jogar, comprarCarta);

        VBox vboxMao = new VBox(listaCartas, cartaPreview, opcoes);
        vboxMao.setPadding(new Insets(10));
        vboxMao.setSpacing(30);

        Rectangle stub = new Rectangle(CartaVisual.LARGURA_CARTA, CartaVisual.ALTURA_CARTA, Color.WHITE);
        cartaPreview.getChildren().add(stub);

        VBox vboxTop = new VBox(vezJogador);
        vboxTop.setPadding(new Insets(10));
        vboxTop.setAlignment(Pos.CENTER);
        
        root.setRight(vboxTop);
        root.setLeft(vboxMao);
        root.setBottom(hbox);

        UnoCliente.fxContainer.setScene(new Scene(root));

        new Thread(() -> iniciarJogo()).start();
    }

    private boolean validarJogada(Carta cartaEscolhida) {
        int corCartaEscolhida = cartaEscolhida.getCor();
	int numeroCartaEscolhida = cartaEscolhida.getNumero();
	int corCartaNaMesa = cartaNaMesa.getCor();
	int numeroCartaNaMesa = cartaNaMesa.getNumero();

	if ((numeroCartaNaMesa == Carta.MAIS_DOIS || numeroCartaNaMesa == Carta.MAIS_QUATRO) && correnteCompra)
	{
            return numeroCartaEscolhida == Carta.MAIS_DOIS || numeroCartaEscolhida == Carta.MAIS_QUATRO;
	}

	if (numeroCartaEscolhida == Carta.MAIS_QUATRO || numeroCartaEscolhida == Carta.CORINGA)
            return true;

	return corCartaEscolhida == corCartaNaMesa || numeroCartaEscolhida == numeroCartaNaMesa;
    }
    
    private void iniciarJogo() {
        String mensagemInicial = comunicador.receberMensagem();
        StringTokenizer st = new StringTokenizer(mensagemInicial, "&");

        int comando = Integer.parseInt(st.nextToken());

        if (comando == Comunicador.DISTRIBUIR_CARTAS) {
            nJogadores = Integer.parseInt(st.nextToken());
            nJogador = Integer.parseInt(st.nextToken());

            cartaNaMesa = Utilitarios.decodificarCarta(st.nextToken());
            maoDoJogador = Utilitarios.decodificarCartas(st);

            Platform.runLater(() -> {
                CartaVisual c = new CartaVisual(cartaNaMesa.getCor(), cartaNaMesa.getNumero());
                cartaNaMesaPane.getChildren().add(new StackPane(c, c.getNumero()));

                if (nJogador == 1) {
                    vezJogador.setText("Sua vez");
                    vezJogador.setTextFill(Color.GREEN);
                } else {
                    vezJogador.setText("Vez do Jogador 1");
                    vezJogador.setTextFill(Color.RED);
                }

                ObservableList<Carta> cartas = FXCollections.observableArrayList();

                for (int i = 0; i < maoDoJogador.size(); i++) {
                    cartas.add(maoDoJogador.get(i));
                }

                listaCartas.setItems(cartas);
                listaCartas.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Carta> observable, Carta oldValue, Carta newValue) -> {
                    jogar.setDisable(false);
                    CartaVisual cTemp = new CartaVisual(newValue.getCor(), newValue.getNumero());
                    cartaPreview.getChildren().clear();
                    cartaPreview.getChildren().addAll(cTemp, cTemp.getNumero());
                });
            });

            printInformacoes();
        }
    }

    private void printInformacoes() {
        System.out.println("Numero de jogadores: " + nJogadores);
        System.out.println("Voce eh o jogador numero: " + nJogador);
        System.out.println("Carta na mesa: " + cartaNaMesa.toString());

        maoDoJogador.stream().forEach((carta) -> {
            System.out.println("Mao: " + carta.toString());
        });
    }
}

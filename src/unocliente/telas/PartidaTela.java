package unocliente.telas;

import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private boolean sentidoHorario = true;
    private boolean jaComprou = false;
    private boolean euComprei = false;

    private Carta cartaNaMesa;

    private List<Carta> maoDoJogador;

    private Comunicador comunicador;

    private StackPane cartaNaMesaPane;

    private int corSelecionadaTemp = -1;

    private final Button jogar = new Button("Jogar");
    private final Button comprar = new Button("Comprar");
    private final Label vezJogador = new Label();
    private final Label contagemCorrenteLabel = new Label("Contagem corrente: 0");
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
                    if (jaComprou){
                        jaComprou = false;
                        comprar.setText("Comprar");
                    }
                    if (cartaJogada.getCor() == Carta.COR_PRETA && cartaJogada.getNumero() == Carta.CORINGA) {
                        Dialog<Integer> dialog = new Dialog<>();
                        dialog.setTitle("Escolha a cor desejada");
                        dialog.setResizable(true);

                        int tamanho = 200;

                        Color vermelhoInicial = Color.rgb(96, 0, 0);
                        Color azulInicial = Color.rgb(0, 0, 96);
                        Color amareloInicial = Color.rgb(215, 215, 0);
                        Color verdeInicial = Color.rgb(0, 96, 0);

                        Color vermelhoSelecionado = Color.RED;
                        Color azulSelecionado = Color.BLUE;
                        Color amareloSelecionado = Color.YELLOW;
                        Color verdeSelecionado = Color.GREEN;

                        Rectangle vermelho = new Rectangle(tamanho, tamanho, vermelhoInicial);
                        Rectangle azul = new Rectangle(tamanho, tamanho, azulInicial);
                        Rectangle amarelo = new Rectangle(tamanho, tamanho, amareloInicial);
                        Rectangle verde = new Rectangle(tamanho, tamanho, verdeInicial);

                        GridPane grid = new GridPane();
                        grid.add(vermelho, 1, 1);
                        grid.add(azul, 2, 1);
                        grid.add(amarelo, 1, 2);
                        grid.add(verde, 2, 2);
                        dialog.getDialogPane().setContent(grid);

                        ButtonType buttonTypeOk = new ButtonType("Escolher", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

                        final Button okButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeOk);
                        okButton.setDisable(true);

                        vermelho.setOnMouseClicked(event -> {
                            vermelho.setFill(vermelhoSelecionado);
                            azul.setFill(azulInicial);
                            verde.setFill(verdeInicial);
                            amarelo.setFill(amareloInicial);
                            corSelecionadaTemp = Carta.COR_VERMELHA;
                            okButton.setDisable(false);
                        });

                        azul.setOnMouseClicked(event -> {
                            azul.setFill(azulSelecionado);
                            vermelho.setFill(vermelhoInicial);
                            verde.setFill(verdeInicial);
                            amarelo.setFill(amareloInicial);
                            corSelecionadaTemp = Carta.COR_AZUL;
                            okButton.setDisable(false);
                        });

                        amarelo.setOnMouseClicked(event -> {
                            amarelo.setFill(amareloSelecionado);
                            vermelho.setFill(vermelhoInicial);
                            azul.setFill(azulInicial);
                            verde.setFill(verdeInicial);
                            corSelecionadaTemp = Carta.COR_AMARELA;
                            okButton.setDisable(false);
                        });

                        verde.setOnMouseClicked(event -> {
                            verde.setFill(verdeSelecionado);
                            vermelho.setFill(vermelhoInicial);
                            azul.setFill(azulInicial);
                            amarelo.setFill(amareloInicial);
                            corSelecionadaTemp = Carta.COR_VERDE;
                            okButton.setDisable(false);
                        });

                        dialog.setResultConverter((ButtonType b) -> {
                            if (b == buttonTypeOk) {
                                return corSelecionadaTemp;
                            }

                            corSelecionadaTemp = -1;
                            return null;
                        });

                        Optional<Integer> result = dialog.showAndWait();

                        if (result.isPresent()) {
                            Integer novaCor = result.get();
                            cartaJogada.setCor(novaCor);

                            this.comunicador.enviarMensagem(Comunicador.JOGAR_CARTA + "&" + cartaJogada.toString());
                            listaCartas.getItems().remove(listaCartas.getSelectionModel().getSelectedIndex());
                            setPreview(null);
                        }
                    } else {
                        this.comunicador.enviarMensagem(Comunicador.JOGAR_CARTA + "&" + cartaJogada.toString());
                        listaCartas.getItems().remove(listaCartas.getSelectionModel().getSelectedIndex());
                        setPreview(null);
                    }
                } else {
                    UnoCliente.enviarMensagemErro("Jogada inválida");
                }
            } else {
                UnoCliente.enviarMensagemErro("Espere a sua vez");
            }
        });

        comprar.setOnAction(value -> {
            if (nJogador == vezDoJogador) {
                if (!jaComprou) {
                    comprar.setText("Pular");
                    jaComprou = true;
                    this.comunicador.enviarMensagem(Integer.toString(Comunicador.COMPRAR_CARTA));
                    euComprei = true;
                } else {
                    comprar.setText("Comprar");
                    jaComprou = false;
                    this.comunicador.enviarMensagem(Integer.toString(Comunicador.PULAR_JOGADA));
                }
            } else {
                UnoCliente.enviarMensagemErro("Espere a sua vez");
            }
        });

        opcoes.getChildren().addAll(jogar, comprar);

        VBox vboxMao = new VBox(listaCartas, cartaPreview, opcoes);
        vboxMao.setPadding(new Insets(10));
        vboxMao.setSpacing(30);

        setPreview(null);

        VBox vboxTop = new VBox(vezJogador, contagemCorrenteLabel);
        vboxTop.setPadding(new Insets(10));
        vboxTop.setAlignment(Pos.CENTER);

        root.setRight(vboxTop);
        root.setLeft(vboxMao);
        root.setBottom(hbox);

        UnoCliente.setScene(root);

        new Thread(() -> iniciarJogo()).start();
    }

    private boolean validarJogada(Carta cartaEscolhida) {
        int corCartaEscolhida = cartaEscolhida.getCor();
        int numeroCartaEscolhida = cartaEscolhida.getNumero();
        int corCartaNaMesa = cartaNaMesa.getCor();
        int numeroCartaNaMesa = cartaNaMesa.getNumero();

        if ((numeroCartaNaMesa == Carta.MAIS_DOIS || numeroCartaNaMesa == Carta.MAIS_QUATRO) && correnteCompra) {
            return numeroCartaEscolhida == Carta.MAIS_DOIS || numeroCartaEscolhida == Carta.MAIS_QUATRO;
        }

        if (numeroCartaEscolhida == Carta.MAIS_QUATRO || numeroCartaEscolhida == Carta.CORINGA) {
            return true;
        }

        return corCartaEscolhida == corCartaNaMesa || numeroCartaEscolhida == numeroCartaNaMesa;
    }

    private void handleMensagens() {
        while (true) {
            String mensagemServidor = comunicador.receberMensagem();
            StringTokenizer st = new StringTokenizer(mensagemServidor, "&");

            int comando = Integer.parseInt(st.nextToken());

            switch (comando) {
                case Comunicador.RESPOSTA_COMPRA:
                    int quantidade = Integer.parseInt(st.nextToken());

                    if (quantidade > 1) {
                        correnteCompra = false;
                        setCorrenteCompraQuantidade(0);
                    }

                    if (euComprei) {
                        euComprei = false;
                        if (quantidade == 1) {
                            Platform.runLater(() -> {
                                Carta carta = Utilitarios.decodificarCarta(st.nextToken());
                                maoDoJogador.add(carta);
                                listaCartas.getItems().add(carta);
                            });
                        } else {
                            Platform.runLater(() -> {
                                List<Carta> cartas = Utilitarios.decodificarCartas(st);

                                cartas.stream().forEach((carta) -> {
                                    maoDoJogador.add(carta);
                                    listaCartas.getItems().add(carta);
                                });
                            });
                        }
                    }

                    break;
                case Comunicador.REPORTAR_JOGADA:
                    setCartaNaMesa(Utilitarios.decodificarCarta(st.nextToken()));
                    sentidoHorario = st.nextToken().equals("1");
                    setVezDoJogador(Integer.parseInt(st.nextToken()));
                    correnteCompra = st.nextToken().equals("1");

                    if (correnteCompra) {
                        setCorrenteCompraQuantidade(Integer.parseInt(st.nextToken()));
                    }

                    break;
                case Comunicador.PULAR_JOGADA:
                    setVezDoJogador(Integer.parseInt(st.nextToken()));
                    break;
            }
        }
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
                setCartaNaMesa(cartaNaMesa);

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
                    jogar.setDisable(nJogador != vezDoJogador);
                    setPreview(newValue);
                });
            });

            printInformacoes();
            handleMensagens();
        }
    }

    private void setCorrenteCompraQuantidade(int contagem) {
        correnteCompraQuantidade = contagem;

        Platform.runLater(() -> {
            contagemCorrenteLabel.setText("Contagem corrente: " + correnteCompraQuantidade);
        });
    }

    private void setVezDoJogador(int jogador) {
        vezDoJogador = jogador;

        Platform.runLater(() -> {
            if (vezDoJogador == nJogador) {
                vezJogador.setText("Sua vez");
                vezJogador.setTextFill(Color.GREEN);
            } else {
                vezJogador.setText("Vez do jogador " + vezDoJogador);
                vezJogador.setTextFill(Color.RED);
            }
        });
    }

    private void setPreview(Carta c) {
        Platform.runLater(() -> {
            cartaPreview.getChildren().clear();

            if (c == null) {
                cartaPreview.getChildren().addAll(CartaVisual.gerarCartaVisual(-1, 0));
            } else {
                cartaPreview.getChildren().addAll(CartaVisual.gerarCartaVisual(c.getCor(), c.getNumero()));
            }
        });
    }

    private void setCartaNaMesa(Carta c) {
        cartaNaMesa = c;
        Platform.runLater(() -> {
            cartaNaMesaPane.getChildren().clear();
            cartaNaMesaPane.getChildren().addAll(CartaVisual.gerarCartaVisual(c.getCor(), c.getNumero()));
        });
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

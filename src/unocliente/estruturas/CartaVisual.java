package unocliente.estruturas;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CartaVisual extends Rectangle {

    private Label numero;

    public static final double ALTURA_CARTA = 50 * 3;
    public static final double LARGURA_CARTA = 35 * 3;
    public static final double DIFERENCA_BORDA_CORPO = 20;
    public static final double DIFERENCA_BORDA_BORDA_PRETA = 2;
    public static final double BORDA_CARTA = 10;

    public CartaVisual(int cor, int numero) {
        super();

        switch (cor) {
            case Carta.COR_PRETA:
                this.setFill(Color.BLACK);
                break;
            case Carta.COR_VERMELHA:
                this.setFill(Color.RED);
                break;
            case Carta.COR_AMARELA:
                this.setFill(Color.YELLOW);
                break;
            case Carta.COR_AZUL:
                this.setFill(Color.BLUE);
                break;
            case Carta.COR_VERDE:
                this.setFill(Color.GREEN);
                break;
        }

        switch (numero) {
            case Carta.MAIS_DOIS:
                this.numero = new Label("+2");
                this.numero.setFont(new Font(50));
                break;
            case Carta.REVERTER:
                this.numero = new Label("<-\n->");
                this.numero.setFont(new Font(20));
                break;
            case Carta.BLOQUEAR:
                this.numero = new Label("B");
                this.numero.setFont(new Font(50));
                break;
            case Carta.MAIS_QUATRO:
                this.numero = new Label("+4");
                this.numero.setFont(new Font(50));
                break;
            case Carta.CORINGA:
                this.numero = new Label("TC");
                this.numero.setFont(new Font(50));
                break;
            default:
                this.numero = new Label(Integer.toString(numero));
                this.numero.setFont(new Font(50));
                break;
        }

        this.setHeight(ALTURA_CARTA);
        this.setWidth(LARGURA_CARTA);

        if (cor == Carta.COR_AMARELA) {
            this.numero.setTextFill(Color.BLACK);
        } else {
            this.numero.setTextFill(Color.WHITE);
        }
    }

    public static StackPane gerarCartaVisual(int cor, int numero) {
        if (cor == -1) {
            return new StackPane(new Rectangle(CartaVisual.LARGURA_CARTA, CartaVisual.ALTURA_CARTA, Color.WHITE));
        } else {
            Rectangle corpo = new Rectangle(LARGURA_CARTA - DIFERENCA_BORDA_CORPO, ALTURA_CARTA - DIFERENCA_BORDA_CORPO);
            corpo.setArcHeight(BORDA_CARTA);
            corpo.setArcWidth(BORDA_CARTA);

            switch (cor) {
                case Carta.COR_PRETA:
                    corpo.setFill(Color.BLACK);
                    break;
                case Carta.COR_VERMELHA:
                    corpo.setFill(Color.RED);
                    break;
                case Carta.COR_AMARELA:
                    corpo.setFill(Color.YELLOW);
                    break;
                case Carta.COR_AZUL:
                    corpo.setFill(Color.BLUE);
                    break;
                case Carta.COR_VERDE:
                    corpo.setFill(Color.GREEN);
                    break;
            }

            Label numeroText;
            int tamanhoTexto = 50;
            
            switch (numero) {
                case Carta.MAIS_DOIS:
                    numeroText = new Label("+2");
                    numeroText.setFont(new Font(tamanhoTexto));
                    break;
                case Carta.REVERTER:
                    numeroText = new Label("<-\n->");
                    numeroText.setFont(new Font(tamanhoTexto - 30));
                    break;
                case Carta.BLOQUEAR:
                    numeroText = new Label("B");
                    numeroText.setFont(new Font(tamanhoTexto));
                    break;
                case Carta.MAIS_QUATRO:
                    numeroText = new Label("+4");
                    numeroText.setFont(new Font(tamanhoTexto));
                    break;
                case Carta.CORINGA:
                    numeroText = new Label("TC");
                    numeroText.setFont(new Font(tamanhoTexto));
                    break;
                default:
                    numeroText = new Label(Integer.toString(numero));
                    numeroText.setFont(new Font(tamanhoTexto));
                    break;
            }

            Rectangle borda = new Rectangle(LARGURA_CARTA - DIFERENCA_BORDA_BORDA_PRETA, ALTURA_CARTA - DIFERENCA_BORDA_BORDA_PRETA, Color.WHITE);
            borda.setArcHeight(BORDA_CARTA);
            borda.setArcWidth(BORDA_CARTA);

            Rectangle bordaPreta = new Rectangle(LARGURA_CARTA, ALTURA_CARTA, Color.BLACK);
            bordaPreta.setArcHeight(BORDA_CARTA);
            bordaPreta.setArcWidth(BORDA_CARTA);

            return new StackPane(bordaPreta, borda, corpo, numeroText);
        }
    }

    public Label getNumero() {
        return numero;
    }

    public void setNumero(Label numero) {
        this.numero = numero;
    }
}

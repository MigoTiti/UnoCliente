package unocliente.estruturas;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class CartaVisual extends Rectangle {

    private Label numero;

    public static final int ALTURA_CARTA = 50 * 2;
    public static final int LARGURA_CARTA = 30 * 2;

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
        
        if (cor == Carta.COR_AMARELA || cor == Carta.COR_PRETA)
            this.numero.setTextFill(Color.BLACK);
        else
            this.numero.setTextFill(Color.WHITE);
    }

    public Label getNumero() {
        return numero;
    }

    public void setNumero(Label numero) {
        this.numero = numero;
    }
}

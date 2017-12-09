package unocliente.estruturas;

import java.util.ArrayDeque;
import java.util.Deque;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CartaVisual {

    public static final double ALTURA_CARTA = 50 * 3;
    public static final double LARGURA_CARTA = 35 * 3;
    public static final double ELIPSE_X = ALTURA_CARTA / 3;
    public static final double ELIPSE_Y = LARGURA_CARTA / 2.6;
    public static final double DIFERENCA_BORDA_CORPO = 20;
    public static final double DIFERENCA_BORDA_BORDA_PRETA = 2;
    public static final double BORDA_CARTA = 10;

    private static Node gerarCartinha(int cor, int translateX, int translateY) {
        Rectangle corpo = new Rectangle((LARGURA_CARTA - DIFERENCA_BORDA_CORPO) / 4, (ALTURA_CARTA - DIFERENCA_BORDA_CORPO) / 4);
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
                corpo.setFill(Color.rgb(255, 190, 0));
                break;
            case Carta.COR_AZUL:
                corpo.setFill(Color.BLUE);
                break;
            case Carta.COR_VERDE:
                corpo.setFill(Color.GREEN);
                break;
        }

        Rectangle borda = new Rectangle(LARGURA_CARTA / 4, ALTURA_CARTA / 4, Color.WHITE);
        borda.setArcHeight(BORDA_CARTA);
        borda.setArcWidth(BORDA_CARTA);
        borda.setId("borda_branca_cartinha");
        borda.setTranslateX(borda.getTranslateX() - 0.8);

        StackPane cartinha = new StackPane(borda, corpo);
        cartinha.setTranslateX(cartinha.getTranslateX() + translateX);
        cartinha.setTranslateY(cartinha.getTranslateY() + translateY);
        return cartinha;
    }

    private static Node gerarCoringa(double tamanhoX, double tamanhoY) {
        double elipseX = tamanhoX * 0.85;
        double elipseY = tamanhoY * 0.85;

        Ellipse elipse = new Ellipse(elipseX, elipseY);
        elipse.setTranslateX(elipse.getTranslateX() + elipseX);

        Rectangle clip = new Rectangle(elipseX, elipseY);

        Shape elipseVermelhaFinal = Shape.subtract(elipse, clip);
        elipseVermelhaFinal.setTranslateX(elipseVermelhaFinal.getTranslateX() - elipseX);
        elipseVermelhaFinal = Shape.subtract(elipseVermelhaFinal, clip);
        elipseVermelhaFinal.setTranslateY(elipseVermelhaFinal.getTranslateY() + elipseY);
        elipseVermelhaFinal.setTranslateX(elipseVermelhaFinal.getTranslateX() + elipseX);
        elipseVermelhaFinal = Shape.subtract(elipseVermelhaFinal, clip);

        Shape elipseAmarelaFinal = Shape.subtract(elipse, clip);
        elipseAmarelaFinal.setTranslateX(elipseAmarelaFinal.getTranslateX() - elipseX);
        elipseAmarelaFinal = Shape.subtract(elipseAmarelaFinal, clip);
        elipseAmarelaFinal.setTranslateY(elipseAmarelaFinal.getTranslateY() + elipseY);
        elipseAmarelaFinal.setTranslateX(elipseAmarelaFinal.getTranslateX() + elipseX);
        elipseAmarelaFinal = Shape.subtract(elipseAmarelaFinal, clip);
        elipseAmarelaFinal.setScaleX(-1);

        Shape elipseAzulFinal = Shape.subtract(elipse, clip);
        elipseAzulFinal.setTranslateX(elipseAzulFinal.getTranslateX() - elipseX);
        elipseAzulFinal = Shape.subtract(elipseAzulFinal, clip);
        elipseAzulFinal.setTranslateY(elipseAzulFinal.getTranslateY() + elipseY);
        elipseAzulFinal.setTranslateX(elipseAzulFinal.getTranslateX() + elipseX);
        elipseAzulFinal = Shape.subtract(elipseAzulFinal, clip);
        elipseAzulFinal.setScaleY(-1);

        Shape elipseVerdeFinal = Shape.subtract(elipse, clip);
        elipseVerdeFinal.setTranslateX(elipseVerdeFinal.getTranslateX() - elipseX);
        elipseVerdeFinal = Shape.subtract(elipseVerdeFinal, clip);
        elipseVerdeFinal.setTranslateY(elipseVerdeFinal.getTranslateY() + elipseY);
        elipseVerdeFinal.setTranslateX(elipseVerdeFinal.getTranslateX() + elipseX);
        elipseVerdeFinal = Shape.subtract(elipseVerdeFinal, clip);
        elipseVerdeFinal.setScaleY(-1);
        elipseVerdeFinal.setScaleX(-1);

        elipseVerdeFinal.setFill(Color.GREEN);
        elipseVerdeFinal.setTranslateY(elipseVerdeFinal.getTranslateY() + elipseY / 2);
        elipseVerdeFinal.setTranslateX(elipseVerdeFinal.getTranslateX() - elipseX / 2);

        elipseAzulFinal.setFill(Color.BLUE);
        elipseAzulFinal.setTranslateY(elipseAzulFinal.getTranslateY() + elipseY / 2);
        elipseAzulFinal.setTranslateX(elipseAzulFinal.getTranslateX() + elipseX / 2);

        elipseAmarelaFinal.setFill(Color.rgb(255, 190, 0));
        elipseAmarelaFinal.setTranslateY(elipseAmarelaFinal.getTranslateY() - elipseY / 2);
        elipseAmarelaFinal.setTranslateX(elipseAmarelaFinal.getTranslateX() - elipseX / 2);

        elipseVermelhaFinal.setFill(Color.RED);
        elipseVermelhaFinal.setTranslateY(elipseVermelhaFinal.getTranslateY() - elipseY / 2);
        elipseVermelhaFinal.setTranslateX(elipseVermelhaFinal.getTranslateX() + elipseX / 2);

        Ellipse borda = new Ellipse(elipseX * 1.15, elipseY * 1.15);
        borda.setFill(Color.WHITE);

        StackPane coringa = new StackPane(borda, elipseVermelhaFinal, elipseAmarelaFinal, elipseAzulFinal, elipseVerdeFinal);
        coringa.setRotate(-40);

        return new Group(coringa);
    }

    private static Node gerarReverter(double tamanho, int cor) {
        Shape seta1 = gerarSeta(tamanho);
        seta1.setTranslateX(seta1.getTranslateX() + tamanho);
        seta1.setStroke(Color.BLACK);
        seta1.setStrokeWidth(tamanho * 0.1);
        
        Shape seta2 = gerarSeta(tamanho);
        seta2.setScaleX(-1);
        seta2.setScaleY(-1);
        seta2.setTranslateY(seta2.getTranslateX() + tamanho/2);
        seta2.setTranslateX(seta1.getTranslateX() - tamanho);
        seta2.setStroke(Color.BLACK);
        seta2.setStrokeWidth(tamanho * 0.1);
        
        switch (cor) {
            case Carta.COR_AMARELA:
                seta1.setFill(Color.rgb(255, 190, 0));
                seta2.setFill(Color.rgb(255, 190, 0));
                break;
            case Carta.COR_VERMELHA:
                seta1.setFill(Color.RED);
                seta2.setFill(Color.RED);
                break;
            case Carta.COR_VERDE:
                seta1.setFill(Color.GREEN);
                seta2.setFill(Color.GREEN);
                break;
            case Carta.COR_AZUL:
                seta1.setFill(Color.BLUE);
                seta2.setFill(Color.BLUE);
                break;
            case -1:
                seta1.setFill(Color.WHITE);
                seta2.setFill(Color.WHITE);
                break;
        }
        
        return new Group(new StackPane(seta1, seta2));
    }

    private static Shape gerarSeta(double tamanho) {
        Path cabeca = gerarCabecaSeta(tamanho * 0.9);
        
        double elipseRaio = tamanho * 0.5;

        Ellipse elipse = new Ellipse(elipseRaio, elipseRaio);
        elipse.setTranslateX(elipse.getTranslateX() + elipseRaio);

        Rectangle clip = new Rectangle(elipseRaio, elipseRaio);

        Shape elipseFinal = Shape.subtract(elipse, clip);
        elipseFinal.setTranslateX(elipseFinal.getTranslateX() - elipseRaio);
        elipseFinal = Shape.subtract(elipseFinal, clip);
        elipseFinal.setTranslateY(elipseFinal.getTranslateY() + elipseRaio);
        elipseFinal.setTranslateX(elipseFinal.getTranslateX() + elipseRaio);
        elipseFinal = Shape.subtract(elipseFinal, clip);
        elipseFinal.setScaleX(-1);
        elipseFinal.setTranslateX(elipseFinal.getTranslateX() - tamanho);
        
        Rectangle caule = new Rectangle(tamanho, elipseRaio);
        
        Shape corpo = Shape.union(elipseFinal, caule);
        cabeca.setTranslateX(cabeca.getTranslateX() + tamanho + elipseRaio);
        cabeca.setTranslateY(cabeca.getTranslateY() + elipseRaio * 0.5);
        
        Shape seta = Shape.union(corpo, cabeca);
        
        return seta;
    }
    
    private static Path gerarCabecaSeta(double tamanho) {
        Path cabeca = new Path();
        cabeca.strokeProperty().bind(cabeca.fillProperty());
        cabeca.setFill(Color.BLACK);

        cabeca.getElements().add(new MoveTo(0, 0));
        cabeca.getElements().add(new LineTo(0, 0));

        double angle = Math.atan2((0 - 0), (0 - 0)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        
        double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * tamanho + 0;
        double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * tamanho + 0;

        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * tamanho + 0;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * tamanho + 0;

        cabeca.getElements().add(new LineTo(x1, y1));
        cabeca.getElements().add(new LineTo(x2, y2));
        cabeca.getElements().add(new LineTo(0, 0));
        
        return cabeca;
    }

    private static Node gerarBloqueio(double tamanho, int cor) {
        Ellipse circuloExterno = new Ellipse(tamanho, tamanho);
        Ellipse circuloClip = new Ellipse(tamanho * 0.7, tamanho * 0.7);
        
        Shape circuloPronto = Shape.subtract(circuloExterno, circuloClip);
        circuloPronto.setTranslateX(tamanho);
        circuloPronto.setTranslateY(tamanho);
        
        Rectangle rect = new Rectangle(tamanho * 2, tamanho * 0.2);
        rect.setTranslateY(tamanho * 0.9);
        
        Shape bloqueio = Shape.union(circuloPronto, rect);
        
        bloqueio.setStroke(Color.BLACK);
        bloqueio.setStrokeWidth(tamanho * 0.05);
        
        switch (cor) {
            case Carta.COR_AMARELA:
                bloqueio.setFill(Color.rgb(255, 190, 0));
                break;
            case Carta.COR_VERMELHA:
                bloqueio.setFill(Color.RED);
                break;
            case Carta.COR_VERDE:
                bloqueio.setFill(Color.GREEN);
                break;
            case Carta.COR_AZUL:
                bloqueio.setFill(Color.BLUE);
                break;
            case -1:
                bloqueio.setFill(Color.WHITE);
                break;
        }
        
        return new Group(bloqueio);
    }
    
    public static Node gerarCartaVisual(int cor, int numero) {
        if (cor == -1) {
            return new StackPane(new Rectangle(LARGURA_CARTA, ALTURA_CARTA, Color.WHITE));
        } else {
            Rectangle corpo = new Rectangle(LARGURA_CARTA - DIFERENCA_BORDA_CORPO, ALTURA_CARTA - DIFERENCA_BORDA_CORPO);
            corpo.setArcHeight(BORDA_CARTA);
            corpo.setArcWidth(BORDA_CARTA);

            Rectangle borda = new Rectangle(LARGURA_CARTA, ALTURA_CARTA, Color.WHITE);
            borda.setArcHeight(BORDA_CARTA);
            borda.setArcWidth(BORDA_CARTA);
            borda.setId("borda_branca");

            Ellipse elipseBranca = new Ellipse(ELIPSE_X, ELIPSE_Y);
            elipseBranca.setRotate(-40);
            elipseBranca.setFill(Color.WHITE);

            Deque<Node> children = new ArrayDeque<>();
            children.add(borda);
            children.add(corpo);
            children.add(elipseBranca);

            switch (cor) {
                case Carta.COR_PRETA:
                    corpo.setFill(Color.BLACK);
                    break;
                case Carta.COR_VERMELHA:
                    corpo.setFill(Color.RED);
                    break;
                case Carta.COR_AMARELA:
                    corpo.setFill(Color.rgb(255, 190, 0));
                    break;
                case Carta.COR_AZUL:
                    corpo.setFill(Color.BLUE);
                    break;
                case Carta.COR_VERDE:
                    corpo.setFill(Color.GREEN);
                    break;
            }

            if (isEspecial(numero)) {
                switch (numero) {
                    case Carta.MAIS_DOIS: {
                        int translate = 7;
                        int tamanhoTexto = (int) Math.rint(ALTURA_CARTA / 2.3);

                        Text numeroMenorTopoText = new Text("+2");
                        numeroMenorTopoText.setFill(Color.WHITE);
                        numeroMenorTopoText.setFont(new Font(tamanhoTexto / 3));
                        numeroMenorTopoText.setTranslateX(numeroMenorTopoText.getTranslateX() - 27);
                        numeroMenorTopoText.setTranslateY(numeroMenorTopoText.getTranslateY() - 53);

                        Text numeroMenorBaixoText = new Text("+2");
                        numeroMenorBaixoText.setFill(Color.WHITE);
                        numeroMenorBaixoText.setFont(new Font(tamanhoTexto / 3));
                        numeroMenorBaixoText.setTranslateX(numeroMenorBaixoText.getTranslateX() + 27);
                        numeroMenorBaixoText.setTranslateY(numeroMenorBaixoText.getTranslateY() + 53);
                        numeroMenorBaixoText.setScaleX(-1);
                        numeroMenorBaixoText.setScaleY(-1);

                        children.add(numeroMenorTopoText);
                        children.add(numeroMenorBaixoText);

                        children.add(gerarCartinha(cor, translate, -translate));
                        children.add(gerarCartinha(cor, -translate, translate));
                        break;
                    }
                    case Carta.MAIS_QUATRO: {
                        int translate = 15;
                        int tamanhoTexto = (int) Math.rint(ALTURA_CARTA / 2.3);

                        Text numeroMenorTopoText = new Text("+4");
                        numeroMenorTopoText.setFill(Color.WHITE);
                        numeroMenorTopoText.setFont(new Font(tamanhoTexto / 2.5));
                        numeroMenorTopoText.setTranslateX(numeroMenorTopoText.getTranslateX() - 27);
                        numeroMenorTopoText.setTranslateY(numeroMenorTopoText.getTranslateY() - 53);

                        Text numeroMenorBaixoText = new Text("+4");
                        numeroMenorBaixoText.setFill(Color.WHITE);
                        numeroMenorBaixoText.setFont(new Font(tamanhoTexto / 2.5));
                        numeroMenorBaixoText.setTranslateX(numeroMenorBaixoText.getTranslateX() + 27);
                        numeroMenorBaixoText.setTranslateY(numeroMenorBaixoText.getTranslateY() + 53);
                        numeroMenorBaixoText.setScaleX(-1);
                        numeroMenorBaixoText.setScaleY(-1);

                        children.add(numeroMenorTopoText);
                        children.add(numeroMenorBaixoText);

                        children.add(gerarCartinha(Carta.COR_AMARELA, 0, translate));
                        children.add(gerarCartinha(Carta.COR_VERDE, translate, 0));
                        children.add(gerarCartinha(Carta.COR_AZUL, 0, -translate));
                        children.add(gerarCartinha(Carta.COR_VERMELHA, -translate, 0));
                        break;
                    }
                    case Carta.REVERTER:
                        Node setaCentral = gerarReverter(26, cor);
                        setaCentral.setRotate(-40);
                        
                        Node setaTopo = gerarReverter(10, -1);
                        setaTopo.setRotate(-40);
                        setaTopo.setTranslateX(setaTopo.getTranslateX() - 27);
                        setaTopo.setTranslateY(setaTopo.getTranslateY() - 45);
                        
                        Node setaBaixo = gerarReverter(10, -1);
                        setaBaixo.setRotate(-40);
                        setaBaixo.setTranslateX(setaBaixo.getTranslateX() + 27);
                        setaBaixo.setTranslateY(setaBaixo.getTranslateY() + 45);
                        
                        children.add(setaTopo);
                        children.add(setaBaixo);
                        children.add(setaCentral);
                        break;
                    case Carta.BLOQUEAR:
                        Node bloqueioCentral = gerarBloqueio(26, cor);
                        bloqueioCentral.setRotate(-40);
                        
                        Node bloqueioTopo = gerarBloqueio(10, -1);
                        bloqueioTopo.setRotate(-40);
                        bloqueioTopo.setTranslateX(bloqueioTopo.getTranslateX() - 27);
                        bloqueioTopo.setTranslateY(bloqueioTopo.getTranslateY() - 50);
                        
                        Node bloqueioBaixo = gerarBloqueio(10, -1);
                        bloqueioBaixo.setRotate(-40);
                        bloqueioBaixo.setTranslateX(bloqueioBaixo.getTranslateX() + 27);
                        bloqueioBaixo.setTranslateY(bloqueioBaixo.getTranslateY() + 50);
                        
                        children.add(bloqueioTopo);
                        children.add(bloqueioBaixo);
                        children.add(bloqueioCentral);
                        break;
                    case Carta.CORINGA:
                        Node coringa = gerarCoringa(ELIPSE_X, ELIPSE_Y);

                        Node coringaTopo = gerarCoringa(ELIPSE_X / 4, ELIPSE_Y / 4);
                        coringaTopo.setTranslateX(coringaTopo.getTranslateX() - 27);
                        coringaTopo.setTranslateY(coringaTopo.getTranslateY() - 50);

                        Node coringaBaixo = gerarCoringa(ELIPSE_X / 4, ELIPSE_Y / 4);
                        coringaBaixo.setTranslateX(coringaBaixo.getTranslateX() + 27);
                        coringaBaixo.setTranslateY(coringaBaixo.getTranslateY() + 50);
                        coringaBaixo.setScaleX(-1);
                        coringaBaixo.setScaleY(-1);

                        children.add(coringa);
                        children.add(coringaTopo);
                        children.add(coringaBaixo);
                        break;
                    default:
                        break;
                }
            } else {
                Text numeroText = new Text(Integer.toString(numero));
                numeroText.setId("numero_centro");
                int tamanhoTexto = (int) Math.rint(ALTURA_CARTA / 2.3);
                numeroText.setFont(new Font(tamanhoTexto));
                numeroText.setTranslateY(numeroText.getTranslateY() - 5);

                Text numeroMenorTopoText = new Text(Integer.toString(numero));
                numeroMenorTopoText.setFill(Color.WHITE);
                numeroMenorTopoText.setFont(new Font(tamanhoTexto / 3));
                numeroMenorTopoText.setTranslateX(numeroMenorTopoText.getTranslateX() - 27);
                numeroMenorTopoText.setTranslateY(numeroMenorTopoText.getTranslateY() - 53);

                Text numeroMenorBaixoText = new Text(Integer.toString(numero));
                numeroMenorBaixoText.setFill(Color.WHITE);
                numeroMenorBaixoText.setFont(new Font(tamanhoTexto / 3));
                numeroMenorBaixoText.setTranslateX(numeroMenorBaixoText.getTranslateX() + 27);
                numeroMenorBaixoText.setTranslateY(numeroMenorBaixoText.getTranslateY() + 53);
                numeroMenorBaixoText.setScaleX(-1);
                numeroMenorBaixoText.setScaleY(-1);

                switch (cor) {
                    case Carta.COR_PRETA:
                        numeroText.setFill(Color.BLACK);
                        break;
                    case Carta.COR_VERMELHA:
                        numeroText.setFill(Color.RED);
                        break;
                    case Carta.COR_AMARELA:
                        numeroText.setFill(Color.rgb(255, 190, 0));
                        break;
                    case Carta.COR_AZUL:
                        numeroText.setFill(Color.BLUE);
                        break;
                    case Carta.COR_VERDE:
                        numeroText.setFill(Color.GREEN);
                        break;
                }

                if (numero == 9 || numero == 6) {
                    numeroText.setUnderline(true);
                    numeroMenorBaixoText.setUnderline(true);
                    numeroMenorTopoText.setUnderline(true);
                }

                children.add(numeroText);
                children.add(numeroMenorTopoText);
                children.add(numeroMenorBaixoText);
            }

            StackPane carta = new StackPane();

            children.stream().forEach((node) -> {
                carta.getChildren().add(node);
            });

            return new Group(carta);
        }
    }

    private static boolean isEspecial(int numero) {
        return numero == Carta.BLOQUEAR || numero == Carta.CORINGA || numero == Carta.MAIS_DOIS || numero == Carta.MAIS_QUATRO || numero == Carta.REVERTER;
    }
}

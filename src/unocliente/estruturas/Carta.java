package unocliente.estruturas;

public class Carta {

    private int cor;
    private int numero;

    public static final int COR_PRETA = 0;
    public static final int COR_VERMELHA = 1;
    public static final int COR_AMARELA = 2;
    public static final int COR_AZUL = 3;
    public static final int COR_VERDE = 4;
    public static final int MAIS_DOIS = 10;
    public static final int REVERTER = 11;
    public static final int BLOQUEAR = 12;
    public static final int MAIS_QUATRO = 13;
    public static final int CORINGA = 14;

    public Carta(int cor, int numero) {
        this.cor = cor;
        this.numero = numero;
    }

    public String serializarCarta() {
        return cor + "," + numero;
    }

    @Override
    public String toString() {
        switch (numero) {
            case Carta.MAIS_QUATRO:
                return "+4 (troca de cor)";
            case Carta.CORINGA:
                return "Troca de cor";
        }

        String nomeCarta = "";
        boolean reversao = false;

        switch (numero) {
            case Carta.MAIS_DOIS:
                nomeCarta += "+2 ";
                break;
            case Carta.BLOQUEAR:
                nomeCarta += "Bloqueio ";
                break;
            case Carta.REVERTER:
                nomeCarta += "Reversão ";
                reversao = true;
                break;
            default:
                nomeCarta += numero + " ";
                break;
        }

        switch (cor) {
            case Carta.COR_AMARELA:
                if (reversao) {
                    nomeCarta += "amarela";
                } else {
                    nomeCarta += "amarelo";
                }
                break;
            case Carta.COR_VERMELHA:
                if (reversao) {
                    nomeCarta += "vermelha";
                } else {
                    nomeCarta += "vermelho";
                }
                break;
            case Carta.COR_VERDE:
                nomeCarta += "verde";
                break;
            case Carta.COR_AZUL:
                nomeCarta += "azul";
                break;

        }

        return nomeCarta;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carta other = (Carta) obj;
        if (this.cor != other.cor) {
            return false;
        }
        return this.numero == other.numero;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.cor;
        hash = 89 * hash + this.numero;
        return hash;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}

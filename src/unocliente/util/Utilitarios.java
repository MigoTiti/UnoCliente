package unocliente.util;

import unocliente.estruturas.Partida;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import unocliente.estruturas.Carta;

public class Utilitarios {
    
    public static Carta decodificarCarta(String raw) {
        StringTokenizer st = new StringTokenizer(raw, ",");
        return new Carta(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }
    
    public static List<Carta> decodificarCartas(StringTokenizer raw) {
        List<Carta> cartas = new ArrayList<>();
        
        while (raw.hasMoreTokens()) {
            String proximoToken = raw.nextToken();
            
            if (proximoToken.trim().equals(""))
                break;
            
            cartas.add(decodificarCarta(proximoToken));
        }
        
        return cartas;
    }
    
    public static List<Partida> decodificarPartidas(StringTokenizer raw) {
        List<Partida> partidas = new ArrayList<>();
        
        while (raw.hasMoreTokens()) {
            String proximoToken = raw.nextToken();
            
            if (proximoToken.trim().equals(""))
                break;
            
            int id = Integer.parseInt(proximoToken);
            String nome = raw.nextToken();
            int nJogadores = Integer.parseInt(raw.nextToken());
            int jogadoresConectados = Integer.parseInt(raw.nextToken());
            partidas.add(new Partida(nome, nJogadores, jogadoresConectados, id));
        }
        
        return partidas;
    }
}

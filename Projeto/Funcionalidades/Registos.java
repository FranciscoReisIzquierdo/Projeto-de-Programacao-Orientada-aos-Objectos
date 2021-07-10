package Funcionalidades;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class Registos implements Serializable{
    private String equipaCasa;
    private String equipaFora;
    private int golosCasa;
    private int golosFora;
    private LocalDate date;
    private List<Integer> jogadoresCasa;
    private List<Integer> jogadoresFora;
    Map<Integer, Integer> substituicoesCasa = new HashMap<>();
    Map<Integer, Integer> substituicoesFora = new HashMap<>();
    private int substituicoes_casa;
    private int substituicoes_fora;

    public Registos(String ec, String ef, int gc, int gf, LocalDate d,  List<Integer> jc, Map<Integer, Integer> sc,  List<Integer> jf, Map<Integer, Integer> sf){
        equipaCasa = ec;
        equipaFora = ef;
        golosCasa = gc;
        golosFora = gf;
        date = d;
        jogadoresCasa = new ArrayList<>(jc);
        jogadoresFora = new ArrayList<>(jf);
        substituicoesCasa = new HashMap<>(sc);
        substituicoesFora = new HashMap<>(sf);
    }

    public static Registos parse(String input){
        String[] campos = input.split(",");
        String[] data = campos[4].split("-");
        List<Integer> jc = new ArrayList<>();
        List<Integer> jf = new ArrayList<>();
        Map<Integer, Integer> subsC = new HashMap<>();
        Map<Integer, Integer> subsF = new HashMap<>();
        for (int i = 5; i < 16; i++){
            jc.add(Integer.parseInt(campos[i]));
        }
        for (int i = 16; i < 19; i++){
            String[] sub = campos[i].split("->");
            subsC.put(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]));
        }
        for (int i = 19; i < 30; i++){
            jf.add(Integer.parseInt(campos[i]));
        }
        for (int i = 30; i < 33; i++){
            String[] sub = campos[i].split("->");
            subsF.put(Integer.parseInt(sub[0]), Integer.parseInt(sub[1]));
        }
        return new Registos(campos[0], campos[1], Integer.parseInt(campos[2]), Integer.parseInt(campos[3]),
                        LocalDate.of(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])),
                        jc, subsC, jf, subsF);
    }
    
    /**
     * Metodo que obtem o nome da equipa da casa
     */
    public String getNomeCasa(){
        return equipaCasa;
    }
    
    /**
     * Metodo que obtem o nome da equipa da casa
     */
    public String getNomeFora(){
        return equipaFora;
    }
    
    /**
     * Metodo que da o historico do jogo
     */
    public String historico(){
        StringBuilder sb= new StringBuilder();
        sb.append("Data: ").append(date).append("\n");
        sb.append(equipaCasa).append(" ").append(golosCasa).append("-").append(golosFora).append(" ").append(equipaFora).append("\n");
        return sb.toString();
    }
    
    
    
    /*
     * Metodo que organiza um Jogo
     *
    public void constroiJogo(Map equipas){
        casa= (Equipa) equipas.get(equipaCasa); //FAZER CLONE
        fora= (Equipa) equipas.get(equipaFora); //FAZER CLONE
    }*/

    /**
     * Metodo toString de Jogo
     */
    public String painelJogo(){
        StringBuilder sb= new StringBuilder();
        int largura= 30;
        sb.append("-----------------------------------Jogo-----------------------------------");
        sb.append("\nData: ").append(date);
        sb.append("\nJogo: ").append(equipaCasa).append(" ").append(golosCasa).append("-").append(golosFora).append(" ").append(equipaFora);
        sb.append("\nSubstituicoes de ").append(equipaCasa).append(": \n");
        if(substituicoesCasa!= null){
            for(int key: substituicoesCasa.keySet()){
                sb.append(key).append("->").append(substituicoesCasa.get(key)).append("; ");
            }
        }
        sb.append("\nSubstituicoes de ").append(equipaFora).append(": \n");
        if(substituicoesFora!= null){
            for(int key: substituicoesFora.keySet()){
                sb.append(key).append("->").append(substituicoesFora.get(key)).append("; ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
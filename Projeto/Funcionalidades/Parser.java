package Funcionalidades;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class Parser{
    private static Map<String, Equipa> equipas;
    private static List<Registos> jogos;
    
    public static void parse() throws LinhaIncorretaException {
        List<String> linhas = lerFicheiro("output.txt");
        equipas = new HashMap<>(); //nome, equipa
        Map<Integer, Jogador> jogadores = new HashMap<>(); //numero, jogador
        jogos = new ArrayList<>();
        Equipa ultima = null; Jogador j = null;
        String[] linhaPartida;
        int contador= 0;
        for (String linha : linhas) {
            if(contador== 21) contador= 0;
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){
                case "Equipa":
                    Equipa e = Equipa.parse(linhaPartida[1]);
                    equipas.put(e.getNome(), e);
                    ultima = e;
                    break;
                case "Guarda-Redes":
                if(contador< 12) j = GuardaRedes.parse(linhaPartida[1], ultima.getNome(), "Baliza");
                else j = GuardaRedes.parse(linhaPartida[1], ultima.getNome(), "");
                    jogadores.put(j.getNumeroJogador(), j);
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.insereJogador(j.clone()); //if no team was parsed previously, file is not well-formed
                    break;
                case "Defesa":
                if(contador< 12) j = Defesa.parse(linhaPartida[1], ultima.getNome(), "Centro");
                else j = Defesa.parse(linhaPartida[1], ultima.getNome(), "");
                    jogadores.put(j.getNumeroJogador(), j);
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.insereJogador(j.clone()); //if no team was parsed previously, file is not well-formed
                    break;
                case "Medio":
                    if(contador< 12){
                        if(contador== 6 || contador== 9) j = Medio.parse(linhaPartida[1], ultima.getNome(), "Lado");
                        else if(contador== 7 || contador== 8) j = Medio.parse(linhaPartida[1], ultima.getNome(), "Centro");
                    }
                    else j = Medio.parse(linhaPartida[1], ultima.getNome(), "");
                    jogadores.put(j.getNumeroJogador(), j);
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.insereJogador(j.clone()); //if no team was parsed previously, file is not well-formed
                    break;
                case "Lateral":
                    if(contador< 12) j = Lateral.parse(linhaPartida[1], ultima.getNome(), "Lado");
                    else j = Lateral.parse(linhaPartida[1], ultima.getNome(), "");
                    jogadores.put(j.getNumeroJogador(), j);
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.insereJogador(j.clone()); //if no team was parsed previously, file is not well-formed
                    break;
                case "Avancado": 
                    if(contador< 12) j = Avancado.parse(linhaPartida[1], ultima.getNome(), "Centro");
                    else j = Avancado.parse(linhaPartida[1], ultima.getNome(), "");
                    jogadores.put(j.getNumeroJogador(), j);
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.insereJogador(j.clone()); //if no team was parsed previously, file is not well-formed
                    break;
                case "Jogo":
                    Registos jo = Registos.parse(linhaPartida[1]);
                    jogos.add(jo);
                    break;
                default:
                    throw new LinhaIncorretaException();
    
            }
            contador++;
        }
    
        //debug
        for (Equipa e: equipas.values()){
            e.constroiPlantel();
            for(Registos reg: jogos){
                if(reg.getNomeCasa().equals(e.getNome()) || reg.getNomeFora().equals(e.getNome())) e.getJogos().add(reg);
                    
            } 
            //System.out.println(e.toString());
            //int formacao []= new int []{1, 4, 3, 3};
            //e.alteraFormacao(formacao);
            //System.out.println(e.toString());
         /*
            try
        {
            ultima.substituicao(36, 30);
            System.out.println(e.toString());
        }
        catch (ComandoInvalidoException cie)
        {
            System.out.println(cie);
            
        }*/
        }
        /*
        for (Registos jog: jogos){
            jog.constroiJogo(equipas);
            //System.out.println(jog.painelJogo());
        }*/


    }

    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }
    
    public static Map<String, Equipa> getMapEquipas(){
        return equipas;
    }
    
    public static List<Registos> getListJogos(){
        return jogos;
    }


}
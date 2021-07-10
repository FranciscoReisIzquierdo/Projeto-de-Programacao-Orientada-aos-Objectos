package Funcionalidades;

import java.util.*;
import java.time.LocalDate;
import java.io.*;

public class Jogo implements Serializable{
    private Equipa casa;
    private Equipa fora;
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
    
    //Construtores
    /*
    public Jogo(){
        this.tempo= 0;
        Equipa visitante= new Equipa();
        Equipa visitada= new Equipa();
        
        this.visitante= visitante;
        this.visitada= visitada;
        this.golos_visitante= 0;
        this.golos_visitada= 0;
        
        
    }*/
    
    public Jogo(Equipa visitante, Equipa visitada){
        fora= visitante;
        casa= visitada;
        golosCasa= 0;
        golosFora= 0;
        equipaCasa= casa.getNome();
        equipaFora= fora.getNome();
        date= LocalDate.now();
        jogadoresCasa= new ArrayList<>();
        for(Jogador jog: casa.getJogadores()) jogadoresCasa.add(jog.getNumeroJogador());
        jogadoresFora= new ArrayList<>();
        for(Jogador jog: fora.getJogadores()) jogadoresCasa.add(jog.getNumeroJogador());
    }
    
    //Metodos acessores e modificadores
    public Equipa getEquipaVisitante(){
        return fora;
    }
    
    public Equipa getEquipaVisitada(){
        return casa;
    }
    
    public String getFinalResult(){
        if(golosCasa> golosFora) return casa.getNome();
        else if(golosCasa< golosFora) return fora.getNome();
        else return "Empate";
    }
    
    /**
     * Metodo que devolve o Map das substituiçoes da equipa da casa
     */
    public Map<Integer, Integer> getSubsCasa(){
        return substituicoesCasa;
    }
    
    /**
     * Metodo que devolve o Map das substituiçoes da equipa de fora
     */
    public Map<Integer, Integer> getSubsFora(){
        return substituicoesFora;
    }
    
    /**
     * Metodo que cria o registo pos jogo
     */
    public Registos criaRegisto(){
        Registos reg= new Registos(casa.getNome(), fora.getNome(), golosCasa, golosFora, date, jogadoresCasa, substituicoesCasa, jogadoresFora, substituicoesFora);
        return reg;
    }
    
    /**
     * Metodo toString da classe Jogo
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("Data: ").append(date);
        sb.append("\nJogo: ").append(equipaCasa).append(" ").append(golosCasa).append("-").append(golosFora).append(" ").append(equipaFora).append("\n");
        sb.append("--------------Equipa da Casa---------------\n");
        sb.append(casa.toString());
        sb.append("\n--------------Equipa de Fora---------------\n");
        sb.append(fora.toString());
        sb.append("\nSubstituicoes de ").append(casa.getNome()).append("\n");
        for(int key: substituicoesCasa.keySet()){
            sb.append(key).append("->").append(substituicoesCasa.get(key)).append("; ");
        }
        sb.append("\nSubstituicoes de ").append(fora.getNome()).append("\n");
        for(int key: substituicoesFora.keySet()){
            sb.append(key).append("->").append(substituicoesFora.get(key)).append("; ");
        }
        return sb.toString();
    }
    
    //Metodos abstratos
    public void goloVisitada(){
        golosCasa++;
    }
    
    public void goloVisitante(){
        golosFora++;
    }
    
    public String showResult(){
        String resultado= casa.getNome()+"(Casa) "+ golosCasa+ " - "+ golosFora+"(Fora) " +  fora.getNome();
        return resultado;
    }    
    
    /**
     * Metodo que aumenta o cansaço dos jogadores titulares
     */
    public void aumentaCansaco(){
        for(Jogador jog: casa.getTitulares()) jog.aumentaCansaco();
        for(Jogador jog: fora.getTitulares()) jog.aumentaCansaco();
    }
    
    /**
     * Metodo que calcula o evento de cada parte do jogo
     */
    public String calculaEvento(){
        Random sorte= new Random();
        int overallCasa= casa.getOverallEquipa();
        int overallFora= fora.getOverallEquipa();
        int cansacoCasa= casa.cansacoEquipa();
        int cansacoFora= fora.cansacoEquipa();
        
        List<Jogador> titularesFora= fora.getTitulares();
        List<Jogador> titularesCasa= casa.getTitulares();
        StringBuilder sb= new StringBuilder();     
        
        int fatorSorteCasa= (int)(Math.random() * (casa.getOverallEquipa()- 1+ 1) + 1);
        int fatorSorteFora= (int)(Math.random() * (fora.getOverallEquipa()- 1+ 1) + 1);
        int resultado= (overallCasa+ fatorSorteCasa)- (overallFora+ fatorSorteFora);
            
        if(resultado< 0){ //Equipa visitante esta a atacar e pode marcar
            sb.append("Equipa visitante "+ fora.getNome()+ " esta no ataque!\n");
            Jogador redes= titularesCasa.get(0);
            int fatorSorteRedes= (int)(Math.random() * (redes.getOverall_Jogo()- 1+ 1) + 1);
            if(overallFora+ fatorSorteFora- cansacoFora> redes.getOverall_Jogo()+ fatorSorteRedes- redes.getCansaco()){
                sb.append("O "+ fora.getNome()+ " esta muito perto de marcar, circula a bola e...\nGolo da equipa visitante\n");
                goloVisitante();
                sb.append(showResult());
            }
            else sb.append("Oportunidade desperdicada");
            }
        else{
            sb.append("Equipa visitada "+ casa.getNome()+ " esta no ataque!\n");
            Jogador redes= titularesFora.get(0);
            int fatorSorteRedes= (int)(Math.random() * (redes.getOverall_Jogo()- 1+ 1) + 1);
            if(overallCasa+ fatorSorteCasa- cansacoCasa> redes.getOverall_Jogo()+ fatorSorteRedes- redes.getCansaco()){
                sb.append("O "+ casa.getNome()+ " esta muito perto de marcar, circula a bola e...\nGolo da equipa visitada\n");
                goloVisitada();
                sb.append(showResult());
            }
            else sb.append("Oportunidade desperdicada");
        }
        aumentaCansaco();
        return sb.toString();
    }   
}

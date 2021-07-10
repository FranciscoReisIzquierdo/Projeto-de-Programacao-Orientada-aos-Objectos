package Funcionalidades;

import java.io.*;
 
public class Medio extends Jogador implements Desporto, Serializable{
    private int recuperacao;

    public Medio(){
    }
    
    public Medio(String nomeJ, int numeroJ, int vel, int res, int des, int imp, int cab, int rem, int p, int rec, String equipa, String role){
        super(nomeJ, numeroJ, vel, res, des, imp, cab, rem, p, equipa, role);
        super.setPosicao_Original("Medio");
        super.setPosicao_Jogo("Medio");
        setHabilidade_Especial(rec);
        int overall= calculaOverall(vel, res, des, imp, cab, rem, p, rec);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }
    
    public Medio(Medio medio){
        super(medio);
        int recuperacao= medio.getHabilidade_Especial();
        setHabilidade_Especial(recuperacao);
        int overall= calculaOverall(super.getVelocidade(), super.getResistencia(), super.getDestreza(),
        super.getImpulsao(), super.getCabeca(), super.getRemate(), super.getPasse(), recuperacao);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }
    
    /**
     * Metodo que trata das linhas do ficheiro e cria um Medio
     */
    public static Medio parse(String input, String equipa, String role){
        String[] campos = input.split(",");
        return new Medio(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                Integer.parseInt(campos[8]),
                Integer.parseInt(campos[9]), equipa, role);
    }
    
    protected Jogador clone(){
        return new Medio(this);
    }
    
    /**
     * Metodo para obter o atributo especial (recuperacao)
     */
    public int getHabilidade_Especial(){
        return recuperacao;
    }
    
    /**
     * Metodo para "settar" o atributo especial (recuperacao)
     */
    public void setHabilidade_Especial(int habilidade_especial){
        this.recuperacao= habilidade_especial;
    }
    
    /*
     * Fator Velocidade-> 0.7
     * Fator Resistencia-> 0.8
     * Fator Destreza-> 0.8
     * Fator Impulsao-> 0.8
     * Fator Jogo de Cabeça-> 0.8
     * Fator Remate-> 0.7
     * Fator Capacidade de Passe-> 0.8
     * Fator Finalizacao-> 1.0
     */
    /**
     * Metodo que calcula o overall de um Medio
     */
    public int calculaOverall(int vel, int res, int des, int imp, int cab, int rem, int p, int habilidade_especial){
        int overall= (int)(vel*0.7+ res*0.8+ des*0.8+ imp*0.8+ cab*0.8+ rem*0.7+ p*0.8+ habilidade_especial)/ 8; 
        return overall;
    }
    
    /**
     * Metodo que "cria" o atributo especial (recuperacao) 
     */
    public int habilidadeEspecial(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= getHabilidade_Especial();
        return habilidade_especial;
    }
    
    /**
     * Metodo toString do Medio
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append(super.toString());
        sb.append("\nEstatisticas: ");
        sb.append("Velocidade: ").append(super.getVelocidade()).append(" Resistencia: ").append(super.getResistencia())
        .append(" Destreza: ").append(super.getDestreza()).append(" Impulsao: ").append(super.getImpulsao()).append(" Jogo de Cabeca: ")
        .append(super.getCabeca()).append(" Remate: ").append(super.getRemate()).append(" Capacidade de Passe: ").append(super.getPasse());
        sb.append(" Recuperacao: ").append(recuperacao).append("\n");
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------");
        return sb.toString();
    }
    
    /**
     * Metodo que cria de facto o atributo especial (recuperacao), em vez de o receber por default
     */
    public int criaRecuperacao(int vel, int res, int des, int imp, int cab, int rem, int p){
        int recuperacao= (int) (des+ res+ imp+ p)/ 4;
        return recuperacao;
    }
    
    /**
     * Metodo abstrato que calcula o overall do jogador apos mudar de posicao
     */
    public int calculaOverallJogador(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= criaRecuperacao(vel, res, des, imp, cab, rem, p);
        return calculaOverall(vel, res, des, imp, cab, rem, p, habilidade_especial);
    }

}
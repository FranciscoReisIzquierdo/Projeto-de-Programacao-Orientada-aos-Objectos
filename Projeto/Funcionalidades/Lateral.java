package Funcionalidades;

import java.io.*;
 
public class Lateral extends Jogador implements Desporto, Serializable{
    private int cruzamento;
    
    public Lateral(){
    }
    
    public Lateral(String nomeJ, int numeroJ, int vel, int res, int des, int imp, int cab, int rem, int p, int cruz, String equipa, String role){
        super(nomeJ, numeroJ, vel, res, des, imp, cab, rem, p, equipa, role);
        super.setPosicao_Original("Lateral");
        super.setPosicao_Jogo("Lateral");
        setHabilidade_Especial(cruz);
        int overall= calculaOverall(vel, res, des, imp, cab, rem, p, cruz);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }
    
    public Lateral(Lateral lateral){
        super(lateral);
        int cruzamento= lateral.getHabilidade_Especial();
        setHabilidade_Especial(cruzamento);
        int overall= calculaOverall(super.getVelocidade(), super.getResistencia(), super.getDestreza(),
        super.getImpulsao(), super.getCabeca(), super.getRemate(), super.getPasse(), cruzamento);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }
    
    /**
     * Metodo que trata das linhas do ficheiro e cria um Lateral
     */
    public static Lateral parse(String input, String equipa, String role){
        String[] campos = input.split(",");
        return new Lateral(campos[0], Integer.parseInt(campos[1]),
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
        return new Lateral(this);
    }
    
    /**
     * Metodo para obter o atributo especial (cruzamento)
     */
    public int getHabilidade_Especial(){
        return cruzamento;
    }
    
    /**
     * Metodo para "settar" o atributo especial (cruzamento)
     */
    public void setHabilidade_Especial(int habilidade_especial){
        this.cruzamento= habilidade_especial;
    }
    
    /*
     * Fator Velocidade-> 0.9
     * Fator Resistencia-> 0.7
     * Fator Destreza-> 0.8
     * Fator Impulsao-> 0.7
     * Fator Jogo de Cabeça-> 0.7
     * Fator Remate-> 0.8
     * Fator Capacidade de Passe-> 0.8
     * Fator Cruzamento-> 1.0
     */
    /**
     * Metodo que calcula o overall de um Lateral
     */
    public int calculaOverall(int vel, int res, int des, int imp, int cab, int rem, int p, int habilidade_especial){
        int overall= (int)(vel*0.9+ res*0.7+ des*0.8+ imp*0.7+ cab*0.7+ rem*0.8+ p*0.8+ habilidade_especial)/ 8; 
        return overall;
    }
    
    /**
     * Metodo que cria o atributo especial (cruzamento) 
     */
    public int habilidadeEspecial(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= getHabilidade_Especial();
        return habilidade_especial;
    }
    
    
    /**
     * Metodo toString do Lateral
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append(super.toString());
        sb.append("\nEstatisticas: ");
        sb.append("Velocidade: ").append(super.getVelocidade()).append(" Resistencia: ").append(super.getResistencia())
        .append(" Destreza: ").append(super.getDestreza()).append(" Impulsao: ").append(super.getImpulsao()).append(" Jogo de Cabeca: ")
        .append(super.getCabeca()).append(" Remate: ").append(super.getRemate()).append(" Capacidade de Passe: ").append(super.getPasse());
        sb.append(" Cruzamento: ").append(cruzamento).append("\n");
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------");
        return sb.toString();
    }
    
    /**
     * Metodo que cria de facto o atributo especial (cruzamento), em vez de o receber por default
     */
    public int criaCruzamento(int vel, int res, int des, int imp, int cab, int rem, int p){
        int cruzamento= (int) (vel+ rem+ p)/ 3;
        return cruzamento;
    }
    
    /**
     * Metodo abstrato que calcula o overall do jogador apos mudar de posicao
     */
    public int calculaOverallJogador(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= criaCruzamento(vel, res, des, imp, cab, rem, p);
        return calculaOverall(vel, res, des, imp, cab, rem, p, habilidade_especial);
    }
}
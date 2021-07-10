package Funcionalidades;

import java.io.*;
 
public class Avancado extends Jogador implements Desporto, Serializable{
    private int finalizacao;
    
    public Avancado(){
    }
    
    public Avancado(String nomeJ, int numeroJ, int vel, int res, int des, int imp, int cab, int rem, int p, String equipa, String role) {
        super(nomeJ, numeroJ, vel, res, des, imp, cab, rem, p, equipa, role);
        super.setPosicao_Original("Avancado");
        super.setPosicao_Jogo("Avancado");
        int finalizacao= habilidadeEspecial(vel, res, des, imp, cab, rem, p);
        setHabilidade_Especial(finalizacao);
        int overall= calculaOverall(vel, res, des, imp, cab, rem, p, finalizacao);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }
    
    public Avancado(Avancado avancado){
        super(avancado);
        int finalizacao= avancado.getHabilidade_Especial();
        setHabilidade_Especial(finalizacao);
        int overall= calculaOverall(super.getVelocidade(), super.getResistencia(), super.getDestreza(),
        super.getImpulsao(), super.getCabeca(), super.getRemate(), super.getPasse(), finalizacao);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }

    /**
     * Metodo que trata das linhas do ficheiro e cria um Avancado
     */
    public static Avancado parse(String input, String equipa, String role){
        String[] campos = input.split(",");
        return new Avancado(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                Integer.parseInt(campos[8]), equipa, role);
    }
    
    
    protected Jogador clone(){
        return new Avancado(this);
    }
    
    
    /**
     * Metodo que obtem o atributo especial (finalizacao)
     */
    public int getHabilidade_Especial(){
        return finalizacao;
    }
    
    /**
     * Metodo para "settar" o atributo especial (finalizacao)
     */
    public void setHabilidade_Especial(int habilidade_especial){
        this.finalizacao= habilidade_especial;
    }
    
    /*
     * Fator Velocidade-> 0.8
     * Fator Resistencia-> 0.7
     * Fator Destreza-> 0.7
     * Fator Impulsao-> 0.8
     * Fator Jogo de Cabeça-> 0.8
     * Fator Remate-> 0.9
     * Fator Capacidade de Passe-> 0.7
     * Fator Finalizacao-> 1.0
     */
    /**
     * Metodo que calcula o overall de um Avancado
     */
    public int calculaOverall(int vel, int res, int des, int imp, int cab, int rem, int p, int habilidade_especial){
        int overall= (int)(vel*0.8+ res*0.7+ des*0.7+ imp*0.8+ cab*0.8+ rem*0.9+ p*0.7+ habilidade_especial)/ 8; 
        return overall;
    }
    
    /**
     * Metodo que cria o atributo especial (finalizaçao) 
     */
    public int habilidadeEspecial(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= (int) (des+ imp+ cab+ rem)/ 4;
        return habilidade_especial;
    }
    
    /**
     * Metodo abstrato que calcula o overall do jogador apos mudar de posicao
     */
    public int calculaOverallJogador(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= habilidadeEspecial(vel, res, des, imp, cab, rem, p);
        return calculaOverall(vel, res, des, imp, cab, rem, p, habilidade_especial);
    }
    
    /**
     * Metodo toString do Avancado
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append(super.toString());
        sb.append("\nEstatisticas: ");
        sb.append("Velocidade: ").append(super.getVelocidade()).append(" Resistencia: ").append(super.getResistencia())
        .append(" Destreza: ").append(super.getDestreza()).append(" Impulsao: ").append(super.getImpulsao()).append(" Jogo de Cabeca: ")
        .append(super.getCabeca()).append(" Remate: ").append(super.getRemate()).append(" Capacidade de Passe: ").append(super.getPasse());
        sb.append(" Finalizacao: ").append(finalizacao).append("\n");
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------");
        return sb.toString();
    }

}
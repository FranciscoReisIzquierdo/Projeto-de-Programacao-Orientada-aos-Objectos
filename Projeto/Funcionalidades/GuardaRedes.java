package Funcionalidades;

import java.io.*;
 
public class GuardaRedes extends Jogador implements Desporto, Serializable{
    private int elasticidade;
    
    public GuardaRedes(){
    }
    
    public GuardaRedes (String nomeJ, int numeroJ, int vel, int res, int des, int imp, int cab, int rem, int p, int elast, String equipa, String role) {
        super(nomeJ, numeroJ, vel, res, des, imp, cab, rem, p, equipa, role);
        super.setPosicao_Original("Guarda Redes");
        super.setPosicao_Jogo("Guarda Redes");
        setHabilidade_Especial(elast);
        int overall= calculaOverall(vel, res, des, imp, cab, rem, p, elast);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }
    
    public GuardaRedes(GuardaRedes redes){
        super(redes);
        int elasticidade= redes.getHabilidade_Especial();
        setHabilidade_Especial(elasticidade);
        int overall= calculaOverall(super.getVelocidade(), super.getResistencia(), super.getDestreza(),
        super.getImpulsao(), super.getCabeca(), super.getRemate(), super.getPasse(), elasticidade);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }

    /**
     * Metodo que trata das linhas do ficheiro e cria um Guarda Redes
     */
    public static GuardaRedes parse(String input, String equipa, String role){
        String[] campos = input.split(",");
        return new GuardaRedes(campos[0], Integer.parseInt(campos[1]),
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
        return new GuardaRedes(this);
    }
    
    /**
     * Metodo para obter o atributo especial (elasticidade)
     */
    public int getHabilidade_Especial(){
        return elasticidade;
    }
    
    /**
     * Metodo para "settar" o atributo especial (elasticidade)
     */
    public void setHabilidade_Especial(int habilidade_especial){
        this.elasticidade= habilidade_especial;
    }
    
    
    /*
     * Fator Velocidade-> 0.7
     * Fator Resistencia-> 0.8
     * Fator Destreza-> 0.8
     * Fator Impulsao-> 0.9
     * Fator Jogo de Cabeça-> 0.7
     * Fator Remate-> 0.5
     * Fator Capacidade de Passe-> 0.7
     * Fator Elasticidade-> 1.0
     */
    /**
     * Metodo que calcula o overall de um Guarda Redes
     */
    public int calculaOverall(int vel, int res, int des, int imp, int cab, int rem, int p, int habilidade_especial){
        int overall= (int)(vel*0.7+ res*0.8+ des*0.8+ imp*0.9+ cab*0.7+ rem*0.5+ p*0.7+ habilidade_especial)/ 8; 
        return overall;
    }
    
    /**
     * Metodo que "cria" o atributo especial (elasticidade) 
     */
    public int habilidadeEspecial(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= getHabilidade_Especial();
        return habilidade_especial;
    }
    
    /**
     * Metodo toString do Guarda Redes
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append(super.toString());
        sb.append("\nEstatisticas: ");
        sb.append("Velocidade: ").append(super.getVelocidade()).append(" Resistencia: ").append(super.getResistencia())
        .append(" Destreza: ").append(super.getDestreza()).append(" Impulsao: ").append(super.getImpulsao()).append(" Jogo de Cabeca: ")
        .append(super.getCabeca()).append(" Remate: ").append(super.getRemate()).append(" Capacidade de Passe: ").append(super.getPasse());
        sb.append(" Elasticidade: ").append(elasticidade).append("\n");
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------");
        return sb.toString();
    }
    
    /**
     * Metodo que cria de facto o atributo especial (elasticidade), em vez de o receber por default
     */
    public int criaElasticidade(int vel, int res, int des, int imp, int cab, int rem, int p){
        int elasticidade= (int) (des+ imp)/ 2;
        return elasticidade;
    }
    
    /**
     * Metodo abstrato que calcula o overall do jogador apos mudar de posicao
     */
    public int calculaOverallJogador(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= criaElasticidade(vel, res, des, imp, cab, rem, p);
        return calculaOverall(vel, res, des, imp, cab, rem, p, habilidade_especial);
    }
}
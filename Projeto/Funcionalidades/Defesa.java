package Funcionalidades;

import java.io.*;
 
public class Defesa extends Jogador implements Desporto, Serializable{
    private int desarme;
    
    public Defesa(){
    }
    
    public Defesa(String nomeJ, int numeroJ, int vel, int res, int des, int imp, int cab, int rem, int p, String equipa, String role) {
        super(nomeJ, numeroJ, vel, res, des, imp, cab, rem, p, equipa, role);
        super.setPosicao_Original("Defesa");
        super.setPosicao_Jogo("Defesa");
        int desarme= habilidadeEspecial(vel, res, des, imp, cab, rem, p);
        setHabilidade_Especial(desarme);
        int overall= calculaOverall(vel, res, des, imp, cab, rem, p, desarme);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }
    
    public Defesa(Defesa defesa){
        super(defesa);
        int desarme= defesa.getHabilidade_Especial();
        setHabilidade_Especial(desarme);
        int overall= calculaOverall(super.getVelocidade(), super.getResistencia(), super.getDestreza(),
        super.getImpulsao(), super.getCabeca(), super.getRemate(), super.getPasse(), desarme);
        super.setOverall_Inicial(overall);
        super.setOverall_Jogo(overall);
    }

    /**
     * Metodo que trata das linhas do ficheiro e cria um Defesa
     */
    public static Defesa parse(String input, String equipa, String role){
        String[] campos = input.split(",");
        return new Defesa(campos[0], Integer.parseInt(campos[1]),
                Integer.parseInt(campos[2]),
                Integer.parseInt(campos[3]),
                Integer.parseInt(campos[4]),
                Integer.parseInt(campos[5]),
                Integer.parseInt(campos[6]),
                Integer.parseInt(campos[7]),
                Integer.parseInt(campos[8]), equipa, role);
    }
    
    
    protected Jogador clone(){
        return new Defesa(this);
    }
    
    /**
     * Metodo para obter o atributo especial (desarme)
     */
    public int getHabilidade_Especial(){
        return desarme;
    }
    
    /**
     * Metodo para "settar" o atributo especial (desarme)
     */
    public void setHabilidade_Especial(int habilidade_especial){
        this.desarme= habilidade_especial;
    }
    
    
    /*
     * Fator Velocidade-> 0.7
     * Fator Resistencia-> 0.7
     * Fator Destreza-> 0.7
     * Fator Impulsao-> 0.8
     * Fator Jogo de Cabeça-> 0.8
     * Fator Remate-> 0.6
     * Fator Capacidade de Passe-> 0.7
     * Fator Desarme-> 1.0
     */
    
    /**
     * Metodo que calcula o overall de um Defesa
     */
    public int calculaOverall(int vel, int res, int des, int imp, int cab, int rem, int p, int habilidade_especial){
        int overall= (int)(vel*0.7+ res*0.7+ des*0.7+ imp*0.8+ cab*0.8+ rem*0.6+ p*0.7+ habilidade_especial)/ 8; 
        return overall;
    }
    
    /**
     * Metodo que cria o atributo especial (desarme) 
     */
    public int habilidadeEspecial(int vel, int res, int des, int imp, int cab, int rem, int p){
        int habilidade_especial= (int) (vel+ res+ imp+ cab)/ 4;
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
     * Metodo toString do Defesa
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append(super.toString());
        sb.append("\nEstatisticas: ");
        sb.append("Velocidade: ").append(super.getVelocidade()).append(" Resistencia: ").append(super.getResistencia())
        .append(" Destreza: ").append(super.getDestreza()).append(" Impulsao: ").append(super.getImpulsao()).append(" Jogo de Cabeca: ")
        .append(super.getCabeca()).append(" Remate: ").append(super.getRemate()).append(" Capacidade de Passe: ").append(super.getPasse());
        sb.append(" Desarme: ").append(desarme).append("\n");
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------");
        return sb.toString();
    }

}
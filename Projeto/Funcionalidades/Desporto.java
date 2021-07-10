package Funcionalidades;


/**
 * Escreva a descrição da interface Desporto aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface Desporto{
    public int calculaOverall(int vel, int res, int des, int imp, int cab, int rem, int p, int habilidade_especial);
    public int habilidadeEspecial(int vel, int res, int des, int imp, int cab, int rem, int p);
    public int getHabilidade_Especial();
    public void setHabilidade_Especial(int habilidade_especial);
    public String toString();
}

package Funcionalidades;

import java.util.*;
import java.io.Serializable;
 

public abstract class Jogador implements Serializable{
    private String nomeJogador;
    private int numeroJogador;
    private int velocidade, resistencia, destreza, impulsao, cabeca, remate, passe;
    private int overall_inicial;
    private int overall_jogo;
    private String posicao_original;
    private String posicao_jogo;
    private String role;
    private String role_jogo;
    private boolean substituido; //Se foi substituido entao e true 
    private String equipa;
    private List<String> historial;
    private int cansaco;

    public Jogador(){
    }
    
    public Jogador(String nomeJ, int numeroJ, int vel, int res, int des, int imp, int cab, int rem, int p, String eq, String papel){
        nomeJogador = nomeJ;
        numeroJogador = numeroJ;
        velocidade = vel;
        resistencia = res;
        destreza = des;
        impulsao = imp;
        cabeca = cab;
        remate = rem;
        passe = p;
        substituido= false;
        equipa= eq;
        List<String> historial= new ArrayList<>();
        historial.add(eq);
        setHistorial(historial);
        role= papel;
        role_jogo= role;
        cansaco= 0;
    }

    public Jogador(Jogador j) {
        nomeJogador = j.getNomeJogador();
        numeroJogador = j.getNumeroJogador();
        velocidade = j.getVelocidade();
        resistencia = j.getResistencia();
        destreza = j.getDestreza();
        impulsao = j.getImpulsao();
        cabeca = j.getCabeca();
        remate = j.getRemate();
        passe = j.getPasse();
        posicao_original= j.getPosicao_Original();
        posicao_jogo= j.getPosicao_Jogo();
        substituido= j.getEstado();
        equipa= j.getEquipa();
        historial= j.getHistorial();
        role= j.getRole();
        role_jogo= role;
    }
    
    /**
     * Metodo que "setta" a habilidade especial do jogador
     */
    public abstract void setHabilidade_Especial(int habilidade_especial);
    
    /**
     * Metodo que obtem o nome do Jogador
     */
    public String getNomeJogador() {
        return nomeJogador;
    }
    
    /**
     * Metodo que "setta" o nome do Jogador
     */
    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }
    
    /**
     * Metodo que obtem o numero do Jogador
     */
    public Integer getNumeroJogador() {
        return numeroJogador;
    }
    
    /**
     * Metodo que "setta" o numero do Jogador
     */
    public void setNumeroJogador(int numeroJogador) {
        this.numeroJogador = numeroJogador;
    }
    
    /**
     * Metodo que obtem o overall do Jogador
     */
    public int getOverall_Inicial(){
        return overall_inicial;
    }
    
    /**
     * Metodo que "setta" o overall do Jogador
     */
    public void setOverall_Inicial(int overall){
        this.overall_inicial= overall;
    }
    
    /**
     * Metodo que obtem o cansaco do Jogador
     */
    public int getCansaco(){
        return cansaco;
    }
    
    /**
     * Metodo que obtem o overall de jogo do Jogador
     */
    public int getOverall_Jogo(){
        return overall_jogo;
    }
    
    /**
     * Metodo que "setta" o overall de jogo do Jogador
     */
    public void setOverall_Jogo(int overall){
        this.overall_jogo= overall;
    }
    
    /**
     * Metodo que obtem a posicao original do Jogador
     */
    public String getPosicao_Original(){
        return posicao_original;
    }
    
    /**
     * Metodo que "setta" a posicao original do Jogador
     */
    public void setPosicao_Original(String pos){
        this.posicao_original= pos;
    }
    
    /**
     * Metodo que obtem a posicao de jogo do Jogador
     */
    public String getPosicao_Jogo(){
        return posicao_jogo;
    }
    
    /**
     * Metodo que "setta" a posicao de jogo do Jogador
     */
    public void setPosicao_Jogo(String pos){
        this.posicao_jogo= pos;
    }
    
    /**
     * Metodo que obtem a zona de jogo do Jogador
     */
    public String getRole(){
        return role;
    }
    
    /**
     * Metodo que "setta" a zona de jogo do Jogador
     */
    public void setRole(String role){
        this.role= role;
    }
    
    /**
     * Metodo que obtem o role de jogo do Jogador
     */
    public String getRoleJogo(){
        return role_jogo;
    }
    
    /**
     * Metodo que "setta" o role de jogo do Jogador
     */
    public void setRoleJogo(String role){
        role_jogo= role;
    }
    
    /**
     * Metodo que obtem o estado do Jogador, se foi substituido ou nao
     */
    public boolean getEstado(){
        return substituido;
    }
    
    /**
     * Metodo que "setta" o estado do Jogador, se foi substituido ou nao (funciona tambem para realizar substituiçoes)
     */
    public void setEstado(boolean est){
        this.substituido= est;
    }
    
    /**
     * Metodo que obtem a equipa atual do Jogador
     */
    public String getEquipa(){
        return equipa;
    }
    
    /**
     * Metodo que "setta" (atualiza) a equipa do Jogador
     */
    public void setEquipa(String eq){
        this.equipa= eq;
    }
    
    /**
     * Metodo que obetm o historial de equipas do Jogador
     */
    public List getHistorial(){
        return historial;
    }
    
    /**
     * Metodo que "setta" o historial de equipas doJogador
     */
    public void setHistorial(List historial){
        this.historial= historial;
    }
    
    /**
     * Metodo que obtem a velocidade do Jogador
     */
    public int getVelocidade() {
        return velocidade;
    }
    
    /**
     * Metodo que "setta" a velocidade do Jogador
     */
    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }
    
    /**
     * Metodo que obtem a resistencia do Jogador
     */
    public int getResistencia() {
        return resistencia;
    }
    
    /**
     * Metodo que "setta" a resistencia do Jogador
     */
    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }
    
    /**
     * Metodo que obtem a destreza do Jogador
     */
    public int getDestreza() {
        return destreza;
    }

    /**
     * Metodo que "setta" a destreza do Jogador
     */
    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }
    
    /**
     * Metodo que obtem a impulsao do Jogador
     */
    public int getImpulsao() {
        return impulsao;
    }
    
    /**
     * Metodo que "setta" a impulsao do Jogador
     */
    public void setImpulsao(int impulsao) {
        this.impulsao = impulsao;
    }

    /**
     * Metodo que obtem o jogo de cabeça do Jogador
     */
    public int getCabeca() {
        return cabeca;
    }

    /**
     * Metodo que "setta" o jogo de cabeça do Jogador
     */
    public void setCabeca(int cabeca) {
        this.cabeca = cabeca;
    }
    
    /**
     * Metodo que obtem o remate do Jogador
     */
    public int getRemate() {
        return remate;
    }

    /**
     * Metodo que "setta" o remate do Jogador
     */
    public void setRemate(int remate) {
        this.remate = remate;
    }
    
    /**
     * Metodo que obtem o passe do Jogador
     */
    public int getPasse() {
        return passe;
    }

    /**
     * Metodo que "setta" o passe do Jogador
     */
    public void setPasse(int passe) {
        this.passe = passe;
    }
    /**
     * Metodo que aumenta o cansaco do Jogador
     */
    public void aumentaCansaco(){
        cansaco+= 4;
    }
    
    /**
     * Metodo abstrato que calcula o overall do jogador apos mudar de posicao
     */
    public abstract int calculaOverallJogador(int vel, int res, int des, int imp, int cab, int rem, int p);
    
    /**
     * Metodo que abstrato que clona um Jogador (definido pelas subclasses)
     */
    protected abstract Jogador clone();
    /*
    @Override
    protected Jogador clone(){
        return new Jogador(this);
    }*/

    /*public static Jogador parse(String input){
        String[] campos = input.split(",");
        return new Jogador(campos[0], Integer.parseInt(campos[1]), campos[2],
                                        Integer.parseInt(campos[3]),
                                        Integer.parseInt(campos[4]),
                                        Integer.parseInt(campos[5]),
                                        Integer.parseInt(campos[6]),
                                        Integer.parseInt(campos[7]),
                                        Integer.parseInt(campos[8]));
    }*/
    
    /**
     * Metodo que atualiza a equipa do Jogador, bem como o seu historial
     */
    public void transferencia(String nova_equipa){
        setEquipa(nova_equipa);
        getHistorial().add(nova_equipa);
    }
    
    /**
     * Metodo que transforma uma posicao num int associado
     */
    public int definePosicao(String posicao){
        if(posicao== "Guarda Redes") return 1;
        else if(posicao== "Defesa") return 2;
        else if(posicao== "Lateral") return 3;
        else if(posicao== "Medio") return 4;
        else return 5;
    }
    
    /**
     * Metodo que define a posicao do Jogador dado um inteiro
     */
    public String convertePosicao(int pos){
        if(pos== 1) return "Guarda Redes";
        else if(pos== 2) return "Defesa";
        else if(pos== 3) return "Lateral";
        else if(pos== 4) return "Medio";
        else return "Avancado";
    }
    
    /**
     * Metodo que associa uma posicao a um role generico
     */
    public String roleGenerico(String posicao_nova){
        String papel= null;
        switch(posicao_nova){
            case "Guarda Redes":
                if(getRole()== "Baliza") papel= "Baliza";
                else papel= "";
                break;
            case "Defesa":
                if(getRole()== "Centro") papel= "Centro";
                else papel= "";
                break;
            case "Lateral":
                if(getRole()== "Lado") papel= "Lado";
                else papel= "";
                break;
            case "Medio":
                if(getRole()== "Centro") papel= "Centro";
                else if(getRole()== "Lado") papel= "Lado";
                else papel= "";
                break;
            case "Avancado":
                if(getRole()== "Centro") papel= "Centro";
                else if(getRole()=="Lado") papel= "Lado";
                else papel= "";
                break;
            }
            return papel;
    
    }
    
    /**
     * Metodo que muda a posicao do Jogador (fora do jogo)
     */
    public String mudaPosicao(String nova_posicao){
        Jogador jog;
        
        if(nova_posicao== "Guarda Redes") jog= new GuardaRedes();
        else if(nova_posicao== "Lateral") jog= new Lateral();
        else if(nova_posicao== "Defesa") jog= new Defesa();
        else if(nova_posicao== "Medio") jog= new Medio();
        else jog= new Avancado();
        setPosicao_Jogo(nova_posicao);
        int novo_overall_jogo= jog.calculaOverallJogador(velocidade, resistencia, destreza, impulsao, cabeca, remate, passe);
        setOverall_Jogo(novo_overall_jogo); 
        return ("Alteracao de posicao efetuada com sucesso!");
    }
    
    /**
     * Metodo que da reset ao cansaco
     */
    public void resetCansaco(){
        cansaco= 0;
    }
    
    /**
     * Metodo que associa a zona de campo ao papel que um Jogador (titular) desempenha em campo
     */
    public String associaRole(boolean flag){
        String papel= null, pos;
        if(flag== true) pos= getPosicao_Original();
        else pos= getPosicao_Jogo();
        switch(pos){
            case "Guarda Redes":
                papel= "";
                break;
            case "Defesa":
                if(getRoleJogo().equals("Centro")) papel= "Central";
                else papel= "";
                break;
            case "Lateral":
                if(getRoleJogo().equals("Lado")) papel= "";
                else papel= "";
                break;
            case "Medio":
                if(getRoleJogo().equals("Centro")) papel= "Centro";
                else if(getRoleJogo().equals("Lado")) papel= "Ala";
                else papel= "";
                break;
            case "Avancado":
                if(getRoleJogo().equals("Centro")) papel= "Centro";
                else if(getRoleJogo().equals("Lado")) papel= "Extremo";
                else papel= "";
                break;
            }
            return papel;
    }
    
    /**
     * Metodo toString do Jogador
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("-------------------------------------------------------------------Jogador--------------------------------------------------------------------");
        sb.append("\nNome do jogador: ").append(nomeJogador);
        sb.append("\nNumero: ").append(numeroJogador);
        sb.append("\nOverall: ").append(overall_inicial);
        sb.append("\nOverall de jogo: ").append(overall_jogo);
        sb.append("\nCansaço do Jogador: ").append(cansaco);
        sb.append("\nPosicao: ").append(getPosicao_Original()).append(" ").append(associaRole(true));
        sb.append("\nPosicao de Jogo: ").append(getPosicao_Jogo()).append(" ").append(associaRole(false));
        sb.append("\nEquipa: ").append(getEquipa());
        sb.append("\nHistorial de equipas: ");
        
        Iterator<String> historico= getHistorial().iterator();
        String team;
        int count= 1;
        while(historico.hasNext()){
            team= historico.next();
            if(count< getHistorial().size()) sb.append(team).append("-> ");
            else sb.append(team);
            count++;
        }
        
        return sb.toString();
    }

}
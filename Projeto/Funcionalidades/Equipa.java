package Funcionalidades;

import java.util.*;
import java.io.Serializable;


public class Equipa implements Serializable{

    private String nome;
    private List<Jogador> jogadores;
    private Map<Integer, Jogador> plantel;
    private List<Jogador> titulares;
    private List<Jogador> suplentes;
    private int overall_equipa;
    private int substituicoes;

    private int numero_defesas;
    private int numero_medios;
    private int numero_avancados;
    private List<Registos> historial;

    public Equipa(String nomeE, int defesas, int medios, int avancados) {
        nome=nomeE;
        jogadores = new ArrayList<>();
        plantel= new HashMap<>();
        titulares= new ArrayList<>();
        suplentes= new ArrayList<>();
        numero_defesas= defesas;
        numero_medios= medios;
        numero_avancados= avancados;
        historial= new ArrayList<>();
        substituicoes= 3;
    }

    public static Equipa parse(String input){
        String[] campos = input.split(",");
        return new Equipa(campos[0], 4, 4, 2);
    }
    
    /**
     * Metodo que obtem a lista de jogos feitos pela equipa
     */
    public List<Registos> getJogos(){
        return historial;
    }

    public void insereJogador(Jogador j) {
        jogadores.add(j);
    }
    
    public List<Jogador> getJogadores(){
        return jogadores;
    }
    
    /**
     * Metodo que obtem o nome da Equipa
     */
    public String getNome(){
        return nome;
    }
    
    /**
     * Metodo que obtem o overall da Equipa
     */
    public int getOverallEquipa(){
        return overall_equipa;
    }
    
    /**
     * Metodo que "setta" o overall da Equipa
     */
    public void setOverallEquipa(int overall){
        this.overall_equipa= overall;
    }

    /**
     * Metodo que obtem a lista dos titulares
     */
    public List<Jogador> getTitulares(){
        return titulares;
    }
    
    /**
     * Metodo que obtem a lista dos suplentes
     */
    public List<Jogador> getSuplentes(){
        return suplentes;
    }
    
    /**
     * Metodo que obtem o plantel
     */
    public Map<Integer, Jogador> getPlantel(){
        return plantel;
    }
    
    /**
     * Metodo que promove um titular a suplente
     */
    public void removeTitular(int number){
        Jogador jog= plantel.get(number);
        titulares.remove(jog);
        suplentes.add(jog);
        jog.setRole("");
        jog.setRoleJogo("");
        jogadores.clear();
        for(Jogador j: titulares) jogadores.add(j);
        for(Jogador j: suplentes) jogadores.add(j);
        atualizaFormacao();
    }
    
    /**
     * Metodo que adiciona um jogador aos titulares
     */
    public void addTitular(int titular, int suplente, int role){
        Jogador tit, sup;
        if(role!= -1){
            String papel;
            if(role== 1) papel= "Lado";
            else if(role== 2) papel= "Centro";
            else papel= "Baliza";
            sup= plantel.get(suplente);
            suplentes.remove(sup);
            titulares.add(sup);
            sup.setRole(papel);
            sup.setRoleJogo(papel);
        }
        else{
            tit= plantel.get(titular);
            sup= plantel.get(suplente);
            int exit= titulares.indexOf(tit);
            int entry= suplentes.indexOf(sup);
            suplentes.set(entry, tit);
            titulares.set(exit, sup);
            if(sup.getPosicao_Jogo().equals("Guarda Redes")){
                sup.setRole("Baliza"); sup.setRoleJogo("Baliza");
            }
            else if(sup.getPosicao_Jogo().equals("Defesa")){
                sup.setRole("Centro"); sup.setRoleJogo("Centro");
            }
            else if(sup.getPosicao_Jogo().equals("Lateral")){
                sup.setRole("Lado"); sup.setRoleJogo("Lado");
            }
            else{
                sup.setRole(tit.getRole()); sup.setRoleJogo(sup.getRole());
            }
            tit.setRole(""); tit.setRoleJogo("");
        }
        jogadores.clear();
        for(Jogador jog: titulares) jogadores.add(jog);
        for(Jogador jog: suplentes) jogadores.add(jog);
        
        atualizaFormacao();
    }
    
    /**
     * Metodo que insere os jogadores no plantel
     */
    public void constroiPlantel(){
        titulares.clear();
        suplentes.clear();
        int contador= 0;
        for(Jogador jog: jogadores){
            if(contador< 11){
                titulares.add(jog);
                jog.setPosicao_Jogo(jog.getPosicao_Original());
                jog.setRoleJogo(jog.getRole());
                contador++;
            }
            else suplentes.add(jog);
            plantel.put(jog.getNumeroJogador(), jog);
            jog.setPosicao_Jogo(jog.getPosicao_Original());
            jog.setRoleJogo(jog.getRole());
        }
        atualizaFormacao();
        setOverallEquipa(calculaOverallEquipa());
    }
    
    /**
     * Metodo que recebe um jogador novo para a Equipa
     */
    public void recebeJogador(Jogador jog){
        int max= -1, numero; boolean flag= false;
        for(Jogador jogador: jogadores){
            numero= jogador.getNumeroJogador();
            if(numero== jog.getNumeroJogador()) flag= true;
            if(numero> max) max= numero;
        }
        if(flag== true) jog.setNumeroJogador(max+ 1);
        plantel.put(jog.getNumeroJogador(), jog);
        suplentes.add(jog);
        jogadores.add(jog);
        jog.setRole("");
        jog.setRoleJogo("");
    }
    
    /**
     * Metodo que "setta" o cansaco de todos os jogadores a 0
     */
    public void setCansaco(){
        for(Jogador jog: plantel.values()) jog.resetCansaco();
    }
    
    /**
     * Metodo que devolve o numero de substituiçoes que foram feitas
     */
    public int getNumeroSubstituicoes(){
        return substituicoes;
    }
    
    /**
     * Metodo que "setta" o numero de substituiçoes no final do jogo (a 0)
     */
    public void resetSubstituicoes(){
        substituicoes= 3;
    }
    
    /**
     * Metodo que calcula o overall da equipa (apenas os titulares)
     */
    public int calculaOverallEquipa(){
        int overall= 0;
        for(Jogador jog: titulares){ 
            overall+= jog.getOverall_Jogo();
        }
        return overall/ titulares.size();
    }
    
    public int neededPlayers(String posicao, String role, int gk, int defesas, int medios, int avancados){
        if(posicao== "Guarda Redes") return gk;
        if(posicao== "Defesa" && defesas> 3) return defesas- 2;
        if(posicao== "Defesa") return defesas;
        if(posicao== "Lateral" && defesas< 4) return 0;
        if(posicao== "Lateral") return 2;
        if(posicao== "Medio" && role== "Centro"){
            if(medios> 3) return medios- 2;
            else return medios;
        }
        if(posicao== "Medio" && role== "Lado"){
            if(medios< 4) return 0;
            else return 2;
        }
        if(posicao== "Avancado" && role== "Centro"){
            if(avancados>= 3) return avancados- 2;
            else return avancados;
        }
        if(posicao== "Avancado" && role== "Lado"){
            if(avancados< 3) return 0;
            else return 2;
        }
        return -1;
    }
    
    public void adicionaJogador(List<Jogador> jogs, List<Jogador> copia, String posicao){
        for(Jogador jog: jogadores){
            if(posicao.equals(jog.getPosicao_Jogo()) && copia.contains(jog)) jogs.add(jog);
        }
    }
    
    public String givePos(int number){
        if(number== 0) return "Guarda Redes";
        if(number== 1) return "Defesa";
        if(number== 2) return "Lateral";
        if(number== 3 || number== 4) return "Medio";
        else return "Avancado";
    }
    
    public String giveRole(int number){
        if(number== 0) return "Baliza";
        if(number== 2 || number== 4 || number== 6) return "Lado";
        else return "Centro";
    }
    
    /**
     * Metodo que avalia se uma equipa e valida 
     */
    public boolean equipaValida(){
        if(titulares.size()!= 11) return false;
        for(Jogador jog: titulares) if(jog.getPosicao_Jogo().equals("Guarda Redes")) return true;
        return false;
    }
    
    /**
     * Metodo que atualiza a formacao
     */
    public void atualizaFormacao(){
        int defesas= 0, medios= 0, avancados= 0, redes= 0;
        for(Jogador jog: titulares){
            if(jog.getPosicao_Jogo().equals("Guarda Redes")) redes++;
            else if(jog.getPosicao_Jogo().equals("Lateral") || jog.getPosicao_Jogo().equals("Defesa")) defesas++;
            else if(jog.getPosicao_Jogo().equals("Medio")) medios++;
            else if(jog.getPosicao_Jogo().equals("Avancado")) avancados++;
        }
        if(redes+ defesas+ medios+ avancados== 11 && redes!= 0){
            setOverallEquipa(calculaOverallEquipa());
            numero_defesas= defesas; numero_medios= medios; numero_avancados= avancados;
        }
        else{
            setOverallEquipa(0);
            numero_defesas= numero_medios= numero_avancados= 0;
        }
    }
    
    /**
     * Metodo que altera a formacao de uma equipa
     */
    public void formacao(int gk, int defesas, int medios, int avancados){
        titulares.clear();
        suplentes.clear();
        for(Jogador jog: jogadores) jog.setRole("");
        Comparator<Jogador> c= Comparator.comparing(v-> v.getOverall_Inicial());
        jogadores.sort(c.reversed());
        List<Jogador> copia= new ArrayList<>(jogadores);
        
        for(int i= 0; i< 7; i++){
                String posicao= givePos(i);
                String role= giveRole(i);
                int redes= neededPlayers(posicao, role, gk, defesas, medios, avancados);
                List<Jogador> jogs= new ArrayList<>();
                adicionaJogador(jogs, copia, posicao);
                int j= 0;
                for(; j< redes && j< jogs.size(); j++){
                    jogs.get(j).setRole(role);
                    jogs.get(j).setRoleJogo(role);
                    titulares.add(jogs.get(j));
                    copia.remove(jogs.get(j));
                }
                for(; j< redes* 2 && j< jogs.size(); j++){
                    suplentes.add(jogs.get(j));
                    copia.remove(jogs.get(j));
                }
                
                if(redes> jogs.size()){
                    for(int k= jogs.size(); copia.size()> 0 && k< redes; k++){
                        copia.get(0).setRole(role);
                        jogs.get(j).setRoleJogo(role);
                        copia.get(0).setPosicao_Jogo(posicao);
                        titulares.add(copia.get(0));
                        copia.remove(copia.get(0));
                    }
                }
        }
        for(Jogador jog: copia) suplentes.add(jog);
        numero_defesas= defesas; numero_medios= medios; numero_avancados= avancados;
        setOverallEquipa(calculaOverallEquipa());
        jogadores.clear();
        for(Jogador jog: titulares) jogadores.add(jog);
        for(Jogador jog: suplentes) jogadores.add(jog);
    }
    
    /**
     * Metodo que transfere um jogador para outra Equipa, eliminando-o da Equipa atual
     */
    public String transfereJogador(int numero_camisola, Equipa nova_equipa){
        Jogador jog= plantel.get(numero_camisola);
        if(titulares.contains(jog)== true){
            titulares.remove(jog);
            jogadores.remove(jog);
            plantel.remove(jog.getNumeroJogador());
            atualizaFormacao();
        }
        else{
            suplentes.remove(jog);
            jogadores.remove(jog);
            plantel.remove(jog.getNumeroJogador());
        }
        jog.transferencia(nova_equipa.getNome());
        nova_equipa.recebeJogador(jog);
        return ("Transferencia feita com sucesso!");
    }
    
    /**
     * Metodo que calcula o cansaco medio da equipa
     */
    public int cansacoEquipa(){
        int cansaco= 0;
        for(Jogador jog: titulares) cansaco+= jog.getCansaco();
        return cansaco/ 11;
    }
    
    /**
     * Metodo que substitui dois jogadores da equipa (um titular e um suplente)
     */
    public void substituicao(Jogador entrada, Jogador saida){
        int exit= titulares.indexOf(saida);
        int entry= suplentes.indexOf(entrada);
        if(saida.getPosicao_Jogo().equals(entrada.getPosicao_Original())){
            suplentes.set(entry, saida);
            titulares.set(exit, entrada);
            entrada.setRoleJogo(saida.getRoleJogo());
            saida.setEstado(true);
            }
        else{
            suplentes.set(entry, saida);
            entrada.setPosicao_Jogo(saida.getPosicao_Jogo());
            entrada.setRoleJogo(saida.getRoleJogo());
            int novo_overall_jogo= (int) (entrada.getOverall_Jogo()* 0.95);
            entrada.setOverall_Jogo(novo_overall_jogo);
            saida.setEstado(true);
            titulares.set(exit, entrada);
            }
        setOverallEquipa(calculaOverallEquipa());
        substituicoes--;
    }
    
    
    /**
     * Metodo que imprime o historial de jogos da Equipa
     */
    public String imprimeHistorial(){
        StringBuilder sb= new StringBuilder();
        sb.append("-----------------------------Registo de Jogos-----------------------------\n");
        for(Registos reg: historial) sb.append(reg.painelJogo()); 
        sb.append("--------------------------------------------------------------------------\n");
        return sb.toString();
    }

    /**
     * Metodo toString da Equipa
     */
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("\nEquipa:").append(nome);
        sb.append("\nFormacao: ").append(numero_defesas).append("-").append(numero_medios).append("-").append(numero_avancados);
        sb.append("\nOverall da Equipa: ").append(overall_equipa);
        sb.append("\nValida: ").append(equipaValida());
        sb.append("\n");
        int largura= 45;
        int largura_numeros= 5;
        for(int y= 0; y< 25* 4+ 2*4; y++) sb.append("-");
        sb.append("\n");
        String numero= "Nº", nome= "Nome", posicao= "Posicao de Jogo", overall= "Overall";
        sb.append("| Nº  ||                    Nome                     ||               Posicao de Jogo               || Ove |\n"); 
        for(int y= 0; y< 25* 4+ 2*4; y++) sb.append("-");
        sb.append("\n");
        
        sb.append("|                                                Titulares                                                 |\n");    
        for(int y= 0; y< 25* 4+ 2*4; y++) sb.append("-");
        sb.append("\n");
        for(Jogador jog: titulares){ 
            numero= Integer.toString(jog.getNumeroJogador());
            nome= jog.getNomeJogador();
            posicao= jog.getPosicao_Jogo()+ " "+ jog.associaRole(false);
            overall= Integer.toString(jog.getOverall_Jogo()); //Talvez mude para overall original
            
            String temp= "";
            for(int j= 0; j< 4; j++){
                int y= 0, espacamento= largura;
                if(j== 0){
                    temp= numero;
                    espacamento= largura_numeros;
                }
                if(j== 1) temp= nome;
                if(j== 2) temp= posicao;
                if(j== 3){
                    temp= overall;
                    espacamento= largura_numeros;
                }
                sb.append("|");
                for(; y< (int) Math.floor((espacamento- temp.length())/ 2); y++) sb.append(" ");
                sb.append(temp);
                for(y= (int) y+ temp.length(); y< espacamento; y++) sb.append(" ");
                sb.append("|");
            }
            sb.append("\n");
        }
        for(int y= 0; y< 25* 4+ 2*4; y++) sb.append("-");
        sb.append("\n|                                                Suplentes                                                 |\n");    
        for(int y= 0; y< 25* 4+ 2*4; y++) sb.append("-");
        sb.append("\n");
        for(Jogador jog: suplentes){ 
            numero= Integer.toString(jog.getNumeroJogador());
            nome= jog.getNomeJogador();
            posicao= jog.getPosicao_Jogo();
            overall= Integer.toString(jog.getOverall_Jogo()); //Talvez mude para overall original
            
            String temp= "";
            for(int j= 0; j< 4; j++){
                int y= 0, espacamento= largura;
                if(j== 0){
                    temp= numero;
                    espacamento= largura_numeros;
                }
                if(j== 1) temp= nome;
                if(j== 2) temp= posicao;
                if(j== 3){
                    temp= overall;
                    espacamento= largura_numeros;
                }
                sb.append("|");
                for(; y< (int) Math.floor((espacamento- temp.length())/ 2); y++) sb.append(" ");
                sb.append(temp);
                for(y= (int) y+ temp.length(); y< espacamento; y++) sb.append(" ");
                sb.append("|");
            }
            sb.append("\n");
        }   
        for(int y= 0; y< 25* 4+ 2*4; y++) sb.append("-");
            
        return sb.toString();
    }

}


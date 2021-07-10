import Funcionalidades.*;
import java.util.*;
import java.io.*;

public class Model implements Serializable{
    private Map<String, Equipa> equipas;
    private List<Registos> jogos;
    private Jogo jogo;
    
    /**
     * Construtor de Model
     */
    public Model() throws Exception{
        Parser.parse();
        equipas= Parser.getMapEquipas();
        jogos= Parser.getListJogos();
    }

    
    public Map<String, Equipa> getMapEquipas(){
        return equipas;
    }
    
    /**
     * Metodo que adiciona um registo a lista de registos
     */
    public void addRegisto(Registos reg){
        jogos.add(reg);
    }
    
    /**
     * Metodo que lista todas as equipas disponiveis
     */
    public String allTeams(){
        StringBuilder sb= new StringBuilder();
        sb.append("---------------Equipas---------------\n");
        for(Equipa team: equipas.values()) sb.append(team.getNome()).append("\n");
        sb.append("-------------------------------------\n");
        return sb.toString();
    }
    
    /**
     * Metodo que diz se uma equipa existe
     */
    public void equipaExiste(String equipa) throws ComandoInvalidoException{
        if(equipas.containsKey(equipa)== false) throw new ComandoInvalidoException("Equipa inexistente!");
    }
    
    /**
     * Metodo que mostra a equipa pretendida
     */ 
    public String showTeam(String equipa) throws ComandoInvalidoException{
        if(equipas.containsKey(equipa)== false) throw new ComandoInvalidoException("Equipa inexistente!");
        else{
            Equipa team= equipas.get(equipa);
            String info= team.toString();
            return info;
        }
    }
    
    /**
     * Metodo que mostra o historial de jogos de uma dada equipa
     */
    public String showGames(String equipa) throws ComandoInvalidoException{
        if(equipas.containsKey(equipa)== false) throw new ComandoInvalidoException("Equipa inexistente!");
        else{
            Equipa team = equipas.get(equipa);
            String info= team.imprimeHistorial();
            return info;
        }
    }
    
    /**
     * Metodo que monstra o jogador selecionado de uma dada equipa
     */
    public String showPlayer(String equipa, int number) throws ComandoInvalidoException{
        Equipa team= equipas.get(equipa);
        if(team.getPlantel().containsKey(number)== false) throw new ComandoInvalidoException("Jogador inexistente!");
        else{
            String player= team.getPlantel().get(number).toString();
            return player;
        }
    }
    
    /**
     * Metodo que remove um titular para suplente
     */
    public String removeTitular(String equipa, int number) throws ComandoInvalidoException{
        if(equipas.get(equipa).getPlantel().containsKey(number)== false) throw new ComandoInvalidoException("Jogador inexistente!");
        else{
            Jogador jog= equipas.get(equipa).getPlantel().get(number);
            if(equipas.get(equipa).getTitulares().contains(jog)== false) throw new ComandoInvalidoException("Jogador titular inexistente!");
            else{
                equipas.get(equipa).removeTitular(number);
                return ("Jogador promovido a suplente com sucesso!");
            }
        }
    
    }
    
    /**
     * Metodo que inicia um jogo
     */
    public void setJogo(String equipaCasa, String equipaFora) throws ComandoInvalidoException{
        if(equipaCasa.equals(equipaFora)) throw new ComandoInvalidoException("Nao pode disputar um jogo entre a mesma equipa!");
        else if(equipas.containsKey(equipaCasa)== false || equipas.containsKey(equipaFora)== false) throw new ComandoInvalidoException("Uma das equipas nao existe!");
        else if(equipas.get(equipaCasa).equipaValida()== false || equipas.get(equipaFora).equipaValida()== false) throw new ComandoInvalidoException("Uma das equipas e invalida!");
        Equipa casa= equipas.get(equipaCasa);
        Equipa fora= equipas.get(equipaFora);
        jogo= new Jogo(casa, fora);
    }
    
    /**
     * Metodo que calcula os eventos em cada uma das partes do jogo (cada um dos 15 minutos)
     */
    public String play(){
        return jogo.calculaEvento();
    }
    
    /**
     * Metodo que mostra o resultado de um jogo
     */
    public String showResult(){
        return jogo.showResult();
    }
        
    /**
     * Metodo que cria um novo registo dado um jogo e adiciona-o ao historico das duas equipas
     */
    public void addRegisto(){
        Registos reg= jogo.criaRegisto();
        jogos.add(reg);
        jogo.getEquipaVisitante().getJogos().add(reg);
        jogo.getEquipaVisitada().getJogos().add(reg);
    }
    
    
    /**
     * Metodo que durante um jogo mostra a equipa da casa
     */
    public String showTeamHome(){
        return jogo.getEquipaVisitada().toString();
    }
    
    /**
     * Metodo que durante um jogo mostra a equipa de fora
     */
    public String showTeamAway(){
        return jogo.getEquipaVisitante().toString();
    }   
    
    /**
     * Metodo que devolve o nome da equipa da casa
     */
    public String equipaCasa(){
        return jogo.getEquipaVisitada().getNome();
    }
    
    /**
     * Metodo que devolve o nome da equipa da casa
     */
    public String equipaFora(){
        return jogo.getEquipaVisitante().getNome();
    }
    
    /**
     * Metodo que verifica se o numero de titulares de uma equipa e 11
     */
    public boolean trocaNecessaria(String nome){
        if(equipas.get(nome).getTitulares().size()== 11) return true;
        return false;
    }
    
    /**
     * Metodo que adiciona um jogador suplente aos titulares
     */
    public String addTitular(String equipa, int titular, int role, int suplente) throws ComandoInvalidoException{
        Jogador jog= equipas.get(equipa).getPlantel().get(suplente);
        if(equipas.get(equipa).getSuplentes().contains(jog)== false) throw new ComandoInvalidoException("Jogador suplente inexistente!");
        else if(titular!= -1 && equipas.get(equipa).getPlantel().containsKey(titular)== false) throw new ComandoInvalidoException("Jogador titular inexistente!");
        else if(titular== -1 && role!= 1 && role!= 2 && role!= 3) throw new ComandoInvalidoException("Zona de campo invalida!");
        else{
            Jogador sup= equipas.get(equipa).getPlantel().get(suplente);
            Jogador tit= equipas.get(equipa).getPlantel().get(titular);
            if(equipas.get(equipa).equipaValida() && sup.getPosicao_Jogo().equals("Guarda Redes") && tit.getPosicao_Jogo().equals("Guarda Redes")== false) throw new ComandoInvalidoException("A equipa titular ja tem um guarda redes! Alteraçao cancelada!");
            else{
                equipas.get(equipa).addTitular(titular, suplente, role);
                return ("Jogador adicionado aos titulares com sucesso!");
            }
        }
    }
    
    /**
     * Metodo que transfere um jogador de uma para outra
     */
    public String transfereJogador(String equipaAtual, String equipaNova, int number) throws ComandoInvalidoException{
        if(equipaAtual.equals(equipaNova)) throw new ComandoInvalidoException("Nao pode transferir para a mesma equipa!");
        else if(equipas.containsKey(equipaAtual)== false || equipas.containsKey(equipaNova)== false) throw new ComandoInvalidoException("Uma das equipas nao existe!");
        else{
            Equipa atual= equipas.get(equipaAtual);
            Equipa nova= equipas.get(equipaNova);
            if(atual.getPlantel().containsKey(number)== false) throw new ComandoInvalidoException("Jogador inexistente!");
            else{
                return atual.transfereJogador(number, nova);
            }
        }
    }
    
    /**
     * Metodo que faz uma susbtituiçao na equipa da casa
     */
    public String subHome(int in, int out) throws ComandoInvalidoException{
        if(jogo.getEquipaVisitada().getPlantel().get(out)== null || jogo.getEquipaVisitada().getPlantel().get(in)== null) throw new ComandoInvalidoException("Um dos jogadores nao existe na equipa!");
        else{
            Jogador saida= jogo.getEquipaVisitada().getPlantel().get(out);
            Jogador entrada= jogo.getEquipaVisitada().getPlantel().get(in);
            if(jogo.getEquipaVisitada().getSuplentes().contains(entrada)== false || jogo.getEquipaVisitada().getTitulares().contains(saida)== false) throw new ComandoInvalidoException("Um dos jogadores nao esta nos titulraes/suplentes!");
            if(entrada.getEstado()== true) throw new ComandoInvalidoException("Este jogador ja foi substituido!");
            else if(jogo.getEquipaVisitada().getNumeroSubstituicoes()== 0) throw new ComandoInvalidoException("Nao pode fazer mais substituiçoes!");
            else{
                jogo.getEquipaVisitada().substituicao(entrada, saida);
                jogo.getSubsCasa().put(out, in);
                return ("Substituiçao efetuada com sucesso!");
            }
        }
    }
    
    /**
     * Metodo que faz uma susbtituiçao na equipa da casa
     */
    public String subAway(int in, int out) throws ComandoInvalidoException{
        if(jogo.getEquipaVisitante().getPlantel().get(out)== null || jogo.getEquipaVisitante().getPlantel().get(in)== null) throw new ComandoInvalidoException("Um dos jogadores nao existe na equipa!");
        else{
            Jogador saida= jogo.getEquipaVisitante().getPlantel().get(out);
            Jogador entrada= jogo.getEquipaVisitante().getPlantel().get(in);
            if(jogo.getEquipaVisitante().getSuplentes().contains(entrada)== false || jogo.getEquipaVisitante().getTitulares().contains(saida)== false) throw new ComandoInvalidoException("Um dos jogadores nao esta nos titulraes/suplentes!");
            if(entrada.getEstado()== true) throw new ComandoInvalidoException("Este jogador ja foi substituido!");
            else if(jogo.getEquipaVisitante().getNumeroSubstituicoes()== 0) throw new ComandoInvalidoException("Nao pode fazer mais substituiçoes!");
            else{
                jogo.getEquipaVisitante().substituicao(entrada, saida);
                jogo.getSubsCasa().put(out, in);
                return ("Substituiçao efetuada com sucesso!");
            }
        }
    }
    
    /**
     * Metodo que apos um jogo "recompoem" as equipas
     */
    public void resetEquipas(){
        jogo.getEquipaVisitada().constroiPlantel();
        jogo.getEquipaVisitante().constroiPlantel();
        jogo.getEquipaVisitada().setCansaco();
        jogo.getEquipaVisitante().setCansaco();
        jogo.getEquipaVisitada().resetSubstituicoes();
        jogo.getEquipaVisitante().resetSubstituicoes();
    }
    
    /**
     * Metodo que muda a posicao de um jogador durante um Jogo
     */
    public String mudaPosicao(int number, String equipa, int posicao, boolean flag) throws ComandoInvalidoException{
        if(posicao> 5 || posicao<= 0) throw new ComandoInvalidoException("Posicao inexistente!");
        else if(equipas.get(equipa).getPlantel().containsKey(number)== false) throw new ComandoInvalidoException("Jogador inexistente!");
        else{
            Jogador jog= equipas.get(equipa).getPlantel().get(number);
            if(jog.roleGenerico(jog.getPosicao_Jogo()).equals(jog.roleGenerico(jog.convertePosicao(posicao)))== false) throw new ComandoInvalidoException("Nao pode mudar jogadores de zonas diferentes do campo!");
            if(flag){
                if(equipas.get(equipa).getTitulares().contains(jog)== false) throw new ComandoInvalidoException("Nao pode alterar a posicao de um suplente!");
                else{
                jog.mudaPosicao(jog.convertePosicao(posicao));
                equipas.get(equipa).atualizaFormacao();
                equipas.get(equipa).setOverallEquipa(equipas.get(equipa).calculaOverallEquipa());
                return ("Mudança efetuada com sucesso!");
                }
            }
            else{
                jog.mudaPosicao(jog.convertePosicao(posicao));
                equipas.get(equipa).atualizaFormacao();
                equipas.get(equipa).setOverallEquipa(equipas.get(equipa).calculaOverallEquipa());
                return ("Mudança efetuada com sucesso!");
            }
        }
    }
    
    /**
     * Metodo que verifica se um dado suplente existe
     */
    public void existeSuplente(String equipa, int suplente) throws ComandoInvalidoException{
        if(equipas.get(equipa).getPlantel().containsKey(suplente)== false) throw new ComandoInvalidoException("Jogador suplente inexistente!");
    }
    
    /**
     * Metodo que verifica se um jogador e especifico de uma zona de campo (como guarda redes, lateral e defesa)
     */
    public String verificaZona(String equipa, int suplente){
        Jogador jog= equipas.get(equipa).getPlantel().get(suplente);
        return jog.getPosicao_Original();
    }
    
    /**
     * Metodo que cria um jogador
     */
    public String criaJogador(String equipa, String nome, int posicao, int numero, int vel, int res, int destr, int imp, int cab, int rem, int pas, int especial, String role) throws ComandoInvalidoException{
        if(posicao> 5 || posicao<= 0) throw new ComandoInvalidoException("Posicao inexistente!");
        else if(vel< 0 || vel> 100 || res< 0 || res> 100 || destr< 0 || destr> 100 || imp< 0 || imp> 100 || cab< 0 || cab> 100 || rem< 0 || rem> 100 || especial< 0 || especial> 100 || numero< 0 || numero> 100)
            throw new ComandoInvalidoException("Uma das estatisticas ou numero da camisola e invalida. As estatisticas e numero da camisola tem de estar entre 0-100. Jogador nao criado!");
        else{
            String pos= convertePosicao(posicao);
            Jogador jog;
            if(posicao== 1) jog= new GuardaRedes(nome, numero, vel, res, destr, imp, cab, rem, pas, especial, equipa, "");
            else if(posicao== 2){
                jog= new Defesa(nome, numero, vel, res, destr, imp, cab, rem, pas, equipa, "");
                jog.setHabilidade_Especial(especial);
            }
            else if(posicao== 3) jog= new Lateral(nome, numero, vel, res, destr, imp, cab, rem, pas, especial, equipa, "");
            else if(posicao== 4) jog= new Medio(nome, numero, vel, res, destr, imp, cab, rem, pas, especial, equipa,  "");
            else{
                jog= new Avancado(nome, numero, vel, res, destr, imp, cab, rem, pas, equipa, "");
                jog.setHabilidade_Especial(especial);
            }
            equipas.get(equipa).recebeJogador(jog);  
            return ("Jogador criado com sucesso!");
        }
    }
    
    /**
     * Metodo que cria uma equipa
     */
    public String createTeam(String nome){
        Equipa nova= new Equipa(nome, 0, 0, 0);
        equipas.put(nome, nova);
        return ("Equipa "+ nome+ " criada com sucesso!");
    }
    
    
    /**
     * Metodo que altera a formacao da equipa
     */    
    public String formacao(String equipa, int gk, int defesas, int medios, int avancados) throws ComandoInvalidoException{
        if(defesas+ medios+ avancados!= 10) throw new ComandoInvalidoException("Formacao Invalida!");
        else try{
            equipaExiste(equipa);
            if(equipas.get(equipa).equipaValida()== false) throw new ComandoInvalidoException("Nao pode alterar a formacao de uma equipa invalida!");
            Equipa team= equipas.get(equipa);
            team.formacao(gk, defesas, medios, avancados);
            return "Formaçao alterada com sucesso!";

        }
        catch(ComandoInvalidoException e){
            throw e;
        }
    }
    
    /**
     * Metodo que transforma uma posiçao de int para String
     */
    public String convertePosicao(int pos){
        if(pos== 1) return "Guarda Redes";
        else if(pos== 2) return "Defesa";
        else if(pos== 3) return "Lateral";
        else if(pos== 4) return "Medio";
        else return "Avancado";
    }
        
        
}

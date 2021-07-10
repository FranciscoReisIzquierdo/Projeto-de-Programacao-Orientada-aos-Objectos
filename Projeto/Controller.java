import Funcionalidades.*;
import java.util.*;
import java.io.*;

public class Controller implements Serializable{
    private Model modelo;
    private View vista;
    private static String info;
    
    public Controller() throws Exception{
        modelo= new Model();
        vista= new View();
    }

    public void run() throws Exception, ComandoInvalidoException{
        showInfo(vista.help());
        info= vista.read();
        while(comandos(info)){
            info= vista.read();
        }
        showInfo("Espero que se tenha divertido! Adeus!\n");
    }
    
    /**
     * Metodo que envia qualquer mensagem a ser impressa no output para a View
     */
    public void showInfo(String informacao){
        info= informacao;
        vista.mensagem(info);
    }
    
    /**
     * Metodo que envia qualquer excecao de comando invalido a ser impressa no output para a View
     */
    public void showInfo(ComandoInvalidoException informacao){
        vista.comandoInvalido(informacao);
    }
    
    /**
     * Metodo que envia a excecao de ficheiro invalido a ser impressa no output para a View
     */
    public void showInfo(java.io.FileNotFoundException e){
        vista.comandoFileNotFound(e);
    }
    
    /**
     * Metodo que envia um registo de jogo para a lista de registos do Model
     */
    public void guardaRegisto(Registos reg){
        modelo.addRegisto(reg);
    }
    
    /**
     * Metodo que guarda o objeto (programa todo) num ficheiro de objetos 
     */
    public void guardaEstado(String path) throws FileNotFoundException, IOException{
        FileOutputStream fos= new FileOutputStream(path);
        ObjectOutputStream oos= new ObjectOutputStream(fos);
        oos.writeObject(modelo);
        oos.flush();
        oos.close();
    }
    
    /**
     * Metodo que le o objeto (programa guardado em ficheiro) e substitui o programa atual pelo do ficheiro
     */
    public void leEstado(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis= new FileInputStream(path);
        ObjectInputStream ois= new ObjectInputStream(fis);
        Model m= (Model) ois.readObject();
        modelo= m;
        ois.close();
    }
    
    /**
     * Metodo que faz o tratamento dos comandos
     */
    public boolean comandos(String info) throws java.io.IOException{
        String secondArgument;
        String[] linhaPartida;
        linhaPartida = info.split(" ", 2);
        if(linhaPartida.length== 1) secondArgument= null;
        else secondArgument= linhaPartida[1];
        if(linhaPartida[0].equals("Quit")) return false;
        else if(linhaPartida[0].equals("Clear")) clearScreen();
        else if(linhaPartida[0].equals("Help")) showInfo(vista.help());
        else if(linhaPartida[0].equals("AllTeams")) showInfo(modelo.allTeams());
        else if(linhaPartida[0].equals("ShowTeam")) comandoShowTeam(secondArgument);
        else if(linhaPartida[0].equals("ShowGames")) comandoShowGames(secondArgument);
        else if(linhaPartida[0].equals("ShowPlayer")) comandoShowPlayer(secondArgument);
        else if(linhaPartida[0].equals("Play")) game();
        else if(linhaPartida[0].equals("Transfer")) comandoTransfer(secondArgument);
        else if(linhaPartida[0].equals("ChangePos")) comandoMudaPosicao(secondArgument, false);
        else if(linhaPartida[0].equals("CreatePlayer")) comandoCreatePlayer();
        else if(linhaPartida[0].equals("RemoveEleven")) comandoRemoveEleven(secondArgument);
        else if(linhaPartida[0].equals("CreateTeam")) comandoCreateTeam(secondArgument);
        else if(linhaPartida[0].equals("Formation")) comandoFormacao(secondArgument);
        else if(linhaPartida[0].equals("AddEleven")) comandoAddTitular(secondArgument);
        else if(linhaPartida[0].equals("LoadProgram")){
            try{
                try{
                    leEstado(linhaPartida[1]);
                    showInfo("Ficheiro "+ linhaPartida[1]+ " carregado com sucesso!");
                }
                catch(java.lang.ClassNotFoundException cnfe){
                    cnfe.printStackTrace();
                }
            }
            catch(java.io.FileNotFoundException e){
                showInfo(e);
            }
        
        }
        else if(linhaPartida[0].equals("SaveProgram")){
            try{
                guardaEstado(linhaPartida[1]);
                showInfo("Ficheiro "+ linhaPartida[1]+ " guardado com sucesso!");
            }
            catch (java.io.FileNotFoundException e){
                showInfo(e);
            } 
        }
        else showInfo("Comando Invalido");
            
        return true;    
    }
    
    /**
     * Metodo que eniva ao modelo o pedido para remover um jogador dos titulares
     */
    public void comandoRemoveEleven(String linha){
        try{
            showInfo(modelo.showTeam(linha));
            showInfo("Qual titular deseja promover a suplente?");
            String tit= vista.read();
            if(isInteger(tit)== false){
                showInfo("Comando Invalido! Nenhuma alteracao efetuada!");
                return;
            }
            int titular= Integer.parseInt(tit);
            showInfo(modelo.removeTitular(linha, titular));
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Metodo que adiciona um jogador aos titulares
     */
    public void comandoAddTitular(String linha){
        try{
            showInfo(modelo.showTeam(linha));
            showInfo("Qual suplente deseja promover a titular?");
            String sup= vista.read();
            if(isInteger(sup)== false){
                showInfo("Comando Invalido! Nenhuma alteracao efetuada!");
                return;
            }
            int suplente= Integer.parseInt(sup);
            try{
                modelo.existeSuplente(linha, suplente);
                if(modelo.trocaNecessaria(linha)){
                    showInfo("Equipa titular cheia. Qual jogador titular deseja trocar?");
                    String n= vista.read();
                    if(isInteger(n)== false){
                        showInfo("Comando Invalido! Nenhuma alteracao efetuada!");
                        return;
                    }
                    int number= Integer.parseInt(n);
                    showInfo(modelo.addTitular(linha, number, -1, suplente));
                }
                else{
                    int zona= 0;
                    if(modelo.verificaZona(linha, suplente).equals("Medio") || modelo.verificaZona(linha, suplente).equals("Avancado")){
                        showInfo("Selecione a zona de campo");
                        showInfo(vista.roles());
                        zona= Integer.parseInt(vista.read());
                    }
                    else if(modelo.verificaZona(linha, suplente).equals("Lateral")) zona= 1;
                    else if(modelo.verificaZona(linha, suplente).equals("Defesa")) zona= 2;
                    else zona= 3;
                    showInfo(modelo.addTitular(linha, -1, zona, suplente));
                }
            }
            catch(ComandoInvalidoException e){
                showInfo(e);
            }
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Metodo que le um estado de programa previamente guardado num ficheiro binario
     */
    public void comandoLoadProgram(String path){
      //Nao funciona aqui nao percebo porque
    }
    
    /**
     * Metodo que guarda o estado do programa num ficheiro binario
     */
    public void comandoSaveProgram(String path){
        //Nao funciona aqui nao percebo porque
    }
    
    /**
     * Metodo que manda ao modelo o pedido para criar uma equipa
     */
    public void comandoCreateTeam(String equipa){
        showInfo(modelo.createTeam(equipa));
    }
    
    /**
     * Metodo que manda ao modelo o pedido para alterar a formaçao da equipa desejada
     */
    public void comandoFormacao(String linha){
        if(linha== null){
            showInfo("Comando Invalido!");
            return;
        }
        String[] linhaPartida; String secondArgument= null;
        linhaPartida= linha.split("=", 2);
        if(linhaPartida.length== 1){
             showInfo("Comando Invalido!");
            return;
        }
        else secondArgument= linhaPartida[1];
        try{
            String equipa= linhaPartida[0];
            String formacao []= secondArgument.split(" ", 3);
            if(isInteger(formacao[0])== false || isInteger(formacao[1])== false || isInteger(formacao[2])== false){
                showInfo("Comando Invalido!");
                return;
            }
            int defesas= Integer.parseInt(formacao[0]);
            int medios= Integer.parseInt(formacao[1]);
            int avancados= Integer.parseInt(formacao[2]);
            showInfo(modelo.formacao(equipa, 1, defesas, medios, avancados));
        }
        catch(ComandoInvalidoException e){ 
            showInfo(e);
        }
        
    }
    
    /**
     * Metodo que manda ao modelo o pedido para criar um novo jogador
     */
    public void comandoCreatePlayer(){
        showInfo("Equipa do Jogador?");
        String equipa= vista.read();
        
        try{
            modelo.equipaExiste(equipa);
            showInfo("Nome do Jogador?");
            String nome= vista.read();
            showInfo("Posicao do Jogador?");
            showInfo(vista.posicoes());
            String arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int posicao= Integer.parseInt(arg);
            showInfo("Numero da Camisola?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int numero= Integer.parseInt(arg);
            showInfo("Velocidade do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int vel= Integer.parseInt(arg);
            showInfo("Resistencia do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int res= Integer.parseInt(arg);
            showInfo("Destreza do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int destr= Integer.parseInt(arg);
            showInfo("Impulsao do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int imp= Integer.parseInt(arg);
            showInfo("Cabeceamento do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int cab= Integer.parseInt(arg);
            showInfo("Remate do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int rem= Integer.parseInt(arg);
            showInfo("Passe do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int pas= Integer.parseInt(arg);
            showInfo("Habilidade especial do Jogador?");
            arg= vista.read();
            if(isInteger(arg)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int especial= Integer.parseInt(arg);
            showInfo(modelo.criaJogador(equipa, nome, posicao, numero, vel, res, destr, imp, cab, rem, pas, especial, ""));
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Metodo que envia ao Model o pedido para transferir um jogador de uma equipa
     */
    public void comandoTransfer(String equipaAtual){
        try{
            showInfo(modelo.showTeam(equipaAtual));
            showInfo("Selecione o numero do jogador que deseja transferir");
            String n= vista.read();
            if(isInteger(n)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int number= Integer.parseInt(n);
            showInfo("Equipa de destino?");
            String equipaNova= vista.read();
            try{
                showInfo(modelo.transfereJogador(equipaAtual, equipaNova, number));
            }
            catch (ComandoInvalidoException e){
                showInfo(e);
            }
        }
        catch(ComandoInvalidoException e){ 
            showInfo(e);
        }
    }
    
    /**
     * Metodo que envia ao Model o pedido para ir buscar a equipa que se pretende mostrar
     */
    public void comandoShowTeam(String equipa){
        try{
            showInfo(modelo.showTeam(equipa));
        }
        catch(ComandoInvalidoException e){ 
            showInfo(e);
        }
    }
    
    /**
     * Metodo que envia ao Model o pedido para ir buscar o historico de jogos de uma dada equipa que se pretende mostrar
     */
    public void comandoShowGames(String equipa){
        try{
            showInfo(modelo.showGames(equipa));
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Metodo que envia ao Model o pedido para buscar o jogador que se pretende mostrar
     */
    public void comandoShowPlayer(String equipa){
        boolean flag= true;
        try{
            showInfo(modelo.showTeam(equipa));
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
            flag= false;
        }
        
        if(flag){
            showInfo("Selecione o numero do jogador que deseja ver");
            String n= vista.read();
            if(isInteger(n)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int number= Integer.parseInt(n);
            try{
                showInfo(modelo.showPlayer(equipa, number));
            }
            catch(ComandoInvalidoException e){
                showInfo(e);
            }
        }
    }
    
    /**
     * Metodo que trata do procedimento de um jogo (as varias etapas)
     */
    public void game(){
        showInfo("Equipa da Casa?");
        String equipaCasa= vista.read();
        showInfo("Equipa de Fora?");
        String equipaFora= vista.read();
        try{
            modelo.setJogo(equipaFora, equipaCasa);
        
            for(int parte= 0; parte< 6; parte++){
                if(parte== 0){
                    showInfo("---------------------------------Primeira Parte---------------------------------");
                    showInfo("-------------------------------- 0 - 15 minutos --------------------------------");
                }
                if(parte== 1) showInfo("------------------------------- 15 - 30 minutos --------------------------------");
                if(parte== 2) showInfo("------------------------------- 30 - 45 minutos --------------------------------");
                if(parte== 3){
                    showInfo("--------------------------------Segunda Parte-----------------------------------");
                    showInfo("------------------------------- 45 - 60 minutos --------------------------------");
                }
                if(parte== 4) showInfo("------------------------------- 60 - 75 minutos --------------------------------");
                if(parte== 5) showInfo("------------------------------- 75 - 90 minutos --------------------------------");
                showInfo(modelo.play());
                if(parte== 2){
                showInfo("Bem meus caros amigos, que jogo frenetico!\nChegamos ao intervalo, mas fique connosco para mais emocao!\n");
                showInfo("Intervalo: "+ modelo.showResult());
                }
                if(parte!= 5) while(comandosInGame());
                if(parte== 5){
                showInfo("E chegamos ao fim desta partida entre duas equipas muito competitivas!\nDesse lado espero que tenham gostado do encontro!");
                showInfo("Final: " + modelo.showResult());
                showInfo("--------------------------------------------------------------------------------");
                }
            }
            modelo.addRegisto();
            modelo.resetEquipas();
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Metodo que recebe os numeros dos dois jogadores da substituiçao
     */
    public void comandoSubHome(){
        showInfo(modelo.showTeamHome());
        showInfo("Jogador que entra?");
        String entrada= vista.read();
        if(isInteger(entrada)== false){
            showInfo("Comando Invalido!");
            return;
        }
        int in= Integer.parseInt(entrada);
        showInfo("Jogador que sai?");
        String saida= vista.read();
        if(isInteger(saida)== false){
            showInfo("Comando Invalido!");
            return;
        }
        int out= Integer.parseInt(saida);
        try{
            showInfo(modelo.subHome(in, out));
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Metodo que recebe os numeros dos dois jogadores da substituiçao
     */
    public void comandoSubAway(){
        showInfo(modelo.showTeamAway());
        showInfo("Jogador que entra?");
        String entrada= vista.read();
        if(isInteger(entrada)== false){
            showInfo("Comando Invalido!");
            return;
        }
        int in= Integer.parseInt(entrada);
        showInfo("Jogador que sai?");
        String saida= vista.read();
        if(isInteger(saida)== false){
            showInfo("Comando Invalido!");
            return;
        }
        int out= Integer.parseInt(saida);
        try{
            showInfo(modelo.subAway(in, out));
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Comando que permite mudar um jogador de posicao
     */
    public void comandoMudaPosicao(String equipa, boolean flag){
        try{
            showInfo(modelo.showTeam(equipa));
            showInfo("Jogador que deseja alterar a posicao?");
            String n= vista.read();
            if(isInteger(n)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int number= Integer.parseInt(n);
            showInfo("Nova posicao?");
            showInfo(vista.posicoes());
            n= vista.read();
            if(isInteger(n)== false){
                showInfo("Comando Invalido!");
                return;
            }
            int posicao= Integer.parseInt(n);
            try{
                showInfo(modelo.mudaPosicao(number, equipa, posicao, flag));
            }
            catch(ComandoInvalidoException e){
                showInfo(e);
            }
        }
        catch(ComandoInvalidoException e){
            showInfo(e);
        }
    }
    
    /**
     * Metodo que le comandos durante o decorrer de um jogo
     */
    public boolean comandosInGame(){
        String info= vista.read();
        String[] linhaPartida; String secondArgument;
        linhaPartida = info.split(" ", 2);
        if(linhaPartida.length== 1) secondArgument= null;
        else secondArgument= linhaPartida[1];
        if(linhaPartida[0].equals("ShowTeamHome")) showInfo(modelo.showTeamHome());
        else if(linhaPartida[0].equals("ShowTeamAway")) showInfo(modelo.showTeamAway());
        else if(linhaPartida[0].equals("ShowPlayerHome")) comandoShowPlayer(modelo.equipaCasa());
        else if(linhaPartida[0].equals("ShowPlayerAway")) comandoShowPlayer(modelo.equipaFora());
        else if(linhaPartida[0].equals("SubHome")) comandoSubHome();
        else if(linhaPartida[0].equals("SubAway")) comandoSubAway();
        else if(linhaPartida[0].equals("ChangePos")) comandoMudaPosicao(secondArgument, true);
        else if(linhaPartida[0].equals("Next")) return false;
        else if(linhaPartida[0].equals("Help")) showInfo(vista.help());
        else if(linhaPartida[0].equals("Clear")) clearScreen();
        else showInfo("Comando Invalido");
        return true;
    }
    
    /**
     * Metodo que limpa o terminal
     */
    public void clearScreen(){
        showInfo("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    /**
     * Metodo que verifica se o input e um int
     */
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    
    
    
}

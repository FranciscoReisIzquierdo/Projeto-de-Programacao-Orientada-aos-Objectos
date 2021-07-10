import Funcionalidades.*;
import java.io.*;
import java.util.*;

/**
 * Escreva a descrição da classe View aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class View implements Serializable{
    String info; 
    public View(){
        info= null;
    }
    
    public static void mensagem(String info){
        System.out.println(info);
    }
    
    public String read(){
        Scanner sc= new Scanner(System.in);
        return sc.nextLine();
    }
    
    public int readNumber(){
        Scanner sc= new Scanner(System.in);
        return sc.nextInt();
    }
    
    /**
     * Metodo que mostra o menu de ajuda para o utilizador
     */
    public static String help(){
        StringBuilder sb= new StringBuilder();
        sb.append("-----------------------------------------Menu de Ajuda-------------------------------------------\n");
        sb.append("Help -> Menu de ajuda\n");
        sb.append("Quit -> Sair do programa;\n");
        sb.append("Clear -> Limpa a tela do programa;\n");
        sb.append("------------------------------------Lista de comandos outGame------------------------------------\n");
        sb.append("AllTeams -> Lista todas as equipas do programa;\n");
        sb.append("ShowTeam <NomeEquipa> -> Mostra a equipa selecionada;\n");
        sb.append("ShowGames <NomeEquipa> -> Mostra o historico de jogos da equipa selecionada;\n");
        sb.append("ShowPlayer <NomeEquipa> -> Mostra o jogador selecionado da equipa respetiva;\n");
        sb.append("CreatePlayer -> Cria um jogador com as caracteristicas e inseri-o numa equipa valida;\n");
        sb.append("RemoveEleven <NomeEquipa> -> Remove um jogador dos titulares e inseri-o nos suplentes;\n");
        sb.append("CreateTeam <NomeEquipa> -> Cria uma equipa previamente sem jogadores;\n");
        sb.append("Transfer <NomeEquipaAtual> -> Transferencia de um dado jogador para outra equipa;\n");
        sb.append("ChangePos <NomeEquipa> -> Muda a posiçao de um dado jogador;\n");
        sb.append("Formation <NomeEquipa>=<Defesas Medios Avancados> -> Altera a formaçao da equipa;\n");
        sb.append("AddEleven <NomeEquipa> -> Promove um suplente a titular;\n");
        sb.append("LoadProgram <NomeFicheiro> -> Carrega um ficheiro binario (programa) previamente guardado;\n");
        sb.append("SaveProgram <NomeFicheiro> -> Guarda em ficheiro binario o programa;\n");
        sb.append("------------------------------------Lista de comandos inGame-------------------------------------\n");
        sb.append("Play -> Começa um jogo entre as duas equipas selecionadas;\n");
        sb.append("SubHome -> Substituiçao entre os jogadores selecionados da equipa da casa;\n");
        sb.append("SubAway -> Substituiçao entre os jogadores selecionados da equipa da casa;\n");
        sb.append("ShowTeamHome -> Mostra a equipa da casa;\n");
        sb.append("ShowTeamAway -> Mostra a equipa de fora;\n");
        sb.append("ShowPlayerHome -> Mostra o jogador da casa a ser previamente selecionado;\n");
        sb.append("ShowPlayerAway -> Mostra o jogador de fora a ser previamente selecionado;\n");
        sb.append("ChangePos <NomeEquipa> -> Muda a posiçao de um dado jogador (nao altera a zona de campo!);\n");
        sb.append("Next -> Prossegue com o decorrer do jogo;\n"); 
        sb.append("-------------------------------------------------------------------------------------------------\n");
        return sb.toString();
    }
    
    /**
     * Metodo que imprime uma exceçao
     */
    public void comandoInvalido(ComandoInvalidoException e){
        System.out.println(e);
    }
    
    /**
     * Metodo que imprime a exceçao de FileNotFound
     */
    public void comandoFileNotFound(java.io.FileNotFoundException e){
        System.out.println(e);
    }
    
    /**
     * Metodo que mostra as posicoes disponiveis
     */
    public String posicoes(){
        StringBuilder sb= new StringBuilder();
        sb.append("--Posicoes Disponiveis--\n");
        sb.append("1) Guarda Redes\n");
        sb.append("2) Defesa\n");
        sb.append("3) Lateral\n");
        sb.append("4) Medio\n");
        sb.append("5) Avancado\n");
        sb.append("------------------------\n");
        return sb.toString();
    }
    
    /**
     * Metodo que imprime as zonas de campo disponiveis
     */
    public String roles(){
        StringBuilder sb= new StringBuilder();
        sb.append("----Zonas Disponiveis----\n");
        sb.append("1) Lado\n");
        sb.append("2) Centro\n");
        
        return sb.toString();
    }
}

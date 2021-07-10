package Funcionalidades;

import java.io.*;

public class LinhaIncorretaException extends Exception{
    public LinhaIncorretaException(){
        super();
    }

    public LinhaIncorretaException(String s){
        super(s);
    }
}
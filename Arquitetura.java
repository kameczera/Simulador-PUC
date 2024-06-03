import java.util.*;
import CPU.*;

public class Arquitetura{
    public static void main(String[] args){
        String[] pathProcessos = {"./processo1.txt","./processo2.txt"};
        Escalar escalar = new Escalar(pathProcessos.length, pathProcessos);
        escalar.rodarCodigo();
        escalar.printarTodosRegistradores();
    }
}
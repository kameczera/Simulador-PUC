import java.util.*;
import CPU.*;

public class Arquitetura{
    public static void main(String[] args){
        String[] pathProcessos = {"./codigoTeste.txt", "./codigo.txt"};
        Escalar escalar = new Escalar(2, pathProcessos);
        escalar.rodarCodigo();
        escalar.printarTodosRegistradores();
    }
}
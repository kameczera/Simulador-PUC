package CPU;
import CPU.*;
import Nodo.*;
import Registrador.*;

public class Escalar implements CPU{
    private Nodo[] pipeline;
    private Nodo ponteiro;
    private Registrador[] registradores;

    public Escalar(){
        pipeline = new Nodo[5];
        registradores = new Registrador[32];
        for(int i = 0; i < 5; i++) {
            pipeline[i] = new Nodo();
            pipeline[i].setNodo(1,2,3,4); 
        }
        for(int i = 0; i < 32; i++){
            String n = "R" + i;
            registradores[i] = new Registrador(n);
        }
    }

    public void printarTodosNos(){
        for(int i = 0; i < 5; i++){
            pipeline[i].printValores();
        }
    }

    public void printarTodosRegistradores(){
        for(int i = 0; i < 32; i++){
            registradores[i].printRegistrador();
        }
    }

    public void rodarCodigo(){
        // for(int i = 0; i < pipeline.length; i++){
            registradores[pipeline[0].getRegistrador()].setValor(pipeline[0].rodarNodo());
        // }
    }
}
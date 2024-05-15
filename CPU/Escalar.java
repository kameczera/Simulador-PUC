package CPU;

import CPU.*;
import Nodo.*;

public class Escalar implements CPU{
    // TODOS: qual é melhor: simular uma visualização  das instruções nos estágios do pipeline
    // ou não (caso sim, utilizar uma ED para representar um pipeline. caso não, apenas ler as instruções)
    private Nodo[] pipeline;
    // TODO: qual é melhor: um array da classe registrador ou um array de ints
    // private Registrador[] registradores;
    private int[] registradores;

    public Escalar(){
        pipeline = new Nodo[5];
        registradores = new int[32];
        // for(int i = 0; i < 5; i++) {
        //     pipeline[i] = new Nodo();
        //     pipeline[i].setNodo(1,0,0,4);
        pipeline[0] = new Nodo(2,0,0,4);
        pipeline[1] = new Nodo(1,0,0,0);
        pipeline[2] = new Nodo(2,10,0,4);
        pipeline[3] = new Nodo(2,10,0,4);
        pipeline[4] = new Nodo(2,10,0,4);
        // }
        for(int i = 0; i < 32; i++){
            registradores[i] = 0;
        }
    }

    public void printarTodosNos(){
        for(int i = 0; i < 5; i++){
            pipeline[i].printValores();
        }
    }

    public void printarTodosRegistradores(){
        for(int i = 0; i < 32; i++){
            System.out.println("R"+ i + " " + registradores[i]);
        }
    }

    // TODO: passar o codigo como parametro
    public void rodarCodigo(){
        for(int i = 0; i < pipeline.length; i++){
            pipeline[i].rodarNodo(registradores);
        }
    }
}
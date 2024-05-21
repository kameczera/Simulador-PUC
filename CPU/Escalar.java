package CPU;

import CPU.*;
import Nodo.*;
import Processo.*;

public class Escalar implements CPU{
    // TODO: será que predefinimos a ordem de execução de todas instruções das thread? ou fazemos em tempo real?
    private Nodo[] pipeline;
    // TODO: qual é melhor: um array da classe registrador ou um array de ints?
    // private Registrador[] registradores;
    private int[] registradores;
    private Processo[] processos;
    private int numeroProcessos;

    public Escalar(){
        pipeline = new Nodo[5];
        registradores = new int[32];
        // for(int i = 0; i < 5; i++) {
        //     pipeline[i] = new Nodo();
        //     pipeline[i].setNodo(1,0,0,4);
        // pipeline[0] = new Nodo(2,0,0,4); // addi r0, r0, 4
        // pipeline[1] = new Nodo(1,0,0,0); // add r0, r0, r0
        // pipeline[2] = new Nodo(2,1,0,4); // addi r1, r0, 4
        // pipeline[3] = new Nodo(3,2,0,1); // and r2, r0, r1
        // pipeline[4] = new Nodo(4,3,0,2); // beq r3, 
        // }
        for(int i = 0; i < 32; i++){
            registradores[i] = 0;
        }
        processos = new Processo[1];
        processos[0] = new Processo("../codigo.txt");
        numeroProcessos = 1;
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

    public void rodarCodigo(){
        pipeline = processos[0].getInstrucoes();
        for(int i = 0; i < pipeline.length; i++){
            pipeline[i].rodarNodo(registradores);
        }
    }
}
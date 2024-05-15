package Nodo;

import Registrador.*;

public class Nodo{
    private int[] instrucoes;

    public Nodo(){
        this.instrucoes = new int[4];
    }

    public Nodo(int i, int j, int k, int l){
        this.instrucoes = new int[]{i, j, k, l};
    }

    public void setNodo(int i, int j, int k, int l){
        this.instrucoes = new int[]{i, j, k, l};
    }

    public void setNodo(int[] instrucao){
        this.instrucoes = instrucao;
    }

    public int[] getInstrucoes(){
        return instrucoes;
    }

    public void printValores(){
        for(int i = 0; i < 3; i++){
            System.out.print(instrucoes[i]);
        }
        System.out.println("");
    }

    public void rodarNodo(int[] registradores){
        switch(instrucoes[0]){
            case 1: // add
                registradores[instrucoes[1]] = registradores[instrucoes[2]] + registradores[instrucoes[3]];
                break;
                // 1,0,0,0
            case 2: // addi
                registradores[instrucoes[1]] = registradores[instrucoes[2]] + instrucoes[3];
                // 2,0,0,4
                break;
        }
    }
}
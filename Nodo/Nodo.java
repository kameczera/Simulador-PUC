package Nodo;

import Registrador.*;

public class Nodo{
    private int[] instrucao;

    public Nodo(){
        this.instrucao = new int[4];
    }

    public Nodo(int i, int j, int k, int l){
        this.instrucao = new int[]{i, j, k, l};
    }

    public void setNodo(int[] instrucao){
        this.instrucao = instrucao;
    }

    public void setNodo(int i, int registrador01,int registrador02, int registrador03)
    {
        this.instrucao = new int[]{i, registrador01, registrador02, registrador03};
    }

    public int[] getinstrucao(){
        return instrucao;
    }

    public void printValores(){
        for(int i = 0; i < 3; i++){
            System.out.print(instrucao[i]);
        }
        System.out.println("");
    }

    public void rodarNodo(int[] registradores){
        switch(instrucao[0]){
            case 1: // add
                registradores[instrucao[1]] = registradores[instrucao[2]] + registradores[instrucao[3]];
                break;
            case 2: // addi
                registradores[instrucao[1]] = registradores[instrucao[2]] + instrucao[3];
                break;
            case 3: // and
                registradores[instrucao[1]] = registradores[instrucao[2]] & registradores[instrucao[3]];
                break;
            // case 4: // beq
            //     registradores[instrucao[1]] = registradores[instrucao[2]] == registradores[instrucao[3]];
            //     break;
        }
    }
}
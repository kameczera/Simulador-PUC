package Nodo;

import Registrador.*;

//Nodo:
// Representa uma fase do pipeline. Guarda a informacao das instrucoes e o id do processo ao qual a instrucao pertence
// TODO: Fazer a leitura e execucao de todas instrucoes RISC-V no metodo rodarNodo (ultimo metodo da classe)
public class Nodo{
    private int[] instrucao;
    private int idProcesso;

    public Nodo(){
        this.instrucao = new int[4];
        idProcesso = 0;
    }

    public Nodo(int i, int j, int k, int l, int idProcesso){
        this.instrucao = new int[]{i, j, k, l};
        this.idProcesso = idProcesso;
    }

    public void setNodo(int[] instrucao){
        this.instrucao = instrucao;
    }

    public void setNodo(int i, int j, int k, int l, int idProcesso){
        this.instrucao = new int[]{i, j, k, l};
    }

    public int[] getInstrucao(){
        return instrucao;
    }

    public int getIdProcesso(){
        return idProcesso;
    }

    public void printValores(){
        for(int i = 0; i < 4; i++){
            System.out.print(instrucao[i]);
        }
        System.out.println("");
    }

    // executa a instrucao, modificando os valores dos registradores.
    // Observacao e TODO: Ao implementar instrucoes de branch( beq, jump etc..). Sera necessario passar como argumento dessa funcao os PCs dos processos/threads. (Prioridade 5)
    public void rodarNodo(int[] registradores){
        switch(instrucao[0]){
            // case 0: // bubble
            //     break;
            case 1: // add
                registradores[instrucao[1]] = registradores[instrucao[2]] + registradores[instrucao[3]];
                break;
            case 2: // addi
                registradores[instrucao[1]] = registradores[instrucao[2]] + instrucao[3];
                break;
            case 3: // and
                registradores[instrucao[1]] = registradores[instrucao[2]] & registradores[instrucao[3]];
                break;
            case 4: // lw
                // TODO
                break;
            // case 4: // beq
            //     registradores[instrucao[1]] = registradores[instrucao[2]] == registradores[instrucao[3]];
            //     break;
        }
    }
}
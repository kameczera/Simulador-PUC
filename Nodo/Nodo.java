package Nodo;

public class Nodo{
    private int[] instrucoes;

    public Nodo(){
        this.instrucoes = new int[4];
    }

    public void setNodo(int i, int j, int k, int l){
        this.instrucoes = new int[]{i, j, k, l};
    }

    public int getRegistrador(){
        return instrucoes[1];
    }

    public void printValores(){
        for(int i = 0; i < 3; i++){
            System.out.print(instrucoes[i]);
        }
        System.out.println("");
    }

    public int rodarNodo(){
        switch(instrucoes[0]){
            case 1: // addi
                return instrucoes[2] + instrucoes[3];
            case 2:
                return instrucoes[2] - instrucoes[3];
            case 3:
                return instrucoes[2] * instrucoes[3];
            default:
                return -1;
        }
    }
}
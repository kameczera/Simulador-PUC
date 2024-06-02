package Registrador;

public class Registradores{
    private int[] registradores;

    public Registradores(int nRegistradores){
        registradores = new int[nRegistradores];
        for(int i = 0; i < nRegistradores; i++) registradores[i] = 0;
    }

    public int[] getRegistradores(){
        return registradores;
    }
}
package Registrador;

public class Registrador {
    private String nome;
    private int valor;

    public Registrador(String nome){
        this.nome = nome;
        valor = 0;
    }

    public void printRegistrador(){
        System.out.println(nome + " " + valor);
    }

    public void setValor(int valor){
        this.valor = valor;
    }
}
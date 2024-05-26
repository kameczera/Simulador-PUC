package Processo;

import Nodo.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Processo {
    private Nodo[] instrucoes;
    private int instrucaoAtual;
    private boolean estado;
    
    public Processo(String nomeArquivo)
    {
        try {
            instrucoes = new Nodo[5];
            instrucaoAtual = 0;
            estado = false;
             // Inicializar cada elemento do array instrucoes. Sem inicializar ele é nulo por padrão e fica dando erro
             for (int i = 0; i < instrucoes.length; i++) {
                instrucoes[i] = new Nodo(); 
            }
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader buffer = new BufferedReader(arq);

            String linha = buffer.readLine();
            int cont = 0;
            while (linha != null) {
                String[] partes = linha.split(" ");
                String[] registradores = partes[1].split(",");
                int a, b, c;
                switch(partes[0]){
                    case "add":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(1));
                        instrucoes[cont].setNodo(1,a,b,c);
                        break;
                    case "addi":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(0));
                        instrucoes[cont].setNodo(2,a,b,c);
                        break;
                    case "and":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(1));
                        instrucoes[cont].setNodo(3,a,b,c);
                        break;
                }
                linha = buffer.readLine();
                cont++;
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public Nodo getInstrucao(){
        Nodo p = null;
        p = instrucoes[instrucaoAtual++];
        if(instrucaoAtual == instrucoes.length) estado = true;
        return p;
    }

    public boolean getEstado(){
        return estado;
    }

}
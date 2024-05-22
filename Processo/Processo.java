package Processo;

import Nodo.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Processo {
    private Nodo[] instrucoes;
    
    public Processo(String nomeArquivo)
    {
        try {
            instrucoes = new Nodo[5];

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
                switch(partes[0])
                {
                    case "add":
                        String[] registradores = partes[1].split(",");
                        System.out.println("Teste");
                        int a = Character.getNumericValue(registradores[0].charAt(1)); //Conversão funcionando
                        int b = Character.getNumericValue(registradores[1].charAt(1));
                        int c = Character.getNumericValue(registradores[2].charAt(1));
                        instrucoes[cont].setNodo(1,a,b,c);
                        System.out.println("Teste");
                }
                linha = buffer.readLine();
                cont++;
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public Nodo[] getInstrucoes(){
        return instrucoes;
    }

}
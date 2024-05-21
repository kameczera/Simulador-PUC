package Processo;

import Nodo.*;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;

public class Processo {
    private Nodo[] instrucoes;
    
    public Processo(String nomeArquivo){
        try {
            instrucoes = new Nodo[5];
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader buffer = new BufferedReader(arq);

            String linha = buffer.readLine();
            int cont = 0;
            while (linha != null) {
                String[] partes = linha.split(" ");
                switch(partes[0]){
                    case "add":
                        String[] registradores = partes[1].split(",");
                        instrucoes[cont].setNodo(1, registradores[0][1], registradores[1][1], registradores[2][1]);
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
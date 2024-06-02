package Processo;

import Nodo.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Processo {
    private List<Nodo> instrucoes;
    private int instrucaoAtual;
    private boolean estado;
    
    public Processo(String nomeArquivo, int id)
    {
        try {
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader buffer = new BufferedReader(arq);
            instrucoes = new ArrayList<Nodo>();
            instrucaoAtual = 0;
            estado = false;
            
            String linha = buffer.readLine();
            // flag -> variavel para encontrar dependencia verdadeira, atualiza no lw e no sw
            // int flag = -1;
            while (linha != null) {
                String[] partes = linha.split(" ");
                String[] registradores = partes[1].split(",");
                int a = -1;
                int b = -1;
                int c = -1;
                int idInstrucao = -1;
                Nodo n = new Nodo();
                switch(partes[0]){
                    case "add":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(1));
                        idInstrucao = 1;
                        // if(b == flag || c == flag) instrucoes.add(new Nodo(0,0,0,0,id));
                        // flag = -1;
                        break;
                        case "addi":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(0));
                        idInstrucao = 2;
                        // if(b == flag || c == flag) instrucoes.add(new Nodo(0,0,0,0,id));
                        // flag = -1;
                        break;
                        case "and":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(1));
                        idInstrucao = 3;
                        // if(b == flag || c == flag) instrucoes.add(new Nodo(0,0,0,0,id));
                        // flag = -1;
                        break;
                        case "lw":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[1].charAt(3));
                        idInstrucao = 4;
                        // if(b == flag || c == flag) instrucoes.add(new Nodo(0,0,0,0,id));
                        // flag = a;
                        break;
                }
                n = new Nodo(idInstrucao,a,b,c,id);
                instrucoes.add(n);
                linha = buffer.readLine();
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }

    public Nodo getInstrucao(){
        Nodo p = null;
        p = instrucoes.get(instrucaoAtual++);
        if(instrucaoAtual == instrucoes.size()) estado = true;
        return p;
    }

    public boolean getEstado(){
        return estado;
    }
}
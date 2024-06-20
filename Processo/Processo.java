package Processo;

import Nodo.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Processo:
// A classe Processo fica encarregada de fazer o PARSE dos arquivos. (Vai transformar as instrucoes RISC-V em um array de ints para a classe Escalar conseguir ler)
// TODO: Fazer o parse de todas instrucoes RISC-V (Prioridade 5). Obs: Esse TODO tambem esta presente na classe Nodo, uma vez que os dois estao correlacionados

public class Processo {
    private List<Nodo> instrucoes;
    private int instrucaoAtual;
    private boolean estado;

    // Processo(): Faz o parse das instruções no .txt
    public Processo(String nomeArquivo, int id) {
        try {
            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader buffer = new BufferedReader(arq);
            instrucoes = new ArrayList<Nodo>();
            instrucaoAtual = 0;
            estado = false;

            String linha = buffer.readLine();
            while (linha != null) {
                // Vai separar a linha em 2 partes, uma sendo a instrucao e outra sendo os
                // componentes.
                // Ex: addi r1,r1,r1 -> partes[] = {addi}, registradores[] = {r1,r1,r1}.
                // Nao necessariamente todas instrucoes vao utilizar 3 componentes que serao
                // registradores
                String[] partes = linha.split(" ");
                String[] registradores = new String[] {};
                registradores = partes[1].split(",");
                int a = -1;
                int b = -1;
                int c = -1;
                int idInstrucao = -1;
                Nodo n = new Nodo();
                // Como dito anteriormente, cada instrucao tem suas peculiaridade. Ter atencao
                // no switch
                switch (partes[0]) {
                    case "add":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(1));
                        idInstrucao = 1;
                        break;
                    case "addi":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(0));
                        idInstrucao = 2;
                        break;
                    case "and":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(1));
                        c = Character.getNumericValue(registradores[2].charAt(1));
                        idInstrucao = 3;
                        break;
                    case "lw":
                        a = Character.getNumericValue(registradores[0].charAt(1));
                        b = Character.getNumericValue(registradores[1].charAt(0));
                        c = Character.getNumericValue(registradores[1].charAt(3));
                        idInstrucao = 4;
                        break;
                    // DELAY e um mecanismo de simularmos as ociosidades I/Os e de rede
                    case "DELAY":
                        // a instrucao DELAY no RISC-V sera deste jeito:
                        // DELAY ("instrucao de delay") + 5 (numero de ciclos ocioso)
                        a = Character.getNumericValue(registradores[0].charAt(0));
                        b = c = idInstrucao = -1;
                        for (int i = 0; i < a - 1; i++) {
                            n = new Nodo(idInstrucao, a, b, c, id);
                            instrucoes.add(n);
                        }
                }
                n = new Nodo(idInstrucao, a, b, c, id);
                instrucoes.add(n);
                linha = buffer.readLine();
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Selecionar o arquivo %s.\n", e.getMessage());
        }
    }

    public Nodo getInstrucao() {
        Nodo p = null;
        p = instrucoes.get(instrucaoAtual++);
        if (instrucaoAtual == instrucoes.size())
            estado = true;
        return p;
    }

    public Nodo getInstrucoesSuper(Nodo[] unidades) 
    {
        int adicionados = 0;
        Nodo p = new Nodo();
        // faz no maximo 4 vezes, pq tem 4 unidades de execucao
        for (int i = 0; i < 4; i++) 
        {
            if (instrucaoAtual + i == instrucoes.size()) 
            {
                estado = true;
                break;
            }
             p = instrucoes.get(instrucaoAtual + i);
            boolean semDependencia = true;
            // verifica se tem dependencia com as adicionadas anteriormente
            for (int j = 0; j < i; j++) 
            {
                Nodo q = unidades[j];
                if(p.getIdProcesso() == q.getIdProcesso())
                {
                    if (p.getInstrucao()[2] == q.getInstrucao()[1] || p.getInstrucao()[3] == q.getInstrucao()[1])
                        semDependencia = false;
                }
            }
            if (semDependencia) 
            {
                // DELAY
                if(p.getInstrucao()[0] == -1) break;
                // addi, add, and etc..
                if (p.getInstrucao()[0] < 3) {
                    if(unidades[0] == null) unidades[0] = p;
                    else if(unidades[1] == null) unidades[1] = p;
                    else break;
                    // load: lw
                } else if (p.getInstrucao()[0] == 4) {
                    if(unidades[2] != null) break;
                    unidades[2] = p;
                    // store: sw
                } else if (p.getInstrucao()[0] == 5) {
                    if(unidades[3] != null) break;
                    unidades[3] = p;
                    // branch: beq, j etc..
                } else {
                    if(unidades[4] != null) break;
                    unidades[4] = p;
                }
                adicionados++;
            } else
                break;
        }
        instrucaoAtual += adicionados;
        return p;
    }

    // avancarInstrucao é um metodo utilizado apenas no BMT para ajudar na simulacao
    // dos DELAYS, enquanto uma thread está executando, a outra vai eliminando o
    // tempo de DELAY e de I/O ou rede.
    public void avancarInstrucao() {
        Nodo p = instrucoes.get(instrucaoAtual);
        if (p.getInstrucao()[0] == -1)
            instrucaoAtual++;
    }

    public boolean getEstado() {
        return estado;
    }
}
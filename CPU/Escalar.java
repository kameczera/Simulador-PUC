package CPU;

import CPU.*;
import Nodo.*;
import Processo.*;
import Registrador.Registradores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Classe Pipeline Escalar
public class Escalar implements CPU {
    private LinkedList<Nodo> pipeline;
    private List<Processo> processos;
    private int nProcessos;
    private int escalonador;
    private Registradores[] registradores;

    // Escalar(): Construtor do pipeline escalar que divide 12 registradores entre 1, 2 ou 3 processos
    // (12, 6 e 4 registradores para cada processo respectivamente).
    // Preenche pipeline com instruções bolha só para simular o processo de adição de instruções no pipeline
    public Escalar(int nProcessos, String[] pathProcessos) 
    {
        
        registradores = new Registradores[nProcessos];
        for (int i = 0; i < nProcessos; i++) 
        {
            registradores[i] = new Registradores(12 / nProcessos);
        }

        pipeline = new LinkedList<Nodo>();
        for(int i = 0; i < 5; i++)
        {
            pipeline.add(new Nodo(20,0,0,0,4));
        }

        this.nProcessos = nProcessos;
        processos = new ArrayList<Processo>();
        
        for (int i = 0; i < nProcessos; i++) 
        {
            Processo processo = new Processo(pathProcessos[i], i);
            processos.add(processo);
        }
    }

   public void printarTodosRegistradores() {
    for (int i = 0; i < registradores.length; i++) {
        // Verifica se todos os valores em registradores[i] são zero
        boolean todosZeros = true;
        for (int j = 0; j < registradores[i].getRegistradores().length; j++) {
            if (registradores[i].getRegistradores()[j] != 0) {
                todosZeros = false;
                break;
            }
        }
        
        // Só imprime se não forem todos zeros
        if (!todosZeros) 
        {
            System.out.println("Thread [" + i + "]");
            for (int j = 0; j < registradores[i].getRegistradores().length; j++) {
                System.out.print("$R"+ j + ":" + registradores[i].getRegistradores()[j] + " ");     
            }
            System.out.println();      
            System.out.println();       

        }
    }
}


    // rodarCodigo(): Método para simular multithreading em pipeline escalar IMT
    public void rodarCodigo() 
    {
        if(pipeline.size() == 5)
        {
        preencherPipelineIMT();
        }
        Nodo p = pipeline.poll();
        if(p.getIdProcesso() != 4)
        {
        p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
        }
    }


    // verificaBolha(): Método para identificar bolha. 
    // (1) Verifica se a instrução do nodo na posição EX do pipeline é um lw
    // (2) Verifica se o nodo na posição EX do pipeline tem o mesmo id processo do nodo MEM
    // (3) Verifica se o nodo na posição EX do pipeline usa registradores que escrevem no nodo MEM
    public void verificaBolha() 
    {
        Nodo nodoIF = pipeline.get(4);
        Nodo nodoEX = pipeline.get(3);
        if (nodoEX.getInstrucao()[0] == 4) 
        {
            if (nodoEX.getIdProcesso() == nodoIF.getIdProcesso()) 
            {
                if (nodoEX.getInstrucao()[1] == nodoIF.getInstrucao()[2] || nodoEX.getInstrucao()[1] == nodoIF.getInstrucao()[3]) 
                {
                    pipeline.add(4, new Nodo(20,0,0,0,4));
                    // Nodo p = pipeline.poll();
                    // p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
                }
            }
        }
    }

    public void preencherPipelineIMT() 
    {
        if(nProcessos > 1) 
        {
            Processo processo = processos.get(escalonador);
            Nodo n = processo.getInstrucao();
            pipeline.add(n);
            if (processo.getEstado()) 
            {
                processos.remove(processo);
                nProcessos--;
            }
            escalonador = (escalonador + 1) % nProcessos;
        }
        else if(nProcessos == 1)
        {
            Processo processo = processos.get(escalonador);
            Nodo n = processo.getInstrucao();
            // ignora se for bolha. vai para a próxima instrução
            pipeline.add(n);
            if (processo.getEstado()) 
            {
                processos.remove(processo);
                nProcessos--;
            }
            verificaBolha();
        }
        else
        {
            pipeline.add(new Nodo(20,0,0,0,4));
        }
    }

    public LinkedList<Nodo> getPipeline(){
        return pipeline;
    }
}
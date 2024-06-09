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
    private int ciclos = 0; 
    private int ciclosBolha = 0;
    private double TempoCiclo = 2.0; // Tempo de ciclo em nanosegundos
    private int instrucoesExecutadas = 0;
    public  int pararPipeLine = 0;

    //um IPC mais alto indica um processador mais eficiente em executar instruções.
    public float CalculoIPC()
    {
     return (float)this.instrucoesExecutadas/(this.ciclos);
    }

    public int CiclosBolha()
    {
     return this.ciclosBolha;
    }

    public int getCiclos()
    {
     return this.ciclos;
    }
    //A fórmula básica para calcular o tempo total de execução em um pipeline é:
    // TempoTotal = Numero de instruções + numero de bolhas + tempo de ciclo
    public double TempoTotalGasto()
    {
        return ((ciclos + ciclosBolha) * (TempoCiclo));
    }
    
    // O nosso pipeline utiliza adiantamento de dados e escrita e leitura no mesmo ciclo.
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
        ciclos++;

        if(p.getIdProcesso() != 4)
        {
        p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
        ++instrucoesExecutadas;
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
                    //bolha
                    pipeline.add(4, new Nodo(20,0,0,0,4));
                    //Porque há adiantamento de dados
                    ciclosBolha++;
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
            //Variavel de controle pra parar de colocar bolha quando o pipeline todo estiver cheio de bolha (vazio)
            this.pararPipeLine = 0;
            pipeline.add(new Nodo(0,0,0,0,4));
            //Verifica cada nodo do pipeline, se ele tiver IdProcesso = 4, isso significa que é uma bolha
            //Ele verifica para todas as instruções no pipeline,caso todas tenha IdProcesso = 4, vamos esconder o botão de proximo.
            for (Nodo nodo : pipeline) 
            {
             if(nodo.getIdProcesso() == 4)
             {
                pararPipeLine++;
             }   
            }


        }
    }

    public LinkedList<Nodo> getPipeline(){
        return pipeline;
    }
}
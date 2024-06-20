package CPU;

import Nodo.*;
import Processo.*;
import BufferReordenamento.*;
import Registrador.Registradores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Classe Pipeline SuperEscalar
// TODO: Fazer o pipeline superescalar + o buffer de reordenamento + a renomeacao de registradores
public class SuperEscalar implements CPU {
    private LinkedList<Nodo[]> pipeline;
    private List<Processo> processos;
    private int[] contagemSaida;
    private int nProcessos;
    private int escalonador;
    private Registradores[] registradores;
    private int ciclos = 0;
    private int ciclosBolha = 0;
    private double TempoCiclo = 2.0; // Tempo de ciclo em nanosegundos, ou seja, cada ciclo leva 2.0 n/s
    private int instrucoesExecutadas = 0;
    public int pararPipeLine = 0;

    // unidades especializadas: ALU, branch e LOAD/STORE
    public SuperEscalar(int nProcessos, String[] pathProcessos) {
        contagemSaida = new int[3];
        for(int i = 0; i < 3; i++) contagemSaida[i] = 0;
        registradores = new Registradores[nProcessos];
        for (int i = 0; i < nProcessos; i++) {
            registradores[i] = new Registradores(12 / nProcessos);
        }
        pipeline = new LinkedList<Nodo[]>();
        // Inicializa as unidades de execução todas como null
        for (int i = 0; i < 4; i++) {
            Nodo[] t = new Nodo[3];
            pipeline.add(t);
        }

        this.nProcessos = nProcessos;
        processos = new ArrayList<Processo>();

        for (int i = 0; i < nProcessos; i++) {
            Processo processo = new Processo(pathProcessos[i], i);
            processos.add(processo);
        }
    }

    public boolean passarParaUnidades(){
        Nodo[] OF = pipeline.get(2);
        Nodo[] unidades = pipeline.get(1);
        for (int i = 0; i < 3; i++) {
            Nodo p = OF[i];
            if(p != null)
            {
                if(p.getInstrucao()[0] == -1) break;
                
                // addi, add, and etc..
                if (p.getInstrucao()[0] < 3) {
                    if(unidades[0] != null) break;
                    unidades[0] = p;
                    OF[i] = null;
                    
                    // store & load: sw & lw
                } else if (p.getInstrucao()[0] == 4 || p.getInstrucao()[0] == 5) {
                    if(unidades[1] != null) break;
                    unidades[1] = p;
                    OF[i] = null;
                    contagemSaida[1] = 2;
                    
                    // branch: beq, j etc..
                } else {
                    if(unidades[2] != null) break;
                    unidades[2] = p;
                    OF[i] = null;
                    contagemSaida[2] = 2;
                }
            }
        }
        boolean OFVazio = true;
        for(int i = 0; i < 3; i++) if(OF[i] != null) OFVazio = false;
        if(OFVazio == false) {
            pipeline.add(2, new Nodo[4]);
        }else{
            pipeline.remove(2);
        }
        return OFVazio;
    }

    public void rodarCodigo(String comboBoxItem) {
        if(passarParaUnidades()){
            if (comboBoxItem.equals("IMT")) {
                preencherPipelineIMT();
            } else if (comboBoxItem.equals("BMT")) {
                preencherPipelineBMT();
            } else {
                preencherPipelineSMT();
            }
        }
        Nodo[] p = pipeline.poll();
        ciclos++;
        for(int i = 0; i < 3; i++) if(p[i] != null) {
            p[i].rodarNodo(registradores[p[i].getIdProcesso()].getRegistradores());

        }
        ++instrucoesExecutadas;

    }

    // if(p.getIdProcesso()!=3)

    // {
    //     p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
    //     ++instrucoesExecutadas;
    // }

    public void preencherPipelineBMT() {
    }

    public void preencherPipelineSMT() {
    }

    public void preencherPipelineIMT() 
    {
        if (nProcessos > 1)
        {
            Processo processo = processos.get(escalonador);
            pipeline.add(processo.getInstrucoesSuper());
            if (processo.getEstado()) 
            {
                processos.remove(processo);
                nProcessos--;
            }
            for(int i = 0; i < nProcessos; i++){
                Processo p = processos.get(i);
                if(processo != p) p.avancarInstrucao();
            }
            // escalonador faz o entrelacamento das threads, pois e atualizado a cada
            // chamada da funcao
            escalonador = (escalonador + 1) % nProcessos;
        } else if (nProcessos == 1) {
            Processo processo = processos.get(0);
            pipeline.add(processo.getInstrucoesSuper());
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
            // apenas se houver 1 processo que ele executa o metodo de verificacao de bolha.
            // Dado que o entrelacamento se perde entre threads se perde
        } else {
            Nodo[] vazio = new Nodo[3];
            for(int i = 0; i < 3; i++) vazio[i] = null;
            pipeline.add(vazio);
        }
    }
}
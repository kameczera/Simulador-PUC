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
    private Nodo[] unidades;
    private List<Processo> processos;
    private int nProcessos;
    private BufferReordenamento buffer;
    private int escalonador;
    private Registradores[] registradores;
    private int ciclos = 0;
    private int ciclosBolha = 0;
    private double TempoCiclo = 2.0; // Tempo de ciclo em nanosegundos
    private int instrucoesExecutadas = 0;
    public int pararPipeLine = 0;

    // unidades especializadas: ALU, branch e LOAD/STORE
    public SuperEscalar(int nProcessos, String[] pathProcessos) 
    {
        registradores = new Registradores[nProcessos];
        for (int i = 0; i < nProcessos; i++) 
        {
            registradores[i] = new Registradores(12 / nProcessos);
        }

        //Inicializa as unidades de execução todas como null
        unidades = new Nodo[4];
        for (int i = 0; i < 4; i++) {
            unidades[i] = null;
        }

        this.nProcessos = nProcessos;
        processos = new ArrayList<Processo>();

        for (int i = 0; i < nProcessos; i++) {
            Processo processo = new Processo(pathProcessos[i], i);
            processos.add(processo);
        }
    }

    public void rodarCodigo(String comboBoxItem) {

        if (comboBoxItem.equals("IMT")) {
            preencherPipelineIMT();
        } else if (comboBoxItem.equals("BMT")) {
            preencherPipelineBMT();
        } else {
            preencherPipelineSMT();
        }
        for (int i = 0; i < 4; i++) {
            Nodo p = unidades[i];
            if(p != null){
                if (p.getIdProcesso() != 4) {
                    p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
                    ++instrucoesExecutadas;
                }
                unidades[i] = null;
            }
        }
        ciclos++;
    }

    public void preencherPipelineBMT() {

    }

    public void preencherPipelineSMT() {

    }

    public void preencherPipelineIMT() {
        if (nProcessos > 1) {
            Processo processo = processos.get(escalonador);
            processo.getInstrucoesSuper(unidades);
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
            // Ignorar delays de outras threads/processos porque o processo atual esta
            // trabalhando no tempo de ociosidade delas
            for (int i = 0; i < nProcessos; i++) {
                Processo p = processos.get(i);
                if (processo != p)
                    p.avancarInstrucao();
            }
            // escalonador faz o entrelacamento das threads, pois e atualizado a cada
            // chamada da funcao
            escalonador = (escalonador + 1) % nProcessos;
        } else if (nProcessos == 1) 
        {
            Processo processo = processos.get(0);
            processo.getInstrucoesSuper(unidades);
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
            // apenas se houver 1 processo que ele executa o metodo de verificacao de bolha.
            // Dado que o entrelacamento se perde entre threads se perde
        } else {
            for(int i = 0; i < 4; i++){
                Nodo p = unidades[i];
                if (p == null) {
                    unidades[0] = p;
                }
            }
        }
    }

    public void printUnidades(){
        for(int i = 0; i < unidades.length; i++){
            unidades[0].printValores();
        }
    }
}
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

    public Escalar() {
        registradores = new Registradores[nProcessos];
        for (int i = 0; i < nProcessos; i++) {
            registradores[i] = new Registradores(12 / nProcessos);
        }
        escalonador = 0;
        pipeline = new LinkedList<Nodo>();
        processos = new ArrayList<Processo>();
        Processo processo = new Processo("./codigoTeste.txt", 0);
        processos.add(processo);
        nProcessos = 1;
    }

    // Escalar(): Construtor do pipeline escalar que divide 12 registradores entre 1, 2 ou 3 processos
    // (12, 6 e 4 registradores para cada processo respectivamente).
    // Preenche pipeline com instruções bolha só para simular o processo de adição de instruções no pipeline
    public Escalar(int nProcessos, String[] pathProcessos) {
        
        registradores = new Registradores[nProcessos];
        for (int i = 0; i < nProcessos; i++) {
            registradores[i] = new Registradores(12 / nProcessos);
        }
        pipeline = new LinkedList<Nodo>();
        for(int i = 0; i < 5; i++){
            pipeline.add(new Nodo(0,0,0,0,0));
        }
        this.nProcessos = nProcessos;
        processos = new ArrayList<Processo>();
        for (int i = 0; i < nProcessos; i++) {
            Processo processo = new Processo(pathProcessos[i], i);
            processos.add(processo);
        }
    }

    public void printarTodosRegistradores() {
        for (int i = 0; i < registradores.length; i++) {
            System.out.println("Processo " + i);
            for (int j = 0; j < 12 / registradores.length; j++) {
                System.out.println(registradores[i].getRegistradores()[j]);
            }
        }
    }

    // rodarCodigo(): Método para simular multithreading em pipeline escalar IMT
    public void rodarCodigo() {
        if (processos.size() > 1) preencherPipelineIMT();
        else preencherPipeline();
        Nodo p = pipeline.poll();
        p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
    }

    // verificaBolha(): Método para identificar bolha. 
    // (1) Verifica se a instrução do nodo na posição EX do pipeline é um lw
    // (2) Verifica se o nodo na posição EX do pipeline tem o mesmo id processo do nodo MEM
    // (3) Verifica se o nodo na posição EX do pipeline usa registradores que escrevem no nodo MEM
    public void verificaBolha() {
        if(pipeline.size() > 2){
            Nodo nodoEX = pipeline.get(2);
            Nodo nodoMEM = pipeline.get(1);
            if (nodoMEM.getInstrucao()[0] == 4) {
                if (nodoMEM.getIdProcesso() == nodoEX.getIdProcesso()) {
                    if (nodoMEM.getInstrucao()[1] == nodoEX.getInstrucao()[2] || nodoMEM.getInstrucao()[1] == nodoEX.getInstrucao()[3]) {
                        pipeline.add(2, new Nodo(0,0,0,0,0));
                        Nodo p = pipeline.poll();
                        p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
                    }
                }
            }
        }
    }

    public void preencherPipelineIMT() {
        Processo processo = processos.get(escalonador);
        Nodo n = processo.getInstrucao();
        // ignora se for bolha. vai para a próxima instrução
        pipeline.add(n);
        if (processo.getEstado()) {
            processos.remove(processo);
            nProcessos--;
        }
        escalonador = (escalonador + 1) % nProcessos;
    }

    public void preencherPipelineBMT() {
        Processo processo = processos.get(escalonador);
        Nodo n = processo.getInstrucao();
        // ignora se for bolha. vai para a próxima instrução
        pipeline.add(n);
        if (processo.getEstado()) {
            processos.remove(processo);
            nProcessos--;
        }
        escalonador = (escalonador + 1) % nProcessos;
    }

    public void preencherPipeline() {
        if (!processos.isEmpty()){
            Processo processo = processos.get(0);
            pipeline.add(processo.getInstrucao());
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
        }
        verificaBolha();
    }

    public LinkedList<Nodo> getPipeline(){
        return pipeline;
    }
}
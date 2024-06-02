package CPU;

import CPU.*;
import Nodo.*;
import Processo.*;
import Registrador.Registradores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Escalar implements CPU {
    // TODO: será que predefinimos a ordem de execução de todas instruções das
    // thread? ou fazemos em tempo real?
    private LinkedList<Nodo> pipeline;
    // TODO: qual é melhor: um array da classe registrador ou um array de ints?
    // private Registrador[] registradores;
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

    public Escalar(int nProcessos, String[] pathProcessos) {
        registradores = new Registradores[nProcessos];
        for (int i = 0; i < nProcessos; i++) {
            registradores[i] = new Registradores(12 / nProcessos);
        }
        pipeline = new LinkedList<Nodo>();
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

    public void rodarCodigo() {
        do {
            if (processos.size() > 1) preencherPipelineIMT();
            else preencherPipeline();
            Nodo p = pipeline.poll();
            p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
        } while (pipeline.size() != 0);
    }

    public void verificaBolha() {
        if(pipeline.size() > 2){
            Nodo nodoEX = pipeline.get(2);
            Nodo nodoMEM = pipeline.get(1);
            if (nodoMEM.getInstrucao()[0] == 4) {
                if (nodoMEM.getIdProcesso() == nodoEX.getIdProcesso()) {
                    if (nodoMEM.getInstrucao()[1] == nodoEX.getInstrucao()[2] || nodoMEM.getInstrucao()[1] == nodoEX.getInstrucao()[3]) {
                        Nodo p = pipeline.poll();
                        p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
                        pipeline.add(3, new Nodo(0,0,0,0,0));
                    }
                }
            }
        }
    }

    public void preencherPipelineIMT() {
        while (pipeline.size() < 5) {
            Processo processo = processos.get(escalonador);
            Nodo n = processo.getInstrucao();
            // ignora se for bolha. vai para a próxima instrução
            if (n.getInstrucao()[0] == 0)
                n = processo.getInstrucao();
            pipeline.add(n);
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
                if (processos.isEmpty())
                    break;
            }
            escalonador = (escalonador + 1) % nProcessos;
        }
    }

    public void preencherPipeline() {
        if (!processos.isEmpty()){
            while (pipeline.size() < 5) {
                Processo processo = processos.get(0);
                pipeline.add(processo.getInstrucao());
                if (processo.getEstado()) {
                    processos.remove(processo);
                    nProcessos--;
                    break;
                }
            }
        }
        verificaBolha();
    }
}
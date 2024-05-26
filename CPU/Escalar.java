package CPU;

import CPU.*;
import Nodo.*;
import Processo.*;

import java.util.ArrayList;
import java.util.List;

public class Escalar implements CPU{
    // TODO: será que predefinimos a ordem de execução de todas instruções das thread? ou fazemos em tempo real?
    private List<Nodo> pipeline;
    // TODO: qual é melhor: um array da classe registrador ou um array de ints?
    // private Registrador[] registradores;
    private int[] registradores;
    private List<Processo> processos;
    private int numeroProcessos;
    private int escalonador;

    public Escalar(){
        escalonador = 0;
        pipeline = new ArrayList<Nodo>();
        registradores = new int[32];
        for(int i = 0; i < 32; i++){
            registradores[i] = 0;
        }
        processos = new ArrayList<Processo>();
        Processo processo = new Processo("./codigoTeste.txt");
        processos.add(processo);
        numeroProcessos = 1;
    }

    public Escalar(int numeroProcessos, String[] pathProcessos){
        pipeline = new ArrayList<Nodo>();
        registradores = new int[32];
        this.numeroProcessos = numeroProcessos;
        for(int i = 0; i < 32; i++){
            registradores[i] = 0;
        }
        processos = new ArrayList<Processo>();
        for(int i = 0; i < numeroProcessos; i++) {
            Processo processo = new Processo(pathProcessos[i]);
            processos.add(processo);
        }
    }

    // public void printarTodosNos(){
    //     for(int i = 0; i < 5; i++){
    //         pipeline[i].printValores();
    //     }
    // }

    public void printarTodosRegistradores(){
        for(int j = 0; j < 32; j++) System.out.println("R"+ j + " " + registradores[j]);
    }
    
    public void rodarCodigo(){
        while(!processos.isEmpty()){
            preencherPipelineIMT();
            int tamanhoPipeline = pipeline.size();
            for(int i = 0; i < tamanhoPipeline; i++){
                Nodo p = pipeline.get(0);
                p.rodarNodo(registradores);
                pipeline.remove(p);
            }
        }
    }
    
    // java.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 5
    public void preencherPipelineIMT(){
        while(pipeline.size() < 5){
            Processo processo = processos.get(escalonador);
            pipeline.add(processo.getInstrucao());
            if(processo.getEstado()){
                processos.remove(processo);
                numeroProcessos--;
                if(processos.isEmpty()) break;
            }
            escalonador = (escalonador + 1) % numeroProcessos;
        }
    }
}
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

    public Escalar(){
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
        for(int i = 0; i < numeroProcessos; i++){
            System.out.println("Registradores do Processo " + i);
            for(int j = 1; j <= 32 / numeroProcessos; j++) System.out.println("R"+ j + " " + registradores[j]);
        }
    }


    public void rodarCodigo(){
        // TODO: loop de adição de instruções no pipeline. esse while faltante é pra loopar até todas threads já terem concluídas
        while(!processos.isEmpty()){
            preencherPipelineIMT();
            int tamanho = pipeline.size();
            for(int i = 0; i < tamanho; i++){
                Nodo p = pipeline.get(0);
                if(p != null){
                    p.rodarNodo(registradores);
                    pipeline.remove(p);
                }
            }
        }
    }
    
    public void preencherPipelineIMT(){
        int p = 0;
        while(pipeline.size() < 5){
            Processo processo = processos.get(p);
            if(!processo.getEstado()) {
                processos.remove(processo);
                numeroProcessos--;
            }
            else{
                pipeline.add(processo.getInstrucao());
                p = (p + 1) % numeroProcessos;
            }
        }
    }
}
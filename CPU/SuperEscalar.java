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

    //unidades especializadas: ALU, branch e LOAD/STORE
    public SuperEscalar(int nProcessos, String[] pathProcessos) {
        registradores = new Registradores[nProcessos];
        for (int i = 0; i < nProcessos; i++) {
            registradores[i] = new Registradores(12 / nProcessos);
        }

        unidades = new Nodo[3];
        for (int i = 0; i < 3; i++) {
            unidades[i] = new Nodo(20, 0, 0, 0, 4);
        }

        this.nProcessos = nProcessos;
        processos = new ArrayList<Processo>();

        for (int i = 0; i < nProcessos; i++) {
            Processo processo = new Processo(pathProcessos[i], i);
            processos.add(processo);
        }
    }

    public void rodarCodigo() {
        
    }

    public void preencherUnidades(){
        
    }
}
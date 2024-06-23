package CPU;

import Nodo.*;
import Processo.*;
import BufferReordenamento.*;
import Registrador.Registradores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JApplet;

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
    private double TempoCiclo = 1.0; // Tempo de ciclo em nanosegundos, ou seja, cada ciclo leva 1.0 n/s
    private int instrucoesExecutadas = 0;
    public int pararPipeLine = 0;

    // unidades especializadas: ALU, branch e LOAD/STORE
    public SuperEscalar(int nProcessos, String[] pathProcessos) {
        contagemSaida = new int[3];
        for (int i = 0; i < 3; i++)
            contagemSaida[i] = 0;
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

    public boolean passarParaUnidades() {
        Nodo[] OF = pipeline.get(2);
        boolean OFVazio = true;
        int contador = 0;
        Nodo[] unidades = new Nodo[3];
        pipeline.add(2, unidades);
        for (int i = 0; i < 3; i++) {
            Nodo p = OF[i];
            if (p != null) {
                boolean semDependencia = true;
                for (int j = 0; j < 3; j++) {
                    Nodo q = unidades[j];
                    if (q != null) {
                        if (p.getIdProcesso() == q.getIdProcesso()) {
                            if (p.getInstrucao()[2] == q.getInstrucao()[1]
                                    || p.getInstrucao()[3] == q.getInstrucao()[1])
                                semDependencia = false;
                        }
                    }
                }
                if (semDependencia) {
                    if (p.getInstrucao()[0] == -1){
                        OF[i] = null;
                        break;
                    }

                    // addi, add, and etc..
                    if (p.getInstrucao()[0] < 3) {
                        if (unidades[0] != null)
                            break;
                        unidades[0] = p;
                        OF[i] = null;

                        // store & load: sw & lw
                    } else if (p.getInstrucao()[0] == 4 || p.getInstrucao()[0] == 5) {
                        if (unidades[1] != null)
                            break;
                        unidades[1] = p;
                        OF[i] = null;

                        // branch: beq, j etc..
                    } else {
                        if (unidades[2] != null)
                            break;
                        unidades[2] = p;
                        OF[i] = null;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++)
            if (OF[i] != null)
                contador++;
        if (contador != 0)
            OFVazio = false;
        if(OFVazio) pipeline.remove(3);
        return OFVazio;
    }

    public boolean passarParaOF() {
        boolean IFVazio = true;
        if(nProcessos > 1){
            Nodo[] IF = pipeline.get(3);
            Nodo[] OF = pipeline.get(2);
            int contador = 0;
            int instrucaoIF = 0;
            for(int j = 0; j < 3; j++){
                if(OF[j] == null){
                    OF[j] = IF[instrucaoIF];
                    IF[instrucaoIF] = null;
                    instrucaoIF++;
                }
            }
            for (int i = 0; i < 3; i++)
                if (IF[i] != null)
                    contador++;
            if (contador != 0)
                IFVazio = false;
                Nodo[] vazio = new Nodo[3];
                for (int i = 0; i < 3; i++)
                    vazio[i] = null;
            pipeline.add(2, vazio);    
        }
        return IFVazio;
    }

    public void rodarCodigo(String comboBoxItem) {
        if (passarParaUnidades()) {
            if (comboBoxItem.equals("IMT")) {
                preencherPipelineIMT();
            } else if (comboBoxItem.equals("BMT")) {
                preencherPipelineBMT();
            } else{
                if(comboBoxItem.equals("SMT")){
                    passarParaOF();
                    preencherPipelineSMT();
                }
            }
        }
        Nodo[] p = pipeline.poll();
        ciclos++;
        for (int i = 0; i < 3; i++)
        if (p[i] != null) {
            p[i].rodarNodo(registradores[p[i].getIdProcesso()].getRegistradores());
            
        }
        ++instrucoesExecutadas;

    }

    // if(p.getIdProcesso()!=3)

    // {
    // p.rodarNodo(registradores[p.getIdProcesso()].getRegistradores());
    // ++instrucoesExecutadas;
    // }

    public void preencherPipelineBMT() {
        if (nProcessos > 1) {
            Processo processo = processos.get(escalonador);
            pipeline.add(processo.getInstrucoesSuper());
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
            for (int i = 0; i < nProcessos; i++) {
                Processo p = processos.get(i);
                if (processo != p)
                    p.avancarInstrucao();
            }
            // escalonador faz o entrelacamento das threads, pois e atualizado a cada
            // chamada da funcao
            Nodo[] f = pipeline.getLast();
            for(int i = 0; i < 3; i++) {
                if(f[i].getInstrucao()[0] == -1) {
                    escalonador = (escalonador + 1) % nProcessos;
                    break;
                }
            }
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
            for (int i = 0; i < 3; i++)
                vazio[i] = null;
            pipeline.add(vazio);
        }
    }

    

    public void preencherPipelineSMT() {
        if (nProcessos > 1) {
            Processo processo = processos.get(escalonador);
            processo.getInstrucoesSuper(pipeline.getLast());
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
            for (int i = 0; i < nProcessos; i++) {
                Processo p = processos.get(i);
                if (processo != p)
                    p.avancarInstrucao();
            }
            // escalonador faz o entrelacamento das threads, pois e atualizado a cada
            // chamada da funcao
            escalonador = (escalonador + 1) % nProcessos;
        } else if (nProcessos == 1) {
            Processo processo = processos.get(0);
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
            Nodo[] vazio = new Nodo[3];
            for (int i = 0; i < 3; i++)
            vazio[i] = null;
            pipeline.add(vazio);
            processo.getInstrucoesSuper(pipeline.getLast());
            // apenas se houver 1 processo que ele executa o metodo de verificacao de bolha.
            // Dado que o entrelacamento se perde entre threads se perde
        } else {
            Nodo[] vazio = new Nodo[3];
            for (int i = 0; i < 3; i++)
                vazio[i] = null;
            pipeline.add(vazio);
        }
    }

    public void preencherPipelineIMT() {
        if (nProcessos > 1) {
            Processo processo = processos.get(escalonador);
            pipeline.add(processo.getInstrucoesSuper());
            if (processo.getEstado()) {
                processos.remove(processo);
                nProcessos--;
            }
            for (int i = 0; i < nProcessos; i++) {
                Processo p = processos.get(i);
                if (processo != p)
                    p.avancarInstrucao();
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
            for (int i = 0; i < 3; i++)
                vazio[i] = null;
            pipeline.add(vazio);
        }
    }


    //Pegando os ID's dos processos
    //Se não houver instrução (nulo), idProcesso = 4 (rosa) o que indica vazio
    public int[] getIdProcessosSuperEscalar(Nodo[] instrucoes)
    {
     int countInstrucoes = 0;
     int[] idProcessos = new int[3];

     for (Nodo nodo : instrucoes) 
     {
      if(nodo == null)
      {
        idProcessos[countInstrucoes] = 4;
        countInstrucoes++;
      }
      else
      {
      idProcessos[countInstrucoes] = nodo.getIdProcesso();
      countInstrucoes++;
      }  
     }
     return idProcessos;
    }

    //Verificando se na unidade atual todos os processos são da mesma Thread
    //Nao esta sendo utilizado por enquanto, porem pode ter seu uso no futuro quem sabe
    public boolean TodosProcessosIguais(int[] Processos)
    {
    boolean processosIguais = true;
    int verificarProcesso = Processos[0];

    for(int i = 0 ; i < 3; i++)
    {
     if(Processos[i] != verificarProcesso)
     {
     processosIguais = false;
     break;
     }
    }
     return processosIguais;
    }

   public boolean verificarTodasInstrucoesVazias(int[] idProcessos)
   {
   boolean todasInstrucoesVazias = true;

   for(int i = 0; i < 3; i++)
   {
    if(idProcessos[i] != 4)
    {
     todasInstrucoesVazias = false;
     break;
    }
   }
   return todasInstrucoesVazias;

   }

    public String getOperacaoInstrucao(int valor)
    {
      if(valor == 20)
      return "";
      else if(valor == -1)
      return "DELAY";
      else if(valor == 0)
      return "";
      else if(valor == 1)
      return "add";
      else if(valor == 2)
      return "addi";
      else if(valor == 3)
      return "and";
      else if(valor == 4)
      return "lw";
      return "Operador nao encontrado";
    }

    public LinkedList<Nodo[]> getPipeLineSuperEscalar() {
        return pipeline;
    }

}
package BufferReordenamento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class Instrucoes {
    String operacao;
    String registrador;
    String valor1;
    String valor2;

    Instrucoes(String operacao, String registrador, String valor1, String valor2) {
        this.operacao = operacao;
        this.registrador = registrador;
        this.valor1 = valor1;
        this.valor2 = valor2;
    }

    // Formata a instrução como uma string para imprimir
    @Override
    public String toString() {
        return operacao + " " + registrador + "," + valor1 + (valor2 != null ? "," + valor2 : "");
    }
}

public class ExecucaoForaDeOrdem {
    public static void main(String[] args) {
        String inputFileName = "processoTeste4.txt";
        String outputFileName = "instrucoes_reordenadas.txt";
        List<String> instrucoes = new ArrayList<>();

        // Ler instruções do arquivo
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                instrucoes.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parsear e reordenar instruções
        // Chama parseinstrucoes para converter as strings lidas em objetos Instrucoes.
        List<Instrucoes> parsedinstrucoes = parseinstrucoes(instrucoes);
        // Chama reordenarInstrucoes para reordenar as instruções respeitando as
        // dependências.
        List<Instrucoes> reordenarInstrucoes = reordenarInstrucoes(parsedinstrucoes);

        // Escrever instruções reordenadas em um novo arquivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) 
        {
            for (Instrucoes Instrucoes : reordenarInstrucoes) {
                bw.write(Instrucoes.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Converte uma lista de strings em uma lista de objetos Instrucoes.
    private static List<Instrucoes> parseinstrucoes(List<String> instrucoes) {
        List<Instrucoes> parsedinstrucoes = new ArrayList<>();
        for (String Instrucoes : instrucoes) {
            String[] parts = Instrucoes.split("[, ]+");
            parsedinstrucoes.add(new Instrucoes(parts[0], parts[1], parts[2], parts.length > 3 ? parts[3] : null));
        }
        return parsedinstrucoes;
    }

    private static List<Instrucoes> reordenarInstrucoes(List<Instrucoes> instrucoes) {

    List<Instrucoes> reordenarInstrucoes = new ArrayList<>();

        reordenarInstrucoes.add(instrucoes.get(0));//Instrução inicial não tem dependencia de dados
        for (int i = 1; i < instrucoes.size(); i++) {

            //Se a instrução atual tem dependencia de dados com a anterior
            if (instrucoes.get(i).valor1.equals(instrucoes.get(i - 1).registrador) || instrucoes.get(i).valor2.equals(instrucoes.get(i - 1).registrador))
            {
            reordenarInstrucoes.add(instrucoes.get(i+1));
            reordenarInstrucoes.add(instrucoes.get(i));
            i++;
            }
            else
            {
            reordenarInstrucoes.add(instrucoes.get(i));
            }
        }
        return reordenarInstrucoes;
    }


}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Interface {
    public static void main(String[] args) {
        // Criar uma janela
        JFrame janela = new JFrame("Simulador Suporte a Multithreading");
        QuadradoComDado quadrado = new QuadradoComDado("6");

        // Definir tamanho da janela
        janela.setSize(600, 400);

        // Criar um painel para o cabeçalho
        JPanel painelHeader = new JPanel();
        painelHeader.setBounds(0, 0, 600, 50); // Posição (0,0) com largura de 400 e altura de 50
        painelHeader.setBackground(Color.LIGHT_GRAY); // Cor de fundo do cabeçalho

        // Criar um botão no conteúdo da janela
        JButton botao = new JButton("Selecionar Arquivo");
        botao.setBounds(50, 100, 100, 50);

        // Adicionar os botões ao painel do cabeçalho
        painelHeader.add(botao);

        // Criar um campo de texto
        JTextField IF = new JTextField();
        IF.setBounds(80, 100, 80, 50);
        IF.setEditable(false); // Definir como não editável
        IF.setBackground(Color.WHITE);

        // Criar um campo de texto
        JTextField ID = new JTextField();
        ID.setBounds(170, 100, 80, 50);
        ID.setEditable(false); // Definir como não editável
        ID.setBackground(Color.WHITE);

        // Criar um campo de texto
        JTextField EX = new JTextField();
        EX.setBounds(260, 100, 80, 50);
        EX.setEditable(false); // Definir como não editável
        EX.setBackground(Color.WHITE);

        // Criar um campo de texto
        JTextField MEM = new JTextField();
        MEM.setBounds(350, 100, 80, 50);
        MEM.setEditable(false); // Definir como não editável
        MEM.setBackground(Color.WHITE);

        // Criar um campo de texto
        JTextField WB = new JTextField();
        WB.setBounds(440, 100, 80, 50);
        WB.setEditable(false); // Definir como não editável
        WB.setBackground(Color.WHITE);

        // Adicionar o campo de texto à janela
        janela.add(ID);
        janela.add(IF);
        janela.add(EX);
        janela.add(MEM);
        janela.add(WB);

        // Adicionar o painel do cabeçalho à janela
        janela.add(painelHeader);

        // Definir layout para o restante da janela
        JPanel conteudo = new JPanel();
        conteudo.setLayout(null); // Usando layout nulo para posicionar manualmente os componentes

        // Adicionar um ouvinte de evento ao botão
        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Criar um seletor de arquivo
                JFileChooser seletorArquivo = new JFileChooser();

                // Exibir o seletor de arquivo e aguardar a seleção do usuário
                int resultado = seletorArquivo.showOpenDialog(null);

                // Verificar se o usuário selecionou um arquivo
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    // Obter o arquivo selecionado
                    File arquivoSelecionado = seletorArquivo.getSelectedFile();

                    // Exibir o caminho do arquivo selecionado no console (você pode fazer o que
                    // quiser com o arquivo aqui)
                    System.out.println("Arquivo selecionado: " + arquivoSelecionado.getAbsolutePath());
                }
            }
        });

        // Definir layout do conteúdo
        conteudo.setBounds(0, 50, 400, 250); // Posição (0,50) com largura de 400 e altura de 250

        // Adicionar o QuadradoComDado à janela
        janela.add(quadrado);

        // Adicionar o conteúdo à janela
        janela.add(conteudo);

        // Definir operação padrão de fechamento
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Exibir a janela
        janela.setVisible(true);
    }
}

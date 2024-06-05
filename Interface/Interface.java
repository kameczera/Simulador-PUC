import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Interface {
    public static void main(String[] args) {
        // Criar uma janela
        JFrame janela = new JFrame("Simulador Suporte a Multithreading");
        
        // Definir tamanho da janela
        janela.setSize(600, 400);
        janela.setLayout(new BorderLayout()); // Usar BorderLayout na janela principal

        // Criar um painel para o cabeçalho
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.LIGHT_GRAY); // Cor de fundo do cabeçalho
        painelHeader.setPreferredSize(new Dimension(600, 50));
        
        // Criar um botão no painel do cabeçalho
        JButton botao = new JButton("Selecionar Arquivo");
        painelHeader.add(botao); // Adicionar o botão ao painel do cabeçalho

        // Criar um painel de conteúdo
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new GridBagLayout()); // Usar GridBagLayout no painel de conteúdo
        GridBagConstraints gbcConteudo = new GridBagConstraints();
        gbcConteudo.gridx = 0;
        gbcConteudo.gridy = 0;
        gbcConteudo.anchor = GridBagConstraints.CENTER;
        gbcConteudo.fill = GridBagConstraints.NONE;

        // Criar painel centralizado
        JPanel painelCentralizado = new JPanel();
        GroupLayout layout = new GroupLayout(painelCentralizado);
        painelCentralizado.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Criar rótulos e campos de texto
        JLabel LabelIPC = new JLabel("IPC: 2.5");
        LabelIPC.setFont(new Font("Arial", Font.BOLD, 14)); // Definir a fonte e o tamanho do texto

        JLabel LabelCiclos = new JLabel("Ciclos: 100");
        LabelCiclos.setFont(new Font("Arial", Font.BOLD, 14)); // Definir a fonte e o tamanho do texto

        JLabel LabelCiclosBolha = new JLabel("Ciclos Bolhas: 7");
        LabelCiclosBolha.setFont(new Font("Arial", Font.BOLD, 14)); // Definir a fonte e o tamanho do texto

        JLabel LabelTempoGasto = new JLabel("Tempo Gasto: 20 ns");
        LabelTempoGasto.setFont(new Font("Arial", Font.BOLD, 14)); // Definir a fonte e o tamanho do texto

        JLabel LabelIF = new JLabel("IF");
        LabelIF.setFont(new Font("Arial", Font.BOLD, 16)); // Definir a fonte e o tamanho do texto

        JLabel LabelID = new JLabel("ID");
        LabelID.setFont(new Font("Arial", Font.BOLD, 16)); // Definir a fonte e o tamanho do texto

        JLabel LabelEX = new JLabel("EX");
        LabelEX.setFont(new Font("Arial", Font.BOLD, 16)); // Definir a fonte e o tamanho do texto

        JLabel LabelMEM = new JLabel("MEM");
        LabelMEM.setFont(new Font("Arial", Font.BOLD, 16)); // Definir a fonte e o tamanho do texto

        JLabel LabelWB = new JLabel("WB");
        LabelWB.setFont(new Font("Arial", Font.BOLD, 16)); // Definir a fonte e o tamanho do texto

        JTextField IF = new JTextField();
        IF.setEditable(false); // Definir como não editável
        IF.setBackground(Color.WHITE);

        JTextField ID = new JTextField();
        ID.setEditable(false); // Definir como não editável
        ID.setBackground(Color.WHITE);

        JTextField EX = new JTextField();
        EX.setEditable(false); // Definir como não editável
        EX.setBackground(Color.WHITE);

        JTextField MEM = new JTextField();
        MEM.setEditable(false); // Definir como não editável
        MEM.setBackground(Color.WHITE);

        JTextField WB = new JTextField();
        WB.setEditable(false); // Definir como não editável
        WB.setBackground(Color.WHITE);

        // Configurar o GroupLayout para organizar os componentes
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(LabelIF)
                    .addComponent(IF, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(LabelID)
                    .addComponent(ID, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(LabelEX)
                    .addComponent(EX, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(LabelMEM)
                    .addComponent(MEM, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(LabelWB)
                    .addComponent(WB, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(LabelIF)
                    .addComponent(LabelID)
                    .addComponent(LabelEX)
                    .addComponent(LabelMEM)
                    .addComponent(LabelWB))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(IF, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(ID, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(EX, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(MEM, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                    .addComponent(WB, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
        );

        // Adicionar painel centralizado ao painel de conteúdo
        conteudo.add(painelCentralizado, gbcConteudo);

        // Criar outro painel de conteúdo para o IPC e Ciclos
        JPanel painelDeDados = new JPanel();
        painelDeDados.setLayout(new FlowLayout(FlowLayout.LEFT)); // Usar FlowLayout para organizar os rótulos
        painelDeDados.add(LabelIPC);
        painelDeDados.add(Box.createHorizontalStrut(20)); // Espaçamento de 10 pixels
        painelDeDados.add(LabelCiclos);
        painelDeDados.add(Box.createHorizontalStrut(20)); // Espaçamento de 10 pixels
        painelDeDados.add(LabelCiclosBolha);
        painelDeDados.add(Box.createHorizontalStrut(20)); // Espaçamento de 10 pixels
        painelDeDados.add(LabelTempoGasto);

        // Adicionar o painel IPC ao painel de conteúdo
        GridBagConstraints gbcIPC = new GridBagConstraints();
        gbcIPC.gridx = 0;
        gbcIPC.gridy = 1;
        gbcIPC.anchor = GridBagConstraints.WEST;
        gbcIPC.insets = new Insets(60, 0, 0, 0); // Adicionar preenchimento superior para mover o IPC para baixo
        conteudo.add(painelDeDados, gbcIPC);

        // Adicionar painel do cabeçalho e painel de conteúdo à janela principal
        janela.add(painelHeader, BorderLayout.NORTH);
        janela.add(conteudo, BorderLayout.CENTER);

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

                    // Exibir o caminho do arquivo selecionado no console (você pode fazer o que quiser com o arquivo aqui)
                    System.out.println("Arquivo selecionado: " + arquivoSelecionado.getAbsolutePath());
                }
            }
        });

        // Definir operação padrão de fechamento
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Exibir a janela
        janela.setVisible(true);
    }
}

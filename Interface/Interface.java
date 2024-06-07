import javax.swing.*;
import CPU.Escalar;
import Nodo.Nodo;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ListIterator;

public class Interface {
    private QuadradoComDado IF;
    private QuadradoComDado ID;
    private QuadradoComDado BUBBLE;
    private QuadradoComDado EX;
    private QuadradoComDado MEM;
    private QuadradoComDado WB;
    private Color[] corProcessos = { Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.PINK };
    private Escalar escalar;

    public Interface() {
        String[] pathProcessos = {  "./processo1.txt","./processo2.txt"};
        escalar = new Escalar(pathProcessos.length, pathProcessos);
        IF = new QuadradoComDado("ASD", Color.WHITE);
        ID = new QuadradoComDado("ASD", Color.WHITE);
        BUBBLE = new QuadradoComDado("ASD", Color.WHITE);
        EX = new QuadradoComDado("ASD", Color.WHITE);
        MEM = new QuadradoComDado("ASD", Color.WHITE);
        WB = new QuadradoComDado("ASD", Color.WHITE);
    }

    public void criarInterface() {
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
        JButton botaoArquivo = new JButton("Selecionar Arquivo");
        painelHeader.add(botaoArquivo);
        JButton botaoProximo = new JButton("Proximo");
        painelHeader.add(botaoProximo);

        // Criar um painel de conteúdo
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new GridBagLayout()); // Usar GridBagLayout no painel de conteúdo

        // Criar rótulos e campos de texto
        JLabel LabelIPC = new JLabel("IPC: 2.5");
        LabelIPC.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel LabelCiclos = new JLabel("Ciclos: 100");
        LabelCiclos.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel LabelCiclosBolha = new JLabel("Ciclos Bolhas: 7");
        LabelCiclosBolha.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel LabelTempoGasto = new JLabel("Tempo Gasto: 20 ns");
        LabelTempoGasto.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel LabelIF = new JLabel("IF");
        LabelIF.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel LabelID = new JLabel("ID");
        LabelID.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel LabelBUBBLE = new JLabel("BUBBLE");
        LabelBUBBLE.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel LabelEX = new JLabel("EX");
        LabelEX.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel LabelMEM = new JLabel("MEM");
        LabelMEM.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel LabelWB = new JLabel("WB");
        LabelWB.setFont(new Font("Arial", Font.BOLD, 16));
        
        JPanel panel = new JPanel();
        GroupLayout pipeline = new GroupLayout(panel);
        panel.setLayout(pipeline);
        pipeline.setAutoCreateGaps(true);
        pipeline.setAutoCreateContainerGaps(true);

        pipeline.setHorizontalGroup(
                pipeline.createSequentialGroup()
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelIF)
                                .addComponent(IF.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelID)
                                .addComponent(ID.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelBUBBLE)
                                .addComponent(BUBBLE.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelEX)
                                .addComponent(EX.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelMEM)
                                .addComponent(MEM.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelWB)
                                .addComponent(WB.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE)));

        pipeline.setVerticalGroup(
                pipeline.createSequentialGroup()
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelIF)
                                .addComponent(LabelID)
                                .addComponent(LabelBUBBLE)
                                .addComponent(LabelEX)
                                .addComponent(LabelMEM)
                                .addComponent(LabelWB))
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(IF.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ID.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(BUBBLE.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(EX.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(MEM.getPanel(), GroupLayout.PREFERRED_SIZE, 50,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(WB.getPanel(), GroupLayout.PREFERRED_SIZE, 50,
                                        GroupLayout.PREFERRED_SIZE)));

        // Adicionar painel centralizado ao painel de conteúdo
        conteudo.add(panel);

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
        botaoArquivo.addActionListener(new ActionListener() {
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

        botaoProximo.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                escalar.rodarCodigo();
                ListIterator<Nodo> list_Iter = escalar.getPipeline().listIterator();
                WB.mudarCor(corProcessos[list_Iter.next().getIdProcesso()]);
                MEM.mudarCor(corProcessos[list_Iter.next().getIdProcesso()]);
                EX.mudarCor(corProcessos[list_Iter.next().getIdProcesso()]);
                if(escalar.getPipeline().size() == 6) 
                {
                        BUBBLE.mudarCor(corProcessos[list_Iter.next().getIdProcesso()]);
                        BUBBLE.invisivel(true);
                        LabelBUBBLE.setVisible(true);
                }
                else
                {
                        BUBBLE.invisivel(false);
                        LabelBUBBLE.setVisible(false);
                }
                ID.mudarCor(corProcessos[list_Iter.next().getIdProcesso()]);
                IF.mudarCor(corProcessos[list_Iter.next().getIdProcesso()]);
                if(escalar.getPipeline().size() == 0) botaoProximo.setEnabled(false);
                escalar.printarTodosRegistradores();
            }
        });

        // Definir operação padrão de fechamento
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Exibir a janela
        janela.setVisible(true);
    }

//     public void simular() 
//     {
//         String[] pathProcessos = { "./processo1.txt", "./processo2.txt" };
//         Escalar escalar = new Escalar(pathProcessos.length, pathProcessos);
//         escalar.rodarCodigo();
//     }
}

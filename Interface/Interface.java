import javax.swing.*;
import CPU.Escalar;
import Nodo.Nodo;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ListIterator;

// Interface:
// Classe da interface grafica do nosso simulador.
// TODO: pathProcessos deve ser dinamico (Prioridade 1)

public class Interface {
    private QuadradoComDado IF;
    private QuadradoComDado ID;
    private QuadradoComDado BUBBLE;
    private QuadradoComDado EX;
    private QuadradoComDado MEM;
    private QuadradoComDado WB;
    private Color[] corProcessos = { Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.PINK };
    String[] suportesMultiThreading = { "IMT", "BMT"};
    private Escalar escalar;
    String[] pathProcessos = { "./processo1.txt", "./processo3.txt"};

    public Interface() 
    {
        
        escalar = new Escalar(pathProcessos.length, pathProcessos);
        IF = new QuadradoComDado("Vazio", Color.WHITE);
        ID = new QuadradoComDado("Vazio", Color.WHITE);
        BUBBLE = new QuadradoComDado("Vazio", Color.WHITE);
        EX = new QuadradoComDado("Vazio", Color.WHITE);
        MEM = new QuadradoComDado("Vazio", Color.WHITE);
        WB = new QuadradoComDado("Vazio", Color.WHITE);
    }


    public void criarInterface() 
    {
        // Criar uma janela
        JFrame janela = new JFrame("Simulador Suporte a Multithreading");

        // Definir tamanho da janela
        janela.setSize(600, 400);
        janela.setLayout(new BorderLayout()); // Usar BorderLayout na janela principal

        // Criação do painel de cabeçalho
        JPanel painelHeader = new JPanel(new BorderLayout());
        painelHeader.setBackground(Color.LIGHT_GRAY); // Cor de fundo do cabeçalho
        painelHeader.setPreferredSize(new Dimension(600, 50));
        //Criação do ComboBox
        JComboBox<String> comboBoxSuportesMultiThreading = new JComboBox<>(suportesMultiThreading);
        Dimension comboBoxSize = new Dimension(60,25);
        // Criando um ListCellRenderer personalizado para centralizar o texto
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        //Tamanho do ComboBox
        comboBoxSuportesMultiThreading.setPreferredSize(comboBoxSize);
        comboBoxSuportesMultiThreading.setRenderer(listRenderer);
        // Painel para manter o JComboBox alinhado à esquerda
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false); // Para manter o fundo transparente
        leftPanel.add(comboBoxSuportesMultiThreading);

        // Adicionar o painel alinhado à esquerda no painel de cabeçalho
        painelHeader.add(leftPanel, BorderLayout.EAST);

        // Criação dos botões
        JButton botaoArquivo = new JButton("Selecionar Arquivo");
        JButton botaoProximo = new JButton("Próximo");

        // Painel para manter os botões alinhados à direita
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false); // Para manter o fundo transparente
        centerPanel.add(botaoArquivo);
        centerPanel.add(botaoProximo);

        // Adicionar o painel alinhado à direita no painel de cabeçalho
        painelHeader.add(centerPanel, BorderLayout.CENTER);
        // Adicionar o painel de cabeçalho na janela principal
        janela.add(painelHeader, BorderLayout.NORTH);

        // Criar um painel de conteúdo
        JPanel conteudo = new JPanel();
        conteudo.setLayout(new GridBagLayout()); // Usar GridBagLayout no painel de conteúdo

        // Criar rótulos e campos de texto
        JLabel LabelIPC = new JLabel("IPC: ");
        LabelIPC.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel LabelCiclos = new JLabel("Ciclos: ");
        LabelCiclos.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel LabelCiclosBolha = new JLabel("Ciclos Bolhas: ");
        LabelCiclosBolha.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel LabelTempoGasto = new JLabel("Tempo Gasto: ");
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
                    String processoSelecionado = arquivoSelecionado.getAbsolutePath();
                    pathProcessos[0] = processoSelecionado;
                }
            }
        });


        botaoProximo.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               //Desativa o botão de Proximo se o pipeline tiver todo vazio
               //Isso significa que todas as instruções ja foram executadas
                if(escalar.pararPipeLine == 4)
                {
                botaoProximo.setEnabled(false);
                }
 
                //Se a opção selecionada no ComboBox for IMT
                if(comboBoxSuportesMultiThreading.getSelectedItem().toString() == suportesMultiThreading[0])
                {
                escalar.rodarCodigo(comboBoxSuportesMultiThreading.getSelectedItem().toString());
                }
                else if(comboBoxSuportesMultiThreading.getSelectedItem().toString() == suportesMultiThreading[1])
                {
                escalar.rodarCodigo(comboBoxSuportesMultiThreading.getSelectedItem().toString());
                }
                int idProcesso = 0;
                //escalar.rodarCodigo();
                ListIterator<Nodo> list_Iter = escalar.getPipeline().listIterator();
               
                idProcesso = list_Iter.next().getIdProcesso();
                WB.mudarCor(corProcessos[(idProcesso)]);
                WB.mudarLabel("T" + idProcesso);

                idProcesso = list_Iter.next().getIdProcesso();
                MEM.mudarCor(corProcessos[(idProcesso)]);
                MEM.mudarLabel("T" + idProcesso);

                idProcesso = list_Iter.next().getIdProcesso();
                EX.mudarCor(corProcessos[(idProcesso)]);
                EX.mudarLabel("T" + idProcesso);

                if(escalar.getPipeline().size() == 6) 
                {
                        idProcesso = list_Iter.next().getIdProcesso();
                        BUBBLE.mudarLabel("T" + idProcesso);
                        BUBBLE.mudarCor(corProcessos[(idProcesso)]);
                        BUBBLE.invisivel(true);
                        LabelBUBBLE.setVisible(true);
                }
                else
                {
                        BUBBLE.invisivel(false);
                        LabelBUBBLE.setVisible(false);
                }
                idProcesso = list_Iter.next().getIdProcesso();
                ID.mudarCor(corProcessos[(idProcesso)]);
                ID.mudarLabel("T" + idProcesso);
                
                idProcesso = list_Iter.next().getIdProcesso();
                IF.mudarCor(corProcessos[(idProcesso)]);
                IF.mudarLabel("T" + idProcesso);
                if(escalar.getPipeline().size() == 0) botaoProximo.setEnabled(false);
                escalar.printarTodosRegistradores();

                LabelIPC.setText("IPC: " + ((escalar.CalculoIPC())));
                LabelCiclos.setText("Ciclos: " + (escalar.getCiclos()));
                LabelCiclosBolha.setText("Ciclos Bolhas: " + escalar.CiclosBolha());
                LabelTempoGasto.setText("Tempo Gasto: " + escalar.TempoTotalGasto() + " ns");
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

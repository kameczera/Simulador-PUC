import javax.swing.*;
import CPU.Escalar;
import CPU.SuperEscalar;
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
    private QuadradoComDado ALU;
    private QuadradoComDado BRANCH;
    private QuadradoComDado LOAD;
    private QuadradoComDado STORE;
    private QuadradoComDado MEM;
    private QuadradoComDado WB;
    private Color[] corProcessos = { Color.GREEN, new Color(0x60, 0x7E, 0xC9), Color.ORANGE, Color.CYAN, Color.PINK };
    String[] suportesMultiThreading = {"","",""};
    String[] TipoDePipeLine = { "","Escalar", "Superescalar"};
    private Escalar escalar;
    private SuperEscalar superEscalar;
    String[] pathProcessos = { "./processo1.txt", "./processo3.txt"};
    private JTextArea selectedFilesArea;
    private String[] selectedFilesPaths;

    public Interface() 
    {      
        escalar = new Escalar(pathProcessos.length, pathProcessos);
        superEscalar = new SuperEscalar(pathProcessos.length, pathProcessos);
        IF = new QuadradoComDado("", "", Color.PINK);
        ID = new QuadradoComDado("", "",  Color.PINK);
        BUBBLE = new QuadradoComDado("", "",  Color.PINK);
        EX = new QuadradoComDado("", "",  Color.PINK);
        MEM = new QuadradoComDado("", "",  Color.PINK);
        WB = new QuadradoComDado("", "",  Color.PINK);
        ALU = new QuadradoComDado("", "", Color.PINK);
        BRANCH = new QuadradoComDado("", "", Color.PINK);
        LOAD = new QuadradoComDado("", "", Color.PINK);
        STORE = new QuadradoComDado("", "", Color.PINK);
    }

    

    public void criarInterface() 
    {
        // Criar uma janela
        JFrame janela = new JFrame("Simulador Suporte a Multithreading");

        // Definir tamanho da janela
        janela.setSize(900, 500);

        // Obter as dimensões da tela
        Dimension dimensaoTela = Toolkit.getDefaultToolkit().getScreenSize();

       // Calcular as coordenadas X e Y para centralizar a janela
       int x = (dimensaoTela.width - janela.getWidth()) / 2;
       int y = (dimensaoTela.height - janela.getHeight()) / 2;

       // Definir a posição da janela
       janela.setLocation(x, y);
       janela.setLayout(new BorderLayout()); // Usar BorderLayout na janela principal
        

        // Criação do painel de cabeçalho
        JPanel painelHeader = new JPanel(new BorderLayout());
        painelHeader.setBackground(Color.LIGHT_GRAY); // Cor de fundo do cabeçalho
        painelHeader.setPreferredSize(new Dimension(600, 50));
        //Criação do ComboBox
        JComboBox<String> comboBoxSuportesMultiThreading = new JComboBox<>(suportesMultiThreading);
        comboBoxSuportesMultiThreading.setEnabled(false);
        JComboBox<String> comboBoxTipoDeEscalaridade = new JComboBox<>(TipoDePipeLine);
        Dimension comboBoxSize = new Dimension(60,25);
        // Criando um ListCellRenderer personalizado para centralizar o texto
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        //Tamanho do ComboBox
        comboBoxSuportesMultiThreading.setPreferredSize(comboBoxSize);
        comboBoxSuportesMultiThreading.setRenderer(listRenderer);
        comboBoxTipoDeEscalaridade.setRenderer(listRenderer);
        // Painel para manter o JComboBox alinhado à esquerda
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false); // Para manter o fundo transparente
        leftPanel.add(comboBoxTipoDeEscalaridade);
        leftPanel.add(comboBoxSuportesMultiThreading);

        // Adicionar o painel alinhado à esquerda no painel de cabeçalho
        painelHeader.add(leftPanel, BorderLayout.EAST);

        // Criação dos botões
        JButton botaoArquivo = new JButton("Selecionar Arquivo");
        JButton botaoProximo = new JButton("Próximo");
        botaoProximo.setEnabled(false);

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

        JLabel LabelCPI = new JLabel("CPI: ");
        LabelCPI.setFont(new Font("Arial", Font.BOLD, 14));

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
        LabelBUBBLE.setVisible(false);
        BUBBLE.invisivel(false);
        LabelBUBBLE.setFont(new Font("Arial", Font.BOLD, 16));

         
        JLabel LabelALU = new JLabel("ALU");
        LabelALU.setVisible(false);
        ALU.invisivel(false);
        LabelALU.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel LabelBranch = new JLabel("BRANCH");
        LabelBranch.setVisible(false);
        BRANCH.invisivel(false);
        LabelBranch.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel LabelStore = new JLabel("STORE");
        LabelStore.setVisible(false);
        STORE.invisivel(false);
        LabelStore.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel LabelLoad = new JLabel("LOAD");
        LabelLoad.setVisible(false);
        LOAD.invisivel(false);
        LabelLoad.setFont(new Font("Arial", Font.BOLD, 16));

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
                                .addComponent(LabelALU)
                                .addComponent(ALU.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))

                                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelBranch)
                                .addComponent(BRANCH.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))

                                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelStore)
                                .addComponent(STORE.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE))

                                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(LabelLoad)
                                .addComponent(LOAD.getPanel(), GroupLayout.PREFERRED_SIZE, 80,
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
                                .addComponent(LabelALU)
                                .addComponent(LabelBranch)
                                .addComponent(LabelStore)
                                .addComponent(LabelLoad)
                                .addComponent(LabelMEM)
                                .addComponent(LabelWB))
                        .addGroup(pipeline.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(IF.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ID.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(BUBBLE.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(EX.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ALU.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(BRANCH.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(LOAD.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(STORE.getPanel(), GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addComponent(MEM.getPanel(), GroupLayout.PREFERRED_SIZE, 50,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(WB.getPanel(), GroupLayout.PREFERRED_SIZE, 50,
                                        GroupLayout.PREFERRED_SIZE)));

        // Adicionar painel centralizado ao painel de conteúdo
        conteudo.add(panel);

        // Criar outro painel de conteúdo para o IPC e Ciclos
        JPanel painelDeDados = new JPanel();
        painelDeDados.setLayout(new FlowLayout(FlowLayout.LEFT)); // Usar FlowLayout para organizar os rótulos
        painelDeDados.add(LabelCiclos);
        painelDeDados.add(Box.createHorizontalStrut(20)); // Espaçamento de 10 pixels
        painelDeDados.add(LabelCiclosBolha);
        painelDeDados.add(Box.createHorizontalStrut(20)); // Espaçamento de 10 pixels
        painelDeDados.add(LabelTempoGasto);
        painelDeDados.add(Box.createHorizontalStrut(20));

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

        selectedFilesArea = new JTextArea();
        selectedFilesArea.setEditable(false);

        // Adicionar um ouvinte de evento ao botão
        botaoArquivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Criar um seletor de arquivo
                JFileChooser seletorArquivo = new JFileChooser();

                seletorArquivo.setMultiSelectionEnabled(true);
                seletorArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
                String userDir = System.getProperty("user.dir");
                // Combina o diretório de trabalho com o nome da pasta
                String targetDir = userDir + File.separator;

                // Especifica o diretório inicial
                seletorArquivo.setCurrentDirectory(new File(targetDir));



                    int returnValue = seletorArquivo.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) 
                {
                    File[] selectedFiles = seletorArquivo.getSelectedFiles();
                    selectedFilesPaths = new String[selectedFiles.length];
                    selectedFilesArea.setText("");
                    for (int i = 0; i < selectedFiles.length; i++) 
                    {
                        selectedFilesPaths[i] = selectedFiles[i].getAbsolutePath();
                        pathProcessos[i] = selectedFilesPaths[i];
                    }
                    escalar = new Escalar(pathProcessos.length, pathProcessos);
                    superEscalar = new SuperEscalar(pathProcessos.length, pathProcessos);

                }
            }
        });


        
        comboBoxTipoDeEscalaridade.addActionListener(new ActionListener() 
        {
                @Override
                public void actionPerformed(ActionEvent e) 
                {

                    // Obtém o item selecionado
                    String selectedItem = (String) comboBoxTipoDeEscalaridade.getSelectedItem();
                    // Exibe o item selecionado em um JOptionPane
                    //JOptionPane.showMessageDialog(janela, "Arquitetura do tipo: " + selectedItem);

                   
                   if(selectedItem.equals("Escalar"))
                   {                  
                   comboBoxSuportesMultiThreading.removeAllItems();
                   comboBoxSuportesMultiThreading.addItem("IMT");
                   comboBoxSuportesMultiThreading.addItem("BMT");
                   comboBoxSuportesMultiThreading.setEnabled(true);
                   EX.invisivel(true);
                   LabelEX.setVisible(true);
                   ALU.invisivel(false);
                   LabelALU.setVisible(false);
                   BRANCH.invisivel(false);
                   LabelBranch.setVisible(false);
                   LOAD.invisivel(false);
                   LabelLoad.setVisible(false);
                   STORE.invisivel(false);
                   LabelStore.setVisible(false);
                   painelDeDados.add(LabelCPI);
                   painelDeDados.remove(LabelIPC);
                   painelDeDados.revalidate();
                   painelDeDados.repaint();
                   }
                   else if(selectedItem.equals("Superescalar"))
                   {
                        comboBoxSuportesMultiThreading.removeAllItems();
                        comboBoxSuportesMultiThreading.addItem("IMT");
                        comboBoxSuportesMultiThreading.addItem("BMT");
                        comboBoxSuportesMultiThreading.addItem("SMT");
                        comboBoxSuportesMultiThreading.setEnabled(true);
                        EX.invisivel(false);
                        LabelEX.setVisible(false);
                        ALU.invisivel(true);
                        LabelALU.setVisible(true);
                        BRANCH.invisivel(true);
                        LabelBranch.setVisible(true);
                        LOAD.invisivel(true);
                        LabelLoad.setVisible(true);
                        STORE.invisivel(true);
                        LabelStore.setVisible(true);
                        painelDeDados.add(LabelIPC);
                        painelDeDados.remove(LabelCPI);
                        painelDeDados.revalidate();
                        painelDeDados.repaint();
                   }
                    
                }
            });

            comboBoxSuportesMultiThreading.addActionListener(new ActionListener() 
            {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
    
                        // Obtém o item selecionado
                        String selectedItem = (String) comboBoxSuportesMultiThreading.getSelectedItem();
                      
                       if(selectedItem != null || selectedItem != "")
                       {
                        botaoProximo.setEnabled(true);
                       }
                       
                        
                    }
                });

        botaoProximo.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               //Desativa o botão de Proximo se o pipeline tiver todo 
               //Isso significa que todas as instruções ja foram executadas
               System.out.println(escalar.getPipeline().size());
               System.out.println(escalar.pararPipeLine);
               comboBoxTipoDeEscalaridade.setEnabled(false);
                //Se a opção selecionada no ComboBox for IMT

                if(escalar.pararPipeLine == escalar.getPipeline().size() && escalar.CiclosBolha() > 0)
                {
                botaoProximo.setEnabled(false);             
                }
                
                //Se a arquitetura for escalar
                if(comboBoxTipoDeEscalaridade.getSelectedItem().toString().equals("Escalar"))
                {
                if(comboBoxSuportesMultiThreading.getSelectedItem().toString().equals("IMT"))
                {
                escalar.rodarCodigo(comboBoxSuportesMultiThreading.getSelectedItem().toString());
                }
                else if(comboBoxSuportesMultiThreading.getSelectedItem().toString().equals("BMT"))
                {
                escalar.rodarCodigo(comboBoxSuportesMultiThreading.getSelectedItem().toString());
                }
                }
                //Se a arquitetura for Superescalar
                else if(comboBoxTipoDeEscalaridade.getSelectedItem().toString().equals("Superescalar"))
                {
                if(comboBoxSuportesMultiThreading.getSelectedItem().toString().equals("IMT"))
                {
                superEscalar.rodarCodigo(comboBoxSuportesMultiThreading.getSelectedItem().toString());
                }
                else if(comboBoxSuportesMultiThreading.getSelectedItem().toString().equals("BMT"))
                {
                superEscalar.rodarCodigo(comboBoxSuportesMultiThreading.getSelectedItem().toString());
                }
                else
                {
                 superEscalar.rodarCodigo(comboBoxSuportesMultiThreading.getSelectedItem().toString());
                }
                  
                }


                comboBoxSuportesMultiThreading.setEnabled(false);

                if(escalar.pararPipeLine == escalar.getPipeline().size() && escalar.CiclosBolha() == 0)
                {
                botaoProximo.setEnabled(false);
                }
                
                Nodo instrucaoAtual;
                int [] arrayInstrucao;
                int idProcesso = 0;

                //escalar.rodarCodigo();
                ListIterator<Nodo> list_Iter = escalar.getPipeline().listIterator();             
                idProcesso = list_Iter.next().getIdProcesso();
                instrucaoAtual = list_Iter.previous();
                arrayInstrucao = instrucaoAtual.getInstrucao();
                WB.mudarLabelInstrucao(arrayInstrucao);
                list_Iter.next();
                WB.mudarCor(corProcessos[(idProcesso)]);
                WB.mudarLabel("T" + idProcesso);

                idProcesso = list_Iter.next().getIdProcesso();
                instrucaoAtual = list_Iter.previous();
                arrayInstrucao = instrucaoAtual.getInstrucao();
                MEM.mudarLabelInstrucao(arrayInstrucao);
                list_Iter.next();
                MEM.mudarCor(corProcessos[(idProcesso)]);
                MEM.mudarLabel("T" + idProcesso);
                

                idProcesso = list_Iter.next().getIdProcesso();
                instrucaoAtual = list_Iter.previous();
                arrayInstrucao = instrucaoAtual.getInstrucao();
                EX.mudarLabelInstrucao(arrayInstrucao);
                list_Iter.next();
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
                instrucaoAtual = list_Iter.previous();
                arrayInstrucao = instrucaoAtual.getInstrucao();
                ID.mudarLabelInstrucao(arrayInstrucao);
                list_Iter.next();
                ID.mudarCor(corProcessos[(idProcesso)]);
                ID.mudarLabel("T" + idProcesso);
                
                idProcesso = list_Iter.next().getIdProcesso();
                instrucaoAtual = list_Iter.previous();
                arrayInstrucao = instrucaoAtual.getInstrucao();
                IF.mudarLabelInstrucao(arrayInstrucao);
                list_Iter.next();
                IF.mudarCor(corProcessos[(idProcesso)]);        
                IF.mudarLabel("T" + idProcesso);
                if(escalar.getPipeline().size() == 0) botaoProximo.setEnabled(false);
                escalar.printarTodosRegistradores();

                LabelCPI.setText("CPI: " + ((escalar.CalculoCPI())));
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

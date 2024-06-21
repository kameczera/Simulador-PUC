import javax.swing.*;
import java.awt.*;
import Nodo.*;

public class QuadradoComDado extends JPanel {
    private JPanel panel;
    private JLabel label;
    private JLabel labelInstrucao;
    private JLabel labelInstrucao2;
    private JLabel labelInstrucao3;

    public QuadradoComDado(String label, String labelInstrucao, Color cor) 
    {
        this.label = new JLabel(label);
        this.labelInstrucao = new JLabel(labelInstrucao);
        this.panel = new JPanel();
        
        // Configura o layout do painel para BoxLayout na orientação Y_AXIS (vertical)
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.panel.setBackground(cor);

          // Centraliza os labels no eixo X
          this.label.setAlignmentX(Component.CENTER_ALIGNMENT);
          this.labelInstrucao.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adiciona os labels ao painel
        panel.add(this.label);
        panel.add(this.labelInstrucao);
    }

    public void mudarLabel(String label) {
        if(label.equals("T4")) 
        {
            label = "";
        }
        this.label.setText(label);
        this.labelInstrucao.setText(this.labelInstrucao.getText());
    }

    public void mudarLabelInstrucao(int [] instrucaoAtual) 
    {
        String operando = getOperando(instrucaoAtual[0]);
        if(operando.equals("DELAY"))
        {
        this.labelInstrucao.setText("DELAY");
        }
        else
        {
        this.labelInstrucao.setText((operando +" r" + instrucaoAtual[1] + " r" + instrucaoAtual[2]+ " " + instrucaoAtual[3]).toString());
        }
    }

    public void mudarLabelInstrucaoSuperEscalar(Nodo[] instrucao)
    {

     //String operando = getOperando(instrucao[0]);
     this.labelInstrucao.setText(instrucao[0].toString());
     this.labelInstrucao2.setText(instrucao[1].toString());
     this.labelInstrucao3.setText(instrucao[2].toString());
    }

    



    public JPanel getPanel() {
        return panel;
    }

    public JLabel getLabel() {
        return label;
    }

    public void mudarCor(Color cor) {
        panel.setBackground(cor);
    }

    public void invisivel(boolean flag) {
        panel.setVisible(flag);
        label.setVisible(flag);
    }

    private String getOperando(int valor)
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

    

}

import javax.swing.*;
import java.awt.*;

public class QuadradoComDado extends JPanel {
    private JPanel panel;
    private JLabel label;
    private JLabel labelInstrucao;

    public QuadradoComDado(String label, String labelInstrucao, Color cor) {
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
        this.labelInstrucao.setText("Teste");
    }

    public void mudarLabelInstrucao(String labelInstrucao) {
        this.labelInstrucao.setText(labelInstrucao);
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
    

}

import javax.swing.*;
import java.awt.*;

public class QuadradoComDado extends JPanel {
    private JPanel panel;
    private JLabel label;

    public QuadradoComDado(String label, Color cor) {
        this.label = new JLabel();
        this.panel = new JPanel();
        this.label.setText(label);
        this.panel.setBackground(cor);
        panel.add(this.label);
    }

    public void mudarLabel(String label){
        this.label.setText(label);
    }

    public JPanel getPanel(){
        return panel;
    }

    public JLabel getLabel(){
        return label;
    }

    public void mudarCor(Color cor){
        panel.setBackground(cor);
    }

    public void invisivel(boolean flag){
        panel.setVisible(flag);
        label.setVisible(flag);
    }
}

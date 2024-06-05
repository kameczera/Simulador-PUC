import javax.swing.*;
import java.awt.*;

public class QuadradoComDado extends JPanel {
    private String dado;

    public QuadradoComDado(String dado) {
        this.dado = dado;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Definir a cor do quadrado
        g.setColor(Color.BLUE);
        
        // Desenhar o quadrado
        g.fillRect(50, 50, 100, 100); // Quadrado com posição (50,50) e tamanho 100x100
        
        // Definir a cor do texto
        g.setColor(Color.WHITE);
        
        // Definir a fonte do texto
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Desenhar o dado dentro do quadrado
        // O texto será centralizado no quadrado
        FontMetrics metrics = g.getFontMetrics();
        int x = (100 - metrics.stringWidth(dado)) / 2 + 50; // Centralizar horizontalmente
        int y = ((100 - metrics.getHeight()) / 2) + metrics.getAscent() + 50; // Centralizar verticalmente
        g.drawString(dado, x, y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quadrado com Dado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Criar um objeto QuadradoComDado
        QuadradoComDado quadrado = new QuadradoComDado("6");
        
        // Adicionar o QuadradoComDado à janela
        frame.add(quadrado);
        
        // Definir o tamanho da janela
        frame.setSize(200, 200);
        
        // Exibir a janela
        frame.setVisible(true);
    }
}

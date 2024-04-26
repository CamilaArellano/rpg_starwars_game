import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import main.Main;

public class AW2 extends JFrame {

    public AW2() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 768, 574);
        getContentPane().setLayout(null);

        // Panel del video
        V3Panel v3Panel = new V3Panel();

        JPanel panelVideo = new JPanel();
        panelVideo.add(v3Panel);
        panelVideo.setBounds(0, 0, 768, 524);
        getContentPane().add(panelVideo);
        panelVideo.setLayout(null);

        Timer timer = new Timer(20000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar el frame actual
                dispose();

                // Abrir el frame "videojuego"
                Main v1 = new Main();
                v1.setVisible(true);
            }
        });

        // Iniciar el temporizador
        timer.setRepeats(false); // Hacer que el temporizador se ejecute solo una vez
        timer.start();
        
        setFocusable(true);
        //setFocusTraversalPolicyProvider(true);

        
        // Agregar KeyListener para el evento Escape
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Detener el temporizador si se presiona ESC antes de que transcurran 20 segundos
                    timer.stop();

                    // Cerrar el frame actual
                    dispose();

                    // Abrir el frame "videojuego"
                    Main v1 = new Main();
                    v1.setVisible(true);
                }
            }
        });

        // Solicitar enfoque en la ventana actual para garantizar que pueda recibir eventos del teclado
        //requestFocusInWindow();

        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                AW2 frame = new AW2();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class V3Panel extends JPanel {

    private final JFXPanel jfxpanel = new JFXPanel();

    public V3Panel() {
        setLayout(null);
        setBounds(0, 0, 768, 574);
        jfxpanel.setBounds(5, 5, 742, 525);
        Video();
        
        //JButton skip= new JButton("BOTON");
        
        //JButton skip = Imagen();
        //add(skip, BorderLayout.PAGE_START);
        add(jfxpanel);

    }
    
    /*
    private JButton Imagen()
    {
    	ImageIcon icon=new ImageIcon("src/res/skip.png");
    	JButton skip=new JButton(icon);
    	skip.setBounds(50,50,200,200);
    	skip.setPreferredSize(new Dimension(50,50));
    	return skip;
    }//*/

    private void Video() {
        File file = new File("C:/Users/NOM/Downloads/introdefinitiva.mp4");

        try {
            MediaPlayer oracleVid = new MediaPlayer(new Media(file.toURI().toString()));
            MediaView mediaView = new MediaView(oracleVid);

            //mediaView.setFitWidth(getWidth());
            mediaView.setFitHeight(getHeight());

            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(mediaView);

            jfxpanel.setScene(new Scene(stackPane));
            oracleVid.setVolume(0.7);
            oracleVid.setCycleCount(MediaPlayer.INDEFINITE);
            oracleVid.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

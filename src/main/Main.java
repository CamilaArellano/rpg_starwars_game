package main;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.Toolkit;
//Equipo 3  //5SA
//Arellano Peraza Eira Camila
//Balam �vila Isaac Alexander
//Pinacho Canto �ngel David
//Salinas Parra Sylvana
//Uicab Flores Jaziel Adonay
public class Main {

	public static final String main = null;

	public static void main() {
		// TODO Auto-generated method stub
		JFrame window=new JFrame();
		window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/objects/icon.png")));
		window.setFont(new Font("Candara", Font.PLAIN, 12));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);//No cambia el tama�o
		window.setTitle("Star Wars Crystals");
		window.setLocationRelativeTo(null);//Se despliega al centro
		window.setVisible(true);
		
		GamePanel gamePanel =new GamePanel();
		window.getContentPane().add(gamePanel);
		
		gamePanel.requestFocus();
		
		window.pack();//El frame toma el tama�o del panel
		
		gamePanel.setupGame();
		
		gamePanel.startGameThread();
		
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		Main m=new Main();
		m.main();
	}

}

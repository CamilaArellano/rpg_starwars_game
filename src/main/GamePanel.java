package main;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import object.UI;
import tile.TileManager;
import entity.Entity;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable
{
	
	//Configuraciones de la pantalla//
	final int originalTileSize=16;//16x16 tama�o est�ndar
	final int scale=3;//Escala
	public final int tileSize=originalTileSize*scale;//48x48 tama�o final
	public final int maxScreenCol=16;//16 columnas
	public final int maxScreenRow=12;//12 filas
	public final int screenWidth=tileSize*maxScreenCol;//Ancho de pantalla (768p)
	public final int screenHeight=tileSize*maxScreenRow;//Largo de pantalla (576p)
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	//public final int worldWidht = tileSize* maxWorldCol; //NO se usaron
	//public final int worldHeight = tileSize* maxWorldRow;
	
	//FPS
	int FPS=60;
	
	//SISTEMA
	TileManager tileM=new TileManager(this);//Teselas
	KeyHandler keyH=new KeyHandler();
	Sound music=new Sound();
	Sound se=new Sound();
	public UI ui=new UI(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public CollisionChecker cCheker = new CollisionChecker(this);
	
	public Thread gameThread;//Hilo
	public Player player=new Player(this,keyH);//Personaje
	public SuperObject obj[] = new SuperObject[10];
	
	
	//Posiciones default del jugador
	int playerX=100;
	int playerY=100;
	int playerSpeed=4;//Cambio de 4pixeles
	public int titleSize;
	
	public GamePanel()
	{
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setDoubleBuffered(true);//Los dibujos se renderizaran en un buffer fuera de pantalla
		this.addKeyListener(keyH);//Reconocer la tecla
		//this.setVisible(true);
		
        // Obtener el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calcular la posición para centrar la ventana en la pantalla
        int x = (screenSize.width - screenWidth)/2;
        int y = (screenSize.height - screenHeight)/2;

        // Establecer la posición de la ventana
        this.setLocation(x, y);
		
		this.setFocusable(true); 
		System.out.println("GamePanel initialized");
	}
	
	public void setupGame(){
		aSetter.setObject();
		
		//musica
		playMusic(0);
	}
	
	public void startGameThread()
	{
		gameThread=new Thread(this);
		gameThread.start();
	}

	
	public void run()
	{
		double drawInterval=1000000000/FPS;//60 fps 
		double delta=0;
		long lastTime=System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCount=0;
		while(gameThread != null)
		{
			currentTime=System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
			timer+=(currentTime-lastTime);
			lastTime=currentTime;
			if(delta>=1)
			{
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer>=1000000000)
			{
				//System.out.println("FPS: "+drawCount);
				timer=0;
				drawCount=0;
			}
		}
	}
	public void update()
	{	//System.out.println("UPDATE PLAYER");
		player.update();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2=(Graphics2D)g;//Pasar los gr�ficos a Gr�ficos2D
		
		//TILE
		tileM.draw(g2);//Antes del personaje
		
		//OBJETO
		for(int i=0;i<obj.length;i++){
			if(obj[i] != null)
			{
				obj[i].draw(g2,this);
			}
		}
		
		//PLAYER
		player.draw(g2);
		
		//UI TEXTO
		ui.draw(g2);
		
		g2.dispose();//Referencia el objeto actual
	}
	
	//SONIDO
	//Musica de fondo tienen diferentes variables para que no haya erores en el sonido.
	public void playMusic(int i){
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic(){
		music.stop();
	}
	//Sonido de objetos (efectos)
	public void playSE(int i){
		se.setFile(i);
		se.play();
	}

}

package object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class UI {//TEXTO
	
	GamePanel gp;
	Font starWarsFont, arial_80B;//arial_40
	BufferedImage keyImage;
	public boolean messageOn=false;
	public String message="";
	int messageCounter=0;
	public boolean gameFinished=false;
	
	public UI(GamePanel gp){
		this.gp=gp;
		starWarsFont=new Font("Candara", Font.BOLD, 40);
		arial_80B=new Font("Arial", Font.BOLD, 80);
		OBJ_Key key=new OBJ_Key();
		keyImage=key.image;
	}
	
	public void showMessage(String text){
		message=text;
		messageOn=true;
	}
	
	public void draw(Graphics g2){
		
		if(gameFinished==true){
			int shadowOffset=2;
			g2.setFont(new Font("Arial", Font.BOLD, 40));
			g2.setColor(Color.black);
			String text;
			int textLength;
			int x;
			int y;
			g2.setColor(Color.BLACK);
			g2.drawString(message, gp.tileSize/2 + shadowOffset, gp.tileSize*5 + shadowOffset);
			
			text="¡Encontraste todos los cristales!";
			textLength=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x=gp.screenWidth/2-textLength/2;
			y=gp.screenHeight/2-gp.tileSize*3;
			g2.drawString(text, x, y);

			
			g2.setFont(arial_80B);
			g2.setColor(Color.RED);
			text="GAME OVER";
			textLength=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x=gp.screenWidth/2-textLength/2;
			y=gp.screenHeight/2+gp.tileSize*2;
			g2.drawString(text, x, y);
			
			gp.gameThread=null;
			
		}else{
			g2.setFont(starWarsFont);
            g2.setColor(new Color(0, 32, 96));
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 70, 60);

            // MENSAJE
            if (messageOn) {
                int shadowOffset = 2;
                Graphics2D g2d = (Graphics2D) g2;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dibuja la sombra
                g2d.setColor(Color.white);
                g2d.drawString(message, gp.tileSize / 2 + shadowOffset, gp.tileSize * 5 + shadowOffset);

                // Dibuja el texto principal
                g2d.setColor(new Color(0, 32, 96));
                g2d.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
				
				messageCounter++;
				//Cuanto dura el mensaje
				if(messageCounter>90){
					messageCounter=0;
					messageOn=false;
				}
			}
		}
	}
}




















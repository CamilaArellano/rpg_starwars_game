package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_ChestOpen;
import object.OBJ_DoorOpen;

public class Player extends Entity
{
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey=0;
	int hasDoor=0;
	
	public Player(GamePanel gp,KeyHandler keyH)
	{
		this.gp=gp;
		this.keyH=keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		solidArea = new Rectangle();
		solidArea.x = 4;//8
		solidArea.y = 4;//16
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY =solidArea.y;
		solidArea.width = 16;//32
		solidArea.height = 16;//32
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues()
	{
		worldX=gp.tileSize * 23;
		worldY=gp.tileSize * 21;
		speed=4;
		direction="stand";
	}
	
	public void getPlayerImage()
	{
		try {
			stand=ImageIO.read(getClass().getResourceAsStream("/player/stand_01.png"));
			back=ImageIO.read(getClass().getResourceAsStream("/player/standBack_01.png"));
			up1=ImageIO.read(getClass().getResourceAsStream("/player/arriba_01.png"));
			up2=ImageIO.read(getClass().getResourceAsStream("/player/arriba02.png"));
			down1=ImageIO.read(getClass().getResourceAsStream("/player/abajo_01.png"));
			down2=ImageIO.read(getClass().getResourceAsStream("/player/abajo_02.png"));
			left1=ImageIO.read(getClass().getResourceAsStream("/player/izq_01.png"));
			left2=ImageIO.read(getClass().getResourceAsStream("/player/izq_02.png"));
			right1=ImageIO.read(getClass().getResourceAsStream("/player/derecha_01.png"));
			right2=ImageIO.read(getClass().getResourceAsStream("/player/derecha_02.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update()
	{	//System.out.println("UPDATE PLAYER");
		
		if(keyH.upPressed==true || keyH.downPressed==true 
				|| keyH.leftPressed==true || keyH.rightPressed==true) //La imagen solo cambiarï¿½ si presionas alguna tecla
		{
			//Cambio de coordenadas de acuerdo a la tecla
			if(keyH.upPressed==true)
			{
				direction="up";
				
				//System.out.println("Tecla Arriba");
			}
			else if(keyH.downPressed==true)
			{
				direction="down";
				
				//System.out.println("Tecla Abajo");
			}
			else if(keyH.leftPressed==true)
			{
				direction="left";
				
				//System.out.println("Tecla Izquierda");
			}
			else if(keyH.rightPressed==true)
			{
				direction="right";
				
				//System.out.println("Tecla Derecha");
			}
			
			//Check TILE COLLISIOM
			collisionOn = false;
			gp.cCheker.checkTile(this);
			
			//CHECK OBJECT COLLISION
			int objIndex = gp.cCheker.checkObject(this,true);
			pickUpObject(objIndex);
			
			// si la colision es falsa, el jugador puede moverse
			if (collisionOn == false){
				switch(direction){
				
				case "up":
					worldY-=speed;
					break;
					
				case "down":
					worldY+=speed;
					break;
					
				case "left":
					worldX-=speed;
					break;
					
				case "right":
					worldX+=speed;
					break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter>12)//La imagen del personaje cambiarï¿½ cada 12 frames
			{
				if(spriteNum==1){
					spriteNum=2;
				}
				else if(spriteNum==2){
					spriteNum=1;
				}
				spriteCounter=0;
			}
		}
		
	}
	
	//COLISION CON OBJETOS
	public void pickUpObject(int i)
	{
		
		if(i !=999)
		{
			String objectName = gp.obj[i].name;
			
			switch(objectName)
			{
			case "Key":
				hasKey++;
				gp.obj[i]=null;
				System.out.println("Key: "+hasKey);
				gp.playSE(1);
				gp.ui.showMessage("¡Encontraste un cristal kyber!");
				
				break;
			case "Door":
				if(hasKey>0)
				{
					int x=  gp.obj[i].worldX;
					int y=  gp.obj[i].worldY;
					gp.obj[i]=new OBJ_DoorOpen();
					gp.obj[i].worldX = x;
					gp.obj[i].worldY = y;
					
					gp.playSE(3);
					hasKey--;
					hasDoor++;
					gp.ui.showMessage("¡Abriste la puerta!");
				}else{
					collisionOn=true;
					gp.ui.showMessage("Necesitas un cristal para abrirla");
				}
				System.out.println("Key: "+hasKey);
				System.out.println("Door: "+hasDoor);
				
				break;
			case "Chest":
				if(hasDoor>2)
				{
					int x=  gp.obj[i].worldX;
					int y=  gp.obj[i].worldY;
					gp.obj[i]=new OBJ_ChestOpen();
					gp.obj[i].name="ChestOpen";
					gp.obj[i].worldX = x;
					gp.obj[i].worldY = y;
					gp.obj[i].collision=true;
					gp.playSE(3);
					hasDoor-=3;
					gp.ui.showMessage("Abriste el cofre");
					gp.ui.gameFinished=true;
					gp.stopMusic();
					gp.playSE(4);
				}else{
					collisionOn=true;
					gp.ui.showMessage("Necesitas abrir todas las puertas");
					//Se puede atorar el muÃ±eco
					
				}
				System.out.println("Key: "+hasKey);
				
				break;
			case "Boots":
				speed+=3;
				gp.playSE(2);
				gp.obj[i]=null;
				gp.ui.showMessage("+2 velocidad");
				break;
			}

		}
	}
	
	public void draw(Graphics2D g2)
	{
		//g2.setColor(Color.white);
		//g2.fillRect(x,y, gp.tileSize, gp.tileSize);
		
		BufferedImage image=null;
		//Dependiendo de la direcciï¿½n se "dibujarï¿½" la imagen
		switch(direction)
		{
		case "up":
			if(spriteNum==1)
			{
				image=up1;
			}
			if(spriteNum==2)
			{
				image=up2;
			}
			break;
		case "down":
			if(spriteNum==1)
			{
				image=down1;
			}
			if(spriteNum==2)
			{
				image=down2;
			}
			break;
		case "left":
			if(spriteNum==1)
			{
				image=left1;
			}
			if(spriteNum==2)
			{
				image=left2;
			}
			break;
		case "right":
			if(spriteNum==1)
			{
				image=right1;
			}
			if(spriteNum==2)
			{
				image=right2;
			}
			break;
		case "stand":
			if(spriteNum==1)
			{
				image=stand;
			}
			break;
		}
		//Personaje en la direcciï¿½n seleccionada
		g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
	}
}

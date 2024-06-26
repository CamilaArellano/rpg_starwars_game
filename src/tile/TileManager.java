package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager 
{
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp)
	{
		this.gp=gp;
		
		tile=new Tile[10];//N�mero de tiles
		mapTileNum=new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/map_3.txt");
	}
	
	public void getTileImage()
	{
		
		try 
		{
			tile[0]=new Tile();
			tile[0].image=ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1]=new Tile();
			tile[1].image=ImageIO.read(getClass().getResourceAsStream("/tiles/snow.png"));
			
			tile[2]=new Tile();
			tile[2].image=ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png"));
			tile[2].collision = true; //NOTA: AUN NO ACTIVEN LAS COLISIONES, NO EST� LISTO EL MAPA-----------------------------
			
			tile[3]=new Tile();
			tile[3].image=ImageIO.read(getClass().getResourceAsStream("/tiles/tierra.png"));
			tile[3].collision = false;//-------------------------------------------------------------------
			
			tile[4]=new Tile();
			tile[4].image=ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true; //--------------------------------------------------------------------------------------
			
			tile[5]=new Tile();
			tile[5].image=ImageIO.read(getClass().getResourceAsStream("/tiles/grass3.png"));
			
			
			tile[6]=new Tile();
			tile[6].image=ImageIO.read(getClass().getResourceAsStream("/tiles/rock.png"));
			tile[6].collision=true;
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath)
	{
	    try
	    {
	        InputStream is = getClass().getResourceAsStream(filePath);
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        int col = 0;
	        int row = 0;

	        while (col < gp.maxWorldCol && row < gp.maxWorldRow) // Leer hasta el final del archivo
	        {
	            String line  = br.readLine();
	            while (col < gp.maxWorldCol)
	            {
	            	String  numbers[] = line.split(" ");
	                int num = Integer.parseInt(numbers[col]);
	                mapTileNum[col][row] = num;
	                col++;
	            }
	            if(col == gp.maxWorldCol)
	            {
	            	col = 0;
	            	row++;
	            }
	            
	        }
	        br.close();
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	public void draw(Graphics2D g2)
	{
		int worldCol=0;
		int worldRow=0;

		
		while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow)
		{
			int tileNum=mapTileNum[worldCol][worldRow];
			
			int worldX= worldCol * gp.tileSize;
			int worldY= worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			//este metodo recorta un poco de procesamiento adicional, ya que solo crea las baldosas necesarias
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
			{
				g2.drawImage(tile[tileNum].image,screenX,screenY, gp.tileSize,gp.tileSize,null);
			}
				
			
			//g2.drawImage(tile[tileNum].image,screenX,screenY, gp.tileSize,gp.tileSize,null);
			
			worldCol++;//Aumenta la columna

			if(worldCol==gp.maxWorldCol)
			{
				worldCol=0; //Se reinicia al llegar al limite
				worldRow++;

			}
			//g2.drawImage(tile[1].image,48,0,gp.tileSize,gp.tileSize,null);
			//g2.drawImage(tile[2].image,96,0,gp.tileSize,gp.tileSize,null);
		}
		
	}
}

package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_ChestOpen extends SuperObject{
	
	public OBJ_ChestOpen(){
		
		name = "ChestOpen";
		try{
			image=ImageIO.read(getClass().getResourceAsStream("/objects/chestopen.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
				
	}

}

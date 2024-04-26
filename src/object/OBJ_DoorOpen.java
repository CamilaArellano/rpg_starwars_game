package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_DoorOpen extends SuperObject{
	
	public OBJ_DoorOpen(){
		
		name = "DoorOpen";
		try{
			image=ImageIO.read(getClass().getResourceAsStream("/objects/dooropen.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
				
	}

}
package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public Clip clip;
	public URL soundURL[]=new URL[10];
	
	public Sound()
	{
		soundURL[0]=getClass().getResource("/sound/illum.wav");
		soundURL[1]=getClass().getResource("/sound/cristal.wav");
		soundURL[2]=getClass().getResource("/sound/powerup.wav");
		soundURL[3]=getClass().getResource("/sound/door.wav");
		soundURL[4]=getClass().getResource("/sound/final.wav");
		soundURL[5]=getClass().getResource("/sound/unloock.wav");
	}
	
	public void setFile(int i){//Lectura de audio
		try{
			AudioInputStream ais=AudioSystem.getAudioInputStream(soundURL[i]);
			clip=AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception e){
			
		}
	}
	public void play(){
		clip.start();
	}
	public void loop(){
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop(){
		clip.stop();
	}
}























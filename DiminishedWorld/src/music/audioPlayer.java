package music;


import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 
/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * @author www.codejava.net
 *
 */
public class audioPlayer implements LineListener {
     
    /**
     * this flag indicates whether the playback completes or not.
     */
    boolean playCompleted;
    AudioInputStream audioStream;
    AudioFormat format;
    DataLine.Info info;
    Clip audioClip;
    FloatControl gainControl;
     
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    public  void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);
 
        try {
            audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            format = audioStream.getFormat();
 
            info = new DataLine.Info(Clip.class, format);
 
            audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
            
            gainControl = 
            	    (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            	//gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
                gainControl.setValue(6.0f);
             
            audioClip.start();
            
            
            
            
            /** Makes it wait for playback to be completed
             
            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            audioClip.close();
            */ 
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
         
    }
     
    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
 
    }
    
    public void modVolume(int newVolume){
    	if(newVolume/10f > 6f){
    		gainControl.setValue(6f);
    	}else if(newVolume*1f < -80){
    		gainControl.setValue(-80);
    	}else{
    		if(newVolume >= 0){
    			gainControl.setValue(newVolume/10f);
    		}else{
    			gainControl.setValue(newVolume*1f);
    		}
    	}
    }
    
    public void close(){
    	audioClip.close();
    }
    /**
 
    public static void main(String[] args) {
        //String audioFilePath = "Z:/thingsBesidesTurnIn/ogpcSongs/menuSongWav.wav"; //WORKS
    	String audioFilePath = "res/menuSongWav.wav";
        audioPlayer player = new audioPlayer();
        player.play(audioFilePath);
        
    }
    */
 
}

package stickfareremastered;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    private boolean mute = false;
    private File soundFile = null;
    AudioInputStream stream = null;
    AudioFormat format = null;
    DataLine.Info info = null;
    Clip clip = null;

    Sound(String name) {
        soundFile = new File(name);
    }
    
    public void play(String fileName, boolean loop) {
        try {
            mute = false;
            soundFile = new File(fileName);
            stream = AudioSystem.getAudioInputStream(soundFile);    
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);   
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();   
            if (loop)
              clip.loop(Clip.LOOP_CONTINUOUSLY);    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void toggleMute() {
        //System.out.println("In mute class");
        if (clip != null) {
            if (mute) {
                clip.start();
                mute = false;
            } else {
                clip.stop();
                mute = true;
            }
        }
        else {
            
        }
    }
}


package stickfareremastered;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import static stickfareremastered.StickfareRemastered.g;

public class Button {
    StickfareRemastered mainframe;
   
   
    
    static private Image muteImage = Toolkit.getDefaultToolkit().getImage("./images/speaker.png");
    static private boolean mouseHoldOn = false;
    static private boolean muteOn = false;
    static private boolean onMute = false;
    static private boolean onInstructions = false;
    static private boolean onExit = false;

    static private boolean onStart = false;
    
   
       
    static private boolean onBack = false;

    static private boolean runFirst = true;


    static public void mouseClickHandler(StickfareRemastered frame, int x, int y) {
        mouseHoldOn = false;
       
       
        if (onInstructions) { activateInstructionsButton(); }
        else if (onExit) { activateExitButton(); }
        else if (onBack) { activateBackButton(); }
        else if (onStart) { activateStartButton(frame); }
        else if (onMute) { activateMuteButton(); }
    }

    static public void mouseDraggedHandler(StickfareRemastered frame, int x, int y) {        
        mouseHoldOn = true;
    }

    static public void mainHandler(StickfareRemastered frame, int x, int y) {
        // Play button detection & sound effect
        if((x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 39/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 60/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 42/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 53/100))) {
            if (!onStart)
                Titlescreen.getMenuSounds().play("./sounds/Menu_Click_Exit.wav");
            onStart = true;
        }

            else
                onStart = false;
        // Instruction button detection & sound effect
        if((x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 39/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 60/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 57/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 68/100))) {
           
            if(!onInstructions)
                Titlescreen.getMenuSounds().play("./sounds/Menu_Click_Exit.wav");
            onInstructions = true;
        }
                         
            else
                onInstructions = false;
     
        // Exit button detection & sound effect
        if((x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 39/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 60/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 72/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 83/100))) {
            if(!onExit)
                Titlescreen.getMenuSounds().play("./sounds/Menu_Click_Exit.wav");
            onExit = true;
        }      
            else
                onExit = false;
           
//        g.drawString("Exit", 360, 668);
        // Mute button detection and drawing
        drawMute(frame, Window.getX(Window.getWidth2()-100), Window.getY(Window.getHeight2()-100));        
        onMute = x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 91.6/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 95.7/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 87.7/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 95.7/100);
        
    }
   

       

    static public void instructionsHandler(StickfareRemastered frame, int x, int y) throws FileNotFoundException, FontFormatException, IOException {
        onBack = (x>=((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 1/100)&&x<=((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 14/100)&&y>=((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 88/100)&&y<=((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 98/100));                      
        onMute = x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 91.6/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 95.7/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 87.7/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 95.7/100);
    }

    static private void activateInstructionsButton() {
        System.out.println("activateInstructionsButton");
        Titlescreen.activateInstructions();
        onInstructions = false;
    }

    static private void activateExitButton() {
        System.out.println("activateExitButton");
        Titlescreen.getMenuSounds().play("./sounds/Menu_Click.wav");
        System.exit(0);
       
    }
    static private void activateStartButton(StickfareRemastered frame) {
        System.out.println("activateStartButton");
        Titlescreen.getMenuSounds().play("./sounds/Menu_Click.wav");
        Titlescreen.startGame(frame);
        onStart = false;
    }

    static private void activateBackButton() {
        System.out.println("activateBackButton");
        Titlescreen.activateMain();
        onBack = false;
    }
    static private void activateMuteButton() {
        SoundManager.toggleMute();
        muteOn = !muteOn;
    }
    static public void drawMute(StickfareRemastered frame, int x, int y) {
        if(muteOn)
            muteImage = Toolkit.getDefaultToolkit().getImage("./images/speakerMute.png");
        else
            muteImage = Toolkit.getDefaultToolkit().getImage("./images/speaker.png");
        g.drawImage(muteImage, x, y, 90, 90, frame);
    }
     

   


   
}

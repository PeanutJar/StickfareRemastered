package stickfareremastered;
import java.awt.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static stickfareremastered.StickfareRemastered.g;

    public class Titlescreen {
    static private final SoundManager MENU_SOUNDS = new SoundManager();
    static private final Image MAIN_MENU = Toolkit.getDefaultToolkit().getImage("./images/Main_Menu.png");
    static private final Image MAIN_MENU_PLAY = Toolkit.getDefaultToolkit().getImage("./images/Main_Menu_Play.png");
    static private final Image MAIN_MENU_INSTRUCTIONS = Toolkit.getDefaultToolkit().getImage("./images/Main_Menu_Instructions.png");
    static private final Image MAIN_MENU_EXIT = Toolkit.getDefaultToolkit().getImage("./images/Main_Menu_Exit.png");
    static private final Image MAIN_MENU_BACKGROUND = Toolkit.getDefaultToolkit().getImage("./images/Main_Menu_Background.gif");

    static private final Image INSTRUCTIONS_IMAGE = Toolkit.getDefaultToolkit().getImage("./images/Instructions.png");
    static private final Image INSTRUCTIONS_EXIT_IMAGE = Toolkit.getDefaultToolkit().getImage("./images/Instructions_Exit.png");
  
    
    static public boolean mainmenuActive = true;
    static public boolean mainmenuplayActive = false;
    static public boolean mainmenuinstructionsActive = false;
    static public boolean mainmenuexitActive = false;
    static public boolean mainmenubackgroundActive = true;
    static public boolean instructionsActive = false;
    static public boolean instructionsexitActive = false;
   
   
   
   
    static private boolean gameStarted = false;
    static private int customizePlayerNum = 1;

    static void reset() {
        MENU_SOUNDS.clearSounds();
        MENU_SOUNDS.addSound("./sounds/Main_Menu_Theme.wav");
        MENU_SOUNDS.addSound("./sounds/Menu_Click.wav");
        MENU_SOUNDS.addSound("./sounds/RifleFire.wav");
        MENU_SOUNDS.addSound("./sounds/Menu_Click_Exit.wav");        
        MENU_SOUNDS.loop("./sounds/Main_Menu_Theme.wav");
    }

    static void titlescreenHandler(StickfareRemastered frame, int x, int y) throws FontFormatException, IOException {
        if (mainmenuActive) {
            mainHandler(x, y, frame);
         
        }
            
        else if (instructionsActive)
            instructionsHandler(x, y, frame);

             
                   
    }
 

    static private void mainHandler(int x, int y, StickfareRemastered frame) throws FileNotFoundException, FontFormatException, IOException {
        g.drawImage(MAIN_MENU_BACKGROUND,0,0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT,frame);
        g.drawImage(MAIN_MENU,0,0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT,frame);    
        Button.mainHandler(frame, x, y);
        if((x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 39/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 60/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 42/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 53/100))) {
            g.drawImage(MAIN_MENU_PLAY,0, 0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT, frame);                       
        }
            
        if((x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 39/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 60/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 57/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 68/100))) {
            g.drawImage(MAIN_MENU_INSTRUCTIONS,0, 0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT, frame);
            
        }
        if((x>((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 39/100)&&x<((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 60/100)&&y>((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 72/100)&&y<((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 83/100))) {
            g.drawImage(MAIN_MENU_EXIT,0, 0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT, frame);
           
        }
        Button.drawMute(frame, Window.getX(Window.getWidth2()-100), Window.getY(Window.getHeight2()-100));        
       
    }


    static private void instructionsHandler(int x, int y, StickfareRemastered frame)throws FileNotFoundException, FontFormatException, IOException {
        g.drawImage(MAIN_MENU_BACKGROUND,0,0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT,frame);
        g.drawImage(INSTRUCTIONS_IMAGE, 0,0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT,frame);      
        
        Button.instructionsHandler(frame, x, y);
        Button.drawMute(frame, Window.getX(Window.getWidth2()-100), Window.getY(Window.getHeight2()-100));        
       
        if((x>=((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 1/100)&&x<=((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 14/100)&&y>=((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 88/100)&&y<=((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 98/100))) {
                       
                g.drawImage(INSTRUCTIONS_EXIT_IMAGE,0, 0,Window.MENU_WINDOW_WIDTH,Window.MENU_WINDOW_HEIGHT, frame);
                 
               
        }
         
    }

       

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static public void startGame(StickfareRemastered frame) {
        
        mainmenuActive = false;
       
        instructionsActive = false;
        gameStarted = true;
    }

    static public void mouseClickHandler(StickfareRemastered frame, int x, int y) {
        Button.mouseClickHandler(frame, x, y);
    }
    static public void mouseDraggedHandler(StickfareRemastered frame, int x, int y) {
        Button.mouseDraggedHandler(frame, x, y);
    }



    static public void activateMain() {
        mainmenuActive = true;
       
        instructionsActive = false;

    }

    static public void activateStart() {
        mainmenuActive = false;
        gameStarted = true;
        instructionsActive = false;        
        Titlescreen.getMenuSounds().play("./sounds/Menu_Click.wav");
        
    }

    static public void activateInstructions() {
        mainmenuActive = false;
       
        instructionsActive = true;
        Titlescreen.getMenuSounds().play("./sounds/Menu_Click.wav");
        
    }

    static public void startedGame() {
        mainmenuActive = false;
        gameStarted = true;
        System.out.println("startedGame");
    }

    static public boolean gameStarted(){
        return gameStarted;
    }

    static public boolean isActive() {
        return mainmenuActive || instructionsActive;
    }

    static public boolean isMainActive() {
        return mainmenuActive;
    }


    static public boolean isInstructionsActive() {
        return instructionsActive;
    }
    static SoundManager getMenuSounds() {
        return MENU_SOUNDS;
    }

}


package stickfareremastered;

import java.awt.*;
import java.util.ArrayList;

public class PowerUp {
    
    public static Image image;
    public static Image image2;
    public static Image image3;
    private static PowerUp powerups[] = new PowerUp[6];
    private static StickfareRemastered mainClassInst;
    private int xpos;
    private int ypos;
    private Image powerup_image;
    private boolean isactive;
    
    
    public static void Draw(Graphics2D g, int xD, int yD) {    
        for(PowerUp powerup : powerups)
        {
            if(powerup.isactive)
            {
                if(powerup.powerup_image == image)
                {
                    drawImage(g,image,powerup.xpos,powerup.ypos,0.0,0.2,0.2 );
                }
                else if(powerup.powerup_image == image2)
                {
                    drawImage(g,image2,powerup.xpos,powerup.ypos,0.0,0.2,0.2 );
                }
                else if(powerup.powerup_image == image3)
                {
                    drawImage(g,image3,powerup.xpos,powerup.ypos,0.0,0.2,0.2 );
                }
                //System.out.println((int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
            }
        }
    }
    
    
    public static void Reset() {
        int xD = Window.getWidth2()/Maps.getWidth();
        int yD = Window.getHeight2()/Maps.getHeight();
        //RED
        powerups[0] = new PowerUp(image2,(Window.getX(0)+xD*((660)- 5) - (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.058))/2,(Window.getY(0)+yD*420 - (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.065))/2);
        powerups[1] = new PowerUp(image2,(Window.getX(0)+xD*((660)- 5) - (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.058))/2,(Window.getY(0)+yD*580 - (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.065))/2);
        
        
        //YELLOW
        powerups[2] = new PowerUp(image3,(Window.getX(0)+xD*((202)- 5) - (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.058))/2,(Window.getY(0)+yD*310 - (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.065))/2);
        powerups[3] = new PowerUp(image3,(Window.getX(0)+xD*((1125)- 5) - (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.058))/2,(Window.getY(0)+yD*310 - (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.065))/2);
        
        //ORANGE
        powerups[4] = new PowerUp(image,(Window.getX(0)+xD*((212)- 5) - (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.058))/2,(Window.getY(0)+yD*410 - (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.065))/2);
        powerups[5] = new PowerUp(image,(Window.getX(0)+xD*((1111)- 5) - (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.058))/2,(Window.getY(0)+yD*410 - (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.065))/2);
    }
    
    PowerUp(Image _image, int _xpos, int _ypos) {
        powerup_image = _image;
        xpos = _xpos;
        ypos = _ypos;
        isactive = true;
    }
    
    public static void drawImage(Graphics2D g,Image image,int xpos,int ypos,double rot,double xscale,
            double yscale) {
        int width = image.getWidth(mainClassInst);
        int height = image.getHeight(mainClassInst);
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.drawImage(image,-width/2,-height/2,
        width,height,mainClassInst);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    
    public static void Animate(int timecount, double framerate) {
        for(int i=0;i<powerups.length;i++) {
            
            for(PowerUp powerup : powerups)
            {
                if(powerup.isactive)
                {
                    if(Player.GetCurrentPlayer().getSuperxPos() <= (powerup.xpos + 10) && Player.GetCurrentPlayer().getSuperxPos() >= (powerup.xpos - 10) && Player.GetCurrentPlayer().getSuperyPos() <= (powerup.ypos + 10) && Player.GetCurrentPlayer().getSuperyPos() >= (powerup.ypos - 10))
                    {           
                        if(powerup.powerup_image == image2)
                        {
                            Player.GetCurrentPlayer().ChangeDamage(10);
                        }
                        else if(powerup.powerup_image == image)
                        {
                            Player.GetCurrentPlayer().ChangeAmmo(5);
                        }
                        else if(powerup.powerup_image == image3)
                        {
                            Player.GetCurrentPlayer().ChangeMovementMeter(20);
                        }
                        powerup.isactive = false;
                    }
                }
                else if(!powerup.isactive)
                {
                    if(timecount % (int)(60*framerate) == ((int)(60*framerate)-1))
                    {
                        powerup.isactive = true;
                    }
                }
            }
        }
        
        
    }
    
    
    
}


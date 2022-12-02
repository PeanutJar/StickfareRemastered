
package stickfareremastered;

import java.awt.*;
import java.util.ArrayList;

public class Gun {
    
    public static Image image;
    private static ArrayList<Gun> bullets = new ArrayList<Gun>();
    private static StickfareRemastered mainClassInst;
    private int xSpeed;
    private int yfall;
    private int xpos;
    private int ypos;
    private double rotation;
    private static boolean IsBulletAimActive = true;
    private boolean fallactive;
    private static boolean fixbug;
    
    
    public static void Draw(Graphics2D g) {     
        for(Gun gun : bullets)
        {
            if(Player.GetCurrentPlayer().getDirection() == Player.Direction.LEFT)
            {
                drawImage(g,image,gun.xpos,gun.ypos,gun.rotation,-1.0,1.0 ); 
            }
            else if(Player.GetCurrentPlayer().getDirection() == Player.Direction.RIGHT)
            {
                drawImage(g,image,gun.xpos,gun.ypos,gun.rotation,1.0,1.0 ); 
            }
        }
        
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
    
    public static void Reset() {
        bullets.clear();
        IsBulletAimActive = true;
        fixbug = true;
    }
    
    public static void Shoot() {
        if(bullets.size() < 1 && Player.GetCurrentPlayer().getAmmo() > 0)
        {
            if(Player.GetCurrentPlayer().getDirection() == Player.Direction.RIGHT)
            {
                Gun gun = new Gun(Player.GetCurrentPlayer().getSuperxPos(),Player.GetCurrentPlayer().getSuperyPos(),Player.GetCurrentPlayer().getRotation());
                bullets.add(gun);
                Player.GetCurrentPlayer().ChangeAmmo(-1);
                //System.out.print(Player.GetCurrentPlayer().getSuperxPos());
                //System.out.print(Player.GetCurrentPlayer().getSuperyPos());
            }
            else if(Player.GetCurrentPlayer().getDirection() == Player.Direction.LEFT)
            {
                Gun gun = new Gun(Player.GetCurrentPlayer().getSuperxPos(),Player.GetCurrentPlayer().getSuperyPos(),Player.GetCurrentPlayer().getRotation());
                bullets.add(gun);
                Player.GetCurrentPlayer().ChangeAmmo(-1);
                //System.out.print(Player.GetCurrentPlayer().getSuperxPos());
                //System.out.print(Player.GetCurrentPlayer().getSuperyPos());
            }
            fixbug = true;
        }
        
    }
    
    Gun(int _xpos, int _ypos, double _rotation) {
        xSpeed = 0;
        yfall = 0;
        xpos = _xpos;
        ypos = _ypos;
        rotation = _rotation;
        fallactive = false;
    
    }
    
    public static boolean getIsBulletAimActive() {
        return(IsBulletAimActive);
    }
    
    public static void Animate(int timecount, double framerate) {
        for (int i=0;i<bullets.size();i++) {
            bullets.get(i).xpos += bullets.get(i).xSpeed;
            bullets.get(i).ypos += bullets.get(i).yfall;
            IsBulletAimActive = false;
            //System.out.println(gun.xSpeed);
            //System.out.println(gun.yfall);
            for(int b = 1; b <= 36; b++)        //goes through all 360 degree angles in sections of 10 (ex: 10-20-30-40-50, ect.)         
                if(Player.GetCurrentPlayer().getDirection() == Player.Direction.RIGHT) {      //if the player is shooting on the right direction          
                    if(Player.GetCurrentPlayer().getRotation() == 0) {                 
                        bullets.get(i).xSpeed = 12;
                        
                        bullets.get(i).yfall = 2;
                        
                        if(timecount % (int)(b*framerate) == ((int)(b*framerate)-1))                        
                            bullets.get(i).rotation += 0.5;    

                    }
                    else if(Player.GetCurrentPlayer().getRotation() < ((b-1) * -10) && Player.GetCurrentPlayer().getRotation() >= (b * -10)) {                     
                        if(Player.GetCurrentPlayer().getRotation() >= -90 && Player.GetCurrentPlayer().getRotation() < 0) {    //the projectile ark if the player is aiming at -90 degreee angle to -1 degree angle
                            bullets.get(i).xSpeed = 12 - (int)(b * 0.8);
                            
                            if(!bullets.get(i).fallactive)             
                                bullets.get(i).yfall = -(int)(b * 0.9);
                            
                            if(timecount % (int)(0.5*framerate) == ((int)(0.5*framerate)-1)) {              
                                bullets.get(i).yfall += 1;
                                bullets.get(i).rotation += 10;
                                bullets.get(i).fallactive = true;
                            }

                        }
                        else if(Player.GetCurrentPlayer().getRotation() >= -360 && Player.GetCurrentPlayer().getRotation() <= -270) {        //the projectile ark if the player is aiming at -360 degreee angle to -270 degree angle         
                            bullets.get(i).xSpeed = -(12 - (int)((b-18) * 1.3));
                            int g = 39 - b;
                            
                            if(!bullets.get(i).fallactive)             
                                bullets.get(i).yfall = (int)(g);
                            
                            if(timecount % (int)(0.8*framerate) == ((int)(0.8*framerate)-1)) {      
                                bullets.get(i).yfall += 1;
                                bullets.get(i).rotation += 0.5;
                                bullets.get(i).fallactive = true;    
                            }

                        }      
                    }
                }
                else if(Player.GetCurrentPlayer().getDirection() == Player.Direction.LEFT) {   //if the player is shooting in the left direction
                    if(Player.GetCurrentPlayer().getRotation() == 0) {               
                        bullets.get(i).xSpeed = -12;
                        
                        bullets.get(i).yfall = 2;
                        
                        if(timecount % (int)(0.5*framerate) == ((int)(0.5*framerate)-1))      
                            bullets.get(i).rotation -= 10;

                    }
                    else if(Player.GetCurrentPlayer().getRotation() <= (b * 10) && Player.GetCurrentPlayer().getRotation() > ((b - 1) * 10)) {   
                        if(Player.GetCurrentPlayer().getRotation() <= 90 && Player.GetCurrentPlayer().getRotation() > 0) {  //the projectile ark if the player is aiming at 90 degreee angle to 1 degree angle
                            bullets.get(i).xSpeed = -(12 - (int)(b * 0.8));
                            
                            if(!bullets.get(i).fallactive)     
                                bullets.get(i).yfall = -(int)(b * 0.8);
                            
                            if(timecount % (int)(0.5*framerate) == ((int)(0.5*framerate)-1)) {
                                bullets.get(i).yfall += 1;
                                bullets.get(i).rotation -= 10;
                                bullets.get(i).fallactive = true;
                            }

                        }
                        else if(Player.GetCurrentPlayer().getRotation() <= 360 && Player.GetCurrentPlayer().getRotation() >= 270) {    //the projectile ark if the player is aiming at 360 degreee angle to 270 degree angle
                            bullets.get(i).xSpeed = 12 - (int)((b-18) * 1.3);
                            int g = 39 - b;
                            
                            if(!bullets.get(i).fallactive)             
                                bullets.get(i).yfall = (int)(g);
                            
                            if(timecount % (int)(0.8*framerate) == ((int)(0.8*framerate)-1)) {
                                bullets.get(i).yfall += 1;
                                bullets.get(i).rotation -= 0.5;
                                bullets.get(i).fallactive = true;       
                            }

                        }  
                    }
                }        
            //projectile interactions with the map and players
            if(bullets.get(i).xpos < 90 || bullets.get(i).xpos > Window.getWidth2() + 90 || bullets.get(i).ypos < 0 || bullets.get(i).ypos > Window.getHeight2() + 30) {
                bullets.remove(i);
                i--;
                if(bullets.size() == 0) 
                    IsBulletAimActive = true;  
                Player.SwitchTurn();
            }
            else  
                for(Player ptr : Player.getPlayers()) 
                    if(fixbug) //this just makes it so it won't loop once the bullet is gone
                        if(ptr.getTeam() == Player.GetEnemyTeam()) 
                            if(bullets.get(i).xpos < ptr.getSuperxPos() + 10 && bullets.get(i).xpos > ptr.getSuperxPos() - 10 && bullets.get(i).ypos < ptr.getSuperyPos() + 15 && bullets.get(i).ypos > ptr.getSuperyPos() - 15) { 
                                //System.out.println("hit");
                                ptr.ChangeHealth();
                                bullets.remove(i);
                                i--;
                                if(bullets.size() == 0)                             
                                    IsBulletAimActive = true;                               
                                if(ptr.getHealth() == 0)       
                                    ptr.setisactive(false);                              
                                else           
                                    Player.SwitchTurn();                                 
                                fixbug = false;
                            }                                         
        }
    }
    
}


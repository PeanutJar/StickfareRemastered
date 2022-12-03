package stickfareremastered;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StickfareRemastered extends JFrame implements Runnable {

    
    public static Image image;
    public static Graphics2D g;

    Image CaveMap;

    boolean W = false;
    boolean A = false;
    boolean S = false;
    boolean D = false;
    boolean GameEnd;
    int timecount;
    double framerate = 25.0;
    double angle1 = 0;
    
    Thread relaxer;
    static int mouseX, mouseY;
    boolean bcActive=false;



    static StickfareRemastered frame;
    public static void main(String[] args) {        
        GraphicsDevice Device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        frame = new StickfareRemastered();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        int x = Device.getDisplayMode().getWidth();
        int y = Device.getDisplayMode().getHeight();
        new Window(x,y);
        Device.setFullScreenWindow(frame);
    }

    public StickfareRemastered() {
        addMouseListener(new MouseAdapter()     {
            public void mousePressed(MouseEvent e) {
                if(Titlescreen.gameStarted())    
                    repaint();
                
                if (e.BUTTON1 == e.getButton()) {
                    //left button
                    
// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();
                    System.out.println(xpos + " " + ypos);
                    if(!Titlescreen.isActive())
                    {
                        Titlescreen.getMenuSounds().play("./sounds/RifleFire.wav");
                        Gun.Shoot();
                        
                    }
                                      
                    
                }
                if (e.BUTTON2 == e.getButton()) {
                    //scroll wheel
                    Gun.Reset();
                }
                if (e.BUTTON3 == e.getButton()) {
                    Titlescreen.mainmenuActive = true;
                    Player.Clear();
                    for(int m = 0; m < 2; m++)
                    {
                        Player.Create(Maps.getWidth()/2, Maps.getHeight()/2,m);
                    }
                    Player.Reset();
                    Gun.Reset();
                    //PowerUp.Reset();
                    GameEnd = false;
                    }
                
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        Titlescreen.mouseDraggedHandler(Window.currentFrame, mouseX, mouseY);
        repaint();
      }
    });
    addMouseListener(new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.BUTTON1 == e.getButton()) {
                //System.out.println(e.getX() + "   " + e.getY());
                Titlescreen.mouseClickHandler(Window.currentFrame, e.getX(), e.getY());
            }
            repaint();
        }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseClicked(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            Titlescreen.mouseClickHandler(Window.currentFrame, mouseX, mouseY);
            repaint();
            
        }
    });
    
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
          mouseX = e.getX();
          mouseY = e.getY();
          repaint();

          if(!Titlescreen.isActive())
          {
             double CursorX = e.getX();
            double CursorY = e.getY();
            double PlayerX = Player.GetCurrentPlayer().getxPos();
            double PlayerY = Player.GetCurrentPlayer().getyPos();
            if(Player.GetCurrentPlayer().getDirection() == Player.Direction.RIGHT)
            {
              if(Gun.getIsBulletAimActive())
              {
                  
                  if(Player.GetCurrentPlayer().getRotation() >= -90)
                  {
                    double angle = (Math.atan2(PlayerY - (CursorY * 3/5 - 100), PlayerX - (CursorX - (CursorX * 6/11 + 10))) * 180 / Math.PI) - 180;
                      //double angle = (Math.atan2((CursorY)-PlayerY, (CursorX)-PlayerX) * 180 / Math.PI) - 180;
                    angle1 = angle;
                    Player.GetCurrentPlayer().Rotate(angle);
                  }
                  else if(Player.GetCurrentPlayer().getRotation() <= -270)
                  {
                    double angle = (Math.atan2(PlayerY - (CursorY - (CursorY * 3/5 - 22)), PlayerX - (CursorX - (CursorX * 6/11 + 32))) * 180 / Math.PI) - 180;
                      //double angle = (Math.atan2((CursorY)-PlayerY, (CursorX)-PlayerX) * 180 / Math.PI) - 180;
                    angle1 = angle;
                    Player.GetCurrentPlayer().Rotate(angle);
                  }
                  

              }
            }
            else if(Player.GetCurrentPlayer().getDirection() == Player.Direction.LEFT)
            {
              if(Gun.getIsBulletAimActive())
              {
                  
                  if(Player.GetCurrentPlayer().getRotation() <= 90)
                  {
                    double angle = (Math.atan2((CursorY - (CursorY * 3/5)) - PlayerY, (CursorX - (CursorX * 6/11)) - PlayerX) * 180 / Math.PI) + 180;
                    angle1 = angle;
                    Player.GetCurrentPlayer().Rotate(angle);
                  }
                  else if(Player.GetCurrentPlayer().getRotation() >= 270)
                  {
                    double angle = (Math.atan2((CursorY - (CursorY * 3/5 - 20)) - PlayerY, (CursorX - (CursorX * 6/11 - 10)) - PlayerX) * 180 / Math.PI) + 180;
                    angle1 = angle;
                    Player.GetCurrentPlayer().Rotate(angle);
                  }


              }
            }
          }  
          
        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
               
                if(!Titlescreen.isActive())
                {
                        if (e.VK_W == e.getKeyCode()) {
                            if (!W)
                         W = true;
                 } else if (e.VK_A == e.getKeyCode()) {
                     if (!A)
                         A = true;
                 } else if (e.VK_D == e.getKeyCode()) {
                     if (!D)
                         D = true;
                 }
                }
                if (e.VK_ESCAPE == e.getKeyCode())
                    System.exit(0);

                repaint();
            }
            public void keyReleased(KeyEvent e){
                if(!Titlescreen.isActive())
                {
                        if (e.VK_W == e.getKeyCode()) {
                     if (W)
                         W = false;
                 } else if (e.VK_A == e.getKeyCode()) {
                     if (A)
                         A = false;
                 } else if (e.VK_D == e.getKeyCode()) {
                     if (D)
                         D = false;
                 }
                }
            }
        });
        init();
        start();
    }
    
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }



////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setColor(Color.black);
        g.fillRect(0, 0, Window.xsize, Window.ysize);
        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};

        if (Window.animateFirstTime)
        {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        try  {
            if (Titlescreen.isActive())
                    Titlescreen.titlescreenHandler(this, mouseX, mouseY);

        } catch (FontFormatException ex) {
            Logger.getLogger(StickfareRemastered.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StickfareRemastered.class.getName()).log(Level.SEVERE, null, ex);
        }
       if(!Titlescreen.isActive())
       {
            g.drawImage(CaveMap,Window.getX(0),Window.getY(0),
            Window.getWidth2(),Window.getHeight2(),this);
            String[][] Thingers = Maps.getHitbox();
            int xD = Window.getWidth2()/Maps.getWidth();
            int yD = Window.getHeight2()/Maps.getHeight();

            Player.Draw(g, xD, yD);
            Gun.Draw(g);
            PowerUp.Draw(g, xD, yD);


         if(GameEnd)
         {
             g.setColor(Color.black);
             g.setFont(new Font("Arial Black", Font.BOLD, 60));
             g.drawString(Player.GetWinningPlayerString() + " Wins!", (Window.getWidth2()*1/2)-170, Window.getHeight2()/2);
         }

         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial Black", Font.PLAIN, 20));
         g.drawString("Angle: " + angle1, Window.getWidth2() * 1/8, Window.getHeight2() * 1/8);
         
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial Black", Font.PLAIN, 20));
         g.drawString("Ammo: " + Player.GetCurrentPlayer().getAmmo(), Window.getWidth2() * 3/8, Window.getHeight2() * 1/8);
         
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial Black", Font.PLAIN, 20));
         g.drawString("Movement: " + Player.GetCurrentPlayer().GetMovementMeter(), Window.getWidth2() * 5/8, Window.getHeight2() * 1/8);
         
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial Black", Font.PLAIN, 20));
         g.drawString("Health: " + Player.GetCurrentPlayer().getHealth(), Window.getWidth2() * 7/8, Window.getHeight2() * 1/8);
       }
       


        gOld.drawImage(image, 0, 0, null);
    }
////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 1/framerate;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
        
//        Titlescreen.isActive() = true;
        Titlescreen.reset();
        Player.Clear();
        for(int m = 0; m < 2; m++)
        {
            Player.Create(Maps.getWidth()/2, Maps.getHeight()/2,m);
        }
        Player.Reset();
        Gun.Reset();
        PowerUp.Reset();
        GameEnd = false;
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (Window.animateFirstTime) {
            Window.animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }
            CaveMap = Toolkit.getDefaultToolkit().getImage("./Maps/MapBack.png");

            Player.image = Toolkit.getDefaultToolkit().getImage("./PlayerImage.png");    
            Player.image2 = Toolkit.getDefaultToolkit().getImage("./Rifle.png");   
            Player.image3 = Toolkit.getDefaultToolkit().getImage("./Stick_Figure_Walking.GIF"); 
            Gun.image = Toolkit.getDefaultToolkit().getImage("./bullet.png");  
            PowerUp.image = Toolkit.getDefaultToolkit().getImage("./Ammo_Power_up.png");   
            PowerUp.image2 = Toolkit.getDefaultToolkit().getImage("./Attack_Power_up.png"); 
            PowerUp.image3 = Toolkit.getDefaultToolkit().getImage("./Run_Power_up.png"); 

            Maps.Start();
            reset();
        }
        
        if(!Titlescreen.isActive())
        {
            Player.Animate();
            Gun.Animate(timecount,framerate);
            PowerUp.Animate(timecount,framerate);

            if(Player.GetCurrentPlayer().GetMovementMeter() != 0)
            {
                if (W)
                    Player.CJump();
                if (A)
                    Player.CMove(-2);
                if (D)
                    Player.CMove(2);
            }
            
            if(Player.getTeam1Size() == 0 || Player.getTeam2Size() == 0)
            {
                GameEnd = true;
            }
            
            timecount++;
        }

    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

}

////////////////////////////////////////////////////////////////////////////
class Drawing {
    private static Graphics2D g;
    private static StickfareRemastered mainClassInst;

    public static void setDrawingInfo(Graphics2D _g,StickfareRemastered _mainClassInst) {
        g = _g;
        mainClassInst = _mainClassInst;
    }
////////////////////////////////////////////////////////////////////////////
    public static void drawCircle(int xpos,int ypos,double rot,double xscale,double yscale,Color color)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.setColor(color);
        g.fillOval(-10,-10,20,20);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
////////////////////////////////////////////////////////////////////////////
    public static void drawImage(Image image,int xpos,int ypos,double rot,double xscale,
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

}

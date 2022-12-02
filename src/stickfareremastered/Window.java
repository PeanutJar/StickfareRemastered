package stickfareremastered;
import java.awt.Toolkit;
import javax.swing.JFrame;

class Window {
    private static int XBORDER;
    static final int XBORDER1 = 0;
    static final int YBORDER = 0;
    static final int YTITLE = 31;
 
    static final int MENU_WINDOW_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static final int MENU_WINDOW_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
    
    static StickfareRemastered currentFrame;
    
//    static final double frameRate = 30.0;
    static boolean animateFirstTime = true;
//    private static final int YBORDER = 20;
   
    private static int TOP_BORDER;
    private static int BOTTOM_BORDER;
   
    private static final int WINDOW_BORDER = 0;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER);
    static final int WINDOW_HEIGHT =  WINDOW_BORDER;
    static int xsize = -1;
    static int ysize = -1;
    static int xD;
    static int yD;

/////////////////////////////////////////////////////////////////////////
    
    
    Window(int x, int y){
        
        int StartX = 600;
        int StartY = 338;
        xD = x/StartX;
        yD = y/StartY;
        XBORDER = (x-(StartX*xD))/2;
        TOP_BORDER = (y-(StartY*yD))/2;
        BOTTOM_BORDER = TOP_BORDER;
    }
    public static int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public static int getY(int y) {
//        return (y + YBORDER + YTITLE );
        return (y + TOP_BORDER );
       
    }

    public static int getYNormal(int y) {
//          return (-y + YBORDER + YTITLE + getHeight2());
      return (-y + TOP_BORDER + getHeight2());
       
    }
   
    public static int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public static int getHeight2() {
//        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
        return (ysize - (BOTTOM_BORDER + TOP_BORDER) - WINDOW_BORDER);
    }    
   
}

package stickfareremastered;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Maps{
    private static String[][] Array2D = null;
    private static final File Map = new File("./Maps/MapFront.png");
    private static BufferedImage BI = null;
    
    public static void Start(){
        try{
            BI = ImageIO.read(Map);
        }catch(IOException e){}

        Array2D = new String[BI.getWidth()][BI.getHeight()];
        for(int x = 0; x < BI.getWidth(); x++)
            for(int y = 0; y < BI.getHeight(); y++){
                int z = BI.getRGB(x, y);
                if(z == 0)
                    Array2D[x][y] = "A";
                else if (((z>>16)&0xff) > 200)
                    Array2D[x][y] = "B";
                else if ((z&0xff) > 200)
                    Array2D[x][y] = "C";
                else if (((z>>8)&0xff) > 200)
                    Array2D[x][y] = "You fucked now, boy";
            }
    }
    public static String getGrid(int x, int y){
        return Array2D[x][y];
    }
    public static String[][] getHitbox(){
        return Array2D;
    }
    public static int getWidth(){
        return BI.getWidth();
    }
    public static int getHeight(){
        return BI.getHeight();
    }
}

package stickfareremastered;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> All = new ArrayList<Player>();
    private static ArrayList<Player> Team1 = new ArrayList<Player>();
    private static ArrayList<Player> Team2 = new ArrayList<Player>();
    public static Image image;
    public static Image image2;
    public static Image image3;
    private static StickfareRemastered mainClassInst;
    private static Player Current;
    private static Player Opposite;
    private static Team OppositeTeam;
    private static Team CurrentTeam;
    private int XPos;
    private int YPos;
    private int LXPos;
    private int XSize = 10;
    private int YSize = 10;
    private int FakeXSize = 10;
    private int FakeYSize = 10;
    private int FallSpeed = 0;
    private int MoveSpeed = 0;
    private static int FallCap = 10;
    private static int SpeedCap = 5;
    private Direction direction;
    private Team team;
    private double rotation;
    private int health;
    private int damage;
    private int ammo;
    private boolean isactive;
    private int Superxpos;
    private int Superypos;
    private int MovementMeter;
    private int ResetMovementMeter;
    private static int MinimumPlayers;
    private int healthbar;
    private int StartingPosX;
    private int StartingPosY;

    public enum Direction {
        RIGHT,LEFT
    }
    
    public enum Team {
        TEAM1,TEAM2
    }
    
    Player(int XP,int YP, Direction _direction,Team _team){
        XPos = XP;
        YPos = YP;
        LXPos = XPos;
        StartingPosX = XP;
        StartingPosY = YP;
        direction = _direction;
        team = _team;
        rotation = 0.0;
        health = 100;
        damage = 10;
        ammo = 10;
        isactive = true;
        Superxpos = 0;
        Superypos = 0;
        MovementMeter = 50;
        ResetMovementMeter = 50;
        healthbar = 10;
    }
    
    public static void Create(int XP,int YP, int i){
            
            if(i == 0)
            {
                for(int b = 0; b < 2; b++)
                {
                    Player thing = new Player(XP/2,YP - (b * 50),Direction.RIGHT,Team.TEAM1);
                    All.add(thing);
                    Team1.add(thing);
                }
            }
            else if(i == 1)
            {
                for(int b = 0; b < 2; b++)
                {
                    Player thing = new Player(XP + (XP/2),YP - (b * 50),Direction.LEFT,Team.TEAM2);
                    All.add(thing);
                    Team2.add(thing);
                }
            }
 
    }
    
    public static void Remove(Player ToBeRemoved){
        for(int i = 0; i < All.size();i++)
            if (ToBeRemoved == All.get(i)){
                Current = null;
                All.remove(i);
            }
    }
    
    public static void Reset() {
        MinimumPlayers = 3;
        Current = All.get(0);
        Opposite = All.get(3);
        CurrentTeam = Team.TEAM1;
        OppositeTeam = Team.TEAM2;
        /*
        int randomnum = (int) (Math.random() * 2);
        boolean _true = true;
        if(randomnum == 0)
        {
            while(_true)
            {
                int randomnum2 = (int) (Math.random() * All.size());
                for(int i = 0; i < All.size();i++)
                {
                    if(All.get(randomnum2).team == Team.TEAM1)
                    {
                        Current = All.get(randomnum2);
                        int randomnum3 = (int) (Math.random() * All.size());
                        for(int b = 0; b < All.size();b++)
                        {
                            if(All.get(randomnum3).team == Team.TEAM2)
                            {
                                Opposite = All.get(randomnum3);
                                _true = false;
                            }
                        }
                    }
                }
            }
        }
        else if(randomnum == 1)
        {
            while(_true)
            {
                int randomnum2 = (int) (Math.random() * All.size());
                for(int i = 0; i < All.size();i++)
                {
                    if(All.get(randomnum2).team == Team.TEAM2)
                    {
                        Current = All.get(randomnum2);
                        int randomnum3 = (int) (Math.random() * All.size());
                        for(int b = 0; b < All.size();b++)
                        {
                            if(All.get(randomnum3).team == Team.TEAM1)
                            {
                                Opposite = All.get(randomnum3);
                                _true = false;
                            }
                        }
                    }
                }               
            }
        }
        */
    }
    
    public static void Clear(){
        All.clear();
    }
    
    public static void Animate(){
        
        if(Current.direction == Direction.RIGHT)
        {
            if(Current.rotation < -90 && Current.rotation > -270)
            {
                Current.direction = Direction.LEFT;
            }
        }
        else if(Current.direction == Direction.LEFT)
        {
            if(Current.rotation > 90 && Current.rotation < 270)
            {
                Current.direction = Direction.RIGHT;
            }
        }
        
        if (All.size() > 0){
            for(Player plr: All){
                if(plr.FallSpeed <= 10)
                plr.FallSpeed++;
                if (plr.MoveSpeed > 0)
                    plr.MoveSpeed --;
                else if (plr.MoveSpeed < 0)
                    plr.MoveSpeed++;
                plr.YMove();
                plr.XMove();
                if(plr.getTeam() == Player.GetEnemyTeam())
                    if(plr.health <= 0)
                        plr.setisactive(false);
            }
        }
        //
        RemovePlayer();
        
    }
    private void YMove(){
        if (FallSpeed > 0)
            for(int i = 0; i < FallSpeed; i++){
                boolean Czech = true;
                for(int v = -5; v < XSize/2-1;v++){
                    if (YPos+YSize+1 > 338 || Maps.getGrid(XPos,YPos+YSize+1) == "You fucked now, boy"){
                        XPos = StartingPosX;
                        YPos = StartingPosY;
                        health -=10;
                        healthbar -=1;
                        MoveSpeed = 0;
                        FallSpeed = 0;
                        if(health > 0)
                        {
                            SwitchTurn();
                        }
                        else
                        {
                            isactive = false;
                        }
                        return;
                    }
                    if (Maps.getGrid(XPos+v, YPos+YSize) != "A")
                        Czech = false;
                }
                if (Czech)
                    YPos++;
                else{
                    FallSpeed = 0;
                    return;
                }
            }
        else if (FallSpeed < 0)
            for (int i = 0; i > FallSpeed; i--){
                boolean Czech = true;
                for(int v = -5; v < XSize/2-1;v++){
                    if (YPos-1 < 0){
                        Czech = false;
                        break;
                    }
                    if (Maps.getGrid(XPos+v, YPos-1) == "B")
                        Czech = false;
                }
                if (Czech)
                    YPos--;
                else{
                    FallSpeed = 0;
                    return;
                }
            }
    }
    private void XMove(){
        if (MoveSpeed < 0 && MovementMeter > 0)
            for(int i = 0; i < Math.abs(MoveSpeed);i++){
                boolean Czech = true;
                int Czech2 = 0;
                for(int v = 0; v < YSize;v++){
                    if(XPos-XSize/2-1 < 0){
                        Czech = false;
                        break;
                    }
                    if ((v == YSize-3 || v == YSize-2) && Maps.getGrid(XPos-6, YPos+v) == "B"){
                        Czech2 = v-6;
                        break;
                    }
                    else if (Maps.getGrid(XPos-6,YPos+v) == "B")
                        Czech = false;
                }
                if (Czech2 !=0)
                    YPos-=2;
                if (Czech)
                    XPos--;
                else{
                    MoveSpeed = 0;
                    return;
                }

            }
        else if (MoveSpeed > 0)
            for(int i = 0; i < Math.abs(MoveSpeed);i++){
                boolean Czech = true;
                int Czech2 = 0;
                for(int v = 0; v < YSize;v++){
                    if (XPos+XSize/2+1 > 599){
                        Czech = false;
                        break;
                    }
                    if ((v == YSize-3 || v == YSize-2) && Maps.getGrid(XPos+5, YPos+v) == "B"){
                        Czech2 = v-6;
                    break;
                    }
                    else if (Maps.getGrid(XPos+5,YPos+v) == "B")
                        Czech = false;
                }
                if(Czech2 !=0)
                    YPos-=Czech2;
                if (Czech)
                    XPos++;
                else{
                    MoveSpeed = 0;
                    return;
                }

            }
    }
    public boolean TouchingGround(){
        boolean Czech = false;
        for(int v = -XSize/2; v < XSize/2-1;v++)
            if (Maps.getGrid(XPos+v, YPos+XSize) != "A")
                Czech = true;
        return Czech;
    }
    public static void Draw(Graphics2D g, int xD, int yD){
        int value = (int)(Math.pow(10, 10));
        
        for(Player plr: All){
            //if(plr.isactive)
            {
                if(plr == Current)
                {
                    g.setColor(Color.green);
                    g.fillRect(Window.getX(0)+xD*((plr.XPos)-plr.XSize/2), Window.getY(0)+yD*plr.getyPos() - 30, 30, 10);   
                }
                if(plr.team == Team.TEAM1)
                {
                    g.setColor(Color.blue);
                    g.fillRect(Window.getX(0)+xD*((plr.XPos)-plr.XSize/2) - plr.healthbar, Window.getY(0)+yD*plr.getyPos() - 15, (plr.getWidth()*xD * plr.healthbar) * 1/5, plr.getHeight()*yD/2);   
                }
                else if(plr.team == Team.TEAM2)
                {
                    g.setColor(Color.red);
                    g.fillRect(Window.getX(0)+xD*((plr.XPos)-plr.XSize/2) - plr.healthbar, Window.getY(0)+yD*plr.getyPos() - 15, (plr.getWidth()*xD * plr.healthbar) * 1/5, plr.getHeight()*yD/2);                  
                }
                
                double misk = Maps.getWidth();
                double mickey = (Window.MENU_WINDOW_HEIGHT/misk)/15;
                if(plr.direction == Direction.RIGHT)
                {                                 
                    //Drawing.drawImage(image2,Window.getX(0)+xD*((plr.XPos)-plr.XSize/2),Window.getY(0)+yD*plr.getyPos(),plr.rotation,0.06,0.06 );         
                    //g.drawImage(image2,-Window.getX(-160)+xD*((plr.XPos)-plr.XSize/2),-Window.getY(-100)+yD*plr.getyPos(), plr.getWidth()*xD * 2,plr.getHeight()*yD,mainClassInst);
                    if(plr.MoveSpeed == 0)
                    {
                        drawImage(g,image,Window.getX(0)+xD*((plr.XPos)-plr.XSize/2),Window.getY(10)+yD*plr.getyPos(),0.0,mickey,mickey );
                    }
                    else if(plr.MoveSpeed != 0)
                    {
                        drawImage(g,image3,Window.getX(10)+xD*((plr.XPos)-plr.XSize/2),Window.getY(10)+yD*plr.getyPos(),0.0,mickey,mickey );
                    }
                    drawImage(g,image2,Window.getX(10)+xD*((plr.XPos)-plr.XSize/2),Window.getY(10)+yD*plr.getyPos(),plr.rotation,mickey*1.2,mickey*1.2 );
                    plr.setSuperxPos(Window.getX(10)+xD*((plr.XPos)-plr.XSize/2));
                    plr.setSuperyPos(Window.getY(10)+yD*plr.getyPos());
                   
                }
                else if(plr.direction == Direction.LEFT)
                {
                   //Drawing.drawImage(image2,Window.getX(0)+xD*((plr.XPos)-plr.XSize/2),Window.getY(0)+yD*plr.getyPos(),plr.rotation,-0.06,0.06 );
                    if(plr.MoveSpeed == 0)
                    {
                        drawImage(g,image,Window.getX(10)+xD*((plr.XPos)-plr.XSize/2),Window.getY(10)+yD*plr.getyPos(),0.0,-mickey,mickey );
                    }
                    else if(plr.MoveSpeed != 0)
                    {
                        drawImage(g,image3,Window.getX(0)+xD*((plr.XPos)-plr.XSize/2),Window.getY(10)+yD*plr.getyPos(),0.0,-mickey,mickey );
                    }
                   drawImage(g,image2,Window.getX(0)+xD*((plr.XPos)-plr.XSize/2),Window.getY(10)+yD*plr.getyPos(),plr.rotation,-mickey*1.2,mickey*1.2 );
                   plr.setSuperxPos(Window.getX(0)+xD*((plr.XPos)-plr.XSize/2));
                   plr.setSuperyPos(Window.getY(0)+yD*plr.getyPos());
                   
                }

            }

        }
        
    }
    
    public static void drawImage(Graphics2D g,Image image,int xpos,int ypos,double rot,double xscale,
            double yscale) {
        int width = image.getWidth(mainClassInst);
        System.out.println(width);
        int height = image.getHeight(mainClassInst);
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.drawImage(image,-width/2,-height/2,
        width,height,mainClassInst);
        g.scale( 1.0/xscale,1.0/yscale);
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    
    public static String GetWinningPlayerString() {
        if(Team2.size() <= 0)
        {
            return("Team 1");
        }
        else if(Team1.size() <= 0)
        {
            return("Team 2");
        }
        return(null);
    }
    
    public static Player GetCurrentPlayer() {
        return(Current);
    }
    
    public static Team GetEnemyTeam() {
        return(OppositeTeam);
    }
    
    public static Player GetEnemyPlayer() {
        return(Opposite);
    }
    
    public static Team GetCurrentTeam() {
        return(CurrentTeam);
    }
    
    public boolean getisactive() {
        return(isactive);
    }
    
    public void setisactive(boolean _boolean) {
        isactive = _boolean;
    }
    
    public int getHealth() {
        return(health);
    }
    
    public double getRotation() {
        return(rotation);
    }
    
    public int GetMovementMeter() {
        return(MovementMeter);
    }
    
    public void ChangeMovementMeter(int _movementmeterchange) {
        ResetMovementMeter += _movementmeterchange;
        MovementMeter += _movementmeterchange;
        
    }
    
    public int getAmmo() {
        return(ammo);
    }
    
    public void ChangeAmmo(int _changeammo) {
        ammo += _changeammo;
    }
     
    public static void RemovePlayer() {
        for(int i = 0; i < Team1.size(); i++)
        {
            if(!Team1.get(i).isactive)
            {
                Team1.remove(i);
                i--;
            }
        }
        for(int i = 0; i < Team2.size(); i++)
        {
            if(!Team2.get(i).isactive)
            {
                Team2.remove(i);
                i--;
            }
        }
        for(int i = 0; i < All.size(); i++)
        {
            if(!All.get(i).isactive)
            {
                All.remove(i);
                SwitchTurn();
                i--;
            }
        }
    } 
    
    public void ChangeHealth() {
        health -= Current.damage;
        healthbar -= (Current.damage/10);
    }
    
    public void ChangeDamage(int _damagechange) {
        damage += _damagechange;
    }
    
    public int getDamage() {
        return(damage);
    }
    
    public void Rotate(double _rotate) {
        rotation = _rotate;
    }
    
    public Direction getDirection() {
        return(direction);
    }
 
    public Team getTeam() {
        return(team);
    }  
    
    public static ArrayList<Player> getPlayers(){
        return All;
    }
    
    public static int getTeam1Size(){
        return Team1.size();
    }
    
    public static int getTeam2Size(){
        return Team2.size();
    }
    
    public static void SwitchTurn() {
        if(Player.getTeam1Size() == 0 || Player.getTeam2Size() == 0)
        {
            return;
        }
        Team ptr1 = OppositeTeam;
        OppositeTeam = CurrentTeam;
        CurrentTeam = ptr1;
        
        if(Current.team == Team.TEAM1)
        {
            Player ptr2 = Opposite;
            if(All.size() >= MinimumPlayers)
            {
                boolean _true = true;
                while(_true)
                {
                    int randomnum2 = (int) (Math.random() * All.size());
                    int randomnum3 = (int) (Math.random() * All.size());             
                    if(All.get(randomnum2).team == Team.TEAM1)
                    {
                        Opposite = All.get(randomnum2);
                        if(All.get(randomnum3).team == Team.TEAM2)
                        {
                            Current = All.get(randomnum3);
                            _true = false;
                        }
                    }        
                }
            }
            else
            {
                Opposite = All.get(0);
                Current = All.get(1);
            }
        }
        else if(Current.team == Team.TEAM2)
        {
            Player ptr2 = Opposite;
            if(All.size() >= MinimumPlayers)
            {
                boolean _true = true;
                while(_true)
                {
                    int randomnum2 = (int) (Math.random() * All.size());
                    int randomnum3 = (int) (Math.random() * All.size());             
                    if(All.get(randomnum2).team == Team.TEAM2)
                    {
                        Opposite = All.get(randomnum2);
                        if(All.get(randomnum3).team == Team.TEAM1)
                        {
                            Current = All.get(randomnum3);
                            _true = false;
                        }
                    }        
                }
            }
            else
            {
                Opposite = All.get(1);
                Current = All.get(0);
            }
        }
        Current.MovementMeter = Current.ResetMovementMeter;

    } 

    public static void CJump(){
        if (Current.TouchingGround())
        {
            Current.MovementMeter-=5;
            Current.FallSpeed = -10;
        }
    }
    public static void CMove(int xPlus){
        if(Current.XPos != Current.LXPos){
            Current.MovementMeter--;
            Current.LXPos = Current.XPos;
        }
        if(xPlus > 0)
            if (Current.MoveSpeed > SpeedCap)
                return;
        if(xPlus < 0)
            if (Current.MoveSpeed < -(SpeedCap))
                return;
        Current.MoveSpeed+=xPlus;
    }
    public int getWidth(){
        return XSize;
    }
    public int getHeight(){
        return YSize;
    }
    public int getxPos(){
        return XPos;
    }
    
     public int getMoveSpeed(){
        return MoveSpeed;
    }
    
    public void setSuperxPos(int _xpos){
        Superxpos = _xpos;
    }
    
    public void setSuperyPos(int _ypos){
        Superypos = _ypos;
    }
    
    public int getSuperxPos(){
        return Superxpos;
    }
    
    public int getSuperyPos(){
        return Superypos;
    }
     
    public int getyPos(){
        return YPos;
    }
}

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;
    static String view[][] = new String[100][100];
    static int viewRange = 4;
    public Rectangle(int xPos,int yPos,int size,String tileType){
        x = xPos;
        y = yPos;
        width = size;
        height = size;
        addToView(tileType);
    }
    public Rectangle(int xPos,int yPos,int size){
        x = xPos;
        y = yPos;
        width = size;
        height = size;
        addToView("X");
    }
    public Rectangle(int xPos,int yPos,int rectW,int rectH,String tileType){
        x = xPos;
        y = yPos;
        width = rectW;
        height = rectH;
        addToView(tileType);
    }
    public Rectangle(int xPos,int yPos,int rectW,int rectH){
        x = xPos;
        y = yPos;
        width = rectW;
        height = rectH;
        addToView("X");
    }
    public void setViewRange(int view){
        viewRange = view;
    }
    public void addToView(String tile){
        for(int i = y;i<y+height;i++){
            for(int j = x;j<x+width;j++){
                /*Something with tile passability is causing problems with
                 just setting the tile to the parameter, so i'm 
                 going to use the bloated but workable solution of
                 a bunch of ifs for now.
                 FIX IT LATER THOUGH!!
                 */
                if(tile.equals("X")){
                    view[i][j] = "X ";
                }else if(tile.equals("&")){
                    view[i][j] = "& ";
                }else if(tile.equals("#")){
                    view[i][j] = "# ";
                }
            }
        }
    }
    public static void changeView(int i,int j,String update){
        view[j][i] = update;
    }
    public static String getViewAtPos(int j, int i){
        return view[i][j];
    }
    public static void render(){
        //Create a 'rendering array' which is the same exept for the enemies being placed on top
        String render[][] = new String[100][100];
        for(int i = 0;i<view.length;i++){
            for(int j = 0;j<view[0].length;j++){
                render[i][j]=view[i][j];
                for(Enemy e:main.enemies){
                    if(e.getY()==i&&e.getX()==j){
                        render[i][j] = "E ";
                    }
                }
            }
        }
         for(int i = 0;i<render.length;i++){
            for(int j = 0;j<render[0].length;j++){
                if(i>Player.getY()-viewRange&&i<Player.getY()+viewRange&&j>Player.getX()-viewRange&&j<Player.getX()+viewRange){
                    if(i==Player.getY()&&j==Player.getX()){
                        System.out.print("@ ");
                    }else{
                System.out.print(render[i][j]);}
                }
            }
            if(i>Player.getY()-viewRange&&i<Player.getY()+viewRange){
            System.out.println("");}
        }
    }
    public static void fillViewWithBlank(){
        for(int i = 0;i<view.length;i++){
            for(int j = 0;j<view[0].length;j++){
                view[i][j] = "  ";
            }
        }
    }
    public static String getTile(int i, int j){
        return view[j][i];
    }
    public static boolean isPassable(int i, int j){
        if(view[j][i] == "X "||view[j][i] == "& "||view[j][i]=="# "||view[j][i]=="E "||view[j][i]=="$ "||view[j][i]=="= "){
            return true;
        }
        return false;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

}
public class Enemy {
    private String name;
    private int health;
    private int strength;
    private int gold;
    private int x;
    private int y;
    public Enemy(String n,int X,int Y, int h, int s, int g){
        name = n;
        health = h;
        strength = s;
        gold = g;
        x = X;
        y = Y;
    }
    public Enemy(String n,int X, int Y, int h, int s){
        name = n;
        health = h;
        strength = s;
        gold = 10;
        x = X;
        y = Y;
    }
    public void move(){
        int choice = (int)(Math.random()*7+1);
        switch(choice){
            case 1:
                if(x+1<100&&Rectangle.isPassable(x+1,y)){
                    x++;
                }
                break;
            case 2:
                if(x-1>0&&Rectangle.isPassable(x-1,y)){
                    x--;
                }
                break;
            case 3:
                if(y+1<100&&Rectangle.isPassable(x,y+1)){
                    y++;
                }
                break;
            case 4:
                if(y-1>0&&Rectangle.isPassable(x,y-1)){
                    y--;
                }
                break;
            case 5:break;
            case 6:break;
            case 7:break;
        }
    }
    public String getName(){
        return name;
    }
    public int getHealth(){
        return health;
    }    
    public int getStrength(){
        return strength;
    }
    public int getGold(){
        return gold;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
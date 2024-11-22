import java.util.*;
public class Player {
    static int playerX;
    static int playerY;
    private double health;
    private double gold;
    private double strength;
    private int enemyHp;
    private double defense;
    Scanner input = new Scanner(System.in);
    public Player(int x,int y,double hp,double g,double str){
        playerX = x;
        playerY = y;
        health = hp;
        gold = g;
        strength = str;
        defense = 1;
    }
    public void moveUp(){
        playerY--;
    }
    public void moveDown(){
        playerY++;
    }
    public void moveLeft(){
        playerX--;
    }
    public void moveRight(){
        playerX++;
    }
    public static int getX(){
        return playerX;
    }
    public static int getY(){
        return playerY;
    }
    public static void setPos(int x, int y){
        playerX=x;
        playerY=y;
    }
    public void setHealth(double health){
        this.health = health;
    }
    public double getHealth(){
        return health;
    }
    public double getGold(){
        return gold;
    }
    public void changeDefense(double change){
        defense+=change;
    }
    public double getDefense(){
        return defense;
    }
    public void changeGold(double g){
        gold+=g;
    }
    public void setStrength(double strength){
        this.strength = strength;
    }
    public double getStrength(){
        return strength;
    }
    public int combat(Enemy e){
        boolean inCombat = true;
        enemyHp = e.getHealth();
        int strengthMult = 1;
        double startDefense = defense;
        while(inCombat){
            System.out.print("\033[H\033[2J");
            //Data and user interaction options
            System.out.println("You're in combat with a "+e.getName());
            System.out.println("The "+e.getName()+" has "+enemyHp+" health");
            System.out.println("You have "+health+" health");
            System.out.println("You will attack for "+(strength*strengthMult*3)+" damage");
            System.out.println("the enemy will attack for "+(e.getStrength()/defense)+" damage");
            System.out.println("Type 1 to attack");
            System.out.println("Type 2 to defend and build up power");
            System.out.println("Type 3 to flee");
            //Takes a value as input
            String val = input.nextLine();
            //Checks which option you picked
            if(val.equals("1")){
                //Damages the enemy and damages you back
                enemyHp -= strength*strengthMult*3;
                //Resets any built up strength
                strengthMult = 1;
            }else if(val.equals("2")){
                //Increases your defense and strength
                defense++;
                strengthMult++;
                //Damages you as the enemy attacks
            }else if(val.equals("3")){
                //Makes the player exit combat
                inCombat = false;
            }
            if(enemyHp<=0){
                //Checks if the enemy is dead, and if so exits combat and gives a reward
                inCombat = false;
                gold += e.getGold();
                System.out.println("You killed the "+e.getName());
                defense = startDefense;
                return 1;
            }else if(health<=0){
                defense=startDefense;
                return -1;
            }else{
                health-=e.getStrength()/defense;
            }
             
        }
        defense=startDefense;
        return 0;
    }
}
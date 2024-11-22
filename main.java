import java.util.*;
public class main
{   
    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    static ArrayList<Rectangle> features = new ArrayList<Rectangle>();
    static ArrayList<Item> inventory = new ArrayList<Item>();
    static boolean running = true;
    static int stairs = 11;
    static int enemyCount = 90;
    static int rooms = 50;
    static int shops = 20;
    static Player player = new Player(8,8,15.0,0.0,1.0);
    public static void main(String[] args)
    {   
        Scanner input = new Scanner(System.in);
        Rectangle.fillViewWithBlank();
        //Creating a start rectangle
        features.add(new Rectangle(4,7,9,20));
        generateFloor(rooms);
        populateFloor(enemyCount,shops,stairs);
        loadingAndMenu();
        while(running)
        {
            System.out.print("\033[H\033[2J");
            Rectangle.render();
            System.out.println("|Xx-----------------------------xX|");
            System.out.println("q - save and quit");
            System.out.println("WASD to move");
            System.out.println("E to access inventory(see stats, equip items,etc)");
            System.out.println("(Hit enter after each keypress)");
            char inputVal = input.next().charAt(0);;
            if(inputVal=='q')
            {
                running = false;
            }else if(inputVal == 'w'&&Rectangle.isPassable(player.getX(),player.getY()-1)){
                player.moveUp();
            }else if (inputVal == 's'&&Rectangle.isPassable(player.getX(),player.getY()+1)){
                player.moveDown();
            }else if(inputVal == 'd'&&Rectangle.isPassable(player.getX()+1,player.getY())){
                player.moveRight();
            }else if(inputVal == 'a'&&Rectangle.isPassable(player.getX()-1,player.getY())){
                player.moveLeft();
            }else if(inputVal == 'e'){
                inventory();
            }if(inputVal!='r'){
                if(Rectangle.getViewAtPos(player.getX(),player.getY()).equals("$ ")){
                    shop();
                }
                if(Rectangle.getViewAtPos(player.getX(),player.getY()).equals("= ")){
                    stairs();
                }
            }
            for(int i = 0;i<enemies.size();i++){
                enemies.get(i).move();
                if(enemies.get(i).getX() == player.getX() && enemies.get(i).getY() == player.getY()){
                    int combatSuccess = player.combat(enemies.get(i));
                    if(combatSuccess == 1){
                        enemies.remove(i);
                        i--;
                    }else if(combatSuccess == -1){
                        running = false;
                        System.out.println("\033[H\033[2J");
                        System.out.println("GAME OVER");
                    }
                }
            }
        }
    }
    public static void loadingAndMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println("\033[H\033[2J");
        System.out.println("Welcome to the last fortress of evil,\na game set in the world of the Tiunu-rounepalo");
        System.out.println("1.New game");
        System.out.println("2.How to play");
        String temp = input.nextLine();
        if(temp.contains("1")){
            System.out.print("\033[H\033[2J");
            for(int i = 0;i<4;i++){
                System.out.print("loading");
                for(int j = 0;j<3;j++){
                    sleep(5);
                    System.out.print(".");
                }
                sleep(5);
                System.out.print("\033[H\033[2J");
                sleep(10);
            }
            return;
        }else if(temp.contains("2")){
            System.out.println("Controls:");
            System.out.println("-All inputs have prompts on screen.");
            System.out.println("-You MUST hit enter between each keypress for the program to detect an input\n");
            System.out.println("Mechanics:");
            System.out.println("-Combat:");
            System.out.println("    --When you enter the same tile as an enemy, you enter combat");
            System.out.println("    --While in combat, the combat menu is visible, telling you of possible combat actions you could take");
            System.out.println("    --Enemies will attack you after you take your action, if they still live");
            System.out.println("    --Fleeing combat allows enemies to heal to full.");
            System.out.println("-Items:");
            System.out.println("    --You can buy items at the shop, and use them to upgrade your stats");
            System.out.println("    --You gain money to buy items by defeating enemies");
            System.out.println("-OVERHEAL:");
            System.out.println("    --Healing with potions or elixirs allows you to heal beyond your starting health, allowing you to have a larger health pool");
            System.out.println("-Stairwells and the boss:");
            System.out.println("    --Stairwells allow you to continue through the dungeon to a lower floor. There are a total of three floors and a boss.");
            System.out.println("KEY:");
            System.out.println("X--Travelable tile\n&--Travelable tile\n#--Travelable tile\nE--Enemy\n$--Shop\n=--Stairwell");
            temp = input.nextLine();
            loadingAndMenu();
        }
    }
    public static void sleep(int seconds){
        try {
            Thread.sleep(seconds * 100);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
    public static void generateFloor(int roomCount){
        //Create a numbeer of rooms equal to the parameter
        for(int i = 0;i<roomCount;i++){
            //Create a room of a random type
            int roomStyle = (int) (Math.random()*3);
            if(roomStyle ==0){
                features.add(new Rectangle((int)(Math.random()*89+1),(int)(Math.random()*89+1),(int)(Math.random()*10+2),(int)(Math.random()*10+2)));
            }else if (roomStyle == 1){
                features.add(new Rectangle((int)(Math.random()*89+1),(int)(Math.random()*89+1),(int)(Math.random()*10+2),(int)(Math.random()*10+2),"&"));
            }else if (roomStyle == 2){
               features.add(new Rectangle((int)(Math.random()*89+1),(int)(Math.random()*89+1),(int)(Math.random()*10+2),(int)(Math.random()*10+2),"#"));
            }
        }
        int hallGenMax = features.size()-1;
        for(int i = 0;i<hallGenMax;i++){
            //Definitions to make code more readable
            Rectangle thisRect = features.get(i);
            Rectangle nextRect = features.get(i+1);
            //Check if verticle is possible and if so creates a hall
            if(thisRect.getX()+(thisRect.getWidth()/2)>=nextRect.getX()&&thisRect.getX()+(thisRect.getWidth()/2)<=nextRect.getX()+(nextRect.getWidth()/2)){
                if(thisRect.getY()+thisRect.getHeight()<nextRect.getY()){
                    features.add(new Rectangle(thisRect.getX()+(thisRect.getWidth()/2),thisRect.getY()+thisRect.getHeight(),1,(nextRect.getY()-(thisRect.getY()+thisRect.getHeight())+1)));
                }else if(nextRect.getY()+nextRect.getHeight()<thisRect.getY()){
                    features.add(new Rectangle(thisRect.getX()+(thisRect.getWidth()/2),nextRect.getY()+nextRect.getHeight(),1,(thisRect.getY()-(nextRect.getY()+nextRect.getHeight())+1)));
                }
            }
            //Check if horizontal is possible and if so creates a hall
            else if(thisRect.getY()+(thisRect.getHeight()/2)>=nextRect.getY()&&thisRect.getY()+(thisRect.getHeight()/2)<=nextRect.getY()+(nextRect.getHeight()/2)){
                if(thisRect.getX()+thisRect.getWidth()<nextRect.getX()){
                    features.add(new Rectangle(thisRect.getX()+thisRect.getWidth(),thisRect.getY()+(thisRect.getHeight()/2),(nextRect.getX()-(thisRect.getX()+thisRect.getWidth())+1),1));
                }else if(nextRect.getX()+nextRect.getWidth()<thisRect.getX()){
                    features.add(new Rectangle(nextRect.getX()+nextRect.getWidth(),thisRect.getY()+(thisRect.getHeight()/2),(thisRect.getX()-(nextRect.getX()+nextRect.getWidth())+1),1));
                }
            }
            //otherwise create a diagonal
            else{
                //Start with veritcle corridor
                if(thisRect.getY()+thisRect.getHeight()<nextRect.getY()){
                    features.add(new Rectangle(thisRect.getX()+(thisRect.getWidth()/2),thisRect.getY()+thisRect.getHeight(),1,(nextRect.getY()-(thisRect.getY()+thisRect.getHeight())+1)));
                }else if(nextRect.getY()+nextRect.getHeight()<thisRect.getY()){
                    features.add(new Rectangle(thisRect.getX()+(thisRect.getWidth()/2),nextRect.getY()+nextRect.getHeight(),1,(thisRect.getY()-(nextRect.getY()+nextRect.getHeight())+1)));
                }
                Rectangle hall1 = features.get(features.size()-1);
                //Create a horizontal one based on the coordinates
                if(hall1.getX()+hall1.getWidth()<nextRect.getX()){
                    features.add(new Rectangle(hall1.getX()+hall1.getWidth(),hall1.getY()+(hall1.getHeight()/2),(nextRect.getX()-(hall1.getX()+hall1.getWidth())+1),1));
                }else if(nextRect.getX()+nextRect.getWidth()<hall1.getX()){
                    features.add(new Rectangle(nextRect.getX()+nextRect.getWidth(),hall1.getY()+(hall1.getHeight()/2),(hall1.getX()-(nextRect.getX()+nextRect.getWidth())+1),1));
                }
            }
        }
        
    }
    public static void populateFloor(int enemyCount,int shopCount, int stairCount){
            //Enemy creation
            String[] names = {"Wolf","Orc","Goblin","Slime"};
            for(int i = 0;i<enemyCount;i++){
                int xToBe = (int)(Math.random()*99+1);
                int yToBe = (int)(Math.random()*99+1);
                if(Rectangle.isPassable(xToBe,yToBe)){
                    enemies.add(new Enemy(names[(int)(Math.random()*names.length)],xToBe,yToBe,(int)(Math.random()*5+1),(int)(Math.random()*5+1)));
                }else{
                    i--;
                }
            }
            //Shop creation
            for(int i = 0;i<shopCount;i++){
                int xToBe = (int)(Math.random()*99+1);
                int yToBe = (int)(Math.random()*99+1);
                if(Rectangle.isPassable(xToBe,yToBe)){
                   Rectangle.changeView(xToBe,yToBe,"$ "); 
                }else{
                    i--;
                }
            }
            //Stair creation
            for(int i = 0;i<stairCount;i++){
                int xToBe = (int)(Math.random()*99+1);
                int yToBe = (int)(Math.random()*99+1);
                if(Rectangle.isPassable(xToBe,yToBe)){
                   Rectangle.changeView(xToBe,yToBe,"= ");
                }else{
                    i--;
                }
            }
        }
    public static void shop(){
        Scanner input = new Scanner(System.in);
        System.out.println("\033[H\033[2J");
        System.out.println("Welcome to my shop,\nwe're now an official partner of the dungeon shop co-op");
        System.out.println("You have: "+player.getGold()+" gold");
        System.out.println("Press 1 to buy a health potion(+5)(10 gold)");
        System.out.println("Press 2 to upgrade your weapon(+9)(15 gold)");
        System.out.println("Press 3 to buy a health elixer(+12)(20 gold)");
        System.out.println("Press 4 to buy an armor upgrade(+1)(25 gold)");
        System.out.println("We may have more options soon");
        System.out.println("(Press 'r' to continue)");
        String choice = input.nextLine();
        if(choice.equals("1")&&player.getGold()>=10){
            inventory.add(new Item("Health potion",1));
            player.changeGold(-10);
        }else if(choice.equals("2")&&player.getGold()>=15){
            inventory.add(new Item("Big sword",2,3));
            player.changeGold(-15);
        }else if(choice.equals("3")&&player.getGold()>=20){
            inventory.add(new Item("Health elixer",1,12));
            player.changeGold(-20);
        }else if(choice.equals("4")&&player.getGold()>=25){
            inventory.add(new Item("Armor upgrade",3,1));
            player.changeGold(-25);
        }
        if(!choice.equals("r")){
            shop();
        }
    }
    public static void stairs(){
        Scanner input = new Scanner(System.in);
        System.out.println("\033[H\033[2J");
        System.out.println("You have found a set of stairs going down");
        System.out.println("Press 'c' to continue down the stairs to the next floor\nor press 'r' to stay on this floor");
        char inputVal = input.next().charAt(0);
        if(inputVal=='c'){
            rooms+=10;
            enemyCount+=10;
            shops-=2;
            stairs--;
            if(stairs-8>0){
                Rectangle.fillViewWithBlank();
                while(features.size()>0){
                    features.remove(0);
                }
                player.setPos(8,8);
                features.add(new Rectangle(Player.getX()-5,Player.getY()-5,10));
                generateFloor(rooms);
                populateFloor(enemyCount,shops,stairs);
            }else{
                boss();
            }
        }else{
            return;
        }
    }
    public static void inventory(){
        boolean inMap = false;
        while(true){
            Scanner input = new Scanner(System.in);
            System.out.println("\033[H\033[2J");
            System.out.println("Coordinates: "+player.getX()+","+player.getY());
            System.out.println("Health: "+player.getHealth());
            System.out.println("Damage: "+(player.getStrength()*3));
            System.out.println("Money: "+player.getGold());
            /*Switching to a more basic list inventory*/
            for(int i = 0;i<inventory.size();i++){
                System.out.println(i+1+": "+inventory.get(i));
            }
            System.out.println("Type the number of the item to use it");
            System.out.println("(Type '0' to continue)");
            String in = input.nextLine();
            if(in.equals("0")){
                break;
            }else if(in.equals("m")&&inMap){
                
            }else if(in.equals("m")&&!inMap){
                
            }else if(canConvertToInt(in)){
                if(inventory.size()>=Integer.valueOf(in)&&inventory.get(Integer.valueOf(in)-1)!=null){
                    inventory.remove(Integer.valueOf(in)-1).use();
                }
            }
        }
    }
    public static void boss(){
        Scanner input = new Scanner(System.in);
        System.out.println("\033[H\033[2J");
        System.out.println("You have reached the boss chamber");
        System.out.println("         	             ______________ ");
        System.out.println("                        ,===:'.,            `-._");
        System.out.println("                       `:.`---.__         `-._  `.");
        System.out.println(" 			        `:.     `--.      `.");
        System.out.println("                                 \\.        `.       `. ");
        System.out.println("                         (,,(,    \\.         `.   ____,-`.,");
        System.out.println("                      (,'     `/   \\.   ,--.___`.' ");
        System.out.println("                  ,  ,'  ,--.  `,   \\.;'         ` ");
        System.out.println("                   `{D, {    \\  :    \\; ");
        System.out.println("                     V,,'    /  /    //  ");
        System.out.println("                     j;;    /  ,' ,-//.    ,---.      , ");
        System.out.println("                     \\;'   /  ,' /  _  \\  /  _  \\   ,'/  ");
        System.out.println("                           \\   `'  / \\  `'  / \\  `.' /  ");
        System.out.println("                            `.___,'   `.__,'   `.__,'  ");
        System.out.println("\n(Press any key to continue)");
        String test = input.nextLine();
        Enemy boss = new Enemy("The last dragon of ro-tiunu",0,0,150,18,99999);
        int result = player.combat(boss);
        while(result==0){
            System.out.println("Your attempt to escape gives the dragon time to heal,\nit quickly chases you down to resume combat.");
            test=input.nextLine();
            result = player.combat(boss);
        }
        if(result == -1){
            System.out.println("You were defeated by the dragon - GAME OVER");
            running = false;
        }else if(result == 1){
            System.out.println("\033[H\033[2J");
            System.out.println("You defeated the dragon, you win.\n");
            running = false;
            sleep(30);
            System.out.println("Programmed by Ethan Scholnick\n");
            sleep(30);
            System.out.println("Game design by Ethan Scholnick\n");
            sleep(30);
            System.out.println("Dragon ascii art shown before the boss by John VanderZwaag\n");
            sleep(30);
            System.out.println("All other art by Ethan Scholnick\n");
            sleep(30);
            System.out.println("\033[H\033[2J");
            System.out.println("___________.__                   __");
            System.out.println("\\__    ___/|  |__ _____    ____ |  | __    ___.__. ____  __ __ ");
            System.out.println("  |    |   |  |  \\\\__  \\  /    \\|  |/ /   <   |  |/  _ \\|  |  \\");
            System.out.println("  |    |   |   Y  \\/ __ \\|   |  \\    <     \\___  (  <_> )  |  /");
            System.out.println("  |____|   |___|  (____  /___|  /__|_ \\    / ____|\\____/|____/");
            System.out.println("                \\/     \\/     \\/     \\/    \\/");
            System.out.println("  _____                       .__                .__");
            System.out.println("_/ ____\\___________    ______ |  | _____  ___.__.|__| ____    ____");
            System.out.println("\\   __\\/  _ \\_  __ \\   \\____ \\|  | \\__  \\<   |  ||  |/    \\  / ___\\");
            System.out.println(" |  | (  <_> )  | \\/   |  |_> >  |__/ __ \\\\___  ||  |   |  \\/ /_/  >");
            System.out.println(" |__|  \\____/|__|      |   __/|____(____  / ____||__|___|  /\\___  /");
            System.out.println("                       |__|             \\/\\/             \\//_____/");
            sleep(100);
        }
        
    }
    public static Player getPlayer(){
        return player;
    }
    public static boolean canConvertToInt(String str){
          try{
            int number = Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
    }
}
/*
TYPE DICTIONARY:
1 - Healing item
2 - Weapon
3 - Spell scroll
4 - Other
*/
import java.util.Scanner;
public class Item {
    private String name;
    private int type;
    private int strength;
    //Constructor for setting up Items manualy
    public Item(String name, int type, int strength){
        this.name = name;
        this.type = type;
        this.strength = strength;
    }
    //A constructor with a default strength
    public Item(String name, int type){
        this.name = name;
        this.type = type;
        //Default strength is five
        this.strength = 5;
    }
    public void use(){
        Scanner input = new Scanner(System.in);
        Player player = main.getPlayer();
        switch(type){
            case 1:
                System.out.println("You used a healing item");
                System.out.println("You were healed up to "+strength+" hp, or your max if you would go beyond that");
                player.setHealth(player.getHealth()+strength);
                break;
            case 2:
                System.out.println("You equipped a weapon.");
                System.out.println("This means your damage is at"+(player.getStrength()+strength)+" damage per attack");
                player.setStrength(player.getStrength()+strength);
                break;
            case 3:
                System.out.println("You upgraded your armor");
                player.changeDefense(1);
                System.out.println("This means you will take 1/"+player.getDefense()+" damage");
                break;
            case 4: 
                break;
            default: break;
        }
        String temp = input.nextLine();
    }
    public String toString(){
       return name+" of strength "+strength;
    }
}
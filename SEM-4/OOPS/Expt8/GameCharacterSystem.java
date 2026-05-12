/**
 * Experiment 1: Game Character System
 * Demonstrates abstract classes with abstract methods and concrete methods
 */

// Abstract base class for game characters
abstract class Character {
    private String name;
    private int health;
    private int attackPower;

    // Constructor
    public Character(String name) {
        this.name = name;
        this.health = 100;
        this.attackPower = 10;
    }

    // Abstract methods - must be implemented by subclasses
    public abstract void attack();
    public abstract void defend();

    // Concrete method - shared implementation
    public void displayStats() {
        System.out.println("=== Character Stats ===");
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Attack Power: " + attackPower);
    }

    // Getters and setters for encapsulation
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }
}

// Warrior subclass
class Warrior extends Character {
    private int armor;

    public Warrior(String name) {
        super(name);
        this.armor = 50;
        setAttackPower(25);
    }

    @Override
    public void attack() {
        System.out.println(getName() + " swings their sword with mighty force!");
        System.out.println(getName() + " attacks for " + getAttackPower() + " damage!");
    }

    @Override
    public void defend() {
        System.out.println(getName() + " raises their shield, blocking " + armor + " damage!");
    }

    public int getArmor() {
        return armor;
    }
}

// Mage subclass
class Mage extends Character {
    private int mana;

    public Mage(String name) {
        super(name);
        this.mana = 100;
        setAttackPower(35);
    }

    @Override
    public void attack() {
        System.out.println(getName() + " casts a powerful fireball!");
        System.out.println(getName() + " deals " + getAttackPower() + " magical damage!");
    }

    @Override
    public void defend() {
        System.out.println(getName() + " creates a magical barrier!");
    }

    public void castSpell(String spellName) {
        if (mana >= 20) {
            mana -= 20;
            System.out.println(getName() + " casts " + spellName + "! (Mana left: " + mana + ")");
        } else {
            System.out.println(getName() + " doesn't have enough mana!");
        }
    }

    public int getMana() {
        return mana;
    }
}

// Main class
public class GameCharacterSystem {
    public static void main(String[] args) {
        System.out.println("=== GAME CHARACTER SYSTEM ===\n");

        // Creating Warrior object
        Warrior warrior = new Warrior("Aragorn");
        System.out.println("--- Warrior ---");
        warrior.displayStats();
        warrior.attack();
        warrior.defend();

        System.out.println();

        // Creating Mage object
        Mage mage = new Mage("Gandalf");
        System.out.println("--- Mage ---");
        mage.displayStats();
        mage.attack();
        mage.defend();
        mage.castSpell("Lightning Bolt");
    }
}

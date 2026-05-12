// Q3: Dynamic Method Dispatch - Game Characters
// Demonstrates runtime polymorphism through dynamic method dispatch

class Character {
    protected String name;
    protected int health;
    protected int attackPower;

    public Character(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    // Method to be dynamically dispatched
    public void attack() {
        System.out.println(name + " performs a basic attack!");
    }

    public void takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " takes " + damage + " damage. Health: " + health);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}

class Warrior extends Character {
    private int ragePoints;

    public Warrior(String name, int health, int attackPower, int ragePoints) {
        super(name, health, attackPower);
        this.ragePoints = ragePoints;
    }

    @Override
    public void attack() {
        System.out.println(name + " swings sword with " + ragePoints + " rage points!");
        System.out.println("Warrior deals " + (attackPower * 2) + " damage!");
    }

    public void specialAttack() {
        System.out.println(name + " uses BERSERKER RAGE! Damage doubled!");
    }
}

class Mage extends Character {
    private int manaPoints;

    public Mage(String name, int health, int attackPower, int manaPoints) {
        super(name, health, attackPower);
        this.manaPoints = manaPoints;
    }

    @Override
    public void attack() {
        System.out.println(name + " casts a spell using " + manaPoints + " mana points!");
        System.out.println("Mage deals " + (attackPower * 3) + " magical damage!");
    }

    public void specialAttack() {
        System.out.println(name + " casts FIREBALL! Area damage dealt!");
    }
}

class Archer extends Character {
    private int arrows;

    public Archer(String name, int health, int attackPower, int arrows) {
        super(name, health, attackPower);
        this.arrows = arrows;
    }

    @Override
    public void attack() {
        System.out.println(name + " shoots arrow! " + arrows + " arrows remaining!");
        System.out.println("Archer deals " + (attackPower * 2) + " piercing damage!");
    }

    public void specialAttack() {
        System.out.println(name + " uses MULTI-SHOT! Fires 5 arrows at once!");
    }
}

public class GameCharactersDemo {
    public static void main(String[] args) {
        System.out.println("=== Game Characters - Dynamic Method Dispatch Demo ===\n");

        // Dynamic method dispatch: parent reference, child objects
        Character[] party = new Character[3];
        party[0] = new Warrior("Thorin", 150, 25, 80);
        party[1] = new Mage("Gandalf", 80, 35, 100);
        party[2] = new Archer("Legolas", 100, 30, 20);

        // Runtime polymorphism - which attack() is called depends on actual object
        for (Character c : party) {
            System.out.println("--- " + c.getName() + " (HP: " + c.getHealth() + ") ---");
            c.attack();  // Dynamic binding determines which attack() to call
            System.out.println();
        }

        // Type casting to access subclass-specific methods
        ((Warrior) party[0]).specialAttack();
        ((Mage) party[1]).specialAttack();
        ((Archer) party[2]).specialAttack();

    }
}

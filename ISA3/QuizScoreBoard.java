import java.util.*;
import java.io.*;

// Interface
interface Scorable {
    int getScore();
}

// Custom Exception
class NegativeScoreException extends Exception {
    NegativeScoreException(String message) {
        super(message);
    }
}

// Player class
class Player implements Scorable {

    String name;
    int score;

    Player(String name, int score) throws NegativeScoreException {

        if (score < 0) {
            throw new NegativeScoreException("Score cannot be negative!");
        }

        this.name = name;
        this.score = score;
    }

    // Implement interface method
    @Override
    public int getScore() {
        return score;
    }

    // Display player details
    void display() {
        System.out.println("Player Name : " + name);
        System.out.println("Score       : " + score);
    }
}

// Main class
public class QuizScoreBoard {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Player> players = new ArrayList<>();

        System.out.print("Enter number of players: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Input player details
        for (int i = 0; i < n; i++) {

            System.out.println("\nPlayer " + (i + 1));

            System.out.print("Enter Player Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Score: ");
            int score = sc.nextInt();
            sc.nextLine();

            try {
                Player p = new Player(name, score);
                players.add(p);
            } catch (NegativeScoreException e) {
                System.out.println(e.getMessage());
            }
        }

        // Sort players by score (Descending)
        Collections.sort(players, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return p2.getScore() - p1.getScore();
            }
        });

        // Display leaderboard
        System.out.println("\n===== LEADERBOARD =====");

        for (Player p : players) {
            p.display();
            System.out.println();
        }

        // Write leaderboard to file
        try {
            FileWriter fw = new FileWriter("scores.txt");

            for (Player p : players) {
                fw.write(p.name + " " + p.score + "\n");
            }

            fw.close();
            System.out.println("Leaderboard written to scores.txt");

        } catch (IOException e) {
            System.out.println("File Error!");
        }

        sc.close();
    }
}
import java.io.*;
import java.util.Scanner;

public class Q2_CricketPlayerStatistics {
    
    static class Player {
        private String playerName;
        private int runsScored;
        
        public Player(String playerName, int runsScored) {
            this.playerName = playerName;
            this.runsScored = runsScored;
        }
        
        public String getPlayerName() {
            return playerName;
        }
        
        public int getRunsScored() {
            return runsScored;
        }
        
        @Override
        public String toString() {
            return playerName + "," + runsScored;
        }
    }
    
    public static Player findHighestScorer(Player[] players) {
        Player highestScorer = players[0];
        for (int i = 1; i < players.length; i++) {
            if (players[i].getRunsScored() > highestScorer.getRunsScored()) {
                highestScorer = players[i];
            }
        }
        return highestScorer;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Cricket Player Statistics ===");
        System.out.print("Enter the number of players: ");
        int n = sc.nextInt();
        sc.nextLine();
        
        Player[] players = new Player[n];
        
        System.out.println("\nEnter player details:");
        for (int i = 0; i < n; i++) {
            System.out.println("\nPlayer " + (i + 1) + ":");
            System.out.print("  Player Name: ");
            String playerName = sc.nextLine();
            
            System.out.print("  Runs Scored: ");
            int runsScored = sc.nextInt();
            sc.nextLine();
            
            players[i] = new Player(playerName, runsScored);
        }
        
        // Display all players
        System.out.println("\n=== All Players ===");
        System.out.println("Player Name | Runs Scored");
        for (Player player : players) {
            System.out.println(player.getPlayerName() + " | " + player.getRunsScored());
        }
        
        // Find highest scorer
        Player highestScorer = findHighestScorer(players);
        
        // Write to output file
        try {
            FileWriter fw = new FileWriter("HighestScorerOutput.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("=== Highest Run Scorer ===\n");
            bw.write("Player Name,Runs Scored\n");
            bw.write(highestScorer.toString() + "\n");
            
            bw.close();
            System.out.println("\nOutput written to HighestScorerOutput.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        
        // Display highest scorer
        System.out.println("\n=== Highest Run Scorer ===");
        System.out.println("Player Name: " + highestScorer.getPlayerName());
        System.out.println("Runs Scored: " + highestScorer.getRunsScored());
        
        sc.close();
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Player {
    private String playerName;
    private int runsScored;

    // Default constructor
    public Player() {
        this.playerName = "Unknown";
        this.runsScored = 0;
    }

    // Parameterized constructor
    public Player(String playerName, int runsScored) {
        this.playerName = playerName;
        this.runsScored = runsScored;
    }

    // Getters and Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    @Override
    public String toString() {
        return "Player Name: " + playerName + " | Runs Scored: " + runsScored;
    }
}

public class CricketPlayerStats {
    public static void main(String[] args) {
        String inputFileName = "players.txt";
        String outputFileName = "highest_scorer.txt";

        File inputFile = new File(inputFileName);

        // Checklist: File exists before reading
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.out.println("Error: Input file '" + inputFileName + "' does not exist or cannot be read.");
            return;
        }

        // Checklist: Store details of N players in an array
        // We will read first to find N, or just use a fixed array if N is given, 
        // or re-read. Let's use an array with a large enough capacity and track count.
        Player[] players = new Player[100]; 
        int playerCount = 0;

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            // Checklist: Buffered I/O used for better performance
            reader = new BufferedReader(new FileReader(inputFile));
            String line;
            
            System.out.println("--- Reading Player Statistics ---");
            while ((line = reader.readLine()) != null && playerCount < players.length) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int runs = Integer.parseInt(parts[1].trim());
                    
                    players[playerCount] = new Player(name, runs);
                    System.out.println("Read: " + players[playerCount]);
                    playerCount++;
                }
            }

            if (playerCount == 0) {
                System.out.println("No player data found.");
                return;
            }

            // Find the highest scorer
            Player highestScorer = players[0];
            for (int i = 1; i < playerCount; i++) {
                if (players[i].getRunsScored() > highestScorer.getRunsScored()) {
                    highestScorer = players[i];
                }
            }

            System.out.println("\nHighest Scorer: " + highestScorer.getPlayerName() + " (" + highestScorer.getRunsScored() + " runs)");

            // Write details of highest scorer to output file
            File outputFile = new File(outputFileName);
            
            // Checklist: Append vs overwrite mode set correctly
            writer = new BufferedWriter(new FileWriter(outputFile, false));
            writer.write("Highest Scorer Details:");
            writer.newLine();
            writer.write(highestScorer.toString());
            writer.newLine();
            
            System.out.println("Successfully wrote highest scorer to " + outputFileName);

        } catch (IOException e) {
            // Checklist: IOException is caught and handled
            System.err.println("An I/O error occurred: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Data format error in input file: " + e.getMessage());
        } finally {
            // Checklist: Streams are always closed after use
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException ex) {
                System.err.println("Error closing streams: " + ex.getMessage());
            }
        }
    }
}

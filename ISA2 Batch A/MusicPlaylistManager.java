import java.util.Scanner;

// Abstract class
abstract class MediaContent {
    String artistName;

    // Constructor
    MediaContent(String artistName) {
        this.artistName = artistName;
    }

    // Abstract method
    abstract void getGenre();

    // Method to trim whitespace and check name
    void formatArtistName() {
        artistName = artistName.trim();

        System.out.println("\nFormatted Artist Name: " + artistName);

        if (artistName.contains(" ")) {
            System.out.println("Artist has First Name and Last Name.");
        } else {
            System.out.println("Artist has only one name.");
        }
    }
}

// Song subclass
class Song extends MediaContent {

    String genre;
    int[] durations = new int[5];

    // Constructor
    Song(String artistName, String genre, int[] durations) {
        super(artistName);
        this.genre = genre;
        this.durations = durations;
    }

    // Implement abstract method
    @Override
    void getGenre() {
        System.out.println("Genre: " + genre);
    }

    // Calculate total duration
    int getTotalDuration() {
        int sum = 0;

        for (int i = 0; i < durations.length; i++) {
            sum += durations[i];
        }

        return sum;
    }

    // Display playlist type
    void checkPlaylist() {
        int total = getTotalDuration();

        System.out.println("Total Duration = " + total + " seconds");

        if (total > 1200) {
            System.out.println("Long Playlist");
        } else {
            System.out.println("Short Playlist");
        }
    }
}

// Main class
public class MusicPlaylistManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Accept artist name
        System.out.print("Enter Artist Name: ");
        String artist = sc.nextLine();

        // Accept genre
        System.out.print("Enter Genre: ");
        String genre = sc.nextLine();

        // Accept durations
        int[] durations = new int[5];

        System.out.println("Enter durations of 5 songs (in seconds):");
        for (int i = 0; i < 5; i++) {
            System.out.print("Song " + (i + 1) + ": ");
            durations[i] = sc.nextInt();
        }

        // Polymorphism
        MediaContent obj = new Song(artist, genre, durations);

        obj.formatArtistName();
        obj.getGenre();

        // Downcasting
        Song s = (Song) obj;

        s.checkPlaylist();

        sc.close();
    }
}
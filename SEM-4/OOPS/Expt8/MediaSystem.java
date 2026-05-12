/**
 * Experiment 3: Multi-Level Media System
 * Demonstrates multilevel inheritance with abstract classes
 */

// Abstract base class for media
abstract class Media {
    private String title;
    private String artist;
    private int duration;

    // Constructor
    public Media(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    // Abstract method - must be implemented by subclasses
    public abstract void play();

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }

    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Artist: " + artist);
        System.out.println("Duration: " + duration + " seconds");
    }
}

// Abstract subclass for audio media
abstract class AudioMedia extends Media {
    protected int volume;

    // Constructor
    public AudioMedia(String title, String artist, int duration) {
        super(title, artist, duration);
        this.volume = 50;
    }

    // Abstract method for volume control
    public abstract void adjustVolume(int level);

    // Concrete method for audio-specific functionality
    public void pause() {
        System.out.println(getTitle() + " paused.");
    }

    public void stop() {
        System.out.println(getTitle() + " stopped.");
    }

    public int getVolume() {
        return volume;
    }
}

// Concrete class for music player
class MusicPlayer extends AudioMedia {
    private String genre;
    private boolean isPlaying;

    public MusicPlayer(String title, String artist, int duration, String genre) {
        super(title, artist, duration);
        this.genre = genre;
        this.isPlaying = false;
    }

    @Override
    public void play() {
        isPlaying = true;
        System.out.println("=== Now Playing ===");
        displayInfo();
        System.out.println("Genre: " + genre);
        System.out.println("Volume: " + volume);
        System.out.println("Status: Playing");
        System.out.println("===================");
    }

    @Override
    public void adjustVolume(int level) {
        if (level >= 0 && level <= 100) {
            this.volume = level;
            System.out.println("Volume adjusted to: " + level);
        } else {
            System.out.println("Invalid volume level! Must be between 0-100.");
        }
    }

    public void togglePlay() {
        if (isPlaying) {
            pause();
            isPlaying = false;
        } else {
            play();
            isPlaying = true;
        }
    }

    public String getGenre() {
        return genre;
    }
}

// Main class
public class MediaSystem {
    public static void main(String[] args) {
        System.out.println("=== MULTI-LEVEL MEDIA SYSTEM ===\n");

        // Creating MusicPlayer object (concrete class)
        MusicPlayer player = new MusicPlayer(
            "Bohemian Rhapsody",
            "Queen",
            354,
            "Rock"
        );

        // Playing media
        player.play();

        System.out.println();

        // Adjusting volume
        player.adjustVolume(75);

        System.out.println();

        // Pausing and resuming
        player.pause();
        player.togglePlay();

        System.out.println();

        // Stopping
        player.stop();
    }
}

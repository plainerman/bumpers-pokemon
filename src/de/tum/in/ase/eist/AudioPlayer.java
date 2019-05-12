package de.tum.in.ase.eist;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * This class handles the background music played during the game
 */
public class AudioPlayer {

    public static String BACKGROUND_MUSIC_FILE = "Music.wav";
    public static String CRASH_MUSIC_FILE = "Crash.wav";
    public static String HIT_MUSIC_FILE = "Hit.wav";
    private static Media MUSIC;
    private static Media CRASH;
    private static Media HIT;
    private MediaPlayer mediaPlayer;
    private static boolean playingBackgroundMusic;
    public boolean crashSoundPlayed = false;
    private MediaPlayer mediaPlayerBang;
    private Duration currentBackgroundTime;


    /**
     * Constructor, gets the media files from resources and sets the boolean
     * playingBackgroundMusic false, which means that after game startup no
     * music is being played.
     */
    public AudioPlayer() {
        AudioPlayer.MUSIC = loadAudioFile(BACKGROUND_MUSIC_FILE);
        AudioPlayer.CRASH = loadAudioFile(CRASH_MUSIC_FILE);
        AudioPlayer.HIT =  loadAudioFile(HIT_MUSIC_FILE);
        AudioPlayer.playingBackgroundMusic = false;
    }

    /**
     * Checks if no music is currently running by checking the value of the
     * boolean playingBackgroundMusic. Starts playing the background music in a
     * new thread.
     */
    public void playBackgroundMusic() {
        if (!AudioPlayer.playingBackgroundMusic) {
            AudioPlayer.playingBackgroundMusic = true;
            this.mediaPlayer = new MediaPlayer(AudioPlayer.MUSIC);
            mediaPlayer.setVolume(0.5);
            this.mediaPlayer.setAutoPlay(true);
            // Loop for the main music sound:
            this.mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    AudioPlayer.this.mediaPlayer.seek(Duration.ZERO);
                }
            });
            this.mediaPlayer.play();
        }
    }

    private Media loadAudioFile(String fileName) {
        String musicSource = getClass().getClassLoader().getResource(fileName).toString();
        return new Media(musicSource);
    }

    /**
     * Checks if music is currently running and stops it if so
     */
    public void stopBackgroundMusic() {
        if (AudioPlayer.playingBackgroundMusic) {
            AudioPlayer.playingBackgroundMusic = false;
            this.mediaPlayer.stop();
        }
    }

    public void playHitSound() {
        new MediaPlayer(AudioPlayer.HIT).play();
    }

    /**
     * Plays the Bang sound when Cars crash
     */
    public void playCrashSound() {
        // define new MediaPlayer variable for Bang Sound
        this.currentBackgroundTime = this.mediaPlayer.getCurrentTime();
        this.mediaPlayer.stop();
        mediaPlayerBang = new MediaPlayer(AudioPlayer.CRASH);
        mediaPlayerBang.setVolume(0.5);
        this.mediaPlayerBang.setOnEndOfMedia(new Runnable() {
            public void run() {
                AudioPlayer.this.mediaPlayerBang.seek(Duration.ZERO);
            }
        });
        mediaPlayerBang.play();
        // set boolean Variable to true
        this.crashSoundPlayed = true;
    }

    public void endCrashSound(boolean continueBackground) {
        if (mediaPlayerBang != null) mediaPlayerBang.stop();
        if (continueBackground && currentBackgroundTime != null) {
            this.mediaPlayer.setStartTime(currentBackgroundTime);
            this.mediaPlayer.play();
        }
    }

    /**
     * Checks if the background music is playing by returning the boolean
     * playingBackgroundMusic. This boolean is initially false after the game
     * startup and set to true in the playBackgroundMusic() method.
     *
     * @return false if background music is not playing
     */
    public static boolean isPlayingBackgroundMusic() {
        return playingBackgroundMusic;
    }

}

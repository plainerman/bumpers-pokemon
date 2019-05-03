package de.tum.in.ase.eist;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * This class handles the background music played during the game
 *
 */
public class AudioPlayer {
	
	public static String BACKGROUND_MUSIC_FILE = "Music.wav";
	public static String CRASH_MUSIC_FILE = "Crash.wav";
	private static Media MUSIC;
	private static Media CRASH;
	private MediaPlayer mediaPlayer;
	private static boolean playingBackgroundMusic;
	public boolean crashSoundPlayed = false;

	/**
	 * Constructor, gets the media files from resources and sets the boolean
	 * playingBackgroundMusic false, which means that after game startup no
	 * music is being played.
	 */
	public AudioPlayer() {
		AudioPlayer.MUSIC = loadAudioFile(BACKGROUND_MUSIC_FILE);
		AudioPlayer.CRASH = loadAudioFile(CRASH_MUSIC_FILE);
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
	
	/**
	 * Plays the Bang sound when Cars crash
	 */
	public void playCrashSound() {
		// define new MediaPlayer variable for Bang Sound
		MediaPlayer mediaPlayerBang = new MediaPlayer(AudioPlayer.CRASH);
		mediaPlayerBang.play();
		// set boolean Variable to true
		this.crashSoundPlayed = true;
	}

	/**
	 * Checks if the background music is playing by returning the boolean
	 * playingBackgroundMusic. This boolean is initially false after the game
	 * startup and set to true in the playBackgroundMusic() method.
	 * 
	 * @return true if background music is playing
	 * @return false if background music is not playing
	 */
	public static boolean isPlayingBackgroundMusic() {
		return playingBackgroundMusic;
	}

}

package MusicMain;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Music Player
 *
 * @author Aric Hurkman
 * date: 11.21.2019
 * Using Javazoom Decoder for MP3 Files
 * <p>
 * Sets File to be played and plays and stops music
 * Creates the FileInputStream
 * <p>
 * Runs on its own thread
 */
class MusicPlayer {

	private Player player;
	private String musicFile;
	private boolean playing = false;
	private Thread thread;

	FileInputStream stream;

	/**
	 * RunPlay invoked from ButtonPanel via the PlayListener interface
	 * Checks if already playing and if the thread is alive and not null
	 * if null starts new thread and sets playing to true
	 */
	void RunPlay() {
		if (!playing) {
			if (thread == null) {
				thread = new Thread(() -> {
					if (musicFile == null) {
						musicFile = openFile().getAbsolutePath();
						play(musicFile);
					} else {
						play(musicFile);
					}
				});
				this.playing = true;
				thread.start();
			} else if (!thread.isAlive()) {
				thread = new Thread(() -> {
					if (musicFile == null) {
						musicFile = openFile().getAbsolutePath();
						play(musicFile);
					} else {
						play(musicFile);
					}
				});
				this.playing = true;
				thread.start();

			} else {
				System.out.println("Thread Already Alive");
			}
		}
	}

	/**
	 * Sets the FileInputStream
	 * Set Player and Plays Mp3
	 * @param file
	 */
	private void play(String file) {
		try {
			stream = new FileInputStream(file);
			player = new Player(stream); // ----> Javazoom api
			player.play();// ----> Javazoom api


		} catch (JavaLayerException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops player
	 * joins thread
	 * sets playing to false
	 */
	void stop() {
		if (player != null) {
			player.close(); // ----> Javazoom api
			try {
				if (thread.isAlive()) {
					thread.join();
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			this.playing = false;
		}
	}

	/**
	 * Opens a File Chooser and returns the File to be called in Play() and Load()
	 *
	 * @return File
	 */
	private File openFile() {
		JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		File f = null;
		int r = j.showOpenDialog(null);


		if (r == JFileChooser.APPROVE_OPTION) {
			musicFile = j.getSelectedFile().getAbsolutePath();
			f = new File(musicFile);
		} else {
			System.out.println("Canceled");
		}
		return f;
	}


}

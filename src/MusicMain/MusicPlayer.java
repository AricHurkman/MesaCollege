package MusicMain;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;

class MusicPlayer {

	private Player player;
	private String musicFile;
	private boolean playing = false;
	private Thread thread;

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

	private void play(String file) {
		try {
			FileInputStream stream = new FileInputStream(file);
			InputStream inputStream = stream;
			byte[] data = inputStreamToByteArray(inputStream);
			for (int i = 0; i < data.length; i++){
				System.out.println(data[i]);
			}
			player = new Player(stream);
			player.play();


		} catch (JavaLayerException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void stop() {
		if (player != null) {
			player.close();
			try {
				if (thread.isAlive()) thread.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			this.playing = false;
		}
	}

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

	public byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int bytesRead;
		while ((bytesRead = inStream.read(buffer)) > 0) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}
}

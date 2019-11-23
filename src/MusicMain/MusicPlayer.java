package MusicMain;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

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

	private static final double WAVEFORM_HEIGHT_COEFFICIENT = 1.3;
	private AdvancedPlayer advancedPlayer;
	private Player player;
	private String musicFile;
	private boolean playing = false;
	private Thread thread;
	public float[] musicPoints;

	/**
	 * RunPlay invoked from ButtonPanel via the PlayListener interface
	 * Checks if already playing and if the thread is alive and not null
	 * if null starts new thread and sets playing to true
	 */
	void RunPlay() {
		if (!playing) {
			play();
			this.playing = true;
		}
	}

	/**
	 * Sets the FileInputStream
	 * Coverts Mp3 to wave
	 */
	private void play() {

		thread = new Thread(() -> {
			final int BUFFER_SIZE = 128000;
			File f = openFile();
			File temp = new File(f.getPath().split("\\.")[0].concat(".wav"));

			try {
				Converter converter = new Converter();
				converter.convert(f.getAbsolutePath(), temp.getAbsolutePath());

				AudioInputStream input = AudioSystem.getAudioInputStream(temp);

				musicPoints = processAmplitudes(getWavAmplitudes(temp));


				temp.delete();
				AudioFormat baseFormat = input.getFormat();
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, baseFormat);
				SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
				sourceLine.open(baseFormat);


				sourceLine.start();

				int nBytesRead = 0;
				byte[] abData = new byte[BUFFER_SIZE];
				while (nBytesRead != -1) {
					try {
						nBytesRead = input.read(abData, 0, abData.length);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (nBytesRead >= 0) {
						@SuppressWarnings("unused") int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
					}
				}

				sourceLine.drain();
				sourceLine.close();


			} catch (JavaLayerException | UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}

		});
		thread.start();

	}

	private int[] getWavAmplitudes(File file) {
		//Get Audio input stream
		try (AudioInputStream input = AudioSystem.getAudioInputStream(file)) {
			AudioFormat baseFormat = input.getFormat();

			//Encoding
			Encoding encoding = AudioFormat.Encoding.PCM_UNSIGNED;
			float sampleRate = baseFormat.getSampleRate();
			int numChannels = baseFormat.getChannels();

			AudioFormat decodedFormat = new AudioFormat(encoding, sampleRate, 16, numChannels, numChannels * 2, sampleRate, false);
			int available = input.available();

			//Get the PCM Decoded Audio Input Stream
			try (AudioInputStream pcmDecodedInput = AudioSystem.getAudioInputStream(decodedFormat, input)) {
				final int BUFFER_SIZE = 4096; //this is actually bytes

				//Create a buffer
				byte[] buffer = new byte[BUFFER_SIZE];

				//Now get the average to a smaller array
				int maximumArrayLength = 100000;
				int[] finalAmplitudes = new int[maximumArrayLength];
				int samplesPerPixel = available / maximumArrayLength;

				//Variables to calculate finalAmplitudes array
				int currentSampleCounter = 0;
				int arrayCellPosition = 0;
				float currentCellValue = 0.0f;

				//Variables for the loop
				int arrayCellValue = 0;

				//Read all the available data on chunks
				while (pcmDecodedInput.read(buffer, 0, BUFFER_SIZE) > 0)
					for (int i = 0; i < buffer.length - 1; i += 2) {

						//Calculate the value
						arrayCellValue = (int) (((((buffer[i + 1] << 8) | buffer[i] & 0xff) << 16) / 32767) * WAVEFORM_HEIGHT_COEFFICIENT);


						if (currentSampleCounter != samplesPerPixel) {
							++currentSampleCounter;
							currentCellValue += Math.abs(arrayCellValue);
						} else {
							//Avoid ArrayIndexOutOfBoundsException
							if (arrayCellPosition != maximumArrayLength)
								finalAmplitudes[arrayCellPosition] = finalAmplitudes[arrayCellPosition + 1] = (int) currentCellValue / samplesPerPixel;
							//Reset
							currentSampleCounter = 0;
							currentCellValue = 0;
							//Next 2
							arrayCellPosition += 2;
						}
					}

				return finalAmplitudes;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		//You don't want this to reach here...
		return new int[1];
	}

	private float[] processAmplitudes(int[] sourcePcmData) {
		System.out.println("Processing WAV amplitudes");

		//The width of the resulting waveform panel
		int width = 10000;
		float[] waveData = new float[width];
		int samplesPerPixel = sourcePcmData.length / width;

		//Calculate
		float nValue;
		for (int w = 0; w < width; w++) {

			//For performance keep it here
			int c = w * samplesPerPixel;
			nValue = 0.0f;

			//Keep going
			for (int s = 0; s < samplesPerPixel; s++) {
				nValue += (Math.abs(sourcePcmData[c + s]) / 65536.0f);
			}

			//Set WaveData
			waveData[w] = nValue / samplesPerPixel;
		}

		System.out.println("Finished Processing amplitudes");
		return waveData;
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

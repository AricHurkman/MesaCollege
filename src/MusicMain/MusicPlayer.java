package MusicMain;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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


	//File Chooser
	JFileChooser chooser;
	//File Filter
	FileNameExtensionFilter filter;
	//The coefficient to multiply the wave height by
	private static final double WAVE_HEIGHT_COEFFICIENT = 1.3;
	//If Playing Music
	boolean playing = false;
	//Thread for music
	private Thread musicThread;
	//Points of amplitudes of music
	float[] musicPoints;

	MusicPlayer() {
		chooser = new JFileChooser();
		filter = new FileNameExtensionFilter("Music", "mp3");
		//Sets Only File can be chosen
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//Set file filter to only open mp3
		chooser.setFileFilter(filter);
	}

	/**
	 * RunPlay invoked from ButtonPanel via the PlayListener interface
	 * Checks if already playing and if the thread is alive and not null
	 * if null starts new thread and sets playing to true
	 */
	void RunPlay() {
		if (!playing) {
			playFile();
		}
	}

	/**
	 * Sets the FileInputStream
	 * Coverts Mp3 to wave
	 */
	private void playFile() {

		if (musicThread != null) if (musicThread.isAlive()) {
			System.out.println("Error Thread is Alive Already");
			try {
				musicThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		musicThread = new Thread(() -> {
			System.out.println("Starting Music Player Thread");

			String fileString = openFile();
			File file;
			if (fileString.equals("")) {
				System.out.println("File Blank");
				return;
			} else {
				file = new File(fileString);
			}
			File temp = new File(file.getPath().split("\\.")[0].concat(".wav"));
			try {
				//Converting the mps to wav
				Converter converter = new Converter();
				converter.convert(file.getAbsolutePath(), temp.getAbsolutePath());
				//getting the stream input for the converted wav
				AudioInputStream input = AudioSystem.getAudioInputStream(temp);
				AudioFormat baseFormat = input.getFormat();
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, baseFormat);
				SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
				final int BUFFER_SIZE = sourceLine.getBufferSize();
				sourceLine.open(baseFormat);
				sourceLine.start();

				//converts the wav file to music points that can be drawn accessed form Visualiser Class
				musicPoints = processAmplitudes(getWavAmplitudes(temp));
				temp.delete();//Remove temp file
				playing = true;
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

					if (!playing) {

						sourceLine.stop();
						break;
					}
				}

				sourceLine.stop();
				sourceLine.drain();
				sourceLine.close();
				musicPoints = new float[1];
				musicPoints[0] = 0;
				playing = false;


			} catch (JavaLayerException | UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}

		});
		musicThread.start();
	}

	/**
	 * Stops player
	 * joins thread
	 * sets playing to false
	 */
	void stop() {
		playing = false;
	}

	/**
	 * Gets the int[] of the converted MP3 file and converts that to a small array of raw Amplitudes
	 *
	 * @param file file to be calculated
	 * @return int[] of the wavAmp
	 */
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
				int arrayCellValue;
				//Read all the available data on chunks
				while (pcmDecodedInput.read(buffer, 0, BUFFER_SIZE) > 0)

					for (int i = 0; i < buffer.length - 1; i += 2) {

						//Calculate the value 8 bits Long
						arrayCellValue = (int) (((((buffer[i + 1] * 256) | (buffer[i] & 0xff)) * 65536) / 32767) * WAVE_HEIGHT_COEFFICIENT);


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

	/**
	 * calculates the wave width to be outputted in the float[] music points used my the visualizer
	 *
	 * @param sourcePcmData takes in the int[] calculated in getWavAmplitudes
	 * @return float[] of processed amplitudes
	 */
	private float[] processAmplitudes(int[] sourcePcmData) {
		System.out.println("Processing WAV amplitudes");

		//The width of the resulting waveform panel
		int width = 10000;
		float[] waveData = new float[width];
		int samplesPerPixel = sourcePcmData.length / width;

		//Calculate
		float nValue;
		for (int w = 0; w < width; w++) {
			int c = w * samplesPerPixel;
			nValue = 0.0f;
			for (int s = 0; s < samplesPerPixel; s++) {
				nValue += sourcePcmData[c + s] / 65536.0f;
			}
			waveData[w] = nValue / samplesPerPixel;
		}

		System.out.println("Finished Processing amplitudes");
		return waveData;
	}

	/**
	 * Opens a File Chooser and returns the File to be called in Play() and Load()
	 *
	 * @return File
	 */
	private String openFile() {
		System.out.println("Trying to open file");
		//Gets the File System
		chooser.getFileSystemView();
		//default file string is blank for file check
		String file = "";
		//if correct file is selected
		int r = chooser.showOpenDialog(null);
		if (r == JFileChooser.APPROVE_OPTION) {
			//if correct file type and file is chosen then set the string
			file = chooser.getSelectedFile().getAbsolutePath();
			System.out.println("File: " + file);
		} else if(r == JFileChooser.CANCEL_OPTION) {
			System.out.println("Canceled");
		}
		else{
			//Should never get here
			System.out.println("Error: File Chosen Is Invalid");
		}
		//return the current file value either blank string or the absolute path of file
		return file;
	}
}

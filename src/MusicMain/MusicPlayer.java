package MusicMain;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.*;
import java.io.*;

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

	enum AudioType {
		File, Live
	}

	public AudioType audioType = AudioType.File;

	private static final double WAVEFORM_HEIGHT_COEFFICIENT = 1.3;
	boolean playing = false;
	boolean runningLive = false;
	private Thread thread;
	float[] musicPoints;
	private ByteArrayOutputStream out;

	/**
	 * RunPlay invoked from ButtonPanel via the PlayListener interface
	 * Checks if already playing and if the thread is alive and not null
	 * if null starts new thread and sets playing to true
	 */
	void RunPlay() {
		if (!this.playing) {
			switch (audioType) {

				case File:
					playFile();
					break;
				case Live:
					//Code Not Working
					//live();
					break;
			}

		}
	}
	public void setAudioType(AudioType audioType){
		this.audioType = audioType;
	}

	/**
	 * Sets the FileInputStream
	 * Coverts Mp3 to wave
	 */
	private void playFile() {

		if (thread != null) if (thread.isAlive()) {
			System.out.println("Error Thread is Alive Already");
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread = new Thread(() -> {
			System.out.println("Starting Music Player Thread");
			final int BUFFER_SIZE = 128000;
			String fileString = openFile();
			File file = null;
			if (fileString.equals("")) {
				System.out.println("File Blank");
				return;
			} else {
				file = new File(fileString);
			}
			File temp = new File(file.getPath().split("\\.")[0].concat(".wav"));
			try {
				Converter converter = new Converter();
				converter.convert(file.getAbsolutePath(), temp.getAbsolutePath());


				AudioInputStream input = AudioSystem.getAudioInputStream(temp);


				AudioFormat baseFormat = input.getFormat();
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, baseFormat);
				SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
				sourceLine.open(baseFormat);
				sourceLine.start();
				musicPoints = processAmplitudes(getWavAmplitudes(temp));
				temp.delete();//Remove temp file
				this.playing = true;
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


					if (!this.playing) {
						sourceLine.stop();
						break;
					}
				}

				sourceLine.stop();
				sourceLine.drain();
				sourceLine.close();
				musicPoints = new float[1];
				musicPoints[0] = 0;
				this.playing = false;


			} catch (JavaLayerException | UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}

		});
		thread.start();
	}
	TargetDataLine line = null;
	private void live() {

		Mixer.Info[] infos = AudioSystem.getMixerInfo();

		Mixer mixer = AudioSystem.getMixer(infos[0]);
		System.out.println(mixer);



		//AudioFormat format = getFormat();
		//DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);


		//TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);

//
//		DataLine.Info info = new DataLine.Info(TargetDataLine.class,
//				format); // format is an AudioFormat object
//		if (!AudioSystem.isLineSupported(info)) {
//			// Handle the error.
//		}
//		// Obtain and open the line.
//		try {
//			line = (TargetDataLine) AudioSystem.getLine(info);
//			line.open(format);
//			line.start();
//		} catch (LineUnavailableException ex) {
//			// Handle the error.
//			//...
//		}
//
//		Runnable r = new Runnable() {
//			int buffSize = (int) format.getSampleRate() * format.getFrameSize();
//			byte buffer[] = new byte[buffSize];
//			@Override
//			public void run() {
//				out = new ByteArrayOutputStream();
//				runningLive = true;
//				playing = true;
//				try {
//
//					while (runningLive){
//						int count = line.read(buffer,0, buffer.length);
//						if(count > 0){
//							out.write(buffer, 0, count);
//							musicPoints = processAmplitudes(getLive());
//						}
//					}
//
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		};
//		Thread thread = new Thread(r);
//		thread.start();

	}

	private AudioFormat getFormat() {
		float sampleRate = 44100;
		int sampleSizeBits = 16;
		int ch = 2;
		return new AudioFormat(Encoding.PCM_SIGNED, sampleRate, sampleSizeBits, ch, ch*2, sampleRate,true);
	}
	private int[] getLive() throws IOException {
		byte audio[]  = out.toByteArray();
		InputStream input = new ByteArrayInputStream(audio);
		out.close();
		return getWavAmplitudes(input);
	}

	/**
	 * Stops player
	 * joins thread
	 * sets playing to false
	 */
	void stop() {
		this.playing = false;
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

						//Calculate the value 8 bits Long
						arrayCellValue = (int) (((((buffer[i + 1] << 8) | buffer[i] & 0xff) << 16) / 32767) * WAVEFORM_HEIGHT_COEFFICIENT);


						if (currentSampleCounter != samplesPerPixel) {
							++currentSampleCounter;
							currentCellValue += Math.abs(arrayCellValue);
						} else {
							//Avoid ArrayIndexOutOfBoundsException
							if (arrayCellPosition != maximumArrayLength)
								finalAmplitudes[arrayCellPosition] = finalAmplitudes[arrayCellPosition + 1] =  (int) currentCellValue / samplesPerPixel;
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
	private int[] getWavAmplitudes(InputStream inputStream) {
		//Get Audio input stream
		try  {
			AudioInputStream input = AudioSystem.getAudioInputStream(inputStream);
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
		int width = 1000;
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
				nValue += Math.abs(sourcePcmData[c + s]) / 65536.0f;
			}

			//Set WaveData
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
	JFileChooser chooser = new JFileChooser();

	private String openFile() {
		System.out.println("Trying to open file");
		chooser.getFileSystemView();
		String file = "";
		int r = chooser.showOpenDialog(null);
		if (r == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile().getAbsolutePath();

			System.out.println("File: " + file);
		} else {
			System.out.println("Canceled");
		}
		return file;
	}
}

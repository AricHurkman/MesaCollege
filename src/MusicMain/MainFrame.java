package MusicMain;


import javax.swing.*;
import java.awt.*;

/**
 * @author Aric Hurkman
 *
 * MainFrame.js is the main Jframe Sets the background frame and adds all subframes to this frame
 * Using Δt in Runnable thread for rendering smooth graphics.
 */
class MainFrame extends JFrame implements Runnable {

	private MusicPlayer musicPlayer = new MusicPlayer();

	CanvasVisualizer musicVisualizer = new CanvasVisualizer();
	private Thread mainThread;
	private Thread startThread;
	private boolean running;

	/**
	 * MainFrame Constructor
	 * Sets Title, size, and any other components to main JFrame
	 * Anonymous PlayListener
	 */
	MainFrame() {
		super("Music Player");
		setLayout(new BorderLayout());
		Dimension d = new Dimension(600, 500);
		setSize(d);

		setMaximumSize(d);
		setMinimumSize(d);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonPanel buttonPanel = new ButtonPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		add(musicVisualizer, BorderLayout.CENTER);

		//Button Panel set interface
		buttonPanel.setPlayerListener(new PlayerListener() {

			public void clickedPlay() {
				musicPlayer.RunPlay();

			}

			public void clickedStop() {
				musicPlayer.stop();
			}
		});
		setVisible(true);
		start();
	}

	/**
	 * synchronized Start
	 * Sets mainThread and running to true
	 */
	private synchronized void start() {
		mainThread = new Thread(this);
		running = true;
		mainThread.start();
	}

	/**
	 * synchronized Stop
	 * Joins mainThread and sets running to false
	 */
	private synchronized void stop() {
		try {
			mainThread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		/**
		 * Start of Δt Thread
		 *
		 * Code Below is based off ofa tutorial on https://gamedev.stackexchange.com
		 * runs code based on delta-time (Δt) ticks and not CPU Tick
		 * using this to smooth out animations
		 * While Running set in start() and mainThread
		 * Running False set in stop()
		 *
		 */
		long lastTime = System.nanoTime();
		final double nanoSeconds = 16666666.6667;
		double delta_time = 0;
		long timer = System.currentTimeMillis();
		//int frames = 0;
		while (running) {

			long timeSinceStart = System.nanoTime();
			delta_time += (timeSinceStart - lastTime) / nanoSeconds;
			lastTime = timeSinceStart;
			while (delta_time >= 1) {

				delta_time--;
			}
			if (running) musicVisualizer.Render(musicPlayer); // Invoke
			//frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;

				//System.out.println("FPS: " + frames);
				//frames = 0;
			}


		}
		stop();
		/**
		 * End Of Δt Thread
		 */
	}
}


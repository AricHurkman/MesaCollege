package MusicMain;


import javax.swing.*;
import java.awt.*;

/**
 * @author Aric Hurkman
 * <p>
 * MainFrame.js is the main Jframe Sets the background frame and adds all subframes to this frame
 * Using Δt in Runnable thread for rendering smooth graphics.
 */
class MainFrame implements Runnable {


	enum VisType {
		ThinCircle, ThickCircle, Lines, Tree
	}

	VisType visType = VisType.ThinCircle;


	JFrame frame = new JFrame();

	MusicPlayer musicPlayer = new MusicPlayer();

	CanvasVisualizer musicVisualizer = new CanvasVisualizer();
	TopMenu topMenu;
	private Thread mainThread;
	private boolean running;

	/**
	 * MainFrame Constructor
	 * Sets Title, size, and any other components to main JFrame
	 * Anonymous PlayListener
	 */
	MainFrame() {
		frame.setTitle("Music Player");
		frame.setLayout(new BorderLayout());
		Dimension d = new Dimension(600, 600);
		frame.setSize(d);

		frame.setMaximumSize(d);
		frame.setMinimumSize(d);
		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonPanel buttonPanel = new ButtonPanel();
		topMenu = new TopMenu(this);
		frame.add(topMenu, BorderLayout.NORTH);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		musicVisualizer.setFrame(frame);
		//Button Panel set interface
		buttonPanel.setPlayerListener(new PlayerListener() {

			public void clickedPlay() {
				musicPlayer.RunPlay();
			}

			public void clickedStop() {
				musicPlayer.stop();
			}
		});
		frame.setVisible(true);
		frame.pack();
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
	public synchronized void stop() {
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
		double amountOfTicks = 120;
		final double nanoSeconds = 1000000000 / amountOfTicks;
		double delta_time = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {

			long timeSinceStart = System.nanoTime();
			delta_time += (timeSinceStart - lastTime) / nanoSeconds;
			lastTime = timeSinceStart;
			while (delta_time >= 1) {

				frames++;
				musicVisualizer.Render(musicPlayer, visType);
				delta_time--;
			}


			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;

				//System.out.println("FPS: " + frames);
				frames = 0;
			}


		}
		stop();
		/**
		 * End Of Δt Thread
		 */
	}
}


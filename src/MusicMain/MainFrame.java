package MusicMain;


import javax.swing.*;
import java.awt.*;

/**
 * @author Aric Hurkman
 * <p>
 * MainFrame.js is the main Jframe Sets the background frame and adds all subframes to this frame
 * Using Δt in Runnable thread for rendering smooth graphics.
 */
class MainFrame {

	//Enum Visual Type being displayed
	enum VisType {
		ThinCircle, ThickCircle, Lines, Tree
	}

	//Ref to current visual type
	VisType visType = VisType.ThinCircle;
	//This JFrame
	JFrame frame = new JFrame();
	//ref to Visualizer
	Visualizer musicVisualizer = new Visualizer(this);
	//ref to music player
	MusicPlayer musicPlayer = new MusicPlayer();
	//ref to top menu
	TopMenu topMenu;
	//Thread for visualizer
	private Thread visualizerThread;
	//if running thread then we calculate Δt
	private boolean running;

	/**
	 * MainFrame Constructor
	 * Sets Title, size, and any other components to main JFrame
	 * Anonymous PlayListener
	 */
	MainFrame() {
		//Frame
		frame.setTitle("Music Player");
		frame.setLayout(new BorderLayout());
		Dimension d = new Dimension(600, 600);
		frame.setSize(d);
		frame.setMaximumSize(d);
		frame.setMinimumSize(d);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Top Menu
		topMenu = new TopMenu(this);
		frame.add(topMenu, BorderLayout.NORTH);
		//Button Panel
		ButtonPanel buttonPanel = new ButtonPanel();
		frame.add(buttonPanel, BorderLayout.SOUTH);
		//Visualizer
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

		frame.pack();
		frame.setVisible(true);
		//Starting Thread and calculate
		start();
	}

	/**
	 * synchronized Start
	 * Sets mainThread and running to true
	 * Δt
	 */
	private synchronized void start() {
		visualizerThread = new Thread(() -> {
			long lastTime = System.nanoTime();
			final double tickLimit = 60;
			double nanoSec = 1000000000 / tickLimit;
			double delta = 0;
			while (running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / nanoSec;
				if (delta >= 1) {
					deltaTick();
					delta--;
				}

			}
		});
		running = true;
		visualizerThread.start();
	}

	/**
	 * deltaTick()
	 * current delta tick
	 */
	private void deltaTick() {
		musicVisualizer.Render();
	}

	/**
	 * synchronized Stop
	 * Joins mainThread and sets running to false
	 */
	public synchronized void stop() {
		running = false;
	}

	/**
	 * @param newVisType takes in the visual type to be changed
	 */
	void changeVisualizer(VisType newVisType) {
		if(musicPlayer.playing){
			System.out.println("Error: Please Stop Music To Chang Visualization");
		}else {
			if (visType == newVisType) return;
			visType = newVisType;
			System.out.println("Changing Visual TO: " + newVisType);
		}


	}
}
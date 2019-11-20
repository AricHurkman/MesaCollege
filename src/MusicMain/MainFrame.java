package MusicMain;


import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame implements Runnable {

	private MusicPlayer musicPlayer = new MusicPlayer();

	CanvasVisualizer musicVisualizer = new CanvasVisualizer();
	private Thread thread;
	private boolean running;

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
		add(musicVisualizer,BorderLayout.CENTER);

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

	public synchronized void start(){
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	public synchronized void stop(){
		try {
			thread.join();
			running = false;
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();
		final double nanoSeconds = 16666666.6667;
		double delta_time = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {

			long timeSinceStart = System.nanoTime();
			delta_time += (timeSinceStart - lastTime) / nanoSeconds;
			lastTime = timeSinceStart;
			while (delta_time >= 1) {

				delta_time--;
			}
			if (running) musicVisualizer.Render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
}


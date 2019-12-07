package MusicMain;

import javax.swing.*;

/**
 * App
 *
 * @author Aric Hurkman
 * date: 11.19.2019
 * <p>
 * Main
 */
public class App {

	public static void main(String[] args) {
		new App();
	}

	private App() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});


	}
}

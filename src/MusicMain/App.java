package MusicMain;

import javax.swing.*;

/**
 * App
 * @author Aric Hurkman
 * date: 11.19.2019
 *
 * Main
 */
public class App extends Test {


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

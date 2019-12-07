package MusicMain;

import javax.swing.*;

/**
 * App
 *
 * This is the class that invokes the mainFrame(JFrame)
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
		//Invoking MainFrame
		SwingUtilities.invokeLater(() -> new MainFrame());
	}
}

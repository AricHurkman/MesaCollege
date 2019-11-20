package MusicMain;

import javax.swing.*;

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

package MusicMain;

import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {

	private MusicPlayer musicPlayer = new MusicPlayer();

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

		buttonPanel.setPlayerListener(new PlayerListener() {

			public void clickedPlay() {
				musicPlayer.RunPlay();
			}

			public void clickedStop() {
				musicPlayer.stop();
			}
		});
		setVisible(true);
	}


}


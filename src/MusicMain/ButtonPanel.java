package MusicMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {

	private JButton play = new JButton("", new ImageIcon("icons/play.png"));
	private JButton stop = new JButton("", new ImageIcon("icons/stop.png"));
	private PlayerListener playerListener;

	ButtonPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER));

		play.addActionListener(this);
		stop.addActionListener(this);
		add(play);
		add(stop);
	}

	void setPlayerListener(PlayerListener playerListener) {
		this.playerListener = playerListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton clicked = (JButton) e.getSource();
		if (clicked == play) {
			if (playerListener != null) {
				playerListener.clickedPlay();
			}
		} else if (clicked == stop) {

			if (playerListener != null) {
				playerListener.clickedStop();
			}
		}
	}
}

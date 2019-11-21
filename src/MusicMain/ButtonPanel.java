package MusicMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Aric Hurkman
 * Date: 11.21.2019
 *
 * Button Panel groups the Play and Stop Buttons for music playing
 *
 */
public class ButtonPanel extends JPanel implements ActionListener {

	//Play Button, Icon is from https://www.flaticon.com/free-icon/ resized to 50x50 pixels
	private JButton play = new JButton("", new ImageIcon("icons/play.png"));
	//Stop Button, Icon is from https://www.flaticon.com/free-icon/ resized to 50x50 pixels
	private JButton stop = new JButton("", new ImageIcon("icons/stop.png"));

	private PlayerListener playerListener;

	/**
	 * Constructor For ButtonPanel
	 * sets the layout and adds 'this' to button action listener sets the buttons to this frame
	 */
	ButtonPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		play.addActionListener(this);
		stop.addActionListener(this);
		add(play);
		add(stop);
	}

	/**
	 * Set the button listener for play and stop buttons
	 *
	 * @param playerListener
	 */
	void setPlayerListener(PlayerListener playerListener) {
		this.playerListener = playerListener;
	}


	/**
	 * ActionListener for play and stop buttons
	 *
	 * @param e Set by setPlayerListener() from MainFrame.java, MainFrame invokes Play and Stop Func
	 */
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

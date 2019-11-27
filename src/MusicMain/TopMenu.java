package MusicMain;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopMenu extends JMenuBar {

	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Visualizers",false);
	JMenuItem thinCircles = new JMenuItem("Thin Circles");
	JMenuItem thickCircles = new JMenuItem("Thick Circles");

	TopMenu(MainFrame mainFrame){

		add(menuBar);
		menuBar.add(menu);
		menu.add(thinCircles);
		menu.add(thickCircles);
		thinCircles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.visType = MainFrame.VisType.ThinCircle;
			}
		});
		thickCircles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.visType = MainFrame.VisType.ThickCircle;
			}
		});

	}
}

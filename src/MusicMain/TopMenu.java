package MusicMain;

import javax.swing.*;

public class TopMenu extends JMenuBar {

	TopMenu(MainFrame mainFrame) {

		JMenuBar menuBar = new JMenuBar();
		add(menuBar);

		//Audio Type (Not Used)
//		JMenu audioType = new JMenu("Audio Type", false);
//		menuBar.add(audioType);
//		JMenuItem file = new JMenuItem("From File");
//		audioType.add(file);
//		JMenuItem live = new JMenuItem("Live");
//		audioType.add(live);
//		file.addActionListener(e -> mainFrame.musicPlayer.setAudioType(MusicPlayer.AudioType.File));
//		live.addActionListener(e -> mainFrame.musicPlayer.setAudioType(MusicPlayer.AudioType.Live));

		//Visualizer Types
		JMenu visualizers = new JMenu("Visualizers", false);
		menuBar.add(visualizers);
		JMenuItem thinCircles = new JMenuItem("Thin Circles");
		visualizers.add(thinCircles);
		JMenuItem thickCircles = new JMenuItem("Thick Circles");
		visualizers.add(thickCircles);
		JMenuItem lines1 = new JMenuItem("Lines");
		visualizers.add(lines1);
		JMenuItem tree = new JMenuItem("Tree");
		visualizers.add(tree);

		thinCircles.addActionListener(e -> mainFrame.visType = MainFrame.VisType.ThinCircle);
		thickCircles.addActionListener(e -> mainFrame.visType = MainFrame.VisType.ThickCircle);
		lines1.addActionListener(e -> mainFrame.visType = MainFrame.VisType.Lines);
		tree.addActionListener(e -> mainFrame.visType = MainFrame.VisType.Tree);


	}
}

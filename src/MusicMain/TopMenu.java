package MusicMain;

import javax.swing.*;

/**
 * @author Aric Hurkman
 * date: Dec 1 2019
 * Top Menu JMenu Bar
 * the menu bar for the selection of Visualizer
 */
public class TopMenu extends JMenuBar {

	TopMenu(MainFrame mainFrame) {

		JMenuBar menuBar = new JMenuBar();
		add(menuBar);
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

		thinCircles.addActionListener(e -> mainFrame.changeVisualizer(MainFrame.VisType.ThinCircle));
		thickCircles.addActionListener(e -> mainFrame.changeVisualizer(MainFrame.VisType.ThickCircle));
		lines1.addActionListener(e -> mainFrame.changeVisualizer(MainFrame.VisType.Lines));
		tree.addActionListener(e -> mainFrame.changeVisualizer(MainFrame.VisType.Tree));


	}
}

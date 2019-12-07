package MusicMain;

import MusicMain.bookClasses.Turtle;
import MusicMain.bookClasses.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @author Aric Hurkman
 * <p>
 * Date: 12-1-2019
 * <p>
 * Visualizer.java
 * <p>
 * Draws the visualization from the music being played.
 */
public class Visualizer {
	//Ref to MainFrame JFrame
	private JFrame frame;
	//Width of the frame
	private int width = 635;
	//Height of the frame
	private int height = 600;
	//Index of the iteration of MusicPlayer.MusicPoints
	private int pointIndex = 0;

	//ref to current world
	World w;
	//Random used to randomize scale, position and direction
	private Random r = new Random();

	//Turtle for thinCircle visualization
	Turtle thinCircle;
	//Turtle array for thickCircle visualization. 10 per call
	Turtle[] thickCircles = new Turtle[10];
	//Turtle for lines visualization set to current width per call
	lines[] lines = new lines[width];
	//Turtle for trees visualization 5 per call
	Turtle[] trees = new Turtle[3];

	//Max Scale set per visual
	private float scaleMax = 0.0f;
	//Min Scale set per visual
	private float scaleMin = 10.0f;

	//Color
	//Max count to wait to call for new color
	private int maxWaitCount = 10;
	//Current wait count
	private int waitCount = maxWaitCount;
	//Current visual type selected

	private MainFrame mainFrame;

	Visualizer(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	/**
	 * set local JFrame from MainFrame
	 *
	 * @param frame mainframe frame
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Render Visualizations
	 */
	void Render() {

		if (mainFrame == null) return;
		if (w == null) {
			w = new World(width, height, frame);

		}
		if (mainFrame.musicPlayer.playing) {
			//Calls the current Visual type when song is playing
			switch (mainFrame.visType) {
				case ThinCircle:
					drawSingleBandCircle();
					break;
				case ThickCircle:
					drawThinkBandCircle();
					break;
				case Lines:
					drawLines();
					break;
				case Tree:
					tree();
					break;

			}
		} else {
			frame.repaint();
		}
	}


	/**
	 * Draw single circles
	 */
	private void drawSingleBandCircle() {

		if (thinCircle == null) {
			thinCircle = new Turtle(width / 2, height / 2, w);
			thinCircle.hide();
		}

		if (mainFrame.musicPlayer.musicPoints != null) {
			if (pointIndex < mainFrame.musicPlayer.musicPoints.length - 1) {
				scaleMax = Math.max(mainFrame.musicPlayer.musicPoints[pointIndex], scaleMax);
				scaleMin = Math.min(mainFrame.musicPlayer.musicPoints[pointIndex], scaleMin);
				float scaleFactor = (scaleMax - scaleMin);
				//System.out.println("Scale Factor: " + scaleFactor);
				int x = (int) (((mainFrame.musicPlayer.musicPoints[pointIndex] / scaleFactor) * width / 100));
				int y = (int) (((mainFrame.musicPlayer.musicPoints[pointIndex] / scaleFactor) * height / 100));
				//System.out.println("x: " + x + " y: " + y);
				int size = (int) (scaleFactor * r.nextInt(20));
				//System.out.println("Size: " + size);
				thinCircle.drawShapes(1, size, 60, thinCircle.getXPos() + x, thinCircle.getYPos() + y, 0, 0, setRandomColor());

			}
			if (pointIndex >= mainFrame.musicPlayer.musicPoints.length - 1) {
				pointIndex = 0;
			} else {
				pointIndex++;
			}
		}
	}

	/**
	 * Draw Think Circles
	 */
	private void drawThinkBandCircle() {
		for (int i = 0; i < thickCircles.length; i++) {
			if (thickCircles[i] == null) {
				thickCircles[i] = new Turtle(width / 2, height / 2, w);
				thickCircles[i].hide();
			}
		}
		for (Turtle thickCircle : thickCircles) {
			if (mainFrame.musicPlayer.musicPoints != null) {
				if (pointIndex < mainFrame.musicPlayer.musicPoints.length - 1) {
					scaleMax = Math.max(mainFrame.musicPlayer.musicPoints[pointIndex], scaleMax);
					scaleMin = Math.min(mainFrame.musicPlayer.musicPoints[pointIndex], scaleMin);
					float scaleFactor = (scaleMax - scaleMin);
					//System.out.println("Scale Factor: " + scaleFactor);
					int x = (int) (((mainFrame.musicPlayer.musicPoints[pointIndex] / scaleFactor) * width / 100));
					int y = (int) (((mainFrame.musicPlayer.musicPoints[pointIndex] / scaleFactor) * height / 100));
					//System.out.println("x: " + x + " y: " + y);
					int size = (int) (scaleFactor * r.nextInt(20));
					//System.out.println("Size: " + size);
					thickCircle.drawShapes(5, size, 60, thickCircle.getXPos() + x, thickCircle.getYPos() + y, 0, 0, setRandomColor());
				}

			}

		}
		if (mainFrame.musicPlayer.musicPoints != null) {
			if (pointIndex >= mainFrame.musicPlayer.musicPoints.length) {
				pointIndex = 0;
			} else {
				pointIndex++;
			}
		}
	}

	/**
	 * Draw Lines
	 */
	int x = 0;

	private void drawLines() {
		if (lines.length != mainFrame.musicPlayer.musicPoints.length) {
			lines = new lines[mainFrame.musicPlayer.musicPoints.length];
		}
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] == null) {

				if (x > width) x = 0;
				else x = x + 2;
				lines[i] = new lines(x, height - 20, new Turtle(w));
				lines[i].line.setPenColor(Color.RED);
			}
		}


		float value = 2;
		for (MusicMain.lines line : lines) {
			line.line.penUp();
			line.line.moveTo(line.x, line.y);
			line.line.penDown();

			if (pointIndex <= mainFrame.musicPlayer.musicPoints.length - 1) {
				scaleMax = Math.max(mainFrame.musicPlayer.musicPoints[pointIndex], scaleMax);
				scaleMin = Math.min(mainFrame.musicPlayer.musicPoints[pointIndex], scaleMin);
				float scaleFactor = scaleMax - scaleMin;
				value = (mainFrame.musicPlayer.musicPoints[pointIndex] / scaleFactor) * height / 3;
			}
			if (value < 5) value = 5;

			line.line.forward((int) value);


			if (pointIndex >= mainFrame.musicPlayer.musicPoints.length) {
				pointIndex = 0;
			} else {
				pointIndex++;
			}
		}


	}

	/**
	 * Draw Tree
	 */
	private void tree() {
		if (!mainFrame.musicPlayer.playing) return;
		for (int i = 0; i < trees.length; i++) {
			if (trees[i] == null) {
				trees[i] = new Turtle(width / 2, height - 40, w);
				trees[i].hide();

			}
		}
		if (trees != null) {
			int treeIndex;
			for (treeIndex = 0; treeIndex < trees.length; treeIndex++) {
				float point = mainFrame.musicPlayer.musicPoints[pointIndex] * 60;

				if (trees[treeIndex] != null) {
					trees[treeIndex].setPenColor(setRandomColor());
					int randDir = r.nextInt(2);
					int randPos = r.nextInt(60);

					trees[treeIndex].penUp();
					if (randDir == 0) {

						trees[treeIndex].moveTo(trees[treeIndex].getXPos() + randPos, trees[treeIndex].getYPos());
					} else {
						trees[treeIndex].moveTo(trees[treeIndex].getXPos() - randPos, trees[treeIndex].getYPos());
					}
					trees[treeIndex].penDown();
					Branch(trees[treeIndex], point, 0, 10);
				}
				if (pointIndex >= mainFrame.musicPlayer.musicPoints.length - 1) {
					pointIndex = 0;
				} else {
					pointIndex++;
				}
			}
		}


	}

	/**
	 * @param t    Turtle
	 * @param x1   passed in current pixel amount to move
	 * @param a    passed in current angle
	 * @param runs the amount of times to run recursive per recursive call
	 */
	private void Branch(Turtle t, double x1, float a, int runs) {
		if (runs <= 0) {

			return;
		} else if (mainFrame.visType != MainFrame.VisType.Tree) {

			t.clearPath();
			return;

		}
		double x2 = x1 + (Math.cos(Math.toRadians(90)));
		t.forward((int) x2);
		t.turn(a);

		Branch(t, x2, a - 10, runs - 1);
		Branch(t, x2, a + 10, runs - 1);
		t.turn(-a);
		t.forward((int) -x2);
	}


	/**
	 * @return the random color set
	 */
	Color color = Color.white;

	private Color setRandomColor() {

		if (waitCount >= maxWaitCount) {
			Random random = new Random();
			int rc = random.nextInt(7);
			//System.out.println(rc);
			switch (rc) {
				case 0:
					color = Color.ORANGE;
					break;
				case 1:
					color = Color.YELLOW;
					break;
				case 2:
					color = Color.MAGENTA;
					break;
				case 3:
					color = Color.GREEN;
					break;
				case 4:
					color = Color.CYAN;
					break;
				case 5:
					color = Color.BLUE;
					break;
				case 6:
					color = Color.PINK;
					break;
			}
			waitCount = 0;
		}
		waitCount++;
		return color;
	}


}

/**
 * lines is a container class for lines visualization
 */
class lines {
	int x;
	int y;
	Turtle line;

	lines(int x, int y, Turtle line) {
		this.x = x;
		this.y = y;
		this.line = line;
		this.line.hide();
	}
}
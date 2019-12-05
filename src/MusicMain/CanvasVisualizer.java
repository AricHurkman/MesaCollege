package MusicMain;

import MusicMain.bookClasses.Turtle;
import MusicMain.bookClasses.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CanvasVisualizer {
	private JFrame frame;
	private int width = 600;
	private int height = 600;
	private int index = 0;

	World w;
	Random r = new Random();

	Turtle thinCircle;
	Turtle[] thickCircles = new Turtle[10];
	Turtle[] Lines = new Turtle[width];
	Turtle[] trees = new Turtle[5];

	int treeIndex = 0;

	float scaleMax = 0.0f;
	float scaleMin = 10.0f;
	//Lines
	int maxPixel = 2;
	//Color
	private int maxCount = 10;
	private int count = maxCount;

	private MainFrame.VisType visType;

	private boolean runningTree = false;

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	void Render(MusicPlayer player, MainFrame.VisType visType) {

		this.visType = visType;
		if (player == null) return;
		if (w == null) {
			w = new World(width, height, frame);
		}

		if (player.playing) {
			switch (this.visType) {

				case ThinCircle:
					drawSingleBandCircle(player);
					break;
				case ThickCircle:
					drawThinkBandCircle(player);
					break;
				case Lines:
					lines(player);
					break;
				case Tree:
					tree(player);
					break;
			}


		}
	}


	private void drawSingleBandCircle(MusicPlayer player) {
		if (thinCircle == null) {
			thinCircle = new Turtle(width / 2, height / 2, w);
			thinCircle.hide();
		}

		if (player.musicPoints != null) {
			if (index < player.musicPoints.length - 1) {
				scaleMax = Math.max(player.musicPoints[index], scaleMax);
				scaleMin = Math.min(player.musicPoints[index], scaleMin);
				float scaleFactor = (scaleMax - scaleMin);
				//System.out.println("Scale Factor: " + scaleFactor);
				int x = (int) (((player.musicPoints[index] / scaleFactor) * width / 100));
				int y = (int) (((player.musicPoints[index] / scaleFactor) * height / 100));
				//System.out.println("x: " + x + " y: " + y);
				int size = (int) (scaleFactor * r.nextInt(70));
				//System.out.println("Size: " + size);
				thinCircle.drawShapes(1, size, 60, thinCircle.getXPos() + x, thinCircle.getYPos() + y, 0, 0, setRandomColor());

			}
			if (index >= player.musicPoints.length -1) {
				index = 0;
			} else {
				index++;
			}
		}
	}

	private void drawThinkBandCircle(MusicPlayer player) {

		for (int i = 0; i < thickCircles.length; i++) {
			if (thickCircles[i] == null) {
				thickCircles[i] = new Turtle(width / 2, height / 2, w);
				thickCircles[i].hide();
			}
		}
		for (int i = 0; i < thickCircles.length; i++) {
			if (player.musicPoints != null) {
				if (index < player.musicPoints.length - 1) {
					scaleMax = Math.max(player.musicPoints[index], scaleMax);
					scaleMin = Math.min(player.musicPoints[index], scaleMin);
					float scaleFactor = (scaleMax - scaleMin);
					//System.out.println("Scale Factor: " + scaleFactor);
					int x = (int) (((player.musicPoints[index] / scaleFactor) * width / 100));
					int y = (int) (((player.musicPoints[index] / scaleFactor) * height / 100));
					//System.out.println("x: " + x + " y: " + y);
					int size = (int) (scaleFactor * r.nextInt(70));
					//System.out.println("Size: " + size);
					thickCircles[i].drawShapes(5, size, 60, thickCircles[i].getXPos() + x, thickCircles[i].getYPos() + y, 0, 0, setRandomColor());
				}

			}

		}
		if (player.musicPoints != null) {
			if (index >= player.musicPoints.length) {
				index = 0;
			} else {
				index++;
			}
		}
	}


	private void lines(MusicPlayer player) {
		for (int i = 0; i < Lines.length; i++) {
			if (Lines[i] == null) {
				Lines[i] = new Turtle(0, height - 40, w);
				Lines[i].hide();
				Lines[i].setPenColor(Color.red);
			}
		}

		for (int i = 0; i < Lines.length; i++) {
			if (index < player.musicPoints.length) {
				scaleMax = Math.max(player.musicPoints[index], scaleMax);
				scaleMin = Math.min(player.musicPoints[index], scaleMin);
				float scaleFactor = scaleMax - scaleMin;
				float value = (player.musicPoints[index] / scaleFactor) * height / 6;
				Lines[i].penUp();
				Lines[i].moveTo(i, height - 40);
				Lines[i].penDown();
				Lines[i].forward((int) value);
			}
			if (index >= player.musicPoints.length) {
				index = 0;
			} else {
				index++;
			}

		}


	}


	private void tree(MusicPlayer player) {


		for (int i = 0; i < trees.length; i++) {
			if (trees[i] == null) {
				trees[i] = new Turtle(width / 2, height - 40, w);
				trees[i].hide();
				trees[i].setPenColor(Color.GREEN);
			}


		}

		for (treeIndex = 0; treeIndex < trees.length; treeIndex++) {
			float point = player.musicPoints[index] * 100;

			if (trees[treeIndex] != null) {
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
			if (index >= player.musicPoints.length - 1) {
				index = 0;
			} else {
				index++;
			}
		}


	}

	private void Branch(Turtle t, double x1, float a, int runs) {
		if (runs <= 0) {

			return;
		} else if (this.visType != MainFrame.VisType.Tree) {

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

	private Color color;

	private Color setRandomColor() {

		if (count >= maxCount) {
			Random random = new Random();
			int rc = random.nextInt(7);
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
					color = Color.WHITE;
					break;
			}
			count = 0;
		}
		count++;
		return color;
	}


}

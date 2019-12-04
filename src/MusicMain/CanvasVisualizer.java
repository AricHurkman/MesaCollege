package MusicMain;

import MusicMain.bookClasses.Turtle;
import MusicMain.bookClasses.World;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class CanvasVisualizer extends Canvas {


	int width = 600;
	int height = 600;
	int index = 0;
	//Circle
	int circleCount = 30;
	//Lines
	int maxPixel = 2;
	//Color
	int maxCount = 10;
	int count = maxCount;


	public void Render(MusicPlayer player, MainFrame.VisType visType) {

		if (player == null) return;
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		if (player.playing) {
			switch (visType) {

				case ThinCircle:
					drawSingleBandCircle(30, 50, 30, 550, g, player);
					break;
				case ThickCircle:
					drawThinkBandCircle(100, 60, 10, 200, g, player);
					break;
				case Lines:
					lines(g, player);
					break;
				case Tree:
					tree(player);
					break;
			}


		}
		g.dispose();
		bs.show();
	}

	private void drawSingleBandCircle(int x, int y, int min, int max, Graphics g, MusicPlayer player) {

		//System.out.println(count);
		g.setColor(setRandomColor());

		for (int i = 0; i < circleCount; i++) {
			if (player.musicPoints != null) {

				int w = 0;
				int h = 0;
				if (index < player.musicPoints.length) {
					w = (int) ((width / player.musicPoints[index]) / 100) * 2 + (i / 2);
					h = (int) ((height / player.musicPoints[index]) / 100) * 2 + (i / 2);
					g.drawOval(30, 50, Clamp.clamp(w, min, max), Clamp.clamp(h, min, max / 2));
				}
				if (index >= player.musicPoints.length) {
					index = 0;
				} else {
					index++;
				}
			}

		}


	}

	private void drawThinkBandCircle(int x, int y, int min, int max, Graphics g, MusicPlayer player) {
		//System.out.println(count);
		g.setColor(setRandomColor());

		for (int i = 0; i < circleCount; i++) {
			if (player.musicPoints != null) {

				int w = 0;
				int h = 0;
				if (index < player.musicPoints.length) {
					w = (int) ((width / player.musicPoints[index]) / 100) * 2 + (i / 2);
					h = (int) ((height / player.musicPoints[index]) / 100) * 2 + (i / 2);
					g.drawOval(x, y, Clamp.clamp(w, min, max), Clamp.clamp(h, min, max));
				}

			}

		}
		if (index >= player.musicPoints.length) {
			index = 0;
		} else {
			index++;
		}


	}


	private void lines(Graphics g, MusicPlayer player) {
		float scaleMax = 0.0f;
		float scaleMin = 10.0f;
		g.setColor(setRandomColor());
		for (int i = 0; i < 1000; i++) {
			if (index < player.musicPoints.length) {
				scaleMax = Math.max(player.musicPoints[index], scaleMax);
				scaleMin = Math.min(player.musicPoints[index], scaleMin);
				float scaleFactor = scaleMax - scaleMin;
				float value = (player.musicPoints[index] / scaleFactor) * height / 6;
				float x = ((index * value) / 360) + i;
				g.drawLine(300, 200, (int) x, (int) -value + i);
				g.drawLine(300, 200, (int) x, (int) value + i);

			}
			if (index >= player.musicPoints.length) {
				index = 0;
			} else {
				index++;
			}

		}
		g.dispose();


	}


	World w;
	Turtle t;
	Random r = new Random();

	private void tree(MusicPlayer player) {
		if (w == null) {
			w = new World(400, 400);

		}

		if (t == null) {
			t = new Turtle(width / 2, height - 40, w);
			t.hide();
			t.setPenColor(Color.GREEN);
		}

		for (int i = 0; i < 10; i++)
			if (index < player.musicPoints.length) {
				float point = player.musicPoints[index] * 100;

				int pos = r.nextInt(2);
				if (pos == 0) {
					t.moveTo(t.getXPos() + 2, t.getYPos());
				} else {
					t.moveTo(t.getXPos() - 2, t.getYPos());
				}

				Branch(t, point, 0, 10);

			}
		if (index >= player.musicPoints.length) {
			index = 0;
		} else {
			index++;
		}

	}

	private void Branch(Turtle t, double x1, float a, int runs) {
		if (runs <= 0) return;
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

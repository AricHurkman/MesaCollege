package MusicMain;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class CanvasVisualizer extends Canvas {


	public static int circleCount = 30;

	int width = 580;
	int height = 600;
	int index = 0;
	//Circle
	int maxCount = 3;
	int count = maxCount;
	Color color;

	public void Render(MusicPlayer player) {

		if (player == null) return;
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(10, 10, width, height);
		if (player.playing) {
			drawSingleBandCircle(30, 50,30, 550, g, player);
			//drawThinkBandCircle(100, 60, 10, 200, g, player);
		} g.dispose();
		bs.show();
	}

	private void drawSingleBandCircle(int x, int y, int min, int max, Graphics g, MusicPlayer player) {
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
		//System.out.println(count);
		g.setColor(color);

		for (int i = 0; i < circleCount; i++) {
			if (player.musicPoints != null) {

				int w = 0;
				int h = 0;
				if (index < player.musicPoints.length) {
					w = (int) ((width / player.musicPoints[index]) / 100) * 2 + (i / 2);
					h = (int) ((height / player.musicPoints[index]) / 100) * 2 + (i / 2);
					g.drawOval(30, 50, Clamp.clamp(w, min, max), Clamp.clamp(h, min, max /2));
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
		//System.out.println(count);
		g.setColor(color);

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

}

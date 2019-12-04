package MusicMain.bookClasses;

import java.awt.*;

public class ExplainVideo1 {

	public static void main(String[] args) {
		tree();
	}

	private static void tree() {
		World w = new World();
		Turtle t = new Turtle(300, 400, w);
		t.hide();
		Color color = new Color(101, 67, 33);
		t.setPenColor(color);
		t.setPenWidth(3);
		t.forward(100);
		t.penUp();
		t.backward(90);
		t.setPenWidth(1);
		t.penDown();
		drawBranches(t, 20, 0, 10);

	}

	private static void drawBranches(Turtle t, int x1, double a, int runs) {
		if (runs <= 0) return;

		int x2 = x1 + (int) (Math.cos(Math.toRadians(90)));

		t.forward(x2);
		t.turn(a);

		drawBranches(t, x2, a - 5, runs-1);
		drawBranches(t, x2, a + 5, runs-1);
		t.turn(-a);
		t.forward(-x2);

	}
}

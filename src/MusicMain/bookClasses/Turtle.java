package MusicMain.bookClasses;

import java.awt.*;

/**
 * Class that represents a turtle which is similar to a Logo turtle.
 * This class inherts from SimpleTurtle and is for students
 * to add methods to.
 * <p>
 * Copyright Georgia Institute of Technology 2004
 *
 * @author Barb Ericson ericson@cc.gatech.edu
 */
public class Turtle extends SimpleTurtle {
	////////////////// constructors ///////////////////////

	int x, y;

	/**
	 * Constructor that takes the x and y and a picture to
	 * draw on
	 *
	 * @param x       the starting x position
	 * @param y       the starting y position
	 * @param picture the picture to draw on
	 */
	public Turtle(int x, int y, Picture picture) {
		// let the parent constructor handle it
		super(x, y, picture);
	}

	/**
	 * Constructor that takes the x and y and a model
	 * display to draw it on
	 *
	 * @param x              the starting x position
	 * @param y              the starting y position
	 * @param modelDisplayer the thing that displays the model
	 */
	public Turtle(int x, int y, ModelDisplay modelDisplayer) {
		// let the parent constructor handle it
		super(x, y, modelDisplayer);
	}

	/**
	 * Constructor that takes the model display
	 *
	 * @param modelDisplay the thing that displays the model
	 */
	public Turtle(ModelDisplay modelDisplay) {
		// let the parent constructor handle it
		super(modelDisplay);
	}

	/**
	 * Constructor that takes a picture to draw on
	 *
	 * @param p the picture to draw on
	 */
	public Turtle(Picture p) {
		// let the parent constructor handle it
		super(p);
	}

	/////////////////// methods ///////////////////////


	public static void main(String[] args) {
		World earth = new World();
		Turtle t1 = new Turtle(earth);
		t1.forward();
	}


	public void drawShapes(int count, int size, int sides, int x, int y, int offsetX, int offsetY, Color c) {

		this.setColor(c);
		int turnAngle = 360 / sides;

		for (int i = 0; i < count; i++) {
			if (i > 0) {
				x = x + offsetX;
				y = y + offsetY;
				noMarkMove(x, y);
			} else noMarkMove(x, y);
			for (int j = sides; j >= 0; j--) {
				this.forward(size);
				this.turn(turnAngle);
			}
		}
	}

	// Method for movingTo(x,y) without marking.
	public void noMarkMove(int x, int y) {
		this.penUp();
		this.moveTo(x, y);
		this.penDown();
	}

} // this } is the end of class Turtle, put all new methods before this
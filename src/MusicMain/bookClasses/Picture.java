package MusicMain.bookClasses;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 * <p>
 * Copyright Georgia Institute of Technology 2004-2005
 *
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
	///////////////////// constructors //////////////////////////////////

	/**
	 * Constructor that takes no arguments
	 */
	public Picture() {
		/* not needed but use it to show students the implicit call to super()
		 * child constructors always call a parent constructor
		 */
		super();
	}

	/**
	 * Constructor that takes a file name and creates the picture
	 *
	 * @param fileName the name of the file to create the picture from
	 */
	public Picture(String fileName) {
		// let the parent class handle this fileName
		super(fileName);
	}

	/**
	 * Constructor that takes the width and height
	 *
	 * @param width  the width of the desired picture
	 * @param height the height of the desired picture
	 */
	public Picture(int width, int height) {
		// let the parent class handle this width and height
		super(width, height);
	}

	/**
	 * Constructor that takes a picture and creates a
	 * copy of that picture
	 */
	public Picture(Picture copyPicture) {
		// let the parent class do the copy
		super(copyPicture);
	}

	/**
	 * Constructor that takes a buffered image
	 *
	 * @param image the buffered image to use
	 */
	public Picture(BufferedImage image) {
		super(image);
	}

	////////////////////// methods ///////////////////////////////////////

	/**
	 * Method to return a string with information about this picture.
	 *
	 * @return a string with information about the picture such as fileName,
	 * height and width.
	 */
	public String toString() {
		String output = "Picture, filename " + getFileName() + " height " + getHeight() + " width " + getWidth();
		return output;

	}

	public static void main(String[] args) {
		String fileName = FileChooser.pickAFile();
		Picture pictObj = new Picture(fileName);
		pictObj.explore();
	}

	public static Picture creativeCollage(Picture p1, Picture p2, Picture p3) {


		Picture finalImage = null;
		List<Picture> picturs = new ArrayList<>();
		picturs.add(p1);
		picturs.add(p2);
		picturs.add(p3);

		int index0 = 0;


		return finalImage;
	}



	/*
	 * Code Created By Aric Hurkman
	 */

	//createSolid takes in doubles to multiply pixels already in the image and luminance to ajust the light blance

	public void createSold(int redMul, int greenMul, int blueMul) {
		Pixel[] pixArray = this.getPixels();
		Pixel pixel = null;

		int redValue = 0;
		int greenValue = 0;
		int blueValue = 0;

		int i = 0;
		for (; i < pixArray.length; ) {

			pixel = pixArray[i];
			redValue = clamp((pixel.getRed() * redMul), 0, 255);
			greenValue = clamp((pixel.getGreen() * blueMul), 0, 255);
			blueValue = clamp((pixel.getBlue() * blueMul), 0, 255);
			Color c = new Color(redValue, greenValue, blueValue);
			pixel.setColor(c);
			i++;
		}
	}


	//iteration changed from create sold to every other pixel (even) get set.
	public void createPattern(int redMul, int greenMul, int blueMul) {

		Pixel[] pixArray = this.getPixels();
		Pixel pixel = null;

		int redValue = 0;
		int greenValue = 0;
		int blueValue = 0;
		int i = 0;
		for (; i < pixArray.length; ) {
			pixel = pixArray[i];
			redValue = clamp((pixel.getRed() * redMul), 0, 255);
			greenValue = clamp((pixel.getGreen() * blueMul), 0, 255);
			blueValue = clamp((pixel.getBlue() * blueMul), 0, 255);
			Color c = new Color(redValue, greenValue, blueValue);
			pixel.setColor(c);
			i = i + 2;
		}
	}

	public void myNegative(int index0, int index1) {

		Pixel[] pixels = this.getPixels();
		Pixel p = null;
		while (index0 < index1) {
			p = pixels[index0];
			int red = 255 - p.getRed();
			int green = 255 - p.getGreen();
			int blue = 255 - p.getBlue();
			Color c = new Color(red, green, blue);
			p.setColor(c);
			index0++;
		}

	}

	public void negate() {
		Pixel[] pixArray = this.getPixels();
		Pixel pixel = null;
		int redValue, blueValue, greenValue = 0;

		for (int i = 0; i < pixArray.length; i++) {
			pixel = pixArray[i];

			redValue = pixel.getRed();
			greenValue = pixel.getGreen();
			blueValue = pixel.getBlue();

			pixel.setColor(new Color(255 - redValue, 255 - greenValue, 255 - blueValue));
		}
	}

	public void myGreyScale(int index0, int index1) {
		Pixel[] pixels = this.getPixels();
		Pixel p = null;
		while (index0 < index1) {
			p = pixels[index0];
			int intensity = (int) ((p.getRed() + p.getGreen() + p.getBlue()) / 3);
			Color c = new Color(intensity, intensity, intensity);
			p.setColor(c);
			index0++;
		}

	}

	public void myFilter(int index0, int index1, int red, int green, int blue) {
		Pixel[] pixels = this.getPixels();
		Color c0 = null;
		Color c1 = null;
		Color c2 = null;
		Color c3 = null;
		Pixel p0 = null;
		Pixel p1 = null;
		Pixel p2 = null;
		Pixel p3 = null;
		while (index0 < index1) {
			p0 = pixels[index0];
			c0 = new Color((int) clamp(((255 - p0.getRed()) * red), 0, 255), p0.getGreen(), p0.getBlue());
			if (index0 + 1 < index1) {
				p1 = pixels[index0 + 1];
				c1 = new Color((int) clamp(((255 - p1.getRed()) * .6), 0, 255), p1.getGreen(), p1.getBlue());
			}

			if (index0 + 2 < index1) {
				p2 = pixels[index0 + 2];
				c2 = new Color((int) clamp(((255 - p2.getRed()) * .6), 0, 255), p2.getGreen(), p2.getBlue());
			}

			if (index0 + 3 < index1) {
				p3 = pixels[index0 + 3];
				c3 = new Color(p3.getRed(), p3.getGreen(), p3.getBlue());
			}
			p0.setColor(c3);
			p1.setColor(c2);
			p2.setColor(c1);
			p3.setColor(c0);
			index0 = index0 + 4;
		}


	}


	public void edgeDetection(double amount) {
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		double topAvg = 0.0;
		double bottomAvg = 0.0;

		int endY = this.getHeight() - 1;
		for (int y = 0; y < endY; y++) {
			for (int x = 0; x < this.getWidth(); x++) {

				topPixel = this.getPixel(x, y);
				bottomPixel = this.getPixel(x, y + 1);

				topAvg = topPixel.getAverage();
				bottomAvg = bottomPixel.getAverage();

				if (Math.abs(topAvg - bottomAvg) < amount) {
					topPixel.setColor(Color.WHITE);
				} else {
					topPixel.setColor(Color.BLACK);
				}

			}

		}

	}

	public void everyOtherPixel(Color color) {
		Pixel pixel = null;
		for (int y = 0; y < this.getHeight(); y += 2) {
			for (int x = 0; x < this.getWidth(); x += 2) {
				pixel = this.getPixel(x, y);
				pixel.setColor(color);

			}
		}
	}

	public void everyOtherColumn(Color color) {
		Pixel[] pixels = this.getPixels();
		for (int i = 0; i < pixels.length; i += 2) {
			pixels[i].setColor(color);
		}
	}

	public void everyOtherRowColor() {
		Pixel pixel = null;
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {

				pixel = this.getPixel(x, y);
				if (y % 2 == 0) {
					pixel.setColor(Color.RED);
				} else {
					pixel.setColor(Color.BLUE);
				}
			}
		}

	}


	public int clamp(int value, int min, int max) {
		if (value <= min) return min;
		else if (value >= max) return max;
		else return value;
	}

	public double clamp(double value, double min, double max) {
		if (value <= min) return min;
		else if (value >= max) return max;
		else return value;
	}
	/*
	 * End Code Created By Aric Hurkman
	 */


// this } is the end of class Picture, put all new methods before this
}
 
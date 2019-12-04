package MusicMain.bookClasses;
/**
 * Filename: CreateMultipleShapes.java
 * Name: Aric Hurkman
 * <p>
 * Date:09/19/19
 * <p>
 * Description: Creates multiple shapes base off of user input.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CreateMultipleShapes {

	private static String results = null;
	private static Turtle t;
	private static int x, y, size, offsetX, offsetY;

	public static void main(String[] args) throws Exception {

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("How many times do you want to draw the shape?");

			results = Readout(reader.readLine().toLowerCase());
			System.out.println(results);
//
//			int count = Integer.valueOf(results);
//
//			System.out.println("Either the Number Of Sides");
//			results = reader.readLine();
//
//			int sides = Integer.valueOf(results);
//			System.out.println("Enter the x-coordinate:");
//			results = reader.readLine();
//			x = Integer.valueOf(results);
//
//			System.out.println("Enter the y-coordinate:");
//			results = reader.readLine();
//			y = Integer.valueOf(results);
//
//			System.out.println("Enter the Offset X:");
//			results = reader.readLine();
//			offsetX = Integer.valueOf(results);
//
//			System.out.println("Enter the Offset Y:");
//			results = reader.readLine();
//			offsetY = Integer.valueOf(results);
//
//			System.out.println("Enter the size:");
//			results = reader.readLine();
//			size = Integer.valueOf(results);
//
//
//			// Color switch, user enter color from list, if invaild color picked default is Red.
//			System.out.println("Enter Color: Blue, Red, Green, Yellow");
//			results = reader.readLine();
//			Color c = null;
//
//			switch (results) {
//				case "Blue":
//					c = new Color(0, 0, 255);
//					break;
//				case "Red":
//					c = new Color(255, 0, 0);
//					break;
//				case "Green":
//					c = new Color(0, 255, 0);
//					break;
//				case "Yellow":
//					c = new Color(255, 255, 0);
//					break;
//				default:
//					System.out.println("No Valid Color Picked: Default Color Red Picked");
//					c = new Color(255, 0, 0);
//					break;
//			}
//
//			World w = new World(800, 600);//Creating world to be x = 800, y = 600.
//			t = new Turtle(w);
//
//			System.out.println("Count: " + count + "\n");
//			System.out.println("Shape Size: " + size + "\n");
//			System.out.println("Shape Side: " + sides + "\n");
//			System.out.println("X Position: " + x + "\n");
//			System.out.println("Y Position: " + y + "\n");
//			System.out.println("X Offset: " + offsetX + "\n");
//			System.out.println("Y Offset: " + offsetY + "\n");
//			System.out.println("Color: " + c + "\n");
//
//
//			t.drawShapes(count, size, sides, x, y, offsetX, offsetY, c);

		} catch (Exception e) {
			System.out.println(e);

		}

	}


	private static String Readout(String results) {
		String[] strings = results.split(" ");
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			strings[i] = strings[i].replaceFirst(String.valueOf(strings[i].charAt(0)), String.valueOf(strings[i].charAt(0)).toUpperCase());
			if (i >= strings.length) {
				stringBuilder.append(strings[i]);
			}else {
				stringBuilder.append(strings[i] + " ");
			}
		}
		return stringBuilder.toString();
	}
}
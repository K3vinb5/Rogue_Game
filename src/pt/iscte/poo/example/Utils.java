package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Utils {

	
	// Useful methods (will probably be moved to other class in the future)
	
	public static boolean withinBounds(int windowWidth, int windowHeight, Point2D newPosition) {
		return (newPosition.getX() < windowWidth && newPosition.getY() < windowHeight && newPosition.getX() >= 0 && newPosition.getY() >= 0);
	}
	
}

package pt.iscte.poo.level;

import pt.iscte.poo.utils.Point2D;

public class Door  extends DoorComponent{

	public Door(Point2D position, String room, Point2D newPosition, String keyID) {
		super(position, room, newPosition, keyID);
	}

	public Door(Point2D position, String room, Point2D newPosition) {
		super(position, room, newPosition);
	}

}

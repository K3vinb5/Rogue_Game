package pt.iscte.level;

import pt.iscte.poo.utils.Point2D;

public class RedDoor extends DoorComponent{

	// Door Open
	public RedDoor(Point2D position, String room, Point2D newPosition) {
		super(position, room, newPosition);
		this.setName("DoorOpen_Red");
	}
	// Door Closed
	public RedDoor(Point2D position, String room, Point2D newPosition, String keyID) {
		super(position, room, newPosition, keyID);
		this.setName("DoorClosed_Red");
	}

	
	
}

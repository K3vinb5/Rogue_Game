package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement implements Transposible{

	private int room;
	private Point2D newPostion;
	private String keyID;
	boolean isLocked;
	
	//Constructor 1
	public Door( Point2D position, String room, Point2D newPosition, String keyID) {
		super("DoorClosed", position, 1);
		this.keyID = keyID;
		this.newPostion = newPosition;
		this.room=Integer.parseInt(room.split("room")[1]);
		this.isLocked = true;
	}
	
	//Constructor 2
	public Door(Point2D position, String room, Point2D newPosition) {
		super("DoorOpen", position, 1);
		this.newPostion = newPosition;
		this.room = Integer.parseInt(room.split("room")[1]);
		this.isLocked = false;
	}
	public int getRoom() {
		return room;
	}

	public Point2D getNewPostion() {
		return newPostion;
	}

	public String getKeyID() {
		return keyID;
	}

	@Override
	public int getLayer() {
		return 1;
	}
}

package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public abstract class DoorComponent extends GameElement implements Transposible{

	private int room;
	private Point2D newPostion;
	private String keyID;
	private boolean isLocked;
	
	//Constructor 1
	public DoorComponent( Point2D position, String room, Point2D newPosition, String keyID) {
		super("DoorClosed", position, 1);
		this.keyID = keyID;
		this.newPostion = newPosition;
		this.room=Integer.parseInt(room.split("room")[1]);
		this.isLocked = true;
	}
	
	//Constructor 2
	public DoorComponent(Point2D position, String room, Point2D newPosition) {
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
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public void setLocked(boolean b) {
		if (b) {
			this.setName("DoorClosed");
			this.isLocked = true;
		}else {
			this.setName("DoorOpen");
			this.isLocked = false;
		}
	}

	@Override
	public int getLayer() {
		return 1;
	}
}

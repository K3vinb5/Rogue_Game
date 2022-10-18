package pt.iscte.poo.example;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

public abstract class GameElement implements ImageTile{
	
	// Game Elements common attributes
	private String name;
	private Point2D position; 
	private int layer; 
	
	// GameElements Constructor
	public GameElement(String name, Point2D position, int layer) {
		this.name = name;
		this.position = position;
		this.layer = layer;
	}
	
	// Getters
	public int getLayer() {
		return layer;
	}

	public  String getName() {
		return name;
	}

	public  Point2D getPosition() {
		return position;
	}
	
	// Setters
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}

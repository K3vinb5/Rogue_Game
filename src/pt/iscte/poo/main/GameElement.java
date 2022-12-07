package pt.iscte.poo.main;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

public abstract class GameElement implements ImageTile{
	
	// Game Elements common attributes
	private String name;
	private Point2D position; 
	private int layer; 
	private Engine gui = Engine.getInstance();
	
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
	
	public Engine getEngine() {
		return gui;
	}
	
	//temp
	@Override
	public String toString() {
		return "Name: " + this.getName() + " X: " + this.getPosition().getX() + " Y: " + this.getPosition().getY();
	}
}

package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Entity extends GameElement {
	
	// Entities exclusive attributes
	public int health;
	public int attack;
	
	// Entity Constructor
	public Entity(String name, Point2D position, int health, int attack) {
		super(name, position, 0);
		this.health = health;
		this.attack = attack;
	}

	// Getters
	public int getHealth() {
		return health;
	}
	
	public int getAttack() {
		return attack;
	}
	// Setters
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	// Entity related methods
	public void move(Direction d) {

		Point2D newPosition = this.getPosition().plus(d.asVector());
		boolean withinBounds = (newPosition.getX() < Engine.GRID_WIDTH && newPosition.getY() < Engine.GRID_HEIGHT && newPosition.getX() >= 0 && newPosition.getY() >= 0);
		
		//Checks if new position is valid and updates matrix that keeps track of occupied positions in the map
		if  (withinBounds && Engine.isValid(newPosition)){
			this.setPosition(newPosition);
		}
	}
	
	public List<Point2D> checkSurrounding() {
		
		List<Point2D> returnPositions = new ArrayList<>();
		
		List<Direction> directionarray = new ArrayList<>();
		directionarray.add(Direction.DOWN);
		directionarray.add(Direction.LEFT);
		directionarray.add(Direction.RIGHT);
		directionarray.add(Direction.UP);
		
		for (Direction d : directionarray) {
			Point2D position = getPosition().plus(d.asVector());
			returnPositions.add(position);
		}
		return returnPositions;
	}
	
	
	// Because all entities layers are 0:
	@Override
	public int getLayer() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Name: " + this.getName() + " X: " + this.getPosition().getX() + " Y: " + this.getPosition().getY();
	}
		
}

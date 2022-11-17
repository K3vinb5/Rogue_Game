package pt.iscte.poo.example;

//import java.util.ArrayList;
//import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;


public abstract class Entity extends GameElement {
	
	// Entities exclusive attributes
	private double health;
	private double attack;
	
	// Entity Constructor
	public Entity(String name, Point2D position, double health, double attack) {
		super(name, position, 2);
		this.health = health;
		this.attack = attack;
	}

	// Getters
	public double getHealth() {
		if (health < 0) {
			return 0;
		}
		return health;
	}
	
	public double getAttack() {
		return attack;
	}
	
	// Setters
	public void setHealth(double health) {
		this.health = health;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	// Entity related methods
	public boolean move(Direction d) {

		Point2D newPosition = this.getPosition().plus(d.asVector());
		boolean withinBounds = (newPosition.getX() < Engine.GRID_WIDTH && newPosition.getY() < Engine.GRID_HEIGHT && newPosition.getX() >= 0 && newPosition.getY() >= 0);
		
		//Checks if new position is valid
		if  (withinBounds && Engine.getLevel().isValid(newPosition)){
			this.setPosition(newPosition);
			return true;
		}else if (Engine.getLevel().getEntity(newPosition) instanceof Entity) {
			return true;
		}else {
			return false;
		}
	}

	// Because all entities layers are 1:
	@Override
	public int getLayer() {
		return 2;
	}
		
}

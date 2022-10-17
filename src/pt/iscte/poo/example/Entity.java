package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Entity extends GameElement{
	
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
	public void move(Direction d, int width, int height) {
		Point2D newPosition = this.getPosition().plus(d.asVector());
		//Checks if new position is valid
		if ( Utils.withinBounds(width, height, newPosition) ){
			this.setPosition(newPosition);
		}
	}
	
	// Because all entities layers are 0:
	@Override
	public int getLayer() {
		return 0;
	}
		
}

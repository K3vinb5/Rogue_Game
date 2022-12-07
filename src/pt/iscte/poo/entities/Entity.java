package pt.iscte.poo.entities;

import pt.iscte.poo.interfaces.*;
import pt.iscte.poo.main.*;

//import java.util.ArrayList;
//import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;


public abstract class Entity extends GameElement implements Movable, Attackable{
	
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
	@Override
	public boolean move(Direction d) {

		Point2D newPosition = this.getPosition().plus(d.asVector());
		boolean withinBounds = (newPosition.getX() < Engine.GRID_WIDTH && newPosition.getY() < Engine.GRID_HEIGHT && newPosition.getX() >= 0 && newPosition.getY() >= 0);
		
		//Checks if new position is valid
		if  (withinBounds && getEngine().getLevel().isValid(newPosition, this)){
			this.setPosition(newPosition);
			return true;
		}else if (getEngine().getLevel().getEntity(newPosition) instanceof Entity) {
			return true;
		}else {
			return false;
		}
	}
	
	public void attackEntity(Entity attacked) {
		Entity e = null;
		boolean someoneDied = false;
		Entity attacker = this;
		if (attacked != null && !attacked.equals(attacker) && !(attacker instanceof Enemy && attacked instanceof Enemy)) {
			attacked.setHealth(attacked.getHealth() - attacker.getAttack());
			if (attacked.getHealth() <= 0) {
				getEngine().removeSprite(attacked);
				getEngine().redrawHealthBarIf(attacked.equals(getEngine().getLevel().getHero()));
				e = attacked;
				someoneDied = true;
			}
			getEngine().redrawHealthBarIf(attacked.equals(getEngine().getLevel().getHero()));
		}
		if (someoneDied && !(e instanceof Hero)) {
			getEngine().getLevel().getElementList().remove(e);
			getEngine().setEnemiesKilled(getEngine().getEnemiesKilled() + 1);
		}	else if (someoneDied && e instanceof Hero) {
			//gui.dispose();
		}
	}

	// Because all entities layers are 2:
	@Override
	public int getLayer() {
		return 2;
	}
		
}

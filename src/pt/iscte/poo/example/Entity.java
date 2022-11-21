package pt.iscte.poo.example;

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
	
	public boolean isValid(Point2D newPosition) {
		boolean returnValue = true;
		for (GameElement g : Engine.getLevel().getElementList()) {
			if (newPosition.equals(g.getPosition()) && (g instanceof Attackable) ) { //posicao ser igual a de um atacavel
				returnValue = false;
			}
			if (newPosition.equals(g.getPosition()) && !(g instanceof Entity) && !(g instanceof Transposible)) { //Posicao ser igual a de um objeto nao transposivel
				returnValue = false;
			}
			if (newPosition.equals(g.getPosition()) &&  (g instanceof Transposible)) { //Posicao ser igual a de um objeto transposivel
				returnValue = true;
			}
			if ( ( newPosition.equals(g.getPosition()) ) && ( this instanceof Enemy) && (g instanceof Door) ){
				returnValue = false;
			}
		}
		return returnValue; //Posicao nao ser igual a nada
	}
	
	// Entity related methods
	@Override
	public boolean move(Direction d) {

		Point2D newPosition = this.getPosition().plus(d.asVector());
		boolean withinBounds = (newPosition.getX() < Engine.GRID_WIDTH && newPosition.getY() < Engine.GRID_HEIGHT && newPosition.getX() >= 0 && newPosition.getY() >= 0);
		
		//Checks if new position is valid
		if  (withinBounds && isValid(newPosition)){
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

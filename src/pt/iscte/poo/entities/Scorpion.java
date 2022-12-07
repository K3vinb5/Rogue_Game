package pt.iscte.poo.entities;

import pt.iscte.poo.interfaces.Enemy;
import pt.iscte.poo.utils.Point2D;

public class Scorpion extends Entity implements Enemy {

	private boolean hasAttacked;
	
	public Scorpion(Point2D position) {
		super("Scorpion", position, 2, 1);
		this.hasAttacked = false;
	}
	
	public boolean hasAttacked() {
		return hasAttacked;
	}
	
	public void setHasAttacked(boolean b) {
		this.hasAttacked = b;
	}

	//gets executed every time the scorpion tries to attack the hero
	@Override
	public double getAttack() {
		if (!hasAttacked) {
			this.hasAttacked = true;
			getEngine().getLevel().getHero().setPoisoned(true);
			return super.getAttack();
		}else {
			return 0;
		}
	}
	
	//gets executed every turn (Esta errado)
//	@Override
//	public boolean move(Direction d) {
//		Point2D newPosition = this.getPosition().plus(d.asVector());
//		if (hasAttacked && !(getEngine().getLevel().getEntity(newPosition) instanceof Hero) ) {
//			getEngine().attackEntity(this, getEngine().getLevel().getHero());
//		}
//		return super.move(d);
//	}
	
}

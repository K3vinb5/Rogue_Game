package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hero extends Entity{

	private boolean hasSword;
	private boolean hasArmor;
	private static final double maxHealth = 50;

	public Hero(Point2D position) {
		super("Hero", position, maxHealth, 1);
		hasSword = false;
		hasArmor = false;
	}
	
	public boolean getSword() {
		return hasSword;
	}
	
	public boolean getArmor() {
		return hasArmor;
	}
	
	public void setSword(boolean b) {
		hasSword = b;
	}
	
	public void setArmor(boolean b) {
		hasArmor = b;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void giveHealth(double health) {
		if (health <=maxHealth) {
			super.setHealth(health);
		} else {
			super.setHealth(maxHealth);
		}
	}
	//Because setHealth is only used when the Hero gets attacked by some enemy
	@Override
	public void setHealth(double health) {
		
		if (!hasArmor) {	
			if (health <=maxHealth) {
					super.setHealth(health);
				} else {
					super.setHealth(maxHealth);
				}
		} else if (hasArmor && (int)(Math.random() * 2)  < 1) {
			if (health <=maxHealth) {
				super.setHealth(health);
			} else {
				super.setHealth(maxHealth);
			}			
		}
		
	}
	
	@Override
	public double getAttack() {
		if (this.getSword()) {
			return super.getAttack()*2;
		}else {
			return super.getAttack();
		}
	}
	
	@Override
	public boolean move(Direction d) {
		// TODO Auto-generated method stub
		return super.move(d);
	}
}

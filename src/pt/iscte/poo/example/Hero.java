package pt.iscte.poo.example;

//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hero extends Entity{

	private boolean hasSword;
	private boolean hasArmor;
	private double maxHealth;

	public Hero(Point2D position) {
		super("Hero", position, 20, 1);
		hasSword = false;
		hasArmor = false;
		this.maxHealth = 20;
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
	
	@Override
	public double getAttack() {
		if (this.getSword()) {
			return super.getAttack()*2;
		}else {
			return super.getAttack();
		}
	}
}

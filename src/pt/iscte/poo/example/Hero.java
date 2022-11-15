package pt.iscte.poo.example;

//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hero extends Entity{

	private boolean hasSword;
	private boolean hasArmor;

	public Hero(Point2D position) {
		super("Hero", position, 1000, 1);
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
}

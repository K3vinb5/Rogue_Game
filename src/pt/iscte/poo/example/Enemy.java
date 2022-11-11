package pt.iscte.poo.example;

//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Enemy extends Entity{

	public Enemy(String name, Point2D position, int health, int attack) {
		super(name, position, health, attack);
	}
	
	public static Enemy createEnemyFromEntity(Entity entity) {
		return new Enemy(entity.getName(), entity.getPosition(), entity.getHealth(), entity.getAttack());
	}
}

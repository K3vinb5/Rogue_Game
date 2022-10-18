package pt.iscte.poo.example;

//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Enemy extends Entity{

	public Enemy(String name, Point2D position, int health, int attack) {
		super(name, position, health, attack);
	}
	
	public static boolean isHero(Point2D position) {
		for (Entity  e : Engine.getEntityList())
			if (e.getPosition().equals(position) && e.getName().equals("Hero"))
				return true;
	return false;
	}
	
	public static Enemy createEnemyFromEntity(Entity entity) {
		return new Enemy(entity.getName(), entity.getPosition(), entity.getHealth(), entity.getAttack());
	}
}

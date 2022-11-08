package pt.iscte.poo.example;

//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hero extends Entity{


	public Hero(String name, Point2D position, int health, int attack) {
		super(name, position, health, attack);
	}
	
	public static boolean IsEnemy(Point2D position) {
		for (Entity  e : Engine.getEntityList())
			if (  e.getPosition().equals(position) && !(e.getName().equals("Hero") ) )
				return true;
	return false;
	}
//	private boolean IsEnemy(Point2D position) {
//		for (Entity  e : Engine.getEntityList())
//			if (  e.getPosition().equals(position) && !(e.getName().equals("Hero") ) )
//				return true;
//	return false;
//	}
	
	public static Enemy getEnemy(Point2D position) {
		for (Entity  e : Engine.getEntityList())
			if ( e.getPosition().equals(position) && !(e.getName().equals("Hero")) )
				return Enemy.createEnemyFromEntity(e);
	return null;
	}
	
}

package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends Enemy{

	public Bat(Point2D position) {
		super("Bat", position, 3, 1);
	}

//	public static Bat createBatFromEntity(Entity entity) {
//		return new Bat(entity.getName(), entity.getPosition());
//	}
	
	@Override
	public boolean move(Direction d) {
		if ((int)(Math.random() * 1000) == 1) { // 50/50 chance
			return super.move(d);
		}else {
			Direction randomDirection = Direction.random();
			 return super.move(randomDirection);
			}
		}
	}


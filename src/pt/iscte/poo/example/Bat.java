package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends Enemy{

	public Bat(String name, Point2D position) {
		super(name, position, 3, 1);
	}

	public static Bat createBatFromEntity(Entity entity) {
		return new Bat(entity.getName(), entity.getPosition());
	}
	
	@Override
	public void move(Direction d) {
		if ((int)(Math.random() * 1000) == 1) { // 50/50 chance
			super.move(d);
		}else {
			Direction randomDirection = Direction.random();
			super.move(randomDirection);
			}
		}
	}


package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Skeleton extends Enemy{

	// Skeleton Constructor
	public Skeleton(String name, Point2D position) {
		super(name, position, 5, 1);
	}
	
	public static Skeleton createSkeletonFromEntity(Entity entity) {
		return new Skeleton(entity.getName(), entity.getPosition());
	}

	@Override
	public void move(Direction d) {
		if ( Engine.getTurns() % 2 != 0 ) {
			super.move(d);
		}

	}
	
}

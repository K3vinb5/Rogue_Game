package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Skeleton extends Entity implements Enemy{

	// Skeleton Constructor
	public Skeleton(Point2D position) {
		super("Skeleton", position, 5, 1);
	}
	
//	public static Skeleton createSkeletonFromEntity(Entity entity) {
//		return new Skeleton(entity.getName(), entity.getPosition());
//	}

	@Override
	public boolean move(Direction d) {
		if ( getEngine().getTurns() % 2 != 0 ) {
			 return super.move(d);
			}
		return false;
	}

	}
	


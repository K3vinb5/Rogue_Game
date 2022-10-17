package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Skeleton extends Entity{

	// Skeleton Constructor
	public Skeleton(String name, Point2D position) {
		super(name, position, 5, 1);
	}
	
	public static Skeleton createSkeletonFromEntity(Entity entity) {
		return new Skeleton(entity.getName(), entity.getPosition());
	}


}

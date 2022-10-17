package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Bat extends Entity{

	public Bat(String name, Point2D position) {
		super(name, position, 3, 1);
	}

	public static Bat createbatFromEntity(Entity entity) {
		return new Bat(entity.getName(), entity.getPosition());
	}
}

package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Thug extends Entity{

	public Thug(String name, Point2D position) {
		super(name, position, 10, 3);
	}

	public static Thug createthugFromEntity(Entity entity) {
		return new Thug(entity.getName(), entity.getPosition());
	}
}

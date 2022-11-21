package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Thug extends Entity implements Enemy{

	public Thug( Point2D position) {
		super("Thug", position, 10, 3);
	}

//	public static Thug createthugFromEntity(Entity entity) {
//		return new Thug(entity.getName(), entity.getPosition());
//	}
}

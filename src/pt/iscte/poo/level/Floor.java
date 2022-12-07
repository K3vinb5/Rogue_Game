package pt.iscte.poo.level;

import pt.iscte.poo.main.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Floor extends GameElement {

	public Floor( Point2D position) {
		super("Floor", position, 0);
	}
	
	public Floor( String name, Point2D position ) {
		super(name, position, 0);
	}

	@Override
	public int getLayer() {
		return 0;
	}
}
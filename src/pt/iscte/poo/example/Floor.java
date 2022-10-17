package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Floor extends GameElement {

	public Floor( Point2D position) {
		super("Floor", position, 0);
	}

	@Override
	public int getLayer() {
		return 0;
	}
}
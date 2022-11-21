package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Treasure extends GameElement implements Transposible{

	public Treasure(Point2D position) {
		super("Treasure", position, 1);
	}

	@Override
	public int getLayer() {
		return 1;
	}
}

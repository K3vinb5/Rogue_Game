package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class HealthBar extends GameElement{

	public HealthBar( Point2D position) {
		super("Green", position, 0);
	}
	
	
	@Override
	public int getLayer() {
		return 0;
	}
}

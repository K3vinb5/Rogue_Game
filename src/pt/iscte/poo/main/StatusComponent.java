package pt.iscte.poo.main;

import pt.iscte.poo.utils.Point2D;

public class StatusComponent extends GameElement {

	public StatusComponent(String name, Point2D position) {
		super(name, position, 0);
	}
	
	@Override
	public int getLayer() {
		return 0;
	}

}

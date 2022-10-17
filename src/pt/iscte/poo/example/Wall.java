package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameElement{

	// Wall Constructor
	public Wall(Point2D position) {
		super("Wall", position, 0);
	}

	// Because wall's layer will always be 0: (not sure)
	@Override
	public int getLayer() {
		return 0;
	}
	
	//temp
	@Override
	public String toString() {
		return "X: " + this.getPosition().getX() + " Y: " + this.getPosition().getY();
	}
}

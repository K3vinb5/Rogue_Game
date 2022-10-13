package pt.iscte.poo.example;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Hero implements ImageTile {

	private Point2D position;

	public Hero(Point2D position) {
		this.position = position;
	}

	@Override
	public String getName() {
		return "Hero";
	}

	public void move(Direction d) {
		position = position.plus(d.asVector());
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}
}

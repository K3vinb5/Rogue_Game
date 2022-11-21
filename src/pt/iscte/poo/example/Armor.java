package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Armor extends GameElement implements Transposible, Pickable{

	public Armor(Point2D position) {
		super("Armor", position, 1);
	}

}

package pt.iscte.poo.items;

import pt.iscte.poo.interfaces.Armament;
import pt.iscte.poo.interfaces.Pickable;
import pt.iscte.poo.interfaces.Transposible;
import pt.iscte.poo.main.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Armor extends GameElement implements Transposible, Pickable, Armament{

	public Armor(Point2D position) {
		super("Armor", position, 1);
	}

}

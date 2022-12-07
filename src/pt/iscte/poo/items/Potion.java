package pt.iscte.poo.items;

import pt.iscte.poo.interfaces.Consumable;
import pt.iscte.poo.interfaces.Pickable;
import pt.iscte.poo.interfaces.Transposible;
import pt.iscte.poo.main.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Potion extends GameElement implements Transposible, Pickable, Consumable {

	public Potion(String name, Point2D position, int layer) {
		super(name, position, layer);
	}

	@Override
	public boolean Consume() {
		return false;
	}

}

package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class HealingPotion extends GameElement implements Transposible, Pickable, Consumable{

	public HealingPotion(Point2D position) {
		super("HealingPotion", position, 1);
	}

}

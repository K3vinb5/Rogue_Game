package pt.iscte.poo.items;

import pt.iscte.poo.interfaces.Armament;
import pt.iscte.poo.interfaces.Pickable;
import pt.iscte.poo.interfaces.Transposible;
import pt.iscte.poo.main.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameElement implements Transposible, Pickable, Armament{

	private int damage;
	
	public Sword(Point2D position, int damage) {
		super("Sword", position, 1);
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
}

package pt.iscte.poo.example;

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

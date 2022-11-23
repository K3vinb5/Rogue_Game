package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class HealingPotion extends Potion{

	public HealingPotion(Point2D position) {
		super("HealingPotion", position, 1);
	}

	@Override
	public boolean Consume() {
		Hero hero = getEngine().getLevel().getHero();
		hero.giveHealth(hero.getHealth() + 5);
		hero.setPoisoned(false);
		return true;
	}
	
	
}

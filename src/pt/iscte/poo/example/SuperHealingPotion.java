package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class SuperHealingPotion extends HealingPotion{

	public SuperHealingPotion(Point2D position) {
		super(position);
		this.setName("SuperHealingPotion");
	}
	
	@Override
	public boolean Consume() {
		Hero hero = getEngine().getLevel().getHero();
		hero.giveHealth(hero.getHealth() + getEngine().getLevel().getHero().getMaxHealth());
		hero.setPoisoned(false);
		return true;
	}

}

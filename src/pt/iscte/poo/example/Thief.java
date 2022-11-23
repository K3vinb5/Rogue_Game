package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Thief  extends Entity implements Enemy{

	private boolean dirTowardsHero;
	private Stats stats = new Stats(false);

	public Thief(Point2D position) {
		super("Thief", position, 5, 0);
		this.dirTowardsHero = true;
	}
	
	@Override
	public boolean move(Direction d) {
		if (dirTowardsHero) {
			return super.move(d);
		}else {
			return super.move(d.opposite());
		}
	}
	
	//Only gets executed when the thief tries to attack the hero
	@Override
	public double getAttack() {
		int occupiedSlots = getEngine().getLevel().getHero().getStats().occupiedSlots();
		
		if (occupiedSlots > 0) {
			dirTowardsHero = false;
			int randomIndex = (int)Math.random() * occupiedSlots;
			GameElement itemStealed = getEngine().getLevel().getHero().getStats().getItem(randomIndex);
			if (itemStealed instanceof Armament){
				getEngine().getLevel().getHero().setArmament(itemStealed, false);
			}
			getEngine().getLevel().getHero().getStats().removeItem(randomIndex, itemStealed);
			getEngine().removeSprite(itemStealed);
			stats.getStatsElements().add(itemStealed);
		}
		return 0;
	}
	
	//If dead do stuff
	@Override
	public void setHealth(double health) {
		if (health <= 0) {
			for (GameElement g : stats.getStatsElements()) {
				g.setPosition(this.getPosition());
				getEngine().getLevel().getElementList().add(g);
				getEngine().addSprite(g);
			}
			super.setHealth(health);
		}else {
			// Normal Behavior
			super.setHealth(health);
		}
	}

}

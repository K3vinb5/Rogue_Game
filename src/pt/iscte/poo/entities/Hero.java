package pt.iscte.poo.entities;

import pt.iscte.poo.items.Armor;
import pt.iscte.poo.items.Sword;
import pt.iscte.poo.main.GameElement;
import pt.iscte.poo.main.Stats;
import pt.iscte.poo.utils.Direction;
//import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Hero extends Entity{

	private boolean isPoisoned;
	private Sword sword;
	private Armor armor;
	private Stats stats = new Stats(true); // has to always be true for the hero
	private static final double maxHealth = 50;

	public Hero(Point2D position) {
		super("Hero", position, maxHealth, 1);
		sword = null;
		armor = null;
	}
	
	public boolean getSword() {
		return ( sword != null );
	}
	
	public boolean getArmor() {
		return ( armor !=null );
	}
	
	public Stats getStats() {
		return stats;
	}
	public boolean isPoisoned() {
		return isPoisoned;
	}

	public void setPoisoned(boolean isPoisoned) {
		this.isPoisoned = isPoisoned;
	}
	
	public void setArmament(GameElement g, boolean b) {
		
		if (g instanceof Armor) {
			if (getSword() && b) {
				this.setName("Hero_Armor_Sword");
				this.armor = (Armor)g;
			}else if (b) {
				this.setName("Hero_Armor");
				this.armor = (Armor)g;
			}else if (!b && getSword()) {
				this.setName("Hero_Sword");
				this.armor = null;
			}else if (!b) {
				this.setName("Hero");
				this.armor = null;
			}
		}else if (g instanceof Sword) {
			if (getArmor() && b) {
				this.setName("Hero_Armor_Sword");
				this.sword = (Sword)g;
			}else if (b) {
				this.setName("Hero_Sword");
				this.sword = (Sword)g;
			}else if (!b && getArmor()) {
				this.setName("Hero_Armor");
				this.sword = null;
			}else if (!b) {
				this.setName("Hero");
				this.sword = null;
			}
		}
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void giveHealth(double health) {
		if (health <=maxHealth) {
			super.setHealth(health);
		} else {
			super.setHealth(maxHealth);
		}
	}
	//Because setHealth is only used when the Hero gets attacked by some enemy
	@Override
	public void setHealth(double health) {
		
		if (!this.getArmor()) {	
			if (health <=maxHealth) {
					super.setHealth(health);
				} else {
					super.setHealth(maxHealth);
				}
		} else if (this.getArmor() && (int)(Math.random() * 2)  < 1) {
			if (health <=maxHealth) {
				super.setHealth(health);
			} else {
				super.setHealth(maxHealth);
			}			
		}
		
	}
	
	@Override
	public double getAttack() {
		if (this.getSword()) {
			return super.getAttack()*sword.getDamage();
		}else {
			return super.getAttack();
		}
	}
	
	@Override
	public boolean move(Direction d) {
		// TODO Auto-generated method stub
		if (isPoisoned) {
			this.setHealth(this.getHealth() - 1);
			getEngine().redrawHealthBarIf(true);			
			if (this.getHealth() <= 0) {
				getEngine().disposeGui();
			}
		}
		return super.move(d);
	}
}

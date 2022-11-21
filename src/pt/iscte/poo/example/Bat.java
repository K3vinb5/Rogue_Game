package pt.iscte.poo.example;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends Entity implements Enemy{

	public Bat(Point2D position) {
		super("Bat", position, 3, 1);
	}

//	public static Bat createBatFromEntity(Entity entity) {
//		return new Bat(entity.getName(), entity.getPosition());
//	}
	
	@Override
	public void setHealth(double health) {
		if (health <= 3) {
			super.setHealth(health);
		}else {
			super.setHealth(3);
		}
		
	}
	// Because this function only gets called when a bat attacks someone successfully we'll add 1 "healthPoint" to the bat here
	@Override
	public double getAttack() {
		this.setHealth(this.getHealth() + 1);
		return super.getAttack();
	}
	
	@Override
	public boolean move(Direction d) {
		if ((int)(Math.random() * 2) < 1) { // 50/50 chance
			return super.move(d);
		}else {
			Direction randomDirection = Direction.random();
			 return super.move(randomDirection);
			}
		}
	}


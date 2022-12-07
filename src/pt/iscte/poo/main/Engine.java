package pt.iscte.poo.main;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.level.DoorComponent;
import pt.iscte.level.GoldenKey;
import pt.iscte.level.Level;
import pt.iscte.level.RedDoor;
import pt.iscte.poo.entities.Entity;
import pt.iscte.poo.entities.Hero;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.interfaces.Armament;
import pt.iscte.poo.interfaces.Consumable;
import pt.iscte.poo.interfaces.Enemy;
import pt.iscte.poo.interfaces.Pickable;
import pt.iscte.poo.items.KeyComponent;
import pt.iscte.poo.items.Treasure;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import java.awt.event.KeyEvent;

public class Engine implements Observer {

	// Window Attributes and others...
	private static Engine INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static final int GRID_HEIGHT_STATS = 1;

	// Other Attributes
	private String userName; //Still not used, it will be later used to display the score, highScore etc.
	private Hero hero;
	private List<Level> levelList = new ArrayList<>();
	private int currentFloor;
	private int turns;

	// - - Methods --

	public static Engine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Engine();
		return INSTANCE;
	}

	private Engine() {
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT + GRID_HEIGHT_STATS);
		gui.go();
	}

	public void start() {
		userName = gui.askUser("Please insert your name: ");
		//gui.setMessage("Test");
		currentFloor = 0;
		for (int i = 0; i < 7; i++)
			levelList.add(new Level("rooms//room" + (currentFloor + i) + ".txt"));
		
//		currentFloor = 0;
//		for (int i = 0; i < 6; i++) {
//			int populatedFloors = 0;
//			while (populatedFloors<3) {				
//				GameElement g = getLevel().getElement(new Point2D((int)Math.random()*GRID_WIDTH, (int)Math.random()*GRID_HEIGHT));
//				if (g == null) {
//					g.setName("Grass");
//					populatedFloors++;
//				}
//			}
//		}
		
		addObjects(new Hero(new Point2D(4, 4)));
		addStatus();
		gui.setStatusMessage("Turns:" + turns);
		gui.update();
	}



	private void addStatus() {
		for (GameElement g : hero.getStats().getStatsElements()) {
			gui.addImage(g);
		}
	}

	// Hero's position missing
	private void addObjects(Hero hero) {
		getLevel().setHero(hero);
		this.hero = getLevel().getHero();
		for (GameElement g : getLevel().getElementList()) {
			if (g instanceof Entity) {
				Entity e = (Entity) g;
				if (e.getHealth() > 0) {
					gui.addImage(g);
				}
			} else {
				gui.addImage(g);
			}
		}
		for (GameElement g : getLevel().getFloorList()) {
			gui.addImage(g);
		}
	}

	public Level getLevel() {
		return levelList.get(currentFloor);
	}

	public int getTurns() {
		return turns;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}
	

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	

	public void removeSprites() {
		// Level Elements
		for (GameElement g : getLevel().getElementList())
			gui.removeImage(g);

		// Status Elements
		for (GameElement g : hero.getStats().getStatsElements()) {
			gui.removeImage(g);
		}
		
		for (GameElement g : getLevel().getFloorList()) {
			gui.removeImage(g);
		}
	}
	
	public void removeSprite(GameElement g) {
		gui.removeImage(g);
	}
	
	public void addSprite(GameElement g) {
		gui.addImage(g);
	}

	public void updateGui() {
		gui.update();
	}

	public void disposeGui() {
		gui.dispose();
	}
	
	@Override
	public void update(Observed source) {
		if (hero.getHealth() <= 0) {
			return;
		}
		// Hero Movement
		int keyPressed = ((ImageMatrixGUI) source).keyPressed();
		moveHero(keyPressed);
		updateInventoryStats(keyPressed);
		test(keyPressed);
		// Entity Movements
		for (GameElement e : getLevel().getElementList()) {
			if (e instanceof Enemy) {
				moveEnemy((Entity) e);
			}
		}

		// Updates Status Message
		gui.setStatusMessage("Turns" + turns);

		// Updates Graphical User Interface
		gui.update();
	}

	private void moveEnemy(Entity e) {
		Direction newDirection;
		Point2D newPosition;
		if (!e.getPosition().equals(hero.getPosition())) {
			newDirection = Direction.forVector(Vector2D.movementVector(e.getPosition(), hero.getPosition()));
			newPosition = e.getPosition().plus(newDirection.asVector());
			if (e.move(newDirection))
				e.attackEntity((Entity)getLevel().getEntity(newPosition));
		}
	}

	private void moveHero(int keyPressed) {

		Direction newDirection;
		Point2D newPosition;
		switch (keyPressed) {
		case KeyEvent.VK_UP:
			newDirection = Direction.UP;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection)) {
				if ( getLevel().getEntity(newPosition) instanceof Entity)
					hero.attackEntity((Entity)getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		case KeyEvent.VK_LEFT:
			newDirection = Direction.LEFT;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection)) {
				if ( getLevel().getEntity(newPosition) instanceof Entity)
					hero.attackEntity((Entity)getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		case KeyEvent.VK_RIGHT:
			newDirection = Direction.RIGHT;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection)) {
				if ( getLevel().getEntity(newPosition) instanceof Entity)
					hero.attackEntity((Entity)getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		case KeyEvent.VK_DOWN:
			newDirection = Direction.DOWN;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection)) {
				if ( getLevel().getEntity(newPosition) instanceof Entity)
					hero.attackEntity((Entity)getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		default:
			break;
		}
	}
	
	public void redrawHealthBarIf(boolean b) {
		if (b) {
			hero.getStats().redrawHealthBar(Stats.getHealthBarMapping(hero.getHealth(), hero.getMaxHealth()));
			gui.update();
		}
	}
	
	private void updateInventoryStats(int keyPressed) {
		switch (keyPressed) {
		case KeyEvent.VK_1:
			dropItem(0);
		break;
		case KeyEvent.VK_2:
			dropItem(1);
		break;
		case KeyEvent.VK_3:
			dropItem(2);
		break;
		case KeyEvent.VK_Q:
			consumeItem(0);
		break;
		case KeyEvent.VK_W:
			consumeItem(1);
		break;
		case KeyEvent.VK_E:
			consumeItem(2);
		break;
		default:
			break;
		}
	}

	private void interact() {
		GameElement item = getLevel().getItem(hero.getPosition());
		if (item != null) {

			if (item instanceof Pickable) {
				pickupItem(item);
			} else if (item instanceof DoorComponent) {
				DoorComponent d = (DoorComponent)item;
				if (d.isLocked()) {
					DoorComponent doorClosed = (DoorComponent) item;
					tryUnlocking(doorClosed);
					if (!doorClosed.isLocked()) { 
						getLevel().loadLevel(doorClosed.getRoom(), doorClosed.getNewPostion());
						DoorComponent nextDoor =(DoorComponent) getLevel().getItem(doorClosed.getNewPostion());
						nextDoor.setLocked(false);
					}
				} else if (!d.isLocked()) {
					DoorComponent doorOpen = (DoorComponent) item;
					getLevel().loadLevel(doorOpen.getRoom(), doorOpen.getNewPostion());
				}
			}else if (item instanceof Treasure && !((Treasure) item).isOpened()){
				extraContent(item);
			}
		}
	}

	private void extraContent(GameElement item) {
		GameElement toRemove = getLevel().getElement(new Point2D(9, 8));
		gui.removeImage(toRemove);
		getLevel().removeFromList(toRemove);
		
		GameElement doorToAdd = new RedDoor(new Point2D(9, 8), "room4", new Point2D(0,1), "KEY10");
		GameElement keyToAdd = new GoldenKey(new Point2D(4,8), "KEY10");
		
		getLevel().insertIntoList(doorToAdd);
		gui.addImage(doorToAdd);
		getLevel().insertIntoList(keyToAdd);
		gui.addImage(keyToAdd);
		((Treasure)item).setOpened(true);
	}
	
	private void pickupItem(GameElement item) {
		if (!hero.getStats().isFull()) {
			// Remove Item from Level ElementList
			getLevel().getElementList().remove(item);
			gui.removeImage(item);
			// Add Item from Status ElementList
			gui.addImage(hero.getStats().getItem(hero.getStats().setItem(item)));
			gui.update();
			if (item instanceof Armament) {
				hero.setArmament(item, true);
			}
		}
	}
		
	
	public void dropItem(int slotNumber) {
		if (hero.getStats().isOccupied(slotNumber)) {
			GameElement item = hero.getStats().getItem(slotNumber);
			gui.removeImage(item);
			hero.getStats().removeItem(slotNumber, item);
			getLevel().getElementList().add(item);
			item.setPosition(getLevel().getValidNeighboringPosition(hero.getPosition()));
			gui.addImage(item);
			gui.update();
			if (item instanceof Armament) {					
				hero.setArmament(item, false);
			}
		}
	}
	
	public void consumeItem(int slotNumber) {
		if (hero.getStats().isOccupied(slotNumber)) {
			GameElement item = hero.getStats().getItem(slotNumber);
			if ( item instanceof Consumable) {
				gui.removeImage(item);
				hero.getStats().removeItem(slotNumber, item);
				redrawHealthBarIf(((Consumable)item).Consume());
			}
		}
	}
	
	private void tryUnlocking(DoorComponent d) {
		for (KeyComponent k : hero.getStats().getKeys()) {
			if (d.getKeyID().equals(k.getKeyID())) {
				System.out.println("Key Index is: " + hero.getStats().getIndex(k));
				hero.getStats().removeItem(hero.getStats().getIndex(k) - hero.getStats().INVENTORY_STARTING_INDEX, k);
				gui.removeImage(k);
				d.setLocked(false);
				break;
			}
		}
	}


	// Temporary
	private void test(int keyPressed) {
		switch (keyPressed) {
		case KeyEvent.VK_NUMPAD7:
			getLevel().loadLevel(0, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD8:
			getLevel().loadLevel(1, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD9:
			getLevel().loadLevel(2, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD4:
			getLevel().loadLevel(3, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD5:
			getLevel().loadLevel(4, hero.getPosition());
			break;
		default:
			break;
		}
	}

}

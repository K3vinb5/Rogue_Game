package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.List;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
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
	private Stats status = new Stats();
	private static List<Level> levelList = new ArrayList<>();
	private static int currentFloor;
	private static int turns;

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
		// temporary
		currentFloor = 0;
		for (int i = 0; i < 4; i++)
			levelList.add(new Level("rooms//room" + (currentFloor + i) + ".txt"));
		addFloor();
		addObjects(new Hero(new Point2D(4, 4)));
		addStatus();
		gui.setStatusMessage("Turns:" + turns);
		gui.update();
	}

	private void addFloor() {
		List<ImageTile> tileList = new ArrayList<>();
		for (int x = 0; x != GRID_WIDTH; x++)
			for (int y = 0; y != GRID_HEIGHT; y++)
				tileList.add(new Floor(new Point2D(x, y)));
		gui.addImages(tileList);
	}

	private void addStatus() {
		for (GameElement g : status.getStatsElements()) {
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
				attackEntity(e, getLevel().getEntity(newPosition));
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
				attackEntity(hero, getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		case KeyEvent.VK_LEFT:
			newDirection = Direction.LEFT;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection)) {
				attackEntity(hero, getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		case KeyEvent.VK_RIGHT:
			newDirection = Direction.RIGHT;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection)) {
				attackEntity(hero, getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		case KeyEvent.VK_DOWN:
			newDirection = Direction.DOWN;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection)) {
				attackEntity(hero, getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		default:
			break;
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
		default:
			break;
		}
	}

	public static Level getLevel() {
		return levelList.get(currentFloor);
	}

	public static int getTurns() {
		return turns;
	}

	public static int getCurrentFloor() {
		return currentFloor;
	}

	private void attackEntity(Entity attacker, Entity attacked) {
		Entity e = null;
		boolean someoneDied = false;
		if (attacked != null && !attacked.equals(attacker) && !(attacker instanceof Enemy && attacked instanceof Enemy)) {
			attacked.setHealth(attacked.getHealth() - attacker.getAttack());
			if (attacked.getHealth() <= 0) {
				gui.removeImage(attacked);
				redrawHealthBarIf(attacked.equals(hero));
				e = attacked;
				someoneDied = true;
			}
			redrawHealthBarIf(attacked.equals(hero));
		}
		if (someoneDied && !(e instanceof Hero)) {
			getLevel().getElementList().remove(e);
		}	else if (someoneDied && e instanceof Hero) {
			gui.dispose();
		}
	}
	private void redrawHealthBarIf(boolean b) {
		if (b) {
			status.redrawHealthBar(Stats.getHealthBarMapping(hero.getHealth(), hero.getMaxHealth()));
			gui.update();
		}
	}

	private void interact() {
		GameElement item = getLevel().getItem(hero.getPosition());
		if (item != null) {

			if (item instanceof Pickable) {
				pickupItem(item);
			} else if (item instanceof Door) {
				if (item.getName() == "DoorClosed") {
					Door doorClosed = (Door) item;
					tryUnlocking(doorClosed);
					if (!doorClosed.isLocked) { 
						loadLevel(doorClosed.getRoom(), doorClosed.getNewPostion());
						Door nextDoor =(Door) getLevel().getItem(doorClosed.getNewPostion());
						nextDoor.setLocked(false);
					}
				} else if (item.getName() == "DoorOpen") {
					Door doorOpen = (Door) item;
					loadLevel(doorOpen.getRoom(), doorOpen.getNewPostion());
				}
			}else if (item.getName() == "Treasure"){
				//Ends game you touch the Treasure
				gui.dispose();
			}
		}
	}

	private void pickupItem(GameElement item) {
		if (!status.isFull()) {
			// Remove Item from Level ElementList
			getLevel().getElementList().remove(item);
			gui.removeImage(item);
			// Add Item from Status ElementList
			gui.addImage(status.getItem(status.setItem(item)));
			gui.update();
			switch (item.getName()) {
			case "Sword":
				hero.setSword(true);
				break;
			case "Armor":
				hero.setArmor(true);
				break;
			default:
				break;
			}
		}
	}

	private void dropItem(int slotNumber) {
		if (status.isOccupied(slotNumber)) {
			GameElement item = status.getItem(slotNumber);
			gui.removeImage(item);
			status.removeItem(slotNumber, item);
			if (item instanceof Consumable) {
				if (item.getName() == "HealingPotion") {
					hero.giveHealth(hero.getHealth() + 5);
					redrawHealthBarIf(true);
				}
			} else {
				getLevel().getElementList().add(item);
				item.setPosition(getLevel().getValidNeighboringPosition(hero.getPosition()));
				gui.addImage(item);
				gui.update();

				switch (item.getName()) {
				case "Sword":
					hero.setSword(false);
					break;
				case "Armor":
					hero.setArmor(false);
					break;
				default:
					break;
				}
			}
		}
	}
	
	private void tryUnlocking(Door d) {
		for (Key k : status.getKeys()) {
			if (d.getKeyID().equals(k.getKeyID())) {
				System.out.println("Key Index is: " + status.getIndex(k));
				status.removeItem(status.getIndex(k) - status.INVENTORY_STARTING_INDEX, k);
				gui.removeImage(k);
				d.setLocked(false);
				break;
			}
		}
	}

	private void removeSprites() {
		// Level Elements
		for (GameElement g : getLevel().getElementList())
			gui.removeImage(g);

		// Status Elements
		for (GameElement g : status.getStatsElements()) {
			gui.removeImage(g);
		}
	}

	private void loadLevel(int newLevel, Point2D newPosition) {
		// Removes all Images (Except floors)
		removeSprites();
		currentFloor = newLevel;
		getLevel().setHero(this.hero);

		// Level Elements
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

		// Status Elements
		for (GameElement g : status.getStatsElements()) {
			gui.addImage(g);
		}

		hero.setPosition(newPosition);
		gui.update();
	}

	// Temporary
	private void test(int keyPressed) {
		switch (keyPressed) {
		case KeyEvent.VK_NUMPAD7:
			loadLevel(0, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD8:
			loadLevel(1, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD9:
			loadLevel(2, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD4:
			loadLevel(3, hero.getPosition());
			break;
		case KeyEvent.VK_NUMPAD5:
			removeSprites();
			break;
		default:
			break;
		}
	}

}

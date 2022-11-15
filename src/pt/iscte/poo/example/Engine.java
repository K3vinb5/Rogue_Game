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
	public static final int GRID_HEALTH = 1;
	
	// Other Attributes
	private Hero hero;
	private Status status = new Status();
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
		gui.setSize(GRID_WIDTH, GRID_HEIGHT + GRID_HEALTH);
		gui.go();
	}

	public void start() {
		// temporary
		currentFloor = 0;
		for (int i = 0; i < 4; i++)
			levelList.add(new Level("rooms//room" + (currentFloor + i) + ".txt"));
		addFloor();
		addObjects(new Hero(new Point2D(4,4)));
		addStatus();
		gui.setStatusMessage("Turns:" + turns);
		gui.update();
	}
	
	private void addFloor() {
		List<ImageTile> tileList = new ArrayList<>();
		for (int x=0; x!=GRID_WIDTH; x++)
			for (int y=0; y!=GRID_HEIGHT; y++)
				tileList.add(new Floor(new Point2D(x,y)));
		gui.addImages(tileList);
	}
	
	private void addStatus() {
		for (GameElement g : status.getStatusElements()) {
			gui.addImage(g);
		}
	}
	
	//Hero's position missing
	private void addObjects(Hero hero) {
		getLevel().setHero(hero);
		this.hero = getLevel().getHero();
		for (GameElement g : getLevel().getElementList()) {
			if ( g instanceof Entity) {
				Entity e = (Entity)g;
				if (e.getHealth() > 0) {
					gui.addImage(g);
				}
			}else {
				gui.addImage(g);
			}
		}
	}
	
	@Override
	public void update(Observed source) {
		
		// Hero Movement
		int keyPressed = ((ImageMatrixGUI) source).keyPressed();
		moveHero(keyPressed);
		updateStatus(keyPressed);
		test(keyPressed);
		Direction newDirection;
		Point2D newPosition;
		
		//Entity Movements
		for(GameElement e : getLevel().getElementList() ){
			switch (e.getName()) {
			case "Skeleton":
					Skeleton skeleton = (Skeleton)e;
					moveEnemy(skeleton);
				break;
			case "Bat":
					Bat bat = (Bat)e;
					moveEnemy(bat);
				break;
			case "Thug":
				Thug thug = (Thug)e;
				moveEnemy(thug);
				break;
			default:
				break;
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
			if ( e.move(newDirection))
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
			if ( hero.move(newDirection) ) {
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
			if ( hero.move(newDirection) ) {
				attackEntity(hero, getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		case KeyEvent.VK_DOWN:
			newDirection = Direction.DOWN;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if ( hero.move(newDirection)) {
				attackEntity(hero, getLevel().getEntity(newPosition));
				interact();
			}
			turns++;
			break;
		default:
			break;
		}
	}
	
	private void updateStatus(int keyPressed) {
		switch (keyPressed) {
		case KeyEvent.VK_NUMPAD1:
				dropItem(0);
			break;
		case KeyEvent.VK_NUMPAD2:
				dropItem(1);
			break;
		case KeyEvent.VK_NUMPAD3:
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
		if (attacked != null && !attacked.equals(attacker) && !(attacker instanceof Enemy && attacked instanceof Enemy) && attacker.getHealth() > 0) {
			//System.out.println(attacked.getName() + " was attacked by " + attacker.getName());
			attacked.setHealth(attacked.getHealth() - attacker.getAttack());
			if ( attacked.getHealth() <= 0 ) {
				gui.removeImage(attacked);
				System.out.println(attacked.getName() + " will be removed");
				gui.update();
			}
		}
	}
	
	private void interact() {
		GameElement item = getLevel().getItem(hero.getPosition());
		if (item  != null) {
			switch (item.getName()) {
			case "Sword":
				Sword sword = (Sword)item;
				pickupItem(sword);
				break;
			case "Armor":
				Armor armor = (Armor)item;
				pickupItem(armor);
				break;
			case "Key":
				Key key = (Key)item;
				pickupItem(key);
				break;			
			case "DoorClosed":
				//Need to change so it doesn't behave like an open door
				Door doorClosed = (Door)item;
				loadLevel(doorClosed.getRoom(), doorClosed.getNewPostion());
				break;
			case "DoorOpen":
				Door doorOpen = (Door)item;
				loadLevel(doorOpen.getRoom(), doorOpen.getNewPostion());
				break;	
			case "HealingPotion":
				HealingPotion healingPotion = (HealingPotion)item;
				pickupItem(healingPotion);
				break;				
			default:
				break;
			}
		}
	}
	
	private void pickupItem(GameElement item) {
		if (!status.isFull()) {
			//Remove Item from Level ElementList
			getLevel().getElementList().remove(item);
			gui.removeImage(item);
			//Add Item from Status ElementList
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
			getLevel().getElementList().add(item);
			status.removeItem(slotNumber, item);
			gui.removeImage(item);
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
	
	private void removeSprites() {
		//Level Elements
		for (GameElement g : getLevel().getElementList())
			gui.removeImage(g);
		
		//Status Elements
		for (GameElement g : status.getStatusElements()){
			gui.removeImage(g);
		}
	}
	
	private void loadLevel(int newLevel, Point2D newPosition) {
		//Removes all Images (Except floors)
		removeSprites();
		currentFloor = newLevel;
		getLevel().setHero(this.hero);
		
		//Level Elements
		for (GameElement g : getLevel().getElementList()) {
			if ( g instanceof Entity) {
				Entity e = (Entity)g;
				if (e.getHealth() > 0) {
					gui.addImage(g);
				}
			}else {
				gui.addImage(g);
			}
		}
		
		//Status Elements
		for (GameElement g : status.getStatusElements()) {
			gui.addImage(g);
		}
		
		hero.setPosition(newPosition);
		gui.update();
	}
	
	//Temporary
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

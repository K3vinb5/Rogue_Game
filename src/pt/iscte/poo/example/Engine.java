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
			levelList.add(new Level("rooms//room" + currentFloor + ".txt"));
				addFloor();
		addObjects(new Hero(new Point2D(4,4)));
		addHealthBar();
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
	
	private void addHealthBar() {
		List<ImageTile>  tileList = new ArrayList<>();
		for (int x = 0; x!= GRID_WIDTH; x++)
			for (int y = GRID_HEIGHT; y != GRID_HEIGHT + GRID_HEALTH; y++)
				if (x< GRID_WIDTH-4)
						tileList.add( new HealthBar(new Point2D(x, y)) );
		gui.addImages(tileList);
	}
	
	//Hero's position missing
	private void addObjects(Hero hero) {
		getLevel().setHero(hero);
		this.hero = getLevel().getHero();
		// Other Objects
		for (GameElement g : getLevel().getElementList()) 
			gui.addImage(g);
	}
	
	@Override
	public void update(Observed source) {
		
		// Hero Movement
		int keyPressed = ((ImageMatrixGUI) source).keyPressed();
		moveHero(keyPressed);
		Direction newDirection;
		Point2D newPosition;
		
		//Entity Movements
		for(GameElement e : getLevel().getElementList() ){
			switch (e.getName()) {
			case "Skeleton":
					Skeleton skeleton = (Skeleton)e;
					if (!skeleton.getPosition().equals(hero.getPosition())) {
						newDirection = Direction.forVector(Vector2D.movementVector(skeleton.getPosition(), hero.getPosition()));
						newPosition = skeleton.getPosition().plus(newDirection.asVector());
						if ( skeleton.move(newDirection))
							attackEntity(skeleton, getLevel().getEntity(newPosition));
					}
				break;
			case "Bat":
					Bat bat = (Bat)e;
					if(!bat.getPosition().equals(hero.getPosition())) {
						newDirection = Direction.forVector(Vector2D.movementVector(bat.getPosition(), hero.getPosition()));
						newPosition = bat.getPosition().plus(newDirection.asVector());
						if ( bat.move(newDirection))
							attackEntity(bat, getLevel().getEntity(newPosition));
					}
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
	
	private void moveHero(int keyPressed) {
		Direction newDirection;
		Point2D newPosition;
		switch (keyPressed) {
		case KeyEvent.VK_UP:
			newDirection = Direction.UP;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if ( hero.move(newDirection) )
				attackEntity(hero, getLevel().getEntity(newPosition));
			turns++;
			break;
		case KeyEvent.VK_LEFT:
			newDirection = Direction.LEFT;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if (hero.move(newDirection))
				attackEntity(hero, getLevel().getEntity(newPosition));
			turns++;
			break;
		case KeyEvent.VK_RIGHT:
			newDirection = Direction.RIGHT;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if ( hero.move(newDirection) )
				attackEntity(hero, getLevel().getEntity(newPosition));
			turns++;
			break;
		case KeyEvent.VK_DOWN:
			newDirection = Direction.DOWN;
			newPosition = hero.getPosition().plus(newDirection.asVector());
			if ( hero.move(newDirection))
				attackEntity(hero, getLevel().getEntity(newPosition));
			turns++;
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
			System.out.println(attacked.getName() + " was attacked by " + attacker.getName());
			attacked.setHealth(attacked.getHealth() - attacker.getAttack());
			if ( attacked.getHealth() <= 0 ) {
				gui.removeImage(attacked);
				System.out.println(attacked.getName() + " will be removed");
				gui.update();
			}
		}
	}
	
	}

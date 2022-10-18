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
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	public static final int GRID_HEALTH = 1;
	private static Engine INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	// Hero related attributes
	private Entity hero;
	private final int heroHEALTH = 10;
	private final int heroATTACK = 1;
	// Skeleton related attributes
	private List<Skeleton> skeletonList;
	//Bat related attributes
	private List<Bat> batList;
	// Thug related attributes
	private List<Thug> thugList;
	//Other attributes (Used for game logic)
	private static int turns;
	private static List<GameElement> elementList = new ArrayList<>();
	private static List<Entity> entityList = new ArrayList<>();
	
	//Still needs Layer checking and "items stuff"
	public static boolean isValid(Point2D position) {
		
		for (GameElement g : elementList) {
			if ( position.equals(g.getPosition()) ) 
				return false;
		}
		return true;
	}
	
	public static List<GameElement> getElementList() {
		return elementList;
	}
	
	public static List<Entity> getEntityList() {
		return entityList;
	}
	
	public static int getTurns() {
		return turns;
	}
	
	private void setHeroHealth(int newHealth) {
		System.out.println("Hero's new health is " + newHealth);
		if ( newHealth <= 0) {
			System.exit(0);
		}else {
			hero.setHealth(newHealth);
		}
	}
	
	private void setEnemyHealth (int newHealth) {
		System.out.println("Enemy's new health is: " + newHealth);
		if ( newHealth <= 0 ) {
			System.exit(0);
		}else {
			hero.setHealth(newHealth);
		}
	}
	
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
		int startingFloor = 0;
		addFloor();
		addObjects("rooms//room" + startingFloor + ".txt");
		addWall("rooms//room" + startingFloor + ".txt");
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
	
	private void addWall(String level) {
		
		ArrayList<Wall> wallList = levelReader.readWalls(level);
		List<ImageTile> tileList = new ArrayList<>();
	
		for (Wall w : wallList) {
			tileList.add(w);
			elementList.add(w);
		}

		gui.addImages(tileList);
	}
	
	private void addHealthBar() {
		List<ImageTile>  tileList = new ArrayList<>();
		for (int x = 0; x!= GRID_WIDTH; x++)
			for (int y = GRID_HEIGHT; y != GRID_HEIGHT + GRID_HEALTH; y++)
				tileList.add( new HealthBar(new Point2D(x, y)) );
		gui.addImages(tileList);
	}
	
	private void addObjects(String floor) {
		// Auxiliary variable
		List<Entity> levelEntityList;
		
		// Adding Hero
		hero = new Hero("Hero", new Point2D(4,4), heroHEALTH, heroATTACK);
		elementList.add(hero);
		entityList.add(hero);
		gui.addImage(hero);
		
		// Adding Level Skeletons
		skeletonList = new ArrayList<>();
		levelEntityList = levelReader.readEntity(floor, "Skeleton");
		for(Entity e : levelEntityList) {
			Skeleton skeleton = Skeleton.createSkeletonFromEntity(e);
			skeletonList.add(skeleton);
			entityList.add(skeleton);
			elementList.add(skeleton);
			gui.addImage(skeleton);
		}
		
		// Adding Level Bats
		batList = new ArrayList<>();
		levelEntityList = levelReader.readEntity(floor, "Bat");
		for(Entity e : levelEntityList) {
			Bat bat = Bat.createBatFromEntity(e);
			batList.add(bat);
			entityList.add(bat);
			elementList.add(bat);
			gui.addImage(bat);
		}
		
		// Adding Level Thugs
		thugList = new ArrayList<>();
		levelEntityList = levelReader.readEntity(floor, "Thug");
		for(Entity e : levelEntityList) {
			Thug thug = Thug.createthugFromEntity(e);
			thugList.add(thug);
			entityList.add(thug);
			elementList.add(thug);
			gui.addImage(thug);
		}
		
	}
	
	@Override
	public void update(Observed source) {
		
		// Hero Movement
		int keyPressed = ((ImageMatrixGUI) source).keyPressed();
		Direction newDirection;
		Point2D  position;
		switch (keyPressed) {
		case KeyEvent.VK_UP:
			newDirection =
			hero.move(Direction.UP);
			turns++;
			break;
		case KeyEvent.VK_LEFT:
			hero.move(Direction.LEFT);
			turns++;
			break;
		case KeyEvent.VK_RIGHT:
			hero.move(Direction.RIGHT);
			turns++;
			break;
		case KeyEvent.VK_DOWN:
			hero.move(Direction.DOWN);
			turns++;
			break;
		default:
			break;
		}
				
		// Skeletons Movement
		for(Skeleton skeleton : skeletonList) {
			Direction newDirection = Direction.forVector(Vector2D.movementVector(skeleton.getPosition(), hero.getPosition()));
			Point2D newPosition = skeleton.getPosition().plus(newDirection.asVector());
			skeleton.move(newDirection);
			if (Enemy.isHero(newPosition))
				setHeroHealth(hero.getHealth() - skeleton.getAttack());
		}
		
		// Bats Movement (still doesn't check for walls)
		for(Bat bat : batList) {
			Direction newDirection = Direction.forVector(Vector2D.movementVector(bat.getPosition(), hero.getPosition()));
			Point2D newPosition = bat.getPosition().plus(newDirection.asVector());
			bat.move(newDirection);
			if (Enemy.isHero(newPosition))
				setHeroHealth(hero.getHealth() - bat.getAttack());
		}
		
		
//		Thug movement still needs implementing
//		for (Thug t : thugList) {
//			
//		}
		
		
		// Updates Status Message
		gui.setStatusMessage("Turns" + turns);
		
		// Updates Graphical User Interface
		gui.update();
	}

	
}

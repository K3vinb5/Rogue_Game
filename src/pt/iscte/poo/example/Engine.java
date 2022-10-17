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
	private int turns;
	private static List<GameElement> elementList = new ArrayList<>();
	
	
//	private static boolean[][] mapOccupied = new boolean[GRID_WIDTH][GRID_HEIGHT];
//	
//
//	public static boolean getMapOccupied(int x, int y) {
//		return mapOccupied[x][y];
//	}
//
//	public static void setMapOccupied(boolean value, int x, int y) {
//		mapOccupied[x][y] = value;
//	}
//	
//	// Updates Position
//	public static void updateMapOccupied(Point2D oldPosition, Point2D newPosition) {
//		setMapOccupied(false,oldPosition.getX() ,oldPosition.getY() );
//		setMapOccupied(true, newPosition.getX(), newPosition.getY());
//	}
	
	//Still needs Layer checking and "items stuff"
	public static boolean isValid(Point2D position) {
		
		for (GameElement g : elementList) {
			if ( position.equals(g.getPosition()) ) 
				return false;
		}
		return true;
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
		//elementList = new ArrayList<>();
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
		List<Entity> entityList;
		
		// Adding Hero
		hero = new Hero("Hero", new Point2D(4,4), heroHEALTH, heroATTACK);
		elementList.add(hero);
		gui.addImage(hero);
		
		// Adding Skeletons
		skeletonList = new ArrayList<>();
		entityList = levelReader.readEntity(floor, "Skeleton");
		for(Entity e : entityList) {
			Skeleton skeleton = Skeleton.createSkeletonFromEntity(e);
			skeletonList.add(skeleton);
			elementList.add(skeleton);
			gui.addImage(skeleton);
		}
		
		// Adding Bats
		batList = new ArrayList<>();
		entityList = levelReader.readEntity(floor, "Bat");
		for(Entity e : entityList) {
			Bat bat = Bat.createbatFromEntity(e);
			batList.add(bat);
			elementList.add(bat);
			gui.addImage(bat);
		}
		
		// Adding Thugs
		thugList = new ArrayList<>();
		entityList = levelReader.readEntity(floor, "Thug");
		for(Entity e : entityList) {
			Thug thug = Thug.createthugFromEntity(e);
			thugList.add(thug);
			elementList.add(thug);
			gui.addImage(thug);
		}
		
	}
	
	@Override
	public void update(Observed source) {
		
		// Hero Movement
		int keyPressed = ((ImageMatrixGUI) source).keyPressed();
				
		switch (keyPressed) {
		case KeyEvent.VK_UP:
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
			Direction skeletonDirection = Direction.forVector(Vector2D.movementVector(skeleton.getPosition(), hero.getPosition()));
			if ( turns % 2 != 0 ) {
				skeleton.move(skeletonDirection);
			}
		}
		
		// Bats Movement (still doesn't check for walls)
		for(Bat bat : batList) {
			if ((int)(Math.random() * 2) == 1) { // 50/50 chance
				Direction batDirection = Direction.forVector(Vector2D.movementVector(bat.getPosition(), hero.getPosition()));
				bat.move(batDirection);
			}else {
				Direction randomDirection = Direction.random();
				bat.move(randomDirection);
			}
		}
		
		
		//Thug movement still needs implementing
		
		
		
		// Updates Status Message
		gui.setStatusMessage("Turns" + turns);
		
		// Updates Graphical User Interface
		gui.update();
	}

	
}

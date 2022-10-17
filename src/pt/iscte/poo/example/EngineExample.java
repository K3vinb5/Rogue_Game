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


public class EngineExample implements Observer {

	// Window Attributes and others...
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	private static EngineExample INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	// Hero related attributes
	private Entity hero;
	private final int heroHEALTH = 10;
	private final int heroATTACK = 1;
	// Skeleton related attributes
	private List<Entity> skeletonList;
	private final int skeletonHEALTH = 5;
	private final int skeletonATTACK = 1;
	//Bat related attributes
	private List<Entity> batList;
	private final int batHEALTH = 3;
	private final int batATTACK = 1;
	// Thug related attributes
	private List<Entity> thugList;
	private final int thugHEALTH = 10;
	private final int thugATTACK = 3;
	//Other attributes (Used for game logic)
	private int turns;
	private boolean[][] mapOccupied= new boolean[GRID_WIDTH][GRID_HEIGHT];
	
	public static EngineExample getInstance() {
		if (INSTANCE == null)
			INSTANCE = new EngineExample();
		return INSTANCE;
	}

	public boolean getMapOccupied(int x, int y) {
		return this.mapOccupied[x][y];
	}

	public void setMapOccupied(boolean value, int x, int y) {
		this.mapOccupied[x][y] = value;
	}
	
	
	private EngineExample() {	
		
		for (int x=0; x!=GRID_WIDTH; x++)
			for (int y=0; y!=GRID_HEIGHT; y++)
				mapOccupied[x][y] = false;
		
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT);
		gui.go();
	}

	public void start() {
		// temporary
		int startingFloor = 0;
		addFloor();
		addObjects("rooms//room" + startingFloor + ".txt");
		addWall("rooms//room" + startingFloor + ".txt");
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
			
		}

		gui.addImages(tileList);
	}
	
	private void addObjects(String floor) {
		// Adding Hero
		hero = new Hero("Hero", new Point2D(4,4), heroHEALTH, heroATTACK);
		gui.addImage(hero);
		
		// Adding Skeletons
		skeletonList = levelReader.readEntity(floor, "Skeleton");
		for(Entity skeleton : skeletonList) {
			skeleton.setAttack(skeletonATTACK); skeleton.setHealth(skeletonHEALTH);
			gui.addImage(skeleton);
		}
		
		// Adding Bats
		batList = levelReader.readEntity(floor, "Bat");
		for(Entity bat : batList) {
			bat.setAttack(batATTACK); bat.setHealth(batHEALTH);
			gui.addImage(bat);
		}
		
		// Adding Thugs
		thugList = levelReader.readEntity(floor, "Thug");
		for(Entity thug : thugList) {
			thug.setAttack(thugATTACK); thug.setHealth(thugHEALTH);
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
		for(Entity skeleton : skeletonList) {
			Direction skeletonDirection = Direction.forVector(Vector2D.movementVector(skeleton.getPosition(), hero.getPosition()));
			if ( turns % 2 != 0 ) {
				skeleton.move(skeletonDirection);
			}
		}
		
		// Bats Movement (still doesn't check for walls)
		for(Entity bat : batList) {
			if ((int)(Math.random() * 2) == 1) { // 50/50 chance
				Direction batDirection = Direction.forVector(Vector2D.movementVector(bat.getPosition(), hero.getPosition()));
				bat.move(batDirection);
			}else {
				Direction randomDirection = Direction.random();
				bat.move(randomDirection);
			}
		}
		
		// Updates Status Message
		gui.setStatusMessage("Turns" + turns);
		
		// Updates Graphical User Interface
		gui.update();
	}

	
}

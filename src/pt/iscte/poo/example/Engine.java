package pt.iscte.poo.example;

import java.util.ArrayList;
import java.util.Iterator;
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
	private Hero hero;
	private final int heroHEALTH = 10;
	private final int heroATTACK = 1;
	
	//Maybe its not worth it to have multiple lists one for each type of entity, change later
	
	// Skeleton related attributes
	private List<Skeleton> skeletonList;
	
	//Bat related attributes
	private List<Bat> batList;
	
	// Thug related attributes
	private List<Thug> thugList;
	
	//Other attributes (Used for storing information)
	private static int turns;
	private static List<GameElement> elementList = new ArrayList<>();
	private static List<Entity> entityList = new ArrayList<>();
	
	
<<<<<<< Updated upstream
	//Still needs Layer checking and "items stuff"
=======
	// - - Methods --
	
	//Still needs Layer checking and "items stuff" (Not so sure about this layer checking i wrote some weeks ago)
>>>>>>> Stashed changes
	public static boolean isValid(Point2D position) {
		for (GameElement g : elementList) {
			if ( position.equals(g.getPosition()) ) 
				return false;
		}
		return true;
	}
	
<<<<<<< Updated upstream
	public static List<GameElement> getElementList() {
		return elementList;
=======
	//Used localy to get the entity at a given position
	private Entity getEntityAt(Point2D position) {
		for (Entity e: entityList) {
			if ( e.getPosition().equals(position) ) {
				return e;
		};
	}
		return null;
>>>>>>> Stashed changes
	}
	
	public static List<Entity> getEntityList() {
		return entityList;
	}
	
	public static int getTurns() {
		return turns;
	}
	
	//Probably only need the position, still have to think about it
	public static Entity getEntityAt(Point2D position, String name) {
		for (Entity e: getEntityList()) {
			if (e.getPosition().equals(position) || e.getName().equals(name)) {
				return e;
		};
	}
		return null;
	}
	
		
<<<<<<< Updated upstream
	//Will have to rewrite the whole damage system :(
	public static void  setEntityHealth(int attack, String name, Point2D position) {
		Entity e = getEntityAt(position, name);
		int newHealth = e.getHealth() - attack;;
		System.out.println(e.getName() + " new health is " + newHealth);
		if (newHealth <= 0) {
			//I will need to alter this function into non static
		}else {
			e.setHealth(newHealth);

=======
	//Used locally to update entities health
	private void attackEntity(Entity attacker, Point2D newPosition) {
		Entity attacked = getEntityAt(newPosition);
		if (attacked != null) {
			attacked.setHealth(attacked.getHealth() - attacker.getAttack());
			System.out.println("Attacked health is: " + attacked.getHealth());
			if ( attacked.getHealth() <= 0 ) {
				gui.removeImage(attacked);
				removeFromLists(attacked);
				gui.update();
			}
>>>>>>> Stashed changes
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
	
	//Hero's position missing
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
		Point2D newPosition;
		
		switch (keyPressed) {
		case KeyEvent.VK_UP:
			newDirection = Direction.UP;
			hero.move(newDirection);
			newPosition = hero.getPosition().plus(newDirection.asVector());
			attackEntity(hero, newPosition);
			turns++;
			break;
		case KeyEvent.VK_LEFT:
			newDirection = Direction.LEFT;
			hero.move(newDirection);
			newPosition = hero.getPosition().plus(newDirection.asVector());
			attackEntity(hero, newPosition);
			turns++;
			break;
		case KeyEvent.VK_RIGHT:
			newDirection = Direction.RIGHT;
			hero.move(newDirection);
			newPosition = hero.getPosition().plus(newDirection.asVector());
			attackEntity(hero, newPosition);
			turns++;
			break;
		case KeyEvent.VK_DOWN:
			newDirection = Direction.DOWN;
			hero.move(newDirection);
			newPosition = hero.getPosition().plus(newDirection.asVector());
			attackEntity(hero, newPosition);
			turns++;
			break;
		default:
			break;
		}
				
		// Skeletons Movement
		for(Skeleton skeleton : skeletonList) {
			newDirection = Direction.forVector(Vector2D.movementVector(skeleton.getPosition(), hero.getPosition()));
			skeleton.move(newDirection);
			newPosition = skeleton.getPosition().plus(newDirection.asVector());
			attackEntity(skeleton, newPosition);
		}
		
		// Bats Movement
		for(Bat bat : batList) {
			newDirection = Direction.forVector(Vector2D.movementVector(bat.getPosition(), hero.getPosition()));
			bat.move(newDirection);
			newPosition = bat.getPosition().plus(newDirection.asVector());
			attackEntity(bat, newPosition);
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
	
	private void removeFromLists(Entity e) {
		skeletonList.remove(e);
		batList.remove(e);
		thugList.remove(e);
		elementList.remove(e);
		entityList.remove(e);
	}
	
	//Removes all gameElements and cleans all Lists. (A bit messy)
//	private void removeObjects() {
//		for (GameElement e : elementList)
//			gui.removeImage(e);
//		
//		Iterator<GameElement> it = elementList.iterator();
//		while(it.hasNext()) {it.remove();}
//		Iterator<Entity> it1 = entityList.iterator();
//		while(it1.hasNext()) {it1.remove();}
//		Iterator<Skeleton> it2 = skeletonList.iterator();
//		while(it2.hasNext()) {it2.remove();}
//		Iterator<Bat> it3 = batList.iterator();
//		while(it3.hasNext()) {it3.remove();}
//		Iterator<Thug> it4 = thugList.iterator();
//		while(it4.hasNext()) {it4.remove();}
//	}
		
	}

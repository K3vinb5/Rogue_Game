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

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	
	private static EngineExample INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	
	private Skeleton skeleton;
	private Hero hero;
	private int turns;
	
	public static EngineExample getInstance() {
		if (INSTANCE == null)
			INSTANCE = new EngineExample();
		return INSTANCE;
	}

	private EngineExample() {		
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT);
		gui.go();
	}

	public void start() {
		addFloor();
		addObjects();
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
	
	private void addObjects() {
		hero = new Hero("Hero", new Point2D(4,4), 10, 1);
		
		//Needs to be changed later to spawn based on file
		skeleton = new Skeleton("Skeleton", new Point2D(0,0), 5, 1);
		
		
		gui.addImage(hero);
		gui.addImage(skeleton);
	}
	
	@Override
	public void update(Observed source) {
		
		// Hero Movement
		int keyPressed = ((ImageMatrixGUI) source).keyPressed();
				
		switch (keyPressed) {
		case KeyEvent.VK_UP:
			hero.move(Direction.UP, GRID_WIDTH, GRID_HEIGHT);
			turns++;
			break;
		case KeyEvent.VK_LEFT:
			hero.move(Direction.LEFT, GRID_WIDTH, GRID_HEIGHT);
			turns++;
			break;
		case KeyEvent.VK_RIGHT:
			hero.move(Direction.RIGHT, GRID_WIDTH, GRID_HEIGHT);
			turns++;
			break;
		case KeyEvent.VK_DOWN:
			hero.move(Direction.DOWN, GRID_WIDTH, GRID_HEIGHT);
			turns++;
			break;
		default:
			break;
		}
		
		//Skeleton Movement
		Direction skeletonDirection = Direction.forVector(Vector2D.movementVector(skeleton.getPosition(), hero.getPosition()));
		if ( turns%2 != 0 ) {
			skeleton.move(skeletonDirection, GRID_WIDTH, GRID_HEIGHT);
		}
		
		// Updates Status Message
		gui.setStatusMessage("Turns" + turns);
		
		
		
		// Updates Graphical User Interface
		gui.update();
	}
}

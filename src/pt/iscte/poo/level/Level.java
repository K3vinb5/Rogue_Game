package pt.iscte.poo.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.entities.*;
import pt.iscte.poo.interfaces.*;
import pt.iscte.poo.items.*;
import pt.iscte.poo.main.*;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Level {
	
	private List<GameElement> elementList = new ArrayList<>();
	private List<Floor> floorList = new ArrayList<>();
	private String name;
	private Hero hero;
	private Engine gui = Engine.getInstance();
	
	public Level(String path) {
		floorList.addAll(readFloors(path));
		elementList.addAll(readWalls(path));
		elementList.addAll(readEntity(path));
		elementList.addAll(readItems(path));
		elementList.addAll(readExtraContent(path)); // Extra content, only applies for level 4 onwards
		name = "room" + path.split("room")[1];
	}
	
	public List<Floor> getFloorList(){
		return floorList;
	}
	
	public List<GameElement> getElementList() {
		return elementList;
	}
	
	public void setHero(Hero hero) {
		this.hero = hero;
		if (!elementList.contains(hero))
			elementList.add(hero);
	}
	
	public Hero getHero() {
		return hero;
	}
	
	public void removeFromList(GameElement g) {
		Iterator<GameElement> it = elementList.iterator();
		while(it.hasNext()) {
			GameElement element = it.next();
			if ( element.equals(g) )
				it.remove();
		}
	}
	
	public void insertIntoList(GameElement g) {
		if (!elementList.contains(g))
			elementList.add(g);
	}
	
	public String getName() {
		return name;
	}
	
	public GameElement getEntity(Point2D position) {
		for (GameElement g : elementList) {
			
			if (g.getPosition().equals(position) && (g instanceof Entity)) {
				Entity e = (Entity)g;
				if(e.getHealth() > 0)
					return e;
			}

		}
		return null;
	}
	
	public GameElement getElement(Point2D position) {
		for (GameElement g : elementList) 
			if (g.getPosition().equals(position)) {
				return g;
			}
		return null;
	}
	
	public GameElement getItem(Point2D position) {
		for (GameElement g : elementList) {
			if (g.getPosition().equals(position) && g instanceof Transposible) {
				return g;
			}
		}
		return null;
	}
	
	public Point2D getValidNeighboringPosition(Point2D position) {
		Hero hero = gui.getLevel().getHero();
		if (isValid(position.plus(Direction.UP.asVector()), hero)){
			return position.plus(Direction.UP.asVector());
		} else if (isValid(position.plus(Direction.LEFT.asVector()), hero)){
			return position.plus(Direction.LEFT.asVector());
		} else if (isValid(position.plus(Direction.DOWN.asVector()), hero)){
			return position.plus(Direction.DOWN.asVector());
		}else if (isValid(position.plus(Direction.RIGHT.asVector()), hero)){
			return position.plus(Direction.RIGHT.asVector());
		}
		return null;
	}
	
		public static ArrayList<GameElement> readWalls(String FilePath){
		
		ArrayList<GameElement> wallList= new ArrayList<>();
		
		try {
			
			File file = new File(FilePath);
			Scanner scanner = new Scanner(file);
			int j = 0;
			
			// Reads Level
			while(scanner.hasNextLine()) {
				
				if (j > Engine.GRID_HEIGHT ) {break;} 
				
				String line = scanner.nextLine();
				char[] lineArray = line.toCharArray();
				int i = 0;
				
				for (char c : lineArray) {
					if (c == '#') {
						wallList.add( new Wall(new Point2D(i, j) ) );
					}
					i++; // increments column counter (x)
				}
				j++; //Increments line counter (y)
			}
						
			scanner.close();
			
		} catch (FileNotFoundException exception ) {
			System.err.println("Ficheiro não encontrado");
		}
		
		return wallList;
		
	}
		
		public static ArrayList<Floor> readFloors(String FilePath){
			
		ArrayList<Floor> floorList= new ArrayList<>();
		
		try {
			
			File file = new File(FilePath);
			Scanner scanner = new Scanner(file);
			int j = 0;
			
			// Reads Level
			while(scanner.hasNextLine()) {
				
				if (j > Engine.GRID_HEIGHT ) {break;} 
				
				String line = scanner.nextLine();
				char[] lineArray = line.toCharArray();
				int i = 0;
				
				for (char c : lineArray) {
					if (c == ' ') {
						if ((int)(Math.random() * 20) < 1) {
							floorList.add(new Floor("Grass", new Point2D(i, j) ) );
						}else {							
							floorList.add( new Floor(new Point2D(i, j) ) );
						}
					}
					i++; // increments column counter (x)
				}
				j++; //Increments line counter (y)
			}
						
			scanner.close();
			
		} catch (FileNotFoundException exception ) {
			System.err.println("Ficheiro não encontrado");
		}
		
		return floorList;
		
	}
	
	public static List<Entity> readEntity(String filePath) {
		
		List<Entity> returnEntityList = new ArrayList<>();
		
		try {
			
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			
			// Skipping the grid information
			for (int i = 0; i < Engine.GRID_HEIGHT; i++) {
				scanner.nextLine();
			}
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] attributes = line.split(",");
				
				switch (attributes[0]) {
//				case "Hero":
//						returnEntityList.add(new Hero(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
//					break;
				case "Skeleton":
					returnEntityList.add(new Skeleton( new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
					break;
				case "Bat":
					returnEntityList.add(new Bat( new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
					break;
				case "Thug":
					returnEntityList.add(new Thug( new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
					break;
				case "Thief":
					returnEntityList.add(new Thief( new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
					break;
				case "Scorpion":
					returnEntityList.add(new Scorpion( new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
					break;
				default:
					break;
				}
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");		
		}
		return returnEntityList;
	}
	
	public static List<GameElement> readItems(String filePath) {
		
		List<GameElement> returnEntityList = new ArrayList<>();
		
		try {
			
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			
			// Skipping the grid information
			for (int i = 0; i < Engine.GRID_HEIGHT; i++) {
				scanner.nextLine();
			}
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] attributes = line.split(",");
				
				switch (attributes[0]) {
				case "Sword":
						returnEntityList.add(new Sword(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])),2));
					break;
				case "HealingPotion":
					returnEntityList.add(new HealingPotion(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
				break;
				case "Armor":
					returnEntityList.add(new Armor(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
				break;
				case "Key":
					returnEntityList.add(new Key(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])), attributes[3]));
				break;
				case "Door":
					if ( attributes.length == 6 ) {
						//We'll use Constructor 2
						returnEntityList.add(new Door(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])), attributes[3], new Point2D(Integer.parseInt(attributes[4]), Integer.parseInt(attributes[5])) ));
					}else if (attributes.length == 7) {
						//We'll use constructor 1
						returnEntityList.add(new Door(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])), attributes[3], new Point2D(Integer.parseInt(attributes[4]), Integer.parseInt(attributes[5])), attributes[6] ));
					}
				break;
				case "Treasure":
					returnEntityList.add(new Treasure(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
				break;
				default:
					break;
				}
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");		
		}
		return returnEntityList;
	}	
	
	public static List<GameElement> readExtraContent(String filePath) {
		
		List<GameElement> returnEntityList = new ArrayList<>();
		
		try {
			
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			
			// Skipping the grid information
			for (int i = 0; i < Engine.GRID_HEIGHT; i++) {
				scanner.nextLine();
			}
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] attributes = line.split(",");
				
				switch (attributes[0]) {
				case "SuperHealingPotion":
					returnEntityList.add(new SuperHealingPotion(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
				break;
				case "GoldenKey":
					returnEntityList.add(new GoldenKey(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])), attributes[3]));
				break;
				case "RedDoor":
					if ( attributes.length == 6 ) {
						//We'll use Constructor 2
						returnEntityList.add(new RedDoor(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])), attributes[3], new Point2D(Integer.parseInt(attributes[4]), Integer.parseInt(attributes[5])) ));
					}else if (attributes.length == 7) {
						//We'll use constructor 1
						returnEntityList.add(new RedDoor(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])), attributes[3], new Point2D(Integer.parseInt(attributes[4]), Integer.parseInt(attributes[5])), attributes[6] ));
					}
				break;
				case "DiamondTreasure":
					returnEntityList.add(new DiamondTreasure(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
				break;
				default:
					break;
				}
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");		
		}
		return returnEntityList;
	}	
	
	public <T> boolean isValid(Point2D newPosition, T caller) {
		boolean returnValue = true;
		for (GameElement g : gui.getLevel().getElementList()) {
			if (newPosition.equals(g.getPosition()) && (g instanceof Attackable) ) { //posicao ser igual a de um atacavel
				returnValue = returnValue && false;
			}
			if (newPosition.equals(g.getPosition()) && !(g instanceof Entity) && !(g instanceof Transposible)) { //Posicao ser igual a de um objeto nao transposivel
				returnValue = returnValue && false;
			}
			if (newPosition.equals(g.getPosition()) &&  (g instanceof DoorComponent) && !(caller instanceof Hero) ) { //Posicao ser igual a de um objeto transposivel
				returnValue = returnValue && false;
			}
			if (newPosition.equals(g.getPosition()) &&  (g instanceof Transposible) ) { //Posicao ser igual a de um objeto transposivel
				returnValue = returnValue && true;
			}
		}
		return returnValue; //Posicao nao ser igual a nada
	}
	
	
	public void loadLevel(int newLevel, Point2D newPosition) {
		// Removes all Images (Except floors)
		gui.removeSprites();
		Hero hero = gui.getLevel().getHero();
		gui.setCurrentFloor(newLevel);
		gui.getLevel().setHero(hero);

		//Floor Elements
		for (GameElement g : gui.getLevel().getFloorList() ) {
			gui.addSprite(g);
		}
		
		// Level Elements
		for (GameElement g : gui.getLevel().getElementList()) {
			if (g instanceof Entity) {
				Entity e = (Entity) g;
				if (e.getHealth() > 0) {
					gui.addSprite(g);
				}
			} else {
				gui.addSprite(g);
			}
		}

		// Status Elements
		for (GameElement g : hero.getStats().getStatsElements()) {
			gui.addSprite(g);
		}

		hero.setPosition(newPosition);
		gui.updateGui();
	}
	
}

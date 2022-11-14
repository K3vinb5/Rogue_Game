package pt.iscte.poo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.utils.Point2D;

public class Level {
	
	private List<GameElement> elementList = new ArrayList<>();
	private String name;
	private Hero hero;
	
	public Level(String path) {
		elementList.addAll(readWalls(path));
		elementList.addAll(readEntity(path));
		elementList.addAll(readItems(path));
		name = "room" + path.split("room")[1];
	}
	
	public List<GameElement> getElementList() {
		return elementList;
	}
	
	public void setHero(Hero hero) {
		this.hero = hero;
		elementList.add(hero);
	}
	
	public Hero getHero() {
		return hero;
	}
	
	public void removeFromLists(GameElement g) {
		//Iterator<GameElement> itG = elementList.iterator();
		elementList.remove(g);
	}
	
	public String getName() {
		return name;
	}
	
	
	public boolean isValid(Point2D newPosition) {
		for (GameElement g : elementList) 
			if (newPosition.equals(g.getPosition())) {
				
				if(g instanceof Entity) {
					Entity e = (Entity)g;
					if (e.getHealth() > 0) {
						return false;
					}else {
						return true;
					}
				}
				
				return false;
			}
		return true;
	}
	
	public Entity getEntity(Point2D position) {
		for (GameElement g : elementList) 
			if (g.getPosition().equals(position) && (g instanceof Entity)) {
				Entity e = (Entity)g;
				if(e.getHealth() > 0)
					return e;
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
						returnEntityList.add(new Sword(new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2]))));
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
	
}

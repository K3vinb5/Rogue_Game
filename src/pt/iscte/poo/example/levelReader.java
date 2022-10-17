package pt.iscte.poo.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.utils.Point2D;

public class levelReader {
	
	
	// Working but I still don't know if I should have it in this class or have this class at all
	public static ArrayList<Wall> readWalls(String FilePath){
		
		ArrayList<Wall> wallList= new ArrayList<>();
		
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
	
	public static List<Entity> readEntity(String filePath, String EntityName) {
		
		List<Entity> returnEntityList = new ArrayList<>();
		
		try {
			
			File file = new File(filePath);
			Scanner scanner = new Scanner(file);
			
			// We skip the Grid related information
			for (int i = 0; i < Engine.GRID_HEIGHT; i++) {
				scanner.nextLine();
			}
			
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] attributes = line.split(",");

				// Checks if read token is the object we are looking for
				if ( attributes[0].equals(EntityName) ) {
					returnEntityList.add(new Entity( EntityName, new Point2D(Integer.parseInt(attributes[1]), Integer.parseInt(attributes[2])) , 0, 0));
				}
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");		
		}
		return returnEntityList;
	}
	
	
}

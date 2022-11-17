package pt.iscte.poo.example;

//import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Stats {
	
	private boolean[] slotsOccupied= new boolean[3];
	private List<GameElement> itemsList= new ArrayList<>();
	private int HEIGHT = Engine.GRID_HEIGHT;
	private static int WIDTH = Engine.GRID_WIDTH - 3;
	
	public Stats() {
		
		for (int x = 0; x < Engine.GRID_WIDTH; x++) {
			for (int y = Engine.GRID_HEIGHT; y != Engine.GRID_HEIGHT + Engine.GRID_HEIGHT_STATS; y++) {
				if (x< Engine.GRID_WIDTH - 3) {
					this.itemsList.add(new StatusComponent("Green", new Point2D(x,y)));
				} else {
					this.itemsList.add(new StatusComponent("Black", new Point2D(x,y)));
				}
			}
		}
	}

	// Inventory related
	
	public int setItem(GameElement item) {
		int i;
		for (i = 0; i < slotsOccupied.length; i++) {
			if (!slotsOccupied[i]) {
				itemsList.add(item);
				item.setPosition(new Point2D(WIDTH + i ,HEIGHT));
				slotsOccupied[i] = true;
				break;
			}
		}
		return i;
	}
	
	public void removeItem(int slotNumber, GameElement item) {
		//If not the last then:
		
		itemsList.remove(item);
		slotsOccupied[slotNumber] = false;
	}
	
	public GameElement getItem(int slotNumber) {
		int index=slotNumber + Engine.GRID_WIDTH;
		while(index >= itemsList.size()) {
			index--;
		}
		return itemsList.get(index);
	}
	
	public boolean isOccupied(int slotNumber) {
		return slotsOccupied[slotNumber];
	}
	
	public int occupiedSlots() {
		int returnValue = 0;
		for (int i  = 0; i < slotsOccupied.length; i++)
			if (slotsOccupied[i])
				returnValue++;
		return returnValue;
	}
	
	// HealthBar related
	
	public List<GameElement> getStatsElements(){
		return itemsList;
	}
	
	public boolean isFull() {
		return (isOccupied(0) && isOccupied(1) && isOccupied(2));
	}
	
    public static int[] getHealthBarMapping(double currentHealth, double maxHealth) {
		double LeftRightMiddle = WIDTH*currentHealth/maxHealth;
		double rightLeftMiddle = WIDTH - LeftRightMiddle;
		boolean halfNotUsed = true;
		int[] returnArray = new int[WIDTH];
		
		for (int i = 0; i < WIDTH; i++) {
			if( halfNotUsed && isInNeighbourhood(i,rightLeftMiddle)) {
			    returnArray[i] = 2;
			    halfNotUsed = false;
			}else if( i < rightLeftMiddle){
			    returnArray[i] = 0;
			}else {
				returnArray[i] = 1;
			}
		}
		
		if (currentHealth > 0 && returnArray[WIDTH - 1] == 0) 
			returnArray[WIDTH - 1] = 2;
		
		return returnArray;
	}
	
	public static boolean isInNeighbourhood(double f1, double f2){
	    return Math.abs(f1 - f2) < 0.5 && Math.abs(f1-f2) != 0;
	}
	
	public void redrawHealthBar(int[] healthBarMapping) {
		for(int i = 0; i < healthBarMapping.length; i++) {
			if (healthBarMapping[i] == 0 ) {
				this.itemsList.get(i).setName("Red");
			}else if (healthBarMapping[i] ==  1) {
				this.itemsList.get(i).setName("Green");
			}else {
				this.itemsList.get(i).setName("RedGreen");
			}
		}
	}

	@Override
	public String toString() {
		String returnValue = "";
		int index = 0;
		for (GameElement g : itemsList) {
			returnValue += index + " " + g.getName();
			index++;
		}
		return returnValue;
	}
	
}

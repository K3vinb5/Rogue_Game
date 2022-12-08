package pt.iscte.poo.main;

import pt.iscte.poo.items.KeyComponent;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Stats {
	
	private boolean[] slotsOccupied= new boolean[3];
	private List<GameElement> itemsList= new ArrayList<>();
	private boolean isVisible;
	private static int HEIGHT;
	private static int WIDTH;
	public final int INVENTORY_STARTING_INDEX;
	
	public Stats(boolean isVisible) {
		this.isVisible = isVisible;
		if (isVisible) {
			WIDTH = Engine.GRID_WIDTH - 3;
			HEIGHT = Engine.GRID_HEIGHT;
			INVENTORY_STARTING_INDEX = Engine.GRID_WIDTH;
			for (int x = 0; x < Engine.GRID_WIDTH; x++) {
				for (int y = Engine.GRID_HEIGHT; y != Engine.GRID_HEIGHT + Engine.GRID_HEIGHT_STATS; y++) {
					if (x< WIDTH) {
						this.itemsList.add(new StatusComponent("Green", new Point2D(x,y)));
					} else {
						this.itemsList.add(new StatusComponent("Black", new Point2D(x,y)));
					}
				}
			}
		}else {
			INVENTORY_STARTING_INDEX = 0;
			WIDTH = 0;
			HEIGHT = 0;
		}
	}

	// Inventory Related Methods
	
	// Adds an items to the first available slot in the inventory and returns the slot where that items was stored
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
	// Removes an item from a given inventory slot and if necessary shifts all the other items
	public void removeItem(int slotNumber, GameElement item) {
		
		int lastSlot = slotsOccupied.length - 1;
		// Shifts all slots to the left if the removed item was not in the last slot
		if (occupiedSlots() > 1 && slotNumber == lastSlot) {
			// Last slot case
			itemsList.remove(item);
			slotsOccupied[lastSlot] = false;
		} else if (occupiedSlots() >= 2) {
			// Multiple Items case and not last
			for (int i = 0; i < lastSlot; i++) {
				if (slotsOccupied[i + 1]) {
					itemsList.get(INVENTORY_STARTING_INDEX + i + 1).setPosition(new Point2D(
							itemsList.get(INVENTORY_STARTING_INDEX + i + 1).getPosition().getX() - 1, HEIGHT));
					slotsOccupied[i] = true;
					slotsOccupied[i + 1] = false;
				}
			}
			itemsList.remove(item);
		} else {
			// One slot case
			itemsList.remove(item);
			slotsOccupied[slotNumber] = false;
		}
		
	}
	public int getIndex(GameElement element) {
		int index = 0;
		for (GameElement g : itemsList) {
			if (g.equals(element)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	// Note : I don't remember what i did here, so if there is any problem this might be the culprit
	public GameElement getItem(int slotNumber) {
		int index=slotNumber + Engine.GRID_WIDTH;
		while(index >= itemsList.size()) {
			index--;
		}
		return itemsList.get(index);
	}
	// Returns a boolean telling if the given slot is occupied
	public boolean isOccupied(int slotNumber) {
		return slotsOccupied[slotNumber];
	}
	// Returns the number of occupied slots
	public int occupiedSlots() {
		int returnValue = 0;
		for (int i  = 0; i < slotsOccupied.length; i++)
			if (slotsOccupied[i])
				returnValue++;
		return returnValue;
	}
	
	public List<KeyComponent> getKeys(){
		List<KeyComponent> returnList = new ArrayList<>();
		for (int i = INVENTORY_STARTING_INDEX; i < INVENTORY_STARTING_INDEX + occupiedSlots(); i++ ) {
			if (itemsList.get(i) instanceof KeyComponent ) {
				returnList.add((KeyComponent)itemsList.get(i));
			}
		}
		return returnList;
	}
	
	// HealthBar Related Methods
	
	public List<GameElement> getStatsElements(){
		return itemsList;
	}
	
	public boolean isFull() {
		return (isOccupied(0) && isOccupied(1) && isOccupied(2));
	}
	
	public boolean isVisible() {
		return isVisible;
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

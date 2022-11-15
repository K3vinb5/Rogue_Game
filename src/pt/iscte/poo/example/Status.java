package pt.iscte.poo.example;

//import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Status {
	
	private boolean[] slotsOccupied= new boolean[3];
	private List<GameElement> itemsList= new ArrayList<>();
	private int HEIGHT = Engine.GRID_HEIGHT;
	private int WIDTH = Engine.GRID_WIDTH - 3;
	
	public Status() {
		
		for (int x = 0; x < Engine.GRID_WIDTH; x++) {
			for (int y = Engine.GRID_HEIGHT; y != Engine.GRID_HEIGHT + Engine.GRID_HEALTH; y++) {
				if (x< Engine.GRID_WIDTH - 3) {
					this.itemsList.add(new StatusComponent("Green", new Point2D(x,y)));
				} else {
					this.itemsList.add(new StatusComponent("Black", new Point2D(x,y)));
				}
			}
		}
	}

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
		if (slotNumber + 10 != itemsList.size() - 1) {
			if (occupiedSlots() == 2) {
				
			}
		}
		
		
		itemsList.remove(item);
		slotsOccupied[slotNumber] = false;
	}
	
	public GameElement getItem(int slotNumber) {
		int index=slotNumber + 10;
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
	
	public List<GameElement> getStatusElements(){
		return itemsList;
	}
	
	public boolean isFull() {
		return (isOccupied(0) && isOccupied(1) && isOccupied(2));
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

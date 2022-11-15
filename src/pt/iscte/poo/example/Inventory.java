package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends GameElement{

	private boolean slots[] = new boolean[3];
	private List<GameElement> itemsList= new ArrayList<>();
	
	public Inventory(Point2D position) {
		super("Black", position, 0);
	}

	public void setItem(int slotNumber, GameElement item) {
		slots[slotNumber]= true;
		itemsList.add(slotNumber, item);
	}
	
	public GameElement getItem(int slotNumber) {
		return itemsList.get(slotNumber);
	}
	
	@Override
	public int getLayer() {
		return 0;
	}
}

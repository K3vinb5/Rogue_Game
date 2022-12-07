package pt.iscte.poo.items;

import pt.iscte.poo.interfaces.Transposible;
import pt.iscte.poo.main.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Treasure extends GameElement implements Transposible{

	private boolean opened;
	
	public Treasure(Point2D position) {
		super("Treasure", position, 1);
		this.opened = false;
	}


	public boolean isOpened() {
		return opened;
	}


	public void setOpened(boolean opened) {
		this.opened = opened;
	}


	@Override
	public int getLayer() {
		return 1;
	}
}

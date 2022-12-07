package pt.iscte.poo.items;

import pt.iscte.poo.interfaces.Pickable;
import pt.iscte.poo.interfaces.Transposible;
import pt.iscte.poo.main.GameElement;
import pt.iscte.poo.utils.Point2D;

public abstract class KeyComponent extends GameElement implements Transposible, Pickable{

	private String keyID;
	
	public KeyComponent( Point2D position, String keyId) {
		super("Key", position, 1);
		this.keyID = keyId;
	}

	public String getKeyID() {
		return keyID;
	}

	public void setKeyID(String keyId) {
		this.keyID = keyId;
	}
	
	
}

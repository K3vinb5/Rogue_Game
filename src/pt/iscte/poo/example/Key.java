package pt.iscte.poo.example;

import pt.iscte.poo.utils.Point2D;

public class Key extends GameElement{

	private String keyId;
	
	public Key( Point2D position, String keyId) {
		super("Key", position, 2);
		this.setKeyId(keyId);
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	
	
}

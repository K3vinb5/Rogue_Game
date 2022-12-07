package pt.iscte.level;

import pt.iscte.poo.items.Key;
import pt.iscte.poo.utils.Point2D;

public class GoldenKey extends Key{

	public GoldenKey(Point2D position, String keyId) {
		super(position, keyId);
		this.setName("GoldenKey");
	}

}

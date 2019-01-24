package environment;

import java.util.Iterator;

public class MapIterator implements Iterator<Tile> {
	
	//Not currently implemented but will need to be in future 
	private int y;
	private int x;
	private Map map;
	
	public MapIterator(Map m) {
		this.y = 0;
		this.x = 0;
		this.map = m;
	}

	@Override
	public boolean hasNext() {
		if (this.x >= this.map.getRows() && this.y == this.map.getColumns() -1) {
			return false;
		}
		return true;
	}

	@Override
	public Tile next() {
		if (this.x >= this.map.getRows()) {
			this.x = 0;
			this.y++;
		}
		if (this.map.validCoordinates(y, x)) {
			int oldx = x;
			x++;
			return this.map.getTile(y, oldx);
		}
		return null;
	}

}

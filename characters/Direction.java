package characters;

public enum Direction {
	UP, RIGHT, DOWN, LEFT, NONE;
	
	public Direction reverseDirection() {
		switch(this) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;

		default:
			return LEFT;
		}
	}
}



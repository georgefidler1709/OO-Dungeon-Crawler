package gameEngine;

import characters.Direction;

public class Input {
	private InputType type;
	private Direction direction;
	private int itemNumber;
	
	public Input() {
		/*
		 * need to remove this call type from the rest of code
		 */
	}
	
	public Input (InputType type, Direction direction) {
		this.type = type;
		this.direction = direction;
	}
	
	public Input (InputType type, Direction direction, int num) {
		this.type = type;
		this.direction = direction;
		this.itemNumber = num;
	}
	
	public InputType getType() {
		return type;
	}
	public void setType(InputType type) {
		this.type = type;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	@Override
	public String toString() {
		return type + " " + direction;
	}
	
	
	
}

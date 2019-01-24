package items.inventory;

import characters.Direction;
import characters.Player;
import environment.Map;
import items.Item;
import javafx.scene.image.Image;

public class InventorySlot {
	Item item;
	int num;

	
	public InventorySlot(Item item) {
		this.item = item;
		this.num = 1;
	}

	public Item getItem() {
		return item;
	}

	public Integer getNum() {
		return num;
	}
	
	public void useItem(Map map, Direction direction, Player player) {
		item.use(map, player, direction);
		//need to know if we reduce the num in inventory or not - ?
		//this probably shouldnt be here anyway - Alex
	}
	
	public void addExtra() {
		this.num++;
	}
	
	public void removeItem() {
		this.num--;
		if(this.num <= 0) this.num = 0;
	}

	public boolean usable() {
		if(getItem().usable()) {
			return true;
		}
		return false;
	}

	public String toString() {
		return new String("Item: "+this.getItem().getClass().toString()+", Quantity: "+this.getNum());
	}

	public Image getItemImage() {
		return item.getImage();
	}
	
}


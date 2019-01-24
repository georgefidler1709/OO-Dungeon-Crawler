package items.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import characters.Direction;
import characters.Player;
import environment.Map;
import items.Item;
import items.objective.Key;

// A class to hold the state of the player's inventory.
public class Inventory {
	
	// The inventory is an array list of InventorySlots
	// which contain a number and an item.
	ArrayList<InventorySlot> inventory;

	// Constructor
	public Inventory() {
		this.inventory = new ArrayList<InventorySlot>();
	}
	
	// Add an item to the inventory. If the item already exists
	// its quantity will be increased, otherwise it is added in
	// a new inventory slot.
	public void addItem(Item newItem) {
		for(InventorySlot i : inventory) {
			if(i.getItem().equals(newItem)) {
				i.addExtra();
				return;
			}
		}
		inventory.add(new InventorySlot(newItem));
	}
	
	// Uses a specific item currently in the inventory.
	public void useItem(int slotNum, Direction direction, Map map, Player player) {
		inventory.get(slotNum).useItem(map, direction, player);
	}
	
	
	
	public void removeItem(Item i) {
		boolean found = false;
		InventorySlot itemSlot = null;
		for (InventorySlot slot : inventory) {
			if(slot.getItem().equals(i)) {
				found = true;
				itemSlot = slot;
				break;
			}
		}
		if(found) {
			if (itemSlot.getNum() > 1) {
				itemSlot.removeItem();
			} else {
				this.inventory.remove(itemSlot);
			}
		}
		
	}
	
	// Return true if player is holding a specific item
	// Return false otherwise
	public boolean hasItem(Class<? extends Item> i) {
		for (InventorySlot slot : inventory) {
			if(slot.getItem().getClass() == i &&
			   slot.getNum() > 0) {
				return true;
			}
		}
		return false;
	}

	public boolean hasItem(Item i) {
		for (InventorySlot slot : inventory) {
			if(slot.getItem() != null && slot.getItem().equals(i)) {
				return true;
			}
		}
		return false;
	}
	
	// Returns an iterator for all the items the player currently holds.
	// Stacked items will appear as duplicate entries in the iterator.
	// E.g. if a player has two arrows, they will appear as {arrow, arrow} in the iterator.
	public Iterator<InventorySlot> iterator() {
		/*Iterator<InventorySlot> it = this.inventory.iterator();
		ArrayList<Item> list = new ArrayList<Item>();
		while (it.hasNext()) {
			InventorySlot slot = it.next();
			for (int i = 0; i < slot.getNum(); i++) {
				list.add(slot.getItem());
			}
		}
		return list.iterator();*/
		return this.inventory.iterator();
	}

	public boolean usableItem(int itemIndex) {
		InventorySlot invSlot = inventory.get(itemIndex);
		if (invSlot.usable()) {
			return true;
		} 
		return false;
	}

	public int size() {
		return inventory.size();
	}
	
	// Print the current contents of the inventory - for debugging purposes.
	public void print() {
		System.out.println("Inventory Contents:");
		Iterator<InventorySlot> it = this.iterator();
		while(it.hasNext()) {
			InventorySlot curr = it.next();
			System.out.println(curr.toString());
		}
	}
	
	public Key getKey() {
		for (InventorySlot slot : inventory) {
			if(slot.getItem() instanceof Key) {
				return (Key) slot.getItem();
			}
		}
		return null;
	}
}


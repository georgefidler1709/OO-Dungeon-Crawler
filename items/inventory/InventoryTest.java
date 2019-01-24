package items.inventory;

import static org.junit.Assert.assertEquals;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import environment.Map;
import items.objective.Treasure;
import items.weapons.Arrow;
import items.weapons.Sword;

class InventoryTest {

	@Test
	void test() {
		Map m = new Map(1,1);
		
		Inventory i = new Inventory();
		Sword s = new Sword();
		i.print();
		i.addItem(s);
		i.print();
		assertEquals(i.hasItem(s.getClass()), true);
		i.removeItem(s);
		i.print();
		assertEquals(i.hasItem(s), false);
		Treasure t1 = new Treasure();
		Treasure t2 = new Treasure();
		Treasure t3 = new Treasure();
		Arrow a = new Arrow();
		i.addItem(s);
		i.print();
		i.addItem(t1);
		i.print();
		i.addItem(t2);
		i.print();
		i.addItem(t3);
		i.print();
		i.addItem(a);
		i.print();
		Iterator<InventorySlot> it = i.iterator();
		assertEquals(it.next().getItem(), s);
		assertEquals(it.next().getItem(), t1);
		assertEquals(it.next().getItem(), a);
	}

}

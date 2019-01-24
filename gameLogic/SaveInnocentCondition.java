package gameLogic;

import java.util.Iterator;

import environment.Map;
import environment.Tile;

//Game is won if the Innocent makes it to an exit
public class SaveInnocentCondition implements LogicCondition {
	//Game is won if the innocent is saved i.e. innocent at the exit.
	@Override
	public boolean checkLogicSatisfied(Map map) {
		return map.savedInnocent();
	}

	@Override
	public boolean isTarget(Tile t) {
		return (t.hasInnocent());
	}
	
	@Override
	public String toString() {
		return "Help Sheriff Oinks get to the Exit";
	}

	//Ensures the map has an exit
	@Override
	public boolean isValidMap(Map map) {
		Iterator<Tile> mapIterator = map.iterator();
		while(mapIterator.hasNext()) {
			Tile t = mapIterator.next();
			if(t.hasExit()) return true;
		}
		return false;
	}

	@Override
	public String mapRequirements() {
		return "Exit required for Save Innocent game mode";
	}
	

}

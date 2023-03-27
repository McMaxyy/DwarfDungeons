package abilities;

import java.io.Serializable;

public class Abilities implements Serializable{
	
	private int attackPower, abilityCost,  ID, requiredLevel;
	
	public Abilities(int attackPower, int abilityCost, int ID, int requiredLevel) {
        this.attackPower = attackPower;
        this.abilityCost = abilityCost;
        this.ID = ID;  
        this.requiredLevel = requiredLevel;
    }

	public int getAttackPower() {
		return attackPower;
	}

	public int getAbilityCost() {
		return abilityCost;
	}

	public int getID() {
		return ID;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}
}
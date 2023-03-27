package constants;

public class Abilities{
	
	private int attackPower, abilityCost,  ID;
	
	public Abilities(int attackPower, int abilityCost, int ID) {
        this.attackPower = attackPower;
        this.abilityCost = abilityCost;
        this.ID = ID; 
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
	
}
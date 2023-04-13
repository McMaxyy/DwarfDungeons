package constants;

public abstract class Abilities {
	private int attackPower, abilityCost,  ID;
	private String abilityName;

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getAbilityCost() {
		return abilityCost;
	}

	public void setAbilityCost(int abilityCost) {
		this.abilityCost = abilityCost;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}
}

class Swing extends Abilities{
	public Swing() {
		setAbilityName("Swing");
		setAttackPower(7);
		setAbilityCost(3);
		setID(1);
	}
}

class Decapitate extends Abilities{
	public Decapitate() {
		setAttackPower(10);
		setAbilityCost(4);
		setID(2);
	}
}

class Riposte extends Abilities{
	public Riposte() {
		setAbilityName("Decapitate");
		setAttackPower(4);
		setAbilityCost(2);
		setID(3);
	}
}

class Rend extends Abilities{
	public Rend() {
		setAbilityName("Rend");
		setAttackPower(3);
		setAbilityCost(2);
		setID(4);
	}
}

class Harden extends Abilities{
	public Harden() {
		setAbilityName("Harden");
		setAttackPower(2);
		setAbilityCost(2);
		setID(5);
	}
}

class Whirlwind extends Abilities{
	public Whirlwind() {
		setAbilityName("Whirlwind");
		setAttackPower(3);
		setAbilityCost(4);
		setID(6);
	}
}

class Weaken extends Abilities{
	public Weaken() {
		setAbilityName("Weaken");
		setAttackPower(4);
		setAbilityCost(2);
		setID(7);
	}
}

class Stun extends Abilities{
	public Stun() {
		setAbilityName("Stun");
		setAttackPower(0);
		setAbilityCost(2);
		setID(8);
	}
}

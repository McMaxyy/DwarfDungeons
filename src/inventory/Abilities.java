package inventory;

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

	public void setID(int ID) {
		this.ID = ID;
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
		setAttackPower(6);
		setAbilityCost(2);
		setID(1);
	}
}

class Decapitate extends Abilities{
	public Decapitate() {
		setAbilityName("Decapitate");
		setAttackPower(15);
		setAbilityCost(4);
		setID(2);
	}
}

class Riposte extends Abilities{
	public Riposte() {
		setAbilityName("Riposte");
		setAttackPower(3);
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
		setAbilityCost(1);
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
		setAbilityCost(1);
		setID(7);
	}
}

class Stun extends Abilities{
	public Stun() {
		setAbilityName("Stun");
		setAbilityCost(1);
		setID(8);
	}
}

class Bubble extends Abilities{
	public Bubble() {
		setAbilityName("Bubble");
		setAttackPower(6);
		setAbilityCost(3);
		setID(9);
	}
}

class Heal extends Abilities{
	public Heal() {
		setAbilityName("Heal");
		setAttackPower(5);
		setAbilityCost(2);
		setID(10);
	}
}

class Block extends Abilities{
	public Block() {
		setAbilityName("Block");
		setAbilityCost(1);
		setID(11);
	}
}

class PoisonSlash extends Abilities{
	public PoisonSlash() {
		setAbilityName("Poison Slash");
		setAttackPower(3);
		setAbilityCost(2);
		setID(12);
	}
}

class ExplosiveAttack extends Abilities{
	public ExplosiveAttack() {
		setAbilityName("Explosive Attack");
		setAttackPower(4);
		setAbilityCost(3);
		setID(13);
	}
}

class Confuse extends Abilities{
	public Confuse() {
		setAbilityName("Confuse");
		setAbilityCost(1);
		setID(14);
	}
}

class Pummel extends Abilities{
	public Pummel() {
		setAbilityName("Pummel");
		setAttackPower(7);
		setAbilityCost(3);
		setID(15);
	}
}

class TendonCutter extends Abilities{
	public TendonCutter() {
		setAbilityName("Tendon Cutter");
		setAttackPower(7);
		setAbilityCost(3);
		setID(16);
	}
}

class LifeSteal extends Abilities{
	public LifeSteal() {
		setAbilityName("Life Steal");
		setAttackPower(4);
		setAbilityCost(3);
		setID(17);
	}
}

class GroundBreaker extends Abilities{
	public GroundBreaker() {
		setAbilityName("Ground Breaker");
		setAttackPower(15);
		setAbilityCost(7);
		setID(18);
	}
}

class ShieldWall extends Abilities{
	public ShieldWall() {
		setAbilityName("Shield Wall");
		setAttackPower(6);
		setAbilityCost(6);
		setID(19);
	}
}

class FortifiedAttack extends Abilities{
	public FortifiedAttack() {
		setAbilityName("Fortified Attack");
		setAttackPower(10);
		setAbilityCost(6);
		setID(20);
	}
}
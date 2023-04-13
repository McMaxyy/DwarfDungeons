package constants;

public abstract class Weapons {
	private int attackPower, ID, isOwned;
	private String weaponName;
	
	public int getAttackPower() {
		return attackPower;
	}
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getWeaponName() {
		return weaponName;
	}
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
	public int getIsOwned() {
		return isOwned;
	}
	public void setIsOwned(int isOwned) {
		this.isOwned = isOwned;
	}	
}

class IronAxe extends Weapons{
	public IronAxe() {
		setWeaponName("Iron Axe");
		setAttackPower(1);
		setID(1);
		setIsOwned(0);
	}
}
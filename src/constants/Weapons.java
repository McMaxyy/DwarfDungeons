package constants;

public abstract class Weapons {
	private int attackPower, ID, isOwned, coinSellValue, coinValue;
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
	public int getCoinValue() {
		return coinValue;
	}
	public void setCoinValue(int coinValue) {
		this.coinValue = coinValue;
	}
	public int getCoinSellValue() {
		return coinSellValue;
	}
	public void setCoinSellValue(int coinSellValue) {
		this.coinSellValue = coinSellValue;
	}	
}

class IronAxe extends Weapons{
	public IronAxe() {
		setWeaponName("Iron Axe");
		setAttackPower(1);
		setID(1);
		setIsOwned(1);
		setCoinValue(20);
		setCoinSellValue(15);
	}
}

class SteelAxe extends Weapons{
	public SteelAxe() {
		setWeaponName("Steel Axe");
		setAttackPower(3);
		setID(2);
		setIsOwned(1);
		setCoinValue(40);
		setCoinSellValue(30);
	}
}
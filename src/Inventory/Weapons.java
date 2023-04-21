package Inventory;

public abstract class Weapons {
	private int attackPower, ID, amount, sellValue, buyValue;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int isOwned) {
		this.amount = isOwned;
	}
	public int getBuyValue() {
		return buyValue;
	}
	public void setBuyValue(int coinValue) {
		this.buyValue = coinValue;
	}
	public int getSellValue() {
		return sellValue;
	}
	public void setSellValue(int coinSellValue) {
		this.sellValue = coinSellValue;
	}	
}

class IronAxe extends Weapons{
	public IronAxe() {
		setWeaponName("Iron Axe");
		setAttackPower(1);
		setID(1);
		setAmount(2);
		setBuyValue(40);
		setSellValue(15);
	}
}

class SilverAxe extends Weapons{
	public SilverAxe() {
		setWeaponName("Silver Axe");
		setAttackPower(2);
		setID(2);
		setAmount(1);
		setSellValue(25);
	}
}

class GoldAxe extends Weapons{
	public GoldAxe() {
		setWeaponName("Gold Axe");
		setAttackPower(3);
		setID(3);
		setAmount(1);
		setSellValue(40);
	}
}

class SteelAxe extends Weapons{
	public SteelAxe() {
		setWeaponName("Steel Axe");
		setAttackPower(5);
		setID(4);
		setAmount(1);
		setBuyValue(150);
		setSellValue(60);
	}
}

class CopperAxe extends Weapons{
	public CopperAxe() {
		setWeaponName("Copper Axe");
		setAttackPower(6);
		setID(5);
		setAmount(1);
		setSellValue(90);
	}
}

class TitaniumAxe extends Weapons{
	public TitaniumAxe() {
		setWeaponName("Titanium Axe");
		setAttackPower(7);
		setID(6);
		setAmount(10);
		setSellValue(150);
	}
}

class FieryAxe extends Weapons{
	public FieryAxe() {
		setWeaponName("Fiery Axe");
		setAttackPower(9);
		setID(7);
		setAmount(1);
		setBuyValue(300);
		setSellValue(150);
	}
}

class MoltenAxe extends Weapons{
	public MoltenAxe() {
		setWeaponName("Molten Axe");
		setAttackPower(10);
		setID(8);
		setAmount(1);
		setSellValue(170);
	}
}

class WaterAxe extends Weapons{
	public WaterAxe() {
		setWeaponName("Water Axe");
		setAttackPower(14);
		setID(9);
		setAmount(1);
		setSellValue(300);
	}
}
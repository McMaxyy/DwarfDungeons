package Inventory;

import constants.Player;

public abstract class Items {
	private Player player = new Player();
	private int itemPower, ID, amount, buyValue, sellValue;
	private String itemName;
	public int pLevel = player.getLevel();
	
	public int getItemPower() {
		return itemPower;
	}
	public void setItemPower(int itemPower) {
		this.itemPower = itemPower;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getBuyValue() {
		return buyValue;
	}
	public void setBuyValue(int buyValue) {
		this.buyValue = buyValue;
	}
	public int getSellValue() {
		return sellValue;
	}
	public void setSellValue(int sellValue) {
		this.sellValue = sellValue;
	}
	
}

class HealthPot extends Items{
	public HealthPot() {
		setItemName("Health potion");
		if(pLevel <= 5)
			setItemPower(5);
		else if(pLevel <= 10)
			setItemPower(15);
		else
			setItemPower(25);
		setID(1);
		setAmount(1);
		setBuyValue(15);
		setSellValue(7);
	}
}

class Shield extends Items{
	public Shield() {
		setItemName("Shield");
		if(pLevel <= 5)
			setItemPower(4);
		else if(pLevel <= 10)
			setItemPower(12);
		else
			setItemPower(20);
		setID(2);
		setAmount(1);
		setBuyValue(15);
		setSellValue(7);
	}
}

class Bomb extends Items{
	public Bomb() {
		setItemName("Bomb");
		setItemPower(10);
		setID(3);
		setAmount(1);
		setBuyValue(13);
		setSellValue(10);
	}
}

class PoisonDart extends Items{
	public PoisonDart() {
		setItemName("Poison dart");
		setItemPower(2);
		setID(4);
		setAmount(1);
		setBuyValue(13);
		setSellValue(10);
	}
}

class BigBomb extends Items{
	public BigBomb() {
		setItemName("Big bomb");
		setItemPower(20);
		setID(5);
		setAmount(1);
		setBuyValue(25);
		setSellValue(15);
	}
}

class BiggerBomb extends Items{
	public BiggerBomb() {
		setItemName("Bigger bomb");
		setItemPower(35);
		setID(6);
		setAmount(1);
		setBuyValue(40);
		setSellValue(25);
	}
}
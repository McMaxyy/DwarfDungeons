package inventory;

public abstract class Materials {
	private int ID, amount;
	private String name;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

class IronIngot extends Materials{
	public IronIngot() {
		setName("Iron Ingot");
		setAmount(6);
		setID(1);
	}
}

class GoldIngot extends Materials{
	public GoldIngot() {
		setName("Gold Ingot");
		setAmount(0);
		setID(2);
	}
}

class SparkPowder extends Materials{
	public SparkPowder() {
		setName("Spark Powder");
		setAmount(1);
		setID(3);
	}
}

class LifePowder extends Materials{
	public LifePowder() {
		setName("Life Powder");
		setAmount(4);
		setID(4);
	}
}

class BottledLava extends Materials{
	public BottledLava() {
		setName("Bottled Lava");
		setAmount(2);
		setID(5);
	}
}

class BottledWater extends Materials{
	public BottledWater() {
		setName("Bottled Water");
		setAmount(2);
		setID(6);
	}
}
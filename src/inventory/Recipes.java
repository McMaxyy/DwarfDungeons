package inventory;

import constants.Player;

public abstract class Recipes {
	private int ID, reqMat1, reqMat2;
	private String name;	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getReqMat1() {
		return reqMat1;
	}
	public void setReqMat1(int reqMat1) {
		this.reqMat1 = reqMat1;
	}
	public int getReqMat2() {
		return reqMat2;
	}
	public void setReqMat2(int reqMat2) {
		this.reqMat2 = reqMat2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

class IronAxeRecipe extends Recipes{
	public IronAxeRecipe() {
		setName("Iron Axe");
		setReqMat1(3);
		setID(1);
	}
}

class CoinRecipe extends Recipes{
	public CoinRecipe() {
		setName("Coin");
		setReqMat1(1);
		setID(2);
	}
}

class BombRecipe extends Recipes{
	public BombRecipe() {
		setName("Bomb");
		setReqMat1(3);
		setID(3);
	}
}

class HealthPotRecipe extends Recipes{
	public HealthPotRecipe() {
		setName("Health Pot");
		setReqMat1(2);
		setID(4);
	}
}
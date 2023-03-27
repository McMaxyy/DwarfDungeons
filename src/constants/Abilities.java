package constants;

public class Abilities {
	
	private int attackPower, abilityCost, DoT;
	private boolean isUnlocked;
	
	public void overheadSwing() {
		attackPower = 4;
		abilityCost = 3;
	}
	
	public void decapitate() {
		attackPower = 8;
		abilityCost = 4;
	}
	
	public void rend() {
		DoT = 3;
		abilityCost = 2;
	}

	public boolean isUnlocked() {
		return isUnlocked;
	}

	public void setUnlocked(boolean isUnlocked) {
		this.isUnlocked = isUnlocked;
	}		
	
	private class Swing extends Abilities{
		private Swing() {
			attackPower = 4;
			abilityCost = 3;
			isUnlocked = false;
			
		}
	}
	
	public Abilities() {
		Swing swing = new Swing();
	}
	
}

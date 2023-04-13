package constants;

public class Weapon {

	private static int weaponDamage;
	
	public void upgradeWeapon(int x) {
		weaponDamage += x;
	}
	
	public void noWeapon() {
		weaponDamage = 0;
	}
	
	public void weaponOne(int x) {
		weaponDamage = x;
	}
	
	public int getWeaponDamage() {
		return weaponDamage;
	}
}

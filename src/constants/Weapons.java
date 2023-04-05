package constants;

public class Weapons {

	private static int weaponDamage;
	
	public void noWeapon() {
		weaponDamage = 0;
	}
	
	public void weaponOne() {
		weaponDamage = 1;
	}
	
	public int getWeaponDamage() {
		return weaponDamage;
	}
	
	public void setWeaponDamage(int weaponDamage) {
		Weapons.weaponDamage = weaponDamage;
	}
}

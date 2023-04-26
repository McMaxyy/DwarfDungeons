package constants;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.util.*;

public class Player {

	private static int maxHP = 30;
	private static int hp;
	private static int strength = 2;
	private static int mana = 4;
	private static int coins = 0;
	private static int level = 0;
	private static int exp = 0;
	private static int ability1ID, ability2ID, ability3ID, ability4ID;
	private static int activeWeapon;
	private static int levelCap = 10;
	private static int unlockedStage = 1;
	private static int tempStr;
	private static int merchantVisits;
	private int bubble = 0;
	public static JButton activeBag[] = new JButton[8];
	protected JLabel lblPlayerHP;
	private Random rand = new Random();
	private static JLabel playerIcon = new JLabel();
	private int x, y, z, w;
	
	public void playerShowHP(JLabel lblPlayerHP) {
		lblPlayerHP.setText("HP: " + hp);
	}
	
	public void playerLoseHP(int dmg) {
		hp -= dmg;
	}
	
	public void gainCoin(int coin) {
		coins += coin;
	}
	
	public void loseCoin(int coin) {
		coins -= coin;
	}
	
	public void increaseMana(int x) {
		mana += x;
	}
	
	public void increaseMerchantVisits() {
		merchantVisits++;
	}
	
	public void decreaseMerchantVisits() {
		merchantVisits--;
	}
	
	public boolean playerAttack() {
		x = rand.nextInt(10);
		if(x != 0 || x != 1 || x != 2)
			return true;
		else
			return false;
	}
	
	public void increaseExp(int x) {
		exp += x;		
	}
	
	public void increaseMaxHP() {
		maxHP += 5;
		hp = maxHP;
	}
	
	public void increasePlayerStr() {
		strength += 2;
		hp = maxHP;
	}
	
	public void increaseTempStr(int x) {
		tempStr += x;
	}
	
	public void increaseHP(int pot) {
		hp += pot;
		if(hp > maxHP)
			hp = maxHP;
	}
	
	public void levelUp() {
		if(level <= 15) {
			level++;
			exp = 0;
			if(level <= 5)
				levelCap += 20;
			else if (level >= 6 && level <= 9)
				levelCap += 40;
			else if (level == 10)
				levelCap += 50;
			else if (level >= 11 && level <= 15)
				levelCap += 100;
		}
	}
	
	public void playerOne() {
		tempStr = 0;
		mana = 4;
		hp = maxHP;
		x = rand.nextInt(1, 14);
		y = rand.nextInt(1, 14);
		z = rand.nextInt(1, 14);
		w = rand.nextInt(1, 14);
		ability1ID = x;
		while(y == x) {
			y = rand.nextInt(1, 14);
		}
		ability2ID = y;
		while (z == y || z == x)
			z = rand.nextInt(1, 14);
		ability3ID = z;
		while (w == y || w == x || w == z)
			w = rand.nextInt(1, 14);
		ability4ID = w;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		Player.mana = mana;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int playerHP) {
		Player.hp = playerHP;
	}
	
	public int getStrength() {
		return strength;
	}

	public void setStrength(int playerStrength) {
		Player.strength = playerStrength;
	}

	public JLabel getPlayerIcon() {
		return playerIcon;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int playerCoins) {
		Player.coins = playerCoins;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int playerExp) {
		Player.exp = playerExp;
	}

	public int getLevelCap() {
		return levelCap;
	}
	
	public void setLevelCap(int levelCap) {
		Player.levelCap = levelCap;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int playerMaxHP) {
		Player.maxHP = playerMaxHP;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int playerLevel) {
		Player.level = playerLevel;
	}

	public int getAbility1ID() {
		return ability1ID;
	}

	public void setAbility1ID(int ability1id) {
		ability1ID = ability1id;
	}

	public int getAbility2ID() {
		return ability2ID;
	}

	public void setAbility2ID(int ability2id) {
		ability2ID = ability2id;
	}

	public int getAbility3ID() {
		return ability3ID;
	}

	public void setAbility3ID(int ability3id) {
		ability3ID = ability3id;
	}
	
	public int getAbility4ID() {
		return ability4ID;
	}

	public void setAbility4ID(int ability4id) {
		ability4ID = ability4id;
	}

	public int getUnlockedStage() {
		return unlockedStage;
	}

	public void setUnlockedStage(int unlockedStage) {
		Player.unlockedStage = unlockedStage;
	}

	public int getTempStr() {
		return tempStr;
	}

	public void setMerchantVisits(int merchantVisits) {
		Player.merchantVisits = merchantVisits;
	}

	public int getMerchantVisits() {
		return merchantVisits;
	}

	public int getActiveWeapon() {
		return activeWeapon;
	}

	public void setActiveWeapon(int activeWeapon) {
		Player.activeWeapon = activeWeapon;
	}

	public JButton[] getActiveBag() {
		return activeBag;
	}

	public void setActiveBag(JButton activeBag[]) {
		Player.activeBag = activeBag;
	}

	public int getBubble() {
		return bubble;
	}

	public void setBubble(int bubble) {
		this.bubble = bubble;
	}
}

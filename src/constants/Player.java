package constants;

import javax.swing.JLabel;

import java.util.*;

public class Player {

	private static int playerMaxHP = 30;
	private static int playerHP;
	private static int playerStrength = 200;
	private static int mana = 4;
	private static int playerCoins = 100;
	private static int playerLevel = 0;
	private static int playerExp = 0;
	private static int ability1ID, ability2ID, ability3ID;
	private final int attackButtonCost = 1;
	private static int levelCap = 10;
	private static int unlockedStage = 1;
	private static int tempStr;
	private static int merchantVisits;
	protected JLabel lblPlayerHP;
	private Random rand = new Random();
	private static JLabel playerIcon = new JLabel();
	private int x, y, z;
	
	public void playerShowHP(JLabel lblPlayerHP) {
		lblPlayerHP.setText("HP: " + playerHP);
	}
	
	public void playerLoseHP(int dmg) {
		playerHP -= dmg;
	}
	
	public void playerGainCoin(int coin) {
		playerCoins += coin;
	}
	
	public void playerLoseCoin(int coin) {
		playerCoins -= coin;
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
	
	public void increasePlayerExp(int x) {
		playerExp += x;		
	}
	
	public void increaseMaxHP() {
		playerMaxHP += 5;
		playerHP = playerMaxHP;
	}
	
	public void increasePlayerStr() {
		playerStrength += 2;
		playerHP = playerMaxHP;
	}
	
	public void increaseTempStr(int x) {
		tempStr += x;
	}
	
	public void increaseHP(int pot) {
		playerHP += pot;
	}
	
	public void levelUp() {
		if(playerLevel <= 15) {
			playerLevel++;
			playerExp = 0;
			if(playerLevel <= 5)
				levelCap += 20;
			else if (playerLevel >= 6 && playerLevel <= 9)
				levelCap += 40;
			else if (playerLevel == 10)
				levelCap += 50;
			else if (playerLevel >= 11 && playerLevel <= 15)
				levelCap += 100;
		}
	}
	
	public void playerOne() {
		tempStr = 0;
		mana = 4;
		playerHP = playerMaxHP;
		x = rand.nextInt(1, 7);
		y = rand.nextInt(1, 7);
		z = rand.nextInt(1, 7);
		ability1ID = x;
		while(y == x) {
			y = rand.nextInt(1, 7);
		}
		ability2ID = y;
		while (z == y || z == x)
			z = rand.nextInt(1, 7);
		ability3ID = z;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		Player.mana = mana;
	}

	public int getPlayerHP() {
		return playerHP;
	}

	public void setPlayerHP(int playerHP) {
		Player.playerHP = playerHP;
	}
	
	public int getPlayerStrength() {
		return playerStrength;
	}

	public void setPlayerStrength(int playerStrength) {
		Player.playerStrength = playerStrength;
	}

	public JLabel getPlayerIcon() {
		return playerIcon;
	}

	public int getAttackButtonCost() {
		return attackButtonCost;
	}

	public int getPlayerCoins() {
		return playerCoins;
	}

	public void setPlayerCoins(int playerCoins) {
		Player.playerCoins = playerCoins;
	}

	public int getPlayerExp() {
		return playerExp;
	}

	public void setPlayerExp(int playerExp) {
		Player.playerExp = playerExp;
	}

	public int getLevelCap() {
		return levelCap;
	}
	
	public void setLevelCap(int levelCap) {
		Player.levelCap = levelCap;
	}

	public int getPlayerMaxHP() {
		return playerMaxHP;
	}

	public void setPlayerMaxHP(int playerMaxHP) {
		Player.playerMaxHP = playerMaxHP;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		Player.playerLevel = playerLevel;
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
	
}

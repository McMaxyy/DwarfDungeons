package constants;

import javax.swing.JLabel;

import java.util.*;

public class Player {

	private static int playerMaxHP = 100;
	private static int playerHP;
	private static int playerStrength = 2;
	private static int turnCount = 4;
	private static int playerCoins = 100;
	private static int playerLevel = 0;
	private static int playerExp = 0;
	private static int ability1ID, ability2ID, ability3ID;
	private final int attackButtonCost = 1;
	private static int levelCap = 10;
	private static int unlockedStage = 1;
	protected JLabel lblPlayerHP;
	private Random rand = new Random();
	private static JLabel playerIcon = new JLabel();
	int x, y, z;
	
	public void playerShowHP(JLabel lblPlayerHP) {
		lblPlayerHP.setText("Player HP: " + playerHP);
	}
	
	public void playerLoseHP(int dmg) {
		setPlayerHP(getPlayerHP() - dmg);
	}
	
	public void playerGainCoin(int coin) {
		setPlayerCoins(getPlayerCoins() + coin);
	}
	
	public void playerLoseCoin(int coin) {
		setPlayerCoins(getPlayerCoins() - coin);
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
		playerMaxHP += 2;
		playerHP = playerMaxHP;
	}
	
	public void increasePlayerStr() {
		playerStrength += 2;
		playerHP = playerMaxHP;
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
		playerHP = playerMaxHP;
		x = rand.nextInt(1, 4);
		y = rand.nextInt(1, 4);
		z = rand.nextInt(1, 4);
		ability1ID = x;
		while(y == x) {
			y = rand.nextInt(1, 4);
		}
		ability2ID = y;
		while (z == y || z == x)
			z = rand.nextInt(1, 4);
		ability3ID = z;
	}

	public int getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(int turnCount) {
		Player.turnCount = turnCount;
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
	
}

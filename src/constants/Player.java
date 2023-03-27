package constants;

import javax.swing.JLabel;

import java.util.*;

public class Player {

	private static int playerMaxHP = 10;
	private static int playerHP;
	private static int playerStrength = 4;
	private static int turnCount = 4;
	private static int playerCoins = 100;
	private static int playerLevel = 1;
	private static int playerExp = 0;
	private final int attackButtonCost = 1;
	private final int levelOneCap = 10;
	protected JLabel lblPlayerHP;
	private Random rand = new Random();
	private static JLabel playerIcon = new JLabel();
	private Entities entity = new Entities();
	
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
		int x = rand.nextInt(10);
		if(x != 0 || x != 1)
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
		playerLevel++;
		playerExp = 0;
	}
	
	public void playerOne() {
		playerHP = playerMaxHP;
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

	public int getLevelOneCap() {
		return levelOneCap;
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
	
}

package constants;

import javax.swing.JLabel;

import java.util.*;


public class Player {

	private static int playerHP;
	private static int playerStrength;
	private static int turnCount = 3;
	private final int attackButtonCost = 1;
	private int selectedPlayer;
	protected JLabel lblPlayerHP;
	private Random rand = new Random();
	private static JLabel playerIcon = new JLabel();
	private Entities entity = new Entities();
	
	public Player() {
		
	}
	
	public void playerShowHP(JLabel lblPlayerHP) {
		lblPlayerHP.setText("Player HP: " + String.valueOf(getPlayerHP()));
	}
	
	public void playerLoseHP(int dmg) {
		setPlayerHP(getPlayerHP() - dmg);
	}
	
	public boolean playerAttack() {
		int x = rand.nextInt(10);
		if(x != 0 || x != 1)
			return true;
		else
			return false;
	}
	
	public void playerOne() {
		selectedPlayer = 1;
		setPlayerHP(10);
		setPlayerStrength(2);
		playerIcon = entity.loadPlayerImg(selectedPlayer);
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

}

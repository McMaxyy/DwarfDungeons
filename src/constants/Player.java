package constants;

import javax.swing.JLabel;

import java.util.*;

import main.GameWindow;

public class Player {

	private int playerHP = 10;
	private int playerStrength = 3;
	protected JLabel lblPlayerHP;
	private Random rand = new Random();
	private GameWindow window;
	
	public Player(GameWindow window) {
		this.window = window;
	}
	
	public void playerShowHP(JLabel lblPlayerHP) {
		lblPlayerHP.setText(String.valueOf(getPlayerHP()));
	}
	
	public void playerLoseHP() {
		setPlayerHP(getPlayerHP() - 1);
	}
	
	public boolean playerAttack() {
		int x = rand.nextInt(9);
		if(x != 0 || x != 1)
			return true;
		else
			return false;
	}

	public int getPlayerHP() {
		return playerHP;
	}

	public void setPlayerHP(int playerHP) {
		this.playerHP = playerHP;
	}
	
	

}

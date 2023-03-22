package constants;

import javax.swing.JLabel;

import main.GameWindow;

public class Enemies {

	private int enemyHP;
	private int enemyStrength;
	protected JLabel lblEnemyHP;
	private GameWindow window;
	
	public Enemies(GameWindow window) {
		this.window = window;
	
	}
	
	public void enemyShowHP(JLabel lblEnemyHP) {
		lblEnemyHP.setText(String.valueOf(getEnemyHP()));
	}
	
	public void enemyLoseHP() {
		enemyHP--;
	}
	
	public void enemyOne() {
		enemyHP = 5;
		enemyStrength = 2;
	}
	
	public int getEnemyHP() {
		return enemyHP;
	}
	
	public void setEnemyHP(int enemyHP) {
		this.enemyHP = enemyHP;
	}
}

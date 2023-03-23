package constants;

import java.util.Random;

import javax.swing.JLabel;


public class Enemies {

	private int enemyHP;
	private int enemyStrength;
	protected JLabel lblEnemyHP;
	private Random rand = new Random();
	private JLabel enemyIcon = new JLabel();
	private int selectedEnemy;	
	private Entities entity = new Entities();
	
	public Enemies() {
	
	}
	
	public void enemyShowHP(JLabel lblEnemyHP) {
		lblEnemyHP.setText("Enemy HP: " + String.valueOf(getEnemyHP()));
	}
	
	public void enemyLoseHP(int dmg) {
		setEnemyHP(getEnemyHP() - dmg);
	}
	
	public void enemyOne() {
		selectedEnemy = 1;
		setEnemyHP(20);
		setEnemyStrength(1);
		enemyIcon = entity.loadEnemyImg(selectedEnemy);
	}
	
	public void enemyTwo() {
		selectedEnemy = 2;
		setEnemyHP(30);
		setEnemyStrength(2);
		enemyIcon = entity.loadEnemyImg(selectedEnemy);
	}
	
	public boolean enemyAttack() {
		int x = rand.nextInt(10);
		if(x != 0 || x != 1 || x != 2 || x!= 3)
			return true;
		else
			return false;
	}
	
	public int getEnemyHP() {
		return enemyHP;
	}
	
	public void setEnemyHP(int enemyHP) {
		this.enemyHP = enemyHP;
	}


	public int getEnemyStrength() {
		return enemyStrength;
	}

	public void setEnemyStrength(int enemyStrength) {
		this.enemyStrength = enemyStrength;
	}
	
	public JLabel getEnemyIcon() {
		return enemyIcon;
	}

}

package constants;

import java.util.Random;

import javax.swing.JLabel;


public class Enemies {

	private int enemyHP;
	private int enemyMaxHP;
	private int enemyStrength;
	private int enemyExp;
	protected JLabel lblEnemyHP;
	private Random rand = new Random();
	private JLabel enemyIcon = new JLabel();
	private int selectedEnemy;	
	private Entities entity = new Entities();
	
	public void enemyShowHP(JLabel lblEnemyHP) {
		lblEnemyHP.setText("Enemy HP: " + String.valueOf(getEnemyHP()));
	}
	
	public void enemyLoseHP(int dmg) {
		setEnemyHP(getEnemyHP() - dmg);
	}
	
	public void enemyOne() {
		selectedEnemy = 1;
		enemyMaxHP = 20;
		enemyHP = enemyMaxHP;
		enemyStrength = 1;
		enemyIcon = entity.loadEnemyImg(selectedEnemy);
		enemyExp = 10;
	}
	
	public void enemyTwo() {
		selectedEnemy = 2;
		enemyMaxHP = 30;
		enemyHP = enemyMaxHP;
		enemyStrength = 2;
		enemyIcon = entity.loadEnemyImg(selectedEnemy);
		enemyExp = 10;
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

	public int getEnemyExp() {
		return enemyExp;
	}

	public void setEnemyExp(int enemyExp) {
		this.enemyExp = enemyExp;
	}

	public int getEnemyMaxHP() {
		return enemyMaxHP;
	}

	public void setEnemyMaxHP(int enemyMaxHP) {
		this.enemyMaxHP = enemyMaxHP;
	}

	
}

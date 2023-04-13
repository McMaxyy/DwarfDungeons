package constants;

import java.awt.Image;
import java.util.Random;

import javax.swing.JLabel;

public class Enemies {

	private int enemyHP;
	private int enemyMaxHP;
	private int enemyStrength;
	private int expValue;
	private int coinValue;
	private int currentStage;
	private static boolean bossActive, strongEnemyActive;
	private String enemyName;
	private Image enemyIdle, enemyAttack;
	protected JLabel lblEnemyHP;
	private Random rand = new Random();
	private Storage entity = new Storage();
	
	public void enemyShowHP(JLabel lblEnemyHP) {
		lblEnemyHP.setText("HP: " + String.valueOf(getEnemyHP()));
	}
	
	public void enemyLoseHP(int dmg) {
		setEnemyHP(getEnemyHP() - dmg);
	}
	
	public void enemyOne() {
		if(currentStage == 1 || currentStage == 2 || currentStage == 3) {
			enemyName = "Baby spider";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSpoder.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSpoder.gif");
			enemyMaxHP = 20;
			enemyHP = enemyMaxHP;
			enemyStrength = 2;
			expValue = 2;
			coinValue = 3;
		}
	}
	
	public void enemyTwo() {
		if(currentStage == 1 || currentStage == 2 || currentStage == 3) {
			enemyName = "Schnopi";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleGoblin.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackGoblin.gif");
			enemyMaxHP = 30;
			enemyHP = enemyMaxHP;
			enemyStrength = 4;
			expValue = 4;
			coinValue = 5;
		}
	}
	
	public void strongEnemy() {
		if(currentStage == 1 || currentStage == 2 || currentStage == 3) {
			enemyName = "Bigdik";
			enemyIdle = entity.setAnimation("res/chr.png");
			enemyAttack = entity.setAnimation("res/chr.png");
			enemyMaxHP = 50;
			enemyHP = enemyMaxHP;
			enemyStrength = 6;
			expValue = 15;
			coinValue = 15;
		}
	}
	
	public void boss() {
		if(currentStage == 1 || currentStage == 2 || currentStage == 3) {
			enemyName = "Smoldik";
			enemyIdle = entity.setAnimation("res/dragun.png");
			enemyAttack = entity.setAnimation("res/dragun.png");
			enemyMaxHP = 80;
			enemyHP = enemyMaxHP;
			enemyStrength = 6;
			expValue = 25;
			coinValue = 30;
		}
	}
	
	public boolean enemyAttack() {
		int x = rand.nextInt(10);
		if(x != 0 || x != 1 || x != 2)
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

	public int getExpValue() {
		return expValue;
	}

	public void setExpValue(int expValue) {
		this.expValue = expValue;
	}

	public int getEnemyMaxHP() {
		return enemyMaxHP;
	}

	public void setEnemyMaxHP(int enemyMaxHP) {
		this.enemyMaxHP = enemyMaxHP;
	}

	public int getCoinValue() {
		return coinValue;
	}

	public void setCoinValue(int coinValue) {
		this.coinValue = coinValue;
	}

	public Image getEnemyIdle() {
		return enemyIdle;
	}
	public Image getEnemyAttack() {
		return enemyAttack;
	}

	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}

	public String getEnemyName() {
		return enemyName;
	}

	public boolean isBossActive() {
		return bossActive;
	}

	public void setBossActive(boolean bossActive) {
		Enemies.bossActive = bossActive;
	}

	public boolean isStrongEnemyActive() {
		return strongEnemyActive;
	}

	public void setStrongEnemyActive(boolean strongEnemyActive) {
		Enemies.strongEnemyActive = strongEnemyActive;
	}
	
}

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
		switch(currentStage) {
		case 1:
			enemyName = "Baby spider";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSpoder.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSpoder.gif");
			enemyMaxHP = 20;
			enemyHP = enemyMaxHP;
			enemyStrength = 2;
			expValue = 2;
			coinValue = 3;
			break;
		case 2:
			enemyName = "Wolf";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleWolf.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackWolf.gif");
			enemyMaxHP = 30;
			enemyHP = enemyMaxHP;
			enemyStrength = 4;
			expValue = 5;
			coinValue = 6;
			break;
		case 3:
			if(rand.nextInt(2) == 0) {
				enemyName = "Baby spider";
				enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSpoder.gif");
				enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSpoder.gif");
				enemyMaxHP = 30;
				enemyHP = enemyMaxHP;
				enemyStrength = 4;
				expValue = 7;
				coinValue = 8;
			}
			else {
				enemyName = "Wolf";
				enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleWolf.gif");
				enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackWolf.gif");
				enemyMaxHP = 40;
				enemyHP = enemyMaxHP;
				enemyStrength = 6;
				expValue = 10;
				coinValue = 12;
			}
			break;
		}
	}
	
	public void enemyTwo() {
		switch(currentStage) {
		case 1:
			enemyName = "Goblin";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleGoblin.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackGoblin.gif");
			enemyMaxHP = 30;
			enemyHP = enemyMaxHP;
			enemyStrength = 4;
			expValue = 4;
			coinValue = 5;
			break;
		case 2:
			enemyName = "Skeleton";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSkelly.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSkelly.gif");
			enemyMaxHP = 40;
			enemyHP = enemyMaxHP;
			enemyStrength = 6;
			expValue = 8;
			coinValue = 10;
			break;
		case 3:
			if(rand.nextInt(2) == 0) {
				enemyName = "Goblin";
				enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleGoblin.gif");
				enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackGoblin.gif");
				enemyMaxHP = 40;
				enemyHP = enemyMaxHP;
				enemyStrength = 6;
				expValue = 10;
				coinValue = 12;
			}
			else {
				enemyName = "Skeleton";
				enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSkelly.gif");
				enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSkelly.gif");
				enemyMaxHP = 50;
				enemyHP = enemyMaxHP;
				enemyStrength = 8;
				expValue = 14;
				coinValue = 16;
			}
			break;
		}
	}
	
	public void strongEnemy() {
		if(currentStage == 1 || currentStage == 2 || currentStage == 3) {
			enemyName = "Fatlin";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleFatlin.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackFatlin.gif");
			enemyMaxHP = 50;
			enemyHP = enemyMaxHP;
			enemyStrength = 6;
			expValue = 15;
			coinValue = 15;
		}
	}
	
	public void boss() {
		switch(currentStage) {
		case 1:
			enemyName = "Basic Sbeve";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSteve.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSteve.gif");
			enemyMaxHP = 80;
			enemyHP = enemyMaxHP;
			enemyStrength = 8;
			expValue = 25;
			coinValue = 30;
			break;
		case 2:
			enemyName = "Stronger Sbeve";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSteve2.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSteve2.gif");
			enemyMaxHP = 110;
			enemyHP = enemyMaxHP;
			enemyStrength = 10;
			expValue = 40;
			coinValue = 50;
			break;
		case 3:
			enemyName = "Angry Sbeve";
			enemyIdle = entity.setAnimation("res/EnemyAnimations/IdleSteve3.gif");
			enemyAttack = entity.setAnimation("res/EnemyAnimations/AttackSteve3.gif");
			enemyMaxHP = 150;
			enemyHP = enemyMaxHP;
			enemyStrength = 14;
			expValue = 100;
			coinValue = 100;
			break;
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

package constants;

import java.awt.Image;
import java.util.Random;

import javax.swing.JLabel;

import inventory.Storage;

public class Enemies {

	private int hp;
	private int maxHP;
	private int strength;
	private int expValue;
	private int coinValue;
	private int currentStage;
	private int currentLevel;
	private String ability1;
	private String ability2;
	private static boolean bossActive, strongEnemyActive;
	private String enemyName;
	private Image enemyIdle, enemyAttack;
	protected JLabel lblEnemyHP;
	private Random rand = new Random();
	private Storage s = Storage.getInstance();
	
	public void enemyShowHP(JLabel lblEnemyHP) {
		lblEnemyHP.setText("HP: " + String.valueOf(getHp()));
	}
	
	public void enemyLoseHP(int dmg) {
		setHp(getHp() - dmg);
	}
	
	public void enemyOne() {
		if(s.gameMode == 0) {
			switch(s.gameLevel) {
			case 1:
				switch(currentStage) {
				case 1:
					enemyName = "Spider";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSpoder.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSpoder.gif");
					maxHP = 30;
					hp = maxHP;
					strength = 4;
					expValue = 3;
					coinValue = 5;
					ability1 = "Stun";
					ability1 = "Buff";
					break;
				case 2:
					enemyName = "Wolf";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleWolf.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackWolf.gif");
					maxHP = 40;
					hp = maxHP;
					strength = 6;
					expValue = 5;
					coinValue = 8;
					ability1 = "Bleed";
					ability2 = "Buff";
					break;
				case 3:
					if(rand.nextInt(2) == 0) {
						enemyName = "Spider";
						enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSpoder.gif");
						enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSpoder.gif");
						maxHP = 40;
						hp = maxHP;
						strength = 6;
						expValue = 7;
						coinValue = 8;
						ability1 = "Stun";
						ability1 = "Buff";
					}
					else {
						enemyName = "Wolf";
						enemyIdle = s.setAnimation("res/EnemyAnimations/IdleWolf.gif");
						enemyAttack = s.setAnimation("res/EnemyAnimations/AttackWolf.gif");
						maxHP = 45;
						hp = maxHP;
						strength = 8;
						expValue = 10;
						coinValue = 12;
						ability1 = "Bleed";
						ability2 = "Buff";
					}
					break;
				}
				break;
			}			
		}
		else {
			switch(s.gameLevel) {
			case 1:
				if(rand.nextInt(2) == 0) {
					enemyName = "Spider";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSpoder.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSpoder.gif");
					maxHP = 35;
					hp = maxHP;
					strength = 4;
					expValue = 3;
					coinValue = 5;
					ability1 = "Stun";
					ability1 = "Buff";
				}
				else {
					enemyName = "Wolf";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleWolf.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackWolf.gif");
					maxHP = 40;
					hp = maxHP;
					strength = 6;
					expValue = 5;
					coinValue = 8;
					ability1 = "Bleed";
					ability2 = "Buff";
				}
				break;
			}			
		}
	}
	
	public void enemyTwo() {
		if(s.gameMode == 0) {
			switch(s.gameLevel) {
			case 1:
				switch(currentStage) {
				case 1:
					enemyName = "Goblin";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleGoblin.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackGoblin.gif");
					maxHP = 35;
					hp = maxHP;
					strength = 6;
					expValue = 6;
					coinValue = 7;
					ability1 = "Stun";
					ability2 = "Bleed";
					break;
				case 2:
					enemyName = "Skeleton";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSkelly.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSkelly.gif");
					maxHP = 45;
					hp = maxHP;
					strength = 8;
					expValue = 9;
					coinValue = 10;
					ability1 = "Harden";
					ability2 = "Buff";
					break;
				case 3:
					if(rand.nextInt(2) == 0) {
						enemyName = "Goblin";
						enemyIdle = s.setAnimation("res/EnemyAnimations/IdleGoblin.gif");
						enemyAttack = s.setAnimation("res/EnemyAnimations/AttackGoblin.gif");
						maxHP = 50;
						hp = maxHP;
						strength = 8;
						expValue = 10;
						coinValue = 12;
						ability1 = "Stun";
						ability2 = "Bleed";
					}
					else {
						enemyName = "Skeleton";
						enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSkelly.gif");
						enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSkelly.gif");
						maxHP = 55;
						hp = maxHP;
						strength = 9;
						expValue = 14;
						coinValue = 16;
						ability1 = "Harden";
						ability2 = "Buff";
					}
					break;
				}
				break;
			}		
		}
		else {
			switch(s.gameLevel) {
			case 1:
				if(rand.nextInt(2) == 0) {
					enemyName = "Goblin";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleGoblin.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackGoblin.gif");
					maxHP = 35;
					hp = maxHP;
					strength = 6;
					expValue = 6;
					coinValue = 7;
					ability1 = "Stun";
					ability2 = "Bleed";
				}
				else {
					enemyName = "Skeleton";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSkelly.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSkelly.gif");
					maxHP = 40;
					hp = maxHP;
					strength = 8;
					expValue = 9;
					coinValue = 10;
					ability1 = "Harden";
					ability2 = "Buff";
				}
				break;
			}			
		}
	}
	
	public void strongEnemy() {
		if(s.gameMode == 0) {
			switch(s.gameLevel) {
			case 1:
				if(currentStage == 1 || currentStage == 2 || currentStage == 3) {
					enemyName = "Fatlin";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleFatlin.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackFatlin.gif");
					maxHP = 50;
					hp = maxHP;
					strength = 8;
					expValue = 15;
					coinValue = 15;
					ability1 = "Stun";
					ability2 = "Harden";
				}
				break;
			}		
		}
		else {
			switch(s.gameLevel) {
			case 1:
				enemyName = "Fatlin";
				enemyIdle = s.setAnimation("res/EnemyAnimations/IdleFatlin.gif");
				enemyAttack = s.setAnimation("res/EnemyAnimations/AttackFatlin.gif");
				maxHP = 60;
				hp = maxHP;
				strength = 8;
				expValue = 15;
				coinValue = 15;
				ability1 = "Stun";
				ability2 = "Harden";
				break;
			}			
		}
	}
	
	public void boss() {
		if(s.gameMode == 0) {
			switch(s.gameLevel) {
			case 1:
				switch(currentStage) {
				case 1:
					enemyName = "Basic Sbeve";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSteve.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSteve.gif");
					maxHP = 80;
					hp = maxHP;
					strength = 10;
					expValue = 25;
					coinValue = 30;
					break;
				case 2:
					enemyName = "Stronger Sbeve";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSteve2.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSteve2.gif");
					maxHP = 110;
					hp = maxHP;
					strength = 12;
					expValue = 40;
					coinValue = 50;
					break;
				case 3:
					enemyName = "Angry Sbeve";
					enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSteve3.gif");
					enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSteve3.gif");
					maxHP = 150;
					hp = maxHP;
					strength = 15;
					expValue = 100;
					coinValue = 100;
					break;
				}
				break;
			}		
		}
		else {
			switch(s.gameLevel) {
			case 1:
				enemyName = "Stronger Sbeve";
				enemyIdle = s.setAnimation("res/EnemyAnimations/IdleSteve2.gif");
				enemyAttack = s.setAnimation("res/EnemyAnimations/AttackSteve2.gif");
				maxHP = 110;
				hp = maxHP;
				strength = 12;
				expValue = 40;
				coinValue = 50;
				break;
			}			
		}
	}
	
	public boolean enemyAttack() {
		int x = rand.nextInt(10);
		if(x != 0 || x != 1 || x != 2)
			return true;
		else
			return false;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int enemyHP) {
		this.hp = enemyHP;
	}


	public int getStrength() {
		return strength;
	}

	public void setStrength(int enemyStrength) {
		this.strength = enemyStrength;
	}

	public int getExpValue() {
		return expValue;
	}

	public void setExpValue(int expValue) {
		this.expValue = expValue;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int enemyMaxHP) {
		this.maxHP = enemyMaxHP;
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

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public String getAbility1() {
		return ability1;
	}

	public String getAbility2() {
		return ability2;
	}
	
}

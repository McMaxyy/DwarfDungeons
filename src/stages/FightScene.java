package stages;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Inventory.Storage;

import javax.swing.JLabel;

import main.GameWindow;
import constants.*;

@SuppressWarnings({"serial"})
public class FightScene extends JPanel implements ActionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapon weapon = new Weapon();
	private Storage s = Storage.getInstance();
	private JLabel lblPlayerHP, lblEnemyHP, lblDamageDealt, lblExp, lblLevelUp, lblEnemyName, lblReward;
	private JButton returnButton, attackButton, menuButton, turnButton, levelUpHPButton, levelUpStrButton, 
			ability1, ability2, ability3;
	private JButton[] buttons = new JButton[] {attackButton, menuButton, turnButton, ability1, ability2, ability3};
	private JButton[] actionButtons = new JButton[] {attackButton, ability1, ability2, ability3};
	String youLost = "You\nLost";
	String youWon = "You\nWon";
	private Random rand = new Random();
	private int enemySelectRand = rand.nextInt(2);
	private int currentLevel, currentStage, turnCounter, abilityID, enemiesDefeated, rendLeft, weakLeft, poisonLeft;
	private boolean playerWin, playerAttack, riposteActive, gameOver, enemyAttack, hardenActive,
			enemyDead, stunActive, bossActive;
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private Image pImg, wImg, pAttackImg, wAttackImg, eImg, eAttackImg, backgroundImg;
	private Timer t = new java.util.Timer();
	private final int SCALE = 250;
	private Image basicAtk, decapBtn, swingBtn, riposteBtn, rendBtn, hardenBtn, whirlwindBtn, 
			returnBtn, turnBtn;
	private JButton activeBag[] = new JButton[8];

	public FightScene(GameWindow window, int levelIndex, int selectedStage){
		this.window = window;
		turnCounter = player.getMana();
		currentLevel = levelIndex;
		currentStage = selectedStage;
		enemy.setCurrentStage(currentStage);	
		abilityID = 0;
		enemiesDefeated = 0;
		
		if(!enemy.isStrongEnemyActive() && !enemy.isBossActive()) {
			if (enemySelectRand == 0)
				enemy.enemyOne();
			else
				enemy.enemyTwo();				
		}
		else if(enemy.isStrongEnemyActive())
			enemy.strongEnemy();
		else {
			enemy.boss();
			bossActive = true;
		}
		
		if(currentLevel == 1 && currentStage == 1) {
			player.playerOne();
			if(player.getActiveWeapon() > 0)
				setWeapon();
			else
				weapon.noWeapon();
		}
		setLayout(null);
		
		loadImages();
		loadAnimations();
        initComponents();
        disableButtonFocus();
        
        for(JButton button : buttons)
        	button.setBorder(s.border);        	
	}

	// Button actions
	@Override
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
        case "Main menu":
            window.showHomeScreen();
            break;
        case "Return":
            if (playerWin) {
				if(currentLevel == 7) {
					player.setUnlockedStage(player.getUnlockedStage() + 1);
					currentLevel = 1;
				}
				else
					currentLevel++;
            }
            window.showLevelSelector(currentLevel);
            break;
        case "Attack":
            turnCounter -= s.getAttackButtonCost();            
            doAttack();
            break;
        case "Swing":
        	abilityID = s.overheadSwing.getID();
        	turnCounter -= s.overheadSwing.getAbilityCost();
        	doAttack();
        	break;
        case "Decapitate":
        	abilityID = s.decapitate.getID();
        	turnCounter -= s.decapitate.getAbilityCost();
        	doAttack();
        	break;
        case "Riposte":
        	if(rand.nextInt(3) != 0)
        		abilityID = s.riposte.getID();
        	else
        		riposteActive = true;
            enemyAttack();            
            turnCounter = player.getMana();
            enableActionButtons();
            playerAttack = true;
        	break;
        case "Rend":
        	rendLeft = 3;
        	turnCounter -= s.rend.getAbilityCost();
        	enableActionButtons();
        	playerAttack = true;
        	break;
        case "Harden":
            turnCounter -= s.harden.getAbilityCost();
            hardenActive = true;
            playerAttack = true;
            break;
        case "Whirlwind":
        	abilityID = s.whirlwind.getID();
            turnCounter -= s.whirlwind.getAbilityCost();
            doAttack();
            break;
        case "Weaken":
        	weakLeft = 2;
        	playerAttack = true;
        	turnCounter -= s.weaken.getAbilityCost();
        	enableActionButtons();
        	break;
        case "Stun":
        	if(rand.nextInt(2) == 0 ) {
	        	stunActive = true;
	        	playerAttack = true;
        	}
        	else
        		lblDamageDealt.setText("Stun failed");
        	turnCounter -= s.weaken.getAbilityCost();
        	enableActionButtons();
        	break;
        case "Turn":
            enemyAttack();       
            turnCounter = player.getMana();      
            break;
        case "HP up":
            player.increaseMaxHP();
            disableLevelUpComponents();
            player.playerShowHP(lblPlayerHP);
            lblPlayerHP.repaint(); 
            if(!gameOver) {
            	enableActionButtons();
            	turnButton.setEnabled(true);
            }          
            if(gameOver)
            	returnButton.setVisible(true);
            break;
        case "Str up":
            player.increasePlayerStr();
            disableLevelUpComponents();
            player.playerShowHP(lblPlayerHP);
            lblPlayerHP.repaint();
            if(!gameOver) {
            	enableActionButtons();
            	turnButton.setEnabled(true);
            }             
            if(gameOver)
            	returnButton.setVisible(true);
            break;
        case "inv1":
        	itemAction(0);
        	break;
        case "inv2":
        	itemAction(1);
        	break;
        case "inv3":
        	itemAction(2);
        	break;
        case "inv4":
        	itemAction(3);
        	break;
        case "inv5":
        	itemAction(4);
        	break;
        case "inv6":
        	itemAction(5);
        	break;
        case "inv7":
        	itemAction(6);
        	break;
        case "inv8":
        	itemAction(7);
        	break;
        }   
	}
	
	private void setWeapon() {
		switch(player.getActiveWeapon()) {
		case 1:
			weapon.weaponOne(s.ironAxe.getAttackPower());
			break;
		case 2:
			weapon.weaponOne(s.silverAxe.getAttackPower());
			break;
		case 3:
			weapon.weaponOne(s.goldAxe.getAttackPower());
			break;
		case 4:
			weapon.weaponOne(s.steelAxe.getAttackPower());
			break;
		case 5:
			weapon.weaponOne(s.copperAxe.getAttackPower());
			break;
		case 6:
			weapon.weaponOne(s.titaniumAxe.getAttackPower());
			break;
		case 7:
			weapon.weaponOne(s.fieryAxe.getAttackPower());
			break;
		case 8:
			weapon.weaponOne(s.moltenAxe.getAttackPower());
			break;
		case 9:
			weapon.weaponOne(s.waterAxe.getAttackPower());
			break;
		}
	}
	
	private void itemAction(int x) {
		switch (activeBag[x].getName()) {
	    case "Health":
        	activeBag[x].setIcon(null);
	        player.increaseHP(5);
	        player.playerShowHP(lblPlayerHP);
	        lblPlayerHP.repaint();	        
	        break;
	    case "Shield":
	    	activeBag[x].setIcon(null);
	        break;
	    case "Bomb":
	    	activeBag[x].setIcon(null);
	        enemy.enemyLoseHP(s.bomb.getItemPower());
	        enemy.enemyShowHP(lblEnemyHP);
	        lblEnemyHP.repaint();
	        isEnemyDead();
	        break;
	    case "PoisonDart":
	    	activeBag[x].setIcon(null);
	        poisonLeft = 3;
	        break;
	    case "Dynamite":
	    	activeBag[x].setIcon(null);
	        enemy.enemyLoseHP(s.bigBomb.getItemPower());
	        enemy.enemyShowHP(lblEnemyHP);
	        lblEnemyHP.repaint();
	        isEnemyDead();
	        break;
	    case "Bombs":
	    	activeBag[x].setIcon(null);
	        enemy.enemyLoseHP(s.biggerBomb.getItemPower());
	        enemy.enemyShowHP(lblEnemyHP);
	        lblEnemyHP.repaint();
	        isEnemyDead();
	        break;
	    case "IronAxe":
	    	player.setActiveWeapon(1);
	    	setWeapon();
			break;
	    case "SilverAxe":
	    	player.setActiveWeapon(2);
			setWeapon();
			break;
	    case "GoldAxe":
	    	player.setActiveWeapon(3);
			setWeapon();
			break;
	    case "SteelAxe":
	    	player.setActiveWeapon(4);
			setWeapon();
			break;
	    case "CopperAxe":
	    	player.setActiveWeapon(5);
			setWeapon();
			break;
	    case "TitaniumAxe":
	    	player.setActiveWeapon(6);
			setWeapon();
			break;
	    case "FieryAxe":
	    	player.setActiveWeapon(7);
			setWeapon();
			break;
	    case "MoltenAxe":
	    	player.setActiveWeapon(8);
			setWeapon();
			break;
	    case "WaterAxe":
	    	player.setActiveWeapon(9);
			setWeapon();
			break;
		}		
	}
	
	public void isPlayerDead() {
		if(player.getHp() <= 0) {
			returnButton.setText("<html>" + youLost.replaceAll("\\n", "<br>") + "</html>");
			returnButton.setVisible(true);
			currentLevel = 1;		// Player didn't succeed at clearing the stage
			playerWin = false;		// Will not unlock the next stage
			for(JButton button : buttons)
				button.setEnabled(false);
			lblDamageDealt.setVisible(false);
			gameOver = true;
			if(player.getActiveWeapon() > 0) {
				switch(player.getActiveWeapon()) {
				case 1:
					s.ironAxe.setAmount(s.ironAxe.getAmount() - 1);
					lblReward.setText("You lost " + s.ironAxe.getWeaponName());
					break;
				case 2:
					s.steelAxe.setAmount(s.steelAxe.getAmount() - 1);
					lblReward.setText("You lost " + s.steelAxe.getWeaponName());
					break;
				}
				player.setActiveWeapon(0);
				lblReward.setVisible(true);
			}
		}			
	}
	
	public void isEnemyDead() {
		if(enemy.getHp() <= 0) {
			enemyDead = true;
			rendLeft = 0;
			
			// Normal enemy rewards
			player.gainCoin(enemy.getCoinValue());
			lblReward.setText("You got " + enemy.getCoinValue() + " coins");
			if(rand.nextInt(10) == 0 && !bossActive) {				
				s.ironAxe.setAmount(s.ironAxe.getAmount() + 1);
				lblReward.setText("You got " + s.ironAxe.getWeaponName());
			}
			
			// Check if the player's defeated 3 enemies, if not, spawn a new one
			if(enemiesDefeated != 2 && currentLevel != 1 && 
				!enemy.isStrongEnemyActive() && !enemy.isBossActive()) {
				
				enemyDead = false;
				
				lblReward.setVisible(true);
				lblExp.setText("Gained " + enemy.getExpValue() + " exp");
				lblExp.setVisible(true);
				t.schedule( 
	    	        new TimerTask() {
	    	            @Override
	    	            public void run() {		    	            	
	    	    			lblExp.setVisible(false);
	    	    			lblReward.setVisible(false);
	    	            }}, 1000);
				
				// Select new enemy
				enemySelectRand = rand.nextInt(2);
				if (enemySelectRand == 0)
					enemy.enemyOne();
				else
					enemy.enemyTwo();
				
				// Refresh enemy HP bar & load animations for new enemy
				lblEnemyName.setText(enemy.getEnemyName());
				loadAnimations();
				enemy.enemyShowHP(lblEnemyHP);
				lblEnemyHP.repaint();	
				if (player.getLevel() <= 15) {	// Check if player is max level
					player.increaseExp(enemy.getExpValue());	// Add EXP to player
					if(player.getExp() >= player.getLevelCap()) {	// Check if player has enough EXP to level up
						player.levelUp();
						enableLevelUpComponents();
					}
				}	
				enemiesDefeated++;
				
				// Refresh turn counter
				turnCounter = player.getMana();							
				repaint();
			}
			// Player has defeated 3 enemies, stage cleared
			else {
				turnCounter = 0;
				lblExp.setText("Gained " + enemy.getExpValue() + " exp");
				lblExp.setVisible(true);
				
				// Boss rewards
				if(bossActive)
					lblReward.setText("You got " + enemy.getCoinValue() + " coins");									
				if(bossActive && rand.nextInt(2) == 0) {
					s.ironAxe.setAmount(1);
					lblReward.setText("You got " + s.ironAxe.getWeaponName());
				}
				
				lblReward.setVisible(true);
				returnButton.setText("<html>" + youWon.replaceAll("\\n", "<br>") + "</html>");
				returnButton.setVisible(true);
				playerWin = true;		// Will unlock the next stage
				for(JButton button : buttons)
					button.setEnabled(false);	
				lblDamageDealt.setVisible(false);
				
				
				if (player.getLevel() <= 15) {
					player.increaseExp(enemy.getExpValue()); // Add EXP to player
					if(player.getExp() >= player.getLevelCap()) {	// Check if player has enough EXP to level up
						player.levelUp();
						enableLevelUpComponents();
					}
				}	
				gameOver = true;
				disableActionButtons();
			}			
		}
	}
	
	private void disableButtonFocus() {		
		for(JButton button : buttons)
			button.setFocusable(false);			
	}
	
	private void enableLevelUpComponents() {
		levelUpHPButton.setVisible(true);
		levelUpStrButton.setVisible(true);
		returnButton.setVisible(false);
		lblDamageDealt.setEnabled(false);
		turnButton.setEnabled(false);
		disableActionButtons();
	}
	
	private void disableLevelUpComponents() {
		levelUpHPButton.setVisible(false);
		levelUpStrButton.setVisible(false);
		lblDamageDealt.setEnabled(true);
		turnButton.setEnabled(true);
	}
	
	private void disableActionButtons() {
		for(JButton button : actionButtons) {
			button.setEnabled(false);
			button.setVisible(false);			
		}
	}
	
	private void enableActionButtons() {		
		if(enemiesDefeated != 3)
			for(JButton button : actionButtons)
				button.setEnabled(true);
		
		if(turnCounter < s.getAttackButtonCost())
			attackButton.setEnabled(false);
		
		switch(ability1.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < s.overheadSwing.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= s.decapitate.getAbilityCost() && enemy.getHp() <= (enemy.getMaxHP() / 3))
	            ability1.setEnabled(true);
	        else 
	            ability1.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter == s.riposte.getAbilityCost())
	    		ability1.setEnabled(true);
	    	else
	    		ability1.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < s.rend.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < s.harden.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < s.whirlwind.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Weaken":
	    	if(turnCounter < s.weaken.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Stun":
	    	if(turnCounter < s.stun.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
		}
		
		switch(ability2.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < s.overheadSwing.getAbilityCost()) {
	        	ability2.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= s.decapitate.getAbilityCost() && enemy.getHp() <= (enemy.getMaxHP() / 3))
	        	ability2.setEnabled(true);
	        else 
	        	ability2.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter == s.riposte.getAbilityCost())
	    		ability2.setEnabled(true);
	    	else
	    		ability2.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < s.rend.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < s.harden.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < s.whirlwind.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Weaken":
	    	if(turnCounter < s.weaken.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Stun":
	    	if(turnCounter < s.stun.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
		}
		
		switch(ability3.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < s.overheadSwing.getAbilityCost()) {
	        	ability3.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= s.decapitate.getAbilityCost() && enemy.getHp() <= (enemy.getMaxHP() / 3))
	        	ability3.setEnabled(true);
	        else 
	        	ability3.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter == s.riposte.getAbilityCost())
	    		ability3.setEnabled(true);
	    	else
	    		ability3.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < s.rend.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < s.harden.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < s.whirlwind.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Weaken":
	    	if(turnCounter < s.weaken.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Stun":
	    	if(turnCounter < s.stun.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
		}
		
		for(JButton button : actionButtons) {
			button.setVisible(true);
		}
	}	
	
	private void enemyAttack() {		
		if(rendLeft != 0) {
			int temp = s.rend.getAttackPower() + player.getStrength() / 2;
			enemy.enemyLoseHP(temp);
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			lblDamageDealt.setText("Enemy got hit by Rend for: " + temp + " damage");
			lblDamageDealt.repaint();
			isEnemyDead();
			rendLeft--;			
		}
		// Since Rend activates before the enemy attack, check if Rend killed the enemy
		if(!enemyDead && !stunActive) {
			t.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	int temp = rand.nextInt(1, enemy.getStrength() + 1);
	            	enemyAttack = true;
	            	// Check if enemy attack missed
	    			if(abilityID != 3 && enemy.enemyAttack()) {
	    				if(hardenActive) {
	    					temp = temp / 2;
	    					if(weakLeft > 0) {						
	    						temp = temp / s.weaken.getAttackPower();
	    						if(temp == 0)
	    							player.playerLoseHP(1);
	    						else
	    							player.playerLoseHP(temp);
	    						weakLeft--;
	    					}
	    					else
	    						player.playerLoseHP(temp);	// Harden is active, enemy deals 50% dmg
	    				}
	    				else if(weakLeft > 0) {
	    					temp = temp / s.weaken.getAttackPower();
	    					if(temp == 0)
    							player.playerLoseHP(1);
    						else
    							player.playerLoseHP(temp);
	    					weakLeft--;
	    				}
	    				else
	    					player.playerLoseHP(temp);
	    		    	player.playerShowHP(lblPlayerHP);
	    		    	lblPlayerHP.repaint();
	    		    	if(riposteActive)
	    		    		lblDamageDealt.setText("Riposte failed and the enemy dealt: " + temp  + " damage");
	    		    	else
	    		    		lblDamageDealt.setText("Enemy dealt: " + temp  + " damage");   		    		    		    					    		    	
	    				lblDamageDealt.repaint();
	    		    	isPlayerDead();
	    		    	riposteActive = false;	    		    	
	    	    	}
	    			// Check if Riposte was successful
	    			else if (abilityID == 3){
	    				int x = enemy.getStrength() / 2 + s.riposte.getAttackPower();			
	    				enemy.enemyLoseHP(x);
	    				enemy.enemyShowHP(lblEnemyHP);
	    				lblEnemyHP.repaint();
	    				lblDamageDealt.setText("You reflected the attack and hit them for " + x + " damage");
	    				lblDamageDealt.repaint();			
	    				isEnemyDead();
	    			}
	    			else {
	    				lblDamageDealt.setText("Enemy attack missed");
	    				lblDamageDealt.repaint();
	    			}
	    			abilityID = 0;
	    			hardenActive = false;
	    			enemyDead = false;	    			
	            }
	        }, 350);	// Timer value in milliseconds        									
		}
		if(stunActive) {
			t.schedule(new TimerTask() {
	            @Override
	            public void run() {		    	            	
	    			lblDamageDealt.setText("Enemy stunned and cannot attack");
	    			enableActionButtons();
	            }}, 350);
		}
		stunActive = false;
	}
	
	private void doAttack() {
        playerAttack();
        playerAttack = true;
	}
	
	private void playerAttack(){
		// Check if player attack missed
		if(player.playerAttack()) {
			int x, y = 0;
			
			// Check if ability was used
			if(abilityID != 0) {
				switch(abilityID) {
				case 1:
					y = s.overheadSwing.getAttackPower();
					break;
				case 2:
					y = s.decapitate.getAttackPower();
					break;
				case 6:
					y = s.whirlwind.getAttackPower();
				}
				x = rand.nextInt(y, player.getStrength() / 2 + weapon.getWeaponDamage()+ y + player.getTempStr() / 2 + 1); // Ability attack
				if(abilityID == 6)	// Whirlwind
					for(int i = 1; i < 3; i++)
						x = x + (player.getStrength() / 2 + weapon.getWeaponDamage()+ y + player.getTempStr() / 2 + 1);	
				abilityID = 0;
			}
			else
				x = rand.nextInt(player.getStrength(), player.getStrength() + weapon.getWeaponDamage() + player.getTempStr() + 1);	// Basic attack
			
			// Damage enemy & update label
			enemy.enemyLoseHP(x);
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			lblDamageDealt.setText("You dealt: " + x + " damage");
			lblDamageDealt.repaint();
		}
		else {
			lblDamageDealt.setText("Your attack missed");
			lblDamageDealt.repaint();
		}			
		if(turnCounter <= 0)
			enableActionButtons();	// Maybe should be disable
		isEnemyDead();	// Check if the player's attack killed the enemy
	}
	
	private void configureAbilityButton(JButton button, int abilityID, int x, int y) {
	    switch(abilityID) {
	        case 1:
	            button.setActionCommand("Swing");
	            button.setIcon(new ImageIcon(swingBtn));
	            break;
	        case 2:
	            button.setActionCommand("Decapitate");
	            button.setIcon(new ImageIcon(decapBtn));
	            button.setEnabled(false);
	            break;
	        case 3:
	            button.setActionCommand("Riposte");
	            button.setIcon(new ImageIcon(riposteBtn));
	            button.setEnabled(false);
	            break;
	        case 4:
	            button.setActionCommand("Rend");
	            button.setIcon(new ImageIcon(rendBtn));
	            break;
	        case 5:
	            button.setActionCommand("Harden");
	            button.setIcon(new ImageIcon(hardenBtn));
	            break;
	        case 6:
	            button.setActionCommand("Whirlwind");
	            button.setIcon(new ImageIcon(whirlwindBtn));
	            break;
	        case 7:
	            button.setActionCommand("Weaken");
	            button.setText("Weaken");
	            break;
	        case 8:
	            button.setActionCommand("Stun");
	            button.setText("Stun");
	            break;
	    }
	    button.addActionListener(this);
	    button.setBounds(x, y, 150, 100);
	    add(button);
	}


	private void initComponents() {	
		
		// Useful labels		
		lblPlayerHP = new JLabel();
		lblPlayerHP.setFont(s.font2);
		player.playerShowHP(lblPlayerHP);
		lblPlayerHP.setForeground(Color.BLACK);
		lblPlayerHP.setBounds(1010, 270, 100, 30);
		add(lblPlayerHP);
		
		lblEnemyHP = new JLabel();
		lblEnemyHP.setFont(s.font2);
		enemy.enemyShowHP(lblEnemyHP);
		lblEnemyHP.setForeground(Color.BLACK);
		lblEnemyHP.setBounds(50, 270, 100, 30);		
		add(lblEnemyHP);
		
		lblEnemyName = new JLabel();
		lblEnemyName.setFont(s.font2);
		lblEnemyName.setText(enemy.getEnemyName());
		lblEnemyName.setForeground(Color.WHITE);
		lblEnemyName.setBounds(40, 240, 200, 30);
		add(lblEnemyName);
		
		lblExp = new JLabel();
		lblExp.setFont(s.font2);
		lblExp.setForeground(Color.WHITE);
		lblExp.setHorizontalAlignment(SwingConstants.CENTER);
		lblExp.setVerticalAlignment(SwingConstants.CENTER);
		lblExp.setBounds(515, 420, 250, 50);
		lblExp.setVisible(false);
		add(lblExp);
		
		lblReward = new JLabel();
		lblReward.setFont(s.font2);
		lblReward.setForeground(Color.WHITE);
		lblReward.setHorizontalAlignment(SwingConstants.CENTER);
		lblReward.setVerticalAlignment(SwingConstants.CENTER);
		lblReward.setBounds(515, 390, 250, 50);
		lblReward.setVisible(false);
		add(lblReward);
		
		lblLevelUp = new JLabel();
		lblLevelUp.setFont(s.font2);
		lblLevelUp.setBounds(200, 300, 100, 100);
		lblLevelUp.setBorder(blackline);
		lblLevelUp.setBackground(Color.gray);
		lblLevelUp.setOpaque(true);
		lblLevelUp.setVisible(false);
		add(lblLevelUp);
		
		// Damage dealt label
		lblDamageDealt = new JLabel();
		lblDamageDealt.setFont(s.font3);
		lblDamageDealt.setBounds(400, 180, 450, 30);
		lblDamageDealt.setBorder(blackline);
		lblDamageDealt.setBackground(Color.WHITE);
		lblDamageDealt.setOpaque(true);
		lblDamageDealt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDamageDealt.setVerticalAlignment(SwingConstants.CENTER);
		add(lblDamageDealt);
		
		// Navigation button							
        menuButton = new JButton();
        menuButton.setActionCommand("Main menu");
        menuButton.setIcon(new ImageIcon(returnBtn));
        menuButton.addActionListener(this);
        menuButton.setOpaque(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setBounds(1100, 25, 100, 50);
        add(menuButton);    
        
        // Button for returning to the level selector when the player either wins or dies
        returnButton = new JButton();
        returnButton.setFont(s.font);
        returnButton.setFocusable(false);
        returnButton.setBackground(Color.WHITE);
        returnButton.setOpaque(true);
        returnButton.setActionCommand("Return");
        returnButton.addActionListener(this);
        returnButton.setBounds(540, 300, 200, 100);
        returnButton.setVisible(false);
        add(returnButton);
        
        // Action buttons
        attackButton = new JButton();
        attackButton.setActionCommand("Attack");
        attackButton.setIcon(new ImageIcon(basicAtk));
        attackButton.addActionListener(this);
        attackButton.setBounds(790, 550, 150, 100);
        add(attackButton);
        
        ability1 = new JButton();
        configureAbilityButton(ability1, player.getAbility1ID(), 250, 550);
                
        ability2 = new JButton();
        configureAbilityButton(ability2, player.getAbility2ID(), 430, 550);
                
        ability3 = new JButton();
        configureAbilityButton(ability3, player.getAbility3ID(), 610, 550);       
        
        turnButton = new JButton();
        turnButton.setActionCommand("Turn");
        turnButton.setIcon(new ImageIcon(turnBtn));
        turnButton.addActionListener(this);
        turnButton.setBounds(950, 600, 100, 50);
        add(turnButton);
        
        // Inventory
        for(int i = 0; i < activeBag.length; i++) {
        	activeBag[i] = Player.activeBag[i];
        	activeBag[i].addActionListener(this);
        	add(activeBag[i]);
        }
        
        // Stat level up buttons
        levelUpHPButton = new JButton();
        levelUpHPButton.setVisible(false);
        levelUpHPButton.setActionCommand("HP up");
        levelUpHPButton.setText("HP+");
        levelUpHPButton.setHorizontalAlignment(SwingConstants.CENTER);
        levelUpHPButton.setVerticalAlignment(SwingConstants.CENTER);
        levelUpHPButton.addActionListener(this);
        levelUpHPButton.setBounds(230, 310, 80, 30);
        add(levelUpHPButton);
        
        levelUpStrButton = new JButton();
        levelUpStrButton.setVisible(false);
        levelUpStrButton.setActionCommand("Str up");
        levelUpStrButton.setText("STR+");
        levelUpStrButton.setHorizontalAlignment(SwingConstants.CENTER);
        levelUpStrButton.setVerticalAlignment(SwingConstants.CENTER);
        levelUpStrButton.addActionListener(this);
        levelUpStrButton.setBounds(230, 350, 80, 30);
        add(levelUpStrButton);
        
        // Add buttons to arrays
        buttons[0] = attackButton;
        buttons[1] = menuButton;
        buttons[2] = turnButton;
        buttons[3] = ability1;
        buttons[4] = ability2;
        buttons[5] = ability3;
        
        actionButtons[0] = attackButton;
        actionButtons[1] = ability1;
        actionButtons[2] = ability2;
        actionButtons[3] = ability3;
	}
	
	private void loadAnimations() {
		
		/* Since the loadAnimations method will be used for new enemies, we want to skip
		 * the player animations being created every time it's called
		 */
		if(pImg == null && wImg == null) {
			// Idle player animations
			pImg = Toolkit.getDefaultToolkit().createImage("res/PlayerAnimations/IdleDwarf.gif");
			wImg = Toolkit.getDefaultToolkit().createImage("res/PlayerAnimations/IronAxe.png");
			// Attack animations
			pAttackImg = Toolkit.getDefaultToolkit().createImage("res/PlayerAnimations/DwarfAttack.gif");
			wAttackImg = Toolkit.getDefaultToolkit().createImage("res/PlayerAnimations/AxeAttack.gif");
		}
		
		eImg = enemy.getEnemyIdle();
		eAttackImg = enemy.getEnemyAttack();
	}
	
	private void loadImages() {
		try {
		    basicAtk = ImageIO.read(new File("res/Buttons/AttackButton.png"));
		    swingBtn = ImageIO.read(new File("res/Buttons/SwingButton.png"));
		    decapBtn = ImageIO.read(new File("res/Buttons/DecapButton.png"));
		    riposteBtn = ImageIO.read(new File("res/Buttons/RiposteButton.png"));
		    rendBtn = ImageIO.read(new File("res/Buttons/RendButton.png"));
		    hardenBtn = ImageIO.read(new File("res/Buttons/HardenButton.png"));
		    whirlwindBtn = ImageIO.read(new File("res/Buttons/WhirlwindButton.png"));
		    backgroundImg = ImageIO.read(new File("res/Backgrounds/Fight_BG.png"));
		    returnBtn = ImageIO.read(new File("res/FunctionButtons/HomeButton.png"));
		    turnBtn = ImageIO.read(new File("res/FunctionButtons/TurnButton.png"));
		} catch (Exception ex) {
			  System.out.println(ex);
		  }
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		float hpProc, hpProc2;
		int bubbleLoc, width, width2;
	    
	    // Draw background
	    g.drawImage(backgroundImg, 0, 0, null);
	    
	    // Draw player animations
	    if (pImg != null && wImg != null && !playerAttack) {
	    	g.drawImage(wImg, 980, 308, SCALE, SCALE, this);
	    	g.drawImage(pImg, 980, 308, SCALE, SCALE, this);
	    }
	    if (pAttackImg != null && wAttackImg != null && playerAttack) {
	    	g.drawImage(pAttackImg, 980, 308, SCALE, SCALE, this);
	    	g.drawImage(wAttackImg, 980, 308, SCALE, SCALE, this);
	    	enableActionButtons();
	    	for(JButton button : actionButtons) {
				button.setVisible(false);			
			}
	    	
	    	// Wait until attack animation is over to re-enable action buttons
	    	t.schedule( 
    	        new TimerTask() {
    	            @Override
    	            public void run() {
    	            	playerAttack = false;
    	            	if(turnCounter > 0 && !levelUpHPButton.isVisible()) {
    	            		for(JButton button : actionButtons) {
    	            			button.setVisible(true);			
    	            		}
    	            	}
    	            }
    	        }, 500);	// Timer value in milliseconds 	    	
	    }
	    
	    // Draw enemy animations
	    if (eImg != null && !enemyAttack) {
	    	g.drawImage(eImg, 10, 308, SCALE, SCALE, this);
	    }
	    if (eAttackImg != null && enemyAttack) {
	    	g.drawImage(eAttackImg, 10, 308, SCALE, SCALE, this);
	    	enableActionButtons();
	    	turnButton.setEnabled(false);
	    	
	    	t.schedule( 
    	        new TimerTask() {
    	            @Override
    	            public void run() {
    	            	enemyAttack = false;
    	            	if(!gameOver) {
    	            		turnButton.setEnabled(true);
    	            		enableActionButtons();
    	            	}
    	            }
    	        }, 500);	    		    	
	    	}
	    
	    //Enemy HP bar
	    g.setColor(Color.WHITE);
	    g.fillRect(40, 270, 200, 30);
	    hpProc = (float)enemy.getHp() / (float)enemy.getMaxHP();
	    // Calculate the width of the rectangle based on hpProc
	    width = (int)(hpProc * 200);
		// Round the width down to the nearest 10
		width = (width / 10) * 10;	
		if (width < 10) {
		     width = 0;
		}	
		g.setColor(Color.RED);
		g.fillRect(40, 270, width, 30);
	    
		//Player HP bar
	    g.setColor(Color.WHITE);
	    g.fillRect(1000, 270, 200, 30);
	    hpProc2 = (float)player.getHp() / (float)player.getMaxHP();
	    width2 = (int)(hpProc2 * 200);
		width2 = (width2 / 10) * 10;	
		if (width2 < 10) {
		     width2 = 0;
		}	
		g.setColor(Color.RED);
		g.fillRect(1000, 270, width2, 30);
	    
	    // Mana bubbles
	    bubbleLoc = 1000;
	    for(int i = 1; i <= player.getMana(); i++) {
	    	g.setColor(Color.BLACK);
		    g.drawOval(bubbleLoc, 240, 20, 20);
		    bubbleLoc += 30;
	    }
	    bubbleLoc = 1000;
	    for(int i = 1; i <= turnCounter; i++) {
	    	g.setColor(new Color(0, 44, 220));
		    g.fillOval(bubbleLoc, 240, 20, 20);
		    bubbleLoc += 30;
	    }
	}	
}

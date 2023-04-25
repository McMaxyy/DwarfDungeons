package stages;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
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
public class FightScene extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapon weapon = new Weapon();
	private Storage s = Storage.getInstance();
	private JLabel lblPlayerHP, lblEnemyHP, lblDamageDealt, lblExp, lblLevelUp, lblEnemyName, lblReward;
	private JButton returnButton, attackButton, menuButton, turnButton, levelUpHPButton, levelUpStrButton, 
			ability1, ability2, ability3, yesAdd, noAdd;
	private JButton[] buttons = new JButton[] {attackButton, menuButton, turnButton, ability1, ability2, ability3};
	private JButton[] actionButtons = new JButton[] {attackButton, ability1, ability2, ability3};
	String youLost = "You\nLost";
	String youWon = "You\nWon";
	private Random rand = new Random();
	private int enemySelectRand = rand.nextInt(2), weaponWon;
	private int currentLevel, currentStage, turnCounter, abilityID, enemiesDefeated, rendLeft, 
			weakLeft, poisonLeft;
	private boolean playerWin, playerAttack, riposteActive, gameOver, enemyAttack, hardenActive,
			enemyDead, stunActive, bossActive, shieldActive, explosionActive, enemyConfused;
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private Image pImg, wImg, pAttackImg, wAttackImg, eImg, eAttackImg, backgroundImg;
	private Timer t = new java.util.Timer();
	private final int SCALE = 250;
	private Image basicAtk, decapBtn, swingBtn, riposteBtn, rendBtn, hardenBtn, whirlwindBtn, 
			returnBtn, turnBtn, stunBtn, weakenBtn, bubbleBtn, healBtn, blockBtn,
			poisonBtn, explosiveBtn, confuseBtn, pummelBtn, tendonBtn, stealBtn, breakerBtn,
			shieldBtn, fortifyBtn;
	private Image swingCard, rendCard, hardenCard, stunCard, riposteCard, whirlwindCard,
			decapCard, weakenCard, attackCard, bubbleCard, healCard, blockCard, poisonCard,
			explosiveCard, confuseCard, pummelCard, tendonCard, stealCard, breakerCard, shieldCard, fortifyCard;
	private JButton activeBag[] = new JButton[8];
	private boolean isHovering, hoverAttack, hoverSwing, hoverRend, hoverHarden, hoverRiposte, hoverWhirlwind,
			hoverDecap, hoverWeaken, hoverBubble, hoverHeal, hoverBlock, hoverPoison, hoverExplosive, hoverConfuse,
			hoverPummel, hoverTendon, hoverSteal, hoverBreaker, hoverShield, hoverFortify, hoverStun;

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
        	turnCounter -= s.riposte.getAbilityCost();
        	if(turnCounter <= 0)
    			disableActionButtons();
        	playerAttack = true;
        	break;
        case "Rend":
        	lblDamageDealt.setText("Bleed applied to enemy");
        	rendLeft = 3;
        	turnCounter -= s.rend.getAbilityCost();
        	enableActionButtons();
        	playerAttack = true;
        	break;
        case "Harden":
        	lblDamageDealt.setText("Harden activated");
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
        	lblDamageDealt.setText("Enemy weakened");
        	turnCounter -= s.weaken.getAbilityCost();
        	enableActionButtons();
        	break;
        case "Stun":
        	if(rand.nextInt(2) == 0 ) {
	        	stunActive = true;
	        	lblDamageDealt.setText("Stun succeeded");
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
        case "Yes":
        	for(int i = 0; i < activeBag.length; i++) {
        		activeBag[i].setActionCommand("remove" + i);
        		activeBag[i].addActionListener(this);
        	}
        	lblReward.setText("Choose an inventory slot"); 
        	noAdd.setVisible(false);
        	yesAdd.setVisible(false);	
        	break;
        case "No":
        	lblReward.setVisible(false);   
        	noAdd.setVisible(false);
        	yesAdd.setVisible(false);
        }
        if (command.startsWith("remove")) {
			int index = Integer.parseInt(command.substring(6));
			switch(weaponWon) {
			case 1:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/IronAxe.png"));
				activeBag[index].setName("IronAxe");
				break;
			case 2:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/SilverAxe.png"));
				activeBag[index].setName("SilverAxe");
				break;
			case 3:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/GoldAxe.png"));
				activeBag[index].setName("GoldAxe");
				break;
			case 4:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/SteelAxe.png"));
				activeBag[index].setName("SteelAxe");
				break;
			case 5:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/CopperAxe.png"));
				activeBag[index].setName("CopperAxe");
				break;
			case 6:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/TitaniumAxe.png"));
				activeBag[index].setName("TitaniumAxe");
				break;
			case 7:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/FieryAxe.png"));
				activeBag[index].setName("FieryAxe");
				break;
			case 8:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/MoltenAxe.png"));
				activeBag[index].setName("MoltenAxe");
				break;
			case 9:
				activeBag[index].setIcon(new ImageIcon("res/Weapons/WaterAxe.png"));
				activeBag[index].setName("WaterAxe");
				break;
			}
			
			for(int i = 0; i < activeBag.length; i++) {
				activeBag[i].setActionCommand("inv" + i);
				activeBag[i].addActionListener(this);
			}
			lblReward.setVisible(false);					
        }
        if(command.startsWith("inv")) {
        	int index = Integer.parseInt(command.substring(3));
        	if(activeBag[index].getIcon() != null)
        		itemAction(index);
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
	    	shieldActive = true;
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
			if(rand.nextInt(5) == 0 && !bossActive) {								
				lblReward.setText("You got " + s.ironAxe.getWeaponName() + ". Add it to inv?");
				weaponWon = 1;
				yesAdd.setVisible(true);
				noAdd.setVisible(true);
			}
			
			// Check if the player's defeated 3 enemies, if not, spawn a new one
			if(enemiesDefeated != 2 && currentLevel != 2 && 
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
//	    	    			lblReward.setVisible(false);
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
					lblReward.setText("You got " + s.silverAxe.getWeaponName() + ". Add it to inv?");
					weaponWon = 2;
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
		
		for (JButton button : Arrays.asList(ability1, ability2, ability3)) {
		    switch(button.getActionCommand()) {
		        case "Swing":
		            button.setEnabled(turnCounter >= s.overheadSwing.getAbilityCost());
		            break;
		        case "Decapitate":
		            button.setEnabled(turnCounter >= s.decapitate.getAbilityCost() && enemy.getHp() <= (enemy.getMaxHP() / 3));
		            break;
		        case "Riposte":
		            button.setEnabled(turnCounter == s.riposte.getAbilityCost());
		            break;
		        case "Rend":
		        	button.setEnabled(turnCounter >= s.rend.getAbilityCost());
		        	break;
		        case "Harden":
		        	button.setEnabled(turnCounter >= s.harden.getAbilityCost());
		        	break;
		        case "Whirlwind":
		        	button.setEnabled(turnCounter >= s.whirlwind.getAbilityCost());
		        	break;
		        case "Weaken":
		        	button.setEnabled(turnCounter >= s.weaken.getAbilityCost());
		        	break;
		        case "Stun":
		        	button.setEnabled(turnCounter >= s.stun.getAbilityCost());
		        	break;
		            
		    }
		}
		
		for(JButton button : actionButtons)
			button.setVisible(true);
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
		if(poisonLeft != 0) {
			int temp = s.poisonDart.getItemPower();
			enemy.enemyLoseHP(temp);
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			lblDamageDealt.setText("Enemy got hit by Poison dart for: " + temp + " damage");
			lblDamageDealt.repaint();
			isEnemyDead();
			poisonLeft--;			
		}
		// Since Rend activates before the enemy attack, check if Rend killed the enemy
		if(!enemyDead && !stunActive) {
			t.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	int temp = rand.nextInt(1, enemy.getStrength() + 1);
	            	enemyAttack = true;
	            	// Check if enemy attack missed
	    			if(abilityID != 3 && enemy.enemyAttack() && !enemyConfused) {
	    				if(hardenActive) {
	    					temp = temp / 2;
	    					if(weakLeft > 0) {						
	    						temp = temp / 4;
	    						if(temp == 0 && player.getBubble() > 0) {
	    							player.setBubble(player.getBubble() - 1);
	    							if(player.getBubble() < 0)
	    								player.playerLoseHP(player.getBubble() * player.getBubble());
	    						}
	    						else if (player.getBubble() > 0) {
		    						player.setBubble(player.getBubble() - temp);
		    						temp = player.getBubble() * player.getBubble();
		    						if(player.getBubble() < 0)
		    							player.playerLoseHP(temp);
		    					}
	    						else if(temp == 0)
	    							player.playerLoseHP(1);
	    						else
	    							player.playerLoseHP(temp);
	    						weakLeft--;
	    					}
	    					else if (player.getBubble() > 0) {
	    						player.setBubble(player.getBubble() - temp);
	    						temp = player.getBubble() * player.getBubble();
	    						if(player.getBubble() < 0)
	    							player.playerLoseHP(temp);
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
	    				else if(player.getBubble() > 0)
	    					player.setBubble(player.getBubble() - temp);
	    				else if(!shieldActive)
	    					player.playerLoseHP(temp);
	    				
	    		    	player.playerShowHP(lblPlayerHP);
	    		    	lblPlayerHP.repaint();
	    		    	if (temp == 0)
	    		    		temp = 1;
	    		    	if(riposteActive)
	    		    		lblDamageDealt.setText("Riposte failed and the enemy dealt: " + temp  + " damage");
	    		    	else if (!shieldActive)
	    		    		lblDamageDealt.setText("Enemy dealt: " + temp  + " damage"); 
	    		    	else
	    		    		lblDamageDealt.setText("Enemy attack blocked"); 
	    				lblDamageDealt.repaint();
	    		    	isPlayerDead();
	    		    	riposteActive = false;	
	    		    	shieldActive = false;
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
	    			else if(enemyConfused) {
	    				lblDamageDealt.setText("Enemy confused and failed to attack");
	    				lblDamageDealt.repaint();
	    				enemyConfused = false;
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
			int x, y = 0, z = 0;
			
			// Check if ability was used
			if(abilityID != 0) {
				switch(abilityID) {
				case 1:
					y = s.overheadSwing.getAttackPower();
					z = y;
					break;
				case 2:
					y = s.decapitate.getAttackPower();
					z = 1;
					break;
				case 6:
					y = s.whirlwind.getAttackPower();					
					z = y;
					break;
				}
				x = rand.nextInt(z, player.getStrength() / 2 + weapon.getWeaponDamage()+ y + player.getTempStr() / 2 + 1); // Ability attack
				if(abilityID == 6)	// Whirlwind
					for(int i = 1; i < 3; i++)
						x = x + (player.getStrength() / 2 + weapon.getWeaponDamage()+ y + player.getTempStr() / 2 + 1);					
				abilityID = 0;
			}
			else
				x = rand.nextInt(player.getStrength(), player.getStrength() + weapon.getWeaponDamage() + player.getTempStr() + 1);	// Basic attack
			
			if(explosionActive) // If Explosive attack procced, add bomb damage to attack
				x = x + s.bomb.getItemPower();
			
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
	            button.setIcon(new ImageIcon(weakenBtn));
	            break;
	        case 8:
	            button.setActionCommand("Stun");
	            button.setIcon(new ImageIcon(stunBtn));
	            break;
	    }
	    button.addActionListener(this);
	    button.addMouseListener(this);
	    button.addMouseMotionListener(this);
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
		lblExp.setBounds(515, 370, 250, 50);
		lblExp.setVisible(false);
		add(lblExp);
		
		lblReward = new JLabel();
		lblReward.setFont(s.font2);
		lblReward.setForeground(Color.WHITE);
		lblReward.setHorizontalAlignment(SwingConstants.CENTER);
		lblReward.setVerticalAlignment(SwingConstants.CENTER);
		lblReward.setBounds(385, 390, 500, 50);
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
		lblDamageDealt.setBounds(400, 80, 450, 30);
		lblDamageDealt.setBorder(blackline);
		lblDamageDealt.setBackground(Color.WHITE);
		lblDamageDealt.setOpaque(true);
		lblDamageDealt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDamageDealt.setVerticalAlignment(SwingConstants.CENTER);
		add(lblDamageDealt);
		
		// Yes/no buttons for receiving reward
		yesAdd = new JButton();
		yesAdd.setFocusable(false);
		yesAdd.setVisible(false);
		yesAdd.setBackground(Color.GRAY);
		yesAdd.setForeground(Color.WHITE);
		yesAdd.setOpaque(true);
		yesAdd.setText("Yes");
		yesAdd.setActionCommand("Yes");
		yesAdd.addActionListener(this);
		yesAdd.setFont(s.font);
		yesAdd.setHorizontalAlignment(SwingConstants.CENTER);
		yesAdd.setVerticalAlignment(SwingConstants.CENTER);
		yesAdd.setBorderPainted(false);
		yesAdd.setBounds(520, 438, 100, 50);
        add(yesAdd);
        
        noAdd = new JButton();
        noAdd.setFocusable(false);
        noAdd.setVisible(false);
        noAdd.setBackground(Color.GRAY);
        noAdd.setForeground(Color.WHITE);
        noAdd.setOpaque(true);
        noAdd.setText("No");
        noAdd.setActionCommand("No");
        noAdd.addActionListener(this);
        noAdd.setFont(s.font);
        noAdd.setHorizontalAlignment(SwingConstants.CENTER);
        noAdd.setVerticalAlignment(SwingConstants.CENTER);
        noAdd.setBorderPainted(false);
        noAdd.setBounds(656, 438, 100, 50);
        add(noAdd);
		
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
        attackButton.addMouseListener(this);
        attackButton.addMouseMotionListener(this);
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
			// Button images
		    basicAtk = ImageIO.read(new File("res/AbilityButtons/AttackButton.png"));
		    swingBtn = ImageIO.read(new File("res/AbilityButtons/SwingButton.png"));
		    decapBtn = ImageIO.read(new File("res/AbilityButtons/DecapButton.png"));
		    riposteBtn = ImageIO.read(new File("res/AbilityButtons/RiposteButton.png"));
		    rendBtn = ImageIO.read(new File("res/AbilityButtons/RendButton.png"));
		    hardenBtn = ImageIO.read(new File("res/AbilityButtons/HardenButton.png"));
		    whirlwindBtn = ImageIO.read(new File("res/AbilityButtons/WhirlwindButton.png"));
		    weakenBtn = ImageIO.read(new File("res/AbilityButtons/WeakenButton.png"));
		    stunBtn = ImageIO.read(new File("res/AbilityButtons/StunButton.png"));
		    bubbleBtn = ImageIO.read(new File("res/AbilityButtons/BubbleButton.png"));
		    healBtn = ImageIO.read(new File("res/AbilityButtons/HealButton.png"));
		    blockBtn = ImageIO.read(new File("res/AbilityButtons/BlockButton.png"));
		    poisonBtn = ImageIO.read(new File("res/AbilityButtons/PoisonButton.png"));
		    explosiveBtn = ImageIO.read(new File("res/AbilityButtons/ExplosiveButton.png"));
		    confuseBtn = ImageIO.read(new File("res/AbilityButtons/ConfuseButton.png"));
		    pummelBtn = ImageIO.read(new File("res/AbilityButtons/PummelButton.png"));
		    tendonBtn = ImageIO.read(new File("res/AbilityButtons/TendonButton.png"));
		    stealBtn = ImageIO.read(new File("res/AbilityButtons/StealButton.png"));
		    breakerBtn = ImageIO.read(new File("res/AbilityButtons/BreakerButton.png"));
		    shieldBtn = ImageIO.read(new File("res/AbilityButtons/ShieldButton.png"));
		    fortifyBtn = ImageIO.read(new File("res/AbilityButtons/FortifyButton.png"));
		    
		    // Other images
		    backgroundImg = ImageIO.read(new File("res/Backgrounds/Fight_BG.png"));
		    returnBtn = ImageIO.read(new File("res/FunctionButtons/HomeButton.png"));
		    turnBtn = ImageIO.read(new File("res/FunctionButtons/TurnButton.png"));
		    
		    // Card images
		    swingCard = ImageIO.read(new File("res/AbilityCards/OverheadSwing.png"));
		    rendCard = ImageIO.read(new File("res/AbilityCards/Rend.png"));
		    hardenCard = ImageIO.read(new File("res/AbilityCards/Harden.png"));
		    stunCard = ImageIO.read(new File("res/AbilityCards/Stun.png"));
		    riposteCard = ImageIO.read(new File("res/AbilityCards/Riposte.png"));
		    whirlwindCard = ImageIO.read(new File("res/AbilityCards/Whirlwind.png"));
		    decapCard = ImageIO.read(new File("res/AbilityCards/Decapitate.png"));
		    weakenCard = ImageIO.read(new File("res/AbilityCards/Weaken.png"));
		    attackCard = ImageIO.read(new File("res/AbilityCards/BasicAttack.png"));
		    bubbleCard = ImageIO.read(new File("res/AbilityCards/Bubble.png"));
		    healCard = ImageIO.read(new File("res/AbilityCards/Heal.png"));
		    blockCard = ImageIO.read(new File("res/AbilityCards/Block.png"));
		    poisonCard = ImageIO.read(new File("res/AbilityCards/PoisonSlash.png"));
		    explosiveCard = ImageIO.read(new File("res/AbilityCards/ExplosiveAttack.png"));
		    confuseCard = ImageIO.read(new File("res/AbilityCards/Confuse.png"));
		    pummelCard = ImageIO.read(new File("res/AbilityCards/Pummel.png"));
		    tendonCard = ImageIO.read(new File("res/AbilityCards/TendonCutter.png"));
		    stealCard = ImageIO.read(new File("res/AbilityCards/LifeSteal.png"));
		    breakerCard = ImageIO.read(new File("res/AbilityCards/GroundBreaker.png"));
		    shieldCard = ImageIO.read(new File("res/AbilityCards/ShieldWall.png"));
		    fortifyCard = ImageIO.read(new File("res/AbilityCards/FortifiedAttack.png"));
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
	    	g.setColor(new Color(255, 200, 0));
		    g.fillOval(bubbleLoc, 240, 20, 20);
		    bubbleLoc += 30;
	    }
	    
	    if (isHovering) {
	    	if(hoverAttack)
	    		g.drawImage(attackCard, 500, 200, this);
	    	else if(hoverSwing)
		    	g.drawImage(swingCard, 500, 200, this);
	    	else if(hoverHarden)
		    	g.drawImage(hardenCard, 500, 200, this);
	    	else if(hoverStun)
		    	g.drawImage(stunCard, 500, 200, this);
	    	else if(hoverRiposte)
		    	g.drawImage(riposteCard, 500, 200, this);
        }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof JButton) {
	        String actionCommand = ((JButton) e.getSource()).getActionCommand();
	        switch (actionCommand) {
	        case "Swing":
	        	hoverSwing = true;
	        	break;
	        case "Attack":
	        	hoverAttack = true;
	        	break;
	        case "Harden":
	        	hoverHarden = true;
	        	break;
	        case "Stun":
	        	hoverStun = true;
	        	break;
	        case "Riposte":
	        	hoverRiposte = true;
	        	break;
	        case "Rend":
	        	hoverRend = true;
	        	break;
	        case "Whirlwind":
	        	hoverWhirlwind = true;
	        	break;
	        case "Decapitate":
	        	hoverDecap = true;
	        	break;
	        case "Weaken":
	        	hoverWeaken = true;
	        	break;
	        case "Bubble":
	        	hoverBubble = true;
	        	break;
	        case "Heal":
	        	hoverHeal = true;
	        	break;
	        case "Block":
	        	hoverBlock = true;
	        	break;
	        }
	        isHovering = true;	
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isHovering = false;  
		hoverAttack = false;
		hoverSwing = false;
		hoverHarden = false;
		hoverStun = false;
		hoverRiposte = false;
	}	
	
	@Override
	public void mouseMoved(MouseEvent e) {

	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}

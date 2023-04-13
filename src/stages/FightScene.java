package stages;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
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

import javax.swing.JLabel;

import main.GameWindow;
import constants.*;

@SuppressWarnings({"serial", "unused"})
public class FightScene extends JPanel implements ActionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapon weapon = new Weapon();
	private JLabel lblPlayerHP, lblEnemyHP, lblDamageDealt, lblExp, lblLevelUp, lblEnemyName, lblReward;
	private JButton returnButton, attackButton, menuButton, turnButton, levelUpHPButton, levelUpStrButton, 
			ability1, ability2, ability3;
	private JButton[] buttons = new JButton[] {attackButton, menuButton, turnButton, ability1, ability2, ability3};
	private JButton[] actionButtons = new JButton[] {attackButton, ability1, ability2, ability3};
	String youLost = "You\nLost";
	String youWon = "You\nWon";
	private Random rand = new Random();
	private int enemySelectRand = rand.nextInt(2);
	private int currentLevel, currentStage, turnCounter, abilityID, enemiesDefeated, rendLeft, weakLeft;
	private boolean playerWin, playerAttack, riposteActive, gameOver, enemyAttack, hardenActive,
			enemyDead, stunActive, bossActive;
	private Font f = new Font("Serif", Font.PLAIN, 18);
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private Image pImg, wImg, pAttackImg, wAttackImg, eImg, eAttackImg, backgroundImg;
	private Timer t = new java.util.Timer();
	private Timer turnTimer = new java.util.Timer();
	private final int SCALE = 250;
	private Image basicAtk, decapBtn, swingBtn, riposteBtn, rendBtn, hardenBtn, whirlwindBtn, 
			returnBtn, turnBtn;

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
			if(Storage.getInstance().ironAxe.getIsOwned() == 1)
				weapon.weaponOne(Storage.getInstance().ironAxe.getAttackPower());
			else
				weapon.noWeapon();
		}
		
		setLayout(null);
		
		loadImages();
		loadAnimations();
        initComponents();
        disableButtonFocus();
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
            turnCounter -= Storage.getInstance().getAttackButtonCost();            
            doAttack();
            break;
        case "Swing":
        	abilityID = Storage.getInstance().overheadSwing.getID();
        	turnCounter -= Storage.getInstance().overheadSwing.getAbilityCost();
        	doAttack();
        	break;
        case "Decapitate":
        	abilityID = Storage.getInstance().decapitate.getID();
        	turnCounter -= Storage.getInstance().decapitate.getAbilityCost();
        	doAttack();
        	break;
        case "Riposte":
        	if(rand.nextInt(4) != 0)
        		abilityID = Storage.getInstance().riposte.getID();
        	else
        		riposteActive = true;
            enemyAttack();            
            turnCounter = player.getMana();
            enableActionButtons();
            playerAttack = true;
        	break;
        case "Rend":
        	rendLeft = 3;
        	turnCounter -= Storage.getInstance().rend.getAbilityCost();
        	enableActionButtons();
        	playerAttack = true;
        	break;
        case "Harden":
            turnCounter -= Storage.getInstance().harden.getAbilityCost();
            hardenActive = true;
            playerAttack = true;
            break;
        case "Whirlwind":
        	abilityID = Storage.getInstance().whirlwind.getID();
            turnCounter -= Storage.getInstance().whirlwind.getAbilityCost();
            doAttack();
            break;
        case "Weaken":
        	weakLeft = 2;
        	playerAttack = true;
        	turnCounter -= Storage.getInstance().weaken.getAbilityCost();
        	enableActionButtons();
        	break;
        case "Stun":
        	if(rand.nextInt(2) == 0 ) {
	        	stunActive = true;
	        	playerAttack = true;
        	}
        	else
        		lblDamageDealt.setText("Stun failed");
        	turnCounter -= Storage.getInstance().weaken.getAbilityCost();
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
        }   
	}
	
	public void isPlayerDead() {
		if(player.getPlayerHP() <= 0) {
			returnButton.setText("<html>" + youLost.replaceAll("\\n", "<br>") + "</html>");
			returnButton.setVisible(true);
			currentLevel = 1;		// Player didn't succeed at clearing the stage
			playerWin = false;		// Will not unlock the next stage
			for(JButton button : buttons)
				button.setEnabled(false);
			lblDamageDealt.setVisible(false);
			gameOver = true;
		}			
	}
	
	public void isEnemyDead() {
		if(enemy.getEnemyHP() <= 0) {
			enemyDead = true;
			rendLeft = 0;
			player.playerGainCoin(enemy.getCoinValue());
			
			// Check if the player's defeated 3 enemies, if not, spawn a new one
			if(enemiesDefeated != 2 && currentLevel != 1 && 
				!enemy.isStrongEnemyActive() && !enemy.isBossActive()) {
				
				enemyDead = false;
				
				lblReward.setText("You got " + enemy.getCoinValue() + " coins");
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
				loadAnimations();
				enemy.enemyShowHP(lblEnemyHP);
				lblEnemyHP.repaint();	
				if (player.getPlayerLevel() <= 15) {	// Check if player is max level
					player.increasePlayerExp(enemy.getExpValue());	// Add EXP to player
					if(player.getPlayerExp() >= player.getLevelCap()) {	// Check if player has enough EXP to level up
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
				lblReward.setText("You got " + enemy.getCoinValue() + " coins");
				lblReward.setVisible(true);
				if(bossActive) {
					Storage.getInstance().ironAxe.setIsOwned(1);
					lblReward.setText("You got " + Storage.getInstance().ironAxe.getWeaponName());
				}
				returnButton.setText("<html>" + youWon.replaceAll("\\n", "<br>") + "</html>");
				returnButton.setVisible(true);
				playerWin = true;		// Will unlock the next stage
				for(JButton button : buttons)
					button.setEnabled(false);	
				lblDamageDealt.setVisible(false);
				
				
				if (player.getPlayerLevel() <= 15) {
					player.increasePlayerExp(enemy.getExpValue()); // Add EXP to player
					if(player.getPlayerExp() >= player.getLevelCap()) {	// Check if player has enough EXP to level up
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
		
		if(turnCounter < Storage.getInstance().getAttackButtonCost())
			attackButton.setEnabled(false);
		
		switch(ability1.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < Storage.getInstance().overheadSwing.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= Storage.getInstance().decapitate.getAbilityCost() && enemy.getEnemyHP() <= (enemy.getEnemyMaxHP() / 3))
	            ability1.setEnabled(true);
	        else 
	            ability1.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter != Storage.getInstance().riposte.getAbilityCost())
	    		ability1.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < Storage.getInstance().rend.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < Storage.getInstance().harden.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < Storage.getInstance().whirlwind.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Weaken":
	    	if(turnCounter < Storage.getInstance().weaken.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Stun":
	    	if(turnCounter < Storage.getInstance().stun.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
		}
		
		switch(ability2.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < Storage.getInstance().overheadSwing.getAbilityCost()) {
	        	ability2.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= Storage.getInstance().decapitate.getAbilityCost() && enemy.getEnemyHP() <= (enemy.getEnemyMaxHP() / 3))
	        	ability2.setEnabled(true);
	        else 
	        	ability2.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter != Storage.getInstance().riposte.getAbilityCost())
	    		ability2.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < Storage.getInstance().rend.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < Storage.getInstance().harden.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < Storage.getInstance().whirlwind.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Weaken":
	    	if(turnCounter < Storage.getInstance().weaken.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Stun":
	    	if(turnCounter < Storage.getInstance().stun.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
		}
		
		switch(ability3.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < Storage.getInstance().overheadSwing.getAbilityCost()) {
	        	ability3.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= Storage.getInstance().decapitate.getAbilityCost() && enemy.getEnemyHP() <= (enemy.getEnemyMaxHP() / 3))
	        	ability3.setEnabled(true);
	        else 
	        	ability3.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter != Storage.getInstance().riposte.getAbilityCost())
	    		ability3.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < Storage.getInstance().rend.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < Storage.getInstance().harden.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < Storage.getInstance().whirlwind.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Weaken":
	    	if(turnCounter < Storage.getInstance().weaken.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Stun":
	    	if(turnCounter < Storage.getInstance().stun.getAbilityCost()) {
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
			int temp = Storage.getInstance().rend.getAttackPower() + player.getPlayerStrength() / 2;
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
			turnTimer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	int temp = enemy.getEnemyStrength();
	            	enemyAttack = true;
	            	// Check if enemy attack missed
	    			if(abilityID != 3 && enemy.enemyAttack()) {
	    				if(hardenActive) {
	    					temp = temp / 2;
	    					if(weakLeft > 0) {						
	    						temp = temp / Storage.getInstance().weaken.getAttackPower();
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
	    					temp = temp / Storage.getInstance().weaken.getAttackPower();
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
	    				int x = enemy.getEnemyStrength() / 2 + Storage.getInstance().riposte.getAttackPower();			
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
					y = Storage.getInstance().overheadSwing.getAttackPower();
					break;
				case 2:
					y = Storage.getInstance().decapitate.getAttackPower();
					break;
				case 6:
					y = Storage.getInstance().whirlwind.getAttackPower();
				}
				x = player.getPlayerStrength() / 2 + weapon.getWeaponDamage() / 2 + y + player.getTempStr(); // Ability attack
				if(abilityID == 6)	// Whirlwind
					for(int i = 1; i < 3; i++)
						x = x + (player.getPlayerStrength() / 2 + weapon.getWeaponDamage() / 2 + y + player.getTempStr());	
				abilityID = 0;
			}
			else
				x = rand.nextInt(1, player.getPlayerStrength() + weapon.getWeaponDamage() + 1 + player.getTempStr());	// Basic attack
			
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

	private void initComponents() {	
		
		// Useful labels		
		lblPlayerHP = new JLabel();
		lblPlayerHP.setFont(Storage.getInstance().font2);
		player.playerShowHP(lblPlayerHP);
		lblPlayerHP.setForeground(Color.BLACK);
		lblPlayerHP.setBounds(1010, 270, 100, 30);
		add(lblPlayerHP);
		
		lblEnemyHP = new JLabel();
		lblEnemyHP.setFont(Storage.getInstance().font2);
		enemy.enemyShowHP(lblEnemyHP);
		lblEnemyHP.setForeground(Color.BLACK);
		lblEnemyHP.setBounds(50, 270, 100, 30);		
		add(lblEnemyHP);
		
		lblEnemyName = new JLabel();
		lblEnemyName.setFont(Storage.getInstance().font2);
		lblEnemyName.setText(enemy.getEnemyName());
		lblEnemyName.setForeground(Color.WHITE);
		lblEnemyName.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnemyName.setVerticalAlignment(SwingConstants.CENTER);
		lblEnemyName.setBounds(40, 240, 200, 30);
		add(lblEnemyName);
		
		lblExp = new JLabel();
		lblExp.setFont(Storage.getInstance().font2);
		lblExp.setForeground(Color.WHITE);
		lblExp.setHorizontalAlignment(SwingConstants.CENTER);
		lblExp.setVerticalAlignment(SwingConstants.CENTER);
		lblExp.setBounds(515, 420, 250, 50);
		lblExp.setVisible(false);
		add(lblExp);
		
		lblReward = new JLabel();
		lblReward.setFont(Storage.getInstance().font2);
		lblReward.setForeground(Color.WHITE);
		lblReward.setHorizontalAlignment(SwingConstants.CENTER);
		lblReward.setVerticalAlignment(SwingConstants.CENTER);
		lblReward.setBounds(515, 390, 250, 50);
		lblReward.setVisible(false);
		add(lblReward);
		
		lblLevelUp = new JLabel();
		lblLevelUp.setFont(Storage.getInstance().font2);
		lblLevelUp.setBounds(200, 300, 100, 100);
		lblLevelUp.setBorder(blackline);
		lblLevelUp.setBackground(Color.gray);
		lblLevelUp.setOpaque(true);
		lblLevelUp.setVisible(false);
		add(lblLevelUp);
		
		// Damage dealt label
		lblDamageDealt = new JLabel();
		lblDamageDealt.setFont(Storage.getInstance().font3);
		lblDamageDealt.setBounds(400, 180, 450, 30);
		lblDamageDealt.setBorder(blackline);
		lblDamageDealt.setBackground(Color.WHITE);
		lblDamageDealt.setOpaque(true);
		lblDamageDealt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDamageDealt.setVerticalAlignment(SwingConstants.CENTER);
		add(lblDamageDealt);
		
		// Navigation buttons								
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
        returnButton.setFont(Storage.getInstance().font);
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
        switch(player.getAbility1ID()) {
        case 1:
        	ability1.setActionCommand("Swing");
        	ability1.setIcon(new ImageIcon(swingBtn));
        	break;
        case 2:
        	ability1.setActionCommand("Decapitate");
        	ability1.setIcon(new ImageIcon(decapBtn));
        	ability1.setEnabled(false);
        	break;
        case 3:
        	ability1.setActionCommand("Riposte");
        	ability1.setIcon(new ImageIcon(riposteBtn));
        	ability1.setEnabled(false);
        	break;
        case 4:
        	ability1.setActionCommand("Rend");
        	ability1.setIcon(new ImageIcon(rendBtn));
        	break;
        case 5:
        	ability1.setActionCommand("Harden");
        	ability1.setIcon(new ImageIcon(hardenBtn));
        	break;
        case 6:
        	ability1.setActionCommand("Whirlwind");
        	ability1.setIcon(new ImageIcon(whirlwindBtn));
        	break;
        case 7:
        	ability1.setActionCommand("Weaken");
        	ability1.setText("Weaken");
        	break;
        case 8:
        	ability1.setActionCommand("Stun");
        	ability1.setText("Stun");
        	break;
        }        
        ability1.addActionListener(this);
        ability1.setBounds(250, 550, 150, 100);
        add(ability1);
      
        ability2 = new JButton();      
        switch(player.getAbility2ID()) {
        case 1:
        	ability2.setActionCommand("Swing");
        	ability2.setIcon(new ImageIcon(swingBtn));
        	break;
        case 2:
        	ability2.setActionCommand("Decapitate");
        	ability2.setIcon(new ImageIcon(decapBtn));
        	ability2.setEnabled(false);
        	break;
        case 3:
        	ability2.setActionCommand("Riposte");
        	ability2.setIcon(new ImageIcon(riposteBtn));
        	ability2.setEnabled(false);
        	break;
        case 4:
        	ability2.setActionCommand("Rend");
        	ability2.setIcon(new ImageIcon(rendBtn));
        	break;
        case 5:
        	ability2.setActionCommand("Harden");
        	ability2.setIcon(new ImageIcon(hardenBtn));
        	break;
        case 6:
        	ability2.setActionCommand("Whirlwind");
        	ability2.setIcon(new ImageIcon(whirlwindBtn));
        	break;
        case 7:
        	ability2.setActionCommand("Weaken");
        	ability2.setText("Weaken");
        	break;
        case 8:
        	ability2.setActionCommand("Stun");
        	ability2.setText("Stun");
        	break;
        }        
        ability2.addActionListener(this);
        ability2.setBounds(430, 550, 150, 100);
        add(ability2);
        
        ability3 = new JButton();      
        switch(player.getAbility3ID()) {
        case 1:
        	ability3.setActionCommand("Swing");
        	ability3.setIcon(new ImageIcon(swingBtn));
        	break;
        case 2:
        	ability3.setActionCommand("Decapitate");
        	ability3.setIcon(new ImageIcon(decapBtn));
        	ability3.setEnabled(false);
        	break;
        case 3:
        	ability3.setActionCommand("Riposte");
        	ability3.setIcon(new ImageIcon(riposteBtn));
        	ability3.setEnabled(false);
        	break;
        case 4:
        	ability3.setActionCommand("Rend");
        	ability3.setIcon(new ImageIcon(rendBtn));
        	break;
        case 5:
        	ability3.setActionCommand("Harden");
        	ability3.setIcon(new ImageIcon(hardenBtn));
        	break;
        case 6:
        	ability3.setActionCommand("Whirlwind");
        	ability3.setIcon(new ImageIcon(whirlwindBtn));
        	break;
        case 7:
        	ability3.setActionCommand("Weaken");
        	ability3.setText("Weaken");
        	break;
        case 8:
        	ability3.setActionCommand("Stun");
        	ability3.setText("Stun");
        	break;
        }        
        ability3.addActionListener(this);
        ability3.setBounds(610, 550, 150, 100);
        add(ability3);
        
        turnButton = new JButton();
        turnButton.setActionCommand("Turn");
        turnButton.setIcon(new ImageIcon(turnBtn));
        turnButton.addActionListener(this);
        turnButton.setBounds(950, 600, 100, 50);
        add(turnButton);
        
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
		Graphics2D g1 = (Graphics2D)g;
//		g1.setStroke(new BasicStroke(5));
		float hpProc, hpProc2;
		int bubbleLoc;
	    
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
	    hpProc = (float)enemy.getEnemyHP() / (float)enemy.getEnemyMaxHP();
	    if(hpProc <= 1 && hpProc > 0.95) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 200, 30);
	    } 
	    else if(hpProc <= 0.95 && hpProc > 0.9) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 190, 30);
	    } 
	    else if(hpProc <= 0.9 && hpProc > 0.85) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 180, 30);
	    } 
	    else if(hpProc <= 0.85 && hpProc > 0.8) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 170, 30);
	    } 
	    else if(hpProc <= 0.8 && hpProc > 0.75) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 160, 30);
	    } 
	    else if(hpProc <= 0.75 && hpProc > 0.7) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 150, 30);
	    } 
	    else if(hpProc <= 0.7 && hpProc > 0.65) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 140, 30);
	    } 
	    else if(hpProc <= 0.65 && hpProc > 0.6) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 130, 30);
	    } 
	    else if(hpProc <= 0.6 && hpProc > 0.55) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 120, 30);
	    } 
	    else if(hpProc <= 0.55 && hpProc > 0.5) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 110, 30);
	    } 
	    else if(hpProc <= 0.5 && hpProc > 0.45) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 100, 30);
	    } 
	    else if(hpProc <= 0.45 && hpProc > 0.4) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 90, 30);
	    } 
	    else if(hpProc <= 0.4 && hpProc > 0.35) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 80, 30);
	    } 
	    else if(hpProc <= 0.35 && hpProc > 0.3) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 70, 30);
	    } 
	    else if(hpProc <= 0.3 && hpProc > 0.25) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 60, 30);
	    } 
	    else if(hpProc <= 0.25 && hpProc > 0.2) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 50, 30);
	    } 
	    else if(hpProc <= 0.2 && hpProc > 0.15) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 40, 30);
	    } 
	    else if(hpProc <= 0.15 && hpProc > 0.1) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 30, 30);	
	    }
	    else if(hpProc <= 0.10 && hpProc > 0.05) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 20, 30);	
	    }
	    else if(hpProc <= 0.05 && hpProc > 0) {
	    	g.setColor(Color.RED);
	    	g.fillRect(40, 270, 10, 30);	
	    }
	    
	  //Player HP bar
	    g.setColor(Color.WHITE);
	    g.fillRect(1000, 270, 200, 30);
	    hpProc2 = (float)player.getPlayerHP() / (float)player.getPlayerMaxHP();
	    if(hpProc2 <= 1 && hpProc2 > 0.95) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 200, 30);
	    } 
	    else if(hpProc2 <= 0.95 && hpProc2 > 0.9) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 190, 30);
	    } 
	    else if(hpProc2 <= 0.9 && hpProc2 > 0.85) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 180, 30);
	    } 
	    else if(hpProc2 <= 0.85 && hpProc2 > 0.8) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 170, 30);
	    } 
	    else if(hpProc2 <= 0.8 && hpProc2 > 0.75) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 160, 30);
	    } 
	    else if(hpProc2 <= 0.75 && hpProc2 > 0.7) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 150, 30);
	    } 
	    else if(hpProc2 <= 0.7 && hpProc2 > 0.65) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 140, 30);
	    } 
	    else if(hpProc2 <= 0.65 && hpProc2 > 0.6) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 130, 30);
	    } 
	    else if(hpProc2 <= 0.6 && hpProc2 > 0.55) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 120, 30);
	    } 
	    else if(hpProc2 <= 0.55 && hpProc2 > 0.5) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 110, 30);
	    } 
	    else if(hpProc2 <= 0.5 && hpProc2 > 0.45) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 100, 30);
	    } 
	    else if(hpProc2 <= 0.45 && hpProc2 > 0.4) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 90, 30);
	    } 
	    else if(hpProc2 <= 0.4 && hpProc2 > 0.35) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 80, 30);
	    } 
	    else if(hpProc2 <= 0.35 && hpProc2 > 0.3) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 70, 30);
	    } 
	    else if(hpProc2 <= 0.3 && hpProc2 > 0.25) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 60, 30);
	    } 
	    else if(hpProc2 <= 0.25 && hpProc2 > 0.2) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 50, 30);
	    } 
	    else if(hpProc2 <= 0.2 && hpProc2 > 0.15) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 40, 30);
	    } 
	    else if(hpProc2 <= 0.15 && hpProc2 > 0.1) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 30, 30);	
	    }
	    else if(hpProc2 <= 0.10 && hpProc2 > 0.05) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 20, 30);	
	    }
	    else if(hpProc2 <= 0.05 && hpProc2 > 0) {
	    	g.setColor(Color.RED);
	    	g.fillRect(1000, 270, 10, 30);	
	    }
	    
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

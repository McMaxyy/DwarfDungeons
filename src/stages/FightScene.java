package stages;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.JLabel;

import main.GameWindow;
import warriorAbilities.*;
import constants.*;

@SuppressWarnings("serial")
public class FightScene extends JPanel implements ActionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapons weapon = new Weapons();
	private OverheadSwing overheadSwing = new OverheadSwing();
	private Decapitate decapitate = new Decapitate();
	private Riposte riposte = new Riposte();
	private Rend rend = new Rend();
	private Harden harden = new Harden();
	private Whirlwind whirlwind = new Whirlwind();
	private JLabel lblPlayerHP, lblEnemyHP, lblDamageDealt, lblTurnCounter, lblExp, lblLevelUp;
	private JButton returnButton, attackButton, quitButton, menuButton, 
			turnButton, levelUpHPButton, levelUpStrButton, ability1, ability2, 
			ability3;
	private JButton[] buttons = new JButton[] {attackButton, quitButton, 
			menuButton, turnButton, ability1, ability2, ability3};
	private JButton[] actionButtons = new JButton[] {attackButton, ability1, 
			ability2, ability3};
	String youLost = "You\nLost";
	String youWon = "You\nWon";
	private Random rand = new Random();
	private int enemySelectRand = rand.nextInt(2);
	private int currentLevel, currentStage, turnCounter, abilityID, enemiesDefeated, rendLeft;
	private boolean playerWin, playerAttack, riposteActive, gameOver, enemyAttack, hardenActive,
			enemyDead;
	private Font f = new Font("Serif", Font.PLAIN, 18);
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private Image pImg, wImg, pAttackImg, wAttackImg, eImg, eAttackImg, backgroundImg;
	private Timer t = new java.util.Timer();
	private Timer turnTimer = new java.util.Timer();
	private final int SCALE = 250;
	private Image basicAtk, decapBtn, swingBtn, riposteBtn;

	public FightScene(GameWindow window, int levelIndex, int selectedStage){
		this.window = window;
		turnCounter = player.getMana();
		currentLevel = levelIndex;
		enemy.setCurrentStage(selectedStage);	
		abilityID = 0;
		enemiesDefeated = 0;
		
		if (enemySelectRand == 0)
			enemy.enemyOne();
		else
			enemy.enemyTwo();
		
		if(currentLevel == 1) {
			player.playerOne();
			weapon.weaponOne();
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
        case "Quit":
            System.exit(0);
            break;
        case "Main menu":
            window.showHomeScreen();
            break;
        case "Return":
            if (playerWin) {
				if(currentLevel == 4) {
					player.setUnlockedStage(player.getUnlockedStage() + 1);
					currentLevel = 1;
				}
				else
					currentLevel++;
            }
            window.showLevelSelector(currentLevel);
            break;
        case "Attack":
            turnCounter -= player.getAttackButtonCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
            playerAttack();
            playerAttack = true;
            break;
        case "Swing":
        	abilityID = overheadSwing.getID();
        	turnCounter -= overheadSwing.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
            playerAttack();
            playerAttack = true;
        	break;
        case "Decapitate":
        	abilityID = decapitate.getID();
        	turnCounter -= decapitate.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
            playerAttack();
            playerAttack = true;
        	break;
        case "Riposte":
        	if(rand.nextInt(2) == 0)
        		abilityID = riposte.getID();
        	else
        		riposteActive = true;
        	turnCounter -= riposte.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
            enemyAttack();            
            turnCounter = player.getMana();
            enableActionButtons();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
            playerAttack = true;
        	break;
        case "Rend":
        	rendLeft = 3;
        	turnCounter -= rend.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
        	playerAttack = true;
        	enableActionButtons();
        	break;
        case "Harden":
            turnCounter -= harden.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
            hardenActive = true;
            playerAttack = true;
            break;
        case "Whirlwind":
        	abilityID = whirlwind.getID();
            turnCounter -= whirlwind.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
            playerAttack();
            playerAttack = true;
            break;
        case "Turn":
            enemyAttack();            
            turnCounter = player.getMana();      
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
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
			rendLeft = 0;
			player.playerGainCoin(enemy.getCoinValue());
			
			// Check if the player's defeated 3 enemies, if not, spawn a new one
			if(enemiesDefeated != 2 && currentLevel != 1) {
				
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
				lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
				lblTurnCounter.repaint();
							
				repaint();
			}
			// Player has defeated 3 enemies, stage cleared
			else {
				turnCounter = 0;
				lblTurnCounter.setText("Available points: 0/" + player.getMana());	
				lblExp.setText("Gained " + enemy.getExpValue() + " exp");
				lblExp.setVisible(true);
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
			enemyDead = true;
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
		
		switch(ability1.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < overheadSwing.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= decapitate.getAbilityCost() && enemy.getEnemyHP() <= (enemy.getEnemyMaxHP() / 3))
	            ability1.setEnabled(true);
	        else 
	            ability1.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter != riposte.getAbilityCost())
	    		ability1.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < rend.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < harden.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < whirlwind.getAbilityCost()) {
	            ability1.setEnabled(false);
	        }
	    	break;
	    default:
	        break;
		}
		
		switch(ability2.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < overheadSwing.getAbilityCost()) {
	        	ability2.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= decapitate.getAbilityCost() && enemy.getEnemyHP() <= (enemy.getEnemyMaxHP() / 3))
	        	ability2.setEnabled(true);
	        else 
	        	ability2.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter != riposte.getAbilityCost())
	    		ability2.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < rend.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < harden.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < whirlwind.getAbilityCost()) {
	    		ability2.setEnabled(false);
	        }
	    	break;
	    default:
	        break;
		}
		
		switch(ability3.getActionCommand()) {
	    case "Swing":
	        if(turnCounter < overheadSwing.getAbilityCost()) {
	        	ability3.setEnabled(false);
	        }
	        break;
	    case "Decapitate":
	        if(turnCounter >= decapitate.getAbilityCost() && enemy.getEnemyHP() <= (enemy.getEnemyMaxHP() / 3))
	        	ability3.setEnabled(true);
	        else 
	        	ability3.setEnabled(false);
	        break;
	    case "Riposte":
	    	if(turnCounter != riposte.getAbilityCost())
	    		ability3.setEnabled(false);
	    	break;
	    case "Rend":
	    	if(turnCounter < rend.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Harden":
	    	if(turnCounter < harden.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    case "Whirlwind":
	    	if(turnCounter < whirlwind.getAbilityCost()) {
	    		ability3.setEnabled(false);
	        }
	    	break;
	    default:
	        break;
		}
		
		for(JButton button : actionButtons) {
			button.setVisible(true);
		}
	}	
	
	private void enemyAttack() {		
		if(rendLeft != 0) {
			int temp = rend.getAttackPower() + player.getPlayerStrength() / 2;
			enemy.enemyLoseHP(temp);
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			lblDamageDealt.setText("Enemy got hit by Rend for: " + temp + " damage");
			lblDamageDealt.repaint();
			isEnemyDead();
			rendLeft--;			
		}
		// Since Rend activates before the enemy attack, check if Rend killed the enemy
		if(!enemyDead) {
			turnTimer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	int temp = enemy.getEnemyStrength();
	            	enemyAttack = true;
	            	// Check if enemy attack missed
	    			if(abilityID != 3 && enemy.enemyAttack()) {
	    				if(hardenActive)
	    					player.playerLoseHP(temp / 2);	// Harden is active, enemy deals 50% dmg
	    				else
	    					player.playerLoseHP(temp);
	    		    	player.playerShowHP(lblPlayerHP);
	    		    	lblPlayerHP.repaint();
	    		    	if(riposteActive) {
	    		    		if(hardenActive)
	    		    			lblDamageDealt.setText("Riposte failed and the enemy dealt: " + temp / 2  + " damage");
	    		    		else
	    		    			lblDamageDealt.setText("Riposte failed and the enemy dealt: " + temp  + " damage");
	    		    	}
	    		    	else { 
	    		    		if(hardenActive)
	    		    			lblDamageDealt.setText("Enemy dealt: " + temp / 2 + " damage");
	    		    		else
	    		    			lblDamageDealt.setText("Enemy dealt: " + temp  + " damage");
	    		    	}	    		    		    		    					    		    	
	    				lblDamageDealt.repaint();
	    		    	isPlayerDead();
	    		    	riposteActive = false;		    		    	
	    	    	}
	    			// Check if Riposte was successful
	    			else if (abilityID == 3){
	    				int x = enemy.getEnemyStrength() / 2 + riposte.getAttackPower();			
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
	    			enableActionButtons();
	    			abilityID = 0;
	    			hardenActive = false;
	            }
	        }, 400);	// Timer value in milliseconds        									
		}	
		enemyDead = false;
	}
	
	private void playerAttack(){
		// Check if player attack missed
		if(player.playerAttack()) {
			int x, y = 0;
			
			// Check if ability was used
			if(abilityID != 0) {
				switch(abilityID) {
				case 1:
					y = overheadSwing.getAttackPower();
					break;
				case 2:
					y = decapitate.getAttackPower();
					break;
				case 6:
					y = whirlwind.getAttackPower();
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
		player.playerShowHP(lblPlayerHP);
		lblPlayerHP.setBackground(Color.WHITE);
		lblPlayerHP.setOpaque(true);
		lblPlayerHP.setBounds(1050, 250, 100, 30);
		add(lblPlayerHP);
		
		lblEnemyHP = new JLabel();
		enemy.enemyShowHP(lblEnemyHP);
		lblEnemyHP.setBackground(Color.WHITE);
		lblEnemyHP.setOpaque(true);
		lblEnemyHP.setBounds(50, 280, 100, 30);		
		add(lblEnemyHP);
		
		lblTurnCounter = new JLabel();
		lblTurnCounter.setBackground(Color.WHITE);
		lblTurnCounter.setOpaque(true);
		lblTurnCounter.setBounds(970, 190, 120, 50);
		lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getMana());
		add(lblTurnCounter);
		
		lblExp = new JLabel();
		lblExp.setBounds(200, 420, 100, 40);
		lblExp.setVisible(false);
		add(lblExp);
		
		lblLevelUp = new JLabel();
		lblLevelUp.setBounds(200, 300, 100, 100);
		lblLevelUp.setBorder(blackline);
		lblLevelUp.setBackground(Color.gray);
		lblLevelUp.setOpaque(true);
		lblLevelUp.setVisible(false);
		add(lblLevelUp);
		
		// Damage dealt label
		lblDamageDealt = new JLabel();
		lblDamageDealt.setBounds(400, 180, 450, 30);
		lblDamageDealt.setBorder(blackline);
		lblDamageDealt.setBackground(Color.WHITE);
		lblDamageDealt.setOpaque(true);
		lblDamageDealt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDamageDealt.setVerticalAlignment(SwingConstants.CENTER);
		add(lblDamageDealt);
		
		// Navigation buttons				
		quitButton = new JButton();
		quitButton.setText("Quit");
		quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(1100, 25, 120, 40);
        add(quitButton);		
		
        menuButton = new JButton();
        menuButton.setActionCommand("Main menu");
        menuButton.setText("Main menu");
        menuButton.addActionListener(this);
        menuButton.setBounds(1100, 80, 120, 40);
        add(menuButton);    
        
        // Button for returning to the level selector when the player either wins or dies
        returnButton = new JButton();
        returnButton.setFont(f);
        returnButton.setFocusable(false);
        returnButton.setActionCommand("Return");
        returnButton.addActionListener(this);
        returnButton.setBounds(200, 300, 100, 100);
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
        	ability1.setText("Rend " + "(-" + rend.getAbilityCost() + ")");
        	break;
        case 5:
        	ability1.setActionCommand("Harden");
        	ability1.setText("Harden " + "(-" + harden.getAbilityCost() + ")");
        	break;
        case 6:
        	ability1.setActionCommand("Whirlwind");
        	ability1.setText("Whirlwind " + "(-" + whirlwind.getAbilityCost() + ")");
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
        	ability2.setText("Rend " + "(-" + rend.getAbilityCost() + ")");
        	break;
        case 5:
        	ability2.setActionCommand("Harden");
        	ability2.setText("Harden " + "(-" + harden.getAbilityCost() + ")");
        	break;
        case 6:
        	ability2.setActionCommand("Whirlwind");
        	ability2.setText("Whirlwind " + "(-" + whirlwind.getAbilityCost() + ")");
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
        	ability3.setText("Rend " + "(-" + rend.getAbilityCost() + ")");
        	break;
        case 5:
        	ability3.setActionCommand("Harden");
        	ability3.setText("Harden " + "(-" + harden.getAbilityCost() + ")");
        	break;
        case 6:
        	ability3.setActionCommand("Whirlwind");
        	ability3.setText("Whirlwind " + "(-" + whirlwind.getAbilityCost() + ")");
        	break;
        }        
        ability3.addActionListener(this);
        ability3.setBounds(610, 550, 150, 100);
        add(ability3);
        
        turnButton = new JButton();
        turnButton.setActionCommand("Turn");
        turnButton.setText("End turn");
        turnButton.addActionListener(this);
        turnButton.setBounds(1120, 200, 100, 30);
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
        buttons[1] = quitButton;
        buttons[2] = menuButton;
        buttons[3] = turnButton;
        buttons[4] = ability1;
        buttons[5] = ability2;
        buttons[6] = ability3;
        
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
		    backgroundImg = ImageIO.read(new File("res/Backgrounds/Fight_BG.png"));
		  } catch (Exception ex) {
			  System.out.println(ex);
		  }
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    
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
    	        }, 
    	        // Timer value in milliseconds 
    	        500
	    	);
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
    	        }, 
    	        500
	    	);	    	
	    }
	 }	
}

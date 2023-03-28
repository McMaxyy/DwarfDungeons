package stages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.google.gson.Gson;

import javax.swing.JLabel;

import main.GameWindow;
import warriorAbilities.*;
import constants.*;

@SuppressWarnings("serial")
public class FightScene extends JPanel implements ActionListener {
	
	private GameWindow window;
	Gson gson = new Gson();
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapons weapon = new Weapons();
	private OverheadSwing overheadSwing = new OverheadSwing();
	private Decapitate decapitate = new Decapitate();
	private Riposte riposte = new Riposte();
	private JLabel lblPlayerHP, lblEnemyHP, lblDamageDealt, lblTurnCounter, lblExp, lblLevelUp, 
			enemyImg;
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
	private int currentLevel, turnCounter, abilityID, enemiesDefeated;
	private boolean playerWin, playerAttack, riposteActive, gameOver;
	private Font f = new Font("Serif", Font.PLAIN, 18);
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private Image pImg, wImg, pAttackImg, wAttackImg;
	private Timer t = new java.util.Timer();

	public FightScene(GameWindow window, int levelIndex){
		this.window = window;
		turnCounter = player.getTurnCount();
		currentLevel = levelIndex;
		weapon.weaponOne();
		abilityID = 0;
		enemiesDefeated = 1;
		
		if (enemySelectRand == 0)
			enemy.enemyOne();
		else
			enemy.enemyTwo();
		
		if(currentLevel == 1)
			player.playerOne();	
				
		// Enemy image
		enemyImg = new JLabel();
		enemyImg = enemy.getEnemyIcon();
		add(enemyImg);
		enemyImg.setBounds(50, 50, enemyImg.getWidth(), enemyImg.getHeight());
		
		pImg = Toolkit.getDefaultToolkit().createImage("res/IdleDwarf.gif");
		wImg = Toolkit.getDefaultToolkit().createImage("res/IronAxe.png");
		pAttackImg = Toolkit.getDefaultToolkit().createImage("res/DwarfAttack.gif");
		wAttackImg = Toolkit.getDefaultToolkit().createImage("res/AxeAttack.gif");
		
		setLayout(null);
		
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
                currentLevel++;
            }
            window.showLevelSelector(currentLevel);
            break;
        case "Attack":
            turnCounter -= player.getAttackButtonCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
            playerAttack();
            playerAttack = true;
            break;
        case "Swing":
        	abilityID = overheadSwing.getID();
        	turnCounter -= overheadSwing.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
            playerAttack();
            playerAttack = true;
        	break;
        case "Decapitate":
        	abilityID = decapitate.getID();
        	turnCounter -= decapitate.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
            playerAttack();
            playerAttack = true;
        	break;
        case "Riposte":
        	if(rand.nextInt(2) == 0)
        		abilityID = riposte.getID();
        	else
        		riposteActive = true;
        	turnCounter -= riposte.getAbilityCost();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
            enemyAttack();            
            turnCounter = player.getTurnCount();
            enableActionButtons();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
            playerAttack = true;
        	break;
        case "Turn":
            enemyAttack();            
            turnCounter = player.getTurnCount();
            enableActionButtons();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
            break;
        case "HP up":
            player.increaseMaxHP();
            disableLevelUpComponents();
            player.playerShowHP(lblPlayerHP);
            lblPlayerHP.repaint(); 
            if(!gameOver)
            	enableActionButtons();           
            if(gameOver)
            	returnButton.setVisible(true);
            break;
        case "Str up":
            player.increasePlayerStr();
            disableLevelUpComponents();
            player.playerShowHP(lblPlayerHP);
            lblPlayerHP.repaint();
            if(!gameOver)
            	enableActionButtons();           
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
			player.playerGainCoin(5);
			
			// Check if the player's defeated 3 enemies, if not, spawn a new one
			if(enemiesDefeated != 3) {
				
				// Remove tags that need to be updated
				remove(enemyImg);
				
				// Select new enemy
				if (rand.nextInt(2) == 0)
					enemy.enemyOne();
				else
					enemy.enemyTwo();
				
				// Refresh enemy pic & HP bar
				enemyImg = enemy.getEnemyIcon();
				add(enemyImg);
				enemyImg.setBounds(50, 50, enemyImg.getWidth(), enemyImg.getHeight());				
				enemy.enemyShowHP(lblEnemyHP);
				lblEnemyHP.repaint();	
				
				player.increasePlayerExp(enemy.getEnemyExp());
				if(player.getPlayerExp() >= player.getLevelOneCap()) {	// Check if player has enough EXP to level up
					player.levelUp();
					enableLevelUpComponents();
				}	
				enemiesDefeated++;
				
				// Refresh turn counter
				turnCounter = player.getTurnCount();
				lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
				lblTurnCounter.repaint();
				
				disableActionButtons();			
			}
			// Player has defeated 3 enemies, stage cleared
			else {
				turnCounter = 0;
				lblTurnCounter.setText("Available points: 0/" + player.getTurnCount());
				player.increasePlayerExp(enemy.getEnemyExp());		// Add EXP to player	
				lblExp.setText("Gained " + enemy.getEnemyExp() + " exp");
				lblExp.setVisible(true);
				returnButton.setText("<html>" + youWon.replaceAll("\\n", "<br>") + "</html>");
				returnButton.setVisible(true);
				playerWin = true;		// Will unlock the next stage
				for(JButton button : buttons)
					button.setEnabled(false);	
				lblDamageDealt.setVisible(false);
				
				
				if(player.getPlayerExp() >= player.getLevelOneCap()) {	// Check if player has enough EXP to level up
					player.levelUp();
					enableLevelUpComponents();
				}
				disableActionButtons();
				gameOver = true;
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
	}
	
	private void disableLevelUpComponents() {
		levelUpHPButton.setVisible(false);
		levelUpStrButton.setVisible(false);
		lblDamageDealt.setEnabled(true);
	}
	
	private void disableActionButtons() {
		for(JButton button : actionButtons)
			button.setEnabled(false);	
	}
	
	private void enableActionButtons() {
		if(enemiesDefeated != 4)
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
	    	if(turnCounter != 1)
	    		ability1.setEnabled(false);
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
	    	if(turnCounter != 1)
	    		ability2.setEnabled(false);
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
	    	if(turnCounter != 1)
	    		ability3.setEnabled(false);
	    	break;
	    default:
	        break;
		}
	}	
	
	private void enemyAttack() {
		if(abilityID != 3) {
			player.playerLoseHP(enemy.getEnemyStrength());
	    	player.playerShowHP(lblPlayerHP);
	    	lblPlayerHP.repaint();
	    	if(riposteActive)
	    		lblDamageDealt.setText("Riposte failed and the enemy dealt: " + enemy.getEnemyStrength()  + " damage");
	    	else {
	    		lblDamageDealt.setText("Enemy dealt: " + enemy.getEnemyStrength()  + " damage");
	    		isPlayerDead();
	    	}
			lblDamageDealt.repaint();
	    	isPlayerDead();
	    	riposteActive = false;
    	}
		else{
			int x = enemy.getEnemyStrength() + riposte.getAttackPower();			
			enemy.enemyLoseHP(x);
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			lblDamageDealt.setText("You reflected the attack and hit them for " + x + " damage");
			lblDamageDealt.repaint();			
			isEnemyDead();
		}
		abilityID = 0;
	}
	
	private void playerAttack(){
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
				}
				x = player.getPlayerStrength() / 2 + y;
				abilityID = 0;
			}
			else
				x = rand.nextInt(1, player.getPlayerStrength() + weapon.getWeaponDamage() + 1);	
			
			// Damage enemy & update label
			enemy.enemyLoseHP(x);
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			lblDamageDealt.setText("You dealt: " + x + " damage");
			lblDamageDealt.repaint();
		}
		else {
			lblDamageDealt.setText("You missed");
			lblDamageDealt.repaint();
		}			
		if(turnCounter <= 0)
			disableActionButtons();	
		isEnemyDead();	
	}
	
	
	
	private void initComponents() {	
		
		// Useful labels
		lblPlayerHP = new JLabel();
		player.playerShowHP(lblPlayerHP);
		lblPlayerHP.setBounds(850, 450, 100, 50);
		add(lblPlayerHP);
		
		lblEnemyHP = new JLabel();
		enemy.enemyShowHP(lblEnemyHP);
		lblEnemyHP.setBounds(220, 50, 100, 50);		
		add(lblEnemyHP);
		
		lblTurnCounter = new JLabel();
		lblTurnCounter.setBounds(970, 420, 120, 50);
		lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
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
		lblDamageDealt.setBounds(400, 320, 450, 30);
		lblDamageDealt.setBorder(blackline);
		lblDamageDealt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDamageDealt.setVerticalAlignment(SwingConstants.CENTER);
		add(lblDamageDealt);
		
		// Buttons				
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
        
        // Lost game button
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
        attackButton.setText("Attack (-" + player.getAttackButtonCost() + ")");
        attackButton.addActionListener(this);
        attackButton.setBounds(790, 550, 150, 100);
        add(attackButton);
        
        ability1 = new JButton();              
        switch(player.getAbility1ID()) {
        case 1:
        	ability1.setActionCommand("Swing");
        	ability1.setText("Swing (-" + overheadSwing.getAbilityCost() + ")");
        	break;
        case 2:
        	ability1.setActionCommand("Decapitate");
        	ability1.setText("Decap (-" + decapitate.getAbilityCost() + ")");
        	ability1.setEnabled(false);
        	break;
        case 3:
        	ability1.setActionCommand("Riposte");
        	ability1.setText("Riposte (-" + riposte.getAbilityCost() + ")");
        	ability1.setEnabled(false);
        	break;
        }        
        ability1.addActionListener(this);
        ability1.setBounds(250, 550, 150, 100);
        add(ability1);
      
        ability2 = new JButton();      
        switch(player.getAbility2ID()) {
        case 1:
        	ability2.setActionCommand("Swing");
        	ability2.setText("Swing (-" + overheadSwing.getAbilityCost() + ")");
        	break;
        case 2:
        	ability2.setActionCommand("Decapitate");
        	ability2.setText("Decap (-" + decapitate.getAbilityCost() + ")");
        	ability2.setEnabled(false);
        	break;
        case 3:
        	ability2.setActionCommand("Riposte");
        	ability2.setText("Riposte (-" + riposte.getAbilityCost() + ")");
        	ability2.setEnabled(false);
        	break;
        }        
        ability2.addActionListener(this);
        ability2.setBounds(430, 550, 150, 100);
        add(ability2);
        
        ability3 = new JButton();      
        switch(player.getAbility3ID()) {
        case 1:
        	ability3.setActionCommand("Swing");
        	ability3.setText("Swing (-" + overheadSwing.getAbilityCost() + ")");
        	break;
        case 2:
        	ability3.setActionCommand("Decapitate");
        	ability3.setText("Decap (-" + decapitate.getAbilityCost() + ")");
        	ability3.setEnabled(false);
        	break;
        case 3:
        	ability3.setActionCommand("Riposte");
        	ability3.setText("Riposte (-" + riposte.getAbilityCost() + ")");
        	ability3.setEnabled(false);
        	break;
        }        
        ability3.addActionListener(this);
        ability3.setBounds(610, 550, 150, 100);
        add(ability3);
        
        turnButton = new JButton();
        turnButton.setActionCommand("Turn");
        turnButton.setText("End turn");
        turnButton.addActionListener(this);
        turnButton.setBounds(1120, 430, 100, 30);
        add(turnButton);
        
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
	
	@Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (pImg != null && wImg != null && !playerAttack) {
	    	g.drawImage(wImg, 1050, 500, this);
	    	g.drawImage(pImg, 1050, 500, this);
	    }
	    if (pAttackImg != null && wAttackImg != null && playerAttack) {
	    	g.drawImage(pAttackImg, 1050, 500, this);
	    	g.drawImage(wAttackImg, 1050, 500, this);
	    	disableActionButtons();
	    	
	    	t.schedule( 
    	        new java.util.TimerTask() {
    	            @Override
    	            public void run() {
    	                // your code here
    	            	playerAttack = false;
    	            	if(turnCounter > 0 && !levelUpHPButton.isVisible())
    	            		enableActionButtons();
    	            }
    	        }, 
    	        550
	    	);
	    	
	    }	    
	 }	
}

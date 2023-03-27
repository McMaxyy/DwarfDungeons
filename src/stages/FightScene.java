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
import javax.swing.JLabel;

import main.GameWindow;
import constants.*;

public class FightScene extends JPanel implements ActionListener {
	
	private GameWindow window;
	private LevelSelector level = new LevelSelector(window, 0);
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapons weapon = new Weapons();
	private Abilities ability = new Abilities();
	private JLabel lblPlayerHP, lblEnemyHP, lblDamageDealt, lblTurnCounter, lblExp, lblLevelUp;
	private JButton returnButton, attackButton, fleeButton, quitButton, menuButton, 
			turnButton, levelUpHPButton, levelUpStrButton;
	private JButton[] buttons = new JButton[] {attackButton, fleeButton, quitButton, 
			menuButton, turnButton};
	private JButton[] actionButtons = new JButton[] {attackButton, fleeButton};
	String youLost = "You\nLost";
	String youWon = "You\nWon";
	private boolean turn, fightOver;
	private Random rand = new Random();
	private int enemySelectRand = rand.nextInt(2);
	private int currentLevel;
	private int turnCounter;
	private boolean playerWin, leveledUp, playerAttack;
	private Font f = new Font("Serif", Font.PLAIN, 18);
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private Image pImg, wImg, pAttackImg, wAttackImg;
	private Timer t = new java.util.Timer();

	public FightScene(GameWindow window, int levelIndex){
		this.window = window;
		turnCounter = player.getTurnCount();
		turn = true;
		currentLevel = levelIndex;
		weapon.weaponOne();		
		
		if (enemySelectRand == 0)
			enemy.enemyOne();
		else
			enemy.enemyTwo();
		
		if(currentLevel == 1)
			player.playerOne();		
				
		// Enemy image
		JLabel enemyImg = new JLabel();
		enemyImg = enemy.getEnemyIcon();
		add(enemyImg);
		enemyImg.setBounds(50, 100, enemyImg.getWidth(), enemyImg.getHeight());
		
		//Player image
//		JLabel playerImg = new JLabel();
//		playerImg = player.getPlayerIcon();
//		add(playerImg);
//		playerImg.setBounds(350, 500, playerImg.getWidth(), playerImg.getHeight());
		
		pImg = Toolkit.getDefaultToolkit().createImage("res/IdleDwarf.gif");
		wImg = Toolkit.getDefaultToolkit().createImage("res/IronAxe.png");
		pAttackImg = Toolkit.getDefaultToolkit().getImage("res/DwarfAttack.gif");
		wAttackImg = Toolkit.getDefaultToolkit().getImage("res/AxeAttack.gif");
		
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
            window.showMainMenu();
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
        case "Flee":
            if (rand.nextInt(2) == 0) {
                window.showLevelSelector(currentLevel);
            } else {
                disableActionButtons();
                lblDamageDealt.setText("You failed to escape.");
                lblDamageDealt.repaint();
                lblTurnCounter.setText("Available points: " + 0 + "/" + player.getTurnCount());
            }
            break;
        case "Turn":
            enemyAttack();
            enableActionButtons();
            turnCounter = player.getTurnCount();
            lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
            break;
        case "HP up":
            player.increaseMaxHP();
            disableLevelUpComponents();
            break;
        case "Str up":
            player.increasePlayerStr();
            disableLevelUpComponents();
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
		}			
	}
	
	public void isEnemyDead() {
		if(enemy.getEnemyHP() <= 0) {
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
		}			
	}
	
	private void disableButtonFocus() {		
		for(JButton button : buttons)
			button.setFocusable(false);			
	}
	
	private void disableActionButtons() {
		for(JButton button : actionButtons)
			button.setEnabled(false);			
	}
	
	private void enableActionButtons() {
		for(JButton button : actionButtons)
			button.setEnabled(true);			
	}
	
	private void enemyAttack() {
		player.playerLoseHP(enemy.getEnemyStrength());
    	player.playerShowHP(lblPlayerHP);
    	lblPlayerHP.repaint();
		lblDamageDealt.setText("Enemy dealt: " + enemy.getEnemyStrength()  + " damage");
		lblDamageDealt.repaint();
    	isPlayerDead();
    	turn = true;   		
	}
	
	private void playerAttack(){
		if(player.playerAttack()) {
			int x = rand.nextInt(1, player.getPlayerStrength() + weapon.getWeaponDamage() + 1);			
			enemy.enemyLoseHP(x);
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			lblDamageDealt.setText("You dealt: " + x + " damage");
			lblDamageDealt.repaint();
		}
		isEnemyDead();
		turn = false;		
		if(turnCounter <= 0)
			disableActionButtons();		
	}
	
	private void enableLevelUpComponents() {
		levelUpHPButton.setVisible(true);
		levelUpStrButton.setVisible(true);
		returnButton.setVisible(false);
	}
	
	private void disableLevelUpComponents() {
		levelUpHPButton.setVisible(false);
		levelUpStrButton.setVisible(false);
		returnButton.setVisible(true);
	}
	
	private void initComponents() {	
		
		// Useful labels
		lblPlayerHP = new JLabel();
		player.playerShowHP(lblPlayerHP);
		lblPlayerHP.setBounds(170, 500, 100, 50);
		add(lblPlayerHP);
		
		lblEnemyHP = new JLabel();
		enemy.enemyShowHP(lblEnemyHP);
		lblEnemyHP.setBounds(200, 100, 100, 50);		
		add(lblEnemyHP);
		
		lblTurnCounter = new JLabel();
		lblTurnCounter.setBounds(170, 560, 120, 50);
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
		lblDamageDealt.setBounds(120, 350, 250, 20);
		lblDamageDealt.setBorder(blackline);
		lblDamageDealt.setHorizontalAlignment(SwingConstants.CENTER);
		lblDamageDealt.setVerticalAlignment(SwingConstants.CENTER);
		add(lblDamageDealt);
		
		// Buttons				
		quitButton = new JButton();
		quitButton.setText("Quit");
		quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(375, 25, 100, 30);
        add(quitButton);		
		
        menuButton = new JButton();
        menuButton.setActionCommand("Main menu");
        menuButton.setText("Main menu");
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 30);
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
        attackButton.setBounds(50, 500, 100, 50);
        add(attackButton);
        
        fleeButton = new JButton();
        fleeButton.setActionCommand("Flee");
        fleeButton.setText("Flee (-" + player.getTurnCount() + ")");
        fleeButton.addActionListener(this);
        fleeButton.setBounds(50, 600, 100, 50);
        add(fleeButton);
        
        turnButton = new JButton();
        turnButton.setActionCommand("Turn");
        turnButton.setText("End turn");
        turnButton.addActionListener(this);
        turnButton.setBounds(370, 430, 100, 30);
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
        buttons[1] = fleeButton;
        buttons[2] = quitButton;
        buttons[3] = menuButton;
        buttons[4] = turnButton;
        
        actionButtons[0] = attackButton;
        actionButtons[1] = fleeButton;
	}
	
	@Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (pImg != null && wImg != null && !playerAttack) {
	    	g.drawImage(wImg, 350, 500, this);
	    	g.drawImage(pImg, 350, 500, this);
	    }
	    if (pAttackImg != null && wAttackImg != null && playerAttack) {
	    	g.drawImage(pAttackImg, 350, 500, this);
	    	g.drawImage(wAttackImg, 350, 500, this);
	    	disableActionButtons();
	    	
	    	t.schedule( 
    	        new java.util.TimerTask() {
    	            @Override
    	            public void run() {
    	                // your code here
    	            	playerAttack = false;
    	            	enableActionButtons();
    	            }
    	        }, 
    	        450
	    	);
	    	
	    }	    
	 }	
}

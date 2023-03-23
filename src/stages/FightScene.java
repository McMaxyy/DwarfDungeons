package stages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.JLabel;

import main.GameWindow;
import constants.Player;
import constants.Weapons;
import constants.Enemies;

public class FightScene extends JPanel implements ActionListener {
	
	private GameWindow window;
	private LevelSelector level = new LevelSelector(window, 0);
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapons weapon = new Weapons();
	private JLabel lblPlayerHP= new JLabel();
	private JLabel lblEnemyHP= new JLabel();
	private JButton returnButton, attackButton, fleeButton, quitButton, menuButton, turnButton;
	private JButton[] buttons = new JButton[] {attackButton, fleeButton, quitButton, menuButton, turnButton};
	private JButton[] actionButtons = new JButton[] {attackButton, fleeButton};
	String youLost = "You\nLost";
	String youWon = "You\nWon";
	private boolean turn, fightOver;
	private Random rand = new Random();
	private int enemySelectRand = rand.nextInt(2);
	private int currentLevel;
	private int turnCounter;
	private boolean playerWin;
	private JLabel lblDamageDealt = new JLabel();
	private JLabel lblTurnCounter = new JLabel();

	public FightScene(GameWindow window, int levelIndex){
		this.window = window;
		turnCounter = player.getTurnCount();
		turn = true;
		currentLevel = levelIndex;
		weapon.weaponOne();		
		
		Font f = new Font("Serif", Font.PLAIN, 18);
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
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
		JLabel playerImg = new JLabel();
		playerImg = player.getPlayerIcon();
		add(playerImg);
		playerImg.setBounds(350, 500, playerImg.getWidth(), playerImg.getHeight());
		
		setLayout(null);
				
		// Useful labels
		player.playerShowHP(lblPlayerHP);
		lblPlayerHP.setBounds(170, 500, 100, 50);
		add(lblPlayerHP);
		
		enemy.enemyShowHP(lblEnemyHP);
		lblEnemyHP.setBounds(200, 100, 100, 50);		
		add(lblEnemyHP);
		
		lblTurnCounter.setBounds(170, 560, 120, 50);
		lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
		add(lblTurnCounter);
		
		// Damage dealt labels
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
               
        buttons[0] = attackButton;
        buttons[1] = fleeButton;
        buttons[2] = quitButton;
        buttons[3] = menuButton;
        buttons[4] = turnButton;
        
        actionButtons[0] = attackButton;
        actionButtons[1] = fleeButton;
        
        disableButtonFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
		if (command.equals("Quit")) {
            System.exit(0);
        }
		else if (command.equals("Main menu")) {
        	window.showMainMenu();
        }
		else if (command.equals("Return")) {
			if(playerWin)
				currentLevel++;
        	window.showLevelSelector(currentLevel);
        }
		else if (command.equals("Attack")) {
			turnCounter -= player.getAttackButtonCost();
			lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
			playerAttack();
        }
		else if (command.equals("Flee")) {
			if (rand.nextInt(2) == 0)
				window.showLevelSelector(currentLevel);
			else {
				disableActionButtons();
				lblDamageDealt.setText("You failed to escape.");
				lblDamageDealt.repaint();
				lblTurnCounter.setText("Available points: " + 0 + "/" + player.getTurnCount());
				}
        	}
		else if (command.equals("Turn")) {
        	enemyAttack();
        	enableActionButtons();
        	turnCounter = player.getTurnCount();
        	lblTurnCounter.setText("Available points: " + turnCounter + "/" + player.getTurnCount());
        }
	}
	
	public void isPlayerDead() {
		if(player.getPlayerHP() <= 0) {
			returnButton.setText("<html>" + youLost.replaceAll("\\n", "<br>") + "</html>");
			returnButton.setVisible(true);
			currentLevel = 1;
			playerWin = false;
			for(JButton button : buttons)
				button.setEnabled(false);
			lblDamageDealt.setVisible(false);
		}			
	}
	
	public void isEnemyDead() {
		if(enemy.getEnemyHP() <= 0) {
			returnButton.setText("<html>" + youWon.replaceAll("\\n", "<br>") + "</html>");
			returnButton.setVisible(true);
			playerWin = true;
			for(JButton button : buttons)
				button.setEnabled(false);	
			lblDamageDealt.setVisible(false);
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
	
}

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
import javax.swing.JLabel;

import main.GameWindow;
import constants.*;
import inventory.Storage;

@SuppressWarnings({"serial"})
public class FightScene extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Weapon weapon = new Weapon();
	private Storage s = Storage.getInstance();
	private JLabel lblPlayerHP, lblEnemyHP, lblDamageDealt, lblExp, lblLevelUp, lblEnemyName, lblReward, lblBubble;
	private JButton returnButton, attackButton, menuButton, turnButton, levelUpHPButton, levelUpStrButton, 
			ability1, ability2, ability3, ability4, yesAdd, noAdd;
	private JButton[] buttons = new JButton[] {attackButton, menuButton, turnButton, ability1, ability2, ability3, ability4};
	private JButton[] actionButtons = new JButton[] {attackButton, ability1, ability2, ability3, ability4};
	String youLost = "You\nLost";
	String youWon = "You\nWon";
	private Random rand = new Random();
	private int enemySelectRand = rand.nextInt(2), weaponWon;
	private int currentLevel, currentStage, turnCounter, abilityID, enemiesDefeated, rendLeft, 
			weakLeft, poisonLeft, bubbleAmount, wallLeft;
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
	private Image swingCard, rendCard, hardenCard, stunCard, riposteCard, whirlwindCard, currentCard,
			decapCard, weakenCard, attackCard, bubbleCard, healCard, blockCard, poisonCard,
			explosiveCard, confuseCard, pummelCard, tendonCard, stealCard, breakerCard, shieldCard, fortifyCard;
	private JButton activeBag[] = new JButton[8];
	private boolean isHovering, ab1Used, ab2Used, ab3Used, ab4Used, legendary1Used, legendary2Used, legendary3Used,
			legendary4Used;
	
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
			
			if(s.gameMode == 0) {
				player.setMaxHP(player.getStoryMaxHP());
				player.setHp(player.getMaxHP());
				player.setStrength(player.getStoryStrength());
				player.setLevelCap(player.getStoryCap());
				player.setExp(player.getStoryExp());
				player.setLevel(player.getStoryLevel());			
			}
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
        
        checkButton();
        
        switch (command) {
        case "Main menu":
            window.showHomeScreen();
            break;
        case "Return":
            if (playerWin) {
				if(currentLevel == 7 && s.gameMode == 0) {
					player.setUnlockedStage(player.getUnlockedStage() + 1);
					currentLevel = 1;					
				}
				else if(currentLevel == 11 && s.gameMode == 1) {
					player.setUnlockedStage(player.getUnlockedStage() + 1);
					currentLevel = 1;	
					s.gameLevel++;
				}
				else
					currentLevel++;
            }
			for(int i = 0; i < activeBag.length; i++) {
				activeBag[i].removeActionListener(this); 
				Player.activeBag[i] = activeBag[i];
			}
			if(s.gameMode == 0)
				window.showLevelSelector(currentLevel);
			else
				window.showFunMode(currentLevel);
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
        	if(rand.nextInt(3) != 0) {
        		lblDamageDealt.setText("Harden activated");          
                hardenActive = true;
        	}
        	else
        		lblDamageDealt.setText("Failed to harden body"); 
            turnCounter -= s.harden.getAbilityCost();
            enableActionButtons();
            break;
        case "Whirlwind":
        	abilityID = s.whirlwind.getID();
            turnCounter -= s.whirlwind.getAbilityCost();
            doAttack();
            break;
        case "Weaken":
        	if(rand.nextInt(4) != 0) {
        		weakLeft = 2;
            	lblDamageDealt.setText("Enemy weakened");
        	}      	
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
        	turnCounter -= s.stun.getAbilityCost();
        	enableActionButtons();
        	break;
        case "Bubble":
        	if(player.getBubble() < 0)
        		player.setBubble(0);
        	bubbleAmount = s.bubble.getAttackPower() + player.getBubble();
        	turnCounter -= s.bubble.getAbilityCost();      	
        	player.setBubble(s.bubble.getAttackPower() + player.getBubble());
        	lblDamageDealt.setText("Surrounded player with a protective bubble");
        	enableActionButtons();
        	lblBubble.setText("" + player.getBubble());
        	break;
        case "Heal":
        	turnCounter -= s.heal.getAbilityCost();
        	player.increaseHP(s.heal.getAttackPower() + player.getLevel());
        	if (player.getHp() > player.getMaxHP())
        		player.setHp(player.getMaxHP());
        	player.playerShowHP(lblPlayerHP);
	        lblPlayerHP.repaint();
        	lblDamageDealt.setText("Player healed");
        	enableActionButtons();
        	break;
        case "Block":
        	turnCounter -= s.block.getAbilityCost();
        	if(rand.nextInt(3) == 0) {
        		shieldActive = false;
        		lblDamageDealt.setText("Block unsucessful");
        	}
        	else {
        		shieldActive = true;
        		lblDamageDealt.setText("Preparing to block enemy attack");
        	}
        	enableActionButtons();
        	break;
        case "PoisonSlash":
        	lblDamageDealt.setText("Poison applied to enemy");
        	poisonLeft = 3;
        	turnCounter -= s.poisonSlash.getAbilityCost();
        	enableActionButtons();
        	playerAttack = true;
        	break;
        case "ExplosiveAttack":
        	turnCounter -= s.explosiveAttack.getAbilityCost();
        	if(rand.nextInt(2) == 0)
        		explosionActive = true;
        	doAttack();
        	break;
        case "Confuse":
        	if(rand.nextInt(3) == 0 ) {
	        	stunActive = true;
	        	lblDamageDealt.setText("Confuse succeeded");
	        	enemyConfused = true;
        	}
        	else
        		lblDamageDealt.setText("Confuse failed");
        	turnCounter -= s.confuse.getAbilityCost();
        	enableActionButtons();
        	break;
        case "Pummel":
        	turnCounter -= s.pummel.getAbilityCost();
        	abilityID = s.pummel.getID();
        	doAttack();
        	if(rand.nextInt(2) == 0)
        		stunActive = true;
        	break;
        case "TendonCutter":
        	turnCounter -= s.tendonCutter.getAbilityCost();
        	abilityID = s.tendonCutter.getID();
        	doAttack();
        	weakLeft = 1;
        	break;
        case "LifeSteal":
        	turnCounter -= s.lifeSteal.getAbilityCost();
        	abilityID = s.lifeSteal.getID();
        	doAttack();
        	break;
        case "GroundBreaker":
        	turnCounter -= s.groundBreaker.getAbilityCost();
        	abilityID = s.groundBreaker.getID();
        	doAttack();
        	break;
        case "ShieldWall":
        	turnCounter -= s.shieldWall.getAbilityCost();
        	wallLeft = 3;
        	break;
        case "FortifiedAttack":
        	turnCounter -= s.fortifiedAttack.getAbilityCost();
        	if(player.getBubble() < 0)
        		player.setBubble(0);
        	bubbleAmount = s.fortifiedAttack.getAttackPower() + player.getBubble();
        	player.setBubble(s.fortifiedAttack.getAttackPower() + player.getBubble());
        	lblBubble.setText("" + player.getBubble());
        	abilityID = s.fortifiedAttack.getID();
        	doAttack();
        	break;
        case "Turn":
            enemyAttack();       
            turnCounter = player.getMana();    
            ab1Used = ab2Used = ab3Used = ab4Used = false;
            if(wallLeft > 0) {
            	if(player.getBubble() < 0)
            		player.setBubble(0);
            	bubbleAmount = s.shieldWall.getAttackPower() + player.getBubble();
            	player.setBubble(s.shieldWall.getAttackPower() + player.getBubble());
            	lblDamageDealt.setText("Surrounded player with a protective bubble");
            	lblBubble.setText("" + player.getBubble());
            	wallLeft--;
            }    
            enableActionButtons();
            break;
        case "HP up":
            player.increaseMaxHP();
            disableLevelUpComponents();
            player.playerShowHP(lblPlayerHP);
            lblPlayerHP.repaint(); 
            if(s.gameMode == 0)
    			player.setStoryMaxHP(player.getMaxHP());
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
            if(s.gameMode == 0)
    			player.setStoryStrength(player.getStrength());
            if(!gameOver) {
            	enableActionButtons();
            	turnButton.setEnabled(true);
            }             
            if(gameOver)
            	returnButton.setVisible(true);
            break;       
        case "Yes":
        	for(int i = 0; i < activeBag.length; i++) {
        		activeBag[i].removeActionListener(this);
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
        	break;
        case "remove0":
        case "remove1":
        case "remove2":
        case "remove3":
        case "remove4":
        case "remove5":
        case "remove6":
        case "remove7":
        	int index = Integer.parseInt(command.substring(6));  
        	addWeapon(index);
        	weaponWon = 0;
			lblReward.setVisible(false);			
        	break;
        case "inv0":
        case "inv1":
        case "inv2":
        case "inv3":
        case "inv4":
        case "inv5":
        case "inv6":
        case "inv7":
        	int index2 = Integer.parseInt(command.substring(3));
        	if(activeBag[index2].getIcon() != null)
        		itemAction(index2);
        	break;
        }
	}
	
	private void checkButton() {
		if(ability1.getModel().isArmed()) {
			ab1Used = true;
			if(ability1.getActionCommand() == "GroundBreaker" || ability1.getActionCommand() == "ShieldWall" ||
					ability1.getActionCommand() == "FortifiedAttack")
				legendary1Used = true;
		}
		else if(ability2.getModel().isArmed()) {
			ab2Used = true;
			if(ability2.getActionCommand() == "GroundBreaker" || ability2.getActionCommand() == "ShieldWall" ||
					ability2.getActionCommand() == "FortifiedAttack")
				legendary2Used = true;
		}
		else if(ability3.getModel().isArmed()) {
			ab3Used = true;
			if(ability3.getActionCommand() == "GroundBreaker" || ability3.getActionCommand() == "ShieldWall" ||
					ability3.getActionCommand() == "FortifiedAttack")
				legendary3Used = true;
		}
		else if(ability4.getModel().isArmed()) {
			ab4Used = true;
			if(ability4.getActionCommand() == "GroundBreaker" || ability4.getActionCommand() == "ShieldWall" ||
					ability4.getActionCommand() == "FortifiedAttack")
				legendary4Used = true;
		}
	}
	
	// Add weapon to inventory by switching out the current inventory slot
	private void addWeapon(int index) {		
		if(weaponWon > 0) {
			
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
				activeBag[i].removeActionListener(this); 
				activeBag[i].setActionCommand("inv" + i);
				activeBag[i].addActionListener(this);
			}			
		}
	}
	
	// Set active weapon
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
	
	// Inventory item actions
	private void itemAction(int x) {
			switch (activeBag[x].getName()) {
		    case "Health":
	        	activeBag[x].setIcon(null);
		        player.increaseHP(5);
		        if(player.getHp() > player.getMaxHP())
		        	player.setHp(player.getMaxHP());
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
	
	// Check if player is dead
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
			for(int i = 0; i < activeBag.length; i++) {
				activeBag[i].setIcon(null);
				activeBag[i].setName("");
			}
		}			
	}
	
	// Check if enemy is dead (also give player rewards if enemy died)
	public void isEnemyDead() {
		if(enemy.getHp() <= 0) {
			legendary1Used = legendary2Used = legendary3Used = legendary4Used = false;
			enemyDead = true;
			rendLeft = 0;
			
			// Normal enemy rewards
			player.gainCoin(enemy.getCoinValue());
			lblReward.setText("You got " + enemy.getCoinValue() + " coins");
			if(rand.nextInt(15) != 0 && !bossActive) {								
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
					player.setStoryExp(player.getExp());
					if(player.getExp() >= player.getLevelCap()) {	// Check if player has enough EXP to level up
						if(s.gameMode == 0)
							player.levelUp(0);
						else
							player.levelUp(1);
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
				if(bossActive && rand.nextInt(3) != 0) {
					switch(s.gameMode) {
					case 0:
						switch(s.gameLevel) {
						case 1:
							if(currentStage == 1) {
								lblReward.setText("You got " + s.silverAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 2;
							}
							else if (currentStage == 2) {
								lblReward.setText("You got " + s.goldAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 3;
							}
							else {
								lblReward.setText("You got " + s.steelAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 4;
							}
							break;
						case 2:
							if(currentStage == 1) {
								lblReward.setText("You got " + s.copperAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 5;
							}
							else if (currentStage == 2) {
								lblReward.setText("You got " + s.titaniumAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 6;
							}
							else {
								lblReward.setText("You got " + s.fieryAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 7;
							}
							break;
						case 3:
							if(currentStage == 1) {
								lblReward.setText("You got " + s.moltenAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 8;
							}
							else if (currentStage == 2) {
								lblReward.setText("You got " + s.waterAxe.getWeaponName() + ". Add it to inv?");
								weaponWon = 9;
							}
							else {
								if(rand.nextInt(2) == 0) {
									lblReward.setText("You got 1x Bottled Lava");
									s.bottledLava.setAmount(s.bottledLava.getAmount() + 1);
								}
								else {
									lblReward.setText("You got 1x Bottled Water");
									s.bottledWater.setAmount(s.bottledWater.getAmount() + 1);
								}
									
							}
							break;
						}
						break;
					case 1:
						switch(s.gameLevel) {
						case 1:
							lblReward.setText("You got " + s.steelAxe.getWeaponName() + ". Add it to inv?");
							weaponWon = 4;
							break;
						case 2:
							lblReward.setText("You got " + s.fieryAxe.getWeaponName() + ". Add it to inv?");
							weaponWon = 7;
							break;
						case 3:
							lblReward.setText("You got " + s.waterAxe.getWeaponName() + ". Add it to inv?");
							weaponWon = 9;
							break;
						}
					}									
					yesAdd.setVisible(true);
					noAdd.setVisible(true);					
				}
				
				lblReward.setVisible(true);
				returnButton.setText("<html>" + youWon.replaceAll("\\n", "<br>") + "</html>");
				returnButton.setVisible(true);
				playerWin = true;		// Will unlock the next stage
				for(JButton button : buttons)
					button.setEnabled(false);	
				lblDamageDealt.setVisible(false);
								
				if (player.getLevel() <= 15) {	// Check if player is max level
					player.increaseExp(enemy.getExpValue());	// Add EXP to player
					player.setStoryExp(player.getExp());
					if(player.getExp() >= player.getLevelCap()) {	// Check if player has enough EXP to level up
						if(s.gameMode == 0)
							player.levelUp(0);
						else
							player.levelUp(1);
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
	
	// Enable or disable buttons based on remaining energy points and/or if the ability was used
	private void enableActionButtons() {		
		if(enemiesDefeated != 3)
			for(JButton button : actionButtons)
				button.setEnabled(true);
		
		if(turnCounter < s.getAttackButtonCost())
			attackButton.setEnabled(false);	
		for (JButton button : Arrays.asList(ability1, ability2, ability3, ability4)) {
			
			if(button == ability1) {
				if(ab1Used || legendary1Used) {
					button.setEnabled(false);
					continue;
				}
			}
			else if(button == ability2) {
				if(ab2Used || legendary2Used) {
					button.setEnabled(false);
					continue;
				}
			}
			else if(button == ability3) {
				if(ab3Used || legendary3Used) {
					button.setEnabled(false);
					continue;
				}
			}
			else if(button == ability4) {
				if(ab4Used || legendary4Used) {
					button.setEnabled(false);
					continue;
				}
			}

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
		        case "Bubble":
		        	button.setEnabled(turnCounter >= s.bubble.getAbilityCost());
		        	break;
		        case "Heal":
		        	button.setEnabled(turnCounter >= s.heal.getAbilityCost());
		        	break;
		        case "Block":
		        	button.setEnabled(turnCounter >= s.block.getAbilityCost());
		        	break;
		        case "PoisonSlash":
		        	button.setEnabled(turnCounter >= s.poisonSlash.getAbilityCost());
		        	break;
		        case "ExplosiveAttack":
		        	button.setEnabled(turnCounter >= s.explosiveAttack.getAbilityCost());
		        	break;
		        case "Confuse":
		        	button.setEnabled(turnCounter >= s.confuse.getAbilityCost());
		        	break;
		        case "Pummel":
		        	button.setEnabled(turnCounter >= s.pummel.getAbilityCost());
		        	break;
		        case "TendonCutter":
		        	button.setEnabled(turnCounter >= s.tendonCutter.getAbilityCost());
		        	break;
		        case "LifeSteal":
		        	button.setEnabled(turnCounter >= s.lifeSteal.getAbilityCost());
		        	break;
		        case "GroundBreaker":
		        	button.setEnabled(turnCounter >= s.groundBreaker.getAbilityCost());
		        	break;
		        case "ShieldWall":
		        	button.setEnabled(turnCounter >= s.shieldWall.getAbilityCost());
		        	break;
		        case "FortifiedAttack":
		        	button.setEnabled(turnCounter >= s.fortifiedAttack.getAbilityCost());
		        	break;
		    }
		}
		
		for(JButton button : actionButtons)
			button.setVisible(true);
	}	
	
	// Enemy attack function
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
			lblDamageDealt.setText("Enemy got hit by Poison for: " + temp + " damage");
			lblDamageDealt.repaint();
			isEnemyDead();
			poisonLeft--;			
		}
		
		// Since Rend & Poison activate before the enemy attack, check if enemy died
		if(!enemyDead && !stunActive) {
			t.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	int temp = rand.nextInt(1, enemy.getStrength() + 1);
	            	enemyAttack = true;
	            	
	            	// Check if enemy attack missed
	    			if(abilityID != 3 && enemy.enemyAttack() && !enemyConfused) {
	    				// Debuff effects
	    				if(hardenActive) {
	    					temp = temp / 2;
	    					if(weakLeft > 0) {						
	    						temp = temp / 3;
	    						if(temp == 0 && player.getBubble() > 0) {
	    							temp = 1;
	    							player.setBubble(player.getBubble() - temp);
	    							if(player.getBubble() < 0)
	    								player.playerLoseHP(player.getBubble() * player.getBubble());
	    						}
	    						else if (player.getBubble() > 0)
		    						bubbleHit(temp);
	    						else if(temp == 0)
	    							player.playerLoseHP(1);
	    						else
	    							player.playerLoseHP(temp);
	    						weakLeft--;
	    					}
	    					else if (player.getBubble() > 0)
	    						bubbleHit(temp);
	    					else
	    						player.playerLoseHP(temp);	// Harden is active, enemy deals 50% dmg
	    				}
	    				else if(shieldActive)
	    					temp = 0;
	    				else if(weakLeft > 0) {
	    					temp = temp / 3;
	    					if (player.getBubble() > 0)
	    						bubbleHit(temp);
	    					else if(temp == 0)
    							player.playerLoseHP(1);
    						else
    							player.playerLoseHP(temp);
	    					weakLeft--;
	    				}
	    				else if (player.getBubble() > 0)
    						bubbleHit(temp);
	    				else if(!shieldActive)
	    					player.playerLoseHP(temp);
	    					    				
	    				if(player.getBubble() < 0)
	    					player.setBubble(0);
	    				lblBubble.setText("" + player.getBubble());
	    				lblBubble.repaint();
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
		if(stunActive && !enemyConfused) {
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
        enableActionButtons();
	}
	
	// If bubble is active, execute this function so the attack hits the bubble instead of the player
	private void bubbleHit(int temp) {
		player.setBubble(player.getBubble() - temp);
		if(player.getBubble() < 0) {
			temp = player.getBubble() * (-1);
			player.playerLoseHP(temp);
		}
	}
	
	// Player attack function
	private void playerAttack(){
		// Check if player attack missed
		if(player.playerAttack()) {
			int x, y = 0, z = 0;
			
			// Check if ability was used
			if(abilityID != 0) {
				switch(abilityID) {
				case 1:
					y = s.overheadSwing.getAttackPower();
					break;
				case 2:
					if(rand.nextInt(10) == 0) {
						y = 999;
						z = y;
					}
					else {
						y = s.decapitate.getAttackPower();					
						z = 1;
					}
					break;
				case 6:
					y = s.whirlwind.getAttackPower();					
					break;
				case 13:
					y = s.explosiveAttack.getAttackPower();
					break;
				case 15:
					y = s.pummel.getAttackPower();
					break;
				case 16:
					y = s.tendonCutter.getAttackPower();
					break;
				case 17:
					y = s.lifeSteal.getAttackPower();
					break;
				case 18:
					if(rand.nextInt(10) == 0)
						y = 999;
					else
						y = s.groundBreaker.getAttackPower();
					break;
				case 20:
					y = s.fortifiedAttack.getAttackPower();					
					break;
				}
				if(abilityID != 2)
					z = y;
				x = rand.nextInt(z, player.getStrength() / 2 + weapon.getWeaponDamage() + y + player.getTempStr() / 2 + 1); // Ability attack
				if(abilityID == 6)	// Whirlwind
					for(int i = 1; i < 3; i++)
						x = x + (player.getStrength() / 2 + weapon.getWeaponDamage() + y + player.getTempStr() / 2 + 1);					
				if(abilityID == 17) { // Life steal heal effect
					player.increaseHP(x / 2);
					if(player.getHp() > player.getMaxHP())
						player.setHp(player.getMaxHP());				
					player.playerShowHP(lblPlayerHP);
			        lblPlayerHP.repaint();
				}
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
	
	// Set abilities to buttons
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
	        case 9:
	            button.setActionCommand("Bubble");
	            button.setIcon(new ImageIcon(bubbleBtn));
	            break;
	        case 10:
	            button.setActionCommand("Heal");
	            button.setIcon(new ImageIcon(healBtn));
	            break;
	        case 11:
	            button.setActionCommand("Block");
	            button.setIcon(new ImageIcon(blockBtn));
	            break;
	        case 12:
	            button.setActionCommand("PoisonSlash");
	            button.setIcon(new ImageIcon(poisonBtn));
	            break;
	        case 13:
	            button.setActionCommand("ExplosiveAttack");
	            button.setIcon(new ImageIcon(explosiveBtn));
	            break;
	        case 14:
	            button.setActionCommand("Confuse");
	            button.setIcon(new ImageIcon(confuseBtn));
	            break;
	        case 15:
	            button.setActionCommand("Pummel");
	            button.setIcon(new ImageIcon(pummelBtn));
	            break;
	        case 16:
	            button.setActionCommand("TendonCutter");
	            button.setIcon(new ImageIcon(tendonBtn));
	            break;
	        case 17:
	            button.setActionCommand("LifeSteal");
	            button.setIcon(new ImageIcon(stealBtn));
	            break;
	        case 18:
	            button.setActionCommand("GroundBreaker");
	            button.setIcon(new ImageIcon(breakerBtn));
	            break;
	        case 19:
	            button.setActionCommand("ShieldWall");
	            button.setIcon(new ImageIcon(shieldBtn));
	            break;
	        case 20:
	            button.setActionCommand("FortifiedAttack");
	            button.setIcon(new ImageIcon(fortifyBtn));
	            break;
	    }
	    button.addActionListener(this);
	    button.addMouseListener(this);
	    button.addMouseMotionListener(this);
	    button.setBounds(x, y, 150, 100);
	    add(button);
	}

	// Initialize all components
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
		
		lblBubble = new JLabel();
		lblBubble.setFont(s.font2);
		lblBubble.setText("" + player.getBubble());
		lblBubble.setForeground(Color.BLACK);
		lblBubble.setBounds(1010, 240, 100, 30);
		add(lblBubble);
		
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
		lblDamageDealt.setFont(s.font4);
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
        menuButton.setBounds(50, 25, 100, 50);
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
        attackButton.setBounds(920, 570, 150, 100);
        add(attackButton);
        
        ability1 = new JButton();
        configureAbilityButton(ability1, player.getAbility1ID(), 200, 570);
                
        ability2 = new JButton();
        configureAbilityButton(ability2, player.getAbility2ID(), 380, 570);
                
        ability3 = new JButton();
        configureAbilityButton(ability3, player.getAbility3ID(), 560, 570);       
        
        ability4 = new JButton();
        configureAbilityButton(ability4, player.getAbility4ID(), 740, 570);   
        
        turnButton = new JButton();
        turnButton.setActionCommand("Turn");
        turnButton.setIcon(new ImageIcon(turnBtn));
        turnButton.addActionListener(this);
        turnButton.setBounds(870, 500, 100, 50);
        add(turnButton);
        
        // Inventory
        for(int i = 0; i < activeBag.length; i++) {
        	activeBag[i] = Player.activeBag[i];
        	activeBag[i].removeActionListener(this);
        	activeBag[i].setActionCommand("inv" + i);
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
        buttons[6] = ability4;
        
        actionButtons[0] = attackButton;
        actionButtons[1] = ability1;
        actionButtons[2] = ability2;
        actionButtons[3] = ability3;
        actionButtons[4] = ability4;
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
		float hpProc, hpProc2, shieldProc;
		int bubbleLoc, width, width2, width3;
	    
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
//	    	enableActionButtons();
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
//	    	enableActionButtons();
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
		
		//Player shield bar
	    g.setColor(Color.WHITE);
	    g.fillRect(1000, 245, 200, 20);
	    if(player.getBubble() > 0)
	    	shieldProc = (float)player.getBubble() / bubbleAmount;
	    else
	    	shieldProc = 0;
	    width3 = (int)(shieldProc * 200);
		width3 = (width3 / 10) * 10;	
		if (width3 < 10) {
		     width3 = 0;
		}	
		g.setColor(Color.GRAY);
		g.fillRect(1000, 245, width3, 20);
	    
	    // Mana bubbles
	    bubbleLoc = 1000;
	    for(int i = 1; i <= player.getMana(); i++) {
	    	g.setColor(Color.BLACK);
		    g.drawOval(bubbleLoc, 220, 20, 20);
		    bubbleLoc += 30;
	    }
	    bubbleLoc = 1000;
	    for(int i = 1; i <= turnCounter; i++) {
	    	g.setColor(new Color(255, 200, 0));
		    g.fillOval(bubbleLoc, 220, 20, 20);
		    bubbleLoc += 30;
	    }
	    
	    if(isHovering && currentCard != null) {
	    	g.drawImage(currentCard, 500, 200, this);
	    }
	    
	}

	// Used for showing ability card upon hovering over the ability button
	@Override
	public void mouseEntered(MouseEvent e) {
		
		if (e.getSource() instanceof JButton) {
	        String actionCommand = ((JButton) e.getSource()).getActionCommand();
	        switch (actionCommand) {
	        case "Swing":
	        	currentCard = swingCard;
	        	break;
	        case "Attack":
	        	currentCard = attackCard;
	        	break;
	        case "Harden":
	        	currentCard = hardenCard;
	        	break;
	        case "Stun":
	        	currentCard = stunCard;
	        	break;
	        case "Riposte":
	        	currentCard = riposteCard;
	        	break;
	        case "Rend":
	        	currentCard = rendCard;
	        	break;
	        case "Whirlwind":
	        	currentCard = whirlwindCard;
	        	break;
	        case "Decapitate":
	        	currentCard = decapCard;
	        	break;
	        case "Weaken":
	        	currentCard = weakenCard;
	        	break;
	        case "Bubble":
	        	currentCard = bubbleCard;
	        	break;
	        case "Heal":
	        	currentCard = healCard;
	        	break;
	        case "Block":
	        	currentCard = blockCard;
	        	break;
	        case "PoisonSlash":
	        	currentCard = poisonCard;
	        	break;
	        case "ExplosiveAttack":
	        	currentCard = explosiveCard;
	        	break;
	        case "Confuse":
	        	currentCard = confuseCard;
	        	break;
	        case "Pummel":
	        	currentCard = pummelCard;
	        	break;
	        case "TendonCutter":
	        	currentCard = tendonCard;
	        	break;
	        case "LifeSteal":
	        	currentCard = stealCard;
	        	break;
	        case "GroundBreaker":
	        	currentCard = breakerCard;
	        	break;
	        case "ShieldWall":
	        	currentCard = shieldCard;
	        	break;
	        case "FortifiedAttack":
	        	currentCard = fortifyCard;
	        	break;
	        }
	        isHovering = true;	
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isHovering = false;  
		currentCard = null;
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

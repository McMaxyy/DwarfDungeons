 package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Player;
import constants.Weapon;
import main.GameWindow;

@SuppressWarnings("serial")
public class Merchant extends JPanel implements ActionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private Weapon weapon = new Weapon();
	private JButton healthPot, ability1, active1, active2, active3, 
			returnButton, manaIncrease, upgradeWeapon;
	private JLabel coins;
	private String a1, a2, a3, abilityText;
	private int abilityID, currentLevel, x;
	private static int hpPrice, mpPrice, weaponPrice, abPrice;
	private float newPrice;
	private Random rand = new Random();
	private boolean playerPurchased, hpPurchased, mpPurchased, weaponPurchased, abPurchased;
	private int[] abilityIds = {player.getAbility1ID(), player.getAbility2ID(), player.getAbility3ID()};
	private String[] abilities = new String[3];
	
	public Merchant(GameWindow window, int levelIndex){
		this.window = window;
		currentLevel = levelIndex;
		x = rand.nextInt(1,7);
		playerPurchased = hpPurchased = mpPurchased = weaponPurchased = abPurchased = false;
		
		hpPrice = rand.nextInt(7, 11);
		mpPrice = rand.nextInt(15, 21);
		weaponPrice = rand.nextInt(25, 31);
		abPrice = rand.nextInt(35, 46);

		switch(player.getMerchantVisits()) {
		case 1:
			break;
		case 2:
			setPrice(1.2f);
			break;
		case 3:
			setPrice(1.5f);
			break;
		case 4:
			setPrice(2);
			break;
		}
		
		for (int i = 0; i < 3; i++) {
		    switch (abilityIds[i]) {
		        case 1:
		            abilities[i] = "Swing";
		            break;
		        case 2:
		            abilities[i] = "Decapitate";
		            break;
		        case 3:
		            abilities[i] = "Riposte";
		            break;
		        case 4:
		            abilities[i] = "Rend";
		            break;
		        case 5:
		            abilities[i] = "Harden";
		            break;
		        case 6:
		            abilities[i] = "Whirlwind";
		            break;
		        case 7:
		            abilities[i] = "Weaken";
		            break;
		        case 8:
		            abilities[i] = "Stun";
		            break;
		    }
		}

		a1 = abilities[0];
		a2 = abilities[1];
		a3 = abilities[2];		
		
		setLayout(null);
		
		initComponents();
		checkAvailability();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch (command) {
	    case "Return":
	    	if(!playerPurchased)
	    		player.decreaseMerchantVisits();
	        currentLevel++;
	        window.showLevelSelector(currentLevel);
	        break;
	    case "Mana":
	        player.increaseMana(1);
	        player.playerLoseCoin(mpPrice);
	        mpPurchased = true;
	        checkAvailability();       
	        playerPurchased = true;
	        break;
	    case "HP":
	        player.increaseHP(5);
	        if(player.getPlayerHP() > player.getPlayerMaxHP())
	        	player.setPlayerHP(player.getPlayerMaxHP());
	        player.playerLoseCoin(hpPrice);
	        hpPurchased = true;
	        checkAvailability();        
	        playerPurchased = true;
	        break;
	    case "Weapon":
	        weapon.upgradeWeapon(1);
	        player.playerLoseCoin(weaponPrice);
	        weaponPurchased = true;
	        checkAvailability();        
	        playerPurchased = true;
	        break;
	    case "ab1":
	        active1.setVisible(true);
	        active2.setVisible(true);
	        active3.setVisible(true);
	        setAbilityID();
	        break;
	    case "ac1":
	    	player.playerLoseCoin(abPrice);
	    	abPurchased = true;
	    	checkAvailability();
	    	disableAbilityButtons();
	        player.setAbility1ID(abilityID);	        
	        playerPurchased = true;
	        break;
	    case "ac2":
	    	player.playerLoseCoin(abPrice);
	    	abPurchased = true;
	    	checkAvailability();
	    	disableAbilityButtons();
	        player.setAbility2ID(abilityID);	        
	        playerPurchased = true;
	        break;
	    case "ac3":
	    	player.playerLoseCoin(abPrice);
	    	abPurchased = true;
	    	checkAvailability();
	    	disableAbilityButtons();
	        player.setAbility3ID(abilityID);	        
	        playerPurchased = true;
	        break;
		}			
	}
	
	private void checkAvailability() {
		if(player.getPlayerCoins() >= hpPrice && !hpPurchased && player.getPlayerHP() < player.getPlayerMaxHP())
			healthPot.setEnabled(true);
		else
			healthPot.setEnabled(false);
		
		if(player.getPlayerCoins() >= mpPrice && !mpPurchased && player.getMana() < 7)
			manaIncrease.setEnabled(true);
		else
			manaIncrease.setEnabled(false);
		
		if(player.getPlayerCoins() >= weaponPrice && !weaponPurchased)
			upgradeWeapon.setEnabled(true);
		else
			upgradeWeapon.setEnabled(false);
		
		if(player.getPlayerCoins() >= abPrice && !abPurchased)
			ability1.setEnabled(true);
		else
			ability1.setEnabled(false);
		
		coins.setText("Available coins: " + player.getPlayerCoins());
	}
	
	private void setPrice(float x) {
		newPrice = hpPrice * x;
		hpPrice = (int)newPrice;
		newPrice = mpPrice * x;
		mpPrice = (int)newPrice;
		newPrice = weaponPrice * x;
		weaponPrice = (int)newPrice;
		newPrice = abPrice * x;
		abPrice = (int)newPrice;
	}
	
	private void disableAbilityButtons() {
		active1.setEnabled(false);
        active2.setEnabled(false);
        active3.setEnabled(false);
        ability1.setEnabled(false);
	}

	private void setAbilityID() {		
		switch(ability1.getText()) {
		case "Swing":
			abilityID = 1;
			break;
		case "Decapitate":
			abilityID = 2;
			break;
		case "Riposte":
			abilityID = 3;
			break;
		case "Rend":
			abilityID = 4;
			break;
		case "Harden":
			abilityID = 5;
			break;
		case "Whirlwind":
			abilityID = 6;
			break;
		case "Weaken":
			abilityID = 7;
			break;
		case "Stun":
			abilityID = 8;
			break;
		}
	}
	
	private void setAbilityText() {
		switch(x) {
		case 1:
			abilityText = "Swing";
			break;
		case 2:
			abilityText = "Decapitate";
			break;
		case 3:
			abilityText = "Riposte";
			break;
		case 4:
			abilityText = "Rend";
			break;
		case 5:
			abilityText = "Harden";
			break;
		case 6:
			abilityText = "Whirlwind";
			break;
		case 7:
			abilityText = "Weaken";
			break;
		case 8:
			abilityText = "Stun";
			break;
		}
	}
	
	private void initComponents() {
		coins = new JLabel();
		coins.setText("Available coins: " + player.getPlayerCoins());
		coins.setBounds(200, 50, 200, 50);
		add(coins);
		
		returnButton = new JButton();
		returnButton.setActionCommand("Return");
		returnButton.setText("Return");
		returnButton.addActionListener(this);
		returnButton.setBounds(50, 50, 100, 50);
        add(returnButton);
		
		healthPot = new JButton();
		healthPot.setEnabled(false);
		healthPot.setActionCommand("HP");
		healthPot.setText("HP (-" + hpPrice + ")");
		healthPot.addActionListener(this);
		healthPot.setBounds(300, 450, 150, 100);
        add(healthPot);
        
        manaIncrease = new JButton();
        manaIncrease.setEnabled(false);
        manaIncrease.setActionCommand("Mana");
        manaIncrease.setText("Mana (-" + mpPrice + ")");
        manaIncrease.addActionListener(this);
        manaIncrease.setBounds(300, 290, 150, 100);
        add(manaIncrease);
        
        upgradeWeapon = new JButton();
        upgradeWeapon.setEnabled(false);
        upgradeWeapon.setActionCommand("Weapon");
        upgradeWeapon.setText("Weapon (-" + weaponPrice + ")");
        upgradeWeapon.addActionListener(this);
        upgradeWeapon.setBounds(300, 130, 150, 100);
        add(upgradeWeapon);
        
        ability1 = new JButton();
        ability1.setEnabled(false);
        ability1.setActionCommand("ab1");
        while(x == player.getAbility1ID() || x == player.getAbility2ID() || x == player.getAbility3ID())
        	x = rand.nextInt(1, 9);
        setAbilityText();
        ability1.setText(abilityText);
        ability1.addActionListener(this);
        ability1.setBounds(300, 560, 150, 100);
        add(ability1);
        
        active1 = new JButton();
        active1.setVisible(false);
        active1.setActionCommand("ac1");
        active1.setText(a1);
        active1.addActionListener(this);
        active1.setBounds(460, 560, 150, 100);
        add(active1);
        
        active2 = new JButton();
        active2.setVisible(false);
        active2.setActionCommand("ac2");
        active2.setText(a2);
        active2.addActionListener(this);
        active2.setBounds(720, 560, 150, 100);
        add(active2);
        
        active3 = new JButton();
        active3.setVisible(false);
        active3.setActionCommand("ac3");
        active3.setText(a3);
        active3.addActionListener(this);
        active3.setBounds(880, 560, 150, 100);
        add(active3);
	}
	
}

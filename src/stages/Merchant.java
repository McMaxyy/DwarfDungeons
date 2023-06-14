package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Player;
import constants.Weapon;
import inventory.Storage;
import main.GameWindow;

@SuppressWarnings("serial")
public class Merchant extends JPanel implements ActionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private Weapon weapon = new Weapon();
	private JButton healthPot, ability1, active1, active2, active3, active4, 
			returnButton, manaIncrease, upgradeWeapon;
	private JLabel coins;
	private String a1, a2, a3, a4, abilityText;
	private int abilityID, currentLevel, x;
	private static int hpPrice, mpPrice, weaponPrice, abPrice;
	private float newPrice;
	private Random rand = new Random();
	private boolean playerPurchased, hpPurchased, mpPurchased, weaponPurchased, abPurchased;
	private int[] abilityIds = {player.getAbility1ID(), player.getAbility2ID(), player.getAbility3ID(), player.getAbility4ID()};
	private String[] abilities = new String[4];
	private Storage s = Storage.getInstance();
	
	public Merchant(GameWindow window, int levelIndex){
		this.window = window;
		currentLevel = levelIndex;
		x = rand.nextInt(1,17);
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
		
		for (int i = 0; i < 4; i++) {
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
		        case 9:
		        	abilities[i] = "Bubble";
		        	break;
		        case 10:
		        	abilities[i] = "Heal";
					break;
				case 11:
					abilities[i] = "Block";
					break;
				case 12:
					abilities[i] = "Poison Slash";
					break;
				case 13:
					abilities[i] = "Explosive Attack";
					break;
				case 14:
					abilities[i] = "Confuse";
					break;
				case 15:
					abilities[i] = "Pummel";
					break;
				case 16:
					abilities[i] = "Tendon Cutter";
					break;
				case 17:
					abilities[i] = "Life Steal";
					break;
		    }
		}

		a1 = abilities[0];
		a2 = abilities[1];
		a3 = abilities[2];	
		a4 = abilities[3];
		
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
	        if(s.gameMode == 0)
	        	window.showLevelSelector(currentLevel);
	        else
	        	window.showFunMode(currentLevel);
	        break;
	    case "Mana":
	        player.increaseMana(1);
	        player.loseCoin(mpPrice);
	        mpPurchased = true;
	        checkAvailability();       
	        playerPurchased = true;
	        break;
	    case "HP":
	        player.increaseHP(5);
	        if(player.getHp() > player.getMaxHP())
	        	player.setHp(player.getMaxHP());
	        player.loseCoin(hpPrice);
	        hpPurchased = true;
	        checkAvailability();        
	        playerPurchased = true;
	        break;
	    case "Weapon":
	        weapon.upgradeWeapon(1);
	        player.loseCoin(weaponPrice);
	        weaponPurchased = true;
	        checkAvailability();        
	        playerPurchased = true;
	        break;
	    case "ab1":
	        active1.setVisible(true);
	        active2.setVisible(true);
	        active3.setVisible(true);
	        active4.setVisible(true);
	        setAbilityID();
	        break;
	    case "ac1":
	    	player.loseCoin(abPrice);
	    	abPurchased = true;
	    	checkAvailability();
	    	disableAbilityButtons();
	        player.setAbility1ID(abilityID);	        
	        playerPurchased = true;
	        break;
	    case "ac2":
	    	player.loseCoin(abPrice);
	    	abPurchased = true;
	    	checkAvailability();
	    	disableAbilityButtons();
	        player.setAbility2ID(abilityID);	        
	        playerPurchased = true;
	        break;
	    case "ac3":
	    	player.loseCoin(abPrice);
	    	abPurchased = true;
	    	checkAvailability();
	    	disableAbilityButtons();
	        player.setAbility3ID(abilityID);	        
	        playerPurchased = true;
	        break;
	    case "ac4":
	    	player.loseCoin(abPrice);
	    	abPurchased = true;
	    	checkAvailability();
	    	disableAbilityButtons();
	        player.setAbility4ID(abilityID);	        
	        playerPurchased = true;
	        break;
		}			
	}
	
	private void checkAvailability() {
		if(player.getCoins() >= hpPrice && !hpPurchased && player.getHp() < player.getMaxHP())
			healthPot.setEnabled(true);
		else
			healthPot.setEnabled(false);
		
		if(player.getCoins() >= mpPrice && !mpPurchased && player.getMana() < 7)
			manaIncrease.setEnabled(true);
		else
			manaIncrease.setEnabled(false);
		
		if(player.getCoins() >= weaponPrice && !weaponPurchased)
			upgradeWeapon.setEnabled(true);
		else
			upgradeWeapon.setEnabled(false);
		
		if(player.getCoins() >= abPrice && !abPurchased)
			ability1.setEnabled(true);
		else
			ability1.setEnabled(false);
		
		coins.setText("Available coins: " + player.getCoins());
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
        active4.setEnabled(false);
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
		case "Bubble":
			abilityID = 9;
			break;
		case "Heal":
			abilityID = 10;
			break;
		case "Block":
			abilityID = 11;
			break;
		case "Poison Slash":
			abilityID = 12;
			break;
		case "Explosive Attack":
			abilityID = 13;
			break;
		case "Confuse":
			abilityID = 14;
			break;
		case "Pummel":
			abilityID = 15;
			break;
		case "Tendon Cutter":
			abilityID = 16;
			break;
		case "Life Steal":
			abilityID = 17;
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
		case 9:
			abilityText = "Bubble";
			break;
		case 10:
			abilityText = "Heal";
			break;
		case 11:
			abilityText = "Block";
			break;
		case 12:
			abilityText = "Poison Slash";
			break;
		case 13:
			abilityText = "Explosive Attack";
			break;
		case 14:
			abilityText = "Confuse";
			break;
		case 15:
			abilityText = "Pummel";
			break;
		case 16:
			abilityText = "Tendon Cutter";
			break;
		case 17:
			abilityText = "Life Steal";
			break;
		}
	}
	
	private void initComponents() {
		coins = new JLabel();
		coins.setText("Available coins: " + player.getCoins());
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
        while(x == player.getAbility1ID() || x == player.getAbility2ID() || x == player.getAbility3ID() || x == player.getAbility4ID())
        	x = rand.nextInt(1, 18);
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
        active1.setBounds(560, 560, 150, 100);
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
        
        active4 = new JButton();
        active4.setVisible(false);
        active4.setActionCommand("ac4");
        active4.setText(a4);
        active4.addActionListener(this);
        active4.setBounds(1040, 560, 150, 100);
        add(active4);
	}
	
}

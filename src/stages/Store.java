package stages;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Player;
import inventory.Storage;
import main.GameWindow;

@SuppressWarnings("serial")
public class Store extends JPanel implements ActionListener{
	private GameWindow window;
	private Storage s = Storage.getInstance();
	private JButton returnButton;
	private Player player = new Player();
	private int xLoc1 = 220, yLoc1 = 318, xLoc2 = 220, yLoc2 = 422;
	private JButton bagW[] = new JButton[s.waterAxe.getID()];
	private JButton bagI[] = new JButton[s.biggerBomb.getID()];
	private JButton buyButtons[] = new JButton[9];
	private Image ironBtn, silverBtn, goldBtn, steelBtn, copperBtn, titanBtn, fieryBtn, bg,
			moltenBtn, waterBtn, healthBtn, shieldBtn, bombBtn, dartBtn, dynamiteBtn, bombsBtn;
	private JLabel weaponName[] = new JLabel[s.waterAxe.getID()];
    private JLabel itemName[] = new JLabel[s.biggerBomb.getID()];
    private JLabel buyName[] = new JLabel[9];
    private JLabel coins = new JLabel();
    private int entered = 1;
	
	public Store(GameWindow window) {
        this.window = window;
        
        setLayout(null);
        
        loadIcons();
        initComponents();
        checkAvailability();
        checkInventory();
	}
	
	private void checkAvailability() {
		// Check weapons
		if(player.getCoins() >= s.ironAxe.getBuyValue())
			buyButtons[0].setEnabled(true);
		else
			buyButtons[0].setEnabled(false);
		if(player.getCoins() >= s.steelAxe.getBuyValue())
			buyButtons[1].setEnabled(true);
		else
			buyButtons[1].setEnabled(false);
		if(player.getCoins() >= s.fieryAxe.getBuyValue())
			buyButtons[2].setEnabled(true);
		else
			buyButtons[2].setEnabled(false);
		
		// Check items
		if(player.getCoins() >= s.healthPot.getBuyValue())
			buyButtons[3].setEnabled(true);
		else
			buyButtons[3].setEnabled(false);
		if(player.getCoins() >= s.shield.getBuyValue())
			buyButtons[4].setEnabled(true);
		else
			buyButtons[4].setEnabled(false);
		if(player.getCoins() >= s.bomb.getBuyValue())
			buyButtons[5].setEnabled(true);
		else
			buyButtons[5].setEnabled(false);
		if(player.getCoins() >= s.poisonDart.getBuyValue())
			buyButtons[6].setEnabled(true);
		else
			buyButtons[6].setEnabled(false);
		if(player.getCoins() >= s.bigBomb.getBuyValue())
			buyButtons[7].setEnabled(true);
		else
			buyButtons[7].setEnabled(false);
		if(player.getCoins() >= s.biggerBomb.getBuyValue())
			buyButtons[8].setEnabled(true);
		else
			buyButtons[8].setEnabled(false);
		
		coins.setText("Available coins: " + player.getCoins());
		checkInventory();
		if(entered == 1)
			entered++;
	}
	
	private void checkInventory() {
		// Check for weapons
		if(s.ironAxe.getAmount() > 0 && bagW[0] == null)
			createWeaponInventory(s.ironAxe.getID());
		if(s.silverAxe.getAmount() > 0 && bagW[1] == null)
			createWeaponInventory(s.silverAxe.getID());
		if(s.goldAxe.getAmount() > 0 && bagW[2] == null)
			createWeaponInventory(s.goldAxe.getID());
		if(s.steelAxe.getAmount() > 0 && bagW[3] == null)
			createWeaponInventory(s.steelAxe.getID());	
		if(s.copperAxe.getAmount() > 0 && bagW[4] == null)
			createWeaponInventory(s.copperAxe.getID());
		if(s.titaniumAxe.getAmount() > 0 && bagW[5] == null)
			createWeaponInventory(s.titaniumAxe.getID());
		if(s.fieryAxe.getAmount() > 0 && bagW[6] == null)
			createWeaponInventory(s.fieryAxe.getID());	
		if(s.moltenAxe.getAmount() > 0 && bagW[7] == null)
			createWeaponInventory(s.moltenAxe.getID());
		if(s.waterAxe.getAmount() > 0 && bagW[8] == null)
			createWeaponInventory(s.waterAxe.getID());
		
		// Check for items
		if(s.healthPot.getAmount() > 0 && bagI[0] == null)
			createItemInventory(s.healthPot.getID());
		if(s.shield.getAmount() > 0 && bagI[1] == null)
			createItemInventory(s.shield.getID());
		if(s.bomb.getAmount() > 0 && bagI[2] == null)
			createItemInventory(s.bomb.getID());
		if(s.poisonDart.getAmount() > 0 && bagI[3] == null)
			createItemInventory(s.poisonDart.getID());
		if(s.bigBomb.getAmount() > 0 && bagI[4] == null)
			createItemInventory(s.bigBomb.getID());
		if(s.biggerBomb.getAmount() > 0 && bagI[5] == null)
			createItemInventory(s.biggerBomb.getID());

		if(s.ironAxe.getAmount() > 0)
			bagW[0].setEnabled(true);
		if(s.silverAxe.getAmount() > 0)
			bagW[1].setEnabled(true);
		if(s.goldAxe.getAmount() > 0)
			bagW[2].setEnabled(true);
		if(s.steelAxe.getAmount() > 0)
			bagW[3].setEnabled(true);	
		if(s.copperAxe.getAmount() > 0)
			bagW[4].setEnabled(true);
		if(s.titaniumAxe.getAmount() > 0)
			bagW[5].setEnabled(true);
		if(s.fieryAxe.getAmount() > 0)
			bagW[6].setEnabled(true);
		if(s.moltenAxe.getAmount() > 0)
			bagW[7].setEnabled(true);
		if(s.waterAxe.getAmount() > 0)
			bagW[8].setEnabled(true);
		
		// Check for items
		if(s.healthPot.getAmount() > 0)
			bagI[0].setEnabled(true);
		if(s.shield.getAmount() > 0)
			bagI[1].setEnabled(true);
		if(s.bomb.getAmount() > 0)
			bagI[2].setEnabled(true);
		if(s.poisonDart.getAmount() > 0)
			bagI[3].setEnabled(true);
		if(s.bigBomb.getAmount() > 0)
			bagI[4].setEnabled(true);
		if(s.biggerBomb.getAmount() > 0)
			bagI[5].setEnabled(true);
    	
	}
	
	private void createWeaponInventory(int x) {
		String temp = "weapon";
		bagW[x - 1] = new JButton();
		bagW[x - 1].setFocusable(false);
		bagW[x - 1].setContentAreaFilled(false);
		bagW[x - 1].setBorder(s.border);
		switch(x) {
		case 1:
			bagW[x - 1].setIcon(new ImageIcon(ironBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Iron<br>Axe (%d)</html>", s.ironAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);			
			break;
		case 2:
			bagW[x - 1].setIcon(new ImageIcon(silverBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Silver<br>Axe (%d)</html>", s.silverAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);
			break;
		case 3:
			bagW[x - 1].setIcon(new ImageIcon(goldBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Gold<br>Axe (%d)</html>", s.goldAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);
			break;
		case 4:
			bagW[x - 1].setIcon(new ImageIcon(steelBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Steel<br>Axe (%d)</html>", s.steelAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);
			break;
		case 5:
			bagW[x - 1].setIcon(new ImageIcon(copperBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Copper<br>Axe (%d)</html>", s.copperAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);
			break;
		case 6:
			bagW[x - 1].setIcon(new ImageIcon(titanBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Titanium<br>Axe (%d)</html>", s.titaniumAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);
			break;
		case 7:
			bagW[x - 1].setIcon(new ImageIcon(fieryBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Fiery<br>Axe (%d)</html>", s.fieryAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);
			break;
		case 8:
			bagW[x - 1].setIcon(new ImageIcon(moltenBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Molten<br>Axe (%d)</html>", s.moltenAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 40, 60, 40);
			add(weaponName[x - 1]);
			break;
		case 9:
			bagW[x - 1].setIcon(new ImageIcon(waterBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Water<br>Axe (%d)</html>", s.waterAxe.getSellValue()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 47, 60, 40);
			add(weaponName[x - 1]);
			break;
		}
		bagW[x - 1].setActionCommand(temp + x);
		bagW[x - 1].addActionListener(this);
		bagW[x - 1].setBounds(xLoc1, yLoc1, 60, 50);
		add(bagW[x - 1]);	
		weaponName[x - 1].setFont(s.font4);
		weaponName[x - 1].setForeground(Color.WHITE);
		xLoc1 += 70;		
	}
	
	private void createItemInventory(int x) {
		String temp = "item";
		bagI[x - 1] = new JButton();
		bagI[x - 1].setFocusable(false);
		bagI[x - 1].setContentAreaFilled(false);
		bagI[x - 1].setBorder(s.border);
		switch(x) {
		case 1:
			bagI[x - 1].setIcon(new ImageIcon(healthBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Health<br>potion<br>(%d)</html>", s.healthPot.getSellValue()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 42, 60, 40);		
			add(itemName[x - 1]);
			break;
		case 2:
			bagI[x - 1].setIcon(new ImageIcon(shieldBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Shield<br>(%d)</html>", s.shield.getSellValue()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 60, 40);			
			add(itemName[x - 1]);
			break;
		case 3:
			bagI[x - 1].setIcon(new ImageIcon(bombBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Bomb<br>(%d)</html>", s.bomb.getSellValue()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 60, 40);			
			add(itemName[x - 1]);
			break;
		case 4:
			bagI[x - 1].setIcon(new ImageIcon(dartBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Poison<br>dart<br>(%d)</html>", s.poisonDart.getSellValue()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 42, 60, 40);			
			add(itemName[x - 1]);
			break;
		case 5:
			bagI[x - 1].setIcon(new ImageIcon(dynamiteBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Dynamite<br>(%d)</html>", s.bigBomb.getSellValue()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 65, 40);			
			add(itemName[x - 1]);
			break;
		case 6:
			bagI[x - 1].setIcon(new ImageIcon(bombsBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Bombs<br>(%d)</html>", s.biggerBomb.getSellValue()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 60, 40);			
			add(itemName[x - 1]);
			break;
		}
		bagI[x - 1].setActionCommand(temp + x);
		bagI[x - 1].addActionListener(this);
		bagI[x - 1].setBounds(xLoc2, yLoc2, 60, 50);
		add(bagI[x - 1]);	
		itemName[x - 1].setFont(s.font4);
		itemName[x - 1].setForeground(Color.WHITE);
		xLoc2 += 70;			
	}
	
	private void loadIcons() {
		try {
			ironBtn = ImageIO.read(new File("res/Weapons/IronAxe.png"));
			silverBtn = ImageIO.read(new File("res/Weapons/SilverAxe.png"));
			goldBtn = ImageIO.read(new File("res/Weapons/GoldAxe.png"));
			steelBtn = ImageIO.read(new File("res/Weapons/SteelAxe.png"));
			copperBtn = ImageIO.read(new File("res/Weapons/CopperAxe.png"));
			titanBtn = ImageIO.read(new File("res/Weapons/TitaniumAxe.png"));
			fieryBtn = ImageIO.read(new File("res/Weapons/FieryAxe.png"));
			moltenBtn = ImageIO.read(new File("res/Weapons/MoltenAxe.png"));
			waterBtn = ImageIO.read(new File("res/Weapons/WaterAxe.png"));
			healthBtn = ImageIO.read(new File("res/Items/HealthPot.png"));
			shieldBtn = ImageIO.read(new File("res/Items/Shield.png"));
			bombBtn = ImageIO.read(new File("res/Items/Bomb.png"));
			dartBtn = ImageIO.read(new File("res/Items/Dart.png"));
			dynamiteBtn = ImageIO.read(new File("res/Items/Dynamite.png"));
			bombsBtn = ImageIO.read(new File("res/Items/Bombs.png"));
			bg = ImageIO.read(new File("res/Backgrounds/Merchant_BG.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initComponents(){
		int x = 220;
		int y = 526;
		
		for(int i = 0; i < buyButtons.length; i++) {
			buyButtons[i] = new JButton();
			buyName[i] = new JLabel();
			buyName[i].setFont(s.font4);
			buyName[i].setForeground(Color.WHITE);
			switch(i) {
			case 0:
				buyName[i].setText(String.valueOf(s.ironAxe.getBuyValue()));
				break;
			case 1:
				buyName[i].setText(String.valueOf(s.steelAxe.getBuyValue()));
				break;
			case 2:
				buyName[i].setText(String.valueOf(s.fieryAxe.getBuyValue()));
				break;
			case 3:
				buyName[i].setText(String.valueOf(s.healthPot.getBuyValue()));
				break;
			case 4:
				buyName[i].setText(String.valueOf(s.shield.getBuyValue()));
				break;
			case 5:
				buyName[i].setText(String.valueOf(s.bomb.getBuyValue()));
				break;
			case 6:
				buyName[i].setText(String.valueOf(s.poisonDart.getBuyValue()));
				break;
			case 7:
				buyName[i].setText(String.valueOf(s.bigBomb.getBuyValue()));
				break;
			case 8:
				buyName[i].setText(String.valueOf(s.biggerBomb.getBuyValue()));
				break;
			}			
			buyName[i].setBounds(x, y - 35, 60, 50);
			x += 70;
			add(buyName[i]);
		}
		
		for (JButton button : buyButtons) {
			button.setFocusable(false);
			button.addActionListener(this);
			button.setEnabled(false);
			button.setContentAreaFilled(false);
			button.setBorder(s.border);
			add(button);
		}
		
		buyButtons[0].setActionCommand("ironAxeB");
		buyButtons[1].setActionCommand("steelAxeB");
		buyButtons[2].setActionCommand("fieryAxeB");
		buyButtons[3].setActionCommand("healthB");
		buyButtons[4].setActionCommand("shieldB");
		buyButtons[5].setActionCommand("bombB");
		buyButtons[6].setActionCommand("dartB");
		buyButtons[7].setActionCommand("bigBombB");
		buyButtons[8].setActionCommand("biggerBombB");
		
		buyButtons[0].setIcon(new ImageIcon(ironBtn));
		buyButtons[1].setIcon(new ImageIcon(steelBtn));
		buyButtons[2].setIcon(new ImageIcon(fieryBtn));
		buyButtons[3].setIcon(new ImageIcon(healthBtn));
		buyButtons[4].setIcon(new ImageIcon(shieldBtn));
		buyButtons[5].setIcon(new ImageIcon(bombBtn));
		buyButtons[6].setIcon(new ImageIcon(dartBtn));
		buyButtons[7].setIcon(new ImageIcon(dynamiteBtn));
		buyButtons[8].setIcon(new ImageIcon(bombsBtn));
		
		x = 220;
		
		for (int i = 0; i < buyButtons.length; i++) {
		    buyButtons[i].setBounds(x, y, 60, 50);
		    x += 70;
		}		
		
		returnButton = new JButton();
		returnButton.setFocusable(false);
		returnButton.setText("Return");
		returnButton.setActionCommand("Return");
		returnButton.addActionListener(this);
		returnButton.setBounds(50, 25, 100, 50);
		add(returnButton);
		
		coins = new JLabel();
		coins.setText("Available coins: " + player.getCoins());
		coins.setFont(s.font2);
		coins.setForeground(Color.WHITE);
		coins.setBounds(220 , 190, 300, 30);
		add(coins);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch(command) {
		case "Return":
			window.showHomeScreen();
			break;
		// Sell weapon buttons
		case "weapon1":
			player.gainCoin(s.ironAxe.getSellValue());
			s.ironAxe.setAmount(s.ironAxe.getAmount() - 1);
			if(s.ironAxe.getAmount() == 0)
				bagW[0].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon2":
			player.gainCoin(s.silverAxe.getSellValue());
			s.silverAxe.setAmount(s.silverAxe.getAmount() - 1);
			if(s.silverAxe.getAmount() == 0)
				bagW[1].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon3":
			player.gainCoin(s.goldAxe.getSellValue());
			s.goldAxe.setAmount(s.goldAxe.getAmount() - 1);
			if(s.goldAxe.getAmount() == 0)
				bagW[2].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon4":
			player.gainCoin(s.steelAxe.getSellValue());
			s.steelAxe.setAmount(s.steelAxe.getAmount() - 1);
			if(s.steelAxe.getAmount() == 0)
				bagW[3].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon5":
			player.gainCoin(s.copperAxe.getSellValue());
			s.copperAxe.setAmount(s.copperAxe.getAmount() - 1);
			if(s.copperAxe.getAmount() == 0)
				bagW[4].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon6":
			player.gainCoin(s.titaniumAxe.getSellValue());
			s.titaniumAxe.setAmount(s.titaniumAxe.getAmount() - 1);
			if(s.titaniumAxe.getAmount() == 0)
				bagW[5].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon7":
			player.gainCoin(s.fieryAxe.getSellValue());
			s.fieryAxe.setAmount(s.fieryAxe.getAmount() - 1);
			if(s.fieryAxe.getAmount() == 0)
				bagW[6].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon8":
			player.gainCoin(s.moltenAxe.getSellValue());
			s.moltenAxe.setAmount(s.moltenAxe.getAmount() - 1);
			if(s.moltenAxe.getAmount() == 0)
				bagW[7].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "weapon9":
			player.gainCoin(s.waterAxe.getSellValue());
			s.waterAxe.setAmount(s.waterAxe.getAmount() - 1);
			if(s.waterAxe.getAmount() == 0)
				bagW[8].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
			
		// Sell item buttons
		case "item1":
			player.gainCoin(s.healthPot.getSellValue());
			s.healthPot.setAmount(s.healthPot.getAmount() - 1);
			if(s.healthPot.getAmount() == 0)
				bagI[0].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "item2":
			player.gainCoin(s.shield.getSellValue());
			s.shield.setAmount(s.shield.getAmount() - 1);
			if(s.healthPot.getAmount() == 0)
				bagI[1].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "item3":
			player.gainCoin(s.bomb.getSellValue());
			s.bomb.setAmount(s.bomb.getAmount() - 1);
			if(s.healthPot.getAmount() == 0)
				bagI[2].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "item4":
			player.gainCoin(s.poisonDart.getSellValue());
			s.poisonDart.setAmount(s.poisonDart.getAmount() - 1);
			if(s.healthPot.getAmount() == 0)
				bagI[3].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "item5":
			player.gainCoin(s.bigBomb.getSellValue());
			s.bigBomb.setAmount(s.bigBomb.getAmount() - 1);
			if(s.healthPot.getAmount() == 0)
				bagI[4].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
		case "item6":
			player.gainCoin(s.biggerBomb.getSellValue());
			s.biggerBomb.setAmount(s.biggerBomb.getAmount() - 1);
			if(s.healthPot.getAmount() == 0)
				bagI[5].setEnabled(false);
			coins.setText("Available coins: " + player.getCoins());
			checkAvailability();
			break;
			
		// Buy weapon buttons
		case "ironAxeB":			
			player.loseCoin(s.ironAxe.getBuyValue());
			s.ironAxe.setAmount(s.ironAxe.getAmount() + 1);
			checkAvailability();
			break;		
		case "steelAxeB":			
			player.loseCoin(s.steelAxe.getBuyValue());
			s.steelAxe.setAmount(s.steelAxe.getAmount() + 1);
			checkAvailability();
			break;
		case "fieryAxeB":			
			player.loseCoin(s.fieryAxe.getBuyValue());
			s.fieryAxe.setAmount(s.fieryAxe.getAmount() + 1);
			checkAvailability();
			break;
		case "healthB":
			player.loseCoin(s.healthPot.getBuyValue());
			s.healthPot.setAmount(s.healthPot.getAmount() + 1);
			checkAvailability();
			break;
		case "shieldB":
			player.loseCoin(s.shield.getBuyValue());
			s.shield.setAmount(s.shield.getAmount() + 1);
			checkAvailability();
			break;
		case "bombB":
			player.loseCoin(s.bomb.getBuyValue());
			s.bomb.setAmount(s.bomb.getAmount() + 1);
			checkAvailability();
			break;
		case "dartB":
			player.loseCoin(s.poisonDart.getBuyValue());
			s.poisonDart.setAmount(s.poisonDart.getAmount() + 1);
			checkAvailability();
			break;
		case "bigBombB":
			player.loseCoin(s.bigBomb.getBuyValue());
			s.bigBomb.setAmount(s.bigBomb.getAmount() + 1);
			checkAvailability();
			break;
		case "biggerBombB":
			player.loseCoin(s.biggerBomb.getBuyValue());
			s.biggerBomb.setAmount(s.biggerBomb.getAmount() + 1);
			checkAvailability();
			break;
		}		
	}
	
	public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	// Draw background
	    g.drawImage(bg, 0, 0, null);
    }
}

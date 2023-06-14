package stages;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;

import constants.Player;
import inventory.Storage;
import main.GameWindow;

@SuppressWarnings("serial")
public class HomeScreen extends JPanel implements ActionListener {
	
    private GameWindow window;
    private Player player = new Player();
    Gson gson = new Gson();
    private JLabel lblCoins;
    private Image returnBtn, quitBtn, saveBtn, ironBtn, silverBtn, goldBtn, steelBtn, copperBtn, titanBtn, fieryBtn,
    		moltenBtn, waterBtn, healthBtn, shieldBtn, bombBtn, dartBtn, dynamiteBtn, bombsBtn, bg, bagBtn, coin;
    private JButton menuButton, quitButton, saveButton, levelSelectButton, slotMachineButton, 
    		storeButton, bagButton, funModeButton, mineGame, blacksmith;
    private Storage s = Storage.getInstance();
    private int xLoc1 = 607, yLoc1 = 300, itemsInRow1 = 0, xLoc2 = 642, yLoc2 = 500, itemsInRow2 = 0;
    private int xLocA = 660, yLocA = 90, itemsInRowA = 0;
    private JButton bagW[] = new JButton[s.obsidianAxe.getID()];
    private JButton bagI[] = new JButton[s.biggerBomb.getID()];
    private JButton activeBag[] = new JButton[8];
    private boolean bagVisible = false, weaponAdded = false;
    private JLabel weaponName[] = new JLabel[s.obsidianAxe.getID()];
    private JLabel itemName[] = new JLabel[s.biggerBomb.getID()];

    public HomeScreen(GameWindow window) {
        this.window = window;        
        player.setUnlockedStage(1);
        player.setMaxHP(60);
        player.setStrength(200);
        player.setLevelCap(10);
        player.setExp(0);
        player.setLevel(0);
        s.gameLevel = 1;
        
        setLayout(null); 
        
        loadIcons();
        initComponents();
        checkInventory();
    }

    private void initComponents() {
    	menuButton = new JButton();  
        menuButton.setIcon(new ImageIcon(returnBtn));
        menuButton.setActionCommand("Return");
        menuButton.addActionListener(this);
        menuButton.setFocusable(false);
        menuButton.setOpaque(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setBounds(25, 25, 100, 50);
        add(menuButton);
              
        quitButton = new JButton();
        quitButton.setIcon(new ImageIcon(quitBtn));
        quitButton.setActionCommand("Quit");
        quitButton.setFocusable(false);
        quitButton.setOpaque(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.addActionListener(this);
        quitButton.setBounds(25, 80, 100, 50);
        add(quitButton);
        
        saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(saveBtn));
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(this);
        saveButton.setFocusable(false);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(this);
        saveButton.setBounds(25, 135, 100, 50);
        add(saveButton);
        
        // Story mode button
        levelSelectButton = new JButton();
        levelSelectButton.setActionCommand("Levels");
        levelSelectButton.setFocusable(false);
        levelSelectButton.addActionListener(this);
        levelSelectButton.setContentAreaFilled(false);
        levelSelectButton.setBorderPainted(false);
        levelSelectButton.setBounds(1040, 80, 175, 250);
        add(levelSelectButton);
              
        // Endless mode button
        funModeButton = new JButton();
        funModeButton.setActionCommand("FunMode");
        funModeButton.setFocusable(false);
        funModeButton.addActionListener(this);
        funModeButton.setContentAreaFilled(false);
        funModeButton.setBorderPainted(false);
        funModeButton.setBounds(1040, 400, 175, 250);
        add(funModeButton);
        
        slotMachineButton = new JButton();
        slotMachineButton.setActionCommand("Slot");
        slotMachineButton.setFocusable(false);
        slotMachineButton.addActionListener(this);
        slotMachineButton.setContentAreaFilled(false);
        slotMachineButton.setBorderPainted(false);
        slotMachineButton.setBounds(15, 295, 100, 150);
        add(slotMachineButton);
        
        storeButton = new JButton();
        storeButton.setActionCommand("Store");
        storeButton.setFocusable(false);
        storeButton.addActionListener(this);
        storeButton.setContentAreaFilled(false);
        storeButton.setBorderPainted(false);
        storeButton.setBounds(165, 340, 130, 100);
        add(storeButton);
        
        // Mining minigame button
        mineGame = new JButton();
        mineGame.setActionCommand("Mine");
        mineGame.setFocusable(false);
        mineGame.addActionListener(this);
        mineGame.setContentAreaFilled(false);
        mineGame.setBorderPainted(false);
        mineGame.setBounds(25, 480, 100, 90);
        add(mineGame);
        
        // Blacksmith button
        blacksmith = new JButton();
        blacksmith.setActionCommand("Blacksmith");
        blacksmith.setFocusable(false);
        blacksmith.addActionListener(this);
        blacksmith.setContentAreaFilled(false);
        blacksmith.setBorderPainted(false);
        blacksmith.setBounds(185, 500, 100, 80);
        add(blacksmith);
        
        // Inventory open button
        bagButton = new JButton();
        bagButton.setIcon(new ImageIcon(bagBtn));
        bagButton.setActionCommand("Bag");
        bagButton.setFocusable(false);
        bagButton.addActionListener(this);
        bagButton.setContentAreaFilled(false);
        bagButton.setBorderPainted(false);
        bagButton.setBounds(730, 200, 100, 50);
        add(bagButton);
        
        // Create inventory
        for(int i = 0; i < activeBag.length; i++) {
        	String temp = "active";
        	activeBag[i] = new JButton();
        	activeBag[i].setFocusable(false);
        	activeBag[i].setActionCommand(temp + (i + 1));
        	activeBag[i].addActionListener(this);
        	activeBag[i].setContentAreaFilled(false);
        	activeBag[i].setBorder(s.border);
        	activeBag[i].setBounds(xLocA, yLocA, 60, 50);
        	add(activeBag[i]);
        	xLocA += 60;
        	itemsInRowA++;
        	if(itemsInRowA == 4) {
    			xLocA = 660;
    			yLocA += 50;
    			itemsInRowA = 0;
    		}
        	Player.activeBag[i] = activeBag[i];
        }
        
        lblCoins = new JLabel();
        lblCoins.setVisible(false);
        lblCoins.setFont(s.font3);
        lblCoins.setText(Integer.toString(player.getCoins()));
        lblCoins.setForeground(Color.WHITE);
        lblCoins.setBounds(885, 205, 100, 40);
        add(lblCoins);
	}
    
    // Move the item from inventory to active bag
	private void addItemToActiveBag(Image img, String name) {
		for(int i = 0; i < activeBag.length; i++) {
			if(weaponAdded) {
				activeBag[0].setIcon(new ImageIcon(img));
				activeBag[0].setName(name);	
				weaponAdded = false;
				break;
			}
			else if(activeBag[i].getIcon() == null && i != 0) {
				activeBag[i].setIcon(new ImageIcon(img));
				activeBag[i].setName(name);	
				break;
			}
		}		
	}
	
    private void checkInventory() {
    	// Check for weapons
    	if(s.ironAxe.getAmount() > 0)
			createWeaponInventory(s.ironAxe.getID());
		if(s.silverAxe.getAmount() > 0)
			createWeaponInventory(s.silverAxe.getID());
		if(s.goldAxe.getAmount() > 0)
			createWeaponInventory(s.goldAxe.getID());
		if(s.steelAxe.getAmount() > 0)
			createWeaponInventory(s.steelAxe.getID());	
		if(s.copperAxe.getAmount() > 0)
			createWeaponInventory(s.copperAxe.getID());
		if(s.titaniumAxe.getAmount() > 0)
			createWeaponInventory(s.titaniumAxe.getID());
		if(s.fieryAxe.getAmount() > 0)
			createWeaponInventory(s.fieryAxe.getID());	
		if(s.moltenAxe.getAmount() > 0)
			createWeaponInventory(s.moltenAxe.getID());
		if(s.waterAxe.getAmount() > 0)
			createWeaponInventory(s.waterAxe.getID());
		if(s.obsidianAxe.getAmount() > 0)
			createWeaponInventory(s.obsidianAxe.getID());
		
		// Check for items
		if(s.healthPot.getAmount() > 0)
			createItemInventory(s.healthPot.getID());
		if(s.shield.getAmount() > 0)
			createItemInventory(s.shield.getID());
		if(s.bomb.getAmount() > 0)
			createItemInventory(s.bomb.getID());
		if(s.poisonDart.getAmount() > 0)
			createItemInventory(s.poisonDart.getID());
		if(s.bigBomb.getAmount() > 0)
			createItemInventory(s.bigBomb.getID());
		if(s.biggerBomb.getAmount() > 0)
			createItemInventory(s.biggerBomb.getID());
	}
	
	private void createWeaponInventory(int x) {
		String temp = "weapon";
		bagW[x - 1] = new JButton();
		bagW[x - 1].setFocusable(false);
		bagW[x - 1].setVisible(false);
		bagW[x - 1].setContentAreaFilled(false);
		switch(x) {
		case 1:
			bagW[x - 1].setIcon(new ImageIcon(ironBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Iron<br>Axe x%d</html>", s.ironAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);			
			break;
		case 2:
			bagW[x - 1].setIcon(new ImageIcon(silverBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Silver<br>Axe x%d</html>", s.silverAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 3:
			bagW[x - 1].setIcon(new ImageIcon(goldBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Gold<br>Axe x%d</html>", s.goldAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 4:
			bagW[x - 1].setIcon(new ImageIcon(steelBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Steel<br>Axe x%d</html>", s.steelAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 5:
			bagW[x - 1].setIcon(new ImageIcon(copperBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Copper<br>Axe x%d</html>", s.copperAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 6:
			bagW[x - 1].setIcon(new ImageIcon(titanBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Titanium<br>Axe x%d</html>", s.titaniumAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 7:
			bagW[x - 1].setIcon(new ImageIcon(fieryBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Fiery<br>Axe x%d</html>", s.fieryAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 8:
			bagW[x - 1].setIcon(new ImageIcon(moltenBtn));
			weaponName[x - 1] = new JLabel();
			weaponName[x - 1].setText(String.format("<html>Molten<br>Axe x%d</html>", s.moltenAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 9:
			bagW[x - 1].setIcon(new ImageIcon(waterBtn));
			weaponName[x - 1] = new JLabel();
			
			weaponName[x - 1].setText(String.format("<html>Water<br>Axe x%d</html>", s.waterAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		case 10:
			bagW[x - 1].setIcon(new ImageIcon(waterBtn));
			weaponName[x - 1] = new JLabel();
			
			weaponName[x - 1].setText(String.format("<html>Obsidian<br>Axe x%d</html>", s.obsidianAxe.getAmount()));
			weaponName[x - 1].setBounds(xLoc1, yLoc1 - 35, 60, 40);
			weaponName[x - 1].setVisible(false);
			add(weaponName[x - 1]);
			break;
		}
		bagW[x - 1].setActionCommand(temp + x);
		bagW[x - 1].addActionListener(this);
		bagW[x - 1].setBounds(xLoc1, yLoc1, 60, 50);
		add(bagW[x - 1]);	
		weaponName[x - 1].setFont(s.font5);
		weaponName[x - 1].setForeground(Color.WHITE);
		xLoc1 += 70;
		itemsInRow1++;
		if(itemsInRow1 == 5) {
			xLoc1 = 607;
			yLoc1 += 90;
			itemsInRow1 = 0;
		}
	}	
	
	private void createItemInventory(int x) {
		String temp = "item";
		bagI[x - 1] = new JButton();
		bagI[x - 1].setFocusable(false);
		bagI[x - 1].setVisible(false);
		bagI[x - 1].setContentAreaFilled(false);
		switch(x) {
		case 1:
			bagI[x - 1].setIcon(new ImageIcon(healthBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Health<br>potion x%d</html>", s.healthPot.getAmount()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 40, 60, 40);
			itemName[x - 1].setVisible(false);
			add(itemName[x - 1]);
			break;
		case 2:
			bagI[x - 1].setIcon(new ImageIcon(shieldBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Shield<br>x%d</html>", s.shield.getAmount()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 60, 40);
			itemName[x - 1].setVisible(false);
			add(itemName[x - 1]);
			break;
		case 3:
			bagI[x - 1].setIcon(new ImageIcon(bombBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Bomb<br>x%d</html>", s.bomb.getAmount()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 60, 40);
			itemName[x - 1].setVisible(false);
			add(itemName[x - 1]);
			break;
		case 4:
			bagI[x - 1].setIcon(new ImageIcon(dartBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Poison<br>dart x%d</html>", s.poisonDart.getAmount()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 60, 40);
			itemName[x - 1].setVisible(false);
			add(itemName[x - 1]);
			break;
		case 5:
			bagI[x - 1].setIcon(new ImageIcon(dynamiteBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Dynamite<br>x%d</html>", s.bigBomb.getAmount()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 65, 40);
			itemName[x - 1].setVisible(false);
			add(itemName[x - 1]);
			break;
		case 6:
			bagI[x - 1].setIcon(new ImageIcon(bombsBtn));
			itemName[x - 1] = new JLabel();
			itemName[x - 1].setText(String.format("<html>Bombs<br>x%d</html>", s.biggerBomb.getAmount()));
			itemName[x - 1].setBounds(xLoc2, yLoc2 - 35, 60, 40);
			itemName[x - 1].setVisible(false);
			add(itemName[x - 1]);
			break;
		}
		bagI[x - 1].setActionCommand(temp + x);
		bagI[x - 1].addActionListener(this);
		bagI[x - 1].setBounds(xLoc2, yLoc2, 60, 50);
		add(bagI[x - 1]);
		itemName[x - 1].setFont(s.font5);
		itemName[x - 1].setForeground(Color.WHITE);
		xLoc2 += 70;
		itemsInRow2++;
		if(itemsInRow2 == 4) {
			xLoc2 = 712;
			yLoc2 += 80;
			itemsInRow2 = 0;
		}			
	}
	
	private void showInventory() {
		for(int i = 0; i < bagW.length; i++) {
			if(bagW[i] != null) {
				bagW[i].setVisible(true);
				weaponName[i].setVisible(true);
			}
		}
		for(int i = 0; i < bagI.length; i++) {
			if(bagI[i] != null) {
				bagI[i].setVisible(true);
				itemName[i].setVisible(true);
			}
		}
		lblCoins.setVisible(true);
		bagVisible = true;
	}
	
	private void hideInventory() {
		for(int i = 0; i < bagW.length; i++) {
			if(bagW[i] != null) {
				bagW[i].setVisible(false);
				weaponName[i].setVisible(false);
			}
		}
		for(int i = 0; i < bagI.length; i++) {
			if(bagI[i] != null) {
				bagI[i].setVisible(false);
				itemName[i].setVisible(false);
			}
		}
		lblCoins.setVisible(false);
		bagVisible = false;
	}
	
	private void checkAvailableItems() {
		if(bagI[0] != null) {
			itemName[0].setText(String.format("<html>Health<br>potion x%d</html>", s.healthPot.getAmount()));
			if(s.healthPot.getAmount() <= 0)
				bagI[0].setEnabled(false);
			else
				bagI[0].setEnabled(true);
		}
		if(bagI[1] != null) {
			itemName[1].setText(String.format("<html>Shield<br>x%d</html>", s.shield.getAmount()));
			if(s.shield.getAmount() <= 0)
				bagI[1].setEnabled(false);
			else
				bagI[1].setEnabled(true);
		}
		if(bagI[2] != null) {
			itemName[2].setText(String.format("<html>Bomb<br>x%d</html>", s.bomb.getAmount()));
			if(s.bomb.getAmount() <= 0)
				bagI[2].setEnabled(false);
			else
				bagI[2].setEnabled(true);
		}
		if(bagI[3] != null) {
			itemName[3].setText(String.format("<html>Poison<br>dart x%d</html>", s.poisonDart.getAmount()));
			if(s.poisonDart.getAmount() <= 0)
				bagI[3].setEnabled(false);
			else
				bagI[3].setEnabled(true);
		}
		if(bagI[4] != null) {
			itemName[4].setText(String.format("<html>Dynamite<br>x%d</html>", s.bigBomb.getAmount()));
			if(s.bigBomb.getAmount() <= 0)
				bagI[4].setEnabled(false);
			else
				bagI[4].setEnabled(true);
		}
		if(bagI[5] != null) {
			itemName[5].setText(String.format("<html>Bombs<br>x%d</html>", s.biggerBomb.getAmount()));
			if(s.biggerBomb.getAmount() <= 0)
				bagI[5].setEnabled(false);
			else
				bagI[5].setEnabled(true);
		}
	}
	
	// Move item from active bag to inventory
	private void addItemBack(int x) {		
		if(activeBag[x].getName() == "Health")
			s.healthPot.setAmount(s.healthPot.getAmount() + 1);
		else if(activeBag[x].getName() == "Shield")
			s.shield.setAmount(s.shield.getAmount() + 1);
		else if(activeBag[x].getName() == "Bomb")
			s.bomb.setAmount(s.bomb.getAmount() + 1);
		else if(activeBag[x].getName() == "PoisonDart")
			s.poisonDart.setAmount(s.poisonDart.getAmount() + 1);
		else if(activeBag[x].getName() == "Dynamite")
			s.bigBomb.setAmount(s.bigBomb.getAmount() + 1);
		else if(activeBag[x].getName() == "Bombs")
			s.biggerBomb.setAmount(s.biggerBomb.getAmount() + 1);
		
		checkAvailableItems();
	}
	
	private void removeWeaponFromInventory(String name) {
		switch(name) {
		case "IronAxe":
			s.ironAxe.setAmount(s.ironAxe.getAmount() - 1);
			break;
		case "SilverAxe":
			s.silverAxe.setAmount(s.silverAxe.getAmount() - 1);
			break;
		case "GoldAxe":
			s.goldAxe.setAmount(s.goldAxe.getAmount() - 1);
			break;
		case "SteelAxe":
			s.steelAxe.setAmount(s.steelAxe.getAmount() - 1);
			break;
		case "CopperAxe":
			s.copperAxe.setAmount(s.copperAxe.getAmount() - 1);
			break;
		case "TitaniumAxe":
			s.titaniumAxe.setAmount(s.titaniumAxe.getAmount() - 1);
			break;
		case "FieryAxe":
			s.fieryAxe.setAmount(s.fieryAxe.getAmount() - 1);
			break;
		case "MoltenAxe":
			s.moltenAxe.setAmount(s.moltenAxe.getAmount() - 1);
			break;
		case "WaterAxe":
			s.waterAxe.setAmount(s.waterAxe.getAmount() - 1);
			break;
		case "ObsidianAxe":
			s.obsidianAxe.setAmount(s.obsidianAxe.getAmount() - 1);
			break;
		}
	}
	
	private void loadIcons() {
		try {
			returnBtn = ImageIO.read(new File("res/FunctionButtons/ReturnButton.png"));
			saveBtn = ImageIO.read(new File("res/FunctionButtons/SaveButton.png"));
			quitBtn = ImageIO.read(new File("res/FunctionButtons/QuitButton.png"));
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
			bg = ImageIO.read(new File("res/Backgrounds/HomeScreen_BG.png"));
			bagBtn = ImageIO.read(new File("res/FunctionButtons/BagButton.png"));
			coin = ImageIO.read(new File("res/LevelIcons/MerchIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveGame() {
		int[] saveS = new int[22];
		saveS[0] = player.getStoryMaxHP();
		saveS[1] = player.getStoryStrength();
		saveS[2] = player.getCoins();
		saveS[3] = player.getStoryLevel();
		saveS[4] = player.getStoryExp();
		saveS[5] = player.getStoryCap();
		saveS[6] = s.ironAxe.getAmount();
		saveS[7] = s.silverAxe.getAmount();
		saveS[8] = s.goldAxe.getAmount();
		saveS[9] = s.steelAxe.getAmount();
		saveS[10] = s.copperAxe.getAmount();
		saveS[11] = s.titaniumAxe.getAmount();
		saveS[12] = s.fieryAxe.getAmount();
		saveS[13] = s.moltenAxe.getAmount();
		saveS[14] = s.waterAxe.getAmount();
		saveS[15] = s.waterAxe.getAmount();
		saveS[16] = s.healthPot.getAmount();
		saveS[17] = s.shield.getAmount();
		saveS[18] = s.bomb.getAmount();
		saveS[19] = s.poisonDart.getAmount();
		saveS[20] = s.bigBomb.getAmount();
		saveS[21] = s.biggerBomb.getAmount();
		
		try (FileWriter writer = new FileWriter("savegame.json")) {
            gson.toJson(saveS, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
        case "Return":
            window.showMainMenu();
            break;
        case "Save":
            saveGame();
            break;
        case "Quit":
            System.exit(0);
            break;
        case "Levels":
        	if(player.getStoryLevel() > 5)
        		s.gameLevel = 2;
        	else if(player.getStoryLevel() > 10)
        		s.gameLevel = 3;
        	else if(player.getStoryLevel() == 15)
        		s.gameLevel = 4;
        	s.gameMode = 0;
        	itemsInRowA = 0;
        	xLocA = 975;
        	yLocA = 30;
        	for(int i = 0; i < activeBag.length; i++) {
        		String temp = "inv";
        		Player.activeBag[i].setActionCommand(temp + i);
        		Player.activeBag[i].setContentAreaFilled(true);
        		Player.activeBag[i].setBackground(Color.DARK_GRAY);
        		if(activeBag[i].getIcon() != null)
        			Player.activeBag[i].setIcon(activeBag[i].getIcon());
        		Player.activeBag[i].setBounds(xLocA, yLocA, 60, 50);
        		itemsInRowA++;
        		xLocA += 60;
        		if(itemsInRowA == 4) {
        			xLocA = 975;
        			yLocA += 50;
        		}      		
        	} 
        	if(activeBag[0].getIcon() != null)
        		removeWeaponFromInventory(activeBag[0].getName());
            window.showLevelSelector(1);
            break;
        case "FunMode":
        	s.gameMode = 1;
        	itemsInRowA = 0;
        	xLocA = 975;
        	yLocA = 30;
        	for(int i = 0; i < activeBag.length; i++) {
        		String temp = "inv";
        		Player.activeBag[i].setActionCommand(temp + i);
        		Player.activeBag[i].setContentAreaFilled(true);
        		Player.activeBag[i].setBackground(Color.DARK_GRAY);
        		if(activeBag[i].getIcon() != null)
        			Player.activeBag[i].setIcon(activeBag[i].getIcon());
        		Player.activeBag[i].setBounds(xLocA, yLocA, 60, 50);
        		itemsInRowA++;
        		xLocA += 60;
        		if(itemsInRowA == 4) {
        			xLocA = 975;
        			yLocA += 50;
        		}      		
        	}
        	if(activeBag[0].getIcon() != null)
        		removeWeaponFromInventory(activeBag[0].getName());
            window.showFunMode(1);
            break;
        case "Slot":
            window.showSlotMachine();
            break;
        case "Mine":
        	window.showMine();
        	break;
        case "Blacksmith":
        	window.showBlacksmith();
        	break;
        case "Store":
        	window.showStore();
        	break;
        case "Bag":
        	if(!bagVisible)
        		showInventory();
        	else
        		hideInventory();
        	break;
        	
        // Weapons in inventory
        case "weapon1":
            player.setActiveWeapon(s.ironAxe.getID()); 
            weaponAdded = true;
            addItemToActiveBag(ironBtn, "IronAxe");           
            break;
        case "weapon2":
            player.setActiveWeapon(s.silverAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(silverBtn, "SilverAxe");
            break;
        case "weapon3":
            player.setActiveWeapon(s.goldAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(goldBtn, "GoldAxe");
            break;
        case "weapon4":
            player.setActiveWeapon(s.steelAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(steelBtn, "SteelAxe");
            break;
        case "weapon5":
            player.setActiveWeapon(s.copperAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(copperBtn, "CopperAxe");
            break;
        case "weapon6":
            player.setActiveWeapon(s.titaniumAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(titanBtn, "TitaniumAxe");           
            break;
        case "weapon7":
            player.setActiveWeapon(s.fieryAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(fieryBtn, "FieryAxe");
            break;
        case "weapon8":
            player.setActiveWeapon(s.moltenAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(moltenBtn, "MoltenAxe");
            break;
        case "weapon9":
            player.setActiveWeapon(s.waterAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(waterBtn, "WaterAxe");
            break;
        case "weapon10":
            player.setActiveWeapon(s.obsidianAxe.getID());
            weaponAdded = true;
            addItemToActiveBag(waterBtn, "ObsidianAxe");
            break;
            
        // Items in inventory
        case "item1":
        	for(int i = 1; i < activeBag.length; i++) {
        		if(activeBag[i].getIcon() == null) {
        			s.healthPot.setAmount(s.healthPot.getAmount() - 1);
        			break;
        		}
        	}
        	checkAvailableItems();
        	addItemToActiveBag(healthBtn, "Health");
        	break;
        case "item2":
        	for(int i = 1; i < activeBag.length; i++) {
        		if(activeBag[i].getIcon() == null) {
        			s.shield.setAmount(s.shield.getAmount() - 1);
        			break;
        		}
        	}
        	checkAvailableItems();
        	addItemToActiveBag(shieldBtn, "Shield");
        	break;
        case "item3":
        	for(int i = 1; i < activeBag.length; i++) {
        		if(activeBag[i].getIcon() == null) {
        			s.bomb.setAmount(s.bomb.getAmount() - 1);
        			break;
        		}
        	}
        	checkAvailableItems();
        	addItemToActiveBag(bombBtn, "Bomb");
        	break;
        case "item4":
        	for(int i = 1; i < activeBag.length; i++) {
        		if(activeBag[i].getIcon() == null) {
        			s.poisonDart.setAmount(s.poisonDart.getAmount() - 1);
        			break;
        		}
        	}
        	checkAvailableItems();
        	addItemToActiveBag(dartBtn, "PoisonDart");
        	break;
        case "item5":
        	for(int i = 1; i < activeBag.length; i++) {
        		if(activeBag[i].getIcon() == null) {
        			s.bigBomb.setAmount(s.bigBomb.getAmount() - 1);
        			break;
        		}
        	}
        	checkAvailableItems();
        	addItemToActiveBag(dynamiteBtn, "Dynamite");
        	break;
        case "item6":
        	for(int i = 1; i < activeBag.length; i++) {
        		if(activeBag[i].getIcon() == null) {
        			s.biggerBomb.setAmount(s.biggerBomb.getAmount() - 1);
        			break;
        		}
        	}
        	checkAvailableItems();
        	addItemToActiveBag(bombsBtn, "Bombs");
        	break;
        	
        // Active inventory
        case "active1":
        	activeBag[0].setIcon(null);
        	break;
        case "active2":
        	addItemBack(1);
        	activeBag[1].setIcon(null);
        	break;
        case "active3":
        	addItemBack(2);
        	activeBag[2].setIcon(null);
        	break;
        case "active4":
        	addItemBack(3);
        	activeBag[3].setIcon(null);
        	break;
        case "active5":
        	addItemBack(4);
        	activeBag[4].setIcon(null);
        	break;
        case "active6":
        	addItemBack(5);
        	activeBag[5].setIcon(null);
        	break;
        case "active7":
        	addItemBack(6);
        	activeBag[6].setIcon(null);
        	break;
        case "active8":
        	addItemBack(7);
        	activeBag[7].setIcon(null);
        	break;
        }
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	// Draw background
	    g.drawImage(bg, 0, 0, null);
	    
	    if(bagVisible)
	    	g.drawImage(coin, 850, 210, 30, 30, this);
    }
}


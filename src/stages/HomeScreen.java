package stages;

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

import Inventory.Storage;
import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class HomeScreen extends JPanel implements ActionListener {
	
    private GameWindow window;
    private Player player = new Player();
    Gson gson = new Gson();
    private Image returnBtn, quitBtn, saveBtn;
    private JButton menuButton, quitButton, saveButton, levelSelectButton, slotMachineButton, 
    		storeButton, bagButton;
    private Storage s = Storage.getInstance();
    private int xLoc1 = 800, yLoc1 = 300, itemsInRow1 = 0, xLoc2 = 800, yLoc2 = 500, itemsInRow2 = 0;
    private int xLocA = 800, yLocA = 50, itemsInRowA = 0;
    private JButton bagW[] = new JButton[s.waterAxe.getID()];
    private JButton bagI[] = new JButton[s.biggerBomb.getID()];
    private JButton activeBag[] = new JButton[8];
    private boolean bagVisible = false;
    private JLabel ia;

    public HomeScreen(GameWindow window) {
        this.window = window;        
        player.setUnlockedStage(1);
        
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
        
        levelSelectButton = new JButton("Levels");
        levelSelectButton.setActionCommand("Levels");
        levelSelectButton.setFocusable(false);
        levelSelectButton.addActionListener(this);
        levelSelectButton.setBounds(200, 200, 100, 50);
        add(levelSelectButton);
        
        slotMachineButton = new JButton("Slot");
        slotMachineButton.setActionCommand("Slot");
        slotMachineButton.setFocusable(false);
        slotMachineButton.addActionListener(this);
        slotMachineButton.setBounds(200, 300, 100, 50);
        add(slotMachineButton);
        
        storeButton = new JButton("Store");
        storeButton.setActionCommand("Store");
        storeButton.setFocusable(false);
        storeButton.addActionListener(this);
        storeButton.setBounds(200, 400, 100, 50);
        add(storeButton);	
        
        bagButton = new JButton("Bag");
        bagButton.setActionCommand("Bag");
        bagButton.setFocusable(false);
        bagButton.addActionListener(this);
        bagButton.setBounds(800, 200, 100, 50);
        add(bagButton);
        
        for(int i = 0; i < activeBag.length; i++) {
        	String temp = "active";
        	activeBag[i] = new JButton();
        	activeBag[i].setFocusable(false);
        	activeBag[i].setActionCommand(temp + i);
        	activeBag[i].addActionListener(this);
        	activeBag[i].setContentAreaFilled(false);
        	activeBag[i].setBounds(xLocA, yLocA, 60, 50);
        	add(activeBag[i]);
        	xLocA += 60;
        	itemsInRowA++;
        	if(itemsInRowA == 4) {
    			xLocA = 800;
    			yLocA += 50;
    			itemsInRowA = 0;
    		}	
        }
	}
    
	private void addItemToActiveBag(String s) {
		for(int i = 0; i < activeBag.length; i++) {
			if(activeBag[i].getText().trim().isEmpty()) {
				activeBag[i].setText(s);
				activeBag[i].setContentAreaFilled(true);
				activeBag[i].repaint();
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
		switch(x) {
		case 1:
			bagW[x - 1].setText("IA");
			ia = new JLabel();
			ia.setText(Integer.toString(s.ironAxe.getAmount()));
			ia.setBounds(xLoc1, yLoc1 - 20, 50, 20);
			add(ia);			
			break;
		case 2:
			bagW[x - 1].setText("SiA");
			break;
		case 3:
			bagW[x - 1].setText("GA");
			break;
		case 4:
			bagW[x - 1].setText("SA");
			break;
		case 5:
			bagW[x - 1].setText("CA");
			break;
		case 6:
			bagW[x - 1].setText("TA");
			break;
		case 7:
			bagW[x - 1].setText("FA");
			break;
		case 8:
			bagW[x - 1].setText("MA");
			break;
		case 9:
			bagW[x - 1].setText("WA");
			break;
		}
		bagW[x - 1].setActionCommand(temp + x);
		bagW[x - 1].addActionListener(this);
		bagW[x - 1].setBounds(xLoc1, yLoc1, 60, 50);
		add(bagW[x - 1]);	
		xLoc1 += 70;
		itemsInRow1++;
		if(itemsInRow1 == 5) {
			xLoc1 = 800;
			yLoc1 += 60;
			itemsInRow1 = 0;
		}
	}	
	
	private void createItemInventory(int x) {
		String temp = "item";
		bagI[x - 1] = new JButton();
		bagI[x - 1].setFocusable(false);
		bagI[x - 1].setVisible(false);
		switch(x) {
		case 1:
			bagI[x - 1].setText("HP");
			break;
		case 2:
			bagI[x - 1].setText("SH");
			break;
		case 3:
			bagI[x - 1].setText("B");
			break;
		case 4:
			bagI[x - 1].setText("PD");
			break;
		case 5:
			bagI[x - 1].setText("bB");
			break;
		case 6:
			bagI[x - 1].setText("BB");
			break;
		}
		bagI[x - 1].setActionCommand(temp + x);
		bagI[x - 1].addActionListener(this);
		bagI[x - 1].setBounds(xLoc2, yLoc2, 60, 50);
		add(bagI[x - 1]);	
		xLoc2 += 70;
		itemsInRow2++;
		if(itemsInRow2 == 4) {
			xLoc2 = 800;
			yLoc2 += 60;
			itemsInRow2 = 0;
		}			
	}
	
	private void showInventory() {
		for(int i = 0; i < bagW.length; i++) {
			if(bagW[i] != null)
				bagW[i].setVisible(true);
		}
		for(int i = 0; i < bagI.length; i++) {
			if(bagI[i] != null)
				bagI[i].setVisible(true);
		}
		bagVisible = true;
	}
	
	private void hideInventory() {
		for(int i = 0; i < bagW.length; i++) {
			if(bagW[i] != null)
				bagW[i].setVisible(false);
		}
		for(int i = 0; i < bagI.length; i++) {
			if(bagI[i] != null)
				bagI[i].setVisible(false);
		}
		bagVisible = false;
	}
	
	private void disableWeaponButton(int x) {
		for(int i = 0; i < bagW.length; i++) {
			if(bagW[i] != null)
				bagW[i].setEnabled(true);
		}		
		if(player.getActiveWeapon() == x)
			bagW[x - 1].setEnabled(false);
	}
    
	private void loadIcons() {
		try {
			returnBtn = ImageIO.read(new File("res/FunctionButtons/ReturnButton.png"));
			saveBtn = ImageIO.read(new File("res/FunctionButtons/SaveButton.png"));
			quitBtn = ImageIO.read(new File("res/FunctionButtons/QuitButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveGame() {
		int[] saveS = new int[7];
		saveS[0] = player.getMaxHP();
		saveS[1] = player.getStrength();
		saveS[2] = player.getCoins();
		saveS[3] = player.getLevel();
		saveS[4] = player.getExp();
		saveS[5] = player.getLevelCap();
		saveS[6] = s.ironAxe.getAmount();
		
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
            window.showLevelSelector(1);
            break;
        case "Slot":
            window.showSlotMachine();
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
            disableWeaponButton(s.ironAxe.getID());
            addItemToActiveBag("IA");
            break;
        case "weapon2":
            player.setActiveWeapon(s.silverAxe.getID());
            disableWeaponButton(s.silverAxe.getID());
            break;
        case "weapon3":
            player.setActiveWeapon(s.goldAxe.getID());
            disableWeaponButton(s.goldAxe.getID());
            break;
        case "weapon4":
            player.setActiveWeapon(s.steelAxe.getID());
            disableWeaponButton(s.steelAxe.getID());
            break;
        case "weapon5":
            player.setActiveWeapon(s.copperAxe.getID());
            disableWeaponButton(s.copperAxe.getID());
            break;
        case "weapon6":
            player.setActiveWeapon(s.titaniumAxe.getID());
            disableWeaponButton(s.titaniumAxe.getID());
            break;
        case "weapon7":
            player.setActiveWeapon(s.fieryAxe.getID());
            disableWeaponButton(s.fieryAxe.getID());
            break;
        case "weapon8":
            player.setActiveWeapon(s.moltenAxe.getID());
            disableWeaponButton(s.moltenAxe.getID());
            break;
        case "weapon9":
            player.setActiveWeapon(s.waterAxe.getID());
            disableWeaponButton(s.waterAxe.getID());
            break;
        }
    }
}


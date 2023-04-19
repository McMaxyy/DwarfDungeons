package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Inventory.Storage;
import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class Store extends JPanel implements ActionListener{
	private GameWindow window;
	private Storage s = Storage.getInstance();
	private JButton returnButton, ironAxeBuy, steelAxeBuy, fieryAxeBuy, healthBuy, shieldBuy, bombBuy, 
			dartBuy, bigBombBuy, biggerBombBuy;
	private Player player = new Player();
	private int xLoc1 = 100, yLoc1 = 100, itemsInRow1 = 0, xLoc2 = 100, yLoc2 = 300, itemsInRow2 = 0;
	private JButton bagW[] = new JButton[s.waterAxe.getID()];
	private JButton bagI[] = new JButton[s.biggerBomb.getID()];
	
	public Store(GameWindow window) {
        this.window = window;
        
        setLayout(null);
        
        initComponents();
        checkAvailability();
        checkInventory();
	}
	
	private void checkAvailability() {
		// Check weapons
		if(player.getCoins() >= s.ironAxe.getBuyValue())
			ironAxeBuy.setEnabled(true);
		else
			ironAxeBuy.setEnabled(false);
		if(player.getCoins() >= s.steelAxe.getBuyValue())
			steelAxeBuy.setEnabled(true);
		else
			steelAxeBuy.setEnabled(false);
		if(player.getCoins() >= s.fieryAxe.getBuyValue())
			fieryAxeBuy.setEnabled(true);
		else
			fieryAxeBuy.setEnabled(false);
		
		// Check items
		if(player.getCoins() >= s.healthPot.getBuyValue())
			healthBuy.setEnabled(true);
		else
			healthBuy.setEnabled(false);
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
		switch(x) {
		case 1:
			bagW[x - 1].setText("IA");
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
			xLoc1 = 100;
			yLoc1 += 60;
			itemsInRow1 = 0;
		}			
	}
	
	private void createItemInventory(int x) {
		String temp = "item";
		bagI[x - 1] = new JButton();
		bagI[x - 1].setFocusable(false);
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
			xLoc2 = 100;
			yLoc2 += 60;
			itemsInRow2 = 0;
		}			
	}
	
	private void removeInventoryWeapon(int x) {
		switch(x) {
		case 1:
			bagW[s.ironAxe.getID() - 1].setVisible(false);
			break;
		case 2:
			bagW[s.steelAxe.getID() - 1].setVisible(false);
			break;
		}
	}
	
	private void removeInventoryItem(int x) {
		switch(x) {
		case 1:
			bagI[x - 1].setVisible(false);
			break;
		case 2:
			bagI[x - 1].setVisible(false);
			break;
		case 3:
			bagI[x - 1].setVisible(false);
			break;
		case 4:
			bagI[x - 1].setVisible(false);
			break;
		case 5:
			bagI[x - 1].setVisible(false);
			break;
		case 6:
			bagI[x - 1].setVisible(false);
			break;
		}
	}
	
	private void initComponents(){
		ironAxeBuy = new JButton();
		ironAxeBuy.setFocusable(false);
		ironAxeBuy.setText("Iron Axe");
		ironAxeBuy.setActionCommand("ironAxeB");
		ironAxeBuy.addActionListener(this);
		ironAxeBuy.setEnabled(false);
		ironAxeBuy.setBounds(100, 500, 100, 50);
		add(ironAxeBuy);
		
		steelAxeBuy = new JButton();
		steelAxeBuy.setFocusable(false);
		steelAxeBuy.setText("Steel Axe");
		steelAxeBuy.setActionCommand("steelAxeB");
		steelAxeBuy.addActionListener(this);
		steelAxeBuy.setEnabled(false);
		steelAxeBuy.setBounds(210, 500, 100, 50);
		add(steelAxeBuy);
		
		fieryAxeBuy = new JButton();
		fieryAxeBuy.setFocusable(false);
		fieryAxeBuy.setText("Fiery Axe");
		fieryAxeBuy.setActionCommand("fieryAxeB");
		fieryAxeBuy.addActionListener(this);
		fieryAxeBuy.setEnabled(false);
		fieryAxeBuy.setBounds(210, 560, 100, 50);
		add(fieryAxeBuy);
		
		healthBuy = new JButton();
		healthBuy.setFocusable(false);
		healthBuy.setText("Health pot");
		healthBuy.setActionCommand("healthB");
		healthBuy.addActionListener(this);
		healthBuy.setEnabled(false);
		healthBuy.setBounds(320, 500, 100, 50);
		add(healthBuy);
		
		shieldBuy = new JButton();
		shieldBuy.setFocusable(false);
		shieldBuy.setText("Shield");
		shieldBuy.setActionCommand("shieldB");
		shieldBuy.addActionListener(this);
		shieldBuy.setEnabled(false);
		shieldBuy.setBounds(430, 500, 100, 50);
		add(shieldBuy);
		
		bombBuy = new JButton();
		bombBuy.setFocusable(false);
		bombBuy.setText("Bomb");
		bombBuy.setActionCommand("bombB");
		bombBuy.addActionListener(this);
		bombBuy.setEnabled(false);
		bombBuy.setBounds(540, 500, 100, 50);
		add(bombBuy);
		
		dartBuy = new JButton();
		dartBuy.setFocusable(false);
		dartBuy.setText("Poison dart");
		dartBuy.setActionCommand("dartB");
		dartBuy.addActionListener(this);
		dartBuy.setEnabled(false);
		dartBuy.setBounds(650, 500, 100, 50);
		add(dartBuy);
		
		bigBombBuy = new JButton();
		bigBombBuy.setFocusable(false);
		bigBombBuy.setText("Big bomb");
		bigBombBuy.setActionCommand("bigBombB");
		bigBombBuy.addActionListener(this);
		bigBombBuy.setEnabled(false);
		bigBombBuy.setBounds(760, 500, 100, 50);
		add(bigBombBuy);
		
		biggerBombBuy = new JButton();
		biggerBombBuy.setFocusable(false);
		biggerBombBuy.setText("Bigger bomb");
		biggerBombBuy.setActionCommand("biggerBombB");
		biggerBombBuy.addActionListener(this);
		biggerBombBuy.setEnabled(false);
		biggerBombBuy.setBounds(870, 500, 100, 50);
		add(biggerBombBuy);
		
		returnButton = new JButton();
		returnButton.setFocusable(false);
		returnButton.setText("Return");
		returnButton.setActionCommand("Return");
		returnButton.addActionListener(this);
		returnButton.setBounds(50, 25, 100, 50);
		add(returnButton);
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
				removeInventoryWeapon(s.ironAxe.getID());
			break;
		case "weapon2":
			player.gainCoin(s.steelAxe.getSellValue());
			s.steelAxe.setAmount(s.steelAxe.getAmount() - 1);
			if(s.steelAxe.getAmount() == 0)
				removeInventoryWeapon(s.steelAxe.getID());
			break;
			
		// Sell item buttons
		case "item1":
			player.gainCoin(s.healthPot.getSellValue());
			s.healthPot.setAmount(s.healthPot.getAmount() - 1);
			removeInventoryItem(s.healthPot.getID());
			break;
		case "item2":
			player.gainCoin(s.shield.getSellValue());
			s.shield.setAmount(s.shield.getAmount() - 1);
			removeInventoryItem(s.shield.getID());
			break;
		case "item3":
			player.gainCoin(s.bomb.getSellValue());
			s.bomb.setAmount(s.bomb.getAmount() - 1);
			removeInventoryItem(s.bomb.getID());
			break;
		case "item4":
			player.gainCoin(s.poisonDart.getSellValue());
			s.poisonDart.setAmount(s.poisonDart.getAmount() - 1);
			removeInventoryItem(s.poisonDart.getID());
			break;
		case "item5":
			player.gainCoin(s.bigBomb.getSellValue());
			s.bigBomb.setAmount(s.bigBomb.getAmount() - 1);
			removeInventoryItem(s.bigBomb.getID());
			break;
		case "item6":
			player.gainCoin(s.biggerBomb.getSellValue());
			s.biggerBomb.setAmount(s.biggerBomb.getAmount() - 1);
			removeInventoryItem(s.biggerBomb.getID());
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
		case "healthB":
			player.loseCoin(s.healthPot.getBuyValue());
			s.healthPot.setAmount(s.healthPot.getAmount() + 1);
			checkAvailability();
			break;
		}
	}
}

package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Player;
import constants.Storage;
import main.GameWindow;

@SuppressWarnings("serial")
public class Store extends JPanel implements ActionListener{
	private GameWindow window;
	private Storage s = Storage.getInstance();
	private JButton ironAxeBuy, returnButton;
	private Player player = new Player();
	private int xLoc = 100, yLoc = 300, itemsInRow = 0;
	private JButton bag[] = new JButton[s.steelAxe.getID()];
	
	public Store(GameWindow window) {
        this.window = window;
        
        setLayout(null);
        
        initComponents();
        checkAvailability();
        checkInventory();
	}
	
	private void checkAvailability() {
		if(player.getPlayerCoins() >= s.ironAxe.getCoinValue())
			ironAxeBuy.setEnabled(true);
	}
	
	private void checkInventory() {
		if(s.ironAxe.getIsOwned() > 0)
			createInventory(s.ironAxe.getID());
		if(s.steelAxe.getIsOwned() > 0)
			createInventory(s.steelAxe.getID());	
	}
	
	private void createInventory(int x) {
		String temp = "i";
		bag[x - 1] = new JButton();
		bag[x - 1].setFocusable(false);
		switch(x) {
		case 1:
			bag[x - 1].setText("IA");
			break;
		case 2:
			bag[x - 1].setText("SA");
			break;
		}
		bag[x - 1].setActionCommand(temp + x);
		bag[x - 1].addActionListener(this);
		bag[x - 1].setBounds(xLoc, yLoc, 50, 50);
		add(bag[x - 1]);	
		xLoc += 60;
		itemsInRow++;
		if(itemsInRow == 4) {
			xLoc = 100;
			yLoc += 60;
			itemsInRow = 0;
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
		case "i1":
			player.playerLoseCoin(s.ironAxe.getCoinValue());
			s.ironAxe.setIsOwned(s.ironAxe.getIsOwned() + 1);
			break;
		case "ironAxeS":
			player.playerGainCoin(s.ironAxe.getCoinSellValue());
			s.ironAxe.setIsOwned(s.ironAxe.getIsOwned() - 1);
			break;
		}
	}
}

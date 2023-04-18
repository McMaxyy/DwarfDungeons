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
import javax.swing.JPanel;

import com.google.gson.Gson;

import constants.Player;
import constants.Storage;
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
    private int xLoc = 900, yLoc = 400, itemsInRow = 0;
    private JButton bag[] = new JButton[s.steelAxe.getID()];
    private boolean bagVisible = false;

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
        bagButton.setBounds(900, 300, 100, 50);
        add(bagButton);	
	}

    private void checkInventory() {
		if(s.ironAxe.getIsOwned() > 0)
			createInventory(s.ironAxe.getID());
		if(s.steelAxe.getIsOwned() > 0)
			createInventory(s.steelAxe.getID());	
	}
	
	private void createInventory(int x) {
		String temp = "item";
		bag[x - 1] = new JButton();
		bag[x - 1].setFocusable(false);
		bag[x - 1].setVisible(false);
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
	
	private void showInventory() {
		for(int i = 0; i < bag.length; i++) {
			if(bag[i] != null)
				bag[i].setVisible(true);
		}
		bagVisible = true;
	}
	
	private void hideInventory() {
		for(int i = 0; i < bag.length; i++) {
			if(bag[i] != null)
				bag[i].setVisible(false);
		}
		bagVisible = false;
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
		saveS[0] = player.getPlayerMaxHP();
		saveS[1] = player.getPlayerStrength();
		saveS[2] = player.getPlayerCoins();
		saveS[3] = player.getPlayerLevel();
		saveS[4] = player.getPlayerExp();
		saveS[5] = player.getLevelCap();
		saveS[6] = s.ironAxe.getIsOwned();
		
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
        case "item1":
            player.setActiveWeapon(1);
            break;
        case "item2":
            player.setActiveWeapon(2);
            break;
        }
    }
}


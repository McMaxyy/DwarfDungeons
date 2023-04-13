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

    public HomeScreen(GameWindow window) {
        this.window = window;
        
        loadIcons();
        player.setUnlockedStage(1);
        
        setLayout(null);
        
        JButton menuButton = new JButton();  
        menuButton.setIcon(new ImageIcon(returnBtn));
        menuButton.setActionCommand("Return");
        menuButton.addActionListener(this);
        menuButton.setFocusable(false);
        menuButton.setOpaque(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.setBounds(25, 25, 100, 50);
        add(menuButton);
              
        JButton quitButton = new JButton();
        quitButton.setIcon(new ImageIcon(quitBtn));
        quitButton.setActionCommand("Quit");
        quitButton.setFocusable(false);
        quitButton.setOpaque(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.addActionListener(this);
        quitButton.setBounds(25, 80, 100, 50);
        add(quitButton);
        
        JButton saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(saveBtn));
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(this);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(this);
        saveButton.setBounds(25, 135, 100, 50);
        add(saveButton);
        
        JButton levelSelectButton = new JButton("Levels");
        levelSelectButton.setActionCommand("Levels");
        levelSelectButton.addActionListener(this);
        levelSelectButton.setBounds(200, 200, 100, 50);
        add(levelSelectButton);
        
        JButton slotMachineButton = new JButton("Slot");
        slotMachineButton.setActionCommand("Slot");
        slotMachineButton.addActionListener(this);
        slotMachineButton.setBounds(200, 300, 100, 50);
        add(slotMachineButton);
       
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
		saveS[6] = Storage.getInstance().ironAxe.getIsOwned();
		
		try (FileWriter writer = new FileWriter("savegame.json")) {
            gson.toJson(saveS, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Return")) {
        	window.showMainMenu();
        }
        else if (command.equals("Save")) {
            saveGame();
        }
        else if (command.equals("Quit")) {
            System.exit(0);
        }
        else if (command.equals("Levels")) {
            window.showLevelSelector(1);
        }
        else if (command.equals("Slot")) {
            window.showSlotMachine();
        }
    }
}


package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.google.gson.Gson;

import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class HomeScreen extends JPanel implements ActionListener {
	
    private GameWindow window;
    private Player player = new Player();
    Gson gson = new Gson();

    public HomeScreen(GameWindow window) {
        this.window = window;
        
        setLayout(null);
        
        JButton menuButton = new JButton("Main menu");  
        menuButton.setActionCommand("Main menu");
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 30);
        add(menuButton);
              
        JButton quitButton = new JButton("Quit");
        quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(25, 65, 100, 30);
        add(quitButton);
        
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
        
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(this);
        saveButton.setBounds(25, 105, 100, 30);
        add(saveButton);
    }

    private void saveGame() {
		int[] saveS = new int[7];
		saveS[0] = player.getPlayerMaxHP();
		saveS[1] = player.getPlayerStrength();
		saveS[2] = player.getPlayerCoins();
		saveS[3] = player.getPlayerLevel();
		saveS[4] = player.getPlayerExp();
		saveS[5] = player.getLevelCap();
		saveS[6] = player.getUnlockedStage();
		
		try (FileWriter writer = new FileWriter("savegame.json")) {
            gson.toJson(saveS, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Main menu")) {
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


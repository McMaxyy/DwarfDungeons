package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.google.gson.Gson;

import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class MainMenu extends JPanel implements ActionListener {
	
    private GameWindow window;
    Gson gson = new Gson();
    private Player player = new Player();

    public MainMenu(GameWindow window) {
        this.window = window;
        
        setLayout(null);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setBounds(590, 200, 100, 50);
        startButton.setFocusable(false);
        add(startButton);
        
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        loadButton.setBounds(590, 300, 100, 50);
        loadButton.setFocusable(false);
        add(loadButton);
        
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(590, 400, 100, 50);
        quitButton.setFocusable(false);
        add(quitButton);
                      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Start")) {
            window.showHomeScreen();
        } else if (command.equals("Load")) {
        	loadStats();
        	window.showHomeScreen();
        } else if (command.equals("Quit")) {
            System.exit(0);
        }
    }
    
    public void loadStats() {
    	try (FileReader reader = new FileReader("savegame.json")) {
            int[] intArray = gson.fromJson(reader, int[].class);
            for (int i : intArray) {
                System.out.println(i);
            }
            player.setPlayerMaxHP(intArray[0]);
            player.setPlayerStrength(intArray[1]);
            player.setPlayerCoins(intArray[2]);
            player.setPlayerLevel(intArray[3]);
            player.setPlayerExp(intArray[4]);
            player.setLevelCap(intArray[5]);
            player.setUnlockedStage(intArray[6]);
        } catch (IOException e) {
            e.printStackTrace();
        } 	
    }
}


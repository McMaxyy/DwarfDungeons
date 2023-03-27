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

public class MainMenu extends JPanel implements ActionListener {
	
    private GameWindow window;
    Gson gson = new Gson();
    private Player player = new Player();

    public MainMenu(GameWindow window) {
        this.window = window;
        
        setLayout(null);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setBounds(200, 200, 100, 50);
        add(startButton);
        
        JButton settingsButton = new JButton("Load");
        settingsButton.addActionListener(this);
        settingsButton.setBounds(200, 300, 100, 50);
        add(settingsButton);
        
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(200, 400, 100, 50);
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
            player.setTurnCount(intArray[2]);
            player.setPlayerCoins(intArray[3]);
            player.setPlayerLevel(intArray[4]);
            player.setPlayerExp(intArray[5]);
        } catch (IOException e) {
            e.printStackTrace();
        } 	
    }
}


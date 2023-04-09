package stages;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
    private Image startBtn, loadBtn, quitBtn;

    public MainMenu(GameWindow window) {
        this.window = window;
        
        loadIcons();
        
        setLayout(null);
        
        JButton startButton = new JButton();
        startButton.setActionCommand("Start");
        startButton.setIcon(new ImageIcon(startBtn));
        startButton.addActionListener(this);
        startButton.setFocusable(false);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setBounds(590, 200, 100, 50);
        startButton.setFocusable(false);
        add(startButton);
        
        JButton loadButton = new JButton();
        loadButton.setActionCommand("Load");
        loadButton.setIcon(new ImageIcon(loadBtn));
        loadButton.addActionListener(this);
        loadButton.setFocusable(false);
        loadButton.setOpaque(false);
        loadButton.setContentAreaFilled(false);
        loadButton.setBorderPainted(false);
        loadButton.setBounds(590, 300, 100, 50);
        loadButton.setFocusable(false);
        add(loadButton);
        
        JButton quitButton = new JButton();
        quitButton.setActionCommand("Quit");
        quitButton.setIcon(new ImageIcon(quitBtn));
        quitButton.addActionListener(this);
        quitButton.setFocusable(false);
        quitButton.setOpaque(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.setBounds(590, 400, 100, 50);
        quitButton.setFocusable(false);
        add(quitButton);
                      
    }
    
    private void loadIcons() {
		try {
			startBtn = ImageIO.read(new File("res/FunctionButtons/StartButton.png"));
			loadBtn = ImageIO.read(new File("res/FunctionButtons/LoadButton.png"));
			quitBtn = ImageIO.read(new File("res/FunctionButtons/QuitButton.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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


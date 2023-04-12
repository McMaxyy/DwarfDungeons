package stages;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.google.gson.Gson;

import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class MainMenu extends JPanel implements ActionListener {
	
    private GameWindow window;
    Gson gson = new Gson();
    private Player player = new Player();
    private Image startBtn, loadBtn, quitBtn, backgroundImg;
    private JButton loadFailed, startButton, loadButton, quitButton, yesLoad, noLoad;
    private Font font;
    private JLabel loadGame;

    public MainMenu(GameWindow window) {
        this.window = window;
        
        loadIcons();
        createFont();
        
        setLayout(null); 
        
        startButton = new JButton();
        startButton.setActionCommand("Start");
        startButton.setIcon(new ImageIcon(startBtn));
        startButton.addActionListener(this);
        startButton.setFocusable(false);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setBounds(590, 350, 100, 50);
        startButton.setFocusable(false);
        add(startButton);
        
        loadButton = new JButton();
        loadButton.setActionCommand("Load");
        loadButton.setIcon(new ImageIcon(loadBtn));
        loadButton.addActionListener(this);
        loadButton.setFocusable(false);
        loadButton.setOpaque(false);
        loadButton.setContentAreaFilled(false);
        loadButton.setBorderPainted(false);
        loadButton.setBounds(590, 438, 100, 50);
        loadButton.setFocusable(false);
        add(loadButton);
        
        quitButton = new JButton();
        quitButton.setActionCommand("Quit");
        quitButton.setIcon(new ImageIcon(quitBtn));
        quitButton.addActionListener(this);
        quitButton.setFocusable(false);
        quitButton.setOpaque(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.setBounds(590, 524, 100, 50);
        quitButton.setFocusable(false);
        add(quitButton);
        
        loadFailed = new JButton();
        loadFailed.setFocusable(false);
        loadFailed.setVisible(false);
        loadFailed.setBackground(Color.WHITE);
        loadFailed.setOpaque(true);
        loadFailed.setBorderPainted(false);
        loadFailed.setText("No save files found");
        loadFailed.setActionCommand("Failed");
        loadFailed.addActionListener(this);
        loadFailed.setFont(font);
        loadFailed.setHorizontalAlignment(SwingConstants.CENTER);
        loadFailed.setVerticalAlignment(SwingConstants.CENTER);
        loadFailed.setBounds(500, 350, 280, 100);
        add(loadFailed);
        
        yesLoad = new JButton();
        yesLoad.setFocusable(false);
        yesLoad.setVisible(false);
        yesLoad.setBackground(Color.GRAY);
        yesLoad.setForeground(Color.WHITE);
        yesLoad.setOpaque(true);
        yesLoad.setText("Yes");
        yesLoad.setActionCommand("Yes");
        yesLoad.addActionListener(this);
        yesLoad.setFont(font);
        yesLoad.setHorizontalAlignment(SwingConstants.CENTER);
        yesLoad.setVerticalAlignment(SwingConstants.CENTER);
        yesLoad.setBorderPainted(false);
        yesLoad.setBounds(520, 438, 100, 50);
        add(yesLoad);
        
        noLoad = new JButton();
        noLoad.setFocusable(false);
        noLoad.setVisible(false);
        noLoad.setBackground(Color.GRAY);
        noLoad.setForeground(Color.WHITE);
        noLoad.setOpaque(true);
        noLoad.setText("No");
        noLoad.setActionCommand("No");
        noLoad.addActionListener(this);
        noLoad.setFont(font);
        noLoad.setHorizontalAlignment(SwingConstants.CENTER);
        noLoad.setVerticalAlignment(SwingConstants.CENTER);
        noLoad.setBorderPainted(false);
        noLoad.setBounds(656, 438, 100, 50);
        add(noLoad);
        
        loadGame = new JLabel();
        loadGame.setVisible(false);
        loadGame.setText("Load game?");
        loadGame.setFont(font);
        loadGame.setForeground(Color.WHITE);
        loadGame.setHorizontalAlignment(SwingConstants.CENTER);
        loadGame.setVerticalAlignment(SwingConstants.CENTER);
        loadGame.setBounds(540, 350, 200, 100);
        add(loadGame);
                      
    }
    
    private void loadIcons() {
		try {
			startBtn = ImageIO.read(new File("res/FunctionButtons/StartButton.png"));
			loadBtn = ImageIO.read(new File("res/FunctionButtons/LoadButton.png"));
			quitBtn = ImageIO.read(new File("res/FunctionButtons/QuitButton.png"));
			backgroundImg = ImageIO.read(new File("res/Backgrounds/Menu_BG.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    private void createFont() {
		try {
		    //create the font to use. Specify the size!
		    font = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(20f);		
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(font);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	}

    @Override
    public void actionPerformed(ActionEvent e) {
    	String command = e.getActionCommand();
    	
    	switch (command) {
    	    case "Start":
    	        window.showHomeScreen();
    	        break;
    	    case "Load":
    	        loadStats();
    	        hideButtons();
    	        break;
    	    case "Quit":
    	        System.exit(0);
    	        break;
    	    case "Failed":
    	        loadFailed.setVisible(false);
    	        startButton.setVisible(true);
    	        loadButton.setVisible(true);
    	        quitButton.setVisible(true);
    	        break;
    	    case "Yes":
    	        window.showHomeScreen();
    	        break;
    	    case "No":
    	        loadGame.setVisible(false);
    	        yesLoad.setVisible(false);
    	        noLoad.setVisible(false);
    	        startButton.setVisible(true);
    	        loadButton.setVisible(true);
    	        quitButton.setVisible(true);
    	        break;
    	}   	
    }
    
    public void hideButtons() {
    	startButton.setVisible(false);
    	loadButton.setVisible(false);
    	quitButton.setVisible(false);
    }
    
    public void loadStats() {
    	try (FileReader reader = new FileReader("savegame.json")) {
            int[] intArray = gson.fromJson(reader, int[].class);

            player.setPlayerMaxHP(intArray[0]);
            player.setPlayerStrength(intArray[1]);
            player.setPlayerCoins(intArray[2]);
            player.setPlayerLevel(intArray[3]);
            player.setPlayerExp(intArray[4]);
            player.setLevelCap(intArray[5]);
            loadGame.setVisible(true);
        	yesLoad.setVisible(true);
        	noLoad.setVisible(true);
        } catch (IOException e) {
        	loadFailed.setVisible(true);
        } 	
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	// Draw background
	    g.drawImage(backgroundImg, 0, 0, null);
    }
}


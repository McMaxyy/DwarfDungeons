package stages;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import inventory.Storage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import main.GameWindow;

@SuppressWarnings("serial")
public class Mining extends JPanel implements ActionListener, KeyListener{
	private GameWindow window;
	private Storage s = Storage.getInstance();
	private JLabel letterArray[] = new JLabel[12];
	private JButton openGame, material, startMining, returnButton;
	private int arrayLoc = 0, arrayX = 200;
	private Random rand = new Random();
	private JLabel winText, loseText, countdownLbl, timesHitLbl, left, right;
	private int countdown = 5, y, x, timesReqToHit;
	private Timer timer, fallingTimer;
	private boolean timerStart, matClicked, minigameActive;
	private Image ironIngot, goldIngot, lifePowder, sparkPowder;
	
	public Mining(GameWindow window) {
		this.window = window;
		timesReqToHit = rand.nextInt(10, 21);		
		
		setLayout(null);
		setFocusable(true);
		addKeyListener(this);
		
		initComponents();
		loadIcons();
		
		fallingTimer = new Timer(1, this);
	}

	private void initComponents() {		
		returnButton = new JButton();
		returnButton.setText("Return");
		returnButton.setFocusable(false);
		returnButton.setActionCommand("Return");
		returnButton.addActionListener(this);
		returnButton.setBounds(25, 25, 100, 50);
		add(returnButton);
		
		startMining = new JButton();
		startMining.setText("Start");
		startMining.setActionCommand("Start");
		startMining.addActionListener(this);
		startMining.setBounds(50, 100, 100, 50);
		add(startMining);	
		
		left = new JLabel();
		left.setText("Left");
		left.setVisible(false);
		left.setBounds(50, 300, 80, 60);
		add(left);
		
		right = new JLabel();
		right.setText("Right");
		right.setVisible(false);
		right.setBounds(150, 300, 80, 60);
		add(right);
		
		timesHitLbl = new JLabel();
		timesHitLbl.setVisible(false);
		timesHitLbl.setText("Hit rock " + timesReqToHit + " times");
		timesHitLbl.setBounds(50, 250, 100, 20);
		add(timesHitLbl);
		
		openGame = new JButton();
		openGame.setText("Open");
		openGame.setVisible(false);
		openGame.setActionCommand("Open");
		openGame.addActionListener(this);
		openGame.setBounds(50, 100, 100, 50);
		add(openGame);	
		
		loseText = new JLabel("You lost");
		loseText.setVisible(false);
		loseText.setBounds(200, 200, 100, 50);
		add(loseText);
		
		winText = new JLabel("You won");
		winText.setVisible(false);
		winText.setBounds(200, 200, 100, 50);
		add(winText);
		
		countdownLbl = new JLabel("Time: " + countdown);
		countdownLbl.setBounds(200, 50, 100, 50);
		countdownLbl.setVisible(false);
		add(countdownLbl);
		
		this.requestFocusInWindow();
	}
	
	private void loadIcons() {
		try {
			ironIngot = ImageIO.read(new File("res/Materials/IronIngot.png"));
			goldIngot = ImageIO.read(new File("res/Materials/GoldIngot.png"));
			sparkPowder = ImageIO.read(new File("res/Materials/SparkPowder.png"));
			lifePowder = ImageIO.read(new File("res/Materials/LifePowder.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void startMining() {
		minigameActive = false;
		timesReqToHit = rand.nextInt(10, 21);	
		this.requestFocusInWindow();
		left.setVisible(true);
		startMining.setVisible(false);
		timesHitLbl.setText("Hit rock " + timesReqToHit + " times");
		timesHitLbl.setVisible(true);
	}
	
	private void startGame() {
		minigameActive = true;
		countdownLbl.setVisible(true);
		countdown = 5;
		countdownLbl.setText("Time: " + countdown); 
		timer = new Timer(1000, new ActionListener() { // initialize the Timer with a 1-second delay
			@Override
			public void actionPerformed(ActionEvent e) {
				if (winText.isVisible()) { // if the winText is visible, stop the timer
					timer.stop();
				} else {
					countdown--; // decrement the countdown variable
					countdownLbl.setText("Time: " + countdown);
					if (countdown == 0) { // if countdown reaches 0, call gameLost()
						gameLost();
					}
				}
			}
		});
		
		for(int i = 0; i < letterArray.length; i++) {
			letterArray[i] = new JLabel();
			int x = rand.nextInt(4);
			letterArray[i].setFont(s.font2);
			if(x == 0) 
				letterArray[i].setText("A");
			else if(x == 1)
				letterArray[i].setText("S");
			else if(x == 2)
				letterArray[i].setText("D");
			else 
				letterArray[i].setText("W");
			
			letterArray[i].setBounds(arrayX, 100, 50, 50);
			letterArray[i].setBorder(BorderFactory.createLineBorder(Color.black));
			letterArray[i].setHorizontalAlignment(SwingConstants.CENTER);
			letterArray[i].setVerticalAlignment(SwingConstants.CENTER);
			add(letterArray[i]);
			repaint();
			arrayX += 50;
		}
		
		this.requestFocusInWindow();		
	}
	
	private void gameWon() {
		if(!letterArray[letterArray.length - 1].isEnabled()) {
			for(int i = 0; i < letterArray.length; i++) {
				letterArray[i].setVisible(false);
			}
			arrayLoc = 0;
			arrayX = 200;
			winText.setVisible(true);
			openGame.setVisible(false);
			openGame.setEnabled(true);
			startMining.setVisible(true);
			repaint();
			timerStart = false;
			countdownLbl.setVisible(false);
			
			if(material.getName() == "II") {
				s.ironIngot.setAmount(s.ironIngot.getAmount() + 1);
				winText.setText("Gained 1x Iron Ingot");
			}
			else if(material.getName() == "GI") {
				s.goldIngot.setAmount(s.goldIngot.getAmount() + 1);
				winText.setText("Gained 1x Gold Ingot");
			}
			else if(material.getName() == "SP") {
				s.sparkPowder.setAmount(s.sparkPowder.getAmount() + 1);
				winText.setText("Gained 1x Spark Powder");
			}
			else {
				s.lifePowder.setAmount(s.lifePowder.getAmount() + 1);
				winText.setText("Gained 1x Life Powder");
			}
		}
	}
	
	private void update() {
	    if (!matClicked) {
	        y += 4;
	        material.setLocation(x, y);

	        if (y + material.getHeight() > getHeight()) {
	            fallingTimer.stop();
	        } 
	    }
	}

	private void gameLost() {
		for(int i = 0; i < letterArray.length; i++) {
			letterArray[i].setVisible(false);
		}
		loseText.setVisible(true);
		startMining.setVisible(true);
		openGame.setVisible(false);
		openGame.setEnabled(true);
		repaint();
		timerStart = false;
		timer.stop();
		arrayLoc = 0;
		arrayX = 200;
		
		countdownLbl.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch(command) {
		case "Open":
			timesHitLbl.setVisible(false);
			openGame.setEnabled(false);
			matClicked = false;
			
			// Spawn material
			x = rand.nextInt(400, 1230);
			y = -50;
			
			if(material != null)
				remove(material);
			
			int x = rand.nextInt(4);
			material = new JButton();
			if(x == 0) {
				material.setName("II");
				material.setIcon(new ImageIcon(ironIngot));
			}
			else if(x == 1) {
				material.setName("GI");
				material.setIcon(new ImageIcon(goldIngot));
			}
			else if(x == 2) {
				material.setName("SP");
				material.setIcon(new ImageIcon(sparkPowder));
			}
			else {
				material.setName("LP");
				material.setIcon(new ImageIcon(lifePowder));
			}
			material.setBorderPainted(false);
			material.setContentAreaFilled(false);
			material.setSize(50, 50);
			material.setActionCommand("Mat");
			material.addActionListener(this);
			add(material);
			
			material.setLocation(x, y);
			fallingTimer.start();		
			fallingTimer.setActionCommand("Timer");
			update();
			break;
		case "Start":
			startMining();
			winText.setVisible(false);
			loseText.setVisible(false);
			break;
		case "Mat":
			matClicked = true;
			timerStart = true;
			winText.setVisible(false);
			loseText.setVisible(false);
			remove(material);
			repaint();
			startGame();
			break;
		case "Timer":
			update();
			break;
		case "Return":
			window.showHomeScreen();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int command = e.getKeyCode();
		
		switch(command) {
		case KeyEvent.VK_A:
			if(minigameActive) {
				if(letterArray[arrayLoc].getText() == "A") {
					if(arrayLoc == 0)
						timer.start();
					letterArray[arrayLoc].setEnabled(false);
					arrayLoc++;
					gameWon();
				}
				else
					gameLost();
			}
			else {
				if(left.isVisible()) {
					left.setVisible(false);
					timesReqToHit--;
					timesHitLbl.setText("Hit rock " + timesReqToHit + " times");
				}
				if(timesReqToHit == 0)
					openGame.setVisible(true);
				else
					right.setVisible(true);
			}
			break;
		case KeyEvent.VK_S:
			if(letterArray[arrayLoc].getText() == "S") {
				if(arrayLoc == 0)
					timer.start();
				letterArray[arrayLoc].setEnabled(false);
				arrayLoc++;
				gameWon();
			}
			else
				gameLost();
			break;
		case KeyEvent.VK_D:
			if(minigameActive) {
				if(letterArray[arrayLoc].getText() == "D") {
					if(arrayLoc == 0)
						timer.start();
					letterArray[arrayLoc].setEnabled(false);
					arrayLoc++;
					gameWon();
				}
				else
					gameLost();
			}
			else {
				if(right.isVisible()) {
					right.setVisible(false);
					timesReqToHit--;
					timesHitLbl.setText("Hit rock " + timesReqToHit + " times");
				}
				if(timesReqToHit == 0)
					openGame.setVisible(true);
				else
					left.setVisible(true);
			}
			break;
		case KeyEvent.VK_W:
			if(letterArray[arrayLoc].getText() == "W") {
				if(arrayLoc == 0)
					timer.start();
				letterArray[arrayLoc].setEnabled(false);
				arrayLoc++;
				gameWon();
			}
			else
				gameLost();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		float time;
		int width;
		
		if(timerStart) {
			g.setColor(Color.WHITE);
			g.fillRect(200, 50, 100, 10);
			time = (float)countdown / 10;
			width = (int)(time * 200);
			width = (width / 10) * 10;			
			if(width < 10)
				width = 0;
			g.setColor(Color.gray);
			g.fillRect(200, 50, width, 10);
		}
	}
}

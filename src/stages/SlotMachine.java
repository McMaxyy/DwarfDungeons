package stages;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class SlotMachine extends JPanel implements ActionListener {

	private int rows = 3, columns = 3;
    private GameWindow window;
    private JButton quitButton, menuButton, spinButton, changeCostButton, pay2Coin, pay3Coin, pay6Coin;
    private JLabel[][] slots = new JLabel[rows][columns];
    private Random rand = new Random();
    private int slotLvl, x;
    private Border blackline;
    private Font f;
    private JLabel coins = new JLabel();
    private JLabel winnings = new JLabel();
    private Player player = new Player();
    private int roundWinnings, spinCost;
    private boolean lineR1, lineR2, lineR3, lineDia1, lineDia2;
	
	public SlotMachine(GameWindow window) {
		this.window = window;
		spinCost = 2;
		
		f = new Font("Serif", Font.BOLD, 48);
		blackline = BorderFactory.createLineBorder(Color.black);
			
		setLayout(null);			
		
		initializeComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Quit")) {
            System.exit(0);
        }
		else if (command.equals("Return")) {
        	window.showHomeScreen();
        }
		else if (command.equals("Spin")) {
			player.loseCoin(spinCost);
			removeAll();
			initializeComponents();
        	spinSlot();
        }
		else if (command.equals("Change cost")) {
        	showCostChange();
        }
		else if (command.equals("Coin2")) {
			spinCost = 2;
        	hideCostChange();
        	repaintButtonText();
        }
		else if (command.equals("Coin3")) {
			spinCost = 3;
			hideCostChange();
			repaintButtonText();
        }
		else if (command.equals("Coin6")) {
			spinCost = 6;
			hideCostChange();
			repaintButtonText();
        }
	}
	
	private void spinSlot() {
				
		for(int i = 0; i < 3; i++) {
			slotLvl = 1;
			x = rand.nextInt(3);
			slots[x][i] = new JLabel("A");
			slotLvl++;
			
			for(int j = 0; j < 2; j++) {
				while(slots[x][i] != null) {
					x = rand.nextInt(3);					
				}
				switch(slotLvl) {
					case 2:
						slots[x][i] = new JLabel("B");
						slotLvl++;
						break;
					case 3:
						slots[x][i] = new JLabel("7");
						break;
				}
			}
		}

	for(int i = 0; i < rows; i++)
		for(int j = 0; j < columns; j++) {
			slots[i][j].setBorder(blackline);
			slots[i][j].setFont(f);
			slots[i][j].setHorizontalAlignment(SwingConstants.CENTER);
			slots[i][j].setVerticalAlignment(SwingConstants.CENTER);
		}
		
		slots[0][0].setBounds(10, 200, 150, 100);
		slots[0][1].setBounds(175, 200, 150, 100);
		slots[0][2].setBounds(340, 200, 150, 100);
		slots[1][0].setBounds(10, 310, 150, 100);
		slots[1][1].setBounds(175, 310, 150, 100);
		slots[1][2].setBounds(340, 310, 150, 100);
		slots[2][0].setBounds(10, 420, 150, 100);
		slots[2][1].setBounds(175, 420, 150, 100);
		slots[2][2].setBounds(340, 420, 150, 100);
		
		checkWinPatterns();
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				add(slots[i][j]);
		
		repaint();		
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++) {
				slots[i][j] = null;
			}
		
		if(player.getCoins() < spinCost)
			spinButton.setEnabled(false);
	}
	
	private void initializeComponents() {
		quitButton = new JButton();
		quitButton.setFocusable(false);
		quitButton.setText("Quit");
		quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(375, 25, 100, 50);
        add(quitButton);		
		
        menuButton = new JButton();
        menuButton.setFocusable(false);
        menuButton.setActionCommand("Return");
        menuButton.setText("Return");
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 50);
        add(menuButton);
        
        spinButton = new JButton();
        spinButton.setFocusable(false);
        if(player.getCoins() <= 0)
			spinButton.setEnabled(false);
        else
        	spinButton.setEnabled(true);
        spinButton.setActionCommand("Spin");
        spinButton.setText("Spin (-" + spinCost + ")");
        spinButton.addActionListener(this);
        spinButton.setBounds(200, 600, 100, 50);
        add(spinButton);
        
        changeCostButton = new JButton();
        changeCostButton.setFocusable(false);
        changeCostButton.setActionCommand("Change cost");
        changeCostButton.setText("Spin cost: " + spinCost);
        changeCostButton.addActionListener(this);
        changeCostButton.setBounds(350, 600, 100, 30);
        add(changeCostButton);
        
        pay2Coin = new JButton();
        pay2Coin.setFocusable(false);
        pay2Coin.setVisible(false);
        pay2Coin.setActionCommand("Coin2");
        pay2Coin.setText("2");
        pay2Coin.addActionListener(this);
        pay2Coin.setBounds(320, 600, 50, 30);
        add(pay2Coin);
        
        pay3Coin = new JButton();
        pay3Coin.setFocusable(false);
        pay3Coin.setVisible(false);
        pay3Coin.setActionCommand("Coin3");
        pay3Coin.setText("3");
        pay3Coin.addActionListener(this);
        pay3Coin.setBounds(380, 600, 50, 30);
        add(pay3Coin);
        
        pay6Coin = new JButton();
        pay6Coin.setFocusable(false);
        pay6Coin.setVisible(false);
        pay6Coin.setActionCommand("Coin6");
        pay6Coin.setText("6");
        pay6Coin.addActionListener(this);
        pay6Coin.setBounds(440, 600, 50, 30);
        add(pay6Coin);
        
        coins.setText("Coins: " + player.getCoins());
        coins.setBounds(200, 650, 100, 50);
        add(coins);
        
        winnings.setBounds(200, 550, 100, 50);
        winnings.setVisible(false);
        add(winnings);
	}
	
	private void checkWinPatterns() {		
		
		if(slots[0][0].getText() == "A" && slots[0][1].getText() == "A" && slots[0][2].getText() == "A") {
			payPlayer(2);
			lineR1 = true;
		}
		else if(slots[1][0].getText() == "A" && slots[1][1].getText() == "A" && slots[1][2].getText() == "A") {
			payPlayer(5);
			lineR2 = true;
		}
		else if(slots[2][0].getText() == "A" && slots[2][1].getText() == "A" && slots[2][2].getText() == "A") { 
			payPlayer(2);
			lineR3 = true;
		}
		else if(slots[0][0].getText() == "A" && slots[1][1].getText() == "A" && slots[2][2].getText() == "A") { 
			payPlayer(2);
			lineDia1 = true;
		}
		else if(slots[2][0].getText() == "A" && slots[1][1].getText() == "A" && slots[0][2].getText() == "A") { 
			payPlayer(2);
			lineDia2 = true;
		}
		
		if(slots[0][0].getText() == "B" && slots[0][1].getText() == "B" && slots[0][2].getText() == "B") {
			payPlayer(2);
			lineR1 = true;
		}
		else if(slots[1][0].getText() == "B" && slots[1][1].getText() == "B" && slots[1][2].getText() == "B") {
			payPlayer(5);
			lineR2 = true;
		}
		else if(slots[2][0].getText() == "B" && slots[2][1].getText() == "B" && slots[2][2].getText() == "B") {
			payPlayer(2);
			lineR3 = true;
		}
		else if(slots[0][0].getText() == "B" && slots[1][1].getText() == "B" && slots[2][2].getText() == "B") { 
			payPlayer(2);
			lineDia1 = true;
		}
		else if(slots[2][0].getText() == "B" && slots[1][1].getText() == "B" && slots[0][2].getText() == "B") { 
			payPlayer(2);
			lineDia2 = true;
		}
		
		if(slots[0][0].getText() == "7" && slots[0][1].getText() == "7" && slots[0][2].getText() == "7") {
			payPlayer(5);
			lineR1 = true;
		}
		else if(slots[1][0].getText() == "7" && slots[1][1].getText() == "7" && slots[1][2].getText() == "7") { 
			payPlayer(10);
			lineR2 = true;
		}
		else if(slots[2][0].getText() == "7" && slots[2][1].getText() == "7" && slots[2][2].getText() == "7") { 
			payPlayer(5);
			lineR3 = true;
		}
		
		if(roundWinnings != 0) {
			winnings.setText("You won " + roundWinnings + " coins");
			winnings.setVisible(true);
		}
		coins.setText("Coins: " + player.getCoins());
		
		roundWinnings = 0;
		
		if(player.getCoins() >= spinCost)
        	spinButton.setEnabled(true);
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D)g;
        g1.setStroke(new BasicStroke(3));
        
        if(lineR1) {
        	g1.drawLine(10, 250, 490, 250);
        	lineR1 = false;
        }
        if(lineR2) {
        	g1.drawLine(10, 360, 490, 360);
        	lineR2 = false;
        }
        if(lineR3) {
        	g1.drawLine(10, 470, 490, 470);
        	lineR3 = false;
        }
        if(lineDia1) {
        	g1.drawLine(10, 200, 490, 520);
        	lineDia1 = false;
        }
        if(lineDia2) {
        	g1.drawLine(10, 520, 490, 200);
        	lineDia2 = false;
        }               
    }
	
	private void showCostChange() {
		changeCostButton.setVisible(false);
		pay2Coin.setVisible(true);
		pay3Coin.setVisible(true);
		pay6Coin.setVisible(true);
	}
	
	private void hideCostChange() {
		changeCostButton.setVisible(true);
		pay2Coin.setVisible(false);
		pay3Coin.setVisible(false);
		pay6Coin.setVisible(false);
		if(player.getCoins() >= spinCost)
        	spinButton.setEnabled(true);
	}
	
	private void repaintButtonText() {
		spinButton.setText("Spin (-" + spinCost + ")");
		changeCostButton.setText("Spin cost: " + spinCost);
	}
	
	private void payPlayer(int payout) {
		if(spinCost == 2) {
			player.gainCoin(payout);
			roundWinnings += payout;
		}		
		else if(spinCost == 3) {
			player.gainCoin(payout * 2);
			roundWinnings += payout * 2;
		}
		else if(spinCost == 6) {
			player.gainCoin(payout * 3);
			roundWinnings += payout * 3;
		}
	}
}

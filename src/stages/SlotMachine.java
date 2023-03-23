package stages;

import java.awt.Color;
import java.awt.Font;
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

public class SlotMachine extends JPanel implements ActionListener {

	private int rows = 3, columns = 3;
    private GameWindow window;
    private JButton quitButton, menuButton, spinButton;
    private JLabel[][] slots = new JLabel[rows][columns];
    private Random rand = new Random();
    private int slotLvl, x;
    private Border blackline;
    private Font f;
    private JLabel coins = new JLabel();
    private JLabel winnings = new JLabel();
    private Player player = new Player();
    private int roundWinnings;
	
	public SlotMachine(GameWindow window) {
		this.window = window;
		
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
		else if (command.equals("Main menu")) {
        	window.showMainMenu();
        }
		else if (command.equals("Spin")) {			
			removeAll();
			initializeComponents();
        	spinSlot();
        }		
	}
	
	public void spinSlot() {
		
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
	}
	
	public void initializeComponents() {
		quitButton = new JButton();
		quitButton.setText("Quit");
		quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(375, 25, 100, 30);
        add(quitButton);		
		
        menuButton = new JButton();
        menuButton.setActionCommand("Main menu");
        menuButton.setText("Main menu");
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 30);
        add(menuButton);
        
        spinButton = new JButton();
        spinButton.setActionCommand("Spin");
        spinButton.setText("Spin");
        spinButton.addActionListener(this);
        spinButton.setBounds(200, 600, 100, 50);
        add(spinButton);
        
        coins.setText("Coins: " + player.getPlayerCoins());
        coins.setBounds(200, 650, 100, 50);
        add(coins);
        
        winnings.setBounds(200, 550, 100, 50);
        winnings.setVisible(false);
        add(winnings);
	}
	
	public void checkWinPatterns() {
		
		if(slots[0][0].getText() == "A" && slots[0][1].getText() == "A" && slots[0][2].getText() == "A") {
			player.playerGainCoin(3);
			roundWinnings += 3;
		}
		else if(slots[1][0].getText() == "A" && slots[1][1].getText() == "A" && slots[1][2].getText() == "A") {
			player.playerGainCoin(6);
			roundWinnings += 6;
		}
		else if(slots[2][0].getText() == "A" && slots[2][1].getText() == "A" && slots[2][2].getText() == "A") { 
			player.playerGainCoin(3);
			roundWinnings += 3;
		}
		
		if(slots[0][0].getText() == "B" && slots[0][1].getText() == "B" && slots[0][2].getText() == "B") {
			player.playerGainCoin(3);
			roundWinnings += 3;
		}
		else if(slots[1][0].getText() == "B" && slots[1][1].getText() == "B" && slots[1][2].getText() == "B") {
			player.playerGainCoin(6);
			roundWinnings += 6;
		}
		else if(slots[2][0].getText() == "B" && slots[2][1].getText() == "B" && slots[2][2].getText() == "B") {
			player.playerGainCoin(3);
			roundWinnings += 3;
		}
		
		if(slots[0][0].getText() == "7" && slots[0][1].getText() == "7" && slots[0][2].getText() == "7") {
			player.playerGainCoin(6);
			roundWinnings += 6;
		}
		else if(slots[1][0].getText() == "7" && slots[1][1].getText() == "7" && slots[1][2].getText() == "7") { 
			player.playerGainCoin(12);
			roundWinnings += 12;
		}
		else if(slots[2][0].getText() == "7" && slots[2][1].getText() == "7" && slots[2][2].getText() == "7") { 
			player.playerGainCoin(6);
			roundWinnings += 6;
		}
		
		if(roundWinnings != 0) {
			winnings.setText("You won " + roundWinnings + " coins");
			winnings.setVisible(true);
		}
		coins.setText("Coins: " + player.getPlayerCoins());
		
		roundWinnings = 0;
	}
}

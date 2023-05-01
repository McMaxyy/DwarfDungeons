package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Player;
import inventory.Storage;
import main.GameWindow;

public class Blacksmith extends JPanel implements ActionListener{
	private GameWindow window;
	private Storage s = Storage.getInstance();
	private Player player = new Player();
	private JButton ironAxe, coin, bomb, healthPotion, returnButton;
	
	public Blacksmith(GameWindow window) {
		this.window = window;
		
		setLayout(null);
		
		initComponents();
		checkAvailability();
	}

	private void checkAvailability() {
		if(s.ironIngot.getAmount() >= s.ironAxeR.getReqMat1())
			ironAxe.setEnabled(true);
		else
			ironAxe.setEnabled(false);
		
		if(s.goldIngot.getAmount() >= s.coinR.getReqMat1())
			coin.setEnabled(true);
		else
			coin.setEnabled(false);
		
		if(s.sparkPowder.getAmount() >= s.bombR.getReqMat1())
			bomb.setEnabled(true);
		else
			bomb.setEnabled(false);
		
		if(s.lifePowder.getAmount() >= s.healthPotR.getReqMat1())
			healthPotion.setEnabled(true);
		else
			healthPotion.setEnabled(false);
	}
	
	private void initComponents() {
		returnButton = new JButton();
		returnButton.setText("Return");
		returnButton.setFocusable(false);
		returnButton.setActionCommand("Return");
		returnButton.addActionListener(this);
		returnButton.setBounds(50, 50, 100, 50);
		add(returnButton);
		
		ironAxe = new JButton();
		ironAxe.setText("IronAxe");
		ironAxe.setFocusable(false);
		ironAxe.setActionCommand("IronAxe");
		ironAxe.addActionListener(this);
		ironAxe.setBounds(250, 150, 100, 50);
		add(ironAxe);
		
		coin = new JButton();
		coin.setText("Coins");
		coin.setFocusable(false);
		coin.setActionCommand("Coins");
		coin.addActionListener(this);
		coin.setBounds(350, 150, 100, 50);
		add(coin);
		
		bomb = new JButton();
		bomb.setText("Bomb");
		bomb.setFocusable(false);
		bomb.setActionCommand("Bomb");
		bomb.addActionListener(this);
		bomb.setBounds(250, 200, 100, 50);
		add(bomb);
		
		healthPotion = new JButton();
		healthPotion.setText("HP");
		healthPotion.setFocusable(false);
		healthPotion.setActionCommand("HP");
		healthPotion.addActionListener(this);
		healthPotion.setBounds(350, 200, 100, 50);
		add(healthPotion);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch(command) {
		case "Return":
			window.showHomeScreen();
			break;
		case "IronAxe":
			s.ironIngot.setAmount(s.ironIngot.getAmount() - s.ironAxeR.getReqMat1());
			s.ironAxe.setAmount(s.ironAxe.getAmount() + 1);
			checkAvailability();
			break;
		case "Coins":
			player.setCoins(player.getCoins() + 5);
			s.goldIngot.setAmount(s.goldIngot.getAmount() - s.coinR.getReqMat1());
			checkAvailability();
			break;
		case "Bomb":
			s.bomb.setAmount(s.bomb.getAmount() + 1);
			s.sparkPowder.setAmount(s.sparkPowder.getAmount() - s.bombR.getReqMat1());
			checkAvailability();
			break;
		case "HP":
			s.healthPot.setAmount(s.healthPot.getAmount() + 1);
			s.lifePowder.setAmount(s.lifePowder.getAmount() - s.healthPotR.getReqMat1());
			checkAvailability();
			break;
		}
		
	}
}

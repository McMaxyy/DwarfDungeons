package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class Bonfire extends JPanel implements ActionListener{
	
	private GameWindow window;
	private int currentLevel;
	private JButton healthPot, strengthUp, returnButton;
	private Player player = new Player();
	
	public Bonfire(GameWindow window, int levelIndex) {
		this.window = window;
		currentLevel = levelIndex;
		
		setLayout(null);
		
		initComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if(command.equals("HP")) {
			player.increaseHP(5);
			if(player.getPlayerHP() > player.getPlayerMaxHP())
	        	player.setPlayerHP(player.getPlayerMaxHP());
			healthPot.setEnabled(false);
			strengthUp.setEnabled(false);
		}
		else if(command.equals("Str")) {
			player.increaseTempStr(1);
			healthPot.setEnabled(false);
			strengthUp.setEnabled(false);
		}
		else if (command.equals("Return")) {
			currentLevel++;
			window.showLevelSelector(currentLevel);
		}
		
	}
	
	private void initComponents() {
		returnButton = new JButton();
		returnButton.setActionCommand("Return");
		returnButton.setText("Return");
		returnButton.addActionListener(this);
		returnButton.setBounds(50, 50, 100, 50);
        add(returnButton);
		
		healthPot = new JButton();
		healthPot.setActionCommand("HP");
		healthPot.setText("HP");
		healthPot.addActionListener(this);
		healthPot.setBounds(300, 450, 150, 100);
        add(healthPot);
        
        strengthUp = new JButton();
        strengthUp.setActionCommand("Str");
        strengthUp.setText("Strength");
        strengthUp.addActionListener(this);
        strengthUp.setBounds(300, 290, 150, 100);
        add(strengthUp);
		
	}

}

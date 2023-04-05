package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.*;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Player;
import main.GameWindow;

public class Merchant extends JPanel implements ActionListener{
	
	private GameWindow window;
	private Player player = new Player();
	private JButton healthPot, ability1, ability2, ability3, active1, active2, active3, 
			returnButton;
	private String a1, a2, a3, abilityText;
	private int abilityID, currentLevel, x;
	private Random rand = new Random();
	
	public Merchant(GameWindow window, int levelIndex){
		this.window = window;
		currentLevel = levelIndex;
		x = rand.nextInt(1,7);
		
		switch(player.getAbility1ID()) {
		case 1:
			a1 = "Swing";
			break;
		case 2:
			a1 = "Decapitate";
			break;
		case 3:
			a1 = "Riposte";
			break;
		case 4:
			a1 = "Rend";
			break;
		case 5:
			a1 = "Harden";
			break;
		case 6:
			a1 = "Whirlwind";
			break;
		}
		
		switch(player.getAbility2ID()) {
		case 1:
			a2 = "Swing";
			break;
		case 2:
			a2 = "Decapitate";
			break;
		case 3:
			a2 = "Riposte";
			break;
		case 4:
			a2 = "Rend";
			break;
		case 5:
			a2 = "Harden";
			break;
		case 6:
			a2 = "Whirlwind";
			break;
		}
		
		switch(player.getAbility3ID()) {
		case 1:
			a3 = "Swing";
			break;
		case 2:
			a3 = "Decapitate";
			break;
		case 3:
			a3 = "Riposte";
			break;
		case 4:
			a3 = "Rend";
			break;
		case 5:
			a3 = "Harden";
			break;
		case 6:
			a3 = "Whirlwind";
			break;
		}
		
		setLayout(null);
		
		initComponents();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals("Return")) {
			currentLevel++;
			window.showLevelSelector(currentLevel);
		}			
		else if(command.equals("HP")) {
			player.increaseHP(5);
			healthPot.setEnabled(false);
		}
		else if(command.equals("ab1")) {
			active1.setVisible(true);
			active2.setVisible(true);
			active3.setVisible(true);
			setAbilityID();
		}
		else if(command.equals("ac1")) {
			active1.setEnabled(false);
			active2.setEnabled(false);
			active3.setEnabled(false);
			player.setAbility1ID(abilityID);
		}
		else if(command.equals("ac2")) {
			active1.setEnabled(false);
			active2.setEnabled(false);
			active3.setEnabled(false);
			player.setAbility2ID(abilityID);
		}
		else if(command.equals("ac3")) {
			active1.setEnabled(false);
			active2.setEnabled(false);
			active3.setEnabled(false);
			player.setAbility3ID(abilityID);
		}
			
	}

	private void setAbilityID() {		
		switch(ability1.getText()) {
		case "Swing":
			abilityID = 1;
			break;
		case "Decapitate":
			abilityID = 2;
			break;
		case "Riposte":
			abilityID = 3;
			break;
		case "Rend":
			abilityID = 4;
			break;
		case "Harden":
			abilityID = 5;
			break;
		case "Whirlwind":
			abilityID = 6;
			break;
		}
	}
	
	private void setAbilityText() {
		switch(x) {
		case 1:
			abilityText = "Swing";
			break;
		case 2:
			abilityText = "Decapitate";
			break;
		case 3:
			abilityText = "Riposte";
			break;
		case 4:
			abilityText = "Rend";
			break;
		case 5:
			abilityText = "Harden";
			break;
		case 6:
			abilityText = "Whirlwind";
			break;
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
        
        ability1 = new JButton();
        ability1.setActionCommand("ab1");
        while(x == player.getAbility1ID() || x == player.getAbility2ID() || x == player.getAbility3ID())
        	x = rand.nextInt(1, 7);
        setAbilityText();
        ability1.setText(abilityText);
        ability1.addActionListener(this);
        ability1.setBounds(300, 560, 150, 100);
        add(ability1);
        
        active1 = new JButton();
        active1.setVisible(false);
        active1.setActionCommand("ac1");
        active1.setText(a1);
        active1.addActionListener(this);
        active1.setBounds(460, 560, 150, 100);
        add(active1);
        
        active2 = new JButton();
        active2.setVisible(false);
        active2.setActionCommand("ac2");
        active2.setText(a2);
        active2.addActionListener(this);
        active2.setBounds(720, 560, 150, 100);
        add(active2);
        
        active3 = new JButton();
        active3.setVisible(false);
        active3.setActionCommand("ac3");
        active3.setText(a3);
        active3.addActionListener(this);
        active3.setBounds(880, 560, 150, 100);
        add(active3);
	}
	
}

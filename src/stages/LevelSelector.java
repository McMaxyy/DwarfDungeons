package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameWindow;

public class LevelSelector extends JPanel implements ActionListener{

	private GameWindow window;
	private int levelIndex, fromHomeScreen;
	private JButton levelOne = new JButton();
	private JButton levelTwo = new JButton();
	
	public LevelSelector(GameWindow window, int currentLevel) {
		this.window = window;
		
		setLayout(null);
		
		switch(currentLevel) {
		case 1:
			levelTwo.setEnabled(false);
			break;
		case 2: 
			levelTwo.setEnabled(true);
			levelOne.setEnabled(false);
			break;
		}
		
		levelOne.setActionCommand("Level One");
		levelOne.setText("Level One");
		levelOne.addActionListener(this);
		levelOne.setBounds(200, 300, 100, 50);
        add(levelOne);
        
        levelTwo.setActionCommand("Level Two");
        levelTwo.setText("Level Two");
        levelTwo.addActionListener(this);
        levelTwo.setBounds(200, 200, 100, 50);
        add(levelTwo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Level One")) {
			levelIndex = 1;
			fromHomeScreen++;
			window.showFightScene(levelIndex);      	
        }
		else if(command.equals("Level Two")) {
			levelIndex = 2;
			window.showFightScene(levelIndex);      	
        }		
	}
}

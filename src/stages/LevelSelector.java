package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameWindow;

@SuppressWarnings("serial")
public class LevelSelector extends JPanel implements ActionListener{

	private GameWindow window;
	private int levelIndex;
	private JButton levelOne, levelTwo, levelThree, bossLevel;
	
	public LevelSelector(GameWindow window, int currentLevel) {
		this.window = window;
		
		setLayout(null);
		
		JButton menuButton = new JButton("Main menu");  
        menuButton.setActionCommand("Main menu");
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 30);
        add(menuButton);
              
        JButton quitButton = new JButton("Quit");
        quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(375, 25, 100, 30);
        add(quitButton);
		
        levelOne = new JButton();
        levelOne.setEnabled(false);
		levelOne.setActionCommand("Level One");
		levelOne.setText("Level One");
		levelOne.addActionListener(this);
		levelOne.setBounds(200, 500, 100, 50);
        add(levelOne);
        
        levelTwo = new JButton();
        levelTwo.setEnabled(false);
        levelTwo.setActionCommand("Level Two");
        levelTwo.setText("Level Two");
        levelTwo.addActionListener(this);
        levelTwo.setBounds(200, 400, 100, 50);
        add(levelTwo);
        
        levelThree = new JButton();
        levelThree.setEnabled(false);
        levelThree.setActionCommand("Level Three");
        levelThree.setText("Level Three");
        levelThree.addActionListener(this);
        levelThree.setBounds(200, 300, 100, 50);
        add(levelThree);
        
        bossLevel = new JButton();
        bossLevel.setEnabled(false);
        bossLevel.setActionCommand("Boss");
        bossLevel.setText("Boss");
        bossLevel.addActionListener(this);
        bossLevel.setBounds(200, 200, 100, 50);
        add(bossLevel);
        
        switch(currentLevel) {
		case 1:
			levelOne.setEnabled(true);
			break;
		case 2: 
			levelTwo.setEnabled(true);			
			break;
		case 3:
			levelThree.setEnabled(true);
			break;
		case 4:
			bossLevel.setEnabled(true);
			break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Level One")) {
			levelIndex = 1;
			window.showFightScene(levelIndex);      	
        }
		else if(command.equals("Level Two")) {
			levelIndex = 2;
			window.showFightScene(levelIndex);      	
        }
		else if (command.equals("Main menu")) {
        	window.showMainMenu();
        } 
        else if (command.equals("Quit")) {
            System.exit(0);
        }
	}
}

package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import constants.Player;
import main.GameWindow;

@SuppressWarnings("serial")
public class LevelSelector extends JPanel implements ActionListener{

	private GameWindow window;
	private Player player = new Player();
	private int levelIndex;
	private static int selectedStage;
	private JButton stageOne, stageTwo, stageThree, menuButton, quitButton;
	private JButton levelOne, levelTwo, levelThree, bossLevel;
	private JButton[] levelButtons = new JButton[] {levelOne, levelTwo, levelThree, bossLevel};
	
	public LevelSelector(GameWindow window, int currentLevel) {
		this.window = window;
		
		setLayout(null);
		
		initComponents();
        
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
	
	public void showLevels() {
		for(JButton button: levelButtons)
			button.setVisible(true);
	}
	
	public void hideLevels() {
		for(JButton button: levelButtons)
			button.setVisible(false);
	}
	
	public void initComponents() {
		menuButton = new JButton("Main menu");  
        menuButton.setActionCommand("Main menu");
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 30);
        add(menuButton);
              
        quitButton = new JButton("Quit");
        quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(375, 25, 100, 30);
        add(quitButton);
        
        
        // Stage buttons
        stageOne = new JButton();
        stageOne.setActionCommand("Stage1");
        stageOne.setText("Stage 1");
        stageOne.addActionListener(this);
        stageOne.setBounds(50, 200, 100, 50);
        add(stageOne);
        
        stageTwo = new JButton();
        if(player.getUnlockedStage() >= 2)
        	stageTwo.setVisible(true);
        else
        	stageTwo.setVisible(false);
        stageTwo.setActionCommand("Stage2");
        stageTwo.setText("Stage 2");
        stageTwo.addActionListener(this);
        stageTwo.setBounds(50, 300, 100, 50);
        add(stageTwo);
		
        
        // Level buttons
        levelOne = new JButton();
        levelOne.setEnabled(false);
        levelOne.setVisible(false);
		levelOne.setActionCommand("Level One");
		levelOne.setText("Level One");
		levelOne.addActionListener(this);
		levelOne.setBounds(200, 500, 100, 50);
        add(levelOne);
        
        levelTwo = new JButton();
        levelTwo.setEnabled(false);
        levelTwo.setVisible(false);
        levelTwo.setActionCommand("Level Two");
        levelTwo.setText("Merchant");
        levelTwo.addActionListener(this);
        levelTwo.setBounds(200, 400, 100, 50);
        add(levelTwo);
        
        levelThree = new JButton();
        levelThree.setEnabled(false);
        levelThree.setVisible(false);
        levelThree.setActionCommand("Level Three");
        levelThree.setText("Level Three");
        levelThree.addActionListener(this);
        levelThree.setBounds(200, 300, 100, 50);
        add(levelThree);
        
        bossLevel = new JButton();
        bossLevel.setEnabled(false);
        bossLevel.setVisible(false);
        bossLevel.setActionCommand("Boss");
        bossLevel.setText("Boss");
        bossLevel.addActionListener(this);
        bossLevel.setBounds(200, 200, 100, 50);
        add(bossLevel);
        
        levelButtons[0] = levelOne;
        levelButtons[1] = levelTwo;
        levelButtons[2] = levelThree;
        levelButtons[3] = bossLevel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Stage1")) {
			selectedStage = 1;
			if(!levelOne.isVisible())
				showLevels();
			else
				hideLevels();
        }
		else if (command.equals("Stage2")) {
			selectedStage = 1;
			if(!levelOne.isVisible())
				showLevels();
			else
				hideLevels();
        }
		else if (command.equals("Level One")) {
			levelIndex = 1;
			window.showFightScene(levelIndex, selectedStage);      	
        }
		else if(command.equals("Level Two")) {
			levelIndex = 2;
			window.showMerchant(levelIndex);    	
        }
		else if(command.equals("Level Three")) {
			levelIndex = 3;
			window.showFightScene(levelIndex, selectedStage);   	
        }
		else if(command.equals("Boss")) {
			levelIndex = 4;
			window.showFightScene(levelIndex, selectedStage);    	
        }
		else if (command.equals("Main menu")) {
        	window.showMainMenu();
        } 
        else if (command.equals("Quit")) {
            System.exit(0);
        }
	}
}

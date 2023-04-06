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
	private JButton levelTwo1, levelTwo2, levelThree1, levelThree2, levelThree3, levelThree4,
			levelFour1, levelFour2, levelFour3, levelFour4, levelFour5, levelFive1, levelFive2,
			levelFive3, levelFive4, levelSix1, levelSix2;
	private final int fight = 2, merch = 1, q = 1, bonfire = 2;
	
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
        
        if(currentLevel > 1)
        	showLevels();
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
	
	private void setStage() {
		levelOne = new JButton();
        levelOne.setEnabled(true);
        levelOne.setVisible(true);
		levelOne.setActionCommand("Level One");
		levelOne.setText("Fight");
		levelOne.addActionListener(this);
		levelOne.setBounds(700, 600, 70, 70);
        add(levelOne);
        
        levelTwo1 = new JButton();
        levelTwo1.setEnabled(true);
        levelTwo1.setVisible(true);
        levelTwo1.setActionCommand("LevelTwo1");
        levelTwo1.setText("Fight");
        levelTwo1.addActionListener(this);
        levelTwo1.setBounds(550, 500, 70, 70);
        add(levelTwo1);
        
        levelTwo2 = new JButton();
        levelTwo2.setEnabled(true);
        levelTwo2.setVisible(true);
        levelTwo2.setActionCommand("LevelTwo2");
        levelTwo2.setText("Fight");
        levelTwo2.addActionListener(this);
        levelTwo2.setBounds(850, 500, 70, 70);
        add(levelTwo2);
        
        levelThree1 = new JButton();
        levelThree1.setEnabled(true);
        levelThree1.setVisible(true);
        levelThree1.setText("Fight");
        levelThree1.setActionCommand(levelThree1.getText());     
        levelThree1.addActionListener(this);
        levelThree1.setBounds(470, 400, 70, 70);
        add(levelThree1);
        
        levelThree2 = new JButton();
        levelThree2.setEnabled(true);
        levelThree2.setVisible(true);
        levelThree2.setText("Fight");
        levelThree2.setActionCommand(levelThree2.getText());        
        levelThree2.addActionListener(this);
        levelThree2.setBounds(630, 400, 70, 70);
        add(levelThree2);
        
        levelThree3 = new JButton();
        levelThree3.setEnabled(true);
        levelThree3.setVisible(true);
        levelThree3.setText("Fight");
        levelThree3.setActionCommand(levelThree3.getText());       
        levelThree3.addActionListener(this);
        levelThree3.setBounds(770, 400, 70, 70);
        add(levelThree3);
        
        levelThree4 = new JButton();
        levelThree4.setEnabled(true);
        levelThree4.setVisible(true);
        levelThree4.setText("Fight");
        levelThree4.setActionCommand(levelThree4.getText());       
        levelThree4.addActionListener(this);
        levelThree4.setBounds(930, 400, 70, 70);
        add(levelThree4);
	}
	
	private void randomLevel() {
		
		
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
		
		switch(command) {
		case "Stage1":
			setStage();
			break;
		case "Fight":
			break;
		case "Strong Enemy":
			break;
		case "???":
			break;
		case "Merchant":
			break;
		case "Bonfire":
			break;
		case "Boss":
			break;
		}
	}
}

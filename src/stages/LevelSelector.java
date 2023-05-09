package stages;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import constants.*;
import inventory.Storage;
import main.GameWindow;

@SuppressWarnings("serial")
public class LevelSelector extends JPanel implements ActionListener{

	private GameWindow window;
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private Storage s = Storage.getInstance();
	private int levelIndex;
	private static int selectedStage;
	private JButton stageOne, stageTwo, stageThree, menuButton, quitButton;
	private JButton levelOne, bossLevel;
	private JButton levelTwo1, levelTwo2, levelThree1, levelThree2, levelThree3, levelThree4,
	levelFour1, levelFour2, levelFour3, levelFour4, levelFour5, levelFive1, levelFive2,
	levelFive3, levelFive4, levelSix1, levelSix2;
	private JButton[] levelButtons = new JButton[] {levelOne, levelTwo1, levelTwo2, levelThree1, 
			levelThree2, levelThree3, levelThree4,levelFour1, levelFour2, levelFour3, levelFour4, 
			levelFour5, levelFive1, levelFive2,levelFive3, levelFive4, levelSix1, levelSix2, bossLevel};
	private Random rand = new Random();
	private static boolean left1, right1, left2, right2, strongEnemyRoom;
	private static String levelThree1Txt, levelThree2Txt, levelThree3Txt, 
	levelThree4Txt, levelFour1Txt, levelFour2Txt, levelFour4Txt, levelFour5Txt, 
	levelFive1Txt, levelFive2Txt, levelFive3Txt,levelFive4Txt, levelSix1Txt, levelSix2Txt;
	private Image bonfireIcon, bossIcon, fightIcon, merchIcon, randIcon, strEnemyIcon, quitBtn, returnBtn, bg;
	
	public LevelSelector(GameWindow window, int currentLevel) {
		this.window = window;
		levelIndex = currentLevel;
		enemy.setBossActive(false);
		enemy.setStrongEnemyActive(false);
		
		setLayout(null);
		
		loadIcons();
		initComponents();
		setStage();
		setButtons();
		
		if(player.getUnlockedStage() > 1)
			stageOne.setEnabled(false);
		
		if(levelIndex > 1) {
			stageOne.setEnabled(false);
			stageTwo.setEnabled(false);
			stageThree.setEnabled(false);
		}
        
		switch(levelIndex) {
		case 1:
			levelOne.setEnabled(true);
			break;
		case 2:
			levelTwo1.setEnabled(true);
			levelTwo2.setEnabled(true);
			break;
		case 3:
			if(left1) {
				levelThree1.setEnabled(true);
				levelThree2.setEnabled(true);
				left1 = false;
			}
			else if(right2){
				levelThree3.setEnabled(true);
				levelThree4.setEnabled(true);
				right2 = false;
			}
			break;
		case 4:
			if(left1) {
				levelFour1.setEnabled(true);
				levelFour2.setEnabled(true);
				left1 = false;
			}
			else if(right2) {
				levelFour4.setEnabled(true);
				levelFour5.setEnabled(true);
				right2 = false;
			}
			else if(right1) {
				levelFour3.setEnabled(true);
				right1 = false;
			}
			else if(left2) {
				levelFour3.setEnabled(true);
				left2 = false;
			}
			break;
		case 5:
			if(left1) {
				levelFive1.setEnabled(true);
				left1 = false;
			}
			else if(right2) {
				levelFive4.setEnabled(true);
				right2 = false;
			}
			else if(strongEnemyRoom) {
				levelFive2.setEnabled(true);
				levelFive3.setEnabled(true);
				strongEnemyRoom = false;
			}
			break;
		case 6:
			if(left1) {
				levelSix1.setEnabled(true);
				left1 = false;
			}
			else if(right2) {
				levelSix2.setEnabled(true);
				right2 = false;
			}
			break;
		case 7:
			bossLevel.setEnabled(true);
			break;
		}		
		
        if(levelIndex > 1)
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
		menuButton = new JButton();  
		menuButton.setIcon(new ImageIcon(returnBtn));
        menuButton.setActionCommand("Return");
        menuButton.setFocusable(false);
        menuButton.setOpaque(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setBorderPainted(false);
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 50);
        add(menuButton);
              
        quitButton = new JButton();
        quitButton.setIcon(new ImageIcon(quitBtn));
        quitButton.setActionCommand("Quit");
        quitButton.setFocusable(false);
        quitButton.setOpaque(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);
        quitButton.addActionListener(this);
        quitButton.setBounds(175, 25, 100, 50);
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
        
        stageThree = new JButton();
        if(player.getUnlockedStage() >= 3)
        	stageThree.setVisible(true);
        else
        	stageThree.setVisible(false);
        stageThree.setActionCommand("Stage3");
        stageThree.setText("Stage 3");
        stageThree.addActionListener(this);
        stageThree.setBounds(50, 400, 100, 50);
        add(stageThree); 
        
        levelOne = new JButton();
        levelOne.setEnabled(false);
        levelOne.setVisible(false);
		levelOne.setActionCommand("Level One");
		levelOne.setIcon(new ImageIcon(fightIcon));
		levelOne.addActionListener(this);
		levelOne.setBounds(700, 600, 70, 70);
        add(levelOne);
	}
	
	private void setStage() {
		int x;
		
		bossLevel = new JButton();
		bossLevel.setEnabled(false);
		bossLevel.setVisible(false);
		bossLevel.setActionCommand("Boss");
		bossLevel.setIcon(new ImageIcon(bossIcon));
		bossLevel.addActionListener(this);
		bossLevel.setBounds(700, 10, 70, 70);
        add(bossLevel);
        
        levelTwo1 = new JButton();
        levelTwo1.setEnabled(false);
        levelTwo1.setVisible(false);
        levelTwo1.setActionCommand("Fight");
        levelTwo1.setIcon(new ImageIcon(setIcons(levelTwo1.getActionCommand())));
        levelTwo1.addActionListener(this);
        levelTwo1.setBounds(550, 500, 70, 70);
        add(levelTwo1);
        
        levelTwo2 = new JButton();
        levelTwo2.setEnabled(false);
        levelTwo2.setVisible(false);
        levelTwo2.setActionCommand("Fight");
        levelTwo2.setIcon(new ImageIcon(setIcons(levelTwo2.getActionCommand())));
        levelTwo2.addActionListener(this);
        levelTwo2.setBounds(850, 500, 70, 70);
        add(levelTwo2);
               
        levelThree1 = new JButton();
        levelThree1.setEnabled(false);
        levelThree1.setVisible(false);
        if(levelIndex == 1) {
        	x = rand.nextInt(4);
	        if(x == 0)
	        	levelThree1.setText("Bonfire");
	        else if (x == 1)
	        	levelThree1.setText("Merchant");
	        else if (x == 2)
	        	levelThree1.setText("???");
	        else 
	        	levelThree1.setText("Fight");
        
        levelThree1Txt = levelThree1.getText();
        }
        else
        	levelThree1.setText(levelThree1Txt);
        levelThree1.setActionCommand(levelThree1.getText());
        levelThree1.setText("");
        levelThree1.setIcon(new ImageIcon(setIcons(levelThree1.getActionCommand())));
        levelThree1.addActionListener(this);
        levelThree1.setBounds(470, 400, 70, 70);
        add(levelThree1);
              
        levelThree2 = new JButton();
        levelThree2.setEnabled(false);
        levelThree2.setVisible(false);
        if(levelIndex == 1) {
        	x = rand.nextInt(5);
        	if(x == 0 || x == 1)
        		levelThree2.setText("Bonfire");
            else if (x == 2 || x == 3)
            	levelThree2.setText("Merchant");
            else
            	levelThree2.setText("???");
	        
	        levelThree2Txt = levelThree2.getText();
		}
        else
        	levelThree2.setText(levelThree2Txt);
        levelThree2.setActionCommand(levelThree2.getText());
        levelThree2.setText("");
        levelThree2.setIcon(new ImageIcon(setIcons(levelThree2.getActionCommand())));
        levelThree2.addActionListener(this);
        levelThree2.setBounds(630, 400, 70, 70);
        add(levelThree2);
             
        levelThree4 = new JButton();
        levelThree4.setEnabled(false);
        levelThree4.setVisible(false);
        if(levelIndex == 1) {
        	x = rand.nextInt(4);
        	if(x == 0)
        		levelThree4.setText("Bonfire");
            else if (x == 1)
            	levelThree4.setText("Merchant");
            else if (x == 2)
            	levelThree4.setText("???");
            else 
            	levelThree4.setText("Fight");
	        
	        levelThree4Txt = levelThree4.getText();
        }
        else
        	levelThree4.setText(levelThree4Txt);
        levelThree4.setActionCommand(levelThree4.getText());    
        levelThree4.setText("");
        levelThree4.setIcon(new ImageIcon(setIcons(levelThree4.getActionCommand())));
        levelThree4.addActionListener(this);
        levelThree4.setBounds(930, 400, 70, 70);
        add(levelThree4);
        
        x = rand.nextInt(5);
        levelThree3 = new JButton();
        levelThree3.setEnabled(false);
        levelThree3.setVisible(false);
        if(levelIndex == 1) {
	        if(x == 0)
	        	levelThree3.setText("Bonfire");
	        else if (x == 1)
	        	levelThree3.setText("???");
	        else
	        	levelThree3.setText("Merchant");
	        
	        levelThree3Txt = levelThree3.getText();
        }
        else
        	levelThree3.setText(levelThree3Txt);
        levelThree3.setActionCommand(levelThree3.getText()); 
        levelThree3.setText("");
        levelThree3.setIcon(new ImageIcon(setIcons(levelThree3.getActionCommand())));
        levelThree3.addActionListener(this);
        levelThree3.setBounds(770, 400, 70, 70);
        add(levelThree3);         
                
        levelFour1 = new JButton();
        levelFour1.setEnabled(false);
        levelFour1.setVisible(false);
        if(levelIndex == 1) {  
        	x = rand.nextInt(2);
	        if(levelThree1.getActionCommand() == "Merchant" || levelThree1.getActionCommand() == "Bonfire") {
	        	if(x == 0)
	        		levelFour1.setText("Fight");
	        	else
	        		levelFour1.setText("???");
	        }
	        else if(x == 0)
	        	levelFour1.setText("Merchant");
	        else
	        	levelFour1.setText("Bonfire");
	        
	        levelFour1Txt = levelFour1.getText();
        }
        else
        	levelFour1.setText(levelFour1Txt);
        levelFour1.setActionCommand(levelFour1.getText());
        levelFour1.setText("");
        levelFour1.setIcon(new ImageIcon(setIcons(levelFour1.getActionCommand())));
        levelFour1.addActionListener(this);
        levelFour1.setBounds(390, 300, 70, 70);
        add(levelFour1);
        
        levelFour2 = new JButton();
        levelFour2.setEnabled(false);
        levelFour2.setVisible(false);
        if(levelIndex == 1) {
        	x = rand.nextInt(4);
	        if(levelThree1.getActionCommand() == "Merchant" || levelThree1.getActionCommand() == "Bonfire") {
	        	if(x == 0 || x == 1)
	        		levelFour2.setText("Fight");
	        	else
	        		levelFour2.setText("???");
	        }
	        else if(x == 0)
	        	levelFour2.setText("Merchant");
	        else if(x == 1)
	        	levelFour2.setText("Bonfire");
	        else if(x == 2)
	        	levelFour2.setText("Fight");
	        else
	        	levelFour2.setText("???");
	        
	        levelFour2Txt = levelFour2.getText();
        }
        else
        	levelFour2.setText(levelFour2Txt);
        levelFour2.setActionCommand(levelFour2.getText());
        levelFour2.setText("");
        levelFour2.setIcon(new ImageIcon(setIcons(levelFour2.getActionCommand())));
        levelFour2.addActionListener(this);
        levelFour2.setBounds(550, 300, 70, 70);
        add(levelFour2);
        
        levelFour3 = new JButton();
        levelFour3.setEnabled(false);
        levelFour3.setVisible(false);
        levelFour3.setActionCommand("Strong Enemy");
        levelFour3.setIcon(new ImageIcon(setIcons(levelFour3.getActionCommand())));
        levelFour3.addActionListener(this);
        levelFour3.setBounds(700, 300, 70, 70);
        add(levelFour3);
        
        levelFour4 = new JButton();
        levelFour4.setEnabled(false);
        levelFour4.setVisible(false);
        if(levelIndex == 1) {
        	x = rand.nextInt(2);
	        if(levelThree4.getActionCommand() == "Merchant" || levelThree4.getActionCommand() == "Bonfire") {
	        	if(x == 0)
	        		levelFour4.setText("Fight");
	        	else
	        		levelFour4.setText("???");
	        }
	        else if(x == 0)
	        	levelFour4.setText("Merchant");
	        else
	        	levelFour4.setText("Bonfire");
	        
	        levelFour4Txt = levelFour4.getText();
        }
        else
        	levelFour4.setText(levelFour4Txt);
        levelFour4.setActionCommand(levelFour4.getText()); 
        levelFour4.setText("");
        levelFour4.setIcon(new ImageIcon(setIcons(levelFour4.getActionCommand())));
        levelFour4.addActionListener(this);
        levelFour4.setBounds(850, 300, 70, 70);
        add(levelFour4);
        
        levelFour5 = new JButton();
        levelFour5.setEnabled(false);
        levelFour5.setVisible(false);
        if(levelIndex == 1) {
        	x = rand.nextInt(4);
	        if(levelThree4.getActionCommand() == "Merchant" || levelThree4.getActionCommand() == "Bonfire") {
	        	if(x == 0 || x == 1)
	        		levelFour5.setText("Fight");
	        	else
	        		levelFour5.setText("???");
	        }
	        else if(x == 0)
	        	levelFour5.setText("Merchant");
	        else if(x == 1)
	        	levelFour5.setText("Bonfire");
	        else if(x == 2)
	        	levelFour5.setText("Fight");
	        else
	        	levelFour5.setText("???");
	        
	        levelFour5Txt = levelFour5.getText();
        }
        else
        	levelFour5.setText(levelFour5Txt);
        levelFour5.setActionCommand(levelFour5.getText()); 
        levelFour5.setText("");
        levelFour5.setIcon(new ImageIcon(setIcons(levelFour5.getActionCommand())));
        levelFour5.addActionListener(this);
        levelFour5.setBounds(1010, 300, 70, 70);
        add(levelFour5);
        
        levelFive1 = new JButton();
        levelFive1.setEnabled(false);
        levelFive1.setVisible(false);
        if(levelIndex == 1) {
	        x = rand.nextInt(5);
	        if(x == 0 || x == 1)
	        	levelFive1.setText("Merchant");
	        else if(x == 2 || x == 3)
	        	levelFive1.setText("Bonfire");
	        else
	        	levelFive1.setText("Fight");
	        
	        levelFive1Txt = levelFive1.getText();
        }
        else
        	levelFive1.setText(levelFive1Txt);
        levelFive1.setActionCommand(levelFive1.getText());   
        levelFive1.setText("");
        levelFive1.setIcon(new ImageIcon(setIcons(levelFive1.getActionCommand())));
        levelFive1.addActionListener(this);
        levelFive1.setBounds(470, 200, 70, 70);
        add(levelFive1);
        
       levelFive2 = new JButton();
       levelFive2.setEnabled(false);
       levelFive2.setVisible(false);
       if(levelIndex == 1) {
    	   x = rand.nextInt(2);
		   if(x == 0)
			   levelFive2.setText("Bonfire");
		   else
			   levelFive2.setText("Merchant");
		   
		   levelFive2Txt = levelFive2.getText();
       }
       else
    	   levelFive2.setText(levelFive2Txt);
       levelFive2.setActionCommand(levelFive2.getText()); 
       levelFive2.setText("");
       levelFive2.setIcon(new ImageIcon(setIcons(levelFive2.getActionCommand())));
       levelFive2.addActionListener(this);
       levelFive2.setBounds(630, 200, 70, 70);
        add(levelFive2);
        
        levelFive3 = new JButton();
        levelFive3.setEnabled(false);
        levelFive3.setVisible(false);
        if(levelIndex == 1) {
	        if(levelFive2.getActionCommand() == "Bonfire")
	        	levelFive3.setText("Merchant");
	        else
	        	levelFive3.setText("Bonfire");
        
	        levelFive3Txt = levelFive3.getText();
        }
        else
        	levelFive3.setText(levelFive3Txt);
        levelFive3.setActionCommand(levelFive3.getText());
        levelFive3.setText("");
        levelFive3.setIcon(new ImageIcon(setIcons(levelFive3.getActionCommand())));
        levelFive3.addActionListener(this);
        levelFive3.setBounds(770, 200, 70, 70);
        add(levelFive3);
        
        levelFive4 = new JButton();
        levelFive4.setEnabled(false);
        levelFive4.setVisible(false);
        if(levelIndex == 1) {
        	x = rand.nextInt(5);
	        if(x == 0 || x == 1)
	        	levelFive4.setText("Merchant");
	        else if(x == 2 || x == 3)
	        	levelFive4.setText("Bonfire");
	        else
	        	levelFive4.setText("Fight");
	        
	        levelFive4Txt = levelFive4.getText();
        }
        else
        	levelFive4.setText(levelFive4Txt);
        levelFive4.setActionCommand(levelFive4.getText()); 
        levelFive4.setText("");
        levelFive4.setIcon(new ImageIcon(setIcons(levelFive4.getActionCommand())));
        levelFive4.addActionListener(this);
        levelFive4.setBounds(930, 200, 70, 70);
        add(levelFive4);
                
        levelSix1 = new JButton();
        levelSix1.setEnabled(false);
        levelSix1.setVisible(false);
        if(levelIndex == 1) {
	        if(levelFive1.getActionCommand() == "Merchant" || levelFive1.getActionCommand() == "Bonfire") {
	        	x = rand.nextInt(6);
	        	switch(x) {
	        	case 0:
	        	case 1:
	        		levelSix1.setText("Fight");
	        		break;
	        	case 2:
	        	case 3:
	        		levelSix1.setText("???");
	        		break;
	        	case 4:
	        	case 5:
	        		if(levelFive2.getActionCommand() == "Merchant")
	        			levelSix1.setText("Bonfire");
	        		else
	        			levelSix1.setText("Merchant");
	        	}
	        }
	        else if(levelFive2.getActionCommand() == "Merchant")
	        	levelSix1.setText("Bonfire");
        	else
        		levelSix1.setText("Merchant");
        	
	        	
	        levelSix1Txt = levelSix1.getText();
        }
        else
        	levelSix1.setText(levelSix1Txt);
        levelSix1.setActionCommand(levelSix1.getText());
        levelSix1.setText("");
        levelSix1.setIcon(new ImageIcon(setIcons(levelSix1.getActionCommand())));
        levelSix1.addActionListener(this);
        levelSix1.setBounds(630, 100, 70, 70);
        add(levelSix1);
        
        x = rand.nextInt(4);
        levelSix2 = new JButton();
        levelSix2.setEnabled(false);
        levelSix2.setVisible(false);
        if(levelIndex == 1) {
        	if(levelFive4.getActionCommand() == "Merchant" || levelFive4.getActionCommand() == "Bonfire") {
	        	x = rand.nextInt(6);
	        	switch(x) {
	        	case 0:
	        	case 1:
	        		levelSix2.setText("Fight");
	        		break;
	        	case 2:
	        	case 3:
	        		levelSix2.setText("???");
	        		break;
	        	case 4:
	        	case 5:
	        		if(levelFive3.getActionCommand() == "Merchant")
	        			levelSix2.setText("Bonfire");
	        		else
	        			levelSix2.setText("Merchant");
	        	}
	        }
	        else if(levelFive3.getActionCommand() == "Merchant")
	        	levelSix2.setText("Bonfire");
        	else
        		levelSix2.setText("Merchant");
	        
	        levelSix2Txt = levelSix2.getText();
        }
        else
        	levelSix2.setText(levelSix2Txt);
        levelSix2.setActionCommand(levelSix2.getText()); 
        levelSix2.setText("");
        levelSix2.setIcon(new ImageIcon(setIcons(levelSix2.getActionCommand())));
        levelSix2.addActionListener(this);
        levelSix2.setBounds(770, 100, 70, 70);
        add(levelSix2);	
        
        levelButtons[0] = levelOne;
        levelButtons[1] = levelTwo1;
        levelButtons[2] = levelTwo2;
        levelButtons[3] = levelThree1;
        levelButtons[4] = levelThree2;
        levelButtons[5] = levelThree3;
        levelButtons[6] = levelThree4;
        levelButtons[7] = levelFour1;
        levelButtons[8] = levelFour2;
        levelButtons[9] = levelFour3;
        levelButtons[10] = levelFour4;
        levelButtons[11] = levelFour5;
        levelButtons[12] = levelFive1;
        levelButtons[13] = levelFive2;
        levelButtons[14] = levelFive3;
        levelButtons[15] = levelFive4;
        levelButtons[16] = levelSix1;
        levelButtons[17] = levelSix2;
        levelButtons[18] = bossLevel;
        
        resetStage();
	}
	
	private void resetStage() {
		int x = 0, y = 0;
		for(JButton button : levelButtons) {
			if(button.getActionCommand() == "Merchant")
				x++;
			if(button.getActionCommand() == "Bonfire")
				y++;
		}
		if(x >= 5 || y >= 5)
			setStage();
	}
	
	private void loadIcons() {
		try {
			bonfireIcon = ImageIO.read(new File("res/LevelIcons/BonfireIcon.png"));
			bossIcon = ImageIO.read(new File("res/LevelIcons/BossIcon.png"));
			fightIcon = ImageIO.read(new File("res/LevelIcons/FightIcon.png"));
			merchIcon = ImageIO.read(new File("res/LevelIcons/MerchIcon.png"));
			randIcon = ImageIO.read(new File("res/LevelIcons/RandIcon.png"));
			strEnemyIcon = ImageIO.read(new File("res/LevelIcons/StrEnemyIcon.png"));
			quitBtn = ImageIO.read(new File("res/FunctionButtons/QuitButton.png"));
			returnBtn = ImageIO.read(new File("res/FunctionButtons/ReturnButton.png"));
			bg = ImageIO.read(new File("res/Backgrounds/LevelSelect_BG.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Image setIcons(String s) {
		switch(s) {
		case "Fight":
			return fightIcon;
		case "???":
			return randIcon;
		case "Bonfire":
			return bonfireIcon;
		case "Merchant":
			return merchIcon;
		case "Strong Enemy":
			return strEnemyIcon;
		}
		return null;
	}
	
	private void setButtons() {
		for(JButton button : levelButtons) {
			button.setFocusable(false);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
		}			
	}
	
	private void addItem(String item) {
		switch(item) {
		case "IronAxe":
			s.ironAxe.setAmount(s.ironAxe.getAmount() + 1);
			break;
		case "SilverAxe":
			s.silverAxe.setAmount(s.silverAxe.getAmount() + 1);
			break;
		case "GoldAxe":
			s.goldAxe.setAmount(s.goldAxe.getAmount() + 1);
			break;
		case "SteelAxe":
			s.steelAxe.setAmount(s.steelAxe.getAmount() + 1);
			break;
		case "CopperAxe":
			s.copperAxe.setAmount(s.copperAxe.getAmount() + 1);
			break;
		case "TitaniumAxe":
			s.titaniumAxe.setAmount(s.titaniumAxe.getAmount() + 1);
			break;
		case "FieryAxe":
			s.fieryAxe.setAmount(s.fieryAxe.getAmount() + 1);
			break;
		case "MoltenAxe":
			s.moltenAxe.setAmount(s.moltenAxe.getAmount() + 1);
			break;
		case "WaterAxe":
			s.waterAxe.setAmount(s.waterAxe.getAmount() + 1);
			break;
		case "Health":
			s.healthPot.setAmount(s.healthPot.getAmount() + 1);
			break;
		case "Shield":
			s.shield.setAmount(s.shield.getAmount() + 1);
			break;
		case "Bomb":
			s.bomb.setAmount(s.bomb.getAmount() + 1);
			break;
		case "PoisonDart":
			s.poisonDart.setAmount(s.poisonDart.getAmount() + 1);
			break;
		case "Dynamite":
			s.bigBomb.setAmount(s.bigBomb.getAmount() + 1);
			break;
		case "Bombs":
			s.biggerBomb.setAmount(s.biggerBomb.getAmount() + 1);
			break;
		}
	}
	
	private void checkInventory() {
		for(int i = 0; i < Player.activeBag.length; i++) {
			if(Player.activeBag[i].getIcon() != null)
				addItem(Player.activeBag[i].getName());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch(command) {
		case "Quit":
			System.exit(0);
			break;
		case "Return":
			checkInventory();
			window.showHomeScreen();
			break;
		case "Stage1":
			selectedStage = 1;
			player.setMerchantVisits(0);
			if(!levelOne.isVisible()) {
				setStage();
				setButtons();
				levelOne.setEnabled(true);
				showLevels();
			}
			else
				hideLevels();
			break;
		case "Stage2":
			selectedStage = 2;
			setStage();
			setButtons();
			levelOne.setEnabled(true);
			showLevels();
			stageOne.setEnabled(false);
			stageTwo.setEnabled(false);			
			break;
		case "Stage3":
			selectedStage = 3;
			setStage();
			setButtons();
			levelOne.setEnabled(true);
			showLevels();
			stageOne.setEnabled(false);
			stageTwo.setEnabled(false);	
			stageThree.setEnabled(false);
			break;
		case "Level One":
			levelIndex = 1;			
			window.showFightScene(levelIndex, selectedStage); 
			break;
		case "Fight":
			if(e.getSource() == levelTwo1 || e.getSource() == levelThree1 || e.getSource() == levelFour1 || 
			e.getSource() == levelFour2 ||e.getSource() == levelFive1 || e.getSource() == levelFive2)
				left1 = true;
			else if(e.getSource() == levelTwo2 || e.getSource() == levelThree4 || e.getSource() == levelFour4 || 
					e.getSource() == levelFour5 ||e.getSource() == levelFive3 || e.getSource() == levelFive4)
				right2 = true;			
			window.showFightScene(levelIndex, selectedStage); 
			break;
		case "Strong Enemy":
			strongEnemyRoom = true;
			enemy.setStrongEnemyActive(true);
			window.showFightScene(levelIndex, selectedStage);
			break;
		case "???":
			if(e.getSource() == levelTwo1 || e.getSource() == levelThree1 || e.getSource() == levelFour1 || 
			e.getSource() == levelFour2 ||e.getSource() == levelFive1 || e.getSource() == levelFive2)
				left1 = true;
			else if(e.getSource() == levelTwo2 || e.getSource() == levelThree4 || e.getSource() == levelFour4 || 
					e.getSource() == levelFour5 ||e.getSource() == levelFive3 || e.getSource() == levelFive4)
				right2 = true;
			else if(e.getSource() == levelThree2)
				right1 = true;
			else if(e.getSource() == levelThree3)
				left2 = true;
			
			switch(rand.nextInt(4)) {
			case 0:
				window.showFightScene(levelIndex, selectedStage);
				break;
			case 1:
				window.showMerchant(levelIndex);
				break;
			case 2:
				window.showBonfire(levelIndex);
				break;
			case 3:
				enemy.setStrongEnemyActive(true);
				window.showFightScene(levelIndex, selectedStage);
			}
			break;
		case "Merchant":
			if(e.getSource() == levelTwo1 || e.getSource() == levelThree1 || e.getSource() == levelFour1 || 
			e.getSource() == levelFour2 ||e.getSource() == levelFive1 || e.getSource() == levelFive2)
				left1 = true;
			else if(e.getSource() == levelTwo2 || e.getSource() == levelThree4 || e.getSource() == levelFour4 || 
					e.getSource() == levelFour5 ||e.getSource() == levelFive3 || e.getSource() == levelFive4)
				right2 = true;	
			else if(e.getSource() == levelThree2)
				right1 = true;
			else if(e.getSource() == levelThree3)
				left2 = true;
			player.increaseMerchantVisits();
			window.showMerchant(levelIndex);
			break;
		case "Bonfire":
			if(e.getSource() == levelTwo1 || e.getSource() == levelThree1 || e.getSource() == levelFour1 || 
			e.getSource() == levelFour2 ||e.getSource() == levelFive1 || e.getSource() == levelFive2)
				left1 = true;
			else if(e.getSource() == levelTwo2 || e.getSource() == levelThree4 || e.getSource() == levelFour4 || 
			e.getSource() == levelFour5 ||e.getSource() == levelFive3 || e.getSource() == levelFive4)
				right2 = true;	
			else if(e.getSource() == levelThree2)
				right1 = true;
			else if(e.getSource() == levelThree3)
				left2 = true;
			window.showBonfire(levelIndex);
			break;
		case "Boss":
			enemy.setBossActive(true);
			window.showFightScene(levelIndex, selectedStage); 
			break;
		}		
	}
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D)g;
        g1.setStroke(new BasicStroke(3));
        
        g.drawImage(bg, 0, 0, null);
        
        if(levelOne.isVisible()) {
	        g1.drawLine(levelOne.getLocation().x, levelOne.getLocation().y, levelTwo1.getLocation().x + 70, levelTwo1.getLocation().y + 70);
	        g1.drawLine(levelOne.getLocation().x + 70, levelOne.getLocation().y, levelTwo2.getLocation().x, levelTwo2.getLocation().y + 70);
	        
	        g1.drawLine(levelTwo1.getLocation().x, levelTwo1.getLocation().y, levelThree1.getLocation().x + 55, levelThree1.getLocation().y + 70);
	        g1.drawLine(levelTwo1.getLocation().x + 70, levelTwo1.getLocation().y, levelThree2.getLocation().x + 15, levelThree2.getLocation().y + 70);
	        
	        g1.drawLine(levelTwo2.getLocation().x, levelTwo2.getLocation().y, levelThree3.getLocation().x + 55, levelThree3.getLocation().y + 70);
	        g1.drawLine(levelTwo2.getLocation().x + 70, levelTwo2.getLocation().y, levelThree4.getLocation().x + 15, levelThree4.getLocation().y + 70);
	        
	        g1.drawLine(levelThree1.getLocation().x, levelThree1.getLocation().y, levelFour1.getLocation().x + 55, levelFour1.getLocation().y + 70);
	        g1.drawLine(levelThree1.getLocation().x + 70, levelThree1.getLocation().y, levelFour2.getLocation().x + 15, levelFour2.getLocation().y + 70);
	        
	        g1.drawLine(levelThree2.getLocation().x + 70, levelThree2.getLocation().y, levelFour3.getLocation().x + 15, levelFour3.getLocation().y + 70);
	        g1.drawLine(levelThree3.getLocation().x, levelThree3.getLocation().y, levelFour3.getLocation().x + 55, levelFour3.getLocation().y + 70);
	        
	        g1.drawLine(levelThree4.getLocation().x, levelThree4.getLocation().y, levelFour4.getLocation().x + 55, levelFour4.getLocation().y + 70);
	        g1.drawLine(levelThree4.getLocation().x + 70, levelThree4.getLocation().y, levelFour5.getLocation().x + 15, levelFour5.getLocation().y + 70);
	        
	        g1.drawLine(levelFour2.getLocation().x + 15, levelFour2.getLocation().y, levelFive1.getLocation().x + 70, levelFive1.getLocation().y + 70);
	        g1.drawLine(levelFour1.getLocation().x + 55, levelFour1.getLocation().y, levelFive1.getLocation().x, levelFive1.getLocation().y + 70);
	        
	        g1.drawLine(levelFour3.getLocation().x + 55, levelFour3.getLocation().y, levelFive3.getLocation().x, levelFive3.getLocation().y + 70);
	        g1.drawLine(levelFour3.getLocation().x + 15, levelFour3.getLocation().y, levelFive2.getLocation().x + 70, levelFive2.getLocation().y + 70);
	        
	        g1.drawLine(levelFour5.getLocation().x + 15, levelFour5.getLocation().y, levelFive4.getLocation().x + 70, levelFive4.getLocation().y + 70);
	        g1.drawLine(levelFour4.getLocation().x + 55, levelFour4.getLocation().y, levelFive4.getLocation().x, levelFive4.getLocation().y + 70);
	        
	        g1.drawLine(levelFive1.getLocation().x + 55, levelFive1.getLocation().y, levelSix1.getLocation().x + 15, levelSix1.getLocation().y + 70);
	        g1.drawLine(levelFive2.getLocation().x + 35, levelFive2.getLocation().y, levelSix1.getLocation().x + 35, levelSix1.getLocation().y + 70);
	        
	        g1.drawLine(levelFive4.getLocation().x + 15, levelFive4.getLocation().y, levelSix2.getLocation().x + 55, levelSix2.getLocation().y + 70);
	        g1.drawLine(levelFive3.getLocation().x + 35, levelFive3.getLocation().y, levelSix2.getLocation().x + 35, levelSix2.getLocation().y + 70);
	        
	        g1.drawLine(levelSix1.getLocation().x + 70, levelSix1.getLocation().y, bossLevel.getLocation().x + 15, bossLevel.getLocation().y + 70);
	        g1.drawLine(levelSix2.getLocation().x, levelSix2.getLocation().y, bossLevel.getLocation().x + 55, bossLevel.getLocation().y + 70);
        }
	}    
}

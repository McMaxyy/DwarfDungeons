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

import constants.Enemies;
import constants.Player;
import main.GameWindow;

public class EndlessLevel extends JPanel implements ActionListener{

	private GameWindow window;
	private Player player = new Player();
	private Enemies enemy = new Enemies();
	private int levelIndex;
	private Image bonfireIcon, bossIcon, fightIcon, merchIcon, randIcon, strEnemyIcon, quitBtn, returnBtn;
	private JButton stageOne, stageTwo, stageThree, menuButton, quitButton, levelOne, bossLevel;
	private Random rand = new Random();
	private JButton levelTwo1, levelTwo2, levelThree1, levelThree2, levelThree3,
	levelFour1, levelFour2, levelFour3, levelFour4, levelFive1, levelFive2, levelFive3, levelFive4, 
	levelFive5, levelFive6, levelFive7, levelFive8, levelSix1, levelSix2, levelSeven1, levelSeven2,
	levelSeven3, levelSeven4, levelSeven5, levelSeven6, levelSeven7, levelSeven8, levelEight1,
	levelEight2, levelEight3, levelNine1, levelNine2, levelNine3, levelNine4, levelTen1, levelTen2;
	private static String levelThree1Txt, levelThree2Txt, levelThree3Txt,
	levelFour1Txt, levelFour2Txt, levelFour3Txt, levelFour4Txt, levelFive1Txt, levelFive2Txt, levelFive3Txt, levelFive4Txt, 
	levelFive5Txt, levelFive6Txt, levelFive7Txt, levelFive8Txt, levelSix1Txt, levelSix2Txt, levelSeven1Txt, levelSeven2Txt,
	levelSeven3Txt, levelSeven4Txt, levelSeven5Txt, levelSeven6Txt, levelSeven7Txt, levelSeven8Txt, levelEight1Txt,
	levelEight2Txt, levelEight3Txt, levelNine1Txt, levelNine2Txt, levelNine3Txt, levelNine4Txt, levelTen1Txt, levelTen2Txt;
	private JButton[] levelButtons = new JButton[] {levelOne, levelTwo1, levelTwo2, levelThree1, levelThree2, levelThree3,
		levelFour1, levelFour2, levelFour3, levelFour4, levelFive1, levelFive2, levelFive3, levelFive4, 
		levelFive5, levelFive6, levelFive7, levelFive8, levelSix1, levelSix2, levelSeven1, levelSeven2,
		levelSeven3, levelSeven4, levelSeven5, levelSeven6, levelSeven7, levelSeven8, levelEight1,
		levelEight2, levelEight3, levelNine1, levelNine2, levelNine3, levelNine4, levelTen1, levelTen2, bossLevel};
	private int arrL = 0;
	
	public EndlessLevel(GameWindow window, int currentLevel) {
		this.window = window;
		levelIndex = currentLevel;
		enemy.setBossActive(false);
		enemy.setStrongEnemyActive(false);
		
		setLayout(null);
		
		loadIcons();
		initComponents();
		setStage();
		setButtons();
	}
	
	private void setButtons() {
		for(JButton button : levelButtons) {
			button.setFocusable(false);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
		}		
	}
	
	private void setLevel(JButton button) {
		int x;
		x = rand.nextInt(100);
        if(x < 15)
        	button.setText("Bonfire");
        else if (x < 30)
        	button.setText("Merchant");
        else if (x < 60)
        	button.setText("???");
        else if (x < 90)
        	button.setText("Fight");
        else
        	button.setText("Strong Enemy");
        
        levelButtons[arrL] = button;
        arrL++;       
	}

	private void setStage() { 
		int x;
		arrL = 0;
		
        levelOne = new JButton();
        levelOne.setEnabled(false);
        levelOne.setVisible(true);
		levelOne.setActionCommand("Level One");
		levelOne.setIcon(new ImageIcon(fightIcon));
		levelOne.addActionListener(this);
		levelOne.setBounds(200, 25, 70, 70);
        add(levelOne);
        levelButtons[arrL] = levelOne;
        arrL++;
		
		levelTwo1 = new JButton();
        levelTwo1.setEnabled(false);
        levelTwo1.setVisible(true);
        levelTwo1.setActionCommand("Fight");
        levelTwo1.setIcon(new ImageIcon(setIcons(levelTwo1.getActionCommand())));
        levelTwo1.addActionListener(this);
        levelTwo1.setBounds(200, 125, 70, 70);
        add(levelTwo1);
        levelButtons[arrL] = levelTwo1;
        arrL++;
        
        levelTwo2 = new JButton();
        levelTwo2.setEnabled(false);
        levelTwo2.setVisible(true);
        levelTwo2.setActionCommand("Fight");
        levelTwo2.setIcon(new ImageIcon(setIcons(levelTwo2.getActionCommand())));
        levelTwo2.addActionListener(this);
        levelTwo2.setBounds(300, 25, 70, 70);
        add(levelTwo2);
        levelButtons[arrL] = levelTwo2;
        arrL++;
        
        levelThree1 = new JButton();
        levelThree1.setEnabled(false);
        levelThree1.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelThree1);        
        levelThree1Txt = levelThree1.getText();
        }
        else
        	levelThree1.setText(levelThree1Txt);
        levelThree1.setActionCommand(levelThree1.getText());
        levelThree1.setText("");
        levelThree1.setIcon(new ImageIcon(setIcons(levelThree1.getActionCommand())));
        levelThree1.addActionListener(this);
        levelThree1.setBounds(240, 220, 70, 70);
        add(levelThree1);
		
        levelThree2 = new JButton();
        levelThree2.setEnabled(false);
        levelThree2.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelThree2);        
        	levelThree2Txt = levelThree2.getText();
        }
        else
        	levelThree2.setText(levelThree2Txt);
        levelThree2.setActionCommand(levelThree2.getText());
        levelThree2.setText("");
        levelThree2.setIcon(new ImageIcon(setIcons(levelThree2.getActionCommand())));
        levelThree2.addActionListener(this);
        levelThree2.setBounds(320, 135, 70, 70);
        add(levelThree2);
        
        levelThree3 = new JButton();
        levelThree3.setEnabled(false);
        levelThree3.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelThree3);        
        	levelThree3Txt =levelThree3.getText();
        }
        else
        	levelThree3.setText(levelThree3Txt);
        levelThree3.setActionCommand(levelThree3.getText());
        levelThree3.setText("");
        levelThree3.setIcon(new ImageIcon(setIcons(levelThree3.getActionCommand())));
        levelThree3.addActionListener(this);
        levelThree3.setBounds(400, 50, 70, 70);
        add(levelThree3);
        
        levelFour1 = new JButton();
        levelFour1.setEnabled(false);
        levelFour1.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFour1);        
        	levelFour1Txt =levelFour1.getText();
        }
        else
        	levelFour1.setText(levelFour1Txt);
        levelFour1.setActionCommand(levelFour1.getText());
        levelFour1.setText("");
        levelFour1.setIcon(new ImageIcon(setIcons(levelFour1.getActionCommand())));
        levelFour1.addActionListener(this);
        levelFour1.setBounds(240, 320, 70, 70);
        add(levelFour1);
        
        levelFour2 = new JButton();
        levelFour2.setEnabled(false);
        levelFour2.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFour2);        
        	levelFour2Txt =levelFour2.getText();
        }
        else
        	levelFour2.setText(levelFour2Txt);
        levelFour2.setActionCommand(levelFour2.getText());
        levelFour2.setText("");
        levelFour2.setIcon(new ImageIcon(setIcons(levelFour2.getActionCommand())));
        levelFour2.addActionListener(this);
        levelFour2.setBounds(340, 250, 70, 70);
        add(levelFour2);
        
        levelFour3 = new JButton();
        levelFour3.setEnabled(false);
        levelFour3.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFour3);       
        	levelFour3Txt = levelFour3.getText();
        }
        else
        	levelFour3.setText(levelFour3Txt);
        levelFour3.setActionCommand(levelFour3.getText());
        levelFour3.setText("");
        levelFour3.setIcon(new ImageIcon(setIcons(levelFour3.getActionCommand())));
        levelFour3.addActionListener(this);
        levelFour3.setBounds(420, 155, 70, 70);
        add(levelFour3);
        
        levelFour4 = new JButton();
        levelFour4.setEnabled(false);
        levelFour4.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFour4);      
        	levelFour4Txt =levelFour4.getText();
        }
        else
        	levelFour4.setText(levelFour4Txt);
        levelFour4.setActionCommand(levelFour4.getText());
        levelFour4.setText("");
        levelFour4.setIcon(new ImageIcon(setIcons(levelFour4.getActionCommand())));
        levelFour4.addActionListener(this);
        levelFour4.setBounds(500, 50, 70, 70);
        add(levelFour4);
        
        levelFive1 = new JButton();
        levelFive1.setEnabled(false);
        levelFive1.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive1);       
        	levelFive1Txt =levelFive1.getText();
        }
        else
        	levelFive1.setText(levelFive1Txt);
        levelFive1.setActionCommand(levelFive1.getText());
        levelFive1.setText("");
        levelFive1.setIcon(new ImageIcon(setIcons(levelFive1.getActionCommand())));
        levelFive1.addActionListener(this);
        levelFive1.setBounds(200, 520, 70, 70);       
        add(levelFive1);
        
        levelFive2 = new JButton();
        levelFive2.setEnabled(false);
        levelFive2.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive2);
        	levelFive2Txt =levelFive2.getText();
        }
        else
        	levelFive2.setText(levelFive2Txt);
        levelFive2.setActionCommand(levelFive2.getText());
        levelFive2.setText("");
        levelFive2.setIcon(new ImageIcon(setIcons(levelFive2.getActionCommand())));
        levelFive2.addActionListener(this);
        levelFive2.setBounds(290, 455, 70, 70);
        add(levelFive2);
        
        levelFive3 = new JButton();
        levelFive3.setEnabled(false);
        levelFive3.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive3);        
        	levelFive3Txt =levelFive3.getText();
        }
        else
        	levelFive3.setText(levelFive3Txt);
        levelFive3.setActionCommand(levelFive3.getText());
        levelFive3.setText("");
        levelFive3.setIcon(new ImageIcon(setIcons(levelFive3.getActionCommand())));
        levelFive3.addActionListener(this);
        levelFive3.setBounds(375, 410, 70, 70);
        add(levelFive3);
        
        levelFive4 = new JButton();
        levelFive4.setEnabled(false);
        levelFive4.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive4);        
        	levelFive4Txt =levelFive4.getText();
        }
        else
        	levelFive4.setText(levelFive4Txt);
        levelFive4.setActionCommand(levelFive4.getText());
        levelFive4.setText("");
        levelFive4.setIcon(new ImageIcon(setIcons(levelFive4.getActionCommand())));
        levelFive4.addActionListener(this);
        levelFive4.setBounds(460, 340, 70, 70);
        add(levelFive4);
        
        levelFive5 = new JButton();
        levelFive5.setEnabled(false);
        levelFive5.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive5);       
        	levelFive5Txt = levelFive5.getText();
        }
        else
        	levelFive5.setText(levelFive5Txt);
        levelFive5.setActionCommand(levelFive5.getText());
        levelFive5.setText("");
        levelFive5.setIcon(new ImageIcon(setIcons(levelFive5.getActionCommand())));
        levelFive5.addActionListener(this);
        levelFive5.setBounds(530, 260, 70, 70);
        add(levelFive5);
        
        levelFive6 = new JButton();
        levelFive6.setEnabled(false);
        levelFive6.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive6);        
        	levelFive6Txt = levelFive6.getText();
        }
        else
        	levelFive6.setText(levelFive6Txt);
        levelFive6.setActionCommand(levelFive6.getText());
        levelFive6.setText("");
        levelFive6.setIcon(new ImageIcon(setIcons(levelFive6.getActionCommand())));
        levelFive6.addActionListener(this);
        levelFive6.setBounds(580, 180, 70, 70);
        add(levelFive6);
        
        levelFive7 = new JButton();
        levelFive7.setEnabled(false);
        levelFive7.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive7);        
        	levelFive7Txt =levelFive7.getText();
        }
        else
        	levelFive7.setText(levelFive7Txt);
        levelFive7.setActionCommand(levelFive7.getText());
        levelFive7.setText("");
        levelFive7.setIcon(new ImageIcon(setIcons(levelFive7.getActionCommand())));
        levelFive7.addActionListener(this);
        levelFive7.setBounds(620, 100, 70, 70);
        add(levelFive7);
        
        levelFive8 = new JButton();
        levelFive8.setEnabled(false);
        levelFive8.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelFive8);        
        	levelFive8Txt =levelFive8.getText();
        }
        else
        	levelFive8.setText(levelFive8Txt);
        levelFive8.setActionCommand(levelFive8.getText());
        levelFive8.setText("");
        levelFive8.setIcon(new ImageIcon(setIcons(levelFive8.getActionCommand())));
        levelFive8.addActionListener(this);
        levelFive8.setBounds(650, 20, 70, 70);
        add(levelFive8);
        
        levelSix1 = new JButton();
        levelSix1.setEnabled(false);
        levelSix1.setVisible(true);
        if(levelIndex == 1) {
        	x = rand.nextInt(2);
            if(x == 0)
            	levelSix1.setText("Bonfire");
            else
            	levelSix1.setText("Merchant");       
        	levelSix1Txt =levelSix1.getText();
        }
        else
        	levelSix1.setText(levelSix1Txt);
        levelSix1.setActionCommand(levelSix1.getText());
        levelSix1.setText("");
        levelSix1.setIcon(new ImageIcon(setIcons(levelSix1.getActionCommand())));
        levelSix1.addActionListener(this);
        levelSix1.setBounds(570, 410, 70, 70);
        add(levelSix1);
        levelButtons[arrL] = levelSix1;
        arrL++;
        
        levelSix2 = new JButton();
        levelSix2.setEnabled(false);
        levelSix2.setVisible(true);
        if(levelIndex == 1) {
        	x = rand.nextInt(2);
            if(x == 0)
            	levelSix2.setText("Bonfire");
            else
            	levelSix2.setText("Merchant");       
        	levelSix2Txt =levelSix2.getText();
        }
        else
        	levelSix2.setText(levelSix2Txt);
        levelSix2.setActionCommand(levelSix2.getText());
        levelSix2.setText("");
        levelSix2.setIcon(new ImageIcon(setIcons(levelSix2.getActionCommand())));
        levelSix2.addActionListener(this);
        levelSix2.setBounds(740, 200, 70, 70);
        add(levelSix2);
        levelButtons[arrL] = levelSix2;
        arrL++;
        
        levelSeven1 = new JButton();
        levelSeven1.setEnabled(false);
        levelSeven1.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven1);       
        	levelSeven1Txt =levelSeven1.getText();
        }
        else
        	levelSeven1.setText(levelSeven1Txt);
        levelSeven1.setActionCommand(levelSeven1.getText());
        levelSeven1.setText("");
        levelSeven1.setIcon(new ImageIcon(setIcons(levelSeven1.getActionCommand())));
        levelSeven1.addActionListener(this);
        levelSeven1.setBounds(520, 590, 70, 70);       
        add(levelSeven1);
        
        levelSeven2 = new JButton();
        levelSeven2.setEnabled(false);
        levelSeven2.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven2);
        	levelSeven2Txt =levelSeven2.getText();
        }
        else
        	levelSeven2.setText(levelSeven2Txt);
        levelSeven2.setActionCommand(levelSeven2.getText());
        levelSeven2.setText("");
        levelSeven2.setIcon(new ImageIcon(setIcons(levelSeven2.getActionCommand())));
        levelSeven2.addActionListener(this);
        levelSeven2.setBounds(610, 525, 70, 70);
        add(levelSeven2);
        
        levelSeven3 = new JButton();
        levelSeven3.setEnabled(false);
        levelSeven3.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven3);        
        	levelSeven3Txt =levelSeven3.getText();
        }
        else
        	levelSeven3.setText(levelSeven3Txt);
        levelSeven3.setActionCommand(levelSeven3.getText());
        levelSeven3.setText("");
        levelSeven3.setIcon(new ImageIcon(setIcons(levelSeven3.getActionCommand())));
        levelSeven3.addActionListener(this);
        levelSeven3.setBounds(695, 480, 70, 70);
        add(levelSeven3);
        
        levelSeven4 = new JButton();
        levelSeven4.setEnabled(false);
        levelSeven4.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven4);        
        	levelSeven4Txt =levelSeven4.getText();
        }
        else
        	levelSeven4.setText(levelSeven4Txt);
        levelSeven4.setActionCommand(levelSeven4.getText());
        levelSeven4.setText("");
        levelSeven4.setIcon(new ImageIcon(setIcons(levelSeven4.getActionCommand())));
        levelSeven4.addActionListener(this);
        levelSeven4.setBounds(780, 410, 70, 70);
        add(levelSeven4);
        
        levelSeven5 = new JButton();
        levelSeven5.setEnabled(false);
        levelSeven5.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven5);       
        	levelSeven5Txt = levelSeven5.getText();
        }
        else
        	levelSeven5.setText(levelSeven5Txt);
        levelSeven5.setActionCommand(levelSeven5.getText());
        levelSeven5.setText("");
        levelSeven5.setIcon(new ImageIcon(setIcons(levelSeven5.getActionCommand())));
        levelSeven5.addActionListener(this);
        levelSeven5.setBounds(850, 330, 70, 70);
        add(levelSeven5);
        
        levelSeven6 = new JButton();
        levelSeven6.setEnabled(false);
        levelSeven6.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven6);        
        	levelSeven6Txt = levelSeven6.getText();
        }
        else
        	levelSeven6.setText(levelSeven6Txt);
        levelSeven6.setActionCommand(levelSeven6.getText());
        levelSeven6.setText("");
        levelSeven6.setIcon(new ImageIcon(setIcons(levelSeven6.getActionCommand())));
        levelSeven6.addActionListener(this);
        levelSeven6.setBounds(900, 250, 70, 70);
        add(levelSeven6);
        
        levelSeven7 = new JButton();
        levelSeven7.setEnabled(false);
        levelSeven7.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven7);        
        	levelSeven7Txt =levelSeven7.getText();
        }
        else
        	levelSeven7.setText(levelSeven7Txt);
        levelSeven7.setActionCommand(levelSeven7.getText());
        levelSeven7.setText("");
        levelSeven7.setIcon(new ImageIcon(setIcons(levelSeven7.getActionCommand())));
        levelSeven7.addActionListener(this);
        levelSeven7.setBounds(940, 170, 70, 70);
        add(levelSeven7);
        
        levelSeven8 = new JButton();
        levelSeven8.setEnabled(false);
        levelSeven8.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelSeven8);        
        	levelSeven8Txt =levelSeven8.getText();
        }
        else
        	levelSeven8.setText(levelSeven8Txt);
        levelSeven8.setActionCommand(levelSeven8.getText());
        levelSeven8.setText("");
        levelSeven8.setIcon(new ImageIcon(setIcons(levelSeven8.getActionCommand())));
        levelSeven8.addActionListener(this);
        levelSeven8.setBounds(970, 90, 70, 70);
        add(levelSeven8);
        
        levelEight1 = new JButton();
        levelEight1.setEnabled(false);
        levelEight1.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelEight1);       
        	levelEight1Txt =levelEight1.getText();
        }
        else
        	levelEight1.setText(levelEight1Txt);
        levelEight1.setActionCommand(levelEight1.getText());
        levelEight1.setText("");
        levelEight1.setIcon(new ImageIcon(setIcons(levelEight1.getActionCommand())));
        levelEight1.addActionListener(this);
        levelEight1.setBounds(800, 550, 70, 70);       
        add(levelEight1);
        
        levelEight2 = new JButton();
        levelEight2.setEnabled(false);
        levelEight2.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelEight2);
        	levelEight2Txt =levelEight2.getText();
        }
        else
        	levelEight2.setText(levelEight2Txt);
        levelEight2.setActionCommand(levelEight2.getText());
        levelEight2.setText("");
        levelEight2.setIcon(new ImageIcon(setIcons(levelEight2.getActionCommand())));
        levelEight2.addActionListener(this);
        levelEight2.setBounds(940, 400, 70, 70);
        add(levelEight2);
        
        levelEight3 = new JButton();
        levelEight3.setEnabled(false);
        levelEight3.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelEight3);        
        	levelEight3Txt =levelEight3.getText();
        }
        else
        	levelEight3.setText(levelEight3Txt);
        levelEight3.setActionCommand(levelEight3.getText());
        levelEight3.setText("");
        levelEight3.setIcon(new ImageIcon(setIcons(levelEight3.getActionCommand())));
        levelEight3.addActionListener(this);
        levelEight3.setBounds(1050, 220, 70, 70);
        add(levelEight3);
        
        levelNine1 = new JButton();
        levelNine1.setEnabled(false);
        levelNine1.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelNine1);       
        	levelNine1Txt =levelNine1.getText();
        }
        else
        	levelNine1.setText(levelNine1Txt);
        levelNine1.setActionCommand(levelNine1.getText());
        levelNine1.setText("");
        levelNine1.setIcon(new ImageIcon(setIcons(levelNine1.getActionCommand())));
        levelNine1.addActionListener(this);
        levelNine1.setBounds(920, 590, 70, 70);       
        add(levelNine1);
        
        levelNine2 = new JButton();
        levelNine2.setEnabled(false);
        levelNine2.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelNine2);
        	levelNine2Txt =levelNine2.getText();
        }
        else
        	levelNine2.setText(levelNine2Txt);
        levelNine2.setActionCommand(levelNine2.getText());
        levelNine2.setText("");
        levelNine2.setIcon(new ImageIcon(setIcons(levelNine2.getActionCommand())));
        levelNine2.addActionListener(this);
        levelNine2.setBounds(980, 500, 70, 70);
        add(levelNine2);
        
        levelNine3 = new JButton();
        levelNine3.setEnabled(false);
        levelNine3.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelNine3);        
        	levelNine3Txt =levelNine3.getText();
        }
        else
        	levelNine3.setText(levelNine3Txt);
        levelNine3.setActionCommand(levelNine3.getText());
        levelNine3.setText("");
        levelNine3.setIcon(new ImageIcon(setIcons(levelNine3.getActionCommand())));
        levelNine3.addActionListener(this);
        levelNine3.setBounds(1060, 400, 70, 70);
        add(levelNine3);
        
        levelNine4 = new JButton();
        levelNine4.setEnabled(false);
        levelNine4.setVisible(true);
        if(levelIndex == 1) {
        	setLevel(levelNine4);        
        	levelNine4Txt =levelNine4.getText();
        }
        else
        	levelNine4.setText(levelNine4Txt);
        levelNine4.setActionCommand(levelNine4.getText());
        levelNine4.setText("");
        levelNine4.setIcon(new ImageIcon(setIcons(levelNine4.getActionCommand())));
        levelNine4.addActionListener(this);
        levelNine4.setBounds(1140, 310, 70, 70);
        add(levelNine4);
        
        levelTen1 = new JButton();
        levelTen1.setEnabled(false);
        levelTen1.setVisible(true);
        if(levelIndex == 1) {
        	x = rand.nextInt(2);
            if(x == 0)
            	levelTen1.setText("Bonfire");
            else
            	levelTen1.setText("Merchant");       
        	levelTen1Txt = levelTen1.getText();
        }
        else
        	levelTen1.setText(levelTen1Txt);
        levelTen1.setActionCommand(levelTen1.getText());
        levelTen1.setText("");
        levelTen1.setIcon(new ImageIcon(setIcons(levelTen1.getActionCommand())));
        levelTen1.addActionListener(this);
        levelTen1.setBounds(1060, 575, 70, 70);
        add(levelTen1);
        levelButtons[arrL] = levelTen1;
        arrL++;
        
        levelTen2 = new JButton();
        levelTen2.setEnabled(false);
        levelTen2.setVisible(true);
        if(levelIndex == 1) {
        	x = rand.nextInt(2);
            if(x == 0)
            	levelTen2.setText("Bonfire");
            else
            	levelTen2.setText("Merchant");       
        	levelTen2Txt =levelTen2.getText();
        }
        else
        	levelTen2.setText(levelTen2Txt);
        levelTen2.setActionCommand(levelTen2.getText());
        levelTen2.setText("");
        levelTen2.setIcon(new ImageIcon(setIcons(levelTen2.getActionCommand())));
        levelTen2.addActionListener(this);
        levelTen2.setBounds(1150, 470, 70, 70);
        add(levelTen2);
        levelButtons[arrL] = levelTen2;
        arrL++;
        
        bossLevel = new JButton();
		bossLevel.setEnabled(false);
		bossLevel.setVisible(true);
		bossLevel.setActionCommand("Boss");
		bossLevel.setIcon(new ImageIcon(bossIcon));
		bossLevel.addActionListener(this);
		bossLevel.setBounds(1170, 590, 70, 70);
        add(bossLevel);
        levelButtons[arrL] = bossLevel;
        
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
		if(x >= 6 || y >= 6) {
			hideLevels();
			setStage();
		}
	}

	private void initComponents() {
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
        quitButton.setBounds(25, 85, 100, 50);
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
	
	public void showLevels() {
		for(JButton button: levelButtons)
			button.setVisible(true);
	}
	
	public void hideLevels() {
		for(JButton button: levelButtons)
			button.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch(command) {
		case "Quit":
			System.exit(0);
			break;
		case "Return":
			window.showHomeScreen();
			break;
		case "Stage1":
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
			}
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g1 = (Graphics2D)g;
		g1.setStroke(new BasicStroke(3));
		
		g1.drawLine(levelOne.getLocation().x + 35, levelOne.getLocation().y + 70, levelTwo1.getLocation().x + 35, levelTwo1.getLocation().y);
		g1.drawLine(levelOne.getLocation().x + 70, levelOne.getLocation().y + 35, levelTwo2.getLocation().x, levelTwo2.getLocation().y + 35);
		
		g1.drawLine(levelTwo1.getLocation().x + 35, levelTwo1.getLocation().y + 70, levelThree1.getLocation().x, levelThree1.getLocation().y);
		g1.drawLine(levelTwo1.getLocation().x + 70, levelTwo1.getLocation().y + 35, levelThree2.getLocation().x, levelThree2.getLocation().y + 35);
	
		g1.drawLine(levelTwo2.getLocation().x + 35, levelTwo2.getLocation().y + 70, levelThree2.getLocation().x + 35, levelThree2.getLocation().y);
		g1.drawLine(levelTwo2.getLocation().x + 70, levelTwo2.getLocation().y + 35, levelThree3.getLocation().x, levelThree3.getLocation().y + 15);
	
		g1.drawLine(levelThree1.getLocation().x + 35, levelThree1.getLocation().y + 70, levelFour1.getLocation().x + 35, levelFour1.getLocation().y);
		g1.drawLine(levelThree1.getLocation().x + 70, levelThree1.getLocation().y + 35, levelFour2.getLocation().x, levelFour2.getLocation().y + 15);
		
		g1.drawLine(levelThree2.getLocation().x + 35, levelThree2.getLocation().y + 70, levelFour2.getLocation().x + 35, levelFour2.getLocation().y);
		g1.drawLine(levelThree2.getLocation().x + 70, levelThree2.getLocation().y + 35, levelFour3.getLocation().x, levelFour3.getLocation().y + 35);
		
		g1.drawLine(levelThree3.getLocation().x + 35, levelThree3.getLocation().y + 70, levelFour3.getLocation().x + 35, levelFour3.getLocation().y);
		g1.drawLine(levelThree3.getLocation().x + 70, levelThree3.getLocation().y + 35, levelFour4.getLocation().x, levelFour4.getLocation().y + 35);
		
		g1.drawLine(levelFour1.getLocation().x + 25, levelFour1.getLocation().y + 70, levelFive1.getLocation().x + 35, levelFive1.getLocation().y);
		g1.drawLine(levelFour1.getLocation().x + 55, levelFour1.getLocation().y + 70, levelFive2.getLocation().x + 35, levelFive2.getLocation().y);
		
		g1.drawLine(levelFour2.getLocation().x + 35, levelFour2.getLocation().y + 70, levelFive3.getLocation().x + 35, levelFive3.getLocation().y);
		g1.drawLine(levelFour2.getLocation().x + 70, levelFour2.getLocation().y + 35, levelFive4.getLocation().x + 35, levelFive4.getLocation().y);
		
		g1.drawLine(levelFour3.getLocation().x + 70, levelFour3.getLocation().y + 70, levelFive5.getLocation().x, levelFive5.getLocation().y);
		g1.drawLine(levelFour3.getLocation().x + 70, levelFour3.getLocation().y + 35, levelFive6.getLocation().x, levelFive6.getLocation().y + 35);
		
		g1.drawLine(levelFour4.getLocation().x + 70, levelFour4.getLocation().y + 70, levelFive7.getLocation().x, levelFive7.getLocation().y + 35);
		g1.drawLine(levelFour4.getLocation().x + 70, levelFour4.getLocation().y + 35, levelFive8.getLocation().x, levelFive8.getLocation().y + 35);
		
		g1.drawLine(levelFive1.getLocation().x + 70, levelFive1.getLocation().y + 55, levelSix1.getLocation().x, levelSix1.getLocation().y + 65);
		g1.drawLine(levelFive2.getLocation().x + 70, levelFive2.getLocation().y + 55, levelSix1.getLocation().x, levelSix1.getLocation().y + 45);
		g1.drawLine(levelFive3.getLocation().x + 70, levelFive3.getLocation().y + 35, levelSix1.getLocation().x, levelSix1.getLocation().y + 25);
		g1.drawLine(levelFive4.getLocation().x + 70, levelFive4.getLocation().y + 55, levelSix1.getLocation().x, levelSix1.getLocation().y + 5);
		
		g1.drawLine(levelFive5.getLocation().x + 70, levelFive5.getLocation().y + 35, levelSix2.getLocation().x, levelSix2.getLocation().y + 55);
		g1.drawLine(levelFive6.getLocation().x + 70, levelFive6.getLocation().y + 35, levelSix2.getLocation().x, levelSix2.getLocation().y + 25);
		g1.drawLine(levelFive7.getLocation().x + 70, levelFive7.getLocation().y + 35, levelSix2.getLocation().x + 15, levelSix2.getLocation().y);
		g1.drawLine(levelFive8.getLocation().x + 70, levelFive8.getLocation().y + 55, levelSix2.getLocation().x + 45, levelSix2.getLocation().y);
		
		g1.drawLine(levelSix1.getLocation().x + 25, levelSix1.getLocation().y + 70, levelSeven1.getLocation().x + 35, levelSeven1.getLocation().y);
		g1.drawLine(levelSix1.getLocation().x + 55, levelSix1.getLocation().y + 70, levelSeven2.getLocation().x + 35, levelSeven2.getLocation().y);
		g1.drawLine(levelSix1.getLocation().x + 70, levelSix1.getLocation().y + 35, levelSeven3.getLocation().x + 35, levelSeven3.getLocation().y);
		g1.drawLine(levelSix1.getLocation().x + 70, levelSix1.getLocation().y + 15, levelSeven4.getLocation().x, levelSeven4.getLocation().y + 35);
		
		g1.drawLine(levelSix2.getLocation().x + 55, levelSix2.getLocation().y + 70, levelSeven5.getLocation().x, levelSeven5.getLocation().y + 35);
		g1.drawLine(levelSix2.getLocation().x + 70, levelSix2.getLocation().y + 55, levelSeven6.getLocation().x, levelSeven6.getLocation().y + 35);
		g1.drawLine(levelSix2.getLocation().x + 70, levelSix2.getLocation().y + 35, levelSeven7.getLocation().x, levelSeven7.getLocation().y + 35);
		g1.drawLine(levelSix2.getLocation().x + 70, levelSix2.getLocation().y + 15, levelSeven8.getLocation().x, levelSeven8.getLocation().y + 35);
	}
}

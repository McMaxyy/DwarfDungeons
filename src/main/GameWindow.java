package main;

import java.awt.Color;

import javax.swing.JFrame;

import stages.FightScene;
import stages.HomeScreen;
import stages.LevelSelector;
import stages.MainMenu;
import stages.SlotMachine;

public class GameWindow{
	
    private static MainMenu mainMenu;
    private static HomeScreen homeScreen;
    private static LevelSelector levelSelector;
    private static FightScene fightScene;
    private static SlotMachine slotMachine;
    JFrame frame = new JFrame("Lemony Squeeze");

    public GameWindow() {
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(1280, 720);
    	frame.setLocationRelativeTo(null);
    	frame.setResizable(false);
    	frame.getContentPane().setBackground(Color.BLUE);
    	
//        setUndecorated(true);
//        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        
        mainMenu = new MainMenu(this);
        frame.setContentPane(mainMenu);
        
    }
    
    public void showHomeScreen() {
        homeScreen = new HomeScreen(this);
        frame.setContentPane(homeScreen);
        frame.revalidate();
    }
    
    public void showMainMenu() {
    	mainMenu = new MainMenu(this);
    	frame.setContentPane(mainMenu);
    	frame.revalidate();
    }
    
    public void showLevelSelector(int currentLevel) {
    	levelSelector = new LevelSelector(this, currentLevel);
    	frame.setContentPane(levelSelector);
    	frame.revalidate();
    }
    
    public void showFightScene(int levelIndex){
    	fightScene = new FightScene(this, levelIndex);
    	frame.setContentPane(fightScene);
    	frame.revalidate();
    }
    
    public void showSlotMachine(){
    	slotMachine = new SlotMachine(this);
    	frame.setContentPane(slotMachine);
    	frame.revalidate();
    }

	public void setVisible(boolean b) {
		frame.setVisible(b);		
	}
}


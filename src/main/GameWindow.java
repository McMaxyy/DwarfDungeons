package main;

import java.awt.Color;

import javax.swing.JFrame;

import stages.*;

public class GameWindow{
	
    private static MainMenu mainMenu;
    private static HomeScreen homeScreen;
    private static LevelSelector levelSelector;
    private static FightScene fightScene;
    private static SlotMachine slotMachine;
    private static Merchant merchant;
    private static Bonfire bonfire;
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
    
    public void showFightScene(int levelIndex, int selectedStage){
    	fightScene = new FightScene(this, levelIndex, selectedStage);
    	frame.setContentPane(fightScene);
    	frame.revalidate();
    }
    
    public void showSlotMachine(){
    	slotMachine = new SlotMachine(this);
    	frame.setContentPane(slotMachine);
    	frame.revalidate();
    }
    
    public void showMerchant(int levelIndex) {
    	merchant = new Merchant(this, levelIndex);
    	frame.setContentPane(merchant);
    	frame.revalidate();
    }
    
    public void showBonfire(int levelIndex) {
    	bonfire = new Bonfire(this, levelIndex);
    	frame.setContentPane(bonfire);
    	frame.revalidate();
    }

	public void setVisible(boolean b) {
		frame.setVisible(b);		
	}
}


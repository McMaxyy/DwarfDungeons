package main;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

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
    private static Store store;
    private static EndlessLevel endless;
    private static Mining mining;
    private static Blacksmith blacksmith;
    JFrame frame = new JFrame("Lemony Squeeze");
    
    public GameWindow() {
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(1280, 720);
    	frame.setLocationRelativeTo(null);
    	frame.setUndecorated(true);
    	frame.setResizable(false);
    	frame.getContentPane().setBackground(Color.BLUE);  	
        
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
    
    public void showStore() {
    	store = new Store(this);
    	frame.setContentPane(store);
    	frame.revalidate();
    }
    
    public void showFunMode(int currentLevel) {
    	endless = new EndlessLevel(this, currentLevel);
    	frame.setContentPane(endless);
    	frame.revalidate();
    }
    
    public void showMine() {
    	mining = new Mining(this);
    	frame.setContentPane(mining);
    	frame.revalidate();
    }
    
    public void showBlacksmith() {
    	blacksmith = new Blacksmith(this);
    	frame.setContentPane(blacksmith);
    	frame.revalidate();
    }

	public void setVisible(boolean b) {
		frame.setVisible(b);		
	}
	
	public Point getMousePosition() {
	    PointerInfo info = MouseInfo.getPointerInfo();
	    return info.getLocation();
	}
}


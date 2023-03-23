package main;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import stages.FightScene;
import stages.HomeScreen;
import stages.LevelSelector;
import stages.MainMenu;
import stages.SlotMachine;

public class GameWindow extends JFrame {
	
    private static MainMenu mainMenu;
    private static HomeScreen homeScreen;
    private static LevelSelector levelSelector;
    private static FightScene fightScene;
    private static SlotMachine slotMachine;

    public GameWindow() {
        super("My Text-Based RPG Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        
        mainMenu = new MainMenu(this);
        setContentPane(mainMenu);
        
    }
    
    public void showHomeScreen() {
        homeScreen = new HomeScreen(this);
        setContentPane(homeScreen);
        revalidate();
    }
    
    public void showMainMenu() {
    	mainMenu = new MainMenu(this);
        setContentPane(mainMenu);
        revalidate();
    }
    
    public void showLevelSelector(int currentLevel) {
    	levelSelector = new LevelSelector(this, currentLevel);
        setContentPane(levelSelector);
        revalidate();
    }
    
    public void showFightScene(int levelIndex){
    	fightScene = new FightScene(this, levelIndex);
        setContentPane(fightScene);
        revalidate();
    }
    
    public void showSlotMachine(){
    	slotMachine = new SlotMachine(this);
        setContentPane(slotMachine);
        revalidate();
    }
}


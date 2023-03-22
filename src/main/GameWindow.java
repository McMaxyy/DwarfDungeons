package main;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import stages.FightScene;
import stages.HomeScreen;
import stages.LevelSelector;
import stages.MainMenu;

public class GameWindow extends JFrame {
	
    private MainMenu mainMenu;
    private HomeScreen homeScreen;
    private LevelSelector levelSelector;
    private FightScene fightScene;

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
        HomeScreen homeScreen = new HomeScreen(this);
        setContentPane(homeScreen);
        revalidate();
    }
    
    public void showMainMenu() {
    	MainMenu mainMenu = new MainMenu(this);
        setContentPane(mainMenu);
        revalidate();
    }
    
    public void showLevelSelector() {
    	LevelSelector levelSelector = new LevelSelector(this);
        setContentPane(levelSelector);
        revalidate();
    }
    
    public void showFightScene() throws IOException {
    	FightScene fightScene = new FightScene(this);
        setContentPane(fightScene);
        revalidate();
    }
}


package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameWindow;

public class HomeScreen extends JPanel implements ActionListener {
	
    private GameWindow window;

    public HomeScreen(GameWindow window) {
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
        
        JButton levelSelectButton = new JButton("Levels");
        levelSelectButton.setActionCommand("Levels");
        levelSelectButton.addActionListener(this);
        levelSelectButton.setBounds(200, 200, 100, 50);
        add(levelSelectButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Main menu")) {
        	window.showMainMenu();
        } 
        else if (command.equals("Quit")) {
            System.exit(0);
        }
        else if (command.equals("Levels")) {
            window.showLevelSelector();
        }
    }
}


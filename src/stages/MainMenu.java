package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameWindow;

public class MainMenu extends JPanel implements ActionListener {
	
    private GameWindow window;

    public MainMenu(GameWindow window) {
        this.window = window;
        
        setLayout(null);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setBounds(200, 200, 100, 50);
        add(startButton);
        
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(this);
        settingsButton.setBounds(200, 300, 100, 50);
        add(settingsButton);
        
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(200, 400, 100, 50);
        add(quitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Start")) {
            window.showHomeScreen();
        } else if (command.equals("Settings")) {
            // TODO: implement settings
        } else if (command.equals("Quit")) {
            System.exit(0);
        }
    }
}


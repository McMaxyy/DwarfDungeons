package stages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.GameWindow;

public class LevelSelector extends JPanel implements ActionListener{

	private GameWindow window;
	
	public LevelSelector(GameWindow window) {
		this.window = window;
		
		setLayout(null);
		
		JButton levelOne = new JButton("LevelOne");
		levelOne.setActionCommand("LevelOne");
		levelOne.addActionListener(this);
		levelOne.setBounds(200, 200, 100, 50);
        add(levelOne);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("LevelOne")) {
        	try {
				window.showFightScene();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
		
	}

}

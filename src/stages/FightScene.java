package stages;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;

import main.GameWindow;
import constants.Player;
import constants.Enemies;

public class FightScene extends JPanel implements ActionListener {
	
	private GameWindow window;
	private JLabel pic1;
	private Player player = new Player(window);
	private Enemies enemy = new Enemies(window);
	private JLabel lblPlayerHP= new JLabel();
	private JLabel lblEnemyHP= new JLabel();
	private JButton returnButton, attackButton, fleeButton, quitButton, menuButton;
	JButton[] buttons = new JButton[] {attackButton, fleeButton, quitButton, menuButton};
	String youLost = "You\nLost";
	String youWon = "You\nWon";

	public FightScene(GameWindow window) throws IOException{
		this.window = window;
		
		Font f = new Font("Serif", Font.PLAIN, 18);
		enemy.enemyOne();
		
		// Player image
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("res/chr.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		JLabel pic1 = new JLabel(new ImageIcon(img));
		pic1.setSize(100, 150);
		Image dimg = img.getScaledInstance(pic1.getWidth(), pic1.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		pic1.setIcon(imageIcon);		
		add(pic1);		
		pic1.setBounds(350, 500, pic1.getWidth(), pic1.getHeight());	
		
		setLayout(null);
		
		
		// HP labels
		player.playerShowHP(lblPlayerHP);
		lblPlayerHP.setBounds(200, 100, 100, 50);		
		add(lblPlayerHP);
		
		enemy.enemyShowHP(lblEnemyHP);
		lblEnemyHP.setBounds(200, 50, 100, 50);		
		add(lblEnemyHP);
		
		// Buttons				
		quitButton = new JButton();
		quitButton.setText("Quit");
		quitButton.setActionCommand("Quit");
        quitButton.addActionListener(this);
        quitButton.setBounds(375, 25, 100, 30);
        add(quitButton);		
		
        menuButton = new JButton();
        menuButton.setActionCommand("Main menu");
        menuButton.setText("Main menu");
        menuButton.addActionListener(this);
        menuButton.setBounds(25, 25, 100, 30);
        add(menuButton);      
        
        // Lost game button
        returnButton = new JButton();
        returnButton.setFont(f);
        returnButton.setActionCommand("Return");
        returnButton.addActionListener(this);
        returnButton.setBounds(200, 300, 100, 100);
        returnButton.setVisible(false);
        add(returnButton);
        
        // Action buttons
        attackButton = new JButton();
        attackButton.setActionCommand("Attack");
        attackButton.setText("Attack");
        attackButton.addActionListener(this);
        attackButton.setBounds(50, 500, 100, 50);
        add(attackButton);
        
        fleeButton = new JButton();
        fleeButton.setActionCommand("Flee");
        fleeButton.setText("Flee");
        fleeButton.addActionListener(this);
        fleeButton.setBounds(50, 600, 100, 50);
        add(fleeButton);
        
        buttons[0] = attackButton;
        buttons[1] = fleeButton;
        buttons[2] = quitButton;
        buttons[3] = menuButton;
        
        disableButtonFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
		if (command.equals("Quit")) {
            System.exit(0);
        }
		else if (command.equals("Main menu")) {
        	window.showMainMenu();
        }
		else if (command.equals("Return")) {
        	window.showHomeScreen();
        }
		else if (command.equals("Attack")) {
			enemy.enemyLoseHP();
			enemy.enemyShowHP(lblEnemyHP);
			lblEnemyHP.repaint();
			isEnemyDead();
//        	player.playerLoseHP();
//        	player.playerShowHP(lblPlayerHP);
//        	lblPlayerHP.repaint();
//        	isPlayerDead();
        } 
	}
	
	public void isPlayerDead() {
		if(player.getPlayerHP() <= 0) {
			returnButton.setText("<html>" + youLost.replaceAll("\\n", "<br>") + "</html>");
			returnButton.setVisible(true);
			for(JButton button : buttons)
				button.setEnabled(false);
		}			
	}
	
	public void isEnemyDead() {
		if(enemy.getEnemyHP() <= 0) {
			returnButton.setText("<html>" + youWon.replaceAll("\\n", "<br>") + "</html>");
			returnButton.setVisible(true);
			for(JButton button : buttons)
				button.setEnabled(false);
		}			
	}
	
	private void disableButtonFocus() {
		
		for(JButton button : buttons)
			button.setFocusable(false);		
	}
	
}

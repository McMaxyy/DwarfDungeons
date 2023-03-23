package constants;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Entities {

	public Entities() {
		
	}
	
	public JLabel loadEnemyImg(int selectedEnemy) {
		BufferedImage img = null;
		JLabel enemyIcon = new JLabel();
		switch(selectedEnemy) {
			case 1:
				try {
					img = ImageIO.read(new File("res/chr.png"));
				} catch (IOException e) {
				    e.printStackTrace();
				}
				break;
			case 2:
				try {
					img = ImageIO.read(new File("res/dragun.png"));
				} catch (IOException e) {
				    e.printStackTrace();
				}
				break;
		}
		JLabel pic1 = new JLabel(new ImageIcon(img));
		if(selectedEnemy == 1)
			pic1.setSize(100, 150);
		else if(selectedEnemy == 2)
			pic1.setSize(150, 150);
		Image dimg = img.getScaledInstance(pic1.getWidth(), pic1.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		pic1.setIcon(imageIcon);
		enemyIcon = pic1;
		return enemyIcon;
	}
	
	public JLabel loadPlayerImg(int selectedPlayer) {
		BufferedImage img = null;
		JLabel playerIcon = new JLabel();
		switch(selectedPlayer) {
			case 1:
				try {
					img = ImageIO.read(new File("res/chr.png"));
				} catch (IOException e) {
				    e.printStackTrace();
				}
				break;
			case 2:
				try {
					img = ImageIO.read(new File("res/dragun.png"));
				} catch (IOException e) {
				    e.printStackTrace();
				}
				break;
		}
		JLabel pic1 = new JLabel(new ImageIcon(img));
		if(selectedPlayer == 1)
			pic1.setSize(100, 150);
		else if(selectedPlayer == 2)
			pic1.setSize(150, 150);
		Image dimg = img.getScaledInstance(pic1.getWidth(), pic1.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		pic1.setIcon(imageIcon);
		playerIcon = pic1;
		return playerIcon;
	}
}

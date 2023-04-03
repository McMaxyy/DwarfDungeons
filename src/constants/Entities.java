package constants;

import java.awt.Image;
import java.awt.Toolkit;

public class Entities {
	
	public Image setEnemyStats(String img) {
		Image animation = Toolkit.getDefaultToolkit().getImage(img);
		return animation;
	}
	
}

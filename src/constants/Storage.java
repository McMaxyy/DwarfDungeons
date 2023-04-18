package constants;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class Storage {
	private final int attackButtonCost = 1;
	public Font font, font2, font3;
	private static Storage instance = null;
	
	public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
	
	public Storage() {
		createFont();
	}
	
	public int getAttackButtonCost() {
		return attackButtonCost;
	}
	
	// Load abilities
	public Abilities decapitate = new Decapitate();
	public Abilities overheadSwing = new Swing();
	public Abilities riposte = new Riposte();
	public Abilities rend = new Rend();
	public Abilities harden = new Harden();
	public Abilities whirlwind = new Whirlwind();
	public Abilities weaken = new Weaken();
	public Abilities stun = new Stun();
	
	//Load weapons
	public Weapons ironAxe = new IronAxe();
	public Weapons steelAxe = new SteelAxe();
	
	public Image setAnimation(String img) {
		Image animation = Toolkit.getDefaultToolkit().getImage(img);
		return animation;
	}
	
	private void createFont() {
		try {
		    font = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(25f);
		    font2 = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(18f);
		    font3 = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(11f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    // Register the font
		    ge.registerFont(font);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	}
}

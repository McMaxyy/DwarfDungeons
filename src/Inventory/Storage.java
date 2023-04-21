package Inventory;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Storage {
	private final int attackButtonCost = 1;
	public Font font, font2, font3, font4;
	private static Storage instance = null;
	public Border border = LineBorder.createBlackLineBorder();
	
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
	
	// Load weapons
	public Weapons ironAxe = new IronAxe();
	public Weapons steelAxe = new SteelAxe();
	public Weapons silverAxe = new SilverAxe();
	public Weapons goldAxe = new GoldAxe();
	public Weapons copperAxe = new CopperAxe();
	public Weapons titaniumAxe = new TitaniumAxe();
	public Weapons fieryAxe = new FieryAxe();
	public Weapons moltenAxe = new MoltenAxe();
	public Weapons waterAxe = new WaterAxe();
	
	// Load items
	public Items healthPot = new HealthPot();
	public Items shield = new Shield();
	public Items bomb = new Bomb();
	public Items poisonDart = new PoisonDart();
	public Items bigBomb = new Dynamite();
	public Items biggerBomb = new Bombs();
	
	// Set animation to enemies
	public Image setAnimation(String img) {
		Image animation = Toolkit.getDefaultToolkit().getImage(img);
		return animation;
	}
	
	// Create custom fonts
	private void createFont() {
		try {
		    font = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(25f);
		    font2 = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(18f);
		    font3 = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(11f);
		    font4 = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(10f);
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

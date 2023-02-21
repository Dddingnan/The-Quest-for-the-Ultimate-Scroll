package monster;

import javafx.scene.image.Image;

public class Monkey extends Monster {
	private static Image im = new Image("file:images/monster/fire1.png");
	private static int h = 40;
	private static int sp = 5;
	private static int wd = 50, ht = 50;
	private static int rg =  400;
	private static int at = 20;
	private static int attF = 200;
	private static int type = 1;
	
	
	public Monkey(int px, int py) {
		super(px, py, h, sp, wd, ht, rg, at, type, attF);
	}
	
	public Image getImage() {
		return im;
	}


}

package monster;

import javafx.scene.image.Image;

public class Goblin extends Monster{
	private static Image stand = new Image("file:images/monster/Goblin/Run.png");
	private static Image im_att = new Image("file:images/monster/Goblin/Run.png");
	private static int h = 40;
	private static int sp = 5;
	private static int wd = 50, ht = 50;
	private static int rg =  50;
	private static int at = 20;
	private static int attF = 50;
	private static int type = 0;
	private static int st_phase = 8;
	private static int im_wd = 50;
	private static int im_ht  = 50;
	

	
	public Goblin(int px, int py) {
		super(px, py, h, sp, wd, ht, rg, at, type, attF, st_phase, im_wd, im_ht, stand, im_att);
	}

}

package monster;

import Skill.Banana;
import Skill.EBall;
import javafx.scene.image.Image;

public class Monkey extends Monster {
	private static Image stand = new Image("file:images/monster/Flyingeye/Flight.png");
	private static Image im_att = new Image("file:images/monster/Flyingeye/Attack.png");
	private static int h = 40;
	private static int sp = 5;
	private static int wd = 50, ht = 50;
	private static int rg =  400;
	private static int at = 20;
	private static int attF = 200;
	private static int type = 1;
	private static int st_phase = 8;
	private static int im_wd = 50;
	private static int im_ht  = 50;
	
	
	public Monkey(int px, int py) {
		super(px, py, h, sp, wd, ht, rg, at, type, attF, st_phase, im_wd, im_ht, stand, im_att);
	}
	

	
	@Override
	public EBall shootBall(double x2, double y2) {
		return new Banana(10, sp, getX(0.5), getY(0.5), x2, y2, super.getBP());
	}


}

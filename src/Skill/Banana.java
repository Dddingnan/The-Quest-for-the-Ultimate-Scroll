package Skill;

import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class Banana extends EBall {
	private static Image im = new Image("file:images/monster/Flyingeye/projectile_sprite.png");
	private static final int wd = 50;
	private static final int ht = 50;
	
	public Banana(int d, int sp, double sX, double sY, double fX, double fY, BorderPane bp) {
		super(d, sp, sX, sY, fX, fY, im, wd,  ht);
		super.setImView(bp);
	}
	
	

}

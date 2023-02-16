package monster;

import javafx.scene.image.Image;

public class Monkey extends Monster {
	private Image im = new Image("file:images/monster/fire1.png");
	
	public Monkey(int px, int py, int h, int sp) {
		super(px, py, h, sp, 50, 50, 400, 20, 1, 100);
	}
	
	public Image getImage() {
		return im;
	}


}

package weapon;

import javafx.scene.image.Image;

public class Sword extends MeleeWeapon {
	private Image im = new Image("file:images/weapon/swords.png");
	private static final int xs = 2 + 31 * 3;
	private static final int ys = 0 + 31 * 2;
	private static final int w = 31;
	private static final int h = 31;
	private static final int l = 80; // length of weapon

	public Sword() {
		super(20, 150, xs, ys, w, h, l, 50);
	}
	
	public Image getImage() {
		return im;
	}

}

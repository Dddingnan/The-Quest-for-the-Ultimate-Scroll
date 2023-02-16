package character;

import javafx.scene.image.Image;

public class Warrior extends Character{
	Image im = new Image("file:images/character/warrior/char_p_castle01.png");
	
	public Warrior(int px, int py, int h, int sp) {
		super(px, py, h, sp);
	}
	
	public Image getImage() {
		return im;
	}

}

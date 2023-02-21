package character;
import javafx.scene.image.Image;
import weapon.MeleeWeapon;

public abstract class Character {
	private final int height = 50;  // height of character
	private final int width = 50; // width of character
	private double px; // position of horizontal-axis
	private double py; // position of vertical-axis
	private int h; // health
	private int sp; // speed
	private MeleeWeapon weapon;
	
	public Character(int px, int py, int h, int sp) {
		this.px = px;
		this.py = py;
		this.h = h;

		this.sp = sp;
	}
	
	
	public abstract Image getImage();

	public double getX(double d) {
		return px + width * d;
	}
	
	public double getY(double d) {
		return py + height * d;
	}
	
	public void changeX(double x) {
		px += x * sp;
	}
	
	public void changeY(double x) {
		py += x * sp;
	}
	
	public void setX(int n) {
		px = n;
	}
	
	public void setY(int n) {
		py = n;
	}
	
	public void take(MeleeWeapon w) {
		weapon = w;
	}
	
	public int getRange() {
		return weapon.getRange();
	}
	
	public MeleeWeapon getWeapon() {
		return weapon;
	}

	public int getHeight() {
		 return height;
	}
	
	public int getWidth() {
		 return width;
	}
	
	public int getHealth() {
		return h;
	}
	
	public  void getHur(int d) {
		h -= d;
	}
}

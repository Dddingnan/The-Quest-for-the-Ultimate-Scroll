package weapon;

import javafx.scene.image.Image;

public abstract class MeleeWeapon {
	private int damage;
	private int range;  // attack range
	private int length; // size of weapon
	private int xs; // from (x-axis)
	private int ys; // from (y-axis)
	private int w; // width
	private int h; // deep
	private int atFeq; // attack frequency
	private int atc;  // current attack progress
	private boolean isReady;  // is ready for next attack
	
	public MeleeWeapon(int d, int r, int xs, int ys, int w, int h, int l, int atFeq) {
		damage = d;
		range = r;
		this.xs = xs;
		this.ys = ys;
		this.w = w;
		this.h = h;
		length = l;
		this.atFeq = atFeq;
		isReady = true;
		
	}
		
	public int getXs() {
		return xs;
	}

	public int getYs() {
		return ys;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getRange() {
		return range;
	}
	
	public abstract Image getImage();
	
	public int getLength() {
		return length;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void attack() {
		isReady = false;
		atc = 0;
	}
	
	public void coolDown() {
		if (!isReady && ++atc == atFeq) isReady = true;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public double getProgress() {
		return  atc;
	}
	
	
}

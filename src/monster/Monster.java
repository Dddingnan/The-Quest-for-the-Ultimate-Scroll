package monster;

import javafx.scene.image.Image;

public abstract class Monster {
	private double px, py; // position at x-axis & y-axis
	private int h; // health
	private int sp; // speed
	private int wd, ht; // width & height
	private int range; // attack range
	private int attack;  // damage
	private int runTime; // time for a run
	private double xD, yD;  // coefficient of speed in x-axis
	private int type; // type of monster(shooter 1 / close fighter 0)
	private int attFeq; // attack frequency
	private int attCur; // current attack progress
	
	public Monster(int px, int py, int h, int sp, int wd, int ht, int range, int att, int t, int af) {
		this.px = px;
		this.py = py;
		this.h = h;
		this.sp = sp;
		this.wd = wd;
		this.ht = ht;
		this.range = range;
		this.attack = att;
		type = t;
		attFeq = af;
		attCur = 0;
		
	}
	
	public double getX(double d) {
		return px + wd * d;
	}
	
	public double getY(double d) {
		return py + ht * d;
	}
	
	public void changeX(double x) {
		px += x * sp;
	}
	
	public void changeY(double x) {
		py += x  * sp;
	}
	
	public abstract Image getImage();

	// return false if dead
	public boolean getHurt(int d) {
		h -= d;
		return h > 0;
	}
	
	public int getWidth() {
		return wd;
	}
	
	public int getHeight() {
		return ht;
	}
	
	public int getHealth() {
		return h;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getRange() {
		return range;
	}
	
	// return true if current moving
	public boolean ifContinue() {
		return runTime != 0;
	}
	
	public void newRun(double x, double y, int t) {
		runTime = t;
		xD = x;
		yD = y;
	}
	
	public double getXD() {
		return xD;
	}
	
	public double getYD() {
		return yD;
	}
	
	public void updateRun() {
		runTime -= runTime == 0 ? 0 : 1;
	}
	
	public int getType() {
		return type;
	}
	public void updateAttack() {
		attCur -= attCur == 0 ? 0 : 1;
	}
	
	public boolean readyToStartAttack() {
		return attCur == 0;
	}
	
	public void attackStart() {
		attCur = attFeq;
	}
	
	public boolean readyToAttack() {
		return attCur == attFeq / 2;
	}
	
	public EBall shootBall(double x2, double y2) {
		return new Banana(attack, sp, getX(0.5), getY(0.5), x2, y2);
	}
}

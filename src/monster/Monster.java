package monster;

import Skill.Banana;
import Skill.EBall;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;



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
	private int stand_phase;
	private int stand_progress;
	private int im_wd, im_ht;
	private Image stand;
	private Image im_att;
	private ImageView iv;
	private BorderPane bp;
	private int attacking = 24;
	
	public Monster(int px, int py, int h, int sp, int wd, int ht, int range, int att, int t, int af, int stand_phase, int im_wd, int im_ht, Image st, Image im_att) {
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
		this.stand_phase = stand_phase;
		stand_progress = 0;
		this.im_ht = im_ht;
		this.im_wd = im_wd;
		stand = st;
		this.im_att = im_att;
	}
	
	public void setImView(BorderPane b) {
		bp = b;
		iv =  new ImageView(stand);
		iv.setFitHeight(ht);
		iv.setFitWidth(wd);
		iv.setViewport(new Rectangle2D(50,50,50,50));
		bp.getChildren().add(iv);
	}
	
	public void remove() {
		bp.getChildren().remove(iv);
	}
	public int getIm_wd() {
		return im_wd;
	}

	public int getIm_ht() {
		return im_ht;
	}

	public double getX(double d) {
		return px + wd * d;
	}
	
	public double getY(double d) {
		return py + ht * d;
	}
	
	public void changeX(double x) {
		px += x * sp;
		iv.setX(px);
	}
	
	public void changeY(double x) {
		py += x  * sp;
		iv.setY(py);
	}
	

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
	
	public void attackStart() {
		attCur = attFeq;
		attacking = 0;
		
	}
	
	public boolean readyToAttack() {
		return attCur == 0;
	}
	
	public EBall shootBall(double x2, double y2) {
		return new Banana(attack, sp, getX(0.5), getY(0.5), x2, y2, bp);
	}
	
	public void updateStProgress(double x) {
		attacking++;
		int coe = 6;
		if (attacking < 24) {
			coe = 3;
			iv.setImage(im_att);
		} else {
			iv.setImage(stand);
		}
		if (x > px) {
			iv.setScaleX(1);
		} else {
			iv.setScaleX(-1);
		}
		stand_progress = stand_progress + 1 >= stand_phase * coe  ? 0 : stand_progress + 1;
		iv.setViewport(new Rectangle2D(50 + 150 * (stand_progress / coe) ,50,50,50));
	}
	
	public BorderPane getBP() {
		return bp;
	}
}

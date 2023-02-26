package Skill;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;



public abstract class EBall {
	private int damage;
	private double sX,sY;
	private double incX, incY;
	public double deg;
	private int width, height;
	private Image im_att;
	private ImageView iv;
	private BorderPane bp;
	private int hit;
	private boolean hit_start;
	

	
	public EBall(int d, int sp, double sX, double sY, double fX, double fY, Image im, int wd, int ht) {
		damage = d;
		this.sX = sX;
		this.sY = sY;
		deg = Math.atan2(fY - sY, fX  - sX); 
		incX = Math.cos(deg) * sp;
		incY = Math.sin(deg) * sp;
		width = wd;
		height = ht;
		im_att = im;
		hit_start = false;
	}
	
	public void setImView(BorderPane b) {
		bp = b;
		iv = new ImageView(im_att);
		iv.setFitHeight(height);
		iv.setFitWidth(width);
		iv.setViewport(new Rectangle2D(0,0,48,48));
		bp.getChildren().add(iv);
	}
	
	public double getX(double d) {
		return sX + width * d ;
	}
	
	public double getY(double d) {
		return sY + height * d;
	}
	
	public void update() {
		sX += incX;
		sY += incY;
		iv.setX(sX);
		iv.setY(sY);
		iv.setRotate((iv.getRotate() + 30) % 360);
	}
	
	
	public int  getWidth() {
		return width;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void remove()  {
		bp.getChildren().remove(iv);
	}

	public void hitStart()  {
		hit = 0;
		hit_start = true;
		sX += incX * 5;
		sY += incY * 5;

	}
	
	public boolean isHit() {
		return hit_start;
	}
	
	public boolean hitEffect(double x, double y)  {
		iv.setViewport(new Rectangle2D(148 + 48 * (hit++ / 3), 0, 48, 48));

		return hit == 12;
	}
	
}

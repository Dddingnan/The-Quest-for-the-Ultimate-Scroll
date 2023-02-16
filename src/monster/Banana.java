package monster;

public class Banana {
	private int damage;
	private double sX,sY;
	private int sp;
	private double incX, incY;
	public double deg;
	private int width, height;
	


	
	public Banana(int d, int sp, double sX, double sY, double fX, double fY) {
		damage = d;
		this.sX = sX;
		this.sY = sY;
		deg = Math.atan2(fY - sY, fX  - sX); 
		incX = Math.cos(deg) * sp;
		incY = Math.sin(deg) * sp;
		width = 5;
		height = 5;
	}
	
	public double getSX(double d) {
		return sX + width * d ;
	}
	
	public double getSY(double d) {
		return sY + height * d;
	}
	
	public void update() {
		sX += incX;
		sY += incY;
	}
	
	public int  getWidth() {
		return width;
	}
	
	public int getDamage() {
		return damage;
	}

	
}

package stage;

import java.util.ArrayList;
import java.util.List;

import monster.Monkey;
import monster.Monster;

public class Room {
	private int No, x, y; // coordinate
	private List<Monster> ml = new ArrayList<>();
	
	
	public Room(int c, int x, int y) {
		No = c;
		this.x = x;
		this.y = y;
		ml.add(new Monkey(400, 400));
		ml.add(new Monkey(450, 400));
	}

	public int getNo() {
		return No;
	}

	public void setNo(int no) {
		No = no;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public  List<Monster> getMonster() {
		return ml;
	}
	
	public boolean isClean() {
		return ml.isEmpty();
	}
	
	

}

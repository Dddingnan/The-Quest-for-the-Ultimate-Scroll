package stage;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import monster.Goblin;
import monster.Monkey;
import monster.Monster;

public class Room {
	private int No, x, y; // coordinate
	private List<Monster> ml = new ArrayList<>();
	private Canvas canvas;
	
	
	public Room(int c, int x, int y, Image im) {
		No = c;
		this.x = x;
		this.y = y;
		ml.add(new Monkey(400, 400));
		ml.add(new Goblin(450, 400));
		canvas = new Canvas();
		canvas.setWidth(1280);
		canvas.setHeight(720);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(131, 164, 72));
		gc.fillRect(0, 0, 1280, 720);
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 15; j++) {
				if (Math.random() < 0.2) gc.drawImage(im, 576, 128, 32, 32, i * 50, j * 50, 50, 50);
				else if (Math.random() < 0.05) gc.drawImage(im, 641, 106, 32, 25, i * 50, j * 50, 50, 50);
			}
		}
		
	}

	public Canvas getCanvas() {
		return canvas;
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

package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import stage.GameStage;
import stage.StageOne;
import weapon.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import monster.Monster;
import other.Other;
import java.util.List;
import character.Character;


public class GameView extends Canvas {
	private final double width = 1280;
	private final double height = 720;
	private final int up_x = 600, up_y = 0; // position of upper gate
	private final int right_x = 1225, right_y = 300;
	private final int down_x = 600, down_y = 640;
	private final int left_x = 0 , left_y = 300;
	private double mouseX, mouseY;  // mouse position
	private double deg;
	private GameStage st;
	private GraphicsContext gc; // for others
	private Character role;
	private List<Monster> ml;
	private Weapon wp;
	private boolean up, down, left, right; // keyboard code
	private BorderPane bp;
	private int direction = 0;
	private int phase = 0;
	private UI ui;

	
	public GameView(BorderPane b, UI u) {
		bp = b;
		// initialization
		System.out.println(bp.getChildren().size());
		this.setWidth(width);
		this.setHeight(height);
		st = new StageOne(10);
		role = st.getCharacter();
		ui = u;
		ui.setRole(role);
		ml = st.getMonsters();
		wp = role.getWeapon();		
		bp.getChildren().add(st.getRoom().getCanvas());
		wp.setImView(ml, role, bp);
		System.out.println(bp.getChildren().size());
		for (Monster m : ml)  {
			m.setImView(bp, role);
		}
		gc = this.getGraphicsContext2D();
		this.setFocusTraversable(true);
		setKey();
	    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
	    	drawAll();
	    }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();


		
	}
	
	private void setKey() {
		this.setOnKeyPressed(e -> {
			switch(e.getCode()) {
			case A:
				left = true;
				break;
			case D:
				right = true;
				break;
			case W:
				up = true;
				break;
			case S:
				down = true;
				break;
			default:
			}
		});
		this.setOnKeyReleased(e -> {
			switch(e.getCode()) {
			case A:
				left = false;
				break;
			case D:
				right = false;
				break;
			case W:
				up = false;
				break;
			case S:
				down = false;
				break;
			default:
			}
		});
		bp.setOnMouseMoved(e -> {
			mouseX = e.getX();
			mouseY = e.getY();
		});
		bp.setOnMouseClicked(e -> {
			// when current weapon type is MeleeWeapon
			wp.tryAttack(deg, mouseX, mouseY);
		});
	}
	
	private void drawAll() {
		deg =  Math.atan2(mouseY - role.getY(0.5), mouseX - role.getX(0.5)) + Math.PI;
		double d = (deg + Math.PI/4)%(Math.PI * 2)/(Math.PI/2);
		switch((int)d) {
		case 0:
			direction = 1;
			break;
		case 1:
			direction = 3;
			break;
		case 2:
			direction = 2;
			break;
		case 3:
			direction = 0;
			break;
		}
		role.change(left || right || up || down ? 1 : 0);
		gc.clearRect(0, 0, width, height);
		drawGate();
		updateCharacter();
		updateMonster();
		drawCharacter();
		ui.drawHealth();
		ui.drawMonsterHealth(ml);
	}
	
	private void drawCharacter() {
		gc.setFill(Color.GOLD);
		phase = (phase + 1) %  (role.getPhase() * 6);
		gc.drawImage(role.getImage(), phase / 6  * 64, direction * 128, 64, 128, role.getX(0), role.getY(0), role.getWidth(), role.getHeight());
		wp.update(mouseX, mouseY, deg);
	}
	
	// update position of character based on keyboard control
	private void updateCharacter() {
		if (left && up) {
			role.changeX(role.getX(1) > role.getWidth() ? -0.7 : 0);
			role.changeY(role.getY(1.25) > role.getHeight() ? -0.7 : 0);
		} else if (left && down) {
			role.changeX(role.getX(1) > role.getWidth() ? -0.7 : 0);
			role.changeY(role.getY(0) < height - role.getHeight() * 1.5 ? 0.7 : 0);
		} else if (right && up) {
			role.changeX(role.getX(0) < width  - role.getWidth() ? 0.7 : 0);
			role.changeY(role.getY(1.25) > role.getHeight() ? -0.7 : 0);		
		} else if (right && down) {
			role.changeX(role.getX(0) < width  - role.getWidth() ? 0.7 : 0);
			role.changeY(role.getY(0) < height - role.getHeight() * 1.5 ? 0.7 : 0);
		} else if (left) {
			role.changeX(role.getX(1) > role.getWidth() ? -1 : 0);
		} else if (up) {
			role.changeY(role.getY(1.25) > role.getHeight() ? -1 : 0);
		} else if (right) {
			role.changeX(role.getX(0) < width  - role.getWidth() ? 1 : 0);
		} else if (down) {
			role.changeY(role.getY(0) < height - role.getHeight() * 1.25 ? 1 : 0);
		}
		if (st.roomClean()) {
			if (st.room(0) != 0  && Other.dist(role.getX(0.5), role.getY(0.5), up_x + 25, up_y + 25) < 30) {
				role.setX(600);
				role.setY(580);
				st.nextRoom(0);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			} else if (st.room(1) != 0  && Other.dist(role.getX(0.5), role.getY(0.5), 1250, 325) < 30) {
				role.setX(100);
				role.setY(300);
				st.nextRoom(1);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			} else if (st.room(2) != 0  && Other.dist(role.getX(0.5), role.getY(0.5), 625, 665) < 30) {
				role.setX(600);
				role.setY(100);
				st.nextRoom(2);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			} else if (st.room(3) != 0  && Other.dist(role.getX(0.5), role.getY(0.5), 25, 325) < 30 ) {
				role.setX(1175);
				role.setY(300);
				st.nextRoom(3);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			}
			ml = st.getMonsters();
			wp.updateMonster(ml);
			for (Monster m : ml) {
				m.setImView(bp, role);
			}
		}
		
	}

	private void updateMonster() {
		for (int i = 0; i < ml.size(); i++) {
			Monster m = ml.get(i);
			m.animation();
			if (m.isDead()) {
				m.remove();
			}
			if (m.allDone()) {
				ml.remove(i--);
			}
		}
	}
	
	

	
	private void drawGate() {
		if (st.roomClean()) gc.setFill(Color.BLUE);
		else gc.setFill(Color.GREY);
		if (st.room(0) != 0) gc.fillOval(up_x, up_y, 50, 50);
		if (st.room(1) != 0) gc.fillOval(right_x, right_y, 50, 50);
		if (st.room(2) != 0) gc.fillOval(down_x, down_y, 50, 50);
		if (st.room(3) != 0) gc.fillOval(left_x, left_y, 50, 50);

	}

	
}

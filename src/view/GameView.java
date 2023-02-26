package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import stage.GameStage;
import stage.StageOne;
import weapon.MeleeWeapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import monster.Monster;
import java.util.ArrayList;
import java.util.List;

import Skill.EBall;
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
	private MeleeWeapon wp;
	private ImageView iv;
	private boolean up, down, left, right; // keyboard code
	private List<EBall> ball;
	private BorderPane bp;
	
	private int direction = 0;
	private int phase = 0;
	
	public GameView(BorderPane b) {
		bp = b;
		// initialization
		this.setWidth(width);
		this.setHeight(height);
		st = new StageOne(10);
		role = st.getCharacter();
		ml = st.getMonsters();
		wp = role.getWeapon();
		iv = new ImageView(wp.getImage());
		iv.setFitHeight(wp.getLength());
		iv.setFitWidth(wp.getLength());
		iv.setViewport(new Rectangle2D(wp.getXs(),wp.getYs(),wp.getW(), wp.getH()));
		bp.getChildren().add(st.getRoom().getCanvas());
		bp.getChildren().add(iv);
		for (Monster m : ml)  {
			m.setImView(bp);
		}
		ball = new ArrayList<>();
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
		this.setOnMouseMoved(e -> {
			mouseX = e.getX();
			mouseY = e.getY();
		});
		this.setOnMouseClicked(e -> {
			// when current weapon type is MeleeWeapon
			if (wp instanceof MeleeWeapon) {
				if (!wp.isReady()) return;
				for (int i = 0; i < ml.size(); i++) {
					Monster m = ml.get(i);
					double diff_x = role.getX(0.5) - m.getX(0.5);
					double diff_y = role.getY(0.5) - m.getY(0.5);
					double deg1 =  Math.atan2(-diff_y, -diff_x) + Math.PI;
					if (Math.abs(((6.3 - deg1) % 6.2 - (6.3 - deg) % 6.2)) < 1 && Math.sqrt(diff_x * diff_x + diff_y * diff_y) < wp.getRange()) {
						double x_m = Math.abs(diff_x) / (Math.abs(diff_x) + Math.abs(diff_y));
						double y_m = 1 - x_m;
						m.changeX(diff_x > 0 ? -10 * x_m : 10 * x_m);
						m.changeY(diff_y > 0 ? -10 * y_m : 10 * y_m);
						if (m.getHurt(wp.getDamage()) == false) {
							m.remove();
							ml.remove(i--);
						}
					}
				}
			}
			wp.attack();
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
		updateCharacter();
		updateMonster();
		update_drawBalls();
		drawCharacter();
		drawGate();
	}
	
	private void drawCharacter() {
		gc.setFill(Color.GOLD);
		phase = (phase + 1) %  (role.getPhase() * 6);
		gc.drawImage(role.getImage(), phase / 6  * 64, direction * 128, 64, 128, role.getX(0), role.getY(0), role.getWidth(), role.getHeight());
		if (mouseX > role.getX(0.5)) {
			iv.setScaleX(-1);
			deg += 0.7 * (wp.getProgress() <= 5 ? wp.getProgress() / 5 : 0);
			iv.setRotate(Math.toDegrees(deg) - 135);
		} else {
			deg -= 0.7 * (wp.getProgress() <= 5 ? wp.getProgress() / 5 : 0);
			iv.setScaleX(1);
			iv.setRotate(Math.toDegrees(deg) -  45);

		}
		iv.setX(role.getX(0.5) - 25 - Math.cos(deg) * 50); 
		iv.setY(role.getY(0.5) - 25 - Math.sin(deg) * 50);
		wp.coolDown();

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
			if (st.room(0) != 0  && distOf2P(role.getX(0.5), role.getY(0.5), up_x + 25, up_y + 25) < 20) {
				role.setX(600);
				role.setY(600);
				st.nextRoom(0);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			} else if (st.room(1) != 0  && distOf2P(role.getX(0.5), role.getY(0.5), 1250, 325) < 20) {
				role.setX(100);
				role.setY(300);
				st.nextRoom(1);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			} else if (st.room(2) != 0  && distOf2P(role.getX(0.5), role.getY(0.5), 625, 665) < 20) {
				role.setX(600);
				role.setY(100);
				st.nextRoom(2);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			} else if (st.room(3) != 0  && distOf2P(role.getX(0.5), role.getY(0.5), 25, 325) < 20 ) {
				role.setX(1175);
				role.setY(300);
				st.nextRoom(3);
				bp.getChildren().set(0, st.getRoom().getCanvas());
			}
			ml = st.getMonsters();
			for (Monster m : ml) {
				m.setImView(bp);
			}
		}
		
	}

	private void updateMonster() {
		double rx = role.getX(0.5);
		double ry = role.getY(0.5);
		for (Monster m : ml) {
			double dis = distOf2P(rx, ry, m.getX(0.5), m.getY(0.5));
			// if distance is less than attack range
			if (m.getType() == 1) {
				if (dis < m.getRange()) {
					if (m.readyToAttack()) {
						m.attackStart();
						ball.add(m.shootBall(rx, ry));
					}
					if (!m.ifContinue())
						m.newRun(Math.random() > 0.5 ? (m.getX(0.5) < width ? 0.4 : 0) : (m.getX(0.5) > m.getWidth() ? -0.4 : 0), Math.random() > 0.5 ? (m.getY(0.5) < height ? 0.4 : 0) : (m.getY(0.5) > m.getHeight() ? -0.4 : 0), 50);
					
				}


			// if outside of attack range
				else {
					if (!m.ifContinue()) {
						double ran = Math.random();
						if (ran > 0.1) {
							m.newRun(rx > m.getX(0.5) ? 0.4 : -0.4, ry > m.getY(0.5) ? 0.4 : -0.4, 50);
						} else {
							m.newRun(rx > m.getX(0.5) ? -0.4 : 0.4, ry > m.getY(0.5) ? -0.4 : 0.4, 50);
					}
				}
				
				}
			// if it is close-attack monster
			} else if (m.getType() == 0)  {
				if (dis < m.getRange()) {
					if (m.readyToAttack()) {
						m.attackStart();
						role.getHur(m.getAttack());
						System.out.println(role.getHealth());
					}
				} else {
					if (!m.ifContinue()) {
						m.newRun(Math.abs(rx - m.getX(0.5)) < 10 ? 0 : (rx > m.getX(0.5) ? 0.4 : -0.4), Math.abs(ry - m.getY(0.5)) < 10 ? 0 : (ry > m.getY(0.5) ? 0.4 : -0.4), 1);
					}
				}
			}
			m.changeX(m.getXD() > 0 ? (m.getX(0.5) < width ? m.getXD() : 0) : (m.getX(0.5) > m.getWidth() ? m.getXD() : 0));
			m.changeY(m.getYD() > 0 ? (m.getY(0.5) < height - m.getHeight() ? m.getYD() : 0) : (m.getY(0.5) > m.getWidth() ? m.getYD() : 0));
			m.updateAttack();
			m.updateRun();
			m.updateStProgress(role.getX(0.5));
		}
	}
	
	private void update_drawBalls() {
		if (ball.size() == 0) return;
		for (int i = 0; i < ball.size(); i++) {
			EBall b = ball.get(i);
			if (b.isHit()) {
				if (b.hitEffect(role.getX(0), role.getY(0))) {
					ball.remove(i);
					b.remove();
				} else continue;
			} else if (distOf2P(b.getX(0.5), b.getY(0.5), role.getX(0.5), role.getY(0.5)) <= role.getWidth() * 0.5 + b.getWidth() * 0.5) {
				b.hitStart();
				role.getHur(b.getDamage());
				System.out.println(role.getHealth());
			} else if (b.getX(0.5) < 0 || b.getX(0.5) > width || b.getY(0.5) < 0 || b.getY(0.5) > height) {
				ball.remove(i);
				b.remove();
			}
			b.update();

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
	
	private double distOf2P(double x1, double y1, double x2, double y2) {
		double xd = x1 - x2;
		double yd = y1 - y2;
		return Math.sqrt(xd * xd + yd * yd);
	}
	
}

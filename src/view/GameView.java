package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import stage.GameStage;
import stage.Stage1;
import weapon.MeleeWeapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import monster.Banana;
import monster.Monster;

import java.util.ArrayList;
import java.util.List;

import character.Character;


public class GameView extends Canvas {

	private double mouseX;
	private double mouseY;
	private double deg;
	private GameStage st;
	private GraphicsContext gc;
	private GraphicsContext gc2;
	private Stage stage;
	private Character role;
	private List<Monster> ml;
	private MeleeWeapon wp;
	private ImageView iv;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	private List<Banana> ball;
	
	public GameView(BorderPane bp, Stage stage) {
		this.stage = stage;
		this.setWidth(stage.getWidth());
		this.setHeight(stage.getHeight());
		st = new Stage1();
		ml = st.getMonsters();
		role = st.getCharacter();
		ball = new ArrayList<>();
		wp = role.getWeapon();
		iv = new ImageView(wp.getImage());
		iv.setFitHeight(wp.getLength());
		iv.setFitWidth(wp.getLength());
		iv.setViewport(new Rectangle2D(wp.getXs(),wp.getYs(),wp.getW(), wp.getH()));
		Canvas ca2 = new Canvas();
		ca2.setWidth(stage.getWidth());
		ca2.setHeight(stage.getHeight());

		gc2 = ca2.getGraphicsContext2D();

		bp.getChildren().addAll(ca2, iv);
		
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
					double deg1 =  Math.atan2(m.getY(0.5) - role.getY(0.5), m.getX(0.5) - role.getX(0.5)) + Math.PI;
					if (Math.abs(((6.3 - deg1) % 6.2 - (6.3 - deg) % 6.2)) < 1 && Math.sqrt(diff_x * diff_x + diff_y * diff_y) < wp.getRange()) {
						double x_m = Math.abs(diff_x) / (Math.abs(diff_x) + Math.abs(diff_y));
						double y_m = 1 - x_m;
						m.changeX(diff_x > 0 ? -10 * x_m : 10 * x_m);
						m.changeY(diff_y > 0 ? -10 * y_m : 10 * y_m);
						if (m.getHurt(wp.getDamage()) == false) ml.remove(i--);
					}
				}
			}
			wp.attack();
		});
	}
	
	private void drawAll() {
		deg =  Math.atan2(mouseY - role.getY(0.5), mouseX - role.getX(0.5)) + Math.PI;
		gc.clearRect(0, 0, stage.getWidth(), stage.getHeight());
		gc2.clearRect(0, 0, stage.getWidth(), stage.getHeight());
		gc2.setFill(Color.BLACK);
		gc2.fillRect(0,0,1280,720);
		updateCharacter();
		updateMonster();
		updateBalls();
		drawMonster();
		drawCharacter();	
	}
	
	private void drawCharacter() {
		gc.setFill(Color.GOLD);
		gc.fillRect(role.getX(0), role.getY(0), role.getWidth(), role.getHeight());
		if (mouseX > role.getX(0) + 25) {
			iv.setScaleX(-1);
			deg += 0.7 * (wp.getProgress() <= 5 ? wp.getProgress() / 5 : 0);
			iv.setRotate(Math.toDegrees(deg) - 135);
		} else {
			deg -= 0.7 * (wp.getProgress() <= 5 ? wp.getProgress() / 5 : 0);
			iv.setScaleX(1);
			iv.setRotate(Math.toDegrees(deg) -  45);

		}
		iv.setX(role.getX(0) - 15 - Math.cos(deg) * 80); 
		iv.setY(role.getY(0) - 15 - Math.sin(deg) * 80);
		wp.coolDown();

	}
	
	
	private void updateCharacter() {
		if (left && up) {
			role.changeX(-0.7);
			role.changeY(-0.7);
		} else if (left && down) {
			role.changeX(-0.7);
			role.changeY(0.7);
		} else if (right && up) {
			role.changeX(0.7);
			role.changeY(-0.7);			
		} else if (right && down) {
			role.changeX(0.7);
			role.changeY(0.7);
		} else if (left) {
			role.changeX(-1);
		} else if (up) {
			role.changeY(-1);
		} else if (right) {
			role.changeX(1);
		} else if (down) {
			role.changeY(1);
		}
	}

	private void updateMonster() {
		double rx = role.getX(0.5);
		double ry = role.getY(0.5);
		for (Monster m : ml) {
			double dx = Math.abs(rx - m.getX(0.5)); 
			double dy = Math.abs(ry - m.getY(0.5));
			double dis = Math.sqrt(dx * dx + dy * dy);
			if (dis < m.getRange()) {
				if (m.getType() == 1) {
					if (m.readyToAttack()) {
						ball.add(m.shootBall(rx, ry));
					}
				}
				m.newRun(rx > m.getX(0.5) ? -0.4 : 0.4, ry > m.getY(0.5) ? -0.4 : 0.4, 5);
				m.changeX(m.getXD());
				m.changeY(m.getYD());
			} else {
				if (!m.ifContinue()) {
					double ran = Math.random();
					if (ran > 0.1) {
						m.newRun(rx > m.getX(0.5) ? 0.4 : -0.4, ry > m.getY(0.5) ? 0.4 : -0.4, 10);
					} else {
						m.newRun(rx > m.getX(0.5) ? -0.4 : 0.4, ry > m.getY(0.5) ? -0.4 : 0.4, 5);
					}
				}
				m.changeX(m.getXD());
				m.changeY(m.getYD());
			}	
			m.updateAttack();
			m.updateRun();
		}
	}
	
	private void updateBalls() {
		if (ball.size() == 0) return;
		for (int i = 0; i < ball.size(); i++) {
			Banana b = ball.get(i);
			gc.fillOval(b.getSX(0.5) , b.getSY(0.5), 10, 10);
			if (distOf2P(b.getSX(0.5), b.getSY(0.5), role.getX(0.5), role.getY(0.5)) <= role.getWidth() * 0.5 + b.getWidth() * 0.5) {
				ball.remove(i);
				role.getHur(b.getDamage());
				System.out.println(role.getHealth());
			}
			b.update();
		}
	}
	
	
	private void drawMonster() {
		for (Monster m : ml) {
			gc.drawImage(m.getImage(), m.getX(0), m.getY(0), m.getWidth(), m.getHeight());
		}
	}
	
	private double distOf2P(double x1, double y1, double x2, double y2) {
		double xd = x1 - x2;
		double yd = y1 - y2;
		return Math.sqrt(xd * xd + yd * yd);
	}
}

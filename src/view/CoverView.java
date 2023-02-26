package view;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * The cover of this game.
 *
 * @author Jingwei Zhang
 */
public class CoverView extends Canvas {
  private int option;
  private GraphicsContext gc = this.getGraphicsContext2D();
  private Image title = new Image("file:images/title.png");
  private ArrayList<Image> fires = new ArrayList<>();
  private BorderPane pane;
  private int x;
  private int y;
  private int tic = 0;
  private boolean insOpen;
  private Timeline timeline = new Timeline();
  private int page = 1;

  public CoverView(BorderPane b) {
	  this.setWidth(1280);
	  this.setHeight(720);
	  gc = this.getGraphicsContext2D();
	  fires.add(new Image("file:images/fire1.png"));
	  fires.add(new Image("file:images/fire2.png"));
	  fires.add(new Image("file:images/fire5.png"));
	  fires.add(new Image("file:images/fire3.png"));
	  fires.add(new Image("file:images/fire4.png"));
	  pane = b;
	  MouseHandler mh = new MouseHandler();
	  this.setOnMouseClicked(mh);
	  this.setOnMouseMoved(new DragHandler());
	  timeline = new Timeline(new KeyFrame(Duration.millis(50), new AnimateStarter()));
	  timeline.setCycleCount(Animation.INDEFINITE);
	  timeline.play();
  }

  /**
   * Sets the options.
   */
  public void setOption() {
	  timeline.stop();
	  switch (option) {
      	case 1:
      		pane.setCenter(new GameView(pane));
      		break;
      	case 2:
      		break;
      	case 3:
      	default:
	  }
  }


  /**
   * The {@link EventHandler<ActionEvent>} of the start buttons.
   */
  private class AnimateStarter implements EventHandler<ActionEvent> {
    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(ActionEvent event) {
      tic++;
      gc.clearRect(0, 0, 1280, 720);
      gc.drawImage(title, 168, 0, 1050, 400, 200, 80, 700, 230);
      if (x > 545 && x < 680 && y > 434 && y < 452) {
        gc.drawImage(fires.get(tic / 3 % 5), 19, 150, 548, 280, 530, 428, 168, 30);
      } else if (x > 550 && x < 670 && y > 584 && y < 602) {
        gc.drawImage(fires.get(tic / 3 % 5), 19, 150, 548, 280, 530, 578, 150, 30);
      } else if (x > 535 && x < 670 && y > 485 && y < 501) {
        gc.drawImage(fires.get(tic / 3 % 5), 19, 150, 548, 280, 520, 478, 168, 30);
      } else if (x > 545 && x < 710 && y > 530 && y < 551) {
        gc.drawImage(fires.get(tic / 3 % 5), 19, 150, 548, 280, 510, 530, 200, 30);
      }
      Font fontSmall = Font.font("Comic Sans MS", FontWeight.BOLD, 15);
      gc.setFill(Color.WHITE);
      gc.setFont(fontSmall);
      gc.fillText("Video Copyright: Ori and the Blind Forest, Moon Studios.\nUsed for this project only.", 5, 720);
      Font fontLarge = Font.font("Comic Sans MS", FontWeight.BOLD, 20);
      gc.setFont(fontLarge);
      gc.setFill(Color.WHITE);
      gc.fillText("START GAME", 545, 450);
      gc.fillText("LOAD GAME", 550, 500);
      gc.fillText("INSTRUCTIONS", 535, 550);
      gc.fillText("EXIT GAME", 545, 600);
      if (tic == 20) tic = 0;


    }
  }

  /**
   * The {@link EventHandler<MouseEvent>} for the user's mouse.
   */
  private class MouseHandler implements EventHandler<MouseEvent> {
    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param e the event which occurred
     */
    @Override
    public void handle(MouseEvent e) {
      double x = e.getX();
      double y = e.getY();
      if (x > 540 && x < 605 && y > 434 && y < 452) {
        option = 1;
        insOpen = false;
      } else if (x > 540 && x < 594 && y > 584 && y < 602) {
        Platform.exit();
        insOpen = false;
      } else if (x > 540 && x < 599 && y > 485 && y < 501) {

      } else if (insOpen && page == 1 && (x > 875 && x < 933 && y > 617 && y < 633)) {
        page = 2;
        insOpen = true;
      } else if (x > 540 && x < 621 && y > 530 && y < 551) {
        insOpen = true;
        page = 1;
      } else {
        insOpen = false;
      }
      if (option != 0) {
        setOption();
      }
    }
  }

  /**
   * The {@link EventHandler<MouseEvent>} for user when he drags something.
   */
  private class DragHandler implements EventHandler<MouseEvent> {
    /**
     * Invoked when a specific event of the type for which this handler is registered happens.
     *
     * @param event the event which occurred
     */
    @Override
    public void handle(MouseEvent event) {
      x = (int) event.getX();
      y = (int) event.getY();
    }
  }
}

package controller;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import view.GameView;
import javafx.scene.layout.BorderPane;


public class GUI extends Application {

	private final int width = 1280;
	private final int height = 720;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage)  {
		stage.setHeight(height);
		stage.setWidth(width);
		BorderPane pane = new BorderPane();
	    pane.setCenter(new GameView(pane, stage));
	    Scene scene = new Scene(pane, stage.getWidth(), stage.getHeight());
	    stage.setTitle("Scroll");
	    stage.setResizable(true);
	    stage.setScene(scene);
	    stage.show();
	}
	
}

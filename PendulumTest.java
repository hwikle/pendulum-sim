package pendulum;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

public class PendulumTest extends Application {

	@Override
	public void start(Stage primaryStage) {
		StackPane pane = new StackPane();
		PhysicsEnvironment environment = new PhysicsEnvironment();
		environment.setSize(500, 500);
		environment.setGravity(0, -9.8);
		Circle circle = new Circle();
		circle.setRadius(50);
		pane.getChildren().add(circle);
		
		Pendulum pendulum = new Pendulum();
		pendulum.setAnchor(250, 0);
		//pendulum.setLength(250);
		pendulum.setPosition(250, 250);
		pendulum.setMass(1);
		pendulum.setVelocity(0, 0);
		pendulum.setAcceleration(0,0);

		environment.add(pendulum);

		EventHandler<ActionEvent> nextFrame = e -> {
			environment.update();		
		
			circle.setCenterX((int)(pendulum.getXPosition()));
			circle.setCenterY((int)(pendulum.getYPosition()));

			System.out.println(pendulum.getYPosition());
		};

		Timeline animation = new Timeline(
			new KeyFrame(Duration.millis(1000), nextFrame));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

	/*	
		circle.setCenterX(pendulum.getXPosition());
		circle.setCenterY(pendulum.getYPosition());
		circle.setRadius(50);
		circle.setStroke(Color.BLACK);

*/
		//pane.getChildren().add(circle);
		Scene scene = new Scene(pane, environment.getXSize(), environment.getYSize());
		
		primaryStage.setTitle("Pendulum Demo");
		primaryStage.setScene(scene);
		primaryStage.show();

		pane.requestFocus();
	}
}

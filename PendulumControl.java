//******************************
// Assignment: Final Project (exercise 15.24)
//
// Authors: Henry Wikle & Kelly Whitchurch
//
// Completion Time: ~5 hours (cummulative)
//
// Description: This program defines a PendulumControl class which enables the
// user to control a PendulumPane animation using mouse and keyboard input.
// The PendulumControl object extends the JavaFX application object.
//
// Last Modified: 3 December 2019
//
// Version: 2
//
// Honor Code: We pledge that this program represents our own program code
// We received help from no one in designing and debugging our program.
// We did however, base our code directly on example code from the textbook
// Specifically, this program is based on the Bouncing Ball case study in
// Chapter 15.12.
//*******************************

package pendulum;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.lang.Math;

public class PendulumControl extends Application { // Inheritance
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    PendulumPane pendulumPane = new PendulumPane();
    pendulumPane.setArmLength(250);
    pendulumPane.setPivot(250, 0);
    pendulumPane.setAngle(-Math.PI / 2);
    pendulumPane.setGravity(9.8);
    pendulumPane.setup();
    pendulumPane.play();

    // Pause and resume animation
    pendulumPane.setOnMousePressed(e -> pendulumPane.pause());
    pendulumPane.setOnMouseReleased(e -> pendulumPane.play());

    // Increase and decrease animation   
    pendulumPane.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.UP) {
        pendulumPane.increaseSpeed();
      } 
      else if (e.getCode() == KeyCode.DOWN) {
        pendulumPane.decreaseSpeed();
      }
    });

    // Create a scene and place it in the stage
    Scene scene = new Scene(pendulumPane, 500, 500);
    primaryStage.setTitle("PendulumControl"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    // Must request focus after the primary stage is displayed
    pendulumPane.requestFocus();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}

//******************************
// Assignment: Final Project (exercise 15.24)
//
// Authors: Henry Wikle & Kelly Whitchurch
//
// Completion Time: ~10 hours (cumulative)
//
// Description: This program defines a PendulumPane class which uses
// a simple physics engine to simulate a swinging pendulum. The class displays
// an animation of the swinging pendulum, which may be sped up, slowed down,
// or paused using a mouse and keyboard. The PendulumPane class extends the
// JavaFX Pane class.
//
// This program demonstrates object-oriented principals in the following ways:
//
//	Abstraction : The class abstracts the concept of a swinging pendulum
//			to some object that has mass, a pivot point, an arm length,
//			an initial angle, and so on. Because the class encompasses
//			all objects that have the properties of a pendulum, this deninition
//			is more general (and thus more abstract) than a pendulum with
//			specific values for these properties.
//
//	Polymorhism : The PendulumPane class defines two contructors: a no-argument constructor
//			and an alternate constructor. The ability to call the object's constructor
//			with either of two call signatures is an example of polymorphism.
//
//	Inheritance : The PendulumPane object inherits directly from the JavaFX Pane object.
//			Not only does the PendulumPane have its own properties such as mass and angle,
//			and methods such as applyGravity, but it also inherits properties and methods
//			from the Pane class and all of its superclasses.
//
//	Encapsulation: The methods underlying the PendulumPane class rely on specific formulas
//			from math and physics, which the user may not be familiar with. Since these
//			formulas are only used internally, the user need not understand the math or
//			physics underlying the implementation and simply needs to call the update
//			method to invoke these formulas. By hiding the mathematical implementation
//			from the user, the program exercises the technique of encapsulation.
//
// Last Modified: 5 December 2019
//
// Version: 4
//
// Honor Code: We pledge that this program represents our own program code
// We received help from no one in designing and debugging our program.
// We did however, base our code directly on example code from the textbook
// Specifically, this program is based on the Bouncing Ball case study in
// Chapter 15.12.
//*******************************

package pendulum;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.lang.Math; // for trig functions

public class PendulumPane extends Pane { // Inheritance

	private double PIXELS_PER_METER = 1;
  	private double mass = 1; // mass of pendulum (kg)
	private double armLength = 0; // length of pendulum arm (in meters)
	private double pivotX = 0, pivotY = 0; // position of pivot point (in meters)  
	private double angle = 0; // initial angle of pendulum arm (radians, measured ccw from straight down (0))
	private double angularVelocity = 0; // initial angular velocity (radians/second)
	private double angularAcceleration = 0; // initial angular acceleration (radians/square second)
	private double gravity = 1; // acceleration from gravity (meters per square second)
	private double radius = 20; // radius of pendulum mass (in pixels)
  	private double dt = 0.05; // length of time steps (seconds)
  	private Circle circle = new Circle(0, 0, radius);
	private Line arm = new Line(); // line representing the pendulum arm
  	private Timeline animation;

  public PendulumPane() {
  //******************************
  // No-argument constructor for PendulumPane
  //
  // 	Note: If the no-argument constructor is used to
  //		instantiate the PendulumPane, properties
  //		must be set manually before calling the setup method
  //******************************

    circle.setFill(Color.BLACK); // Set circle color to black
    getChildren().add(circle); // Add the circle to the list of the Pane's children
    getChildren().add(arm); // Add the pendulum arm to the list of the Pane's children
  }

  public PendulumPane(double mass, double length, double pivotX, double pivotY) {
  //******************************
  // Alternate constructor for PendulumPane
  //
  //	Note: If the alternate constructor is used to instantiate
  //		the PendulumPane, properties need not be set
  //		manually before calling the setup method.
  //******************************
    	circle.setFill(Color.BLACK); // Set circle color to black
    	getChildren().add(circle); // Add the circle to the list of the Pane's children
    	getChildren().add(arm); // Add the pendulum arm to the list of the Pane's children
 
  	this.mass = mass;
	this.radius = Math.sqrt((mass / Math.PI)); // Set the radius so that the area of the circle is proportiate to its mass
	this.armLength = length;
	this.pivotX = pivotX;
	this.pivotY = pivotY;

  }

  public void setMass(double mass) {
  //******************************
  // Sets the mass of the pendulum.
  // 	mass : mass of the pendulum (kilograms)
  //	returns : void 
  //******************************

  	this.mass = mass;
	this.radius = Math.sqrt((mass / Math.PI)); // Set the radius so that the area of the circle is proportiate to its mass
  }

  public void setPivot(double x, double y) {
  //******************************
  // Sets the pivot point for the pendulum arm.
  //	x, y : x, y coordinates of pivot point (meters)
  //	returns : void
  //
  //	Note : origin is in the upper left of the window and 
  //		the positive y-axis is defined as down
  //******************************

  	pivotX = x;
	pivotY = y;
  }

  public void setArmLength(double length) {
  //******************************
  // Sets the length of the pendulum arm.
  //	length : length of the pendulum arm (meters)
  //	returns : void
  //******************************

  	this.armLength = length;
  }

  public void setAngle(double angle) {
  //******************************
  // Sets the angle of the pendulum arm
  //	angle : angle of the pendulum arm (radians)
  //	returns : void
  //
  //	Note: angle is measured counterclockwise from zero,
  //		with zero defined as straight down
  //******************************
  
  	this.angle = angle;
  }

  public void setAngularVelocity(double vel) {
  //******************************
  // Sets the angular velocity of the pendulum arm
  //	vel : angular velocity of the pendulum arm (radians/second)
  //	returns : void
  //
  //	Note: anglular velocity is measured counterclockwise from zero,
  //		with zero defined as straight down
  //******************************
  
  	this.angularVelocity = vel;
  }

  public void setAngularAcceleration(double accel) {
  //******************************
  // Sets the angular acceleartion of the pendulum arm
  //	accel : angular acceleration of the pendulum arm (radians/second^2)
  //	returns : void
  //
  //	Note: anglular velocity is measured counterclockwise from zero,
  //		with zero defined as straight down
  //******************************

  	this.angularAcceleration = accel;	
  }

  public void setGravity(double g) {
  //******************************
  // Sets the gravity in the pendulum's environment.
  //	g : acceleration due to gravity (meters/second^2)
  //	returns : void
  //******************************

  	gravity = g;
  }

  public double getMass() {
  //******************************
  // Gets the mass of the pendulum
  //	returns : double representing the mass of the pendulum (kg)
  //******************************

  	return this.mass;
}



  public double getPivotX() {
  //******************************
  // Gets the x-coordinate of the pendulum's pivot point
  //	returns : double representing x-coordinate of the pivot (m)
  //******************************

	return this.pivotX;
 }

  public double getPivotY() {
   //******************************
  // Gets the y-coordinate of the pendulum's pivot point
  //	returns : double representing y-coordinate of the pivot (m)
  //******************************

	return this.pivotY;
 }

  public double getArmLength() {
  //******************************
  // Gets the length of the pendulum arm
  //	returns : double representing arm length (m)
  //******************************

  	return this.armLength;
}

  public double getAngle() {
  //******************************
  // Gets the angle of the pendulum, measured counter-clockwise
  // from straight down (zero radians)
  //	returns : double representing the angle of the pendulum (radians)
  //******************************

  	return this.angle;
}



  public double getAngularVelocity() {
  //******************************
  // Gets the current angular velocity of the pendulum
  //	returns : double representing angular velocity (radians/second)
  //******************************

  	return this.angularVelocity;
}



  public double getAngularAcceleration() {
  //******************************
  // Gets the current angular acceleration of the pendulum
  //	returns : double representing the angular acceleration (radians/second^2)
  //******************************

  	return this.angularAcceleration;
}

  public double getGravity() {
   //******************************
  // Gets the gravity of the pendulum pane
  //	returns : double representing acceleration due to gravity (m/second^2)
  //******************************

  	return this.gravity;
}

  public void setup() {
  //******************************
  // Sets up the drawing of the pendulum arm and the animation timline
  //	returns : void
  //
  //	Algorithms: The formulas for converting between polar and
  //		rectangular coordinates are:
  //
  //		x = r*cos(theta)
  //		y = r*sin(theta)
  //
  // 	Note: PendulumPane attributes (mass, armLength, etc.) should
  //		be set BEFORE calling setup.
  //******************************
 
 	// Convert from polar to rectangular coordinates
  	double xPos = pivotX + armLength * Math.sin(angle);
	double yPos = pivotY + armLength * Math.cos(angle);
	
	// Set the position of the pendulum mass
  	circle.setCenterX(xPos);
	circle.setCenterY(yPos);

	// Draw the pendulum arm
	arm.setStartX(pivotX);
	arm.setStartY(pivotY);
	arm.setEndX(xPos);
	arm.setEndY(yPos);

	// Set up animation timeline
  	animation = new Timeline(
		new KeyFrame(Duration.millis(dt*1000), e -> update())); // duration depends on set time step
	animation.setCycleCount(Timeline.INDEFINITE);
  }

  public void play() {
  //******************************
  // Begins the animation.
  //	returns : void
  //******************************

    animation.play();
  }

  public void pause() {
  //******************************
  // Pauses the animation
  //	returns : void
  //******************************

    animation.pause();
  }

  public void increaseSpeed() {
  //******************************
  // Increases the speed of the animation by 10%
  //******************************

    animation.setRate(animation.getRate() + 0.1);
  }

  public void decreaseSpeed() {
  //******************************
  // Decreases the speed of the animation by 10%
  //******************************

    animation.setRate(
      animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
  }

  public DoubleProperty rateProperty() {
    return animation.rateProperty();
  }
  
  protected void applyGravity() {
  //******************************
  // Applies torque on the pendulum due to gravity
  // and updates the angular acceleration accordingly.
  //	returns : void
  //
  //	Algorithms: By definition, torque is:
  //
  //		Tau = F * r * sin(theta)
  //
  //		Where F is the force, r is the length of the lever arm,
  //		theta is the angle between the two.
  //
  //		Angular acceleration due to torque is given to an equation
  //		analagous to the equation governing Newton's Second Law:
  //
  //		alpha = Tau / I = Tau / (m * r)
  //******************************

  	double torque = gravity * mass * -Math.sin(angle);
	angularAcceleration = torque / (mass * armLength);
	}
	
	protected void updateAngularVelocity() {
	//******************************
	// Applies angular acceleration due to gravity,
	// then updates the angular velocity accordingly.
	//	returns : void
	//******************************

		applyGravity();
		angularVelocity += angularAcceleration * dt;
  	}

  protected void updateAngle() {
  //******************************
  // Updates the angle of the pendulum based on
  // the current angular velocity.
  //	returns : void
  //******************************

  	angle += angularVelocity * dt;

	circle.setCenterX(pivotX + armLength * Math.sin(angle));
	circle.setCenterY(pivotY + armLength * Math.cos(angle));
	arm.setEndX(pivotX + armLength * Math.sin(angle));
	arm.setEndY(pivotY + armLength * Math.cos(angle));
  }

  protected void update() {
  //******************************
  // Sequentially applies gravity, updates the angular
  // velocity, and updates the angle of the pendulum.
  //	returns : void
  //******************************
  
  	updateAngularVelocity();
	updateAngle();
  }
  
}

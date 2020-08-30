// Time: 3 hours
package pendulum;

import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PhysicsEnvironment extends Pane {

	private int xSize;
	private int ySize;
	private double xGravity;
	private double yGravity;
	private Timeline animation;

	private ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();

	public PhysicsEnvironment() {
	}

	public void setSize(int x, int y) {
		xSize = x;
		ySize = y;
	}

	public void setGravity(double x, double y) {
		xGravity = x;
		yGravity = y;
	}

	public double getXGravity() {
		return xGravity;
	}

	public double getYGravity() {
		return yGravity;
	}

	public double getXSize() {
		return xSize;
	}

	public double getYSize() {
		return ySize;
	}

	public void add(PhysicsObject object) {
		objects.add(object);
		object.setEnvironment(this);
	}

	public void applyGravity() {
		for (PhysicsObject o : objects) {
			o.applyForce(xGravity*o.getMass(), yGravity*o.getMass());
		}
	}

	public void update() {
		applyGravity();
		for (PhysicsObject o : objects) {
			o.update();
		}
	}
}

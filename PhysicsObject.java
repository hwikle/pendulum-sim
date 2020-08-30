package pendulum;

public abstract class PhysicsObject {
	
	protected double mass;
	protected double xPosition;
	protected double yPosition;
	protected double xVelocity;
	protected double yVelocity;
	protected double xAcceleration;
	protected double yAcceleration;
	protected PhysicsEnvironment environment;

	public PhysicsObject() {
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setPosition(double x, double y) {
		xPosition = x;
		yPosition = y;	
	}

	public void setVelocity(double x, double y) {
		xVelocity = x;
		yVelocity = y;
	}

	public void setAcceleration(double x, double y) {
		xAcceleration = x;
		yAcceleration = y;
	}

	public void setEnvironment(PhysicsEnvironment environment) {
		this.environment = environment;
	}

	public PhysicsEnvironment getEnvironment() {
		return environment;
	}

	public double getMass() {
		return mass;
	}

	public double getXPosition() {
		return xPosition;
	}

	public double getYPosition() {
		return yPosition;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public double getXAcceleration() {
		return xAcceleration;
	}

	public double getYAcceleration() {
		return yAcceleration;
	}

	public void applyForce(double x, double y) {
		System.out.println("Applying force of " + x +", " + y + "...");
		xAcceleration = x / mass;
		yAcceleration = y / mass;
	}

	public void update() {
		xVelocity += xAcceleration;
		yVelocity += yAcceleration;
		xPosition += xVelocity;
		yPosition += yVelocity;
		xAcceleration = 0;
		yAcceleration = 0;
	}
}


package pendulum;

public class Pendulum extends PhysicsObject {

	private double cordLength;
	private double angle;
	private double xAnchor;
	private double yAnchor;

	public Pendulum() {
	}

	public void setAnchor(double x, double y) {
		xAnchor = x;
		yAnchor = y;
	}
/*
	public double calculateTension() {
		double tension = environment.getGravity()*mass * ;

		return tension;
	}
*/
	@Override
	public void update() {
		//cord = anchor.sub(position);
		//applyForce(calculateTension());
		super.update();
	}

}

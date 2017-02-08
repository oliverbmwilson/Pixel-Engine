package Core.physics;

import javafx.geometry.Point2D;

public class Manifold {
	
	private Body b0;
	private Body b1;
	private boolean collided = false;
	private Point2D axis;
	private double overlap;
	private double b0ImpulseX;
	private double b0ImpulseY;
	private double b0CorrectionX;
	private double b0CorrectionY;
	private double b1ImpulseX;
	private double b1ImpulseY;
	private double b1CorrectionX;
	private double b1CorrectionY;
	
	public Manifold(Body b0, Body b1) {
		
		this.b0 = b0;
		this.b1 = b1;
		
	}
	
	public void positionalCorrection() {
		
		b0.correctPosition((float) b0CorrectionX, (float) b0CorrectionY);
		b1.correctPosition((float) b1CorrectionX, (float) b1CorrectionY);
		
	}

	public Point2D getAxis() {
		return axis;
	}

	public double getOverlap() {
		return overlap;
	}

	public Body getB0() {
		return b0;
	}

	public void setB0(Body b0) {
		this.b0 = b0;
	}

	public Body getB1() {
		return b1;
	}

	public void setB1(Body b1) {
		this.b1 = b1;
	}

	public void setAxis(Point2D axis) {
		this.axis = axis;
	}

	public void setOverlap(double overlap) {
		this.overlap = overlap;
	}

	public boolean isCollided() {
		return collided;
	}

	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public double getB0ImpulseX() {
		return b0ImpulseX;
	}

	public void setB0ImpulseX(double b0ImpulseX) {
		this.b0ImpulseX = b0ImpulseX;
	}

	public double getB0ImpulseY() {
		return b0ImpulseY;
	}

	public void setB0ImpulseY(double b0ImpulseY) {
		this.b0ImpulseY = b0ImpulseY;
	}

	public double getB1ImpulseX() {
		return b1ImpulseX;
	}

	public void setB1ImpulseX(double b1ImpulseX) {
		this.b1ImpulseX = b1ImpulseX;
	}

	public double getB1ImpulseY() {
		return b1ImpulseY;
	}

	public void setB1ImpulseY(double b1ImpulseY) {
		this.b1ImpulseY = b1ImpulseY;
	}

	public double getB0CorrectionX() {
		return b0CorrectionX;
	}

	public void setB0CorrectionX(double b0CorrectionX) {
		this.b0CorrectionX = b0CorrectionX;
	}

	public double getB0CorrectionY() {
		return b0CorrectionY;
	}

	public void setB0CorrectionY(double b0CorrectionY) {
		this.b0CorrectionY = b0CorrectionY;
	}

	public double getB1CorrectionX() {
		return b1CorrectionX;
	}

	public void setB1CorrectionX(double b1CorrectionX) {
		this.b1CorrectionX = b1CorrectionX;
	}

	public double getB1CorrectionY() {
		return b1CorrectionY;
	}

	public void setB1CorrectionY(double b1CorrectionY) {
		this.b1CorrectionY = b1CorrectionY;
	}

}

package Core.physics;

import Core.Renderer;
import javafx.geometry.Point2D;

public abstract class Shape {
	
	public enum Type
	{
		Circle, Polygon, Line
	}

	protected Body body;
	protected Point2D[] vertices;
	protected Point2D[] axes;
	protected float centroidX, centroidY;

	public Shape() {
		
	}

	public abstract Shape clone();

	public abstract void initialise();

	public abstract void computeMass( float density );
	
	public abstract void setOrient(float radians);
	
	public abstract void render(Renderer r);
	
	public abstract void update();
	
	public abstract Type getType();

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Point2D[] getVertices() {
		return vertices;
	}

	public Point2D[] getAxes() {
		return axes;
	}

	public float getCentroidX() {
		return centroidX;
	}
	
	public float getCentroidY() {
		return centroidY;
	}

}

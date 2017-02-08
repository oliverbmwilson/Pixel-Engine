package Core.physics;

import Core.Renderer;
import Core.FX.ShadowType;
import javafx.geometry.Point2D;

public class Line extends Shape {

private float lineLength;
	
	public Line() {
		
	}
	
	public Line(Point2D p0, Point2D p1) {
		
		vertices = new Point2D[2];
		
		vertices[0] = p0;
		vertices[1] = p1;
		
		setAxis();
		setCentroid();
		
	}
	
	public void setLine(Point2D p0, Point2D p1) {
		
		vertices = new Point2D[2];
		
		vertices[0] = p0;
		vertices[1] = p1;
		
		setAxis();
		setCentroid();
		
	}
	
	public void setAxis() {
		
		axes = new Point2D[2];
		
		Point2D axis0 = new Point2D(0, 0);
		axis0 = vertices[0].subtract(vertices[1]);
		
		double axis0X = axis0.getX();
		double axis0Y = axis0.getY();
		
		Point2D axis1 = new Point2D(axis0Y, -axis0X);
		
		axis0 = axis0.normalize();
		axis1 = axis1.normalize();
		
		axes[0] = axis0;
		axes[1] = axis1;
		
	}

	public void render(Renderer r) {
		
		//Draws cirle bounding box if debug mode is on	
		r.drawLine((int)vertices[0].getX(), (int) vertices[0].getY(), (int) vertices[1].getX(), (int) vertices[1].getY(), 0xffffff00, ShadowType.NONE);
			
	}

	public float getLength() {
		
		return lineLength;
	}
	
	@Override
	public Shape clone() {
		
		return new Line(vertices[0], vertices[1]);
	}

	@Override
	public void initialise() {
		
		computeMass( 1.0f );
	}

	@Override
	public void computeMass( float density ) {
		
		lineLength = (float) Math.sqrt(body.x * body.x + body.y * body.y);
		
		body.mass = lineLength * density;
		body.inverseMass = (body.mass != 0.0f) ? 1.0f / body.mass : 0.0f;
		//body.inertia = body.mass * radius * radius;
		//body.inverseInertia = (body.inertia != 0.0f) ? 1.0f / body.inertia : 0.0f;
		
	}

	@Override
	public Type getType()
	{
		return Type.Line;
	}

	@Override
	public void setOrient(float radians) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		

		//Each update, a collider is calculated for the object and added to the physics arraylist
		//This is the same process as the initialisation. It needs to be reclaculated as the object would have moved
		double changeX = body.x - body.previousX;
		double changeY = body.y - body.previousY;
		
		body.previousX = body.x;
		body.previousY = body.y;
			
		vertices[0] = vertices[0].add(changeX, changeY); 
		vertices[1] = vertices[1].add(changeX, changeY);
		centroidX += changeX;
		centroidY += changeY;
		
	}
	
	public void setCentroid() {
		
		centroidX = (float) (vertices[0].getX() + vertices[1].getX()) / 2;
		centroidY = (float) (vertices[0].getY() + vertices[1].getY()) / 2;
		
	}

	public float getLineLength() {
		return lineLength;
	}

	public void setLineLength(float lineLength) {
		this.lineLength = lineLength;
	}
	
}

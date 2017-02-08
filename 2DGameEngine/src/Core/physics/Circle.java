package Core.physics;

import Core.GameContainer;
import Core.Renderer;
import Core.FX.ShadowType;
import Core.components.GameObject;
import javafx.geometry.Point2D;

public class Circle extends Shape {
	
	//A component class of an object that is used in calculating collision detection
	private float radius;
	
	public Circle() {
		
	}
	
	public Circle(float radius) {
		
		this.radius = radius;
		
	}

	public void update(GameContainer gc, GameObject object) {
		
	}

	public void render(Renderer r) {
		
		//Draws cirle bounding box if debug mode is on	
		r.drawCircle((int) body.getX(),(int) body.getY(), (int) radius, 0xffffff00, ShadowType.NONE);
			
	}

	public float getRadius() {
		
		return radius;
	}

	public void setRadius(float radius) {
		
		this.radius = radius;
	}
	
	@Override
	public Shape clone() {
		
		return new Circle(radius);
	}

	@Override
	public void initialise() {
		
		computeMass( 1.0f );
	}

	@Override
	public void computeMass( float density ) {
		
		body.mass = (float) Math.PI * radius * radius * density;
		body.inverseMass = (body.mass != 0.0f) ? 1.0f / body.mass : 0.0f;
		body.inertia = body.mass * radius * radius;
		body.inverseInertia = (body.inertia != 0.0f) ? 1.0f / body.inertia : 0.0f;
		centroidX = body.x;
		centroidY = body.y;
	}

	@Override
	public Type getType()
	{
		return Type.Circle;
	}

	@Override
	public void setOrient(float radians) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		
		centroidX = body.x;
		centroidY = body.y;
		
	}

}



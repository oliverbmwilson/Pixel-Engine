package PhysicsTest;


import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Manifold;
import Core.physics.Polygon;
import javafx.geometry.Point2D;

public class Triangle extends GameObject {
	
	
	public Triangle(float x, float y, int width, int height, float dx, float dy) {
		
		setTag("triangle");
		Point2D[] trianglePoints = new Point2D[3];
		trianglePoints[0] = new Point2D(x, y);
		trianglePoints[2] = new Point2D(x + width, y + height);
		trianglePoints[1] = new Point2D(x, y + height);
		body = new Body(this, new Polygon(trianglePoints), x, y);
		body.setRestitution(1f);
		body.dx = dx;
		body.dy = dy;
		
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, int x, int y) {
		
		body.getShape().render(r);
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void collisionEvent(Manifold manifold) {
		// TODO Auto-generated method stub
		
	}

}

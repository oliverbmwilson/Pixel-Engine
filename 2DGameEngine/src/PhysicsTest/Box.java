package PhysicsTest;


import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Manifold;
import Core.physics.Polygon;
import javafx.geometry.Point2D;

public class Box extends GameObject {
	
	public Box(float x, float y, int width, int height, float dx, float dy) {
		
		setTag("box");
		Point2D[] boxPoints = new Point2D[5];
		boxPoints[1] = new Point2D(x, y - height * 2);
		boxPoints[4] = new Point2D(x + width, y - height);
		boxPoints[0] = new Point2D(x + width * 2, y + height);
		boxPoints[2] = new Point2D(x, y + height * 2);
		boxPoints[3] = new Point2D(x - width, y);
		body = new Body(this, new Polygon(boxPoints), x, y);
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

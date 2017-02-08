package PhysicsTest;

import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Line;
import Core.physics.Manifold;
import javafx.geometry.Point2D;

public class MovingLine extends GameObject{
	
	public MovingLine(float x0, float y0, float x1, float y1 , float dx, float dy) {
		
		setTag("movingLine");
		Point2D p0 = new Point2D(x0, y0);
		Point2D p1 = new Point2D(x1, y1);
		body = new Body(this, new Line(p0, p1), x0, y0);
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

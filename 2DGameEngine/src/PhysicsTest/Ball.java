package PhysicsTest;


import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Circle;
import Core.physics.Manifold;
import Core.physics.Polygon;

public class Ball extends GameObject {
	
	public Ball(float x, float y, int width, float dx, float dy) {
		
		setTag("ball");
		body = new Body(this, new Circle(width / 2), x, y);
		body.setRestitution(1f);
		body.setStaticFriction(0.2f);
		body.setDynamicFriction(0.1f);
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

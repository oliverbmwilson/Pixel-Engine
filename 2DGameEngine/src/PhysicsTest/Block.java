package PhysicsTest;

import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Manifold;
import Core.physics.Polygon;
import javafx.geometry.Point2D;

public class Block extends GameObject{
	
	public Block(float x, float y, int width, int height, float dx, float dy) {
		
		setTag("block");
		Point2D[] blockPoints = new Point2D[4];
		blockPoints[1] = new Point2D(x, y - 45);
		blockPoints[0] = new Point2D(x + width, y);
		blockPoints[2] = new Point2D(x + width, y + height);
		blockPoints[3] = new Point2D(x, y + height);
		body = new Body(this, new Polygon(blockPoints), x, y);
		body.setRestitution(1f);
		body.setGravity(false);
		body.setStatic();
		
		
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

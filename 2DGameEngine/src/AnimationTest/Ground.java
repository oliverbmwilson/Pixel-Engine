package AnimationTest;

import Core.GameContainer;
import Core.Renderer;
import Core.FX.ImageResource;
import Core.FX.ParallaxBackground;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Manifold;
import Core.physics.StaticBody;
import javafx.geometry.Point2D;

public class Ground extends GameObject {
	
	private ImageResource[] background = new ImageResource[5];
	private ParallaxBackground parallax;
	float x;
	float y;
	float dx;
	float dy;
	
	public Ground(int x, int y) {
		
		this.x = x;
		this.y = y;
		width = 272;
		height = 140;
		dx = 0;
		dy = 0;
		/*
		Point2D[] groundPoints = new Point2D[4];
		groundPoints[3] = new Point2D(x, 103);
		groundPoints[2] = new Point2D(x + width, 103);
		groundPoints[1] = new Point2D(x + width, 103 + height);
		groundPoints[0] = new Point2D(x, 103 + height);
		addComponent(new AdvancedCollider(this, x, 103, groundPoints, 0, 0.5f));
		*/
		
		background[4] = new ImageResource("/platform.png");
		background[3] = new ImageResource("/foreground.png");
		background[2] = new ImageResource("/buildings.png");
		background[1] = new ImageResource("/farBuildings.png");
		background[0] = new ImageResource("/background.png");
		parallax = new ParallaxBackground(background);
		parallax.setSpeed(0, 0.4f);
		parallax.setSpeed(1, 0.6f);
		parallax.setSpeed(2, 0.8f);
		parallax.setSpeed(3, 1.0f);
		parallax.setSpeed(4, 1.2f);
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, int x, int y) {
		

		r.drawBackground(parallax);
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void collisionEvent(Manifold manifold) {
		// TODO Auto-generated method stub
		
	}

}

package AnimationTest;

import Core.GameContainer;
import Core.Renderer;
import Core.FX.Animation;
import Core.FX.ImageResource;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Manifold;
import Core.physics.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Spy extends GameObject {
	
	ImageResource spriteSheet = new ImageResource("/Spy/SpySpriteSheet.png");
	Animation smoking = new Animation(spriteSheet, 5, 0.5f, true);
	int width, height;
	
	
	public Spy(float x, float y, int width, int height, float dx, float dy, float mass, float restitution) {
		
		setTag("spy");
		this.width = width;
		this.height = height;
		Point2D[] spyPoints = new Point2D[4];
		spyPoints[3] = new Point2D(x, y);
		spyPoints[2] = new Point2D(x + width, y);
		spyPoints[1] = new Point2D(x + width, y + height);
		spyPoints[0] = new Point2D(x, y + height);
		body = new Body(this, new Polygon(width, height), x, y);
		body.setMass(5.0f);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		if(gc.getInput().isKey(KeyCode.A)) {
			
			body.dx = -120;
			
			if(body.x < 0) {
				
				body.x = 0;
				body.dx = 0;
				
			}
			
		}
		
		if(gc.getInput().isKey(KeyCode.D)) {
			
			body.dx = 120;
			
			if(body.x + width > gc.getWorldWidth()) {
				
				body.dx = 0;
				body.x = gc.getWorldWidth() - width;
				
			}
			
		}
		
		if(gc.getInput().isKeyReleased(KeyCode.A)) {
			
			body.dx = 0;
			
			if(body.x < 0) {
				
				body.x = 0;
				
			}
			
		}
		
		if(gc.getInput().isKeyReleased(KeyCode.D)) {
			
			body.dx = 0;
			
			if(body.x + width > gc.getWorldWidth()) {
				
				body.x = gc.getWorldWidth() - width;
				
			}
			
		}
		
		if(gc.getInput().isKey(KeyCode.W)) {
			
			body.dy = -120;
			
			if(body.y < 0) {
				
				body.y = 0;
				body.dy = 0;
				
			}
			
		}
		
		if(gc.getInput().isKey(KeyCode.S)) {
			
			body.dy = 120;
			
			if(body.y + height > gc.getWorldHeight()) {
				
				body.dy = 0;
				body.y = gc.getWorldHeight() - height;
				
			}
			
		}
		
		if(gc.getInput().isKeyReleased(KeyCode.W)) {
			
			body.dy = 0;
			
			if(body.y < 0) {
				
				body.y = 0;
				
			}
			
		}
		
		if(gc.getInput().isKeyReleased(KeyCode.S)) {
			
			body.dy = 0;
			
			if(body.y + height > gc.getWorldHeight()) {
				
				body.y = gc.getWorldHeight() - height;
				
			}
			
		}
		
		body.x += body.dx * dt;
		body.y += body.dy * dt;
		
		smoking.update(dt);

		gc.getCamera().centerCamX(this);
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, int x, int y) {
		
		smoking.animate(r, x, y);
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void collisionEvent(Manifold manifold) {
		// TODO Auto-generated method stub
		
	}

}

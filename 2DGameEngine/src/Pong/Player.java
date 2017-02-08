package Pong;

import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Manifold;
import Core.physics.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class Player extends GameObject {
	
	private int width, height;
	
	public Player(int width, int height) {
		
		setTag("player");
		this.width = width;
		this.height = height;
		float x = 0;
		float y = 0;
		body = new Body(this, new Polygon(width, height), x, y);
		body.setStatic();
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		if(gc.getInput().isKey(KeyCode.W)) {
			
			body.dy = -120;
			
			if(body.y < 0) {
				
				body.y = 0;
				body.dy = 0;
				
			}
			
		}
		
		if(gc.getInput().isKey(KeyCode.S)) {
			
			body.dy = 120;
			
			if(body.y + height > gc.getHeight()) {
				
				body.dy = 0;
				body.y = gc.getHeight() - height;
				
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
			
			if(body.y + height > gc.getHeight()) {
				
				body.y = gc.getHeight() - height;
				
			}
			
		}
		
		body.x += body.dx * dt;
		body.y += body.dy * dt;
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, int x, int y) {
		
		r.drawRect(x, y, width, height, 0xffffffff);
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void collisionEvent(Manifold manifold) {
		// TODO Auto-generated method stub
		
	}

}

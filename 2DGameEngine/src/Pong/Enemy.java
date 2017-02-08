package Pong;

import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Manifold;
import Core.physics.Polygon;
import javafx.geometry.Point2D;

public class Enemy extends GameObject {
	
	private GameObject target = null;
	private int width, height;
	
	public Enemy(int width, int height) {
		
		setTag("enemy");
		int x = 304;
		int y = 0;
		this.width = width;
		this.height = height;
		body = new Body(this, new Polygon(width, height), x, y);
		body.setStatic();
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		if(target == null) {
			
			target = gc.getGame().peek().getObjectManager().findObject("ball");
			
		}
		
		if(target.getBody().getY() + target.getHeight() / 2  > body.y + height / 2) {
			
			body.dy = 120;
			
			if(body.y + height > gc.getHeight()) {
				
				body.y = gc.getHeight() - height;
				body.dy = 0;
				
			}
			
		}
			
		if(target.getBody().getY() + target.getHeight() / 2  < body.y + height / 2) {
				
			body.dy = -120;
			
			if(body.y < 0) {
				
				body.y = 0;
				body.dy=0;
			
			}
			
		}
		
		body.y += body.dy * dt;
		body.x += body.dx * dt;
		
		
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

package Pong;

import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Body;
import Core.physics.Circle;
import Core.physics.Manifold;
import Core.physics.Polygon;
import javafx.geometry.Point2D;

public class Ball extends GameObject {
	
	private int width, height;
	
	public Ball(int width, int height) {
		
		setTag("ball");
		int x = 156;
		int y = 116;
		this.width = width;
		this.height = height;
		body = new Body(this, new Circle(width / 2), x, y);
		body.setRestitution(1.0f);
		body.setDx(-2);
		body.setDy(3);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		if(body.y + height > gc.getHeight()) {
			
			body.y = gc.getHeight() - height;
			body.dy *= -1;
		}
		
		if(body.y < 0) {
			
			body.y = 0;
			body.dy *= -1;
		}
		
		body.x += body.dx * dt;
		body.y += body.dy * dt;
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, int x, int y) {
		
		r.drawRect(x, y, width, height, 0xff0000ff);
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void collisionEvent(Manifold manifold) {
		// TODO Auto-generated method stub
		
	}

}

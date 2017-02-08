package Core.components;

import java.util.ArrayList;
import java.util.List;

import Core.GameContainer;
import Core.Renderer;
import Core.physics.Body;
import Core.physics.Manifold;

public abstract class GameObject {
	
	protected int width, height;
	protected Body body;
	protected String tag = "null";
	protected boolean dead = false;
	
	public abstract void update(GameContainer gc, float dt);
	
	public abstract void collisionEvent(Manifold manifold);
	
	public abstract void render(GameContainer gc, Renderer r, int timeAdjustedX, int timeAdjustedY);
	
	public abstract void dispose();

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
	

}

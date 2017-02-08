package Core.components;

import Core.GameContainer;
import Core.Renderer;

public abstract class State {
	
	private GameContainer gc;
	
	public State(GameContainer gc) {
		
		this.gc = gc;
		
	}
	
	protected ObjectManager manager = new ObjectManager(gc);
	
	public abstract void update(GameContainer gc, float dt);
	
	public abstract void render(GameContainer gc, Renderer r, float alpha);
	 
	public abstract void dispose();
	
	public ObjectManager getObjectManager() {
		
		return manager;
		
	}

}

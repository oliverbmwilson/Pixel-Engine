package Pong;

import java.util.ArrayList;
import java.util.List;

import Core.GameContainer;
import Core.Renderer;
import Core.components.ObjectManager;
import Core.components.State;
import Core.physics.Body;
import javafx.geometry.Point2D;

public class PlayState extends State{
	
	public PlayState(GameContainer gc) {
		super(gc);
		
		manager.addObject(new Player(16, 64));
		manager.addObject(new Ball(16, 16));
		manager.addObject(new Enemy(16, 64));
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		manager.updateObjects(gc, dt);
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, float alpha) {
		
		manager.renderObjects(gc, r, alpha);
		
	}

	@Override
	public void dispose() {
		
		manager.disposeObjects();
		
	}

	public ObjectManager getManager() {
		return manager;
	}
	
	

}

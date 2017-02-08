package AnimationTest;

import java.util.ArrayList;
import java.util.List;

import Core.GameContainer;
import Core.Renderer;
import Core.components.ObjectManager;
import Core.components.State;
import Core.physics.Body;
import javafx.geometry.Point2D;

public class PlayState extends State{
	
	Ground background;
	
	public PlayState(GameContainer gc) {
		super(gc);
		
		
		background = new Ground(0, 0);
		manager.addObject(new Spy(100, 100, 10, 38, 0, 0, 70, 0.5f));
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		manager.updateObjects(gc, dt);
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, float alpha) {
		
		background.render(gc, r, 0, 0);
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

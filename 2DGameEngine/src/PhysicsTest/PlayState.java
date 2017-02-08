package PhysicsTest;

import java.util.ArrayList;
import java.util.List;

import Core.GameContainer;
import Core.Renderer;
import Core.FX.ShadowType;
import Core.components.ObjectManager;
import Core.components.State;
import Core.physics.Body;
import Core.physics.Line;
import javafx.geometry.Point2D;

public class PlayState extends State{
	
	public PlayState(GameContainer gc) {
		super(gc);
		
		//Collision test
		/*
		manager.addObject(new Box(200, 200, 10, 10, 120, 20));
		manager.addObject(new Box(100, 200, 5, 20, 130, -50));
		manager.addObject(new Box(100, 100, 5, 10, 100, 180));
		manager.addObject(new Box(300, 200, 10, 10, 80, 10));
		manager.addObject(new Box(70, 70, 25, 6, -30, -30));
		
		manager.addObject(new Triangle(110, 110, 40, 50, 0, 0));
		manager.addObject(new Triangle(156, 10, 40, 50, 69, -30));
		manager.addObject(new Triangle(20, 150, 80, 60, -69, 30));
		
		manager.addObject(new Ball(50, 10, 50, 30, -60));
		manager.addObject(new Ball(310, 20, 50, 120, -30));
		manager.addObject(new Ball(70, 80, 10, 80, 10));
		manager.addObject(new Ball(45, 10, 25, -30, -30));
		manager.addObject(new Ball(100, 80,35, 0, -30));
		
		manager.addObject(new StaticLine(0, 0, 320, 0, 0, 0));
		manager.addObject(new StaticLine(0, 0, 0, 240, 0, 0));
		manager.addObject(new StaticLine(0, 240, 320, 240, 0, 0));
		manager.addObject(new StaticLine(320, 0, 320, 240, 0, 0));
		
		manager.addObject(new MovingLine(100, 50, 80, 20, 50, 0));
		manager.addObject(new MovingLine(300, 180, 320, 150, 0, -60));
		manager.addObject(new MovingLine(20, 80, 60, 80, -60, 20));
		*/
		//Friction test
		
		manager.addObject(new StaticLine(0, 0, 320, 0, 0, 0));
		manager.addObject(new StaticLine(0, 0, 0, 240, 0, 0));
		manager.addObject(new StaticLine(0, 240, 320, 240, 0, 0));
		manager.addObject(new StaticLine(320, 0, 320, 240, 0, 0));
		
		manager.addObject(new Box(200, 30, 10, 10, 120, 0));
		manager.addObject(new Box(100, 50, 5, 20, 120, -80));
		
		manager.addObject(new Triangle(110, 0, 40, 50, 120, -60));
		manager.addObject(new Triangle(156, 20, 40, 50, 150, 60));
		
		manager.addObject(new Ball(70, 80, 50, 120, -20));
		manager.addObject(new Ball(180, 70, 50, 120, 80));
		
		manager.addObject(new MovingLine(100, 70, 80, 20, 120, 80));
		manager.addObject(new MovingLine(250, 150, 320, 150, 120,20));
		
		//manager.addObject(new Block(50, 200, 220, 50, 0, 0));
		
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

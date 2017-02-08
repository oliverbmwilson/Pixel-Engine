package Core.components;

import java.util.ArrayList;
import java.util.List;

import Core.GameContainer;
import Core.Renderer;
import Core.physics.Body;

public class ObjectManager {
	
	private GameContainer gc;
	
	public ObjectManager(GameContainer gc) {
		
		this.gc = gc;
		
	}
	
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	
	public void addObject(GameObject object) {
		
		gameObjects.add(object);
		
	}
	
	public void updateObjects(GameContainer gc, float dt) {
		
		for(int i = 0; i < gameObjects.size(); i++) {
			
			GameObject object = gameObjects.get(i);
			Body body = object.getBody();
			gc.getPhysics().addBody(body);
			
			object.update(gc, dt);
			
			if(object.isDead()) {
				
				gc.getPhysics().getBodies().remove(body);
				gameObjects.remove(object);
				
			}
			
		}
		
	}
	
	public void renderObjects(GameContainer gc, Renderer r, float alpha) {
		
		for(GameObject object: gameObjects) {
			
			//used to offset the rendering position of an object in the casee that rendering and physics don't occur at the same rate
			//For example physics might update at 30fps and rendering at 60fps
			//This equation will give the visuak illusion that they are in sync
			Body body = object.getBody();
			
			int x = (int) (((body.getX() * alpha) + (body.getPreviousX() * (1 - alpha))) + 0.5f);
			int y = (int) (((body.getY() * alpha) + (body.getPreviousY() * (1 - alpha))) + 0.5f);
			
			object.render(gc, r, x, y);
		}
		
	}
	
	public GameObject findObject(String tag) {
		
		for(GameObject object: gameObjects) {
			
			if(object.getTag().equalsIgnoreCase(tag)) {
				
				return object;
				
			}
			
		}
		
		return null;
		
	}
	
	public void disposeObjects() {
		
		for(GameObject object: gameObjects) {
			
			object.dispose();
				
		}
		
	}

}










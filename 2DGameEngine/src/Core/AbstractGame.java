package Core;

import java.util.Stack;

import Core.components.State;
import javafx.application.Application;
import javafx.stage.Stage;

public abstract class AbstractGame extends Application {
	
	private Stack<State> states = new Stack<State>();
	
	public abstract void update(GameContainer gc, float dt);
	
	public abstract void render(GameContainer gc, Renderer r, float alpha);
	
	public abstract void start(Stage primaryStage) throws Exception;
	
	public State peek() {
		
		return states.peek();
	}
	
	public void push(State s) {
		
		states.push(s);
		
	}
	
	public void pop() {
		
		states.peek().dispose();
		states.pop();
		
	}
	
	public void setState(State s) {
		
		states.pop();
		states.push(s);
		
	}

}

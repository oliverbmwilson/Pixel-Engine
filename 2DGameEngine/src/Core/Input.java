package Core;

import java.util.HashSet;
import java.util.Set;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Input implements EventHandler<Event>{
	
	//HashMaps are for storing input
	//Keys/buttons keeps track of keys/buttons pressed in current frame
	//KeysLast/buttonsLast keeps track of keys/buttons pressed in previous frame
	//This is so we know whether or not a key is being pressed or released
	private Set<KeyCode> keys = new HashSet<KeyCode>();
	private Set<KeyCode> keysLast = new HashSet<KeyCode>();
	private Set<MouseButton> buttons = new HashSet<MouseButton>();
	private Set<MouseButton> buttonsLast = new HashSet<MouseButton>();
	
	private int mouseX, mouseY;
	
	private GameContainer gc;
	
	public Input(GameContainer gameContainer) {
		
		gc = gameContainer;
		
		//Attaching all the event listeners to the scene
		gc.getWindow().getScene().setOnMouseClicked(this);  
		gc.getWindow().getScene().setOnMouseEntered(this);
		gc.getWindow().getScene().setOnMouseExited(this);
		gc.getWindow().getScene().setOnMouseDragged(this);
		gc.getWindow().getScene().setOnMouseMoved(this);
		gc.getWindow().getScene().setOnMousePressed(this);
		gc.getWindow().getScene().setOnMouseReleased(this);
		gc.getWindow().getScene().setOnKeyPressed(this);
		gc.getWindow().getScene().setOnKeyReleased(this);
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update() {
		keysLast = ((Set) ((HashSet) keys).clone());
		buttonsLast = ((Set) ((HashSet) buttons).clone());
	}
	
	public boolean isKey(KeyCode keyCode) {
		return keys.contains(keyCode);
	}
	
	public boolean isKeyPressed(KeyCode keyCode) {
		return keys.contains(keyCode) && !keysLast.contains(keyCode);
	}
	
	public boolean isKeyReleased(KeyCode keyCode) {
		return !keys.contains(keyCode) && keysLast.contains(keyCode);
	}
	
	public boolean isButton(MouseButton mouseButton) {
		return buttons.contains(mouseButton);
	}
	
	public boolean isButtonPressed(MouseButton mouseButton) {
		return buttons.contains(mouseButton) && !buttonsLast.contains(mouseButton);
	}
	
	public boolean isButtonReleased(MouseButton mouseButton) {
		return !buttons.contains(mouseButton) && buttonsLast.contains(mouseButton);
	}

	@Override
	public void handle(Event e) {
		
	    String eventType = e.getEventType().toString();
	    
	    switch (eventType) {
	     
	    	case "MOUSE_CLICKED":
	    		break;
	      
	    	case "MOUSE_ENTERED":
	    		break;
	      
	    	case "MOUSE_EXITED":
	    		break;
	      
	    	case "MOUSE_DRAGGED":
	    		break; 
	     
	    	case "MOUSE_MOVED":
	    		MouseEvent mm = (MouseEvent) e;
	    		mouseX = (int) (mm.getX() / gc.getScale());
	    		mouseY = (int) (mm.getY() / gc.getScale());
	    		break;
	     
	    	case "MOUSE_RELEASED":
	    		MouseEvent mr = (MouseEvent) e;
	    		buttons.remove(mr.getButton());
	    		break;
	     
	    	case "MOUSE_PRESSED":
	    		MouseEvent mp = (MouseEvent) e;
	    		buttons.add(mp.getButton());
	    		break;
	     
	    	case "KEY_PRESSED":
	    		KeyEvent kp = (KeyEvent) e;
	    		keys.add(kp.getCode());
	    		break;
	    		
	    	case "KEY_RELEASED":
	    		KeyEvent kr = (KeyEvent) e;
	    		keys.remove(kr.getCode());
	    		break;
    	}
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

}

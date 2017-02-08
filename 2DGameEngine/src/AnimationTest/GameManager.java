package AnimationTest;

import java.util.Stack;

import Core.AbstractGame;
import Core.GameContainer;
import Core.Input;
import Core.Renderer;
import Core.FX.ImageResource;
import Core.FX.Light;
import Core.FX.ShadowType;
import Core.components.State;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class GameManager extends AbstractGame {
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	

	@Override
	public void update(GameContainer gc, float dt) {
		
		peek().update(gc, dt);
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, float alpha) {
		
		peek().render(gc, r, alpha);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameContainer gc = new GameContainer(this, primaryStage);
		push(new PlayState(gc));
		gc.setWidth(272);
		gc.setHeight(140);
		gc.setWorldWidth(1000);
		gc.setScale(3);
		gc.setDynamicLights(false);
		gc.setLighting(false);
		gc.setGravity(0);
		gc.start();
		gc.setAmbientLight(1.0f, 0.2f, 0.2f, 0.2f);
	}

}

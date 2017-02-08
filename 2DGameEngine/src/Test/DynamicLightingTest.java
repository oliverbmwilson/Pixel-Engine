package Test;

import Core.AbstractGame;
import Core.GameContainer;
import Core.Input;
import Core.Renderer;
import Core.FX.ImageResource;
import Core.FX.Light;
import Core.FX.ShadowType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class DynamicLightingTest extends AbstractGame {
	
	private ImageResource image2 = new ImageResource("/shadowTest2.png");
	private ImageResource image1 = new ImageResource("/shadowTest1.png");
	private Light light1 = new Light(0xffff0000, 200);
	private Light light2 = new Light(0xff00ff00, 60);
	private Light light3 = new Light(0xff0000ff, 60);
	
	public static void main(String[] args) {
		
		launch(args);
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, float alpha) {
		
		r.drawImage(image2, 0, 0);
		r.drawLight(light1, gc.getInput().getMouseX(), gc.getInput().getMouseY());
		r.drawImage(image1, 0, 0);
		
		//r.drawLight(light2, 50, 50);
		//r.drawLight(light3, 75, 50);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameContainer gc = new GameContainer(this, primaryStage);
		image1.setShadowType(ShadowType.FADE);
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(3);
		gc.setDynamicLights(true);
		gc.setLighting(false);
		gc.start();
		gc.setAmbientLight(1.0f, 0.2f, 0.2f, 0.2f);
	}

}

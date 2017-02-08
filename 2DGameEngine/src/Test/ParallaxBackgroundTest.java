package Test;


import Core.AbstractGame;
import Core.GameContainer;
import Core.Input;
import Core.Renderer;
import Core.FX.ImageResource;
import Core.FX.Light;
import Core.FX.ParallaxBackground;
import Core.FX.ShadowType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class ParallaxBackgroundTest extends AbstractGame {
	
	private ImageResource[] background = new ImageResource[5];
	private int transX = 0;
	private ParallaxBackground parallax;
	
	public ParallaxBackgroundTest() {
		background[4] = new ImageResource("/platform.png");
		background[3] = new ImageResource("/foreground.png");
		background[2] = new ImageResource("/buildings.png");
		background[1] = new ImageResource("/farBuildings.png");
		background[0] = new ImageResource("/background.png");
		parallax = new ParallaxBackground(background);
		parallax.setSpeed(0, 0.4f);
		parallax.setSpeed(1, 0.6f);
		parallax.setSpeed(2, 0.8f);
		parallax.setSpeed(3, 1.0f);
		parallax.setSpeed(4, 1.2f);
	
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
	}

	@Override
	public void update(GameContainer gc, float dt) {
		
		if(gc.getInput().isKey(KeyCode.D)) {
			transX += 2;
			if(transX > gc.getWorldWidth() - gc.getWidth() / 2) {
				transX = gc.getWorldWidth() - gc.getWidth() / 2;
			}
		}
		
		if(gc.getInput().isKey(KeyCode.A)) {
			transX -= 2;
			if(transX < gc.getWidth() / 2) {
				transX = gc.getWidth() / 2;
			}
		}
		
	}

	@Override
	public void render(GameContainer gc, Renderer r, float alpha) {
		
		gc.getCamera().setCamX(transX);
		r.drawBackground(parallax);
		
		//r.drawLight(light2, 50, 50);
		//r.drawLight(light3, 75, 50);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		GameContainer gc = new GameContainer(this, primaryStage);
		gc.setWidth(272);
		gc.setHeight(140);
		gc.setWorldWidth(1000);
		gc.setScale(3);
		gc.setDynamicLights(false);
		gc.setLighting(false);
		gc.start();
		gc.setAmbientLight(1.0f, 0.2f, 0.2f, 0.2f);
	}

}

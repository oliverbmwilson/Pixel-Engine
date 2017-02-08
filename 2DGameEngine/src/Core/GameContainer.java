package Core;

import Core.FX.Camera;
import Core.physics.ImpulseScene;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GameContainer {
	
	private AbstractGame game;
	private Stage stage;
	private boolean isRunning = false;
	private boolean render = false;
	private AnimationTimer gameLoop;
	private float frameCap = 60.0f;
	private float fps = 1 / frameCap;
	private int fpsDisplay = 0;
	private int fpsCounter = 0;
	private float lastTime;
	private float passedTime;
	private float frameTime = 0;
	private Renderer renderer;
	private int width = 320, height = 240;
	private int worldWidth = 320, worldHeight = 240;
	private float scale = 2.0f;
	private float gravity = 50f;
	private String title;
	private Window window;
	private Input input;
	private ImpulseScene physics = new ImpulseScene();
	private Camera camera;
	private boolean dynamicLights = false;
	private boolean clearScreen = true;
	private boolean lighting = false;
	private boolean debug = false;
	
	public GameContainer(AbstractGame g, Stage s) {
		
		game = g;
		stage = s;
		title = "TwinkleToeEngine";
		
	}
	
	public void start() {
		
		if(isRunning) {
			return;
		}
		
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		camera = new Camera(this);
		gameLoop = new AnimationTimer() {

			@Override
			public void handle(long time) {
				
				double firstTime = time / 1000000000.0;
				run(firstTime);
			}
		};
		
		lastTime = (float) (System.nanoTime() / 1000000000.0);
		gameLoop.start();
		
	}
	
	public void stop() {
		
		if(!isRunning) {
			return;
		}
		
		isRunning = false;
		gameLoop.stop();
	}
	
	public void run(double firstTime) {
		
		isRunning = true;
		//Update FPS counter
	    float currentTime = (float) firstTime;
		passedTime += currentTime - lastTime;
		lastTime = currentTime;
		frameTime += passedTime;
		if(frameTime >= 1) {
			
			frameTime = 0;
			fpsDisplay = fpsCounter;
			fpsCounter= 0;
			
		}
		
		
		if(passedTime > 0.2f) {
			passedTime = 0.2f;
		}
		
		while(passedTime > fps) {
			
			passedTime -= fps;
		
			if(input.isKeyPressed(KeyCode.F2)) debug = !debug;
			game.update(this, fps);
			physics.update(fps);
			input.update();
			render = true;
			
		}
		
		//We only want to render if we have done an update
		if(render) {
			
			float alpha = passedTime / fps;
			
			if(clearScreen) renderer.clear();
			game.render(this, renderer, alpha);
			if(lighting || dynamicLights) { 
				renderer.drawLightArray();
				renderer.flushMaps();
			}
			//if(debug) physics.render(renderer);
			if(debug) renderer.drawString("FPS: " + fpsDisplay, 0xffffff00, 2 + camera.getCamX(), 2 + camera.getCamY());
			renderer.updateWritableImage();
			window.update();
			fpsCounter++;
		
		}
		
		
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Stage getStage() {
		return stage;
	}

	public boolean isDynamicLights() {
		return dynamicLights;
	}

	public void setDynamicLights(boolean dynamicLights) {
		this.dynamicLights = dynamicLights;
	}

	public boolean isClearScreen() {
		return clearScreen;
	}

	public void setClearScreen(boolean clearScreen) {
		this.clearScreen = clearScreen;
	}

	public boolean isLighting() {
		return lighting;
	}

	public void setLighting(boolean lighting) {
		this.lighting = lighting;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	public void setAmbientLight(float alpha, float red, float green, float blue) {
		this.renderer.setAmbientLight(alpha, red, green, blue);
	}

	public AbstractGame getGame() {
		return game;
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public ImpulseScene getPhysics() {
		return physics;
	}

	public void setPhysics(ImpulseScene physics) {
		this.physics = physics;
	}

	public float getFrameCap() {
		return frameCap;
	}

	public void setFrameCap(float frameCap) {
		this.frameCap = frameCap;
		this.fps = 1 / frameCap;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

	public Camera getCamera() {
		return camera;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
		physics.setGravity(gravity);
	}
}

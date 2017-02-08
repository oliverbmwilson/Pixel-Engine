package Core;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

public class Window {
	
	private Stage stage;
	private Scene scene;
	private WritableImage image;
	private Canvas canvas;
	private GraphicsContext g;
	private Group root;
		
	public Window(GameContainer gc) {
		
		stage = gc.getStage();
		stage.setTitle(gc.getTitle());
		
		root = new Group();
		canvas = new Canvas(gc.getWidth() * gc.getScale(), gc.getHeight() * gc.getScale());
		
		
		root.getChildren().add(canvas);
		scene = new Scene(root);
		
		g = canvas.getGraphicsContext2D();
		image = new WritableImage(gc.getWidth(), gc.getHeight());
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
		
	}
	
	public void update() {
		
		//no need for buffer strategy as this is built into the Scene API
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public WritableImage getImage() {
		return image;
	}
	
	public void setImage(WritableImage image) {
		this.image = image;
	}

	public Scene getScene() {
		return scene;
	}

}

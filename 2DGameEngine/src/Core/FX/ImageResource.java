package Core.FX;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class ImageResource {
	
	//need to create a int array that contains the colour of every pixel in the image
	private int width, height;
	private int[] pixels;
	private PixelReader reader;
	//default of images is to have no shadows
	private ShadowType shadowType = ShadowType.NONE;
	
	public ImageResource(String path) {
		
		Image image = null;
		
		try {
            image = new Image(getClass().getResourceAsStream(path));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		width = (int) image.getWidth();
		height = (int) image.getHeight();
		reader = image.getPixelReader();
		pixels = new int[width * height];
		
		//Iterate over each of the pixels getting their ARGB int value, adding them to a colour array
		//Saving every pixels colour in the colour array means that new colour 
		//objects do not have to be created every time the image is rendered
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int index = x + y * width;
				pixels[index] = reader.getArgb(x, y);
			}
		}
		
		//Delete the image from memory as we have the colour array of it instead
		image = null;
		
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public ShadowType getShadowType() {
		return shadowType;
	}

	public void setShadowType(ShadowType shadowType) {
		this.shadowType = shadowType;
	}

}

package Core;


import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import Core.FX.Font;
import Core.FX.ImageResource;
import Core.FX.ImageTile;
import Core.FX.Light;
import Core.FX.LightRequest;
import Core.FX.ParallaxBackground;
import Core.FX.Pixel;
import Core.FX.ShadowType;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritablePixelFormat;

public class Renderer {
	
	//The image will be represented by a one dimensional int array. 
	//The format of the pixels will be ARGB
	private GameContainer gc;
	private int[] pixels;
	private int[] lightMap;
	private ShadowType[] shadowMap;
	private PixelWriter writer;
	private WritablePixelFormat<IntBuffer> format;
	private int width, height;
	private Font font = Font.STANDARD;
	private int ambientLight = Pixel.getColour(1, 0.1f, 0.1f, 0.1f);
	private List<LightRequest> lightRequests = new ArrayList<LightRequest>();
	private int clearColour = 0xff000000;
	//The trans values can be used to shift the lighting and the images so that a camera can be used
	
	public Renderer(GameContainer gc) {
		
		this.gc = gc;
		width = gc.getWidth();
		height = gc.getHeight();
		pixels = new int[width * height];
		lightMap = new int[width * height];
		shadowMap = new ShadowType[width * height];
		writer = gc.getWindow().getImage().getPixelWriter();
		format = WritablePixelFormat.getIntArgbPreInstance();
		
	}
	
	public void setPixel(int x, int y, int colour, ShadowType shadowType) {
		
		x -= gc.getCamera().getCamX();
		y -= gc.getCamera().getCamY();
		
		//Make sure pixel is not outside border of window. Magenta is not drawn
		//as this is the background of the font file
		//The shadowtype of the pixel is given at the same time
		if(x < 0 || x >= width || y < 0 || y >= height || colour == 0xffff00ff) {
			return;
		}
		
		pixels[x + y * width] = colour;
		shadowMap[x + y * width] = shadowType;
		
	}
	
	public void setLightMap(int x, int y, int colour) {
		
		x -= gc.getCamera().getCamX();
		y -= gc.getCamera().getCamY();
		
		//Make sure pixel is not outside border of window. Magenta is not drawn
		//as this is the background of the font file
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}
		
		//sets the pixel light as the most intense of the two lights that are being 
		//compared (the light that is already there and the light passed in as an argument)
		lightMap[x + y * width] = Pixel.getMax(colour, lightMap[x + y * width]);
		
	}
	
	public ShadowType getShadowMap(int x, int y) {
		
		x -= gc.getCamera().getCamX();
		y -= gc.getCamera().getCamY();
		
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return ShadowType.TOTAL;
		}
		
		return shadowMap[x + y * width];
		
	}
	
	public void clear() {
		
		if(gc.isLighting()) {
		
			//turns the screen black and sets the lighting to the ambient light
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					
					pixels[x + y * width] = clearColour;
					lightMap[x + y * width] = ambientLight;
					
				}
			}
		
		} else {
			
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					
					pixels[x + y * width] = clearColour;
					
				}
			}
			
		}
		
	}
	
	public void drawString(String text, int colour, int offx, int offy) {
		
		//set the text to uppercase as we only have uppercase unicodes in the font
		text = text.toUpperCase();
		//This offset ensure the characters of the string are printed next 
		//to each other and not ontop of each other
		int offset = 0;
		
		//This for loop traverses across each unicode in the text
		for(int i = 0; i < text.length(); i++) {
			
			//An offset of 32 is applied because we have only included 
			//unicodes starting from the 32nd code
			int unicode = text.codePointAt(i) - 32;
			
			//This double for loop sets a pixel on the canvas for every pixel 
			//in the segement of the font image that contains the unicode character
			// y = 1 to get rid of the blue 
			for(int y = 1; y < font.getImage().getHeight(); y++) {
				for(int x = 0; x < font.getWidths()[unicode]; x++) {
					
					//if statement gets the colour of the pixel in the font image. If it is equal to black
					//(the colour of the writing in the font) then we will render the pixel in the canvas the colour that was passed in.
					//Otherwise, we do not render the pixel
					if(font.getImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getImage().getWidth()] == 0xff000000) {
						//off y needs to have 1 pixel removed to account for the top line missing
						//Shadowtype is set to none as fonts don't cast shadows
						setPixel(x + offx + offset, y + offy -1, colour, ShadowType.NONE);
					}
					
				}
			}
			
			offset += font.getWidths()[unicode];
			
		}
		
	}
	
	public void drawAnimation(ImageResource image, int offX, int offY, int offset, int width, ShadowType shadow) {
	
		//Width of the sprite sheet segment along with the offset of the segment is added in to the argument similar to drawstring 
		for(int y = 1; y < image.getHeight(); y++) {
			for(int x = 0; x < width; x++) {
				setPixel(x + offX, y + offY -1, image.getPixels()[(x + offset) + y * image.getWidth()], shadow);
			}
				
		}
	}
	
	public void drawImage(ImageResource image, int offx, int offy) {
		
		//Only changes the pixel in the pixel array where the image will be located
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				setPixel(x + offx, y + offy, image.getPixels()[x + y * image.getWidth()], image.getShadowType());
			}
		}
		
	}
	
	public void drawImageTile(ImageTile image, int offx, int offy, int tileX, int tileY) {
		
		//Only changes the pixel in the pixel array where the tile will be located
		for(int y = 0; y < image.getTileWidth(); y++) {
			for(int x = 0; x < image.getTileHeight(); x++) {
				setPixel(x + offx, y + offy, image.getPixels()[(x + image.getTileWidth() * tileX) + (y + image.getTileHeight() * tileY) * image.getWidth()], image.getShadowType());
			}
		}
		
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, int colour, ShadowType shadow) {
		
		int d = 0;
		
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		
		int dy2 = (dy << 1);
		int dx2 = (dx << 1);
		
		int ix = x1 < x2 ? 1 : -1;
		int iy = y1 < y2 ? 1 : -1;
		
		if(dy <= dx) {
			
			while(true) {
				
				setPixel(x1, y1, colour, shadow);
				if(x1 == x2) {
					break;
				}
				x1 += ix;
				d += dy2;
				if(d > dx) {
					y1 += iy;
					d -= dx2;
				}
				
			}
			
		} else {
			
			while(true) {
				
				setPixel(x1, y1, colour, shadow);
				if(y1 == y2) {
					break;
				}
				y1 += iy;
				d += dx2;
				if(d > dy) {
					x1 += ix;
					d -= dy2;
				}
				
			}
			
		}
		
	}
	
	public void drawRect(int offX, int offY, int width, int height, int colour, ShadowType shadow) {
		
		drawLine(offX, offY, offX + width, offY, colour, shadow);
		drawLine(offX + width, offY, offX + width, offY + height, colour, shadow);
		drawLine(offX + width, offY + height, offX, offY + height, colour, shadow);
		drawLine(offX, offY + height, offX, offY, colour, shadow);
		
	}
	
	public void drawCircle(int offX, int offY, int radius, int colour, ShadowType shadow) {
		
		int y = radius;
		int x = 0;
		int d = (3-2 * radius);
		
		setPixel(x + offX, y + offY, colour, shadow);
		
		while(x <= y) {
			
			if(d <= 0) {
				
				d = d + (4 * x + 6);
				
			} else {
				
				d = d + 4 * (x - y) + 10;
				
				y--;
				
			}
			
			x++;
			
			setPixel(offX + x, offY + y, colour, shadow);
			setPixel(offX + x, offY - y, colour, shadow);
			setPixel(offX - x, offY + y, colour, shadow);
			setPixel(offX - x, offY - y, colour, shadow);
			setPixel(offX + y, offY + x, colour, shadow);
			setPixel(offX - y, offY + x, colour, shadow);
			setPixel(offX + y, offY - x, colour, shadow);
			setPixel(offX - y, offY - x, colour, shadow);
		}
		
	}
	
	public void drawPolygon(Point2D[] vertices, int colour, ShadowType shadow) {
		
		for(int i = 0; i < vertices.length; i ++) {
			
			if(i == vertices.length - 1) {
				
				drawLine((int) vertices[i].getX(), (int) vertices[i].getY(), (int) vertices[0].getX(), (int) vertices[0].getY(), colour, shadow);
				
			} else {
			
				drawLine((int) vertices[i].getX(), (int) vertices[i].getY(), (int) vertices[i+1].getX(), (int) vertices[i+1].getY(), colour, shadow);
			
			}
			
		}
		
		
	}
	
	//Method for drawing a parallax background
	public void drawBackground(ParallaxBackground background) {
		
		ImageResource[] layers = background.getLayers();
		float[] speeds = background.getSpeeds();
		
		for(int i = 0; i < layers.length; i++) {
			
			int offX = (int) (gc.getCamera().getCamX() -(gc.getCamera().getCamX() * speeds[i]) % width);
			
			drawImage(layers[i], offX, 0);
			drawImage(layers[i], offX - width, 0);
			drawImage(layers[i], offX + width, 0);
				
		}
		
	}
	
	//When the game requests to draw a light. It is stored in an arraylist so that it can be rendered at the end of the pulse
	public void drawLight(Light light, int offX, int offY) {
		
		if(gc.isDynamicLights() || gc.isLighting()) {
			
			lightRequests.add(new LightRequest(light, offX, offY));
			
		} 
		
	}
	
	//This method is called from the GameContainer when the pulse is ready to draw all the lighting
	public void drawLightArray() {
		
		for(LightRequest lr : lightRequests) {
			
			drawLightRequest(lr.getLight(), lr.getX(), lr.getY());
			
		}
		
		lightRequests.clear();
		
	}
	
	private void drawLightRequest(Light light, int offX, int offY) {
		
		if(gc.isDynamicLights()) {
		
			//Only changes the pixel in the lightmap array where the light will be located
			//Each of the 4 drawlightline method calls draws the light from the centre of the 
			//source outwards in the up, down, left and right directions
			for(int i = 0; i <= light.getDiameter(); i++) {
				
				//Scans across the top edge
				drawLightLine(light.getRadius(), light.getRadius(), i, 0, light, offX, offY);
				//Scans across the bottom edge
				drawLightLine(light.getRadius(), light.getRadius(), i, light.getDiameter(), light, offX, offY);
				//Scans across the left edge
				drawLightLine(light.getRadius(), light.getRadius(), 0, i, light, offX, offY);
				//Scans across the right edge
				drawLightLine(light.getRadius(), light.getRadius(),light.getDiameter(), i, light, offX, offY);
				
			}
			
		} else {
			
			for(int y = 0; y < light.getDiameter(); y++) {
				for(int x = 0; x < light.getDiameter(); x++) {
					
					setLightMap(x + offX - light.getRadius(), y + offY - light.getRadius(), light.getLightValue(x, y));
					
				}
			}
			
		}
		
	}
	
	//Light line algorithm. Not exactly sure how the math works
	private void drawLightLine(int x0, int y0, int x1, int y1, Light light, int offX, int offY) {
		
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int e2;
		
		//intensity of light for dealing with objects that let some light through
		float intensity = 1.0f;
		
		//Used for calculating half shadows
		boolean hit = false;
		
		while(true) {
			
			//break if the light value is black (out of bounds of our radius) we don't need to continue rendering the light line
			if(light.getLightValue(x0, y0) == 0xff000000) break;
			
			int screenX = x0 - light.getRadius() + offX;
			int screenY = y0 - light.getRadius() + offY;
			
			
			if(intensity == 1) {
				//The light continues on if it is not passing through an object that has shadowing
				setLightMap(screenX , screenY, light.getLightValue(x0, y0));
			} else {
				//If the object does have shadowing then the intensity of the light is reduced by alling the getLightValue method
				setLightMap(screenX , screenY, Pixel.getColourIntensity(light.getLightValue(x0, y0), intensity));
			}
			
			if(x0 == x1 && y0 == y1) break;
			
			//stops the light ray traveling any further as it has 
			//reached an object that light cannot travel through if the pixel 
			//at the edge of the light ray (screenX, screenY) has a shadowtype of total
			if(getShadowMap(screenX, screenY) == ShadowType.TOTAL) break;
			
			//Similar to above, the intensity value of the light ray is decreased if hitting a pixel 
			//that only lets some light through
			if(getShadowMap(screenX, screenY) == ShadowType.FADE) intensity -= 0.1f;
			
			//The light intensity is halved for every pixel it goes through with half shadowing
			if(getShadowMap(screenX, screenY) == ShadowType.HALF && hit == false) {
				intensity /= 2;
				hit = true;
			}
			
			if(getShadowMap(screenX, screenY) == ShadowType.NONE && hit == true) hit = false;
			
			//Light intensity of the ray is now zero so there is no more light to render
			//We can't quite go down to zero otherwise the light traveling through objects 
			//with half shadowing will render forever as the intensity is halved
			if(intensity <= 0.1f) break;
			
			e2 = 2 * err;
			
			if(e2 > -1 * dy) {
				err -= dy;
				x0 += sx;
			}
			
			if(e2 < dx) {
				err += dx;
				y0 += sy;
			}
			
		}
		
	}
	
	//combines the two colour arrays together to blend the image colour and the light colour
	public void flushMaps() {
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				
				setPixel(x, y, Pixel.getLightBlend(pixels[x + y * width], lightMap[x + y * width], ambientLight), shadowMap[x + y * width]);
				lightMap[x + y * width] = ambientLight;
				
			}
		}
		
	}
	
	//Once the final colours of the pixel array have been set for the current frame, we 
	//update the writable image, ready for the canvas to display on the window
	public void updateWritableImage() {
		
		writer.setPixels(0, 0, width, height, format, pixels, 0, width);
		
	}
	
	public void drawImage(ImageResource image) {drawImage(image, 0 ,0);}
	public void drawImageTile(ImageTile image, int tileX, int tileY) {drawImageTile(image, 0 ,0, tileX, tileY);}
	public void drawRect(int offX, int offY, int width, int height, int colour) {drawRect(offX, offY, width, height, colour, ShadowType.NONE);}
	public void drawLight(Light light) {drawLight(light, 0 ,0);}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public int getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(float alpha, float red, float green, float blue) {
		this.ambientLight = Pixel.getColour(alpha, red, green, blue);
	}

}

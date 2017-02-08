package Core.FX;

public class Pixel {
	
	//Each method takes in a 32bit representation of
	//a colour and bitshfts it to retrieve only the colour we need. 
	//This deconstructs the colour into ARGB componenets
		
	public static float getAlpha(int colour) {
		
		return (0xff & (colour >> 24)) / 255f;
		
	}
	
	public static float getRed(int colour) {
		
		return (0xff & (colour >> 16)) / 255f;
		
	}
	
	public static float getGreen(int colour) {
		
		return (0xff & (colour >> 8)) / 255f;
		
	}
	
	public static float getBlue(int colour) {
		
		return (0xff & colour) / 255f;
		
	}
	
	//This method reconstructs the colour back into a readable format by taking in 
	//deconstructed colour components and bitshifting them
	
	public static int getColour(float a, float r, float g, float b) {
		
		return ((int) (a * 255 + 0.5f) << 24 |
				(int) (r * 255 + 0.5f) << 16 |
				(int) (g * 255 + 0.5f) << 8 |
				(int) (b * 255 + 0.5f));
		
	}
	
	//Used to create lighting effect. Deconstructs the colour of the pixel into its 
	//componenets and then adjusts the intensity of the components by the intensity value.
	//Then reconstructs the colour and returns it
	public static int getColourIntensity(int colour, float intensity) {
		
		return getColour(1, getRed(colour) * intensity,
							getGreen(colour) * intensity,
							getBlue(colour) * intensity);
		
	}
	
	//Combines the colour of the pixel image with the colour of the light as per the above method
	public static int getLightBlend(int colour, int light, int ambientLight) {
		
		//breaks down the componenets of the light colour
		float r = getRed(light);
		float g = getGreen(light);
		float b = getBlue(light);
		
		//breaks down componenets of the ambient light
		float ar = getRed(ambientLight);
		float ag = getGreen(ambientLight);
		float ab = getBlue(ambientLight);
		
		//makes sure the light is not darker than the ambient light,
		//otherwise we use the ambient light
		if(r < ar) {
			r = ar;	
		}
		if(g < ag) {
			g = ag;	
		}
		if(b < ab) {
			b = ab;	
		}
		
		//reconstructs the colour with the new light and returns it
		return getColour(1, getRed(colour) * r, getGreen(colour) * g, getBlue(colour) * b);
		
	}
	
	//takes two colours and returns only the more intense of the two compared componenets
	public static int getMax(int colour1, int colour2) {
		
		return getColour(1, Math.max(getRed(colour1), getRed(colour2)),
							Math.max(getGreen(colour1), getGreen(colour2)),
							Math.max(getBlue(colour1), getBlue(colour2)));
		
	}


}
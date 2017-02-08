package Core.FX;

public class Light {
	
	private int colour, radius, diameter;
	private int[] lightMap;
	
	public Light(int colour, int radius) {
		
		this.colour = colour;
		this.radius = radius;
		this.diameter = radius * 2;
		
		this.lightMap = new int[this.diameter * this.diameter];
		
		//set all of the pixels in the lightmap to the colour while 
		//decreasing in intensity as we get to the edges of the diameter
		for(int y = 0; y < diameter; y++) {
			for(int x = 0; x < diameter; x++) {
				
				// a^2 + b^2 = c^2 Gives the distance from the centre of the light source
				float distance = (float) Math.sqrt((x - radius) * (x - radius) + (y - radius) * (y - radius));
				
				//this if statement will tell us if the pixel is inisde of the lightmap circle
				if(distance < radius) {
					
					//sets the pixel in the light map to the colour adjusted by the intensity value if the pixel is inside the lightmap circle
					lightMap[x + y * diameter] = Pixel.getColourIntensity(colour, 1 - distance / radius);
				} else {
					
					//otherwise the colour is set to black if the pixel is outside the lightmap circle
					lightMap[x + y * diameter] = 0xff000000;
					
				}
				
			}
		}
		
	}
	
	//Used in the light line algorithm. It will return the colour 
	//of the pixel if it is inside the light circle
	public int getLightValue(int x, int y) {
		
		if(x < 0 || x >= diameter || y < 0 || y >= diameter) {
			
			return 0xff000000;
			
		}
		
		return lightMap[x + y * diameter];
		
	}

	public int getDiameter() {
		
		return diameter;
		
	}

	public int[] getLightMap() {
		return lightMap;
	}

	public int getRadius() {
		return radius;
	}

}

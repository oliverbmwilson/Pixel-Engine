package Core.FX;

public enum Font {

	STANDARD("/fonts/standard.png");

	private final int NUM_UNICODES = 65;
	private int[] offsets = new int[NUM_UNICODES];
	private int[] widths = new int[NUM_UNICODES];
	private ImageResource image;
	
	Font(String path) {
		
		/*
		The fonts are in an image where the top line of pixels contains blue and yellow dots. Each unicode
		character is between a blue and yellow dot. A blue dot indicates the start of a unicode character 
		and a yellow dot indicates the end. The for loop traverses across the top line of pixels in the image 
		and puts the pixel number into the offsets array when it finds a blue dot. If it finds a yellow dot, it 
		calculates the distance between this yellow dot and the last blue dot and puts this number into the widths 
		array. The end result gives us an array of offsets for each unicode character (where they begin in the 
		image) and an array of widths for each unicode character.
		*/
		
		image = new ImageResource(path);
		
		int unicode = -1;
		
		for(int x = 0; x < image.getWidth(); x++) {
			
			int colour = image.getPixels()[x];
			
			if(colour == 0xff0000ff) {
				
				unicode++;
				offsets[unicode] = x;
			}
			
			if(colour == 0xffffff00) {
				widths[unicode] = x - offsets[unicode];
			}
		}
	}

	public int[] getOffsets() {
		return offsets;
	}

	public int[] getWidths() {
		return widths;
	}

	public ImageResource getImage() {
		return image;
	}
	
}

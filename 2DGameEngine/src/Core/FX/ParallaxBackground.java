package Core.FX;

public class ParallaxBackground {
	
	private ImageResource[] layers;
	private float[] speeds;
	
	public ParallaxBackground(ImageResource[] layers) {
		
		this.layers = layers;
		this.speeds = new float[layers.length];
		
	}
	
	public void setSpeed(int position, float speed) {
		
		speeds[position] = speed;
		
	}
	
	public ImageResource[] getLayers() {
		
		return this.layers;
		
	}
	
	public float[] getSpeeds() {
		
		return this.speeds;
		
	}

}

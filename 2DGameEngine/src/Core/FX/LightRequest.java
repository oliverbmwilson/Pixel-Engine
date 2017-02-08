package Core.FX;

public class LightRequest {
	
	//A class for ensuring that light is rendered after everything else
	
	private Light light;
	private int x, y;
	
	public LightRequest(Light light, int x, int y) {
		
		this.light = light;
		this.x = x;
		this.y = y;
		
	}

	public Light getLight() {
		return light;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}

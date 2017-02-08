package Core.FX;


import Core.GameContainer;
import Core.components.GameObject;

public class Camera {
	
	private int maxOffsetX;
	private int maxOffsetY;
	private int minOffsetX;
	private int minOffsetY;
	private int camX;
	private int camY;
	private int camCenterX;
	private int camCenterY;
	
	public Camera(GameContainer gc) {
		
		minOffsetX = 0;
		minOffsetY = 0;
		maxOffsetX = gc.getWorldWidth() - gc.getWidth();
		maxOffsetY = gc.getWorldHeight() - gc.getHeight();
		camX = 0;
		camY = 0;
		camCenterX = gc.getWidth() / 2;
		camCenterY = gc.getHeight() / 2;
		
	}
	
	public void setCamX(float camX) {
		
		if(camX - camCenterX < minOffsetX) {
			
			this.camX = minOffsetX;
			
		} else if(camX - camCenterX > maxOffsetX) {
			
			this.camX = maxOffsetX;
			
		} else {
			
			this.camX = (int) (camX - camCenterX);
			
		}
		
	}
	
	public void setCamY(float camY) {
		
		if(camY - camCenterY < minOffsetY) {
			
			this.camY = minOffsetY;
			
		} else if(camY - camCenterY > maxOffsetY) {
			
			this.camY = maxOffsetY;
			
		} else {
			
			this.camY = (int) (camY - camCenterY);
			
		}
		
	}
	
	public void setCam(float x, float y) {
		
		setCamX(x);
		setCamY(y);
	}
	
	public void centerCam(GameObject object, int offX, int offY) {
		
		setCam(object.getBody().getX() + (object.getWidth() / 2) + offX, object.getBody().getY() + (object.getHeight() / 2) + offY);
			
		}
	
	public void centerCam(GameObject object) {
		
		centerCam(object, 0, 0);
		
	}
	
	public void centerCamX(GameObject object) {
		
		centerCamX(object, 0);
		
	}
	
	public void centerCamX(GameObject object, int offX) {
		
		setCam(object.getBody().getX() + (object.getWidth() / 2) + offX, 0);
		
	}
	
	public void centerCamY(GameObject object) {
		
		centerCamY(object, 0);
		
	}

	public void centerCamY(GameObject object, int offY) {
		
		setCam(object.getBody().getY() + (object.getHeight() / 2) + offY, 0);
		
	}

	public int getCamX() {
		return camX;
	}

	public int getCamY() {
		return camY;
	}

}

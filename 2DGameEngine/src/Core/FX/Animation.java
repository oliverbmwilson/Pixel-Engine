package Core.FX;


import Core.GameContainer;
import Core.Renderer;
import Core.FX.ImageResource;

public class Animation {
	
	private ImageResource image;
	private int[] offsets;
	private int[] widths;
	
	private int frames;
	private float timeEachFrame;
	private int currentFramePosition = 0;
	private float currentFrameTime = 0.0f;
	private boolean loop;
	
	
	
	public Animation(ImageResource image, int frames, float timeEachFrame, boolean loop) {
		
		//See the Font class for an explaination of this code
		
		this.image = image;
		this.offsets = new int[frames];
		this.widths = new int[frames];
		
		this.frames = frames;
		this.timeEachFrame = timeEachFrame;
		this.loop = loop;
		
		int frame = -1;
		
		for(int x = 0; x < image.getWidth(); x++) {
			
			int colour = image.getPixels()[x];
			
			if(colour == 0xff0000ff) {
				
				frame++;
				offsets[frame] = x;
			}
			
			if(colour == 0xffffff00) {
				widths[frame] = x - offsets[frame];
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

	public void update(float dt) {
		
		currentFrameTime += dt;
		
		if(currentFrameTime < timeEachFrame) {
			return;
		}
		
		if(currentFramePosition != frames - 1) {
			
			currentFramePosition++;
			
		} else if(currentFramePosition == frames -1 && loop) {
			
			currentFramePosition = 0;
		}
		
		currentFrameTime = 0;
		
	}

	public void animate(Renderer r, int x, int y) {
		
		r.drawAnimation(image, x, y, offsets[currentFramePosition], widths[currentFramePosition], image.getShadowType());
		
	}

	public float getTimeEachFrame() {
		return timeEachFrame;
	}

	public void setTimeEachFrame(float timeEachFrame) {
		this.timeEachFrame = timeEachFrame;
	}

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

}

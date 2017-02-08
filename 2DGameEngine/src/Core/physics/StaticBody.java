package Core.physics;

import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;

public class StaticBody extends Body {

	public StaticBody(GameObject object, Shape shape, float x, float y) {
		super(object, shape, x, y);
	}

}

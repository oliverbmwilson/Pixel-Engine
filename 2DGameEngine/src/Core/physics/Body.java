package Core.physics;

import Core.GameContainer;
import Core.Renderer;
import Core.components.GameObject;

public class Body {
	
	//A component class of an object that is used in calculating collision detection
	
	private GameObject object;
	protected Shape shape;
	public float x, y, dx, dy, previousX, previousY, previousDx, previousDy;
	public float forceX, forceY, orient, torque, angularVelocity;
	public float mass, inverseMass, inertia, inverseInertia;
	public float staticFriction, dynamicFriction, restitution;
	public boolean gravityOn = true;
	
	public Body(GameObject object, Shape shape, float x, float y) {
		
		this.object = object;
		this.shape = shape;
		
		this.x = x;
		this.y = y;
		this.dx = 0;
		this.dy = 0;
		this.previousX = this.x;
		this.previousY = this.y;
		this.previousDx = this.dx;
		this.previousDy = this.dy;
		
		this.angularVelocity = 0;
		this.torque = 0;
		this.orient = 0;
		this.forceX = 0;
		
		this.staticFriction = 0.5f;
		this.dynamicFriction = 0.3f;
		this.restitution = 0.2f;
		
		shape.body = this;
		shape.initialise();
		
	}
	
	public void setGravity(boolean gravity) {
		
		this.gravityOn = gravity;
		
	}
	
	public void applyForce( float x, float y ) {
		
		this.forceX += x;
		this.forceY += y;
	}

	
	public void applyImpulse(float impulseX, float impulseY, float contactVectorX, float contactVectorY) {
		
		// velocity += im * impulse;
		// angularVelocity += iI * Cross( contactVector, impulse );

		dx += impulseX * inverseMass;
		dy += impulseY * inverseMass;
		
		//angularVelocity += invInertia * Vec2.cross( contactVector, impulse );
	}
	
	public void correctPosition(float correctionX, float correctionY) {
		
		x += correctionX * inverseMass;
		y += correctionY * inverseMass;
		
	}
	
	public void setStatic() {
		
		inertia = 0.0f;
		inverseInertia = 0.0f;
		mass = 0.0f;
		inverseMass = 0.0f;
	}

	public void setOrient( float radians ) {
		orient = radians;
		shape.setOrient( radians );
	}
	
	public void addX(float x, float dt) {
		
		this.x += x * dt;
		
	}
	
	public void addY(float y, float dt) {
		
		this.y += y * dt;
		
	}
	
	public void addDx(float x, float dt) {
		
		this.dx += x * dt;
		
	}
	
	public void addDy(float y, float dt) {
		
		this.dy += y * dt;
		
	}

	public GameObject getObject() {
		return object;
	}

	public void setObject(GameObject object) {
		this.object = object;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
		this.inverseMass = 1 / mass;
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	public float getInverseMass() {
		return inverseMass;
	}

	public void setInverseMass(float inverseMass) {
		this.inverseMass = inverseMass;
	}

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getPreviousX() {
		return previousX;
	}

	public void setPreviousX(float previousX) {
		this.previousX = previousX;
	}

	public float getPreviousY() {
		return previousY;
	}

	public void setPreviousY(float previousY) {
		this.previousY = previousY;
	}

	public float getPreviousDx() {
		return previousDx;
	}

	public void setPreviousDx(float previousDx) {
		this.previousDx = previousDx;
	}

	public float getPreviousDy() {
		return previousDy;
	}

	public void setPreviousDy(float previousDy) {
		this.previousDy = previousDy;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
		
	}

	public float getForceX() {
		return forceX;
	}

	public void setForceX(float forceX) {
		this.forceX = forceX;
	}

	public float getForceY() {
		return forceY;
	}

	public void setForceY(float forceY) {
		this.forceY = forceY;
	}

	public float getTorque() {
		return torque;
	}

	public void setTorque(float torque) {
		this.torque = torque;
	}

	public float getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public float getInertia() {
		return inertia;
	}

	public void setInertia(float inertia) {
		this.inertia = inertia;
	}

	public float getInverseInertia() {
		return inverseInertia;
	}

	public void setInverseInertia(float inverseInertia) {
		this.inverseInertia = inverseInertia;
	}

	public float getStaticFriction() {
		return staticFriction;
	}

	public void setStaticFriction(float staticFriction) {
		this.staticFriction = staticFriction;
	}

	public float getDynamicFriction() {
		return dynamicFriction;
	}

	public void setDynamicFriction(float dynamicFriction) {
		this.dynamicFriction = dynamicFriction;
	}

	public float getOrient() {
		return orient;
	}

	public boolean isGravityOn() {
		return gravityOn;
	}

}


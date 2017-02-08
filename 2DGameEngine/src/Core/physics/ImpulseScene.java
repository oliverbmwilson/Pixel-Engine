package Core.physics;

import java.util.ArrayList;
import java.util.List;

import Core.Renderer;
import Core.components.GameObject;
import Core.physics.Shape.Type;

public class ImpulseScene {
	
	private float gravity = 50f;
	private static List<Body> bodies = new ArrayList<Body>();
	private List<Manifold> manifolds = new ArrayList<Manifold>();
	private CollisionEvaluation collisionEvaluation = new CollisionEvaluation();
	private CollisionResolution collisionResolution = new CollisionResolution();
	
	public void update(float dt) {
		
		manifolds.clear();
		
		if(bodies.size() > 1) {
			
			for (int i = 0; i < bodies.size(); ++i)
			{
				Body b0 = bodies.get(i);
			
				for (int j = i + 1; j < bodies.size(); ++j)
				{
					Body b1 = bodies.get(j);
			
					if (b0.getInverseMass() == 0 && b1.getInverseMass() == 0){
						
						continue;
					}
			
					Manifold manifold = new Manifold(b0, b1);
					
					testCollision(manifold);
			
					if(manifold.isCollided()) {
						
						manifolds.add(manifold);
						
					}
				}
			}
		}

		
		// Integrate forces
		//eg gravity
		for(Body body : bodies) {
			
			integrateForces(body, dt);
		}

		/*
		// Initialize collision
		for (int i = 0; i < contacts.size(); ++i)
		{
			contacts.get( i ).initialize();
		}

		 */
		// Solve collisions
		for(Manifold manifold : manifolds) {
			
			collisionResolution.resolve(manifold);
			
		}

		// Integrate velocities
		//This also integrates forces again
		for (Body body : bodies) {
			
			integrateVelocity(body, dt);
			
		}

		// Correct positions
		for (Manifold manifold : manifolds) {
			
			manifold.positionalCorrection();
			
		}

		// Clear all forces
		for (Body body : bodies) {
		
			body.setForceX(0);
			body.setForceY(0);
			//b.torque = 0;
		}
		
		for(Body body : bodies) {
			
			body.getShape().update();
			
		}
		
		clear();
		
	}
	
	public void testCollision(Manifold manifold) {
		
		Body b0 = manifold.getB0();
		Body b1 = manifold.getB1();
	
		if(b0.getShape().getType() == Type.Polygon && b1.getShape().getType() == Type.Polygon) {
			
			collisionEvaluation.evaluateCollisionPolygonPolygon(manifold, b0, b1);
			
		} else if(b0.getShape().getType() == Type.Circle && b1.getShape().getType() == Type.Circle) {
							
			collisionEvaluation.evaluateCollisionCircleCircle(manifold, b0, b1);
			
		} else if(b0.getShape().getType() == Type.Polygon && b1.getShape().getType() == Type.Circle) {
			
			collisionEvaluation.evaluateCollisionPolygonCircle(manifold, b0, b1);
			
		} else if(b0.getShape().getType() == Type.Circle && b1.getShape().getType() == Type.Polygon) {
			
			manifold.setB0(b1);
			manifold.setB1(b0);
			
			collisionEvaluation.evaluateCollisionPolygonCircle(manifold, b1, b0);
			
		} else if(b0.getShape().getType() == Type.Polygon && b1.getShape().getType() == Type.Line) {
			
			collisionEvaluation.evaluateCollisionPolygonLine(manifold, b0, b1);
			
		} else if(b0.getShape().getType() == Type.Line && b1.getShape().getType() == Type.Polygon) {
			
			manifold.setB0(b1);
			manifold.setB1(b0);
			
			collisionEvaluation.evaluateCollisionPolygonLine(manifold, b1, b0);
			
		} else if(b0.getShape().getType() == Type.Circle && b1.getShape().getType() == Type.Line) {
			
			collisionEvaluation.evaluateCollisionCircleLine(manifold, b0, b1);
			
		} else if(b0.getShape().getType() == Type.Line && b1.getShape().getType() == Type.Circle) {
			
			manifold.setB0(b1);
			manifold.setB1(b0);
	
			collisionEvaluation.evaluateCollisionCircleLine(manifold, b1, b0);
	
		} else if(b0.getShape().getType() == Type.Line && b1.getShape().getType() == Type.Line) {
			
			collisionEvaluation.evaluateCollisionLineLine(manifold, b0, b1);
			
		}
		
	}

	// Acceleration
	// F = mA
	// => A = F * 1/m

	// Explicit Euler
	// x += v * dt
	// v += (1/m * F) * dt

	// Semi-Implicit (Symplectic) Euler
	// v += (1/m * F) * dt
	// x += v * dt

	// see http://www.niksula.hut.fi/~hkankaan/Homepages/gravity.html
	public void integrateForces(Body body, float dt)
	{
//			if(b->im == 0.0f)
//				return;
//			b->velocity += (b->force * b->im + gravity) * (dt / 2.0f);
//			b->angularVelocity += b->torque * b->iI * (dt / 2.0f);

		if (body.getInverseMass() == 0.0f) {
			
			return;
			
		}
		
		float dts = dt * 0.5f;
		
		//Need to multiply by 0.5 because forces are applied twice each iteration
		body.addDx(body.getForceX() * body.getInverseMass(), dts);
		body.addDy(body.getForceY() * body.getInverseMass(), dts);
		
		if(body.gravityOn) {
			
			body.addDy(gravity, dts);
			
		}
		//b.angularVelocity += b.torque * b.invInertia * dts;
	}

	public void integrateVelocity(Body body, float dt) {
		
//			if(b->im == 0.0f)
//				return;
//			b->position += b->velocity * dt;
//			b->orient += b->angularVelocity * dt;
//			b->SetOrient( b->orient );
//			IntegrateForces( b, dt );

		if (body.getInverseMass() == 0.0f) {
			
			return;
			
		}

		body.addX(body.getDx(), dt );
		body.addY(body.getDy(), dt );
		//b.orient += b.angularVelocity * dt;
		//b.setOrient( b.orient );
		
		integrateForces(body, dt);
		
	
		
	}
	
	public Body add(GameObject object, Shape shape, float x, float y ) {
		
		Body body = new Body(object, shape, x, y);
		bodies.add(body);
		return body;
	}
	/*
	public void render(Renderer r) {
		
		for(Body body : bodies) {
			
			body.getShape().render(r);
			
		}
		
	}
	*/
	public void clear() { 
		
		manifolds.clear();
		bodies.clear();
	}
	
	public void addBody(Body body) {
		
		bodies.add(body);
		
	}

	public List<Body> getBodies() {
		return bodies;
	}

	public void setBodies(List<Body> bodies) {
		this.bodies = bodies;
	}

	public List<Manifold> getManifolds() {
		return manifolds;
	}

	public void setManifolds(List<Manifold> m) {
		manifolds = m;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	

}

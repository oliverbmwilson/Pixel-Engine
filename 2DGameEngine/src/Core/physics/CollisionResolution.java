package Core.physics;

import javafx.geometry.Point2D;

public class CollisionResolution {
	
	public void resolve(Manifold manifold) {
		
		Body b0 = manifold.getB0();
		Body b1 = manifold.getB1();
		
		
		  //Calculate relative velocity on the x and y
		float relativeVelocityX = b1.getDx() - b0.getDx();
		float relativeVelocityY = b1.getDy() - b0.getDy();
		
		//System.out.println(b1.getDy() + " " + b0.getDy());		
		  //Get the normal vector
		float normalX = (float) manifold.getAxis().getX();
		float normalY = (float) manifold.getAxis().getY();
			
		  //Calculate the relative velocity along the normal axis of the collision
		  float velocityAlongNormal = relativeVelocityX * normalX + relativeVelocityY * normalY;
			
		  //Don't resolve if the velocities of the objects are seperating
		  
		  if(velocityAlongNormal > 0) {
			  return;
		  }
		  
		  //Get the restitution or "bounciness" (always the smallest of the two obects
		  float e = (b0.getRestitution() + b1.getRestitution()) * 0.5f;
			
		  //Calculate magnitude of impulse
		  float j = -(1 + e) * velocityAlongNormal;
		  j /= b0.getInverseMass() + b1.getInverseMass();
			
		  //Apply impulse
		  float impulseX = normalX * j;
		  float impulseY = normalY * j;
		 
		  b0.applyImpulse(-impulseX, -impulseY, 0, 0);
		  b1.applyImpulse(impulseX, impulseY, 0, 0);
		  
		  
		  //Friction impulse
		  
		  //recalculate relative velocities
		  relativeVelocityX = b1.getDx() - b0.getDx();
		  relativeVelocityY = b1.getDy() - b0.getDy();
		  
		  Point2D relativeVelocity = new Point2D(relativeVelocityX, relativeVelocityY);
		  Point2D normal = new Point2D(normalX, normalY);
		  
		  //get the tangent
		  Point2D tangent = relativeVelocity.subtract(normal.multiply(relativeVelocity.dotProduct(normal)));
		  tangent = tangent.normalize();
		  
		  //Magnitude of friction vector
		  double jt = -(relativeVelocity.dotProduct(tangent));
		  jt /= b0.getInverseMass() + b1.getInverseMass();
		  
		  //Get average of the two types of each object's static friction coefficients
		  float mu = (b0.getStaticFriction() + b1.getStaticFriction()) / 2;
		  
		  //Use the smallest friction between static and dynamic. If the impulse impulse 
		  //is not strong enough to overcome the static friction threshold, then we use 
		  //static friction. Otherwise we use dynamic friction
		  Point2D frictionImpulse;
		  if(Math.abs(jt) < j * mu) {
			  
			  frictionImpulse = tangent.multiply(jt);
			  
		  } else {
			  
			 float dynamicFriction = (b0.getDynamicFriction() + b1.getDynamicFriction()) / 2;
			 frictionImpulse = tangent.multiply(dynamicFriction).multiply(-j);
			  
		  }
		  
		  //Apply friction impulse
		  float frictionImpulseX = (float) frictionImpulse.getX();
		  float frictionImpulseY = (float) frictionImpulse.getY();
		  
		  b0.applyImpulse(-frictionImpulseX, -frictionImpulseY, 0, 0);
		  b1.applyImpulse(frictionImpulseX, frictionImpulseY, 0, 0);
		  
		  
		  //Adjust positions slightly so that stationary objects do not sink into each other or jitter
		  float percent = 0.4f; // usually 20% to 80%
		  float slop = 0.05f; // usually 0.01 to 0.1. This prevents jittering
		  float correction = Math.max(( (float) manifold.getOverlap()) - slop, 0.0f) / (b0.getInverseMass() + b1.getInverseMass()) * percent;
		  float correctionVectorX = normalX * correction;
		  float correctionVectorY = normalY * correction;
		  manifold.setB0CorrectionX(-correctionVectorX);
		  manifold.setB0CorrectionY(-correctionVectorY);
		  manifold.setB1CorrectionX(correctionVectorX);
		  manifold.setB1CorrectionY(correctionVectorY);
		  
		  
	}

}

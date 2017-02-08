package Core.physics;

import javafx.geometry.Point2D;

public class CollisionEvaluation {
	
	public void evaluateCollisionPolygonPolygon(Manifold manifold, Body b0, Body b1) {
		
		//If every axis overlaps, we know there has been a collision so we need to return 
		//the axis that has the smallest overlap as this is the point where the two objects collided
		double overlap = Double.MAX_VALUE;
		Point2D normal = null;
		
		Polygon poly0 = (Polygon) b0.getShape();
		Polygon poly1 = (Polygon) b1.getShape();
		
		//We need to retrieve the normal axis of both shapes
		Point2D[] axes0 = poly0.getAxes();
		Point2D[] axes1 = poly1.getAxes();
		
		// loop over the axes of the first shape
		for (int i = 0; i < axes0.length; i++) {
			
			  Point2D axis = axes0[i];
			  
			  // project both shapes onto the axis
			  Point2D p0 = projectPolyOntoAxis(poly0, axis);
			  Point2D p1 = projectPolyOntoAxis(poly1, axis);
			  
			  double ol = checkOverlap(p0, p1);
			  
			  if(ol == 0) {
				  
				  return;
				  
			  } else if(ol < overlap) {
				  
				  overlap = ol;
				  normal = axis;
				  
			  }
		  
		}
		
		// loop over the axes of the second shape
		for (int i = 0; i < axes1.length; i++) {
			
			  Point2D axis = axes1[i];
			  
			  // project both shapes onto the axis
			  Point2D p0 = projectPolyOntoAxis(poly0, axis);
			  Point2D p1 = projectPolyOntoAxis(poly1, axis);
			  
			  double ol = checkOverlap(p0, p1);
			  
			  if(ol == 0) {
				  
				  return;
				  
			  } else if(ol < overlap) {
				  
				  overlap = ol;
				  normal = axis;
				  
			  }
		  
		}
		
		
		  
		// if we get here then we know that every axis had overlap on it
		// so we can guarantee an intersection. 
		//We need to pass on the smallest axis and overlap value to deal with the collision
		
		//Point normal vector from b0 to b1
		float centroidX0 = poly0.getCentroidX();
		float centroidX1 = poly1.getCentroidX();
		
		float centroidY0 = poly0.getCentroidY();
		float centroidY1 = poly1.getCentroidY();
		
		float relativeX = centroidX1 - centroidX0;
		float relativeY = centroidY1 - centroidY0;
		
		double separationVec = normal.getX() * relativeX + normal.getY() * relativeY;
		
		 // if the separation vector is in the opposite direction of the center to center vector then flip it around by negating it
		if(separationVec < 0) {
			
			normal = new Point2D(-normal.getX(), -normal.getY());
			
		}
		  
		manifold.setCollided(true);
		manifold.setAxis(normal);
		manifold.setOverlap(overlap);
		  
		b0.getObject().collisionEvent(manifold);
		b1.getObject().collisionEvent(manifold);
		  
	}
	
	public void evaluateCollisionCircleCircle(Manifold manifold, Body b0, Body b1) {
		
		Point2D normal;
		double overlap;
		
		Circle circle0 = (Circle) b0.getShape();
		Circle circle1 = (Circle) b1.getShape();
		
		float b0PosX = b0.getX();
		float b0PosY = b0.getY();
		float b0Radius = circle0.getRadius();
		
		float b1PosX = b1.getX();
		float b1PosY = b1.getY();
		float b1Radius = circle1.getRadius();
		
		float xDistance = b1PosX - b0PosX;
		float yDistance = b1PosY - b0PosY;
		
		float r = b1Radius + b0Radius;
		float rSquared = r * r;
		
		float distanceSquared = xDistance * xDistance + yDistance * yDistance;
		
		if(distanceSquared > rSquared) {
			
			return;
			
		}
		
		double distance = Math.sqrt(distanceSquared);
		
		if(distance != 0) {
			
			overlap = r - distance;
			
			double xNormal = xDistance / distance;
			double yNormal = yDistance / distance;
			
			normal = new Point2D(xNormal, yNormal);
			
		} else {
			
			//The circles are in the sae position so return a random manifold
			overlap = b0Radius;
			normal = new Point2D(1, 0);
			
		}
		
		float centroidX0 = b0.getX();
		float centroidX1 = b1.getX();
		
		float centroidY0 = b0.getY();
		float centroidY1 = b1.getY();
		
		float relativeX = centroidX1 - centroidX0;
		float relativeY = centroidY1 - centroidY0;
		
		double separationVec = normal.getX() * relativeX + normal.getY() * relativeY;
		
		 // if the separation vector is in the opposite direction of the center to center vector then flip it around by negating it
		if(separationVec < 0) {
			
			normal = new Point2D(-normal.getX(), -normal.getY());
			
		}
		
		  manifold.setCollided(true);
		  manifold.setAxis(normal);
		  manifold.setOverlap(overlap);
		  
		  b0.getObject().collisionEvent(manifold);
		  b1.getObject().collisionEvent(manifold);
		
		
	}
	
	public void evaluateCollisionPolygonCircle(Manifold manifold, Body b0, Body b1) {
		
		Point2D normal = null;
		double overlap = Double.MAX_VALUE;
		
		Polygon poly = (Polygon) b0.getShape();
		Circle circle = (Circle) b1.getShape();
		
		Point2D[] axes = poly.getAxes();
		
		// loop over the axes of the poly shape
		for (int i = 0; i < axes.length; i++) {
			
			  Point2D axis = axes[i];
			  
			  // project both shapes onto the axis
			  Point2D p0 = projectPolyOntoAxis(poly, axis);
			  Point2D p1 = projectCircleOntoAxis(b1, circle, axis);
			  
			  double ol = checkOverlap(p0, p1);
			  
			  if(ol == 0) {
				  
				  return;
				  
			  } else if(ol < overlap) {
				  
				  overlap = ol;
				  normal = axis;
				  
			  }
		  
		}
		
		
		Point2D closestVertexAxis =  axisToClosestVertex(poly, b1);
	
		
		// project both shapes onto the axis
		Point2D p0 = projectPolyOntoAxis(poly, closestVertexAxis);
		Point2D p1 = projectCircleOntoAxis(b1, circle, closestVertexAxis);
		  
		double ol = checkOverlap(p0, p1);
		  
		if(ol == 0) {
			  
			return;
			  
		} else if(ol < overlap) {
			  
			overlap = ol;
			normal = closestVertexAxis;
			  
		}
		  
		float centroidX0 = poly.getCentroidX();
		float centroidX1 = circle.getCentroidX();
		
		float centroidY0 = poly.getCentroidY();
		float centroidY1 = circle.getCentroidY();
		
		float relativeX = centroidX1 - centroidX0;
		float relativeY = centroidY1 - centroidY0;
		
		double separationVec = normal.getX() * relativeX + normal.getY() * relativeY;
		
		 // if the separation vector is in the opposite direction of the center to center vector then flip it around by negating it
		if(separationVec < 0) {
			
			normal = new Point2D(-normal.getX(), -normal.getY());
			
		}
		
		
		// if we get here then we know that every axis had overlap on it
		// so we can guarantee an intersection. 
		//We need to pass on the smallest axis and overlap value to deal with the collision
		  
		manifold.setCollided(true);
		manifold.setAxis(normal);
		manifold.setOverlap(overlap);
		  
		b0.getObject().collisionEvent(manifold);
		b1.getObject().collisionEvent(manifold);
		
	}
	
	public void evaluateCollisionPolygonLine(Manifold manifold, Body b0, Body b1) {
		
		//If every axis overlaps, we know there has been a collision so we need to return 
		//the axis that has the smallest overlap as this is the point where the two objects collided
		
		Point2D normal = null;
		double overlap = Double.MAX_VALUE;
		
		Polygon poly = (Polygon) b0.getShape();
		Line line = (Line) b1.getShape();
		
		Point2D[] lineAxes = line.getAxes();
		Point2D[] polyAxes = poly.getAxes();
		
		for (int i = 0; i < lineAxes.length; i++) {
			
			  Point2D axis = lineAxes[i];
			  
			// project both shapes onto the line axis
			Point2D p0 = projectPolyOntoAxis(poly, axis);
			Point2D p1 = projectLineOntoAxis(line, axis);
				  
			double ol = checkOverlap(p0, p1);
			  
			if(ol == 0) {
				  
				return;
				  
			} else if(ol < overlap) {
				  
				overlap = ol;
				normal = axis;
				  
			}
		}
			  
		// loop over the axes of the poly shape
		for (int i = 0; i < polyAxes.length; i++) {
			
			  Point2D axis = polyAxes[i];
			  
			  // project both shapes onto the axis
			  Point2D p0 = projectPolyOntoAxis(poly, axis);
			  Point2D p1 = projectLineOntoAxis(line, axis);
			  
			  double olp = checkOverlap(p0, p1);
			  
			  if(olp == 0) {
				  
				  return;
				  
			  } else if(olp < overlap) {
				  
				  overlap = olp;
				  normal = axis;
				  
			  }
		  
		}
		 
		//Ensure the line axis is pointing towards the shape
		float centroidX0 = poly.getCentroidX();
		float centroidY0 = poly.getCentroidY();
		
		double centroidX1 = line.getCentroidX();
		double centroidY1 = line.getCentroidY();
		
		double relativeX = centroidX1 - centroidX0;
		double relativeY = centroidY1 - centroidY0;
		
		double separationVec = normal.getX() * relativeX + normal.getY() * relativeY;
		
		 // if the sit is in the opposite direction then flip it around by negating it
		if(separationVec < 0) {
			
			normal = new Point2D(-normal.getX(), -normal.getY());
			
		}
		
	  
		manifold.setCollided(true);
		manifold.setAxis(normal);
		manifold.setOverlap(overlap);
		  
		b0.getObject().collisionEvent(manifold);
		b1.getObject().collisionEvent(manifold);
		  
	}
	
	public void evaluateCollisionCircleLine(Manifold manifold, Body b0, Body b1) {
		
		//If every axis overlaps, we know there has been a collision so we need to return 
		//the axis that has the smallest overlap as this is the point where the two objects collided
		
		Point2D normal = null;
		double overlap = Double.MAX_VALUE;
		
		Circle circle = (Circle) b0.getShape();
		Line line = (Line) b1.getShape();
		
		Point2D[] lineAxes = line.getAxes();
		
		for (int i = 0; i < lineAxes.length; i++) {
			
			  Point2D axis = lineAxes[i];
				  
			  // project both shapes onto the line axis
			  Point2D p0 = projectCircleOntoAxis(b0, circle, axis);
			  Point2D p1 = projectLineOntoAxis(line, axis);
				  
			  double ol = checkOverlap(p0, p1);
			  
			  if(ol == 0) {
				  
				  return;
				  
			  } else if(ol < overlap) {
				  
				  overlap = ol;
				  normal = axis;
				  
			  }
		}
		
		Point2D closestVertexAxis =  axisToClosestVertex(line, b0);
	
		
		// project both shapes onto the axis
		Point2D p0 = projectLineOntoAxis(line, closestVertexAxis);
		Point2D p1 = projectCircleOntoAxis(b1, circle, closestVertexAxis);
		  
		double ol = checkOverlap(p0, p1);
		  
		if(ol == 0) {
			  
			return;
			  
		} else if(ol < overlap) {
			  
			overlap = ol;
			normal = closestVertexAxis;
			  
		}
		 
		//Ensure the line axis is pointing towards the shape
		float centroidX0 = circle.getCentroidX();
		float centroidY0 = circle.getCentroidY();
		
		double centroidX1 = line.getCentroidX();
		double centroidY1 = line.getCentroidY();
		
		double relativeX = centroidX1 - centroidX0;
		double relativeY = centroidY1 - centroidY0;
		
		double separationVec = normal.getX() * relativeX + normal.getY() * relativeY;
		
		 // if the sit is in the opposite direction then flip it around by negating it
		if(separationVec < 0) {
			
			normal = new Point2D(-normal.getX(), -normal.getY());
			
		}
		
	  
	    manifold.setCollided(true);
	    manifold.setAxis(normal);
	    manifold.setOverlap(overlap);
	  
	    b0.getObject().collisionEvent(manifold);
	    b1.getObject().collisionEvent(manifold);
		  
	}
	
	public void evaluateCollisionLineLine(Manifold manifold, Body b0, Body b1) {
		
		//If every axis overlaps, we know there has been a collision so we need to return 
		//the axis that has the smallest overlap as this is the point where the two objects collided
		double overlap = Double.MAX_VALUE;
		Point2D normal = null;
		
		Line line0 = (Line) b0.getShape();
		Line line1 = (Line) b1.getShape();
		
		//We need to retrieve the normal axis of both shapes
		Point2D[] axes0 = line0.getAxes();
		Point2D[] axes1 = line1.getAxes();
		
		// loop over the axes of the first shape
		for (int i = 0; i < axes0.length; i++) {
			
			  Point2D axis = axes0[i];
			  
			  // project both shapes onto the axis
			  Point2D p0 = projectLineOntoAxis(line0, axis);
			  Point2D p1 = projectLineOntoAxis(line1, axis);
			  
			  double ol = checkOverlap(p0, p1);
			  
			  if(ol == 0) {
				  
				  return;
				  
			  } else if(ol < overlap) {
				  
				  overlap = ol;
				  normal = axis;
				  
			  }
		  
		}
		
		// loop over the axes of the second shape
		for (int i = 0; i < axes1.length; i++) {
			
			  Point2D axis = axes1[i];
			  
			  // project both shapes onto the axis
			  Point2D p0 = projectLineOntoAxis(line0, axis);
			  Point2D p1 = projectLineOntoAxis(line1, axis);
			  
			  double ol = checkOverlap(p0, p1);
			  
			  if(ol == 0) {
				  
				  return;
				  
			  } else if(ol < overlap) {
				  
				  overlap = ol;
				  normal = axis;
				  
			  }
		  
		}
		
		
		  
		// if we get here then we know that every axis had overlap on it
		// so we can guarantee an intersection. 
		//We need to pass on the smallest axis and overlap value to deal with the collision
		
		//Point normal vector from b0 to b1
		float centroidX0 = line0.getCentroidX();
		float centroidX1 = line1.getCentroidX();
		
		float centroidY0 = line0.getCentroidY();
		float centroidY1 = line1.getCentroidY();
		
		float relativeX = centroidX1 - centroidX0;
		float relativeY = centroidY1 - centroidY0;
		
		double separationVec = normal.getX() * relativeX + normal.getY() * relativeY;
		
		 // if the separation vector is in the opposite direction of the center to center vector then flip it around by negating it
		if(separationVec < 0) {
			
			normal = new Point2D(-normal.getX(), -normal.getY());
			
		}
		  
		manifold.setCollided(true);
		manifold.setAxis(normal);
		manifold.setOverlap(overlap);
		  
		b0.getObject().collisionEvent(manifold);
		b1.getObject().collisionEvent(manifold);
		  
	}
	
	//Projects the shape onto the normal axis. This is like how a sunray casts a 2D projection of a shadow of a 3D object. A 1D projection of the 2D object is cast
	public Point2D projectPolyOntoAxis(Polygon poly, Point2D axis) {
		
		double min = axis.dotProduct(poly.getVertices()[0]);
		double max = min;
		
		for (int i = 1; i < poly.getVertices().length; i++) {
			
			// The axis must be normalized to get accurate projections
			double p = axis.dotProduct(poly.getVertices()[i]);
			
			if (p < min) {
				
				min = p;
				
			} else if (p > max) {
				
				max = p;
			}
		}
		
		Point2D projection = new Point2D(min, max);
		
		return projection;
		
	}
	
	//Projects the shape onto the normal axis. This is like how a sunray casts a 2D projection of a shadow of a 3D object. A 1D projection of the 2D object is cast
	private Point2D projectCircleOntoAxis(Body body, Circle circle, Point2D axis) {
		
		Point2D circleCenter = new Point2D(body.getX(), body.getY());
		float radius = circle.getRadius();
		
		double min = axis.dotProduct(circleCenter) - radius;
		double max = axis.dotProduct(circleCenter) + radius;
		
		Point2D projection = new Point2D(min, max);
		
		return projection;
		
	}
	
	//Projects the shape onto the normal axis. This is like how a sunray casts a 2D projection of a shadow of a 3D object. A 1D projection of the 2D object is cast
	public Point2D projectLineOntoAxis(Line line, Point2D axis) {
		
		double min = axis.dotProduct(line.getVertices()[0]);
		double max = min;
			
		// The axis must be normalized to get accurate projections
		double p = axis.dotProduct(line.getVertices()[1]);
		
		if (p < min) {
			
			min = p;
			
		} else if (p > max) {
			
			max = p;
		}
		
		
		Point2D projection = new Point2D(min, max);
		
		return projection;
		
	}
	
	public double checkOverlap(Point2D p0, Point2D p1) {
		
		  // do the projections overlap?
		  if (!overlap(p0, p1)) {
		    // then we can guarantee that the shapes do not overlap
		    return 0;
		    
		  } else {
			  
			  //get the overlap value and check to see if it is the smallest. 
			  //If so set the new overlap value and the smallest axis
			  double ol = getOverlap(p0, p1);
			  
			  //This checks to make sure that one shape isn't inside another. If it is then it will remove the shape from the other
			  if(contains(p0, p1)) {
				  
				  double mins = Math.abs(Math.min(p0.getX(), p0.getY()) - Math.min(p1.getX(), p1.getY()));
				  double maxs = Math.abs(Math.max(p0.getX(), p0.getY()) - Math.max(p1.getX(), p1.getY()));
				  
				  if(mins < maxs) {
					  
					  ol += mins;
					  
				  } else {
					  
					  ol += maxs;
					  
				  }
				  
			  }
			  
			  return ol;
		  }
		
	}
		
	private boolean overlap(Point2D p1, Point2D p2) {
		
		return !(p1.getX() > p2.getY() || p2.getX() > p1.getY());
		
	}
	
	private double getOverlap(Point2D p1, Point2D p2) {
		
		return Math.min(p1.getY(), p2.getY()) - Math.max(p1.getX(), p2.getX());
		
	}
	
	private boolean contains(Point2D p1, Point2D p2) {
		
		return (p1.getX() > p2.getX() && p1.getY() < p2.getY()) || (p2.getX() > p1.getX() && p2.getY() < p1.getY());
		
	}
	
	public Point2D axisToClosestVertex(Shape shape, Body circle) {
		
		//Now we need to project both shapes onto the axes that runs from the vertex of the polygon closest to the center point of the circle
		Point2D[] vertices = shape.getVertices();
		
		Point2D circleCenter = new Point2D(circle.getX(), circle.getY());
		Point2D closestVertex = vertices[0];
		Point2D distanceVec = circleCenter.subtract(closestVertex);
		double distanceSquared = distanceVec.dotProduct(distanceVec);
		
		//Find which vertex is closest
		for(int i = 1; i < vertices.length; i++) {
			
			Point2D nextVertex = vertices[i];
			Point2D nextDistanceVec = circleCenter.subtract(nextVertex);
			double nextDistanceSquared = nextDistanceVec.dotProduct(nextDistanceVec);
			
			if(nextDistanceSquared < distanceSquared) {
				
				distanceSquared = nextDistanceSquared;
				closestVertex = nextVertex;
				
			}
			
		}
			
		//Get the normalised version of the axis
		Point2D axis = circleCenter.subtract(closestVertex);
		axis = axis.normalize();
		
		return axis;
		
	}
}
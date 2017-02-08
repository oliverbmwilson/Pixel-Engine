package Core.physics;

import Core.GameContainer;
import Core.Renderer;
import Core.FX.ShadowType;
import Core.components.GameObject;
import javafx.geometry.Point2D;

public class Polygon extends Shape {
	
	//A component class of an object that is used in calculating collision detection
	private int verticesCount;
	
	//Points need to be arranged in the array going in a clockwise order. The shape the points form must be convex
	//From the points information, we need to find the vectors of the edges and the vectors of the normal axes 
	//(the normal is an axis that is perpendicular to the edge in question)
	public Polygon() {
		
	}
	
	public Polygon(Point2D[] vertices) {
		
		setVertices(vertices);
	}
	
	public Polygon(float width, float height) {
		
		float hw = width / 2;
		float hh = height /2;
		
		verticesCount = 4;
		vertices = new Point2D[4];
		axes = new Point2D[4];
		
		vertices[0] = new Point2D(-hw, -hh);
		vertices[1] = new Point2D(hw, -hh);
		vertices[2] = new Point2D(hw, hh);
		vertices[3] = new Point2D(-hw, hh);
		axes[0] = new Point2D(0.0f, -1.0f);
		axes[1] = new Point2D(1.0f, 0.0f);
		axes[2] = new Point2D(0.0f, 1.0f);
		axes[3] = new Point2D(-1.0f, 0.0f);
		
	}
	
	//Algorithm I took from Randy Gaul's code
	public void setVertices(Point2D[] vertices) {
		
		this.verticesCount = vertices.length;
		this.vertices = new Point2D[verticesCount];
		
		// Find the right most point on the Poly
		int rightMost = 0;
		double  highestXCoord = vertices[0].getX();
		
		for (int i = 1; i < vertices.length; ++i) {
			
			double x = vertices[i].getX();

			if (x > highestXCoord) {
				
				highestXCoord = x;
				rightMost = i;
				
			}
			
			// If matching x then take farthest negative y
			else if (x == highestXCoord) {
				
				if (vertices[i].getY() < vertices[rightMost].getY()) {
					
					rightMost = i;
					
				}
			}
		}

		int[] temp = new int[vertices.length];
		int outCount = 0;
		int indexTemp = rightMost;

		while(true) {
			
			temp[outCount] = indexTemp;

			// Search for next index that wraps around the hull
			// by computing cross products to find the most counter-clockwise
			// vertex in the set, given the previos hull index
			int nextTempIndex = 0;
			
			for (int i = 1; i < vertices.length; ++i)
			{
				// Skip if same coordinate as we need three unique
				// points in the set to perform a cross product
				if (nextTempIndex == indexTemp)
				{
					nextTempIndex = i;
					continue;
				}

				// Cross every set of three unique vertices
				// Record each counter clockwise third vertex and add
				// to the output hull
				Point2D e1 = vertices[nextTempIndex].subtract( vertices[temp[outCount]] );
				Point2D e2 = vertices[i].subtract( vertices[temp[outCount]] );
				double c = e1.getX() * e2.getY() - e1.getY() * e2.getX();
				
				if (c < 0.0f){
					
					nextTempIndex = i;
				}

				// Cross product is zero then e vectors are on same line
				// therefore want to record vertex farthest along that line
				if (c == 0.0f && e2.magnitude() > e1.magnitude()){
					
					nextTempIndex = i;
				}
			}

			++outCount;
			indexTemp = nextTempIndex;

			// Conclude algorithm upon wrap-around
			if (nextTempIndex == rightMost){
				
				break;
				
			}
		}

		// Copy vertices into shape's vertices
		for (int i = 0; i < verticesCount; ++i){
			
			this.vertices[i] = vertices[temp[i]];
			
		}

		// Compute face normals
		
		setAxes();
		
	}
	
	
	public void setOrderedVertices(Point2D[] vertices){
		
		this.verticesCount = vertices.length;
		this.vertices = vertices;
		setAxes();
		
	}
	
	public void setAxes() {
		
	axes = new Point2D[verticesCount];
		
		for (int i = 0; i < verticesCount; ++i){
			
			Point2D vertex1 = this.vertices[i];
			Point2D vertex2 = this.vertices[i + 1 == vertices.length ? 0 : i + 1];
			Point2D edge = vertex2.subtract(vertex1);
			
			double edgeX = edge.getX();
			double edgeY = edge.getY();
			
			Point2D axis = new Point2D(edgeY, edgeX * -1);
			axis = axis.normalize();
			axes[i] = axis;
		}
		
	}
	
	@Override
	public void initialise() {
		
		computeMass(1.0f);
		
	}

	@Override
	public Shape clone() {
		
		Polygon poly = new Polygon();
		poly.setOrderedVertices(this.vertices);

		return poly;
	}

	public void update() {
		
		//Each update, a collider is calculated for the object and added to the physics arraylist
		//This is the same process as the initialisation. It needs to be reclaculated as the object would have moved
		double changeX = body.x - body.previousX;
		double changeY = body.y - body.previousY;
		
		body.previousX = body.x;
		body.previousY = body.y;
		
		for(int i = 0; i < vertices.length; i++) {
			
			vertices[i] = vertices[i].add(changeX, changeY); 
			
		}
		
		centroidX += changeX;
		centroidY += changeY;
		
	}

	public void render(Renderer r) {
		
		//Draw collision bounding polygon if debug mode is on
			
		r.drawPolygon(vertices, 0xffffff00, ShadowType.NONE);
		
	}

	@Override
	public void computeMass(float density) {
		
		float areaCounter = 0;
		float centroidXCounter = 0;
		float centroidYCounter = 0;
		
		for(int i = 0; i < verticesCount; i++) {
			
			double x0 = vertices[i].getX();
			double y0 = vertices[i].getY();
			double x1 = vertices[i + 1 == verticesCount ? 0 : i + 1].getX();
			double y1 = vertices[i + 1 == verticesCount ? 0 : i + 1].getY();
			
			double centroidVariable = x0 * y1 - x1 * y0;
			areaCounter += x0 * y1 - x1 * y0;
			centroidXCounter += (x0 + x1) * centroidVariable;
			centroidYCounter += (y0 + y1) * centroidVariable;
			
		}
		
		float area = areaCounter / 2;
		centroidX = 1 / (6 * area) * centroidXCounter;
		centroidY = 1 / (6 * area) * centroidYCounter;
		
		body.setMass(area * density);
		body.setInverseMass(1 / body.getMass());
		
	}

	@Override
	public Type getType() {
		
		return Type.Polygon;
	}

	@Override
	public void setOrient(float radians) {
		// TODO Auto-generated method stub
		
	}

	

}


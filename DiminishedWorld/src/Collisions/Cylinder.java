package Collisions;

import org.lwjgl.util.vector.Vector3f;

public class Cylinder {
	
	private int radius, height; 
	
	private Vector3f position;
	
	public Cylinder() {
		this.radius = 0;
		this.height = 0;
		
		this.position.x = 0;
		this.position.y = 0;
		this.position.z = 0;
	}
	
	public Cylinder(int Nradius, int Nheight, Vector3f Nposition) {
		this.radius = Nradius;
		this.height = Nheight;
		
		this.position = Nposition;
	}
	
	public void setPos(Vector3f Npos) {
		this.position = Npos;
	}
	
	public void setRadius(int Nradius) {
		this.radius = Nradius;
	}
	
	public void setHeight(int Nheight) {
		this.height = Nheight;
	}
	
	public boolean checkCollisionsCylinder(Cylinder body) {
		return false;
	}
	
	public boolean checkCollisonsBasic(Basic body) {
		return false;
	}
	
}

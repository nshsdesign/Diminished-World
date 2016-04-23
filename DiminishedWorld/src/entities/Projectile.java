package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Projectile extends Entity{

	private List<Enemy> enemies;
	private float dx, dy, dz;
	private boolean shouldRemove;
	
	public Projectile(List<Enemy> enemies, TexturedModel model, Vector3f position, float[] infoForRay){
		super(model, new Vector3f(position), 0, 0, 0, 1, false, "projectile");
		this.enemies = enemies;
		this.dx = infoForRay[0];
		this.dy = infoForRay[1];
		this.dz = infoForRay[2];
	}
	
	public void update(){
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
		
		checkAndResolveCollisions();
	}
	
	public void checkAndResolveCollisions(){
		for(Enemy e: enemies){
			if(this.boundingBox.intersects(e.getBoundingBox())){
				//System.out.println("HIT");
				e.setShrinking(true);
				this.shouldRemove = true;
			}
		}
	}	
	
	public boolean isShouldRemove() {
		return shouldRemove;
	}
	
	
}

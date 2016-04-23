package picking;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Entity;
import entities.Player;
import objConverter.ModelData;
import toolbox.Maths;

public class BoundingBox{

	private ModelData data;
	private Matrix4f modelMatrix = new Matrix4f();
	private Vector3f scale = new Vector3f();
	private Vector3f pos = new Vector3f();
	private boolean dirty = true;
	private Entity e;
	private AABB aabb;
	private float softScale;

	public BoundingBox(Entity e) {
		this.e = e;
		this.data = e.getModel().getRawModel().getModelData();
		this.aabb = AABB.createAABBFromData(data);
		this.pos = e.getPosition();
		this.softScale = e.getScale();
	}
	
	public BoundingBox(Player p, Vector3f min, Vector3f max) {
		this.e = null;
		this.data = null;
		this.aabb = new AABB(min,max);
		this.pos = p.getPosition();
		this.softScale = 1;
	}

	public Vector3f getSizes() {
		if (needsRecalculating()) {
			recalculate();
		}
		return scale;
	}

	public Matrix4f getModelMatrix() {
		if (needsRecalculating()) {
			recalculate();
		}
		return modelMatrix;
	}

	public float getHeight() {
		return (aabb.getScale().y * 0.5f + aabb.getOffset().y) * e.getScale();
	}

	public float getMaxWidth() {
		return aabb.getMaxWidth() * e.getScale();
	}

	private boolean needsRecalculating() {
		return dirty;
	}

	private void recalculate() {
		scale.set(aabb.getScale());
		scale.scale(e.getScale());
		Vector4f position = Matrix4f.transform(e.getModelMatrix(), aabb.getOffset(), null);
//		scale.set(e.getScale(), e.getScale(), e.getScale());
		Maths.updateModelMatrix(modelMatrix, new Vector3f(position), e.getRotX(), e.getRotY(),
				e.getRotZ(), scale);
		dirty = false;
	}
	
	public AABB getAABB(){
		return aabb;
	}
	
	public Vector3f getMin(){
		return Vector3f.add(new Vector3f(aabb.getMin().x*softScale, aabb.getMin().y*softScale, aabb.getMin().z*softScale), pos, null);
	}
	
	public Vector3f getMax(){
		return Vector3f.add(new Vector3f(aabb.getMax().x*softScale, aabb.getMax().y*softScale, aabb.getMax().z*softScale), pos, null);
	}
	
	public boolean contains(Vector3f point){
		if(getMax().x>=point.x && getMax().y>=point.y && getMax().z>=point.z){
			if(getMin().x<=point.x && getMin().y<=point.y && getMin().z<=point.z){
				return true;
			}
		}
		return false;
	}
	
	public boolean intersects(BoundingBox b){
		if(this.contains(b.getMin()) || this.contains(b.getMax())){
//			System.out.println(getMin() + " || " + getMax());
//			System.out.println(b.getMin() + " || " + b.getMax());
			return true;
		}
		if(b.contains(this.getMin()) || b.contains(this.getMax())){
//			System.out.println(getMin() + " || " + getMax());
//			System.out.println(b.getMin() + " || " + b.getMax());
			return true;
		}
		return false;
	}
	
	public void update(Player p){
		this.pos = p.getPosition();
	}
	
	public void update(Entity e){
		this.pos = e.getPosition();
		this.softScale = e.getScale();
	}

}

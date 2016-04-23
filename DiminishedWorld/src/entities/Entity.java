package entities;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import picking.AABB;
import picking.BoundingBox;
import renderEngine.Loader;
import textures.ModelTexture;
import toolbox.Maths;

public class Entity {

	private TexturedModel model;
	protected Vector3f position;
	protected float rotX, rotY, rotZ;
	protected float scale;
	private float minScale; //ST
	private float maxScale; //ST
	private boolean isShrinking; //ST
	private boolean shrinkOrGrowNow; //ST
	private boolean canShrink; //ST
	private String name; //ST may not be needed
	private float maxY;
	protected  BoundingBox boundingBox;
	private boolean isStatic;
	private Matrix4f modelMatrix = new Matrix4f();
	
	private int textureIndex = 0;

	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, boolean isAbleToShrink, String name) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.maxScale = scale;
		this.minScale = scale/80f; //ST
		this.isShrinking = false; //ST 
		this.shrinkOrGrowNow = false; //ST
		this.canShrink = isAbleToShrink; //ST
		this.name = name; //ST
		maxY = position.y;
		
		this.boundingBox = new BoundingBox(this);
	}
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, boolean isAbleToShrink, String name, float minScaleAmount) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.maxScale = scale;
		this.minScale = scale/minScaleAmount; //ST
		this.isShrinking = false; //ST 
		this.shrinkOrGrowNow = false; //ST
		this.canShrink = isAbleToShrink; //ST
		this.name = name; //ST
		maxY = position.y;
		
		this.boundingBox = new BoundingBox(this);
	}
	
	public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, boolean isAbleToShrink, String name, float minScaleAmount) {
		this.textureIndex = index;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.maxScale = scale;
		this.minScale = scale/minScaleAmount; //ST
		this.isShrinking = false; //ST 
		this.shrinkOrGrowNow = false; //ST
		this.canShrink = isAbleToShrink; //ST
		this.name = name; //ST
		maxY = position.y;
		
		this.boundingBox = new BoundingBox(this);
	}
	
	public float getX(){
		return this.position.x;
	}
	
	public float getY(){
		return this.position.y;
	}
	
	public float getMaxY(){
		return maxY;
	}
	
	public float getZ(){
		return this.position.z;
	}
	
	public float getMinScale() { //ST
		return minScale;
	}

	public void setMinScale(float newMin) { //ST
		this.minScale = newMin;
	}
	
	public float getMaxScale() { //ST
		return maxScale;
	}

	public void setMaxScale(float newMax) { //ST
		this.minScale = newMax;
	}
	
	public boolean getIsShrinking(){
		return isShrinking;		
	}
	
	public void modIsShrinking(){
		if(this.isShrinking == true){
			isShrinking = false;
		}else{
			isShrinking = true;
		}
	}
	
	public void modIsShrinking(boolean is){
		isShrinking = is;
	}
	
	public void startShrinking(){
		this.shrinkOrGrowNow = true;
	}
	
	public void endShrinking(){
		this.shrinkOrGrowNow = false;
	}
	
	public void modIsShrinkable(){
		if(this.canShrink == true){
			canShrink = false;
		}else{
			canShrink = true;
		}
	}
	
	public boolean accCanShrink(){
		return canShrink;
	}
	
	public void modName(String newName){
		this.name = newName;
	}
	
	public String accName(){
		return name;
	}

	public Entity(Loader loader, String name, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this(loader, name, 0, position, rotX, rotY, rotZ, scale);
	}
	
	public Entity(Loader loader, String name, Vector3f position, Vector3f rot, float scale) {
		this(loader, name, 0, position, rot.x, rot.y, rot.z, scale);
	}

	public Entity(Loader loader, String name, int index, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		this.name = name;
		this.textureIndex = index;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;

		RawModel rawModel = OBJFileLoader.loadOBJ(name, loader);
		ModelTexture tex = new ModelTexture(loader.loadTexture("textureFiles", name));
		this.model = new TexturedModel(rawModel, tex);

		this.boundingBox = new BoundingBox(this);
		
		//modelMatrix = Maths.createTransformationMatrix(position, rotX, rotY, rotZ, scale);
	}

	public Entity(Entity e) {
		this.name = e.getName();
		this.position = e.getPosition();
		this.rotX = e.getRotX();
		this.rotY = e.getRotY();
		this.rotZ = e.getRotZ();
		this.scale = e.getScale();
		this.boundingBox = e.getBoundingBox();
		this.model = e.getModel();
	}
	
	public Entity() {
		
	}

	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}

	public float getTextureYOffset() {
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public boolean getIsStatic() {
		return isStatic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setModel(Loader loader, String name) {
		RawModel rawModel = OBJFileLoader.loadOBJ(name, loader);
		ModelTexture tex = new ModelTexture(loader.loadTexture("textureFiles", name));
		this.model = new TexturedModel(rawModel, tex);
		this.name = name;
	}

	public AABB getAABB() {
		return boundingBox.getAABB();
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public Matrix4f getModelMatrix(){
		Maths.updateTransformationMatrix(modelMatrix, position.x, position.y, position.z, rotX, rotY, rotZ, scale);
		return modelMatrix;
	}

	public void update() {
		boundingBox.update(this);
	}
	
	public void isAbleToShrink(boolean b){
		canShrink = b;
	}
	
	public float[] accInfoForRayGun(){
		float[] infoForRay = new float[6];
		float yaw = -90 - this.getRotY();
		float pitch = this.getRotZ();
		infoForRay[0] = (float) (Math.sin(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90))); //dx
	    infoForRay[1] = (float) (Math.sin(Math.toRadians(pitch))); // correct dy
		infoForRay[2] = (float) (Math.cos(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90))); //dz
		
		return infoForRay;
	}
	
//		public void updateModelMatrix(Vector3f up, Vector3f forward) {
//			Matrix4f rotation = Maths.getRotationMatrix(up, forward);
//			modelMatrix.setIdentity();
//			Matrix4f.translate(position, modelMatrix, modelMatrix);
//			Matrix4f.mul(modelMatrix, rotation, modelMatrix);
//			//Matrix4f.scale(new Vector3f(scale, scale, scale), modelMatrix, modelMatrix);
//		}
//	
//		public void update(){
//			Vector3f up = new Vector3f(0,1,0);
//			Vector3f forward = new Vector3f(1,0,0);
//			updateModelMatrix(up, forward);
//		}
		
}

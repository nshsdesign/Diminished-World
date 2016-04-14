package entities;

import models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

public class Entity {

	private TexturedModel model;
	protected Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private float minScale; //ST
	private float maxScale; //ST
	private boolean isShrinking; //ST
	private boolean shrinkOrGrowNow; //ST
	private boolean canShrink; //ST
	private String name; //ST may not be needed
	private float maxY;
	
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
	}
	
	public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, boolean isAbleToShrink, String name) {
		this.textureIndex = index;
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
	}
	
	public float getTextureXOffset(){
		int column = textureIndex%model.getTexture().getNumberOfRows();
		return (float)column/(float)model.getTexture().getNumberOfRows();
	}
	
	public float getTextureYOffset(){
		int row = textureIndex/model.getTexture().getNumberOfRows();
		return (float)row/(float)model.getTexture().getNumberOfRows();
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

}

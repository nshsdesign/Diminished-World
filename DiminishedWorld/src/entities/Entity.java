package entities;

import models.TexturedModel;
import java.lang.Math;

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
	
	public void setMaxY(double d){
		this.maxY = (float)d;
	}
	public void setPositionMoving(float goX, float goY, float goZ){
		boolean allMoved = false;
		boolean xDone = false;
		boolean yDone = false;
		boolean zDone = false;
		while(allMoved==false){
			if((xDone==true) && (yDone==true) && (zDone==true)){
				allMoved = true;
			}
			if(Math.round(this.position.x) == Math.round(goX)){
				xDone = true;
			}else{
				if(this.position.x < goX){
					this.position.x = this.position.x+0.5f;
				}else if(this.position.x > goX){
					this.position.x = this.position.x-0.5f;
				}
			}
			if(Math.round(this.position.y) == Math.round(goY)){
				yDone = true;
			}else{
				if(this.position.y < goY){
					this.position.y = this.position.y+0.5f;
				}else if(this.position.y > goY){
					this.position.y = this.position.y-0.5f;
				}
			}
			if(Math.round(this.position.z) == Math.round(goZ)){
				zDone = true;
			}else{
				if(this.position.z < goZ){
					this.position.z = this.position.z+0.5f;
				}else if(this.position.z > goZ){
					this.position.z = this.position.z-0.5f;
				}
			}

		}
	}
	public void setRotXMoving(float rotToX){
		System.out.println("stuck 2");
		boolean finished = false;
		while(finished == false){
			if(this.rotX<rotToX){
				this.rotX = this.rotX+0.5f;
			}else if(this.rotX>rotToX){
				this.rotX = this.rotX-0.5f;
			}
			if(Math.round(this.rotX) == rotToX){
				finished = true;
			}
		}
	}
	public void setRotYMoving(float rotToY){
		System.out.println("stuck 3");
		boolean finished = false;
		while(finished == false){
			if(this.rotY<rotToY){
				this.rotY = this.rotY+0.5f;
			}else if(this.rotY>rotToY){
				this.rotY = this.rotY-0.5f;
			}
			if(Math.round(this.rotY) == rotToY){
				finished = true;
			}
		}
		
	}
	public void setRotZMoving(float rotToZ){
		System.out.println("stuck 4");
		boolean finished = false;
		while(finished == false){
			if(this.rotZ<rotToZ){
				this.rotZ = this.rotZ+0.5f;
			}else if(this.rotZ>rotToZ){
				this.rotZ = this.rotZ-0.5f;
			}
			if(Math.round(this.rotZ) == rotToZ){
				finished = true;
			}
		}
	
	}

}

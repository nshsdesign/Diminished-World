package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity {

	private static final float MOVE_SPEED = 50;
	private static final float SPRINT_MULT = 2;
	private static final float TURN_SPEED = 2f;

	private float speed = MOVE_SPEED;

	float dx = 0;
	float dz = 0;
	float dy = 0;

	private Camera camera;
	
	private boolean hasRayGun;
	
	private int sanity;
	private int sprint;
	private int amountOfDust;

	public Player(Camera camera, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale, false, "player");
		this.camera = camera;
		camera.setPlayer(this);
		sanity = 0;
		sprint = 0;
		amountOfDust = 0;
		hasRayGun = true; //ST FOR TESTS SHOULD START AS FALSE
	}

	public void move() {
		speed = MOVE_SPEED * DisplayManager.getFrameTimeSeconds();
		checkInputs();
	}
	
	public void move(String face) {
		speed = MOVE_SPEED * DisplayManager.getFrameTimeSeconds();
		checkInputs(face);
	}
	
	private void checkInputs() {
		float yaw = -90 - this.getRotY();
		float pitch = this.getRotZ();

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			speed *= SPRINT_MULT;
		}
		if (camera.getType() == Camera.FIRST_PERSON || camera.getType() == Camera.THIRD_PERSON) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				dz = (float) (speed * Math.cos(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dx = (float) (-speed * Math.sin(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dy = (float) (-speed * Math.sin(Math.toRadians(pitch))); // correct
				position.z += dz;
				position.x += dx;
				position.y += dy;
				if (camera.getType() == Camera.THIRD_PERSON) {
					if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
						this.setRotY(this.getRotY() - TURN_SPEED);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
						this.setRotY(this.getRotY() + TURN_SPEED);
					}
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				dz = (float) (-speed * Math.cos(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dx = (float) (speed * Math.sin(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dy = (float) (speed * Math.sin(Math.toRadians(pitch)));
				position.z += dz;
				position.x += dx;
				position.y += dy;
				if (camera.getType() == Camera.THIRD_PERSON) {
					if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
						this.setRotY(this.getRotY() - TURN_SPEED);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
						this.setRotY(this.getRotY() + TURN_SPEED);
					}
				}
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			this.setRotZ(this.getRotZ() + 1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			this.setRotZ(this.getRotZ() - 1f);
		}
	}
	
	private boolean testLeftClick(){
		return Mouse.isButtonDown(0); // 0 is Left, 1 is Right		
	}
	
	private void checkInputs(String face) {
		float yaw = -90 - this.getRotY();
		float pitch = this.getRotZ();

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			speed *= SPRINT_MULT;
		}
		if (camera.getType() == Camera.FIRST_PERSON || camera.getType() == Camera.THIRD_PERSON) {
			if ((Keyboard.isKeyDown(Keyboard.KEY_W)) && (!face.equals("right"))) {
				dz = (float) (speed * Math.cos(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dx = (float) (-speed * Math.sin(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dy = (float) (-speed * Math.sin(Math.toRadians(pitch))); // correct
				position.z += dz;
				position.x += dx;
				position.y += dy;
				if (camera.getType() == Camera.THIRD_PERSON) {
					if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
						this.setRotY(this.getRotY() - TURN_SPEED);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
						this.setRotY(this.getRotY() + TURN_SPEED);
					}
				}
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_S)) && (!face.equals("left"))) {
				dz = (float) (-speed * Math.cos(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dx = (float) (speed * Math.sin(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90)));
				dy = (float) (speed * Math.sin(Math.toRadians(pitch)));
				position.z += dz;
				position.x += dx;
				position.y += dy;
				if (camera.getType() == Camera.THIRD_PERSON) {
					if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
						this.setRotY(this.getRotY() - TURN_SPEED);
					}
					if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
						this.setRotY(this.getRotY() + TURN_SPEED);
					}
				}
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			this.setRotZ(this.getRotZ() + 1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			this.setRotZ(this.getRotZ() - 1f);
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_SPACE)) && (!face.equals("up"))) {
			position.y += 1;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_V)) && (!face.equals("down"))) {
			position.y -= 1;
		}
	}
	
	public float[] accInfoForRayGun(){
		float[] infoForRay = new float[6];
		float yaw = -90 - this.getRotY();
		float pitch = this.getRotZ();
		infoForRay[0] = (float) (-speed * Math.sin(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90))); //dx
	    infoForRay[1] = (float) (-speed * Math.sin(Math.toRadians(pitch))); // correct dy
		infoForRay[2] = (float) (speed * Math.cos(Math.toRadians(yaw)) * Math.sin(Math.toRadians(pitch - 90))); //dz
		
		return infoForRay;
	}

	public void modSanity(int newSanity){
		sanity = newSanity;
	}
	public void modSprint(int newSprint){
		sprint = newSprint;
	}
	public void amountOfDust(int newAmount){
		amountOfDust = newAmount;
	}
	public int accSanity(){
		return sanity;
	}
	public int accSprint(){
		return sprint;
	}
	public int accDustAmount(){
		return amountOfDust;
	}
	public float accX(){
		return this.position.x;
	}
	public float accY(){
		return this.position.y;
	}
	public float accZ(){
		return this.position.z;
	}

}

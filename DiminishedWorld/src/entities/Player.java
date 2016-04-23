package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import engineTester.OpenGLView;
import picking.BoundingBox;
import renderEngine.DisplayManager;

public class Player extends Entity {

	private static final float MOVE_SPEED = 80;
	private static final float SPRINT_MULT = 1.5f;
	private static final float STRAFE_MULT = .8f;
	private static final float GRAVITY = -9.8f/200;
	private static final float JUMP_POWER = 1;
	private static final float TERMINAL_VELOCTIY = -9.8f;
	private static final float Y_OFFSET = 20;

	private float speed = MOVE_SPEED;

	private float dx = 0;
	private float dy = 0;
	private float dz = 0;

	private Camera camera;
	
	private boolean hasRayGun;
	
	private int sanity;
	private int sprint;
	private int amountOfDust;
	private BoundingBox feetHitbox;
	private boolean isOnGround = false;
	
	private Vector3f spawnPoint;
	
	public Player(Camera camera, Vector3f pos, Vector3f rot) {
		super();
		this.camera = camera;
		this.position = pos;
		this.rotX = rot.x;
		this.rotY = rot.y;
		this.rotZ = rot.z;
		camera.setPlayer(this);
		sanity = 0;
		sprint = 0;
		amountOfDust = 0;
		hasRayGun = true; //ST FOR TESTS SHOULD START AS FALSE
		this.feetHitbox = new BoundingBox(this, new Vector3f(-1,-20,-1), new Vector3f(1,-2,1));
		this.boundingBox = new BoundingBox(this, new Vector3f(-1,-2,-1), new Vector3f(1,2,1));
		
		spawnPoint = new Vector3f(pos);
	}

	public void update() {
		feetHitbox.update(this);
		
		speed = MOVE_SPEED * DisplayManager.getFrameTimeSeconds();
		checkInputs();
	}
	
	private void checkInputs() {
		float yaw = camera.getYaw();

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			speed *= SPRINT_MULT;
		}
		if (camera.getType() == Camera.FIRST_PERSON || camera.getType() == Camera.THIRD_PERSON) {
			dx = 0;
			dz = 0;
			if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				dz += (float) (-speed * Math.cos(Math.toRadians(yaw)));
				dx += (float) (speed * Math.sin(Math.toRadians(yaw)));
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S)){
				dz += (float) (speed * Math.cos(Math.toRadians(yaw)));
				dx += (float) (-speed * Math.sin(Math.toRadians(yaw)));
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				dz += (float) (-speed * STRAFE_MULT * Math.cos(Math.toRadians(yaw-90)));
				dx += (float) (speed * STRAFE_MULT * Math.sin(Math.toRadians(yaw-90)));
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				dz += (float) (speed * STRAFE_MULT * Math.cos(Math.toRadians(yaw-90)));
				dx += (float) (-speed * STRAFE_MULT * Math.sin(Math.toRadians(yaw-90)));
			}
			
			dy = calcDY(dy);
			
			position.z += dz;
			position.x += dx;
			position.y += dy;
		}
	}
	
	private float calcDY(float dy){
		
		if(position.y>Y_OFFSET){
			if(dy-TERMINAL_VELOCTIY < TERMINAL_VELOCTIY){
				dy = TERMINAL_VELOCTIY;
			}else if(dy != TERMINAL_VELOCTIY){
				dy += GRAVITY;
			}
		}else{
			dy = 0;
			position = new Vector3f(spawnPoint);
		}
		
		for(Entity e: OpenGLView.entities){
			if(feetHitbox.intersects(e.getBoundingBox())){
				if(e.getBoundingBox().getMax().y > position.y - 10){
					dx=(position.x - e.getPosition().x)/10;
					dz=(position.z - e.getPosition().z)/10;
				}else{
					dy=0;
					position.y = e.getBoundingBox().getMax().y + Y_OFFSET;
					isOnGround = true;
				}
				
			}
		}
		
		if(isOnGround && dy <= 0 && Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			dy = JUMP_POWER;
		}
		
		isOnGround = false;
		
		return dy;
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

	public int getSanity() {
		return sanity;
	}

	public void setSanity(int sanity) {
		this.sanity = sanity;
	}

	public int getAmountOfDust() {
		return amountOfDust;
	}

	public void setAmountOfDust(int amountOfDust) {
		this.amountOfDust = amountOfDust;
	}

	public int getSprint() {
		return sprint;
	}

	public void setSprint(int sprint) {
		this.sprint = sprint;
	}

	public void setSpawnPoint(Vector3f spawnPoint) {
		this.spawnPoint = spawnPoint;
	}

}

package entities;

import org.lwjgl.util.vector.Vector3f;

import engineTester.OpenGLView;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public class Enemy extends Entity{
	
	private static final float MOVE_SPEED = 40;
	private static final float GRAVITY = -9.8f/200;
	private static final float TERMINAL_VELOCTIY = -9.8f;
	private float Y_OFFSET;

	private float speed = MOVE_SPEED;
	
	private float dx = 0;
	private float dy = 0;
	private float dz = 0;
	
	private Player player;
	private boolean isShrinking, shouldRemove;
	private float maxScale, minScale;
	
	public Enemy(Player player, Loader loader, String name, Vector3f position, Vector3f rot, float scale) {
		super(loader, name, position, rot, scale);
		this.player = player;
		this.isAbleToShrink(true);
		
		Y_OFFSET = boundingBox.getAABB().getMin().y + 10;
		maxScale = scale;
		minScale = scale/100;
	}

	public Enemy(Player player, TexturedModel mite, Vector3f position, float rotX, float rotY, float rotZ, float scale, String name) {
		super(mite, position, rotX, rotY, rotZ, scale, true, name);
		this.player = player;
		
		Y_OFFSET = boundingBox.getAABB().getMin().y;
		maxScale = scale;
		minScale = scale/100;
	}

	public void update() {
		boundingBox.update(this);
		
		speed = MOVE_SPEED * DisplayManager.getFrameTimeSeconds();
		checkInputs();
		
		if(isShrinking){
			scale -= (maxScale)/100f;
//			System.out.println(scale);
			
			if(this.scale <= minScale){
				shouldRemove = true;
				position.y = 0;
			}
		}else{
			checkCollisionWithPlayer();
		}
	}
	
	private void checkInputs() {
		float yaw = calcAngleToPlayer();
		this.rotY = yaw;
		
		dx = 0;
		dz = 0;
		dz += (player.getPosition().z - position.z) /1000;//(float) (-speed * Math.cos(Math.toRadians(yaw + 90)));
		dx += (player.getPosition().x - position.x) /1000;//(float) (speed * Math.sin(Math.toRadians(yaw + 90)));
		
		dy = calcDY(dy);
		
		position.z += dz;
		position.x += dx;
		position.y += dy;
	}
	
	private void checkCollisionWithPlayer(){
		if(boundingBox.intersects(player.getFeetHitbox())){
			System.exit(0);
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
			position.y = Y_OFFSET;
		}
		
		for(Entity e: OpenGLView.entities){
			if(!(e instanceof Projectile) && boundingBox.intersects(e.getBoundingBox())){
				if(e.getBoundingBox().getMax().y > boundingBox.getMin().y){
					dy=0;
					position.y = e.getBoundingBox().getMax().y + Y_OFFSET;
				}else{
					dx=(position.x - e.getPosition().x)/10;
					dz=(position.z - e.getPosition().z)/10;
				}
			}
		}
		
		return dy;
	}
	
	private float calcAngleToPlayer(){
		float dx = position.x - player.getPosition().x;
		float dz = position.z - player.getPosition().z;
		float theta = (float) -Math.toDegrees(Math.atan2(dz, dx));
		theta -= 90;
		return theta;
	}

	public boolean isShouldRemove() {
		return shouldRemove;
	}
	
	public void setShrinking(boolean b){
		this.isShrinking = b;
	}

}

package engineTester;

import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import textures.ModelTexture;

public class ArenaLevel {
	
	Player player;
	Vector3f[] spawnPoints = new Vector3f[16];
	float spawnRate = 5; //in spawns per second
	Random random = new Random();
	List<Enemy> enemies;
	List<Entity> entities;
	
	public static long framesElapsed = 0;
	
	private TexturedModel mite;
	boolean temp = true;
	
	public ArenaLevel(Player player, List<Enemy> enemies, List<Entity> entities){
		this.player = player;
		this.enemies = enemies;
		this.entities = entities;
		player.setPosition(new Vector3f(0,70,0));
		player.setSpawnPoint(new Vector3f(0,70,0));
		int currentPos = 0;
		for(int i=0; i<25; i++){
			if(i<=5 || (i%5==4 || i%5==0) || i>=21){
				spawnPoints[currentPos] = new Vector3f(250*(i%5 - 2)/2, 70, (float) (250*(Math.floor(i/5) - 2)/2));
				currentPos ++;
			}
		}
		
		RawModel rawModel = OBJFileLoader.loadOBJ("mite", OpenGLView.loader);
		ModelTexture tex = new ModelTexture(OpenGLView.loader.loadTexture("textureFiles", "mite"));
		mite = new TexturedModel(rawModel, tex);
	}

	public void update(){
		framesElapsed++;
		if(framesElapsed%20 == 0){
//		if(Keyboard.isKeyDown(Keyboard.KEY_1)){
			enemies.add(new Enemy(player, mite, new Vector3f(spawnPoints[random.nextInt(16)]), 0,0,0, 1, "mite"));
		}
//		System.out.println(enemies.size());
		for(int i=0; i<enemies.size(); i++){
			enemies.get(i).update();			
			if(enemies.get(i).isShouldRemove()){
				enemies.remove(i);
				OpenGLView.score ++;
//				System.out.println(enemies.size());
			}
		}
	}
}

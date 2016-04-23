package engineTester;

import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import entities.Enemy;
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
	
	public static long lastFrame, timeElapsed;
	
	private TexturedModel mite;
	
	public ArenaLevel(Player player, List<Enemy> enemies){
		this.player = player;
		this.enemies = enemies;
		player.setPosition(new Vector3f(0,70,0));
		player.setSpawnPoint(new Vector3f(0,70,0));
		int currentPos = 0;
		for(int i=0; i<25; i++){
			if(i<=5 || (i%5==4 || i%5==0) || i>=21){
				spawnPoints[currentPos] = new Vector3f(290*(i%5 - 2)/2, 75, (float) (290*(Math.floor(i/5) - 2)/2));
				currentPos ++;
			}
		}
		
		RawModel rawModel = OBJFileLoader.loadOBJ("mite", OpenGLView.loader);
		ModelTexture tex = new ModelTexture(OpenGLView.loader.loadTexture("textureFiles", "mite"));
		mite = new TexturedModel(rawModel, tex);
	}

	public void update(){
		long currentTime = Sys.getTime() * 1000 / Sys.getTimerResolution();
		int delta = (int) (currentTime-lastFrame);
		lastFrame = Sys.getTime() * 1000 / Sys.getTimerResolution();
		timeElapsed += delta;
		System.out.println(timeElapsed);
		if(timeElapsed%1 == 0){
			System.out.println("Trying to spawn...");
			enemies.add(new Enemy(player, mite, spawnPoints[random.nextInt(16)], 0,0,0, 3, "mite"));
			System.out.println("spawned");
		}
		
		for(Enemy e:enemies){
			e.update();
		}
	}
	
}

package engineTester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Enemy;
import entities.Entity;
import entities.Light;
import entities.Player;
import entities.World;
import guis.GuiRenderer;
import guis.GuiTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import worldParser.WorldFileLoader;

public class OpenGLView {
	
	public static World tutorial;
	public static World arena;
	public static ArenaLevel arenaLevel;
	public static Loader loader;
	public static MasterRenderer renderer;

	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Entity> normalMapEntities;
	public static List<Light> lights;
	public static List<GuiTexture> guiTextures;
	public static Camera camera;

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		GuiRenderer guiRenderer = new GuiRenderer(loader);

		enemies = new ArrayList<Enemy>();
		entities = new ArrayList<Entity>();
		normalMapEntities = new ArrayList<Entity>();
		lights = new ArrayList<Light>();
		guiTextures = new ArrayList<GuiTexture>();
		
		tutorial = new World();
		arena = new World();
		WorldFileLoader.loadObjectTypes("objectTypes");
		WorldFileLoader.loadWorldFile("Temp_Tutorial", tutorial);
		WorldFileLoader.loadWorldFile("Arena", arena);
		entities = tutorial.getEntities();
		

		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		for (int r = -5; r < 5; r++) {
			for (int c = -5; c < 5; c++) {
				waters.add(new WaterTile(r * 120 + 60, c * 120 + 60, 0));
			}
		}

		Light sun = new Light(new Vector3f(0, 50000, 30000), new Vector3f(.5f, .5f, .5f));
		lights.add(sun);
		
		camera = new Camera(Camera.FIRST_PERSON);
		Player player = new Player(camera, new Vector3f(0,150,0), new Vector3f(0, 90, 0));
		 
		boolean paused = false;
		boolean isPausedPrevDown = false;
		boolean inArena = false;
		
		while (!Display.isCloseRequested()) {
			
			List<Entity> allEntities = new ArrayList<Entity>(entities);
			allEntities.addAll(enemies);
			
			//Toggles gameloop from running and not running by pressing "P"
			if(Keyboard.isKeyDown(Keyboard.KEY_P)){
				if(!isPausedPrevDown){
					paused = !paused;					
				}
				isPausedPrevDown = true;
			}else isPausedPrevDown = false;
			
			if(!paused ){
	
				camera.move();
				player.update();
				for(Entity e: entities){
					e.update();
				}
				
				if(!inArena){
					if(player.getPosition().x>210 && player.getPosition().x<300 && player.getPosition().z>-400 && 
							player.getPosition().z<-360 && player.getPosition().y>130){
						inArena = true;
						arenaLevel = new ArenaLevel(player, enemies);
						entities = arena.getEntities();
					}
				}else{
					arenaLevel.update();
				}
			}

			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

			// render reflection texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(allEntities, normalMapEntities, lights, camera,
					new Vector4f(0, 1, 0, -waters.get(0).getHeight() + 1));
			camera.getPosition().y += distance;
			camera.invertPitch();

			// render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(allEntities, normalMapEntities, lights, camera,
					new Vector4f(0, -1, 0, waters.get(0).getHeight()));

			// render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			renderer.renderScene(allEntities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, 100000));
			waterRenderer.render(waters, camera, sun);
			guiRenderer.render(guiTextures);

			DisplayManager.updateDisplay();

		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

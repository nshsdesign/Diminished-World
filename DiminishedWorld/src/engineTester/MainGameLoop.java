package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {
	
	public static void main(String[] args) {
		
		//initializing stuff
		DisplayManager.createDisplay();
		Loader loader = new Loader();
//		Random random = new Random();
		TextMaster.init(loader);
		MasterRenderer renderer = new MasterRenderer(loader);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		//**********Font Setup************************
		FontType font = new FontType(loader.loadTexture("verdana"), new File("res/verdana.fnt"));
		
		//**********Textured Model Setup************************
		RawModel playerModel = OBJFileLoader.loadOBJ("testPlanet", loader);
		ModelTexture playerTex = new ModelTexture(loader.loadTexture("sunTex"));
		TexturedModel playerTexModel = new TexturedModel(playerModel, playerTex);
		
		//**********Normal Map Setup************************
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		
		//**********Terrain Setup************************
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		

		//lists for keeping track of world items
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		List<Light> lights = new ArrayList<Light>();
		List<Terrain> terrains = new ArrayList<Terrain>();
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		

		//**********Text Setup************************
		//					 (String text, float fontSize, FontType font, vec2D(posX, posY), maxLineLength, boolean centered);
		GUIText text = new GUIText("This is a test text", 2, font, new Vector2f(0.5f,0.5f), 0.5f, true);
		text.setColor(1, 1, 1);

		//**********Light Setup************************
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);
		
		//**********Terrain Setup************************
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		terrains.add(terrain);

		//**********Camera and Player Setup************************
		Camera camera = new Camera(Camera.FIRST_PERSON);
		Player player = new Player(camera, playerTexModel, new Vector3f(100, 0, 0), 0, 0, 0, 1);
		entities.add(player);
		
		//**********Mouse Picker Setup************************
		//lets you get the coords of where the mouse is on the terrain
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		//**********Water Renderer Setup************************
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(75, -75, 0);
		waters.add(water);
		
		//**********Extra items************************
		Light l = new Light(new Vector3f(0, 0, 0), new Vector3f(.5f, .5f, .5f));
		lights.add(l);
		
		while (!Display.isCloseRequested()) {
			if(camera.getType() != Camera.FREE_ROAM)player.move();
			camera.move();
			picker.update();
			for (Entity e : entities) {
				e.update();
			}
			for (Entity e : normalMapEntities) {
				e.update();
			}

			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			//render reflection teture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			//render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
			
			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();	
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));	
			waterRenderer.render(waters, camera, sun);
			guiRenderer.render(guiTextures);
			TextMaster.render();
			
			DisplayManager.updateDisplay();

		}
		
		TextMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}
}

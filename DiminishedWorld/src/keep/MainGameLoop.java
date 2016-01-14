package keep;

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

import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import menu.*;


public class MainGameLoop {
	
	public static void main(String[] args) {

		
		//Frame Below
		JFrame menuFrame = new JFrame("Menu");
	    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    menuFrame.setSize(1280,720);
	    menuFrame.setLocation(330,178);
	    menuFrame.setVisible(true);
	    menuPanel menuPanel = new menuPanel();
	    menuPanel.setLayout(null);
	    menuFrame.add(menuPanel);
	    JButton newButton = new JButton("New");
	    newButton.setVerticalTextPosition(AbstractButton.CENTER);
	    newButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    newButton.setMnemonic(KeyEvent.VK_D);
	    newButton.setActionCommand("disable");
	    buttonActionListener newListener = new buttonActionListener();
	    newButton.addActionListener(newListener);
	    newButton.setBounds(425 ,50, 450, 100);
	    newButton.setIcon(new ImageIcon("res/new.png"));
	    newButton.setOpaque(false);
	    newButton.setContentAreaFilled(false);
	    newButton.setBorderPainted(false);
	    JButton continueButton = new JButton("Continue");
	    continueButton.setVerticalTextPosition(AbstractButton.CENTER);
	    continueButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    continueButton.setMnemonic(KeyEvent.VK_D);
	    continueButton.setActionCommand("disable");
	    buttonActionListener continueListener = new buttonActionListener();
	    continueButton.addActionListener(continueListener);
	    continueButton.setBounds(425 ,200, 450, 100);
	    continueButton.setIcon(new ImageIcon("res/continue.png"));
	    continueButton.setOpaque(false);
	    continueButton.setContentAreaFilled(false);
	    continueButton.setBorderPainted(false);
	    JButton settingsButton = new JButton("Settings");
	    settingsButton.setVerticalTextPosition(AbstractButton.CENTER);
	    settingsButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    settingsButton.setMnemonic(KeyEvent.VK_D);
	    settingsButton.setActionCommand("disable");
	    settingsButton.setOpaque(false);
	    settingsButton.setContentAreaFilled(false);
	    settingsButton.setBorderPainted(false);
	    buttonActionListener settingsListener = new buttonActionListener();
	    settingsButton.addActionListener(settingsListener);
	    settingsButton.setBounds(425 ,350, 450, 100);
	    settingsButton.setIcon(new ImageIcon("res/settings.png"));
	    JButton helpButton = new JButton("Help");
	    helpButton.setVerticalTextPosition(AbstractButton.CENTER);
	    helpButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    helpButton.setMnemonic(KeyEvent.VK_D);
	    helpButton.setActionCommand("disable");
	    helpButton.setOpaque(false);
	    helpButton.setContentAreaFilled(false);
	    helpButton.setBorderPainted(false);
	    buttonActionListener helpListener = new buttonActionListener();
	    helpButton.addActionListener(helpListener);
	    helpButton.setBounds(425 ,500, 450, 100);
	    helpButton.setIcon(new ImageIcon("res/help.png"));
	    menuPanel.add(newButton);
	    menuPanel.add(continueButton);
	    menuPanel.add(settingsButton);
	    menuPanel.add(helpButton);
	    
	    menuFrame.validate();
	    menuPanel.repaint();
	    menuPanel.validate();
	    boolean onMenu = true;
	    
	    while(onMenu == true){
	    	if(newListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	if(continueListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	if(settingsListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	if(helpListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	try{
                Thread.sleep(500);
            }catch(Exception ex){
                System.exit(1);
            }
	    }
	    //Frame Above
		
		//initializing stuff
		DisplayManager.createDisplay();
		menuFrame.setVisible(false); //ST 
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

package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Collisions.Basic;
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
import music.audioPlayer;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import objInfoSaver.objInfoSave;
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
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import menu.*;


public class MainGameLoop {

	public static void main(String[] args) {

		/**
		String[] randomInfo = new String[3];
		randomInfo[0] = "RandomStuffs";
	    randomInfo[1] = "12414241441";
	    randomInfo[2] = "fasbhf978t234f9h4n";
		objInfoSave saver = new objInfoSave();
		saver.objInfoAddInfo(randomInfo);
		*/
		String newline = "\n"; //ST

		//Frame Below -------------------------------------------------------------------------------------------------------------------------------------------------------
		JFrame menuFrame = new JFrame("Menu");
	    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    menuFrame.setSize(1280,720);
	    menuFrame.setLocation(330,178);
	    menuFrame.setVisible(true);
	    menuPanel menuPanel = new menuPanel();
	    menuPanel.setBackground(Color.BLACK);
	    menuPanel.setLayout(null);
	    menuFrame.add(menuPanel);


	    JFrame helpFrame = new JFrame("Help");
	    helpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    helpFrame.setSize(1280,720);
	    helpFrame.setLocation(330,178);
	    helpFrame.setVisible(false);
	    menuPanel helpPanel = new menuPanel();
	    helpPanel.setLayout(null);
	    helpPanel.setBackground(Color.GRAY);
	    helpFrame.add(helpPanel);

	    JFrame settingsFrame = new JFrame("Settings");
	    settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    settingsFrame.setSize(1280,720);
	    settingsFrame.setLocation(330,178);
	    settingsFrame.setVisible(false);
	    menuPanel settingsPanel = new menuPanel();
	    settingsPanel.setLayout(null);
	    //settingsPanel.setBackground(Color.WHITE);
	    settingsFrame.add(settingsPanel);



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

	    JButton returnFromHelpButton = new JButton("Return");
	    returnFromHelpButton.setVerticalTextPosition(AbstractButton.CENTER);
	    returnFromHelpButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    returnFromHelpButton.setMnemonic(KeyEvent.VK_D);
	    returnFromHelpButton.setActionCommand("disable");
	    returnFromHelpButton.setOpaque(false);
	    returnFromHelpButton.setContentAreaFilled(false);
	    returnFromHelpButton.setBorderPainted(false);
	    buttonActionListener returnFromHelpListener = new buttonActionListener();
	    returnFromHelpButton.addActionListener(returnFromHelpListener);
	    returnFromHelpButton.setBounds(425 ,500, 450, 100);
	    returnFromHelpButton.setIcon(new ImageIcon("res/help.png"));

	    JButton returnFromSettingsButton = new JButton("Settings");
	    returnFromSettingsButton.setVerticalTextPosition(AbstractButton.CENTER);
	    returnFromSettingsButton.setHorizontalTextPosition(AbstractButton.LEADING);
	    returnFromSettingsButton.setMnemonic(KeyEvent.VK_D);
	    returnFromSettingsButton.setActionCommand("disable");
	    returnFromSettingsButton.setOpaque(false);
	    returnFromSettingsButton.setContentAreaFilled(false);
	    returnFromSettingsButton.setBorderPainted(false);
	    buttonActionListener returnFromSettingsListener = new buttonActionListener();
	    returnFromSettingsButton.addActionListener(returnFromSettingsListener);
	    returnFromSettingsButton.setBounds(425 ,500, 450, 100);
	    returnFromSettingsButton.setIcon(new ImageIcon("res/settings.png"));

	    //JTextArea helpInfo = new JTextArea();
	    //helpInfo.setBounds(60,400,500,300);
        //helpPanel.add(helpInfo);
        //helpInfo.append("" + newline);

	    //ST Below

        JLabel movementControlInfoTitle = new JLabel("<html><h1><font color='yellow'> Movement Controls </font></h1></html>");
        movementControlInfoTitle.setBounds(60,-100,500,300);
        helpPanel.add(movementControlInfoTitle);

        JLabel movementControlInfoOne = new JLabel("<html><font color='white'>-Walk forward: W, Walk backward: S, Walk left: A, Walk right: D</font></html>");
        movementControlInfoOne.setBounds(80,-80,500,300);
        helpPanel.add(movementControlInfoOne);

        JLabel movementControlInfoTwo = new JLabel("<html><font color='white'>-Sprint: Shift?, Jump: Spacebar</font></html>");
        movementControlInfoTwo.setBounds(80,-65,500,300);
        helpPanel.add(movementControlInfoTwo);

        JLabel rayGunControlInfoTitle = new JLabel("<html><h1><font color='yellow'>Raygun Controls</font></h1></html>");
        rayGunControlInfoTitle.setBounds(60,-25,500,300);
        helpPanel.add(rayGunControlInfoTitle);

        JLabel rayGunControlInfoOne = new JLabel("<html><font color='white'>-Fire: Leftmouse click</font></html>");
        rayGunControlInfoOne.setBounds(80,-3,500,300);
        helpPanel.add(rayGunControlInfoOne);

        JLabel communicatingInfoTitle = new JLabel("<html><h1><font color='yellow'>Communication</font></h1></html>");
        communicatingInfoTitle.setBounds(60,35,500,300);
        helpPanel.add(communicatingInfoTitle);

        JLabel communicatingInfoOne = new JLabel("<html><font color='white'>-Talk: E?, Continue talking: Enter?</font></html>");
        communicatingInfoOne.setBounds(80,55,500,300);
        helpPanel.add(communicatingInfoOne);

        JLabel pictureLabelTitle = new JLabel("<html><h1><font color='yellow'>Picture Items</font></h1></html>");
        pictureLabelTitle.setBounds(60,95,500,300);
        helpPanel.add(pictureLabelTitle);

        JLabel pictureLabelSanityText = new JLabel("<html><font color='white'>-Sanity</font></html>");
        pictureLabelSanityText.setBounds(80,115,500,300);
        helpPanel.add(pictureLabelSanityText);

        ImageIcon sanityIcon = new ImageIcon("res/sanity/sanityBar100.png");

        JLabel pictureLabelSanityPic = new JLabel(sanityIcon);
        pictureLabelSanityPic.setBounds(-15,130,500,300);
        helpPanel.add(pictureLabelSanityPic);

        JLabel pictureLabelSprintText = new JLabel("<html><font color='white'>-Sprint</font></html>");
        pictureLabelSprintText.setBounds(80,160,500,300);
        helpPanel.add(pictureLabelSprintText);

        ImageIcon sprintIcon = new ImageIcon("res/sanity/sanityBar100.png");

        JLabel pictureLabelSprintPic = new JLabel(sprintIcon);
        pictureLabelSprintPic.setBounds(-15,175,500,300);
        helpPanel.add(pictureLabelSprintPic);

        JLabel pictureLabelCooldownText = new JLabel("<html><font color='white'>-Cooldown</font></html>");
        pictureLabelCooldownText.setBounds(80,205,500,300);
        helpPanel.add(pictureLabelCooldownText);

        ImageIcon cooldownIcon = new ImageIcon("res/sanity/sanityBar100.png");

        JLabel pictureLabelCooldownPic = new JLabel(cooldownIcon);
        pictureLabelCooldownPic.setBounds(-15,220,500,300);
        helpPanel.add(pictureLabelCooldownPic);


        //ST Above



        helpPanel.add(movementControlInfoTitle);
        helpPanel.validate();
        helpPanel.repaint();





	    menuPanel.add(newButton);
	    menuPanel.add(continueButton);
	    menuPanel.add(settingsButton);
	    menuPanel.add(helpButton);

	    helpPanel.add(returnFromHelpButton);

	    settingsPanel.add(returnFromSettingsButton);

	    JSlider musicVolume = null;
	    JSlider[] settingsSliders =  {musicVolume};
	    String[] settingNames = {"Music Volume"};
	    int STAT_MIN = -100;
		int STAT_MAX = 100;
		int STAT_INIT = 0;

	    for(int i=0; i<settingsSliders.length; i++){
			JLabel sliderLabel = new JLabel(settingNames[i]+":");
			sliderLabel.setBounds(490 + 10, 18 + (i*100), 300, 50);
			settingsPanel.add(sliderLabel);

			settingsSliders[i] = new JSlider(JSlider.HORIZONTAL,
	                STAT_MIN, STAT_MAX, STAT_INIT);
			settingsSliders[i].addChangeListener(new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					//changeValue((JSlider)e.getSource());
				}
			});
			settingsSliders[i].setMajorTickSpacing((STAT_MAX-STAT_MIN)/10);
			settingsSliders[i].setMinorTickSpacing(2);
			settingsSliders[i].setPaintTicks(true);
			settingsSliders[i].setPaintLabels(true);
			settingsSliders[i].setPaintTrack(false);
			settingsSliders[i].setBounds(490, 50 + (i*100), 300, 50);
			settingsPanel.add(settingsSliders[i]);

		}

	    menuFrame.validate();
	    menuPanel.repaint();
	    menuPanel.validate();
	    boolean onMenu = true;
	    boolean onHelp = false;
	    NewWindowListener menuWindowListener = new NewWindowListener();
	    menuFrame.addWindowListener(menuWindowListener);
	    audioPlayer audio = new audioPlayer();
		audio.play("res/menuSongWav.wav");
	    //audio.play("res/gamesong3.wav");
	    while(onMenu == true){
	    	if(newListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	if(continueListener.accButtonInfo() == true){
	    		onMenu = false;
	    	}
	    	if(settingsListener.accButtonInfo() == true){
	    		//onMenu = false;
	    		onHelp = true;
	    		menuFrame.setVisible(false);
	    		settingsFrame.setVisible(true);
	    		while(onHelp == true){
	    			if(returnFromSettingsListener.accButtonInfo() == true){
	    				onHelp = false;
	    			}
	    			audio.modVolume(settingsSliders[0].getValue());
	    			try{
	                    Thread.sleep(500);
	                }catch(Exception ex){
	                    System.exit(1);
	                }
	    		}
	    		settingsListener.modButton();
	    		returnFromSettingsListener.modButton();
	    		onHelp = false;
	    		settingsFrame.setVisible(false);
	    		menuFrame.setVisible(true);


	    	}
	    	if(helpListener.accButtonInfo() == true){
	    		//onMenu = false;
	    		onHelp = true;
	    		menuFrame.setVisible(false);
	    		helpFrame.setVisible(true);
	    		while(onHelp == true){
	    			if(returnFromHelpListener.accButtonInfo() == true){
	    				onHelp = false;
	    			}
	    			try{
	                    Thread.sleep(500);
	                }catch(Exception ex){
	                    System.exit(1);
	                }
	    		}
	    		helpListener.modButton();
	    		returnFromHelpListener.modButton();
	    		onHelp = false;
	    		helpFrame.setVisible(false);
	    		menuFrame.setVisible(true);
	    	}
	    	try{
                Thread.sleep(500);
            }catch(Exception ex){
                System.exit(1);
            }
	    	if(menuWindowListener.accIsClosed() == true){
	    		audio.close();
	    	}
	    }
	    audio.close();

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

		TexturedModel testPlanetModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("testPlanet", loader),
				new ModelTexture(loader.loadTexture("TestPlanetTex")));
		testPlanetModel.getTexture().setNormalMap(loader.loadTexture("TestPlanetTex"));
		testPlanetModel.getTexture().setShineDamper(10);
		testPlanetModel.getTexture().setReflectivity(0.5f);

		TexturedModel structureModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("structure3withOpening", loader),
				new ModelTexture(loader.loadTexture("sunTex")));
		structureModel.getTexture().setNormalMap(loader.loadTexture("sunTex"));
		structureModel.getTexture().setShineDamper(10);
		structureModel.getTexture().setReflectivity(0.5f);

		//Basic BarrelBox = new Basic(new Vector3f(75, 10, -50), new Vector3f(10, 13, 10), "Barrel");


		//**********Terrain Setup************************
		/**
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		*/

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("space"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("space"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("space"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("space"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		//Basic FloorBox = new Basic(new Vector3f(75, 0, 0), new Vector3f(1000, 1000, 1000), "Floor");
		Basic FloorBox = new Basic(new Vector3f(75, 0, 0), new Vector3f(1, 1, 1), "Floor");


		//lists for keeping track of world items
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		List<Light> lights = new ArrayList<Light>();
		List<Terrain> terrains = new ArrayList<Terrain>();
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();




		//**********Text Setup************************
		//					 (String text, float fontSize, FontType font, vec2D(posX, posY), maxLineLength, boolean centered);
		//GUIText text = new GUIText("Sanity", 2, font, new Vector2f(0.5f,0.5f), 0.5f, true);
		GUIText textSan = new GUIText("Sanity", 1.3f, font, new Vector2f(-0.22f,0.7f), 0.5f, true); //ST
	    textSan.setColour(1, 1, 1);
		GUIText textSpr = new GUIText("Sprint", 1.3f, font, new Vector2f(-0.22f,0.78f), 0.5f, true); //ST
		textSpr.setColour(1, 1, 1);
		GUIText textCool = new GUIText("Cooldown", 1.3f, font, new Vector2f(0.7f,0.74f), 0.5f, true); //ST
		textCool.setColour(1, 1, 1);

		//**********GUI Picture************************
		//guiTextures.add(new GuiTexture(guiTextures.size(), new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(1, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(3, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(5, new Vector2f(0.5f,0.5f), new Vector2f(0.5f,0.5f)));
		//guiTextures.add(new GuiTexture(loader.loadTexture("/sanity/sanityBar100"), new Vector2f(-.65f,-.54f), new Vector2f(0.45f,0.05f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.9f,-0.67f), new Vector2f(0.2f,0.2f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("health"), new Vector2f(0.82f,-0.67f), new Vector2f(0.2f,0.2f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("fern"), new Vector2f(0.82f,0.75f), new Vector2f(0.2f,0.2f))); //ST
		guiTextures.add(new GuiTexture(loader.loadTexture("aim3lineYellowMidV2"), new Vector2f(0f,0f), new Vector2f(0.05f,0.05f))); //ST


		//**********Light Setup************************
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);

		//**********Terrain Setup************************
		//Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrain = new Terrain(0, -1000000, loader, texturePack, blendMap, "heightmap");
		terrains.add(terrain);

		//**********Camera and Player Setup************************
		Camera camera = new Camera(Camera.FIRST_PERSON);
		Player player = new Player(camera, playerTexModel, new Vector3f(100, 0, 0), 0, 0, 0, 1);
		entities.add(player);
		int playerEntityPosition = entities.size() - 1;

		Basic PlayerBox = new Basic(new Vector3f(75, 10, 0), new Vector3f(5, 10, 5), "Player");

		//**********Mouse Picker Setup************************
		//lets you get the coords of where the mouse is on the terrain
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

		//**********Water Renderer Setup************************
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		//WaterTile water = new WaterTile(75, -75, 0);
		WaterTile water = new WaterTile(75, -75000000, 0);
		waters.add(water);

		//**********Extra items************************
		Light l = new Light(new Vector3f(0, 0, 0), new Vector3f(.5f, .5f, .5f));
		lights.add(l);
		int testSanity = player.accSanity()+1; //ST
		GuiTexture sanityTexture = new GuiTexture(loader.loadTexture("/sanity/sanityBar"+testSanity), new Vector2f(-.65f,-.54f), new Vector2f(0.45f,0.05f));
		guiTextures.add(sanityTexture);
		//guiTextures.add(new GuiTexture(loader.loadTexture("/sanity/sanityBar"+testSanity), new Vector2f(-5f,-5f), new Vector2f(0.45f,0.05f)));
		//int guiSanityTexturePosition = guiTextures.size()-1;
		//guiTextures.remove(guiTextures.get(guiSanityTexturePosition));
		//guiTextures.remove(guiTextures.get(guiSanityTexturePosition-1));
		//guiTextures.remove(guiTextures.get(guiSanityTexturePosition-2));

		//**********Barrel Tests************************
		int barrelX = 250;
		int barrelY = 25;
		int barrelZ= 250;


		//normalMapEntities.add(new Entity(structure3TexModel, new Vector3f(0, 0, 0), 0, 0, 0, 1f, true, "structure3Model"));
		normalMapEntities.add(new Entity(barrelModel, new Vector3f(barrelX, barrelY, barrelZ), 0, 0, 0, 1f, true, "barrelModel"));
		Basic BoulderBox = new Basic(new Vector3f(barrelX, barrelY, barrelZ), new Vector3f(5, 13, 5), "Boulder");



		int barrelModelPosition = normalMapEntities.size()-1; //ST
		float barrelModelSize = normalMapEntities.get(barrelModelPosition).getScale(); //ST
		int time=0;
		boolean needToGrowBack = false;

		normalMapEntities.add(new Entity(testPlanetModel, new Vector3f(player.accX(), player.accY(), player.accZ()), 0, 0, 0, .001f, true, "testPlanetModel"));
		//normalMapEntities.add(new Entity(structureModel, new Vector3f(90f, 10, -100f), 0, 0, 0, 10f, true, "structureModel"));
		Vector3f newVectors;

		ArrayList<Float[]> tempEntities = new ArrayList<Float[]>();
	    Float[] tempEntityFloatStart = new Float[8];
	    tempEntityFloatStart[1] = 300f;
	    tempEntityFloatStart[0] = 0f;
	    ArrayList<Basic> rayBoxes = new ArrayList<Basic>();
	    Basic tempBasic = new Basic(player.getPosition(), new Vector3f(100, 100, 100), "ray");

	    float[] tempRayStuff = player.accInfoForRayGun();
	    tempEntityFloatStart[2] = tempRayStuff[0];
	    tempEntityFloatStart[3] = tempRayStuff[1];
	    tempEntityFloatStart[4] = tempRayStuff[2];
	    tempEntityFloatStart[5] = player.accX();
	    tempEntityFloatStart[6] = player.accY();
	    tempEntityFloatStart[7] = player.accZ();

	    boolean didThisRun = false;
	    int defaultFireTime = 25;
	    int fireTime = 0;
		while (!Display.isCloseRequested()) {
			if(camera.getType() != Camera.FREE_ROAM)player.move();
			camera.move();
			picker.update();
			fireTime--;
			/**
			for (Entity e : entities) {
				e.update();
			}
			for (Entity e : normalMapEntities) {
				e.update();
			}
			*/
			//ST BELOW
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);


			//if(time < 300){
				//time++;
			//}else{

			if(Mouse.isButtonDown(0) == true){
				if(fireTime<1){
					tempEntityFloatStart = new Float[11];
					tempEntityFloatStart[1] = 300f;
					tempEntityFloatStart[0] = 0f;
					tempRayStuff = player.accInfoForRayGun();
					tempEntityFloatStart[2] = tempRayStuff[0];
					tempEntityFloatStart[3] = tempRayStuff[1];
					tempEntityFloatStart[4] = tempRayStuff[2];
					tempEntityFloatStart[5] = player.accX();
					tempEntityFloatStart[6] = player.accY();
					tempEntityFloatStart[7] = player.accZ();


					//tempEntityFloatStart[5] = PlayerBox.accX()+(5*tempEntityFloatStart[2]);
					//tempEntityFloatStart[6] = PlayerBox.accY()+(5*tempEntityFloatStart[3]);
					//tempEntityFloatStart[7] = PlayerBox.accZ()+(5*tempEntityFloatStart[4]);
					//entities.get(playerEntityPosition);
					//System.out.println("Button is down");
					//normalMapEntities.add(new Entity(testPlanetModel, PlayerBox.accVectorPoints(), 0, 0, 0, 1f, true, "testPlanetModel"));
					newVectors = new Vector3f(player.accX(), player.accY(), player.accZ());
					normalMapEntities.add(new Entity(testPlanetModel, newVectors, 0, 0, 0, 1f, true, "testPlanetModel"));
					//System.out.println("Normal Map Entity Size: " + normalMapEntities.size());
					tempEntityFloatStart[0] = (float)normalMapEntities.size()-1;
					tempEntities.add(tempEntityFloatStart);
					rayBoxes.add(new Basic(new Vector3f(player.accX(), player.accY(), player.accZ()), new Vector3f(3, 3, 3), "ray"));
			    	//System.out.println("Temp Entitiy Value B: " +tempEntities.get(tempEntities.size()-1)[0].intValue());
			    	fireTime = defaultFireTime;
				}

			}
			for(int y=0; y<tempEntities.size(); y++){
				if(didThisRun == false){
					tempEntities.get(y)[1] = tempEntities.get(y)[1].floatValue()-1;
					//System.out.println(y +"y: " + (tempEntities.get(y)[1].intValue()-1));
					//normalMapEntities.get((int)tempEntities.get(y)[0].floatValue()).getX() + tempEntities.get(y)[5].floatValue();
					//normalMapEntities.get((int)tempEntities.get(y)[0].floatValue()).setPosition(new Vector3f(PlayerBox.accX(), PlayerBox.accY(), PlayerBox.accZ()));

					normalMapEntities.get((int)tempEntities.get(y)[0].floatValue()).setPosition(new Vector3f(normalMapEntities.get((int)tempEntities.get(y)[0].floatValue()).getX() + (tempEntities.get(y)[2].floatValue()*3), normalMapEntities.get((int)tempEntities.get(y)[0].floatValue()).getY() + (tempEntities.get(y)[3].floatValue()*3), normalMapEntities.get((int)tempEntities.get(y)[0].floatValue()).getZ() + (tempEntities.get(y)[4].floatValue()*3)));
					rayBoxes.get(y).setBoxPos(normalMapEntities.get((int)tempEntities.get(y)[0].floatValue()).getPosition());
					if(tempEntities.get(y)[1].intValue() < 1){
						didThisRun=true;
						//System.out.println("Temp Entitiy Value A: " +tempEntities.get(y)[0].intValue());
						//System.out.println("B Normal Map Entity Size: " + normalMapEntities.size());
						normalMapEntities.remove(tempEntities.get(y)[0].intValue());
						//System.out.println("A Normal Map Entity Size: " + normalMapEntities.size());
						//System.out.println("Temp Entitiy Value Br: " +tempEntities.get(tempEntities.size()-1)[0].intValue());
						tempEntities.remove(y);
						rayBoxes.remove(y);
						//System.out.println("Temp Entitiy Value Ar: " +tempEntities.get(tempEntities.size()-1)[0].intValue());
						for(int x=0; x<tempEntities.size(); x++){
							//System.out.println(x + "bx: " + tempEntities.get(x)[0].intValue());
							tempEntities.get(x)[0] = (float)tempEntities.get(x)[0].intValue()-1;
							//System.out.println(x + "ax: " + tempEntities.get(x)[0].intValue());
						}
					}
				}

			}
			didThisRun = false;
			/**
			if(normalMapEntities.get(barrelModelPosition).getIsShrinking() == true){
			    barrelModelSize = normalMapEntities.get(barrelModelPosition).getScale(); //ST
				time = 0;
				if(barrelModelSize > normalMapEntities.get(barrelModelPosition).getMinScale()){
					barrelModelSize=barrelModelSize-0.01f;
				}else{
					normalMapEntities.get(barrelModelPosition).modIsShrinking();
				}
			}else{
				if(time < 1000){
					time++;
					System.out.println(time);
				}else{
					if(barrelModelSize < normalMapEntities.get(barrelModelPosition).getMaxScale()){
						barrelModelSize=barrelModelSize+0.01f;
					}else{
						normalMapEntities.get(barrelModelPosition).modIsShrinking();
						time = 0;
					}

				}
			}
			*/
			//}




			normalMapEntities.get(barrelModelPosition).setScale(barrelModelSize);
			normalMapEntities.get(barrelModelPosition).setPosition(new Vector3f(barrelX,barrelModelSize*6.03f+ barrelY, barrelZ));

			//ST ABOVE
			//render reflection teture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			//renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1));
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
			//The Grow/Shrink checks
			for(int x=0; x<tempEntities.size(); x++){
				//System.out.println("Box of Boulder: " + BoulderBox.accPositionPoints());
				//System.out.println("Box of ray#" + x + ":" + rayBoxes.get(x).accPositionPoints());
				//PlayerBox.setBoxPos(player.getPosition());
				//rayBoxes.get(x).setBoxPos(new Vector3f(tempEntities.get(x)[5].floatValue(), tempEntities.get(x)[6].floatValue(), tempEntities.get(x)[7].floatValue()));
				if ((!rayBoxes.get(x).checkCollisions(BoulderBox)) && (!rayBoxes.get(x).checkCollisions(FloorBox))) {
					//player.move();
					 System.out.println("P1");
				} else {
					// needToGrowBack = true;
				}
				if ((rayBoxes.get(x).checkCollisions(BoulderBox))) {
					normalMapEntities.get(barrelModelPosition).modIsShrinking(true);
					System.out.println("P2");
					if (barrelModelSize > normalMapEntities.get(barrelModelPosition).getMinScale()) {
						barrelModelSize = barrelModelSize - 0.01f;
						time = 0;
					} else {
						normalMapEntities.get(barrelModelPosition).modIsShrinking(false);
					}
					if (normalMapEntities.get(barrelModelPosition).getIsShrinking()) {
						barrelModelSize = normalMapEntities.get(barrelModelPosition).getScale(); // ST
						time = 0;
					}
					normalMapEntities.remove(tempEntities.get(x)[0].intValue());
					tempEntities.remove(x);
					rayBoxes.remove(x);
					for(int y=0; y<tempEntities.size(); y++){
						tempEntities.get(y)[0] = (float)tempEntities.get(y)[0].intValue()-1;
					}
				}else if ((rayBoxes.get(x).checkCollisions(FloorBox))) {
					//player.move(rayBoxes.get(x).checkFaceCollisions(FloorBox));
					 System.out.println("P3");
				}
				// System.out.println("Character Points: " +
				// rayBoxes.get(x).accPositionPoints());
				
			}
			if (normalMapEntities.get(barrelModelPosition).getIsShrinking() == true) {
				barrelModelSize = normalMapEntities.get(barrelModelPosition).getScale(); // ST
				time = 0;
				if (barrelModelSize > normalMapEntities.get(barrelModelPosition).getMinScale()) {
					barrelModelSize = barrelModelSize - 0.01f;
				} else {
					normalMapEntities.get(barrelModelPosition).modIsShrinking(false);
					needToGrowBack = true;
				}
			} else {
				needToGrowBack = true;
			}

			if (needToGrowBack == true) {
				if (time < 200) {
					time++;
					System.out.println(time);
				} else {
					if (barrelModelSize < normalMapEntities.get(barrelModelPosition).getMaxScale()) {
						barrelModelSize = barrelModelSize + 0.01f;
					} else {
						// normalMapEntities.get(barrelModelPosition).modIsShrinking();
						time = 0;
						needToGrowBack = false;
						normalMapEntities.get(barrelModelPosition).modIsShrinking(false);
					}

				}

			}



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

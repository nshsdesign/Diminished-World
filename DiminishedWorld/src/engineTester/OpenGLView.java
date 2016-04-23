package engineTester;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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
import entities.Projectile;
import entities.World;
import guis.GuiRenderer;
import guis.GuiTexture;
import menu.NewWindowListener;
import menu.buttonActionListener;
import menu.menuPanel;
import models.RawModel;
import models.TexturedModel;
import music.audioPlayer;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import textures.ModelTexture;
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
	public static List<Projectile> projectiles;
	public static Camera camera;
	public static int score = 0, prevScore = -1;

	public static void main(String[] args) {
		
		
		//-------------------MAIN MENU-----------------------------
		
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
	    menuFrame.dispose();

		    //-------------------MAINMENU END--------------------------
	    
	    
	    
	    
	    
	    

		DisplayManager.createDisplay();
		
		loader = new Loader();
		renderer = new MasterRenderer(loader);
		GuiRenderer guiRenderer = new GuiRenderer(loader);

		enemies = new ArrayList<Enemy>();
		entities = new ArrayList<Entity>();
		normalMapEntities = new ArrayList<Entity>();
		lights = new ArrayList<Light>();
		guiTextures = new ArrayList<GuiTexture>();
		projectiles = new ArrayList<Projectile>();
		
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
		Entity rayGun = new Entity(loader, "RayGun", new Vector3f(), new Vector3f(0,0,0), 1);
		
		RawModel rawModel = OBJFileLoader.loadOBJ("projectile", OpenGLView.loader);
		ModelTexture tex = new ModelTexture(OpenGLView.loader.loadTexture("textureFiles", "projectile"));
		TexturedModel projectile = new TexturedModel(rawModel, tex);
		 
		boolean paused = false;
		boolean isPausedPrevDown = false;
		boolean inArena = false;
		boolean isMousePrevDown = false;
		
		while (!Display.isCloseRequested()) {
			
			//Toggles gameloop from running and not running by pressing "P"
			if(Keyboard.isKeyDown(Keyboard.KEY_P)){
				if(!isPausedPrevDown){
					paused = !paused;					
				}
				isPausedPrevDown = true;
			}else isPausedPrevDown = false;
			
			if(!paused ){
				
				camera.move();
				
				if(!inArena){
					if(player.getPosition().x>210 && player.getPosition().x<300 && player.getPosition().z>-400 && 
							player.getPosition().z<-360 && player.getPosition().y>130){
						inArena = true;
						arenaLevel = new ArenaLevel(player, enemies, entities);
						entities = arena.getEntities();
						entities.add(rayGun);
					}
				}else{
					arenaLevel.update();
					rayGun.setPosition(new Vector3f((float) (player.getPosition().x + -5*Math.sin(Math.toRadians(camera.getYaw()-180))), 
							player.getPosition().y - 5, (float) (player.getPosition().z + 5*Math.cos(Math.toRadians(camera.getYaw()-180)))));
					rayGun.setRotY(player.getRotY());
					if(Mouse.isButtonDown(0)){
						if(!isMousePrevDown){
							Projectile p = new Projectile(enemies, projectile, player.getPosition(), player.accInfoForRayGun());
							projectiles.add(p);
						}
						isMousePrevDown = true;
					}else isMousePrevDown = false;
					
					if(score != prevScore) System.out.println("Score:" + score);
					prevScore = score;
					
				}
				ArrayList<Projectile> toRemove = new ArrayList<Projectile>();
				for(Projectile p: projectiles){
					p.update();
					if(p.isShouldRemove()){
						toRemove.add(p);
					}
				}
				for(Projectile p: toRemove){
					projectiles.remove(p);
					entities.remove(p);
				}
				player.update();
//				for(Entity e: entities){
//					e.update();
//				}
			}

			//-----------------RENDER------------------//
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

			List<Entity> allEntities = new ArrayList<Entity>(entities);
			allEntities.addAll(enemies);
			allEntities.addAll(projectiles);
			
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
			
			//-----------------RENDER-ENDER------------------//

		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}

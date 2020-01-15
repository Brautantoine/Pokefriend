package fr.pokeTurtles.app;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * <b>StupidSprite</b> <br>
 * 
 * An useless sprite that just bounce against the wall, implements Drawable to be render and
 * clickable. DEBUG INTEDED
 */
public class StupidSprite implements Drawable, Clickable{
	
	//private SpriteBatch batch;
	private Texture imgTexture;
	/** Thanks to openGl we need this boolean to create the openGL elements once in the callback **/
	private boolean createOnce = false;
	private int posX, posY;
	private int dX, dY;
	/** Indicate at wich height this layer as to be draw **/
	private int layerHeight;
	private String fileName = new String();
	/** The instance of the Singleton Engine **/
	private Engine engineInstance;
	
	/** The StupidSprite is time based updated **/
	private Timer timer = new Timer();
	
	final private int weight = 1920;
	final private int height = 1080;
	/**
	 * <b>StupidSprite</b>
	 * 
	 * Instanciate the class
	 * 
	 * @param fileName
	 * @param posX
	 * @param posY
	 */
	public StupidSprite(String fileName, int posX, int posY) {
		// TODO Auto-generated constructor stub
		engineInstance = Engine.getInstance();
		engineInstance.addDrawable(this);
		engineInstance.addClickable(this);
		this.posX = posX;
		this.posY = posY;
		this.fileName = fileName;
		dX = 1;
		dY = 1;
		layerHeight=3;
		
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
              update();
            }
        },0,50);
		
	}
	/**
	 * <b>StupidSprite</b>
	 * 
	 * Instanciate the class with non default speed value
	 * 
	 * @param fileName
	 * @param posX
	 * @param posY
	 * @param dX
	 * @param dY
	 */
	public StupidSprite(String fileName, int posX, int posY, int dX, int dY) {
		// TODO Auto-generated constructor stub
		engineInstance = Engine.getInstance();
		engineInstance.addDrawable(this);
		engineInstance.addClickable(this);
		this.posX = posX;
		this.posY = posY;
		this.fileName = fileName;
		this.dX = dX;
		this.dY = dY;
		
		layerHeight = 3;
		
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
              update();
            }
        },0,50);
		
	}
	/**
	 * <b>Create</b> <br>
	 * 
	 * public void create() 
	 * 
	 * create the openGL element to render, had to be run in the OpenGl thread however it will throw exception
	 */
	@Override
	public void create() {
		// TODO Auto-generated method stub
		imgTexture = new Texture(fileName);
	}
	/**
	 * <b>render</b>
	 * 
	 * Call by the engine, specify how to render the sprite on the screen
	 */
	@Override
	public void render(SpriteBatch batch, int currentLayerHeight) {
		/* That stupid OpenGL only allow openGl manipulation in is own thread
		 * that's why we have to create everything in the callback */
		if(!createOnce)
		{
			create();
			createOnce = true;
		}
		if(layerHeight == currentLayerHeight)
			batch.draw(imgTexture, posX, posY);
	}
	/**
	 * @return int layerHeight
	 */
	public int getLayerHeight() { return layerHeight ; }
	
	@Override
	public boolean close() {
		
		engineInstance.removeDrawable(this);
		timer.cancel();
		
		return true;
	}
	
	private void update() {
		if(posX+dX > weight || posX+dX < 0)
			dX = -dX;
		if(posY+dY > height || posY+dY < 0)
			dY = -dY;
		posX+=dX;
		posY+=dY;
			
	}
	/**
	 * <b>onClick</b>
	 * 
	 * Call by the engine, specify how to render react to a touchDown event
	 */
	public void onClick(int x, int y) {
		if( ((posX<x) && (posX+200 > x)) && ((posY<y) && (posY+200 < y)) )
			System.out.println(fileName);
			
	}
	/* ** DEBUG INTENDED **
	public void onKeyDown() {
		
		if(up) {
		engineInstance.removeDrawable(this);
		up = false;
		}
		else {
			engineInstance.addDrawable(this);
			up = true;
		}
	}
	
	private boolean up = true;
	*/
}


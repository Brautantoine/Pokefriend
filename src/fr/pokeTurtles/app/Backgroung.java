package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * <b>Background</b> <br>
 * 
 * Instanciate a static img at layer 0
 */
public class Backgroung implements Drawable {
	
	private Texture imgTexture;
	private boolean createOnce = false;
	private String fileName = new String();
	private int layerHeight;
	private Engine engineInstance;
	/**
	 * <b>Background</b> <br>
	 * 
	 * Constructor ... 
	 */
	public Backgroung (String fileName) {
		
		engineInstance = Engine.getInstance();
		this.fileName = fileName;
		layerHeight = 0;
		engineInstance.addDrawable(this);
	}
	
	// DRAWABLE IMPLEMENTATION
	
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
	 * <b>render</b> <br>
	 * 
	 * Draw the img at 0 0 on layer 0
	 */
	@Override
	public void render(SpriteBatch batch,int currentLayer) {
		/* That stupid OpenGL only allow openGl manipulation in is own thread
		 * that's why we have to create everything in the callback */
		if(!createOnce)
		{
			create();
			createOnce = true;
		}
		if(currentLayer == layerHeight)
			batch.draw(imgTexture, 0, 0);
	}
	/**
	 * <b>getLayerHeight</b> <br>
	 * 
	 * Nothing special
	 */
	@Override
	public int getLayerHeight() { return layerHeight ; }
	
	@Override
	public boolean close() {
		
		engineInstance.removeDrawable(this);
		
		return true;
	}
}

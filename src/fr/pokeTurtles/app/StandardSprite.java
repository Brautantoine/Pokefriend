package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StandardSprite implements Drawable {

	protected int posx;
	protected int posy;
	
	protected Engine engine;
	
protected boolean createOnce = false;
	
	protected int layer;
	
	protected String fileName;
	protected Texture imgTexture;
	
	public StandardSprite(int posx, int posy, String fileName) {
		
		this.posx = posx;
		this.posy = posy;
		this.fileName = fileName;
		
		
		
		layer = 1;
		
		engine = Engine.getInstance();
		
		engine.addDrawable(this);
	}
	
	/**
	 * <b>create</b> <br>
	 */
	@Override
	public void create() {
		// TODO Auto-generated method stub
		imgTexture = new Texture(fileName);
		
	}
	/**
	 * <b>render</b> <br>
	 */
	@Override
	public void render(SpriteBatch batch, int currentLayerHeight) {
		// TODO Auto-generated method stub
		if(!createOnce)
		{
			create();
			createOnce = true;
		}
		if(layer == currentLayerHeight)
			batch.draw(imgTexture, posx, posy);
			
	}
	/**
	 * <b>getLayerHeight</b> <br>
	 */
	@Override
	public int getLayerHeight() {
		// TODO Auto-generated method stub
		return layer;
	}
	/**
	 * <b>close</b> <br>
	 * 
	 * remove the widget from both Clickable and Drawable list see {@link fr.pokeTurtles.app.Engine}
	 */
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		engine.removeDrawable(this);
		engine.disposeElement(imgTexture);
		return false;
	}

}

package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * <b>Drawable</b> <br>
 * 
 * Interface to specify callback used by the Engine to render elements on screen
 *
 */
public interface Drawable {
	
	abstract public void create();
	abstract public void render(SpriteBatch batch,int currentLayerHeight);
	abstract public int getLayerHeight();
	/** Drawables need to dereference themselves from the Engine in order to gracefully exit **/
	abstract public boolean close();

}

package fr.pokeTurtles.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
/**
 * <b>Engine</b> - singleton <br>
 * 
 * Singleton class that wrap all the I/O with libgdx
 */
public class Engine implements ApplicationListener, InputProcessor {
	/** DEBUG INTENDED **/
    private SpriteBatch batch;
    /** DEBUG INTENDED **/
    private BitmapFont font;
    /** All the drawable to render in screen **/
    private ArrayList<Drawable> elementToDraw;
    /** All the clickable that are waiting for touchDown event **/
    private ArrayList<Clickable> clickable;
    /** The Singleton instance **/
    private static Engine instance;
    /** tell the engine have been created **/
    private boolean ready = false;
    
    private ArrayList<Object> oldElements;
    
    /** Mutex to protect the drawable list from concurrent modification **/
    private ReentrantLock drawableMutex;
    
    private ReentrantLock clickableMutex;
    
    private Lock disposeMutex;
    
    final public int HEIGHT = 1920;
    final public int WIDHT = 1080;
    
    /**
     * <b>Engine</b> <br>
     * 
     * private constructor of the class, use the static method getInstance instead
     */
    private Engine() {
		// TODO Auto-generated constructor stub
	}
    /**
     * <b>getInstance</b> <br>
     * 
     * create the singleton instance if needed, the return the instance
     * 
     * @return Engine
     */
    static public Engine getInstance() {
    	if (instance == null) 
            instance = new Engine(); 
  
        return instance; 
    }
    /**
     * <b>create</b> <br>
     * 
     * create the libgdx instance
     */
    @Override
    public void create() {        
        batch = new SpriteBatch();    
        font = new BitmapFont();
        font.setColor(Color.RED);
        drawableMutex = new ReentrantLock();
        elementToDraw = new ArrayList<>();
        clickableMutex = new ReentrantLock();
        clickable = new ArrayList<>();
        disposeMutex = new ReentrantLock();
        oldElements = new ArrayList<>();
        instance = this;
        ready = true;
        Gdx.input.setInputProcessor(this);
    }
    /**
     * <b>dispose</b> <br>
     * 
     * Remove all the elements from memory and destroy them
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    /**
     * <b>render</b> <br>
     * 
     * draw the view, call all the drawable callback
     */
    @Override
    public void render() {        
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        
        batch.begin();
        font.draw(batch, "Hello World", 200, 200);
        drawableMutex.lock();
        if(elementToDraw.size() > 0) {
        	boolean core = true;
        	int layer = 0;
        	int highestLayer = -1;
        	// First method : loop for each element untill we are at the last layer (n^2)
        	while(core) {
        		for (Iterator<Drawable> elements = elementToDraw.iterator(); elements.hasNext();) {
        			Drawable element = elements.next();
        			try {
        				if (highestLayer < element.getLayerHeight())
            				highestLayer = element.getLayerHeight();
            			element.render(batch, layer);
        			}
        			catch (Exception e) {
        				if(e.getClass() == java.lang.NullPointerException.class) {
        					System.err.println("null pointer in the Drawable list will be removed");
        					elements.remove();
        					continue;
        				}
        				else 
        					throw e;
        			}
        			
        		}
        		if(layer == highestLayer)
        			core = false;
        		else {
        			layer++;
        		}
        			
        	}
        	// Second method : sort the element by layer then loop only once and draw (n*log(n))
        }
        drawableMutex.unlock();
        batch.end();
        
     // Dispose old elements ... of course it had to be done in openGL context for more fun
    	if(oldElements.size() > 0) {
    		disposeMutex.lock();
    		for(Iterator<Object> elements = oldElements.iterator(); elements.hasNext();) {
    			Object o = elements.next();
    			if(o.getClass() == Texture.class) {
    	    		((Texture)o).dispose();
    				System.err.println("free memory");}
    	    	else if (o.getClass() == Label.class)
    	    		;//((Label)element).dispose();
    			elements.remove();
    		}
    		disposeMutex.unlock();
    	}
    }

    @Override
    public void resize(int width, int height) {
    	System.out.println("resized ...");
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    /**
     * <b>addDrawable</b> <br>
     * 
     * add a new Drawable to the list
     * 
     * @param newDrawable
     */
    public void addDrawable(Drawable newDrawable) {
    	drawableMutex.lock();
    	elementToDraw.add(newDrawable);
    	drawableMutex.unlock();
    }
    /**
     * <b>removeDrawable</b> <br>
     * 
     * remove a Drawabme from the list
     * 
     * @param oldDrawable
     */
    public void removeDrawable(Drawable oldDrawable) {
    	//TODO dispose
    	drawableMutex.lock();
    	elementToDraw.remove(oldDrawable);
    	drawableMutex.unlock();
    }
    
    public void addClickable(Clickable newClickable) {
    	clickableMutex.lock();
    	clickable.add(newClickable);
    	clickableMutex.unlock();
    }
    
    public void removeClickable(Clickable oldClickable) {
    	clickableMutex.lock();
    	clickable.remove(oldClickable);
    	clickableMutex.unlock();
    }
    
    public boolean isReady() { return ready; }
    
    // We can't mix batch and shaperenderer that's why we need this, however it batch and shaperenderer don't like each over
    public void endBatch () { batch.end(); }
    public void startBatch () { batch.begin(); }
    
    public void disposeElement(Object element) {
    	disposeMutex.lock();
    	oldElements.add(element);
    	disposeMutex.unlock();
    }
    
    
//////////
	
	
 @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	clickableMutex.lock();
    	for (Iterator<Clickable> elements = clickable.iterator(); elements.hasNext();) {
			Clickable element = elements.next();
			try {
    			element.onClick(screenX, screenY);
			}
			catch (Exception e) {
				if(e.getClass() == java.lang.NullPointerException.class) {
					System.err.println("null pointer in the Clickable list will be removed");
					elements.remove();
					continue;
				}
				else 
					throw e;
			}
    	}
    	clickableMutex.unlock();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    	//System.err.println("x: "+screenX+" y: "+screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
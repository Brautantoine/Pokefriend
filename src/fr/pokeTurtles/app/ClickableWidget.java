package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * <b>ClickableWidget</b> <br>
 * 
 *	Widget to create a clickable static sprite
 */
public class ClickableWidget implements Drawable, Clickable {
	
	/** Engine instance **/
	protected Engine instance;
	/** delimite the Height of the clickable zone **/
	protected int selfHeight;
	/** delimite the Width of the clickable zone **/
	protected int selfWidth;
	
	protected int layerHeight;
	protected int posX;
	protected int posY;
	protected boolean createOnce;
	
	protected String fileName;
	protected Texture imgTexture;
	
	//ShapeRenderer shape;
	/**
	 * <b>ClickableWidget</b> <br>
	 * 
	 * @param posX start x position
	 * @param posY start y position
	 * @param height height of the object
	 * @param width width of the object
	 * @param img the img to draw
	 * 
	 * Construct the object with the specific params
	 */
	public ClickableWidget(int posX, int posY,int height, int width, String img) {
		this.posX = posX;
		this.posY = posY;
		this.selfHeight = height;
		this.selfWidth = width;
		fileName = img;
		
		layerHeight = 1;
		
		createOnce = false;
		
		instance = Engine.getInstance();
		
		instance.addClickable(this);
		instance.addDrawable(this);
	}
	/**
	 * <b>onClick</b> <br>
	 * 
	 * @param x x pos
	 * @param y y pos (inverted)
	 * 
	 * <br>The onClick callback, will be call by the Engine, had to be overrided when used see {@link fr.pokeTurtles.app.MainMenu}
	 */
	@Override
	public void onClick(int x, int y) {
		if (contains(x, y))
			System.out.println("click");
		
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
		if(layerHeight == currentLayerHeight) {
			// image manipulation
			/*Stage stage = new Stage(new ScreenViewport());
			Image img = new Image(imgTexture);
			img.setPosition(posX,posY);
			img.rotateBy(20);
			stage.addActor(img);
			stage.act();
			stage.draw();*/
			batch.draw(imgTexture, posX, posY);
		}
			
	}
	/**
	 * <b>getLayerHeight</b> <br>
	 */
	@Override
	public int getLayerHeight() {
		// TODO Auto-generated method stub
		return layerHeight;
	}
	/**
	 * <b>close</b> <br>
	 * 
	 * remove the widget from both Clickable and Drawable list see {@link fr.pokeTurtles.app.Engine}
	 */
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		instance.removeDrawable(this);
		instance.removeClickable(this);
		
		instance.disposeElement(imgTexture);
		return false;
	}
	/**
	 * <b>contains</b> <br>
	 * 
	 * @param x x pos
	 * @param y y pos (inverted if it come from the onClick callback)
	 * @return true if (x,y) is contained in the widget, else false
	 * 
	 * <br>square collision detections
	 */
	public boolean contains(int x, int y) {
		y = instance.WIDHT - y;
		boolean ret = (x > posX 				&&
				x < posX + selfWidth 	&&
				y > posY 				&&
				y < posY + selfHeight	);
		//System.err.println("ret : "+ret);
		//System.err.println("x: "+x+" y : "+y+" posX : "+posX+" posY : "+posY+" "+selfHeight+" "+selfWidth);
		return ret;
	}
	
	public void move (int posx, int posy) {
		this.posX = posx;
		this.posY = posy;
	}
	
}

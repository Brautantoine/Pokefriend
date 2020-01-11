package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * <b>ToggleableClickableWidget</b> <br>
 * 
 * A clickable widget wich toggle is sprite
 */
public class ToggleableClickableWidget extends ClickableWidget {

	private int offsetLimit;
	private int imageOffset; 
	
	/**
	 * <b>toggle...</b> <br>
	 * 
	 * @param posX
	 * @param posY
	 * @param height
	 * @param width
	 * @param img
	 * @param offsetLimit
	 */
	public ToggleableClickableWidget(int posX, int posY, int height, int width, String img, int offsetLimit) {
		super(posX, posY, height, width, img);
		
		this.offsetLimit = offsetLimit;
		imageOffset = 0;
		// TODO Auto-generated constructor stub
	}
	/**
	 * <b>toggle...</b> <br>
	 * 
	 * @param posX
	 * @param posY
	 * @param height
	 * @param width
	 * @param img
	 * @param offsetLimit
	 * @param startFrame
	 */
	public ToggleableClickableWidget(int posX, int posY, int height, int width, String img, int offsetLimit, int startFrame) {
		super(posX, posY, height, width, img);
		
		this.offsetLimit = offsetLimit;
		imageOffset = startFrame;
		// TODO Auto-generated constructor stub
	}
	/**
	 * <b>render</b> <br>
	 * 
	 * ...
	 */
	public void render(SpriteBatch batch, int currentLayerHeight) {
		
		if(!createOnce)
		{
			create();
			createOnce = true;
		}
		if(layerHeight == currentLayerHeight) {
			batch.draw(imgTexture, posX, posY, (selfWidth*imageOffset), 0, selfWidth, selfHeight);
		}
	}
	/**
	 * <b>toggle...</b> <br>
	 * 
	 * toggle the sprite to use
	 */
	public void toggleSprite() {
		imageOffset++;
		
		if(imageOffset > offsetLimit)
			imageOffset = 0;
	}

}

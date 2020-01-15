package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ToggleableSprite extends StandardSprite {

	private int frameLimit;
	private int frame;
	
	private int spriteHeight;
	private int spriteWidth;
	
	public ToggleableSprite(int posx, int posy, int spriteHeight, int spriteWidth, String fileName, int frameLimit) {
		super(posx, posy, fileName);
		this.spriteHeight = spriteHeight;
		this.spriteWidth = spriteWidth;
		this.frameLimit = frameLimit;
	}
	
	public ToggleableSprite(int posx, int posy, String fileName, int spriteHeight, int spriteWidth, int frameLimit, int frame) {
		super(posx, posy, fileName);
		this.spriteHeight = spriteHeight;
		this.spriteWidth = spriteWidth;
		this.frameLimit = frameLimit;
		this.frame = frame;
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
		if(layer == currentLayerHeight) {
			batch.draw(imgTexture, posx, posy, (spriteWidth*frame), 0, spriteWidth, spriteHeight);
		}
	}
	/**
	 * <b>toggle...</b> <br>
	 * 
	 * toggle the sprite to use
	 */
	public void toggleSprite() {
		frame++;
		
		if(frame > frameLimit)
			frame = 0;
	}

}

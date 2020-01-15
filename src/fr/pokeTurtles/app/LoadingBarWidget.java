package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingBarWidget implements Drawable {

	private int posx;
	private int posy;
	
	private int load;
	private int loadLimit;
	
	private String fileName;
	
	private Engine engine;
	
	private int layer;
	
	private Texture imgTexture;
	
	private boolean createOnce = false;
	
	private int animOffset;
	private int height;
	private int space;
	
	public LoadingBarWidget(int posx, int posy, int width, int height, int loadLimit, int load, int space, String fileName) {
		
		this.posx = posx;
		this.posy = posy;
		this.load = load;
		this.loadLimit = loadLimit;
		this.animOffset = width;
		this.height = height;
		this.space = space;
		
		engine = Engine.getInstance();
		engine.addDrawable(this);
		
		layer = 1;
		
		this.fileName = fileName;
	}

	@Override
	public void create() {
		
		imgTexture = new Texture(fileName);

	}

	@Override
	public void render(SpriteBatch batch, int currentLayerHeight) {

		if(!createOnce)
		{
			create();
			createOnce = true;
		}
		
		for (int i=0; i<loadLimit; i++)
		{
			if(i < load)
				batch.draw(imgTexture, posx+(i*space), posy, 0, 0, animOffset, height);
			else
				batch.draw(imgTexture, posx+(i*space), posy, animOffset, 0, animOffset, height);
		}
	}

	@Override
	public int getLayerHeight() {
		// TODO Auto-generated method stub
		return layer;
	}

	@Override
	public boolean close() {
		engine.removeDrawable(this);
		//imgTexture.dispose();
		return false;
	}
	
	public void fill(int load) {
		this.load += load;
		
		if(this.load > loadLimit)
			this.load = loadLimit;
	}
	
	public void unfill(int load) {
		this.load -= load;
		
		if(this.load < 0)
			this.load = 0;
	}

}

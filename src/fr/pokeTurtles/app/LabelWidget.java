package fr.pokeTurtles.app;

import org.omg.CORBA.FREE_MEM;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LabelWidget implements Drawable {
	
	private BitmapFont label;
	
	private int posx;
	private int posy;
	
	private String labelText;
	
	private Engine engine;
	
	private boolean createOnce = false;
	
	private int layer;

	public LabelWidget(int posx, int posy, String labelText) {
		
		this.posx = posx;
		this.posy = posy;
		
		this.labelText = labelText;
		
		layer = 1;
		
		engine = Engine.getInstance();
		engine.addDrawable(this);
	}

	@Override
	public void create() {
		label = new BitmapFont(new FileHandle("font/arcadeFont.fnt"));
		label.setColor(Color.BLUE);

	}

	@Override
	public void render(SpriteBatch batch, int currentLayerHeight) {
		
		if(!createOnce)
		{
			create();
			createOnce = true;
		}
	
		label.draw(batch, labelText, posx, posy);
	}

	@Override
	public int getLayerHeight() {
		return layer;
	}

	@Override
	public boolean close() {
		engine.removeDrawable(this);
		//label.dispose();
		return false;
	}

}

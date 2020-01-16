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
	
	private Color color;

	public LabelWidget(int posx, int posy, String labelText) {
		
		this.posx = posx;
		this.posy = posy;
		
		this.labelText = labelText;
		
		layer = 1;
		
		color = Color.BLACK;
		
		engine = Engine.getInstance();
		engine.addDrawable(this);
	}
	
public LabelWidget(int posx, int posy, String labelText, Color color) {
		
		this.posx = posx;
		this.posy = posy;
		
		this.labelText = labelText;
		
		layer = 1;
		
		this.color = color; 
		
		engine = Engine.getInstance();
		engine.addDrawable(this);
	}

	@Override
	public void create() {
		label = new BitmapFont(new FileHandle("font/arcadeFont.fnt"));
		label.setColor(color);

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
		engine.disposeElement(labelText);
		return false;
	}

}

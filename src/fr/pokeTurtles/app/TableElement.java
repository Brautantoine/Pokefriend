package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TableElement implements Drawable {

	protected TableElementType type;
	
	protected int posx;
	protected int posy;
	
	protected int layer;
	
	protected Texture imgTexture;
	protected boolean createOnce;
	
	protected Engine engine;
	
	public TableElement(TableElementType type, int posx, int posy) {
		_init(type,posx,posy);
	}
	
	private void _init(TableElementType type, int posx, int posy) {
		this.type = type;
		this.posx = posx;
		this.posy = posy;
		this.layer = 2;
		
		engine = Engine.getInstance();
		engine.addDrawable(this);
	}

	@Override
	public void create() {
		switch (type) {
		case BUSH:
			imgTexture = new Texture("img/layout/coreGame/treeSprite.png");
			break;
		case ROCK:
			imgTexture = new Texture("img/layout/coreGame/rockSprite.png");
			break;
		default:
			throw new RuntimeException("Unsupported Type by this class");
		}

	}

	@Override
	public void render(SpriteBatch batch, int currentLayerHeight) {
		if(!createOnce)
		{
			create();
			createOnce = true;
		}
		if(layer == currentLayerHeight)
			batch.draw(imgTexture, posx, posy);

	}

	@Override
	public int getLayerHeight() {
		return layer;
	}

	public void move(int posx, int posy) {
		this.posx = posx;
		this.posy = posy;
	}
	
	@Override
	public boolean close() {
		engine.removeDrawable(this);
		engine.disposeElement(imgTexture);
		return false;
	}
	
	public TableElementType getType() { return type; }

}

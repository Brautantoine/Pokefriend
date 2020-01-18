package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;

public class Turtles extends TableElement {

	private static int NB_TURTLES = 0;
	private static int NB_PLAYER = 0;
	private final int player;
	
	public Turtles(int posx, int posy) {
		super(TableElementType.TURTLES, posx, posy);
		NB_PLAYER++;
		player = NB_PLAYER;
	}
	
	/**
	 * @return the player
	 */
	public int getPlayer() {
		return player;
	}

	@Override
	public void create() {
		NB_TURTLES++;
		switch (NB_TURTLES) {
		case 1:
			imgTexture = new Texture("img/turtles/chartorSprite.png");
			break;
		case 2:
			imgTexture = new Texture("img/turtles/turtwigSprite.png");
			break;
		case 3:
			imgTexture = new Texture("img/turtles/caratrocSprite.png");
			break;
		case 4:
			imgTexture = new Texture("img/turtles/squirttle.png");
			break;
		default:
			throw new RuntimeException("More than 4 turtles instanciate");
		}
	}
	
	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		NB_TURTLES--;
		return super.close();
	}

}

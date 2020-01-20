package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;

public class Turtles extends TableElement {

	private static int NB_TURTLES = 0;
	private static int NB_PLAYER = 0;
	private final int player;
	
	private int gridX;
	private int gridY;
	
	private final int startGridX;
	private final int startGridY;
	
	private Direction direction;
	
	private final Direction startDirection;
	
	public Turtles(int posx, int posy, Direction direction, int gridX, int gridY) {
		super(TableElementType.TURTLES, posx, posy);
		NB_PLAYER++;
		player = NB_PLAYER;
		this.gridX = gridX;
		this.gridY = gridY;
		startGridX = gridX;
		startGridY = gridY;
		this.direction = direction;
		this.startDirection = this.direction;
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
		NB_TURTLES--;
		return super.close();
	}
	
	public void reset() {
		this.gridX = startGridX;
		this.gridY = startGridY;
		this.direction = startDirection;
		
		posx = posx+((gridX)*104)+20;
		posy = posy-((gridY)*104)+10;
	}
	
	/*public void move(int gridX, int gridY) {
		posx = posx+((gridX)*104)+20;
		posy = posy-((gridY)*104)+10;
	}*/

}

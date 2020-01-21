package fr.pokeTurtles.app;

import com.badlogic.gdx.graphics.Texture;

public class Cristal extends TableElement {

	private static int NB_CRISTAL = 0;
	//private static int NB_PLAYER = 0;
	//private final int player;
	private boolean reached;
	
	private int posI;
	private int posK;
	
	public Cristal(int posx, int posy, int posI, int posK) {
		super(TableElementType.PKCTR, posx, posy);
		//NB_PLAYER++;
		//player = NB_PLAYER;
		reached = false;
		
		this.posI = posI;
		this.posK = posK;
	}
	

	/**
	 * @return the player
	 */
	/*public int getPlayer() {
		return player;
	}*/
	public boolean isReached() {return reached;}

	@Override
	public void create() {
		NB_CRISTAL++;
		switch (NB_CRISTAL) {
		case 1:
			imgTexture = new Texture("img/layout/coreGame/pokecenterO.png");
			break;
		case 2:
			imgTexture = new Texture("img/layout/coreGame/pokecenter.png");
			break;
		case 3:
			imgTexture = new Texture("img/layout/coreGame/pokecenterG.png");
			break;
		case 4:
			imgTexture = new Texture("img/layout/coreGame/pokecenterB.png");
			break;
		default:
			throw new RuntimeException("More than 4 cristal instanciate");
		}
	}
	
	@Override
	public boolean close() {
		NB_CRISTAL--;
		return super.close();
	}
	
	public int getPosI() { return posI; }
	public int getPosK() { return posK; }

}
